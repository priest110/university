import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class WorkThread implements Runnable{
    private Socket cliente;
    protected DataInputStream in;
    protected DataOutputStream out;

    /**
     * Método construtor
     *
     * @param c         socket do cliente
     * @throws IOException
     */
    public WorkThread(Socket c) throws IOException {
        this.cliente = c;
        this.in = new DataInputStream(cliente.getInputStream());
        this.out = new DataOutputStream(cliente.getOutputStream());
    }

    /**
     * Run da thread
     */
    public void run(){
        try {
            String cmd;
            while ((cmd = in.readUTF()) != null) {
                System.out.println("Li do socket: " + cmd);
                out.writeUTF(cmd);
                out.flush();
            }
        }catch(IOException io){

        }finally {
            try {
                cliente.shutdownOutput();
                cliente.shutdownInput();
                cliente.close();
            }catch (IOException io){
                io.printStackTrace();
            }
        }
    }
}
