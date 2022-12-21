import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class AnonGWThread implements Runnable{
    private Socket cliente;
    private Socket anon;

    protected DataInputStream in;
    protected DataOutputStream out;
    protected DataInputStream in_2;
    protected DataOutputStream out_2;


    /**
     * Método construtor
     *
     * @param c         socket do cliente
     * @throws IOException
     */
    public AnonGWThread(Socket c) throws IOException {
        this.cliente = c;
        this.in = new DataInputStream(cliente.getInputStream());
        this.out = new DataOutputStream(cliente.getOutputStream());
        this.anon = new Socket("127.0.0.1", 12345);
        this.out_2 = new DataOutputStream(anon.getOutputStream());
        this.in_2 = new DataInputStream(anon.getInputStream());
    }

    public void disconnect() throws IOException{
        this.out_2.close();
        this.in_2.close();
        this.anon.close();
    }

    /**
     * Run da thread
     */
    public void run(){
        try {
            String cmd;
            while ((cmd = in.readUTF()) != null) {
                System.out.println("Li do socket: " + cmd);
                out_2.writeUTF(cmd);
                out_2.flush();
            }
        }catch(IOException io){

        }finally {
            try {
                disconnect();
                cliente.shutdownOutput();
                cliente.shutdownInput();
                cliente.close();
            }catch (IOException io){
                io.printStackTrace();
            }
        }
    }
}
