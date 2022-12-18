package Servidor;

import Comum.DadosInvalidosException;
import Comum.UsernameExistenteException;

import java.util.HashMap;
import java.util.Map;

public class ListaUtilizadores {
    private Map<String, Utilizador> utilizadores;

    /**
     * Método construtor
     */
    public ListaUtilizadores(){
        this.utilizadores = new HashMap<>();
    }


    /**
     * Regista um novo utilizador
     * @param user      do utilizador
     * @param pass      do utilizador
     * @throws UsernameExistenteException
     */
    public synchronized void registarUtilizador(String user, String pass) throws UsernameExistenteException{
        if(this.utilizadores.containsKey(user))
            throw new UsernameExistenteException();
        this.utilizadores.put(user, new Utilizador(user, pass));
    }

    /**
     * Verifica se um utilizador existe/é válido, ou seja, se o
     * user inserido existe e se a pass inserida corresponde à do
     * mesmo, caso exista
     * @param user      do utilizador
     * @param pass      do utilizador
     * @return  true caso seja válido
     * @throws DadosInvalidosException
     */
    public synchronized boolean utilizadorValido(String user, String pass) throws DadosInvalidosException {
        if(!(this.utilizadores.containsKey(user)) || !(this.utilizadores.get(user).passCorrecta(pass)))
            throw new DadosInvalidosException();
        return true;
    }

    /**
     * Get de um determinado utilizador
     * @param user      do utilizador
     * @return  Utilizador associado ao user
     */
    public synchronized Utilizador getUtilizador(String user){
        return this.utilizadores.get(user);
    }

}
