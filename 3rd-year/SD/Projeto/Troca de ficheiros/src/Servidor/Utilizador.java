package Servidor;

import java.util.concurrent.locks.ReentrantLock;

public class Utilizador {
    private String nome;
    private String pass;
    private ReentrantLock lockUtlizador;

    protected boolean logged;

    /**
     * Método construtor
     * @param nome      do utilizador
     * @param pass      do utilizador
     */
    public Utilizador(String nome, String pass) {
        this.nome = nome;
        this.pass = pass;
        this.lockUtlizador = new ReentrantLock();
        this.logged = false;
    }

    /**
     * Verifica se o Utilizador está logged.
     * @return      true caso esteja
     */
    public boolean isLogged() {
        return this.logged;
    }

    public void setLogged(boolean logged) {
        this.logged = logged;
    }

    /**
     * Verifica se a password está correta
     * @param pass      a verificar
     * @return
     */
    public boolean passCorrecta(String pass) {
        return this.pass.equals(pass);
    }

    /** Lock do objeto Utilizador
     */
    public void lock(){
        this.lockUtlizador.lock();
    }

    /** Unlock do objeto Utilizador
     */
    public void unlock(){
        this.lockUtlizador.unlock();
    }
}
