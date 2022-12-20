import config from 'config';
import { authHeader } from '../_helpers';
import { special_authHeader } from '../_helpers/special_auth-header';

export const userService = {
    login,
    logout,
    register,
    getAll,
    getById,
    create_eport,
    updateEport,
    updateUser,
    deleteEportUser,
    removeEport,
    editUser,
    createFile,
    updateUserFile,
    create,
    getTypes,
    deleteAccountUser,
    updateAccountUser,
    updateFeed,
    addFeed,
    updateLibrary
};

/* POST user login */
function login(identifier, password) {
    const requestOptions = {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ identifier, password })
    };

    return fetch(`${config.apiUrl}/auth/local`, requestOptions)
        .then(handleResponse)
        .then(user => {
            // login successful if there's a jwt token in the response
            if (user.jwt) {
                // store user details and jwt token in local storage to keep user logged in between page refreshes
                localStorage.setItem('user', JSON.stringify(user));
            }

            return user;
        });
}

/* User logout */
function logout() {
    // remove user from local storage to log user out
    localStorage.removeItem('user');
}

/* POST user registration */
function register(user) {
    const requestOptions = {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(user)
    };

    return fetch(`${config.apiUrl}/auth/local/register`, requestOptions).then(handleResponse);
}

/* GET all users */
function getAll() {
    const requestOptions = {
        method: 'GET',
        headers: authHeader()
    };

    return fetch(`${config.apiUrl}/users`, requestOptions).then(handleResponse);
}

/* GET user by ID */
function getById(id, database) {
    const requestOptions = {
        method: 'GET',
        headers: authHeader()
    };

    return fetch(`${config.apiUrl}/${database}/${id}`, requestOptions).then(handleResponse);
}

/* PUT user's edition */
function editUser(password, id){

    console.log("pass: " + password)
    console.log("id: " + id)

    const requestOptions = {
        method: 'PUT',
        headers: special_authHeader(),
        body: JSON.stringify(password)
    };

    return fetch(`${config.apiUrl}/users/${id}`, requestOptions).then(handleResponse);

}

/*  UPDATE user's account */
function updateAccountUser(user){
    console.log(user)
    const requestOptions = {
        method: 'PUT',
        headers: special_authHeader(),
        body: JSON.stringify(user)
    };

    return fetch(`${config.apiUrl}/users/${user.id}`, requestOptions).then(handleResponse);
}

/*  DELETE user's account */
function deleteAccountUser(id){
    const requestOptions = {
        method: 'DELETE',
        headers: authHeader(),
    };

    return fetch(`${config.apiUrl}/users/${id}`, requestOptions).then(handleResponse);
}

/* PUT user with eportfolio */
function updateUser(user, eportfolio){

    var eports
    
    console.log("PDF: " + JSON.stringify(eportfolio))
    console.log("Cvs 1 " + JSON.stringify(user.eportfolios))
    if (typeof user.eportfolios === 'undefined'){
        eports = '{"eportfolios" : [' + JSON.stringify(eportfolio) + ']}'
    }

    else {
        user['eportfolios'].push({"_id" : eportfolio.id})
        eports = '{"eportfolios" :' + JSON.stringify(user.eportfolios) + '}'
    }

    const requestOptions = {
        method: 'PUT',
        headers: special_authHeader(),
        body: eports
    };

    return fetch(`${config.apiUrl}/users/${user.id}`, requestOptions).then(handleResponse);
}

/* PUT user whithout eportfolio */
function deleteEportUser(user){
    var eports
    console.log(user.eportfolios)

    user['eportfolios'].shift()
    eports = '{"eportfolios" :' + JSON.stringify(user.eportfolios) + '}'

    const requestOptions = {
        method: 'PUT',
        headers: special_authHeader(),
        body: eports
    };
    
    return fetch(`${config.apiUrl}/users/${user.id}`, requestOptions).then(handleResponse);
}

/* POST eportfolio */
function create_eport(eportfolio, address, work, education, skills, type) {
    var aux = JSON.parse(eportfolio.get('form'))

    if(address != null)
        aux['endereco'] = address

    console.log(aux)

    for(let w of work){
        if(typeof aux['trabalhos'] === 'undefined'){
            
        console.log(w)
            aux['trabalhos'] = [w]
        }
        else
            aux['trabalhos'].push(w)
    }

    if(skills != null)
        aux['competencias_pessoais'] = skills

    for(let e of education){
        if(typeof aux['educacoes'] === 'undefined')
            aux['educacoes'] = [e]
        else
            aux['educacoes'].push(e)
    }

    for(let t of type){
        if(typeof aux['tipos'] === 'undefined')
            aux['tipos'] = [t]
        else
            aux['tipos'].push(t)
    }
    console.log(aux)

    eportfolio.delete('form')
    eportfolio.set('data', JSON.stringify(aux)) 
    for(var p in eportfolio)
        console.log(p)

    const requestOptions = {
        method: 'POST',
        headers: authHeader(),
        body: eportfolio
    };

    return fetch(`${config.apiUrl}/eportfolios`, requestOptions).then(handleResponse);
}

