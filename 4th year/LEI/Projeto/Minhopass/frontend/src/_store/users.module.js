import { createNamespacedHelpers } from 'vuex';
import { router } from '../_helpers';
import { userService } from '../_services';

export const users = {
    namespaced: true,
    state: {
        all: {},
        types: {},
        user: null, 
        eport: null,
        address: null,
        cv: null
    },
    actions: {

        /* Return all users */
        getAll({ commit }) {
            commit('getAllRequest');

            userService.getAll()
                .then(
                    users => commit('getAllSuccess', users),
                    error => commit('getAllFailure', error)
                );
        },

        /* Return an user with specific id */
        getUser({ commit }, { id }) {
            return new Promise(resolve => {
                commit('getUserRequest', { id });

                userService.getById(id, 'users')
                    .then(
                        user => {
                            commit('getUserSuccess', user),
                            resolve();
                        },
                        error => commit('getUserFailure', error)
                    );
            })   
        },

        /* Return an eportfolio with specific id */
        get_eport({ commit }, { id }) {
            return new Promise(resolve => {
                commit('getEportRequest', { id });

                userService.getById(id, 'eportfolios')
                    .then(
                        eport => {
                            commit('getEportSuccess', eport),
                            resolve();
                        },
                        error => commit('getEportFailure', error)
                    );
            })   
        },

        /* Insert eportfolio into database and associate that same eportfolio with the user who inserted it */
        async putEportfolio({ dispatch, commit }, { eportfolio, address, work,  address_work, education, address_education, skills, type, user, first }) {

            let eport_has_address= false
            let work_has_address = []
            let edu_has_address  = []

            let address_promises = []

            let is_empty = Object.values(JSON.parse(address)).every(x => x === null || x === '')
            if(!is_empty){
                address_promises.push(userService.create("enderecos", address, null))
                eport_has_address = true
            }   

            is_empty = true
            for(let i = 0; i < Object.values(JSON.parse(address_work))[0].length; i++){
                
                var data = Object.values(JSON.parse(address_work))[0][i]
                for(var key in data){
                    if(data[key] !== ""){
                        is_empty = false
                        break;
                    }
                }
                if(!is_empty){  
                    address_promises.push(userService.create("enderecos", JSON.stringify(Object.values(JSON.parse(address_work))[0][i]), null))
                    work_has_address.push(i)
                }
            }

            is_empty = true
            for(let i = 0; i < Object.values(JSON.parse(address_education))[0].length; i++){

                var data = Object.values(JSON.parse(address_education))[0][i]
                for(var key in data){
                    if(data[key] !== ""){
                        is_empty = false
                        break;
                    }
                }

                if(!is_empty){
                    address_promises.push(userService.create("enderecos", JSON.stringify(Object.values(JSON.parse(address_education))[0][i]), null))
                    edu_has_address.push(i)
                }
            }

            let ap = await Promise.all(address_promises)

            let work_promises = []
            let edu_promises = []
            let type_promises = []

            let inc = 0
            for(let i = 0; i < Object.values(JSON.parse(work))[0].length; i++){
                if(work_has_address.includes(i))
                    work_promises.push(userService.create("experiencia-profissionals", JSON.stringify(Object.values(JSON.parse(work))[0][i]), ap[inc++]))
                else
                    work_promises.push(userService.create("experiencia-profissionals", JSON.stringify(Object.values(JSON.parse(work))[0][i]), null))
            }

            for(let i = 0; i < Object.values(JSON.parse(education))[0].length; i++){
                if(edu_has_address.includes(i))
                    edu_promises.push(userService.create("educacaos", JSON.stringify(Object.values(JSON.parse(education))[0][i]), ap[inc++]))
                else
                    edu_promises.push(userService.create("educacaos", JSON.stringify(Object.values(JSON.parse(education))[0][i]), null))
            }
            
            let s = 0
            is_empty = Object.values(JSON.parse(skills)).every(x => x === null || x === '')
            if(!is_empty)
                s = await userService.create("competencias-pessoais", skills, null)
            else
                s = null

            is_empty = true
            for(let i = 0; i < Object.values(JSON.parse(type))[0].length; i++){

                var data = Object.values(JSON.parse(type))[0][i]
                for(var key in data){
                    if(data[key] !== ""){
                        is_empty = false
                        break;
                    }
                }

                if(!is_empty)
                    type_promises.push(userService.create("tipos", JSON.stringify(Object.values(JSON.parse(type))[0][i]), null))
            }

            let wp = await Promise.all(work_promises)
            let ep = await Promise.all(edu_promises)
            let tp = await Promise.all(type_promises)

            if(!eport_has_address)
                ap[0] = null

            for (var p in eportfolio) {
                    console.log("Elemento do eport: " + p);
                }

            userService.create_eport(eportfolio, ap[0], wp, ep, s, tp)
                .then(
                    eport => {
                        if(first){
                            userService.updateUser( user, eport)
                                .then(
                                    u => {
                                        userService.addFeed('Criação', new Date(), 'ePortefolio', 'Criaste o teu ePortefolio, parabéns!', u).then(
                                            f => {
                                                commit('getUserSuccess', u),
                                                
                                                router.push('/eportfolio');
                                                setTimeout(() => {
                                                // display success message after route change completes
                                                    dispatch('alert/success', 'Eportfolio adicionado', { root: true });
                                                })
                                            }
                                        )
                                    },
                                    error => {
                                        commit('getUserFailure', error);
                                        dispatch('alert/error', 'Falhou a adicionar primeiro eport', { root: true });
                                    }
                                )
                        }
                        else{
                            userService.getById(eport.id, 'eportfolios')
                                .then(
                                    c => {
                                        commit('getCVSuccess', c),
                                        setTimeout(() => {
                                            dispatch('alert/success', 'Eportfolio pronto a ser convertido', { root: true });
                                        })
                                    },
                                    error => {
                                        commit('getCVFailure', error);
                                        dispatch('alert/error', 'Falhou a adicionar segundo eport', { root: true });
                                    }
                                )
                        }

                    },
                    error => {
                        commit('getUserFailure', error);
                        dispatch('alert/error', 'Falhou a validar', { root: true });
                    }
                ); 
        } , 
    
        /* Edit eportfolio */
        async editEportfolio({ dispatch, commit }, { eportfolio, address, work,  address_work, education, address_education, skills, type, user, id_eport }) {
            commit('putEportfolioRequest');

            let eport_has_address= false
            let work_has_address = []
            let edu_has_address  = []

            let address_promises = []

            let is_empty = Object.values(JSON.parse(address)).every(x => x === null || x === '')
            if(!is_empty){
                address_promises.push(userService.create("enderecos", address, null))
                eport_has_address = true
            }   

            is_empty = true
            for(let i = 0; i < Object.values(JSON.parse(address_work))[0].length; i++){
                
                var data = Object.values(JSON.parse(address_work))[0][i]
                for(var key in data){
                    if(data[key] !== ""){
                        is_empty = false
                        break;
                    }
                }
                if(!is_empty){  
                    address_promises.push(userService.create("enderecos", JSON.stringify(Object.values(JSON.parse(address_work))[0][i]), null))
                    work_has_address.push(i)
                }
            }

            is_empty = true
            for(let i = 0; i < Object.values(JSON.parse(address_education))[0].length; i++){

                var data = Object.values(JSON.parse(address_education))[0][i]
                for(var key in data){
                    if(data[key] !== ""){
                        is_empty = false
                        break;
                    }
                }

                if(!is_empty){
                    address_promises.push(userService.create("enderecos", JSON.stringify(Object.values(JSON.parse(address_education))[0][i]), null))
                    edu_has_address.push(i)
                }
            }

            let ap = await Promise.all(address_promises)

            let work_promises = []
            let edu_promises = []
            let type_promises = []

            let inc = 0
            for(let i = 0; i < Object.values(JSON.parse(work))[0].length; i++){
                if(work_has_address.includes(i))
                    work_promises.push(userService.create("experiencia-profissionals", JSON.stringify(Object.values(JSON.parse(work))[0][i]), ap[inc++]))
                else
                    work_promises.push(userService.create("experiencia-profissionals", JSON.stringify(Object.values(JSON.parse(work))[0][i]), null))
            }

            for(let i = 0; i < Object.values(JSON.parse(education))[0].length; i++){
                if(edu_has_address.includes(i))
                    edu_promises.push(userService.create("educacaos", JSON.stringify(Object.values(JSON.parse(education))[0][i]), ap[inc++]))
                else
                    edu_promises.push(userService.create("educacaos", JSON.stringify(Object.values(JSON.parse(education))[0][i]), null))
            }
            
            let s = 0
            is_empty = Object.values(JSON.parse(skills)).every(x => x === null || x === '')
            if(!is_empty)
                s = await userService.create("competencias-pessoais", skills, null)
            else
                s = null

            is_empty = true
            for(let i = 0; i < Object.values(JSON.parse(type))[0].length; i++){

                var data = Object.values(JSON.parse(type))[0][i]
                for(var key in data){
                    if(data[key] !== ""){
                        is_empty = false
                        break;
                    }
                }

                if(!is_empty)
                    type_promises.push(userService.create("tipos", JSON.stringify(Object.values(JSON.parse(type))[0][i]), null))
            }

            let wp = await Promise.all(work_promises)
            let ep = await Promise.all(edu_promises)
            let tp = await Promise.all(type_promises)

            if(!eport_has_address)
                ap[0] = null

            for (var p in eportfolio) {
                    console.log("Elemento do eport: " + p);
                }

            userService.updateEport(eportfolio, ap[0], wp, ep, s, tp, id_eport)
                .then(
                    eport => {
                        userService.getById( user.id, 'users')
                            .then(
                                u => {
                                    userService.addFeed('Edição', new Date(), 'ePortefolio', 'Editaste o teu ePortefolio', u).then(
                                        f => {
                                            commit('getUserSuccess', u),

                                            router.push('/eportfolio');
                                            setTimeout(() => {
                                                // display success message after route change completes
                                                dispatch('alert/success', 'Eportfolio editado', { root: true });
                                            })
                                        }
                                    )
                                },
                                error => {
                                    commit('getUserFailure', error);
                                    dispatch('alert/error', error, { root: true });
                                }
                            )
                    },
                    error => {
                        commit('getUserFailure', error);
                        dispatch('alert/error', error, { root: true });
                    }
                ); 
        },

        /* Delete eportfolio */
        delete_eport({ dispatch, commit }, { user }) {
            return new Promise(resolve => {
            commit('putEportfolioRequest');

            var eport = user.eportfolios[0]

            userService.deleteEportUser(user)
                .then(
                    u => {
                        //commit('getUserSuccess', u);
                        userService.removeEport( eport )
                            .then(
                                e => {
                                    console.log("Novo user: " + u)
                                    commit('putEportfolioSuccess', u)
                                
                                    setTimeout(() => {
                                      // display success message after route change completes
                                      dispatch('alert/success', 'Eportfolio removido', { root: true });
                                    })
                                    resolve()
                                },
                                error => {
                                    commit('putEportfolioFailure', error);
                                    dispatch('alert/error', error, { root: true });
                                }
                            )
                    },
                    error => {
                        commit('getUserFailure', error);
                        dispatch('alert/error', error, { root: true });
                    }
                );
            })
        },

        /* Delete account */
        delete_account({ dispatch, commit }, { user }) {
            return new Promise(resolve => {
                userService.deleteAccountUser(user.id)
                    .then(
                        u  => {

                            router.push('/home')
                            setTimeout(() => {
                              // display success message after route change completes
                              dispatch('alert/success', 'Conta removida', { root: true });
                            })
                            resolve()
                        },
                        error => {
                            commit('getUserFailure', error);
                            dispatch('alert/error', error, { root: true });
                        }
                    )
            })
        },

        /* Edit perfil */
        editPerfil({ dispatch, commit }, { password, user }){
            commit('getUserRequest', { user });

            userService.editUser(password, user.id)
                .then(
                    u => {
                        userService.addFeed('Edição', new Date(), 'Conta', 'Shhhh, alteraste a tua password', u).then(
                            f => {
                                commit('getUserSuccess', u),
                                
                                setTimeout(() => {
                                // display success message after route change completes
                                    dispatch('alert/success', 'Password alterada', { root: true });
                                })
                            }
                        )
                    },
                    error => {
                        commit('getUserFailure', error)
                        dispatch('alert/error', error, { root: true })
                    }
                );
        },

        /* Edit account */
        edit_account({ dispatch, commit }, { user }){
            userService.updateAccountUser(user)
                .then(
                    u => {
                        userService.addFeed('Edição', new Date(), 'Conta', 'Alteraste os dados da tua conta.', u).then(
                            f => {
                                commit('getUserSuccess', u),
                                
                                setTimeout(() => {
                                // display success message after route change completes
                                    dispatch('alert/success', 'Conta editada', { root: true });
                                })
                            }
                        )
                    },
                    error => {
                        if(error[0].messages[0].message.localeCompare("Email already taken") == 0)
                            dispatch('alert/error', 'Email já utilizado', { root: true });
                        else   
                            dispatch('alert/error', 'Username já utilizado', { root: true });
                    }
                );
        },

        /* Insert CV into database and associate that same CV to the user that inserted */
        put_file({ dispatch, commit }, { user, file, tipo}){

            userService.createFile(file)
                .then(
                    cv => {
                        console.log(JSON.stringify(cv))
                        userService.updateUserFile( user, cv, tipo)
                            .then(
                                u => {
                                    userService.addFeed('Adição', new Date(), 'Biblioteca', 'Introduziste novo documento na biblioteca(' + tipo + ')', u).then(
                                        f => {
                                            commit('getUserSuccess', u),
                                            
                                            router.push('/biblioteca')
                                            setTimeout(() => {
                                                // display success message after route change completes
                                                if(tipo == 'cvs')
                                                    dispatch('alert/success', 'Novo CV adicionado', { root: true });
                                                else if(tipo == 'certificados')
                                                    dispatch('alert/success', 'Novo certificado/diploma adicionado', { root: true });
                                                else if(tipo == 'cartas')
                                                    dispatch('alert/success', 'Nova carta de apresentação adicionada', { root: true });
                                                else
                                                    dispatch('alert/success', 'Novo documento adicionado', { root: true });

                                            })
                                        }
                                    ) 
                                },
                                error => {
                                    commit('getUserFailure', error);
                                    dispatch('alert/error', 'Aconteceu um erro', { root: true });
                                })
            })
        },

        /* Get all extra types of eportfolio's information */
        get_types({commit}){
            commit('getTypesRequest');

            userService.getTypes()
                .then(
                    types => commit('getTypesSuccess', types),
                    error => commit('getTypesFailure', error)
                );
        },

        /* Delete an operation from feed */
        delete_operation({dispatch, commit}, {feed, user}){
            userService.updateFeed(feed, user)
                .then(
                    u => {
                        commit('getUserSuccess', u)
                        setTimeout(() => {
                            // display success message after route change completes
                            dispatch('alert/success', 'Feed atualizado', { root: true });

                        })
                    },
                    error => {
                        commit('getUserFailure', error);
                        dispatch('alert/error', 'Aconteceu um erro', { root: true });
                    }
                )
        },

        /* Delete a document from library */
        delete_document({dispatch, commit}, {docs, user, type}){
            userService.updateLibrary(docs, user, type)
                .then(
                    u => {
                        userService.addFeed('Remoção', new Date(), 'Biblioteca', 'Removeste um documento da tua biblioteca(' + type +')', u).then(
                            f => {
                                commit('getUserSuccess', u)
                                setTimeout(() => {
                                    // display success message after route change completes
                                    dispatch('alert/success', 'Biblioteca atualizada', { root: true });
                                
                                })
                            }
                        )
                    },
                    error => {
                        commit('getUserFailure', error);
                        dispatch('alert/error', 'Aconteceu um erro', { root: true });
                    }
                )
        },

        /* Return an eportfolio with specific id */
        get_address({ commit }, { id }) {
            return new Promise(resolve => {
                commit('getAddressRequest', { id });

                userService.getById(id, 'enderecos')
                    .then(
                        address => {
                            commit('getAddressSuccess', address),
                            resolve();
                        },
                        error => commit('getAddressFailure', error)
                    );
            })   
        },
                
    },

    mutations: {
        getAllRequest(state) {
            state.all = { loading: true };
        },
        getAllSuccess(state, users) {
            state.all = { params: users };
        },
        getAllFailure(state, error) {
            state.all = { error };
        },
        getUserRequest(state, id) {
            state.user = { loading: true };
        },
        getUserSuccess(state, user) {
            state.user = { params: user };
        },
        getUserFailure(state, error) {
            state.user = { error };
        },
        getEportRequest(state, id) {
            state.eport = { loading: true };
        },
        getEportSuccess(state, eport) {
            state.eport = { params: eport };
        },
        getEportFailure(state, error) {
            state.eport = { error };
        },
        getCVRequest(state, id) {
            state.cv = { loading: true };
        },
        getCVSuccess(state, cv) {
            state.cv = { params: cv };
        },
        getCVFailure(state, error) {
            state.cv = { error };
        },
        getAddressRequest(state, id) {
            state.address = { loading: true };
        },
        getAddressSuccess(state, address) {
            state.address = { params: address };
        },
        getAddressFailure(state, error) {
            state.address = { error };
        },
        putEportfolioRequest(state) {
            state.user = { loading: true };
        },
        putEportfolioSuccess(state, user) {
            state.user = { params: user };
        },
        putEportfolioFailure(state, error) {
            state.user = { error };
        },
        getTypesRequest(state) {
            state.types = { loading: true };
        },
        getTypesSuccess(state, types) {
            state.types = { params: types };
        },
        getTypesFailure(state, error) {
            state.types = { error };
        },
    }
}
