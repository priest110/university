import java.net.Socket;
import java.io.BufferedReader;
import java.io.PrintStream;
import java.io.InputStreamReader;
import java.io.FileReader;


public class ServThread implements Runnable{
    private Socket cliente;
    private BufferedReader in;
    private PrintStream out;

    /**
     * Método construtor
     *
     * @param c   Socket do Anon
     * @throws Exception
     */
    public ServThread(Socket c) throws Exception{
        this.cliente = c;
        this.in = new BufferedReader(new InputStreamReader(this.cliente.getInputStream()));
        this.out = new PrintStream(this.cliente.getOutputStream());
    }

    /**
     * Run da thread
     */
    public void run(){

        try {
            String aux;
            while (true){
                //rececao da mensagem enviado pelo Anon(servidor)
                aux = this.in.readLine();

                System.out.println("Mensagem recebida do Anon(servidor) " + this.cliente.getInetAddress().getHostName() + ": " + aux);

                //Rececao da mensagem de fim de sessao do cliente
                if(aux.equals("exit"))
                    System.out.println("Conexão Encerrada.");

                else{
                    System.out.println("A enviar resposta para o Anon.");
                    BufferedReader inFile = null;

                    try{
                        //abertura do ficheiro pretendido
                        inFile = new BufferedReader(new FileReader(aux));
                    }
                    catch(Exception e){
                        e.printStackTrace();
                    }
                    
                    if(inFile != null){
                        while((aux = inFile.readLine()) != null){
                            //envio da informacao do ficheiro
                            this.out.println(aux);
                        }
                    }
                    else
                        this.out.println("Ficheiro Inválido.");

                    //envio da informacao de fim de leitura
                    this.out.println("#!-FIM_DA_LEITURA-?%");

                    System.out.println("Resposta enviada.");
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally {
            try {
                this.cliente.shutdownOutput();
                this.cliente.shutdownInput();
                this.cliente.close();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}