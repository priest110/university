import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
import java.io.BufferedReader;
import java.io.PrintStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;


public class AnonGWThread2Servidor implements Runnable{
    private Socket servidor;
    private BufferedReader inServ;
    private PrintStream outServ;
    private DatagramSocket anon;

    /**
     * Método construtor
     *
     * @param s     Endereco IP do servidor
     * @param port  Porta do servidor
     * @throws Exception
     */
    public AnonGWThread2Servidor(String s, int port) throws Exception{
        this.servidor = new Socket(s, port);
        this.inServ = new BufferedReader(new InputStreamReader(this.servidor.getInputStream()));
        this.outServ = new PrintStream(this.servidor.getOutputStream());
        this.anon = new DatagramSocket(6666);
    }

    /**
     * Run da thread
     */
    public void run(){
        try {

            while(true){
                
                String aux;
                byte buf[];
                Pdu pdu;
                DatagramPacket packet;

                while(true){
                    buf = new byte[1024];
                    packet = new DatagramPacket(buf, buf.length);
                    //rececao do packet com o comando
                    this.anon.receive(packet);
                    
                    //criacao do InetAdress do Anon(cliente) com o qual comunicar
                    InetAddress anonAddr = packet.getAddress();
                    //obtencao da porta através da qual a mensagem foi enviada
                    int port = packet.getPort();

                    //conversao da mensagem recebida de bytes para uma mensagem do tipo Pdu
                    pdu = new Pdu(packet.getData(), packet.getLength());
                    //obtencao da informacao guardada na mensagem
                    aux = pdu.getPduString();

                    System.out.println("Mensagem recebida do Anon(cliente) " + anonAddr.getHostName() + ": " +  aux);

                    //reencaminhamento do comando para o servidor
                    this.outServ.println(aux);

                    /*Fim do ciclo caso receba a mensagem de fim de sessao do Cliente
                      (e na mesma enviado ao Servidor de forma a que estes saiba que o Cliente terminou sessao)*/
                    if(aux.equals("exit")){
                        System.out.println("Conexão Encerrada.");
                        break;
                    }

                    System.out.println("Aguardando a resposta do Servidor.");

                    int qAntes = 0;
                    while((aux = this.inServ.readLine()) != null){//rececao da resposta do servidor

                        /*terminar a rececao de mensagens caso tenhamos recebido
                          a totalidade da informação do Anon(servidor)*/
                        if(aux.equals("#!-FIM_DA_LEITURA-?%"))
                            break;

                        //adicao de um '\n' por forma a indicar que se trata de uma linha
                        aux = aux + "\n";
                        
                        //criacao da mensagem a enviar por UDP recorrendo à classe Pdu
                        pdu = new Pdu(aux);

                        /*separacao da mensagem recebida em diversos blocos,
                          por forma a que estes sejam enviados como mensagens individuais
                          (e adicionada uma posicao relativa a cada mensagem,
                          por forma a posteriormente ser possivel ordenar os blocos*/
                        Map<Integer, byte[]> blocks = pdu.getByteBlocks(qAntes);
                        for(byte[] b : blocks.values()){
                            /*//encriptacao da informacao
                            buf = pdu.encriptacao(b);
                            //empacotamento da mensagem
                            packet = new DatagramPacket(buf, buf.length, anonAddr, port);*/

                        	//empacotamento da mensagem
                            packet = new DatagramPacket(b, b.length, anonAddr, port);

                            //reencaminhamento para o Anon(cliente)
                            this.anon.send(packet);
                            qAntes++;
                        }
                    }

                    //criacao da mensagem que indica fim de leitura
                    aux = qAntes + "," + "\n#!-FIM_DA_LEITURA-?%\n";
                    pdu = new Pdu(aux);
                    buf = pdu.getPduBytes();
                    //empacotamento da mensagem
                    packet = new DatagramPacket(buf, buf.length, anonAddr, port);
                    //reencaminhamento para o Anon(cliente)
                    this.anon.send(packet);

                    System.out.println("Resposta recebida!");
                }

            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        
        finally {
            try {
                this.servidor.shutdownOutput();
                this.servidor.shutdownInput();
                this.servidor.close();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}