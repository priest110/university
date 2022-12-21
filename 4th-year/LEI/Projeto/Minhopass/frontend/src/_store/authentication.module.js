import { userService } from '../_services';
import { router } from '../_helpers';

const user = JSON.parse(localStorage.getItem('user'));
const initialState = user
    ? { status: { loggedIn: true }, user }
    : { status: {}, user: null };

export const authentication = {
    namespaced: true,
    state: initialState,
    actions: {
        login({ dispatch, commit }, { identifier, password }) {
            return new Promise(resolve => {
                commit('loginRequest', { identifier });

                userService.login(identifier, password).then( u => {
                    commit('loginSuccess', u);
                    resolve();
                }, error => {
                    commit('loginFailure', error);
                    dispatch('alert/error', 'Dados inválidos!', { root: true });    
                });
            });
        },
        
        logout({ commit }) {
            userService.logout();
            commit('logout');
        },

        register({ dispatch, commit }, user) {
            commit('registerRequest', user);
        
            userService.register(user).then(user => {
                commit('registerSuccess', user);
                router.push('/login');
                setTimeout(() => {
                    dispatch('alert/success', 'Registro efetuado com sucesso', { root: true }); // mensagem de sucesso após mudar de route
                })
            }, error => {
                commit('registerFailure', error);
                if(error[0].messages[0].message.localeCompare("Email already taken") == 0)
                    dispatch('alert/error', 'Email já utilizado', { root: true });
                else   
                    dispatch('alert/error', 'Username já utilizado', { root: true });
            });
        },

        getUser({ commit }, { id }) {
            commit('getUserRequest', { id });

            userService.getById(id).then( user => {
                commit('getUserSuccess', user)
            }, error => 
                commit('getUserFailure', error));
        }
    },
    mutations: {
        loginRequest(state, user) {
            state.status = { loggingIn: true };
            state.user = user;
        },
        loginSuccess(state, user) {
            state.status = { loggedIn: true };
            state.user = user;
        },
        loginFailure(state) {
            state.status = {};
            state.user = null;
        },
        logout(state) {
            state.status = {}; 
            state.user = null;
        },
        registerRequest(state, user) {
            state.status = { registering: true };
        },
        registerSuccess(state, user) {
            state.status = {};
        },
        registerFailure(state, error) {
            state.status = {};
        },
        getUserRequest(state, id) {
            state.user = { loading: true };
        },
        getUserSuccess(state, user) {
            state.user = { params: user };
        },
        getUserFailure(state, error) {
            state.user = { error };
        }
    }
}