/* PUT eportfolio's edition */
function updateEport(eportfolio, address, work, education, skills, type, id_eport) {
    
    var aux = JSON.parse(eportfolio.get('form'))

    if(address != null)
        aux['endereco'] = address

    console.log(aux)

    for(let w of work){
        if(typeof aux['trabalhos'] === 'undefined'){
            
        console.log(w)
            aux['trabalhos'] = [w]
        }
        else
            aux['trabalhos'].push(w)
    }

    if(skills != null)
        aux['competencias_pessoais'] = skills

    for(let e of education){
        if(typeof aux['educacoes'] === 'undefined')
            aux['educacoes'] = [e]
        else
            aux['educacoes'].push(e)
    }

    for(let t of type){
        if(typeof aux['tipos'] === 'undefined')
            aux['tipos'] = [t]
        else
            aux['tipos'].push(t)
    }
    console.log(aux)

    eportfolio.delete('form')
    eportfolio.set('data', JSON.stringify(aux)) 
    for(var p in eportfolio)
        console.log(p)

    const requestOptions = {
        method: 'PUT',
        headers: authHeader(),
        body: eportfolio
    };

    return fetch(`${config.apiUrl}/eportfolios/${id_eport}`, requestOptions).then(handleResponse);
}

/* DELETE eportfolio */
function removeEport(eport){

    const requestOptions = {
        method: 'DELETE',
        headers: authHeader(),
    };

    return fetch(`${config.apiUrl}/eportfolios/${eport.id}`, requestOptions).then(handleResponse);
}

/* POST CV */
function createFile(cv) {
    
    const requestOptions = {
        method: 'POST',
        headers: authHeader(),
        body: cv
    };

    return fetch(`${config.apiUrl}/upload/`, requestOptions).then(handleResponse);
}

/* PUT user with file with specific type(could be certificate, cv or other stuff) */
function updateUserFile(user, file, type){
    var data

    if (typeof user[type] === 'undefined')
        data = '{"' + type + '": [' + JSON.stringify(file[0]) + ']}'
    else {
        user[type].push(file[0])
        data = '{"' + type + '":' + JSON.stringify(user[type]) + '}'
    }

    const requestOptions = {
        method: 'PUT',
        headers: special_authHeader(),
        body: data
    };

    return fetch(`${config.apiUrl}/users/${user.id}`, requestOptions).then(handleResponse);
}

/* Create data with [data_mame] [data_content] [data_address] */
 function create(form, content, address){
    console.log("-------------------------------------------------------")
    console.log(form)
    console.log(content)
    console.log(address)
    console.log("-------------------------------------------------------")
    var aux = JSON.parse(content)
    return new Promise(resolve => {
        setTimeout(() => {
            if(address != null)
                aux['endereco'] = address
            const requestOptions = {
                method: 'POST',
                headers: special_authHeader(),
                body: JSON.stringify(aux)
            };
    
            resolve(fetch(`${config.apiUrl}/${form}`, requestOptions).then(handleResponse));
          }, 2000);
        
    })
}

/* Get all types of extra information */
function getTypes(){
    const requestOptions = {
        method: 'GET',
        headers: authHeader()
    };

    return fetch(`${config.apiUrl}/tipos`, requestOptions).then(handleResponse);
}

/* DELETE operation from feed */
function updateFeed(feed, user){
    const requestOptions = {
        method: 'PUT',
        headers: special_authHeader(),
        body: '{"feed" : ' + JSON.stringify(feed) + '}'
    };

    return fetch(`${config.apiUrl}/users/${user.id}`, requestOptions).then(handleResponse);
}

/* ADD operation to feed */
function addFeed(operation, when, where, note, user){
    var aux

    if(user.feed.length > 0){
        user.feed.push({operacao: operation, data: when, tipo: where, nota: note })
        aux = JSON.stringify(user.feed)
    }
    else    
        aux = '{"feed" : [{"operacao": ' + operation + ', "data": ' + when + ', "tipo": ' + where + ', "nota":' + note + '}]}'
    console.log(aux)
    const requestOptions = {
        method: 'PUT',
        headers: special_authHeader(),
        body: aux
    };

    return fetch(`${config.apiUrl}/users/${user.id}`, requestOptions).then(handleResponse);
}




/* DELETE document */
function updateLibrary(docs, user, type){
    const requestOptions = {
        method: 'PUT',
        headers: special_authHeader(),
        body: '{"' + type + '" : ' + JSON.stringify(docs) + '}'
    };

    return fetch(`${config.apiUrl}/users/${user.id}`, requestOptions).then(handleResponse);
}

/* Handle response from backend */
function handleResponse(response) {
    return response.text().then(text => {
        const data = text && JSON.parse(text);
        if (!response.ok) {
            if (response.status === 401) {
                // auto logout if 401 response returned from api
                logout();
                location.reload(true);
            }

            const error = (data && data.message) || response.statusText;
            return Promise.reject(error);
        }
        return data;
    });
}