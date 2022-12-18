package Servidor;

public class NotificationReceiveThread implements Runnable{
    private NotificationLock lock;

    /**
     * Método construtor
     * @param thread        que está a executar a tarefa de login
     */
    public NotificationReceiveThread(WorkThread thread){
        this.lock = thread.getLock_note();
    }

    /**
     * Run da thread
     */
    public void run() {
        while (true) {
            try {
                this.lock.receiveLock(); /* Está à espera de receber notificação */

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
