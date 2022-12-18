package Cliente;

import java.io.DataInputStream;
import java.io.IOException;

public class NotificationClientThread implements Runnable{
    private DataInputStream in;

    /**
     * Método construtor da thread responsavél por emitir a noticação de nova música no System.out, mandada pelo servidor
     * @param in
     * @throws IOException
     */
    public NotificationClientThread(DataInputStream in) throws IOException {
        this.in = in;
    }

    /**
     * Run da thread
     */
    public void run() {
        while (!Thread.interrupted()) {
            try {
                String musica = this.in.readUTF();
                System.out.println(musica);
            } catch (IOException e) {
                break; /* Quando a thread é interrompida */
            }
        }
    }
}
