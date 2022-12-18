package Servidor;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class NotificationToClientThread implements Runnable {
    private Socket cliente;
    private NotificationLock lock;

    protected DataOutputStream out;

    /**
     * Método construtor da thread do servidor criada para enviar a música publicada ao cliente
     * @param cliente       socket
     * @param lock          NotificationLock
     * @throws IOException
     */
    public NotificationToClientThread(Socket cliente,NotificationLock lock) throws IOException {
        this.cliente = cliente;
        this.lock = lock;
        this.out = new DataOutputStream(cliente.getOutputStream());
    }

    /**
     * Run da thread
     */
    public void run() {
        while (true) {
            try {
                this.lock.sendToClient();
                out.writeUTF("Notificação: '" + this.lock.getTitulo() + "'" + " do " + this.lock.getAutor() +" adicionada.");

            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        }
    }
}
