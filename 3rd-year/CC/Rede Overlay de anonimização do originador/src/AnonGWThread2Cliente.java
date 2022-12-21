import java.util.concurrent.ThreadLocalRandom;
import java.net.Socket;
import java.net.ServerSocket;


public class AnonGWThread2Cliente implements Runnable{
    private String[] anons;

    /**
     * Método construtor
     * @param a   Todos os demais Anons existentes
     * @throws Exception
     */
    public AnonGWThread2Cliente(String[] a) throws Exception{
        this.anons = new String[a.length];
        int i = 0;
        for(String s : a)
            this.anons[i++] = s;
    }

    /**
     * Run da thread
     */
    public void run(){
        try {
            //socket que irá aceitar as ligacoes dos Clientes
            ServerSocket socket = new ServerSocket(80);

            while(true){
                //aceitacao das ligacoes dos Clientes
                Socket cliente = socket.accept();

                //selecao aleatoria do Anon(servidor) a ligar
                int rand = ThreadLocalRandom.current().nextInt(0, this.anons.length);

                //criacao da Thread responsavel por comunicar com o Anon(servidor)
                Thread t = new Thread(new AnonGWThreadAux(cliente, this.anons[rand]));
                t.start();
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}