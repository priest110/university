package Servidor;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class NotificationLock {
    private ReentrantLock lock;
    private Condition threadCriadaAposLogin;
    private Condition servidor;
    private Condition sendToClient;
    private boolean publicacao; // True caso seja feita uma publicação
    private String autor;
    private String titulo;

    /**
     * Método construtor
     */
    public NotificationLock(){
        this.lock = new ReentrantLock();
        this.threadCriadaAposLogin = this.lock.newCondition();
        this.sendToClient = this.lock.newCondition();
        this.servidor = this.lock.newCondition();
        this.publicacao = false;
        this.autor = null;
        this.titulo = null;
    }

    /**
     * Get do título da música publicada
     * @return      título
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * Get do autor da música publicada
     * @return      autor
     */
    public String getAutor() {
        return autor;
    }

    /**
     * Método que espera que seja feita alguma publicação, quando é feita, envia um sinal para os users que estão loggados
     * @throws InterruptedException
     */
    public void sendLock() throws InterruptedException {
        this.lock.lock();

        while(!publicacao)
            servidor.await(); /* O servidor espera que seja feita alguma publicação */

        /* Quando receber a informação que foi feita uma publicação */
        this.publicacao = false;
        threadCriadaAposLogin.signalAll(); /* avisa as threads criadas após o login dos utilizadores que foi feita uma publicação */

        this.lock.unlock();
    }

    /**
     * Método que avisa o servidor que foi feita uma publicação
     * @param titulo        da música
     * @param autor         da música
     */
    public void feitaPublicao(String titulo, String autor){
        this.lock.lock();

        this.autor = autor;
        this.titulo = titulo;
        this.publicacao = true;
        servidor.signal(); /* dá sinal ao servidor que foi feita uma publicação */

        this.lock.unlock();
    }

    /**
     * Método que espera que seja comunicado à thread(responsável por comunicar com cliente) que foi feita uma publicação
     * @throws InterruptedException
     */
    public void sendToClient() throws InterruptedException {
        this.lock.lock();

        sendToClient.await(); /* espera por receber a informação que foi feita uma publicação */

        this.lock.unlock();
    }

    /**
     * Método que espera que o serivor comunique à thread(criada após login do cliente) que foi feita uma publicação
     * @throws InterruptedException
     */
    public void receiveLock() throws InterruptedException {
        this.lock.lock();

        threadCriadaAposLogin.await(); /* espera pelo signal do server */
        sendToClient.signal(); /* manda aviso à thread que comunica com o cliente que foi efetuada uma publicação */

        this.lock.unlock();
    }
}
