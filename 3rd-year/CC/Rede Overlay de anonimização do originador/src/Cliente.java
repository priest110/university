import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
import java.io.BufferedReader;
import java.io.PrintStream;
import java.io.InputStreamReader;


public class Cliente {

    /**
     * Main da classe Cliente
     * @param args   todos os Anons aos quais o Cliente se pode conectar
     */
    public static void main(String[] args){

    	if(args.length < 1){
    		System.out.println("Argumentos Insuficientes: Cliente <Anons (1 ou mais)>\n");
    		return;
    	}

        try {
            //selecao aleatoria do Anon(cliente) a ligar
        	int rand = ThreadLocalRandom.current().nextInt(0, args.length);

            //conexao ao Anon(cliente) escolhido e criacao dos canais de leitura e escrita
        	Socket socket = new Socket(args[rand], 80); 

        	BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    		PrintStream out = new PrintStream(socket.getOutputStream());

    		Scanner scan = new Scanner(System.in);

        	while (true) {
                //rececao do comando enviado pelo Cliente
        		System.out.print("Comando a executar: ");
        		String aux = scan.nextLine();

                //envio do comando ao Anon(cliente)
        		out.println(aux);

        		/*fechar o Cliente caso receba o comando de fim de sessao
                  (e na mesma enviado ao Anon(cliente) - que enviara aos restantes componentes -
                   de forma a que estes saibam que o Cliente terminou sessao)*/
                if(aux.equals("exit")){
                    System.out.println("Conexão Encerrada.");
        			break;
                }

                boolean run = true;
                while(run){
                    //rececao da resposta por parte do Anon(cliente)
                	aux = in.readLine();

                    //caso seja o fim da mensagem, termina a espera por linhas
                    if(aux.equals("#!-FIM_DA_LEITURA-?%"))
                        run = false;
                    else
                        System.out.println(aux);
                }
        	}
        	
            socket.shutdownOutput();
            socket.shutdownInput();
        	socket.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}