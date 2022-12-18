import java.net.Socket;


public class AnonGW {

    /**
     * Main da classe AnonGW
     * @param args   Servidor, Porta de Conexao e os restantes Anons ativos
     */
    public static void main(String[] args){
        if(args.length < 3){
            System.out.println("Argumentos Insuficientes: AnonGW <Servidor> <Port> <Anons (1 ou mais)>\n");
            return;
        }

        try{
            //guardar o registo de quais os Anons existentes na rede
            String[] aux = new String[args.length-2];
            for(int i = 0; i < args.length-2; i++)
                aux[i] = args[i+2];

            //criacao da Thread responsavel por aceitar ligacoes de Clientes
            Thread tc = new Thread(new AnonGWThread2Cliente(aux));
            tc.start();

            //criacao da Thread responsavel pela ligacao ao Servidor
            Thread ts = new Thread(new AnonGWThread2Servidor(args[0], Integer.parseInt(args[1])));
            ts.start();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}