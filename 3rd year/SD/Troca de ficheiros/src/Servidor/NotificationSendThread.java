package Servidor;

public class NotificationSendThread implements Runnable {
    private NotificationLock lock;

    /**
     * Método constutor da thread do servidor que envia notificação à thread que é criada após o login de um utilizador
     * @param lock      NoticationLock
     */
    public NotificationSendThread(NotificationLock lock){
        this.lock = lock;
    }

    /**
     * Run da thread
     */
    public void run() {
        while (true) {
            try {
                lock.sendLock(); /* Thread do servidor que envia notifição */
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
