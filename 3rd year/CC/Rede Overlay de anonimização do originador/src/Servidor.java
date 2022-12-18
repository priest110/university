import java.net.Socket;
import java.net.ServerSocket;


public class Servidor {
    /**
     * Main da classe Servidor
     * @param args Porta que deve escutar
     */
    public static void main(String[] args){

        if(args.length != 1){
            System.out.println("Argumentos Inválidos: Servidor <Port>\n");
            return;
        }

        try{
            //socket que irá aceitar as ligacoes dos Anons(servidor)
            ServerSocket servidor = new ServerSocket(Integer.parseInt(args[0]));

            while(true){
                //aceitacao das ligacoes dos Anons(servidor)
                Socket cliente = servidor.accept();

                System.out.println("Ligou-se um cliente ao servidor.");

                //criacao da Thread responsavel por processar os comandos enviados pelo Anon(servidor)
                Thread c = new Thread(new ServThread(cliente));
                c.start();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}