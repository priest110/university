import java.io.BufferedReader;
import java.io.PrintStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.Scanner;
import java.util.Collections;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;


public class AnonGWThreadAux implements Runnable{
    private Socket cliente;
    private BufferedReader inCli;
    private PrintStream outCli;
    private DatagramSocket anon;
    private String anonIp;

    /**
     * Método construtor
     * @param c   O Cliente que se ligou
     * @param a   Endereco IP do Anon(servidor) com o qual comunicar
     * @throws Exception
     */
    public AnonGWThreadAux(Socket c, String a) throws Exception{
        this.cliente = c;
        this.inCli = new BufferedReader(new InputStreamReader(this.cliente.getInputStream()));
        this.outCli = new PrintStream(this.cliente.getOutputStream());
        this.anon = new DatagramSocket();
        this.anonIp = a;
    }

    /**
     * Run da thread
     */
    public void run(){
        try {
                //criacao do InetAdress do Anon(servidor) com o qual comunicar
                InetAddress anonAddr = InetAddress.getByName(this.anonIp);

                String aux;
                byte buf[];
                Pdu pdu;
                DatagramPacket packet;

                while(true){
                    //rececao do comando a executar
                    aux = this.inCli.readLine();

                    System.out.println("Mensagem recebida do Cliente " + this.cliente.getInetAddress().getHostName() + ": " + aux);
                    
                    //criacao da mensagem a enviar por UDP recorrendo à classe Pdu
                    pdu = new Pdu(aux);
                    //obtencao da mensagem, em bytes
                    buf = pdu.getPduBytes();
                    //criacao do packet a ser enviado
                    packet = new DatagramPacket(buf, buf.length, anonAddr, 6666);
                    //conexao ao Anon(servidor)
                    this.anon.connect(anonAddr, 6666);
                    //envio do packet
                    this.anon.send(packet);

                    /*Fim da Thread caso receba a mensagem de fim de sessao do Cliente
                      (e na mesma enviado ao Anon(servidor) - que enviara aos restantes componentes -
                       de forma a que estes saibam que o Cliente terminou sessao)*/
                    if(aux.equals("exit")){
                        System.out.println("Conexão Encerrada.");
                        break;
                    }

                    System.out.println("Aguardando a resposta do Anon(servidor).");

                    boolean run = true;
                    int pos;
                    //estrutura que ira guardar as mensagens recebidas e a sua posicao(identificador)
                    Map<Integer, String> blocks = new HashMap<>();
                    /*lista que ira guardar todos os identificadores,
                      por forma a poder enviar as mensagens de forma ordenada ao Cliente*/
                    List<Integer> sorted = new ArrayList<>();

                    while(run){
                        buf = new byte[1024];
                        packet = new DatagramPacket(buf, buf.length);
                        //rececao da resposta do Anon(servidor)
                        this.anon.receive(packet);

                        /*//encriptacao da informacao
                        buf = pdu.desencriptacao(packet.getData());
                        //conversao da mensagem recebida de bytes para uma mensagem do tipo Pdu
                        pdu = new Pdu(buf, buf.length);*/

                        //conversao da mensagem recebida de bytes para uma mensagem do tipo Pdu
                        pdu = new Pdu(packet.getData(), packet.getLength());
                        //obtencao da posicao do bloco recebido
                        pos = pdu.posBlock();
                        //obtencao da informacao guardada na mensagem
                        aux = pdu.getPduString();

                        //adicao do bloco recebido e do seu identificador as estruturas responsaveis
                        blocks.put(pos, aux);
                        sorted.add(pos);

                        /*terminar a rececao de mensagens caso tenhamos recebido
                          a totalidade da informação do Anon(servidor)*/
                        if(aux.equals("\n#!-FIM_DA_LEITURA-?%\n"))
                            run = false;
                    }

                    //ordenacao dos identificadores recebidos
                    Collections.sort(sorted);
                    //envio ordenado das mensagens recebidas
                    for(Integer i : sorted)
                        this.outCli.print(blocks.get(i)); //reencaminhamento da resposta para o Cliente

                    System.out.println("Resposta recebida!");

                    //fim da conexao ao Anon(servidor)
                    this.anon.disconnect();
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