package projeto.servDist.cliente;

import org.zeromq.ZMQ;
import projeto.servDist.distrital.Localizacao;
import java.util.ArrayList;

public class Subscriber implements Runnable {
    public ZMQ.Context context;
    public ZMQ.Socket socket;
    public int ativo;
    public String[] pessoas_contactadas;
    public Localizacao posicao_atual;
    public ArrayList<String> distritos;
    public String distrito;

    public Subscriber(ZMQ.Context c, ZMQ.Socket s, int ativo, String[] pessoas_contactadas, Localizacao posicao_atual, ArrayList<String> distritos, String distrito){
        this.context = c;
        this.socket = s;
        this.ativo = ativo;
        this.pessoas_contactadas = pessoas_contactadas;
        this.posicao_atual = posicao_atual;
        this.distritos = distritos;
        this.distrito = distrito;
    }

    /**
     * Subscriber subscreve as pessoas com que já contactou(caso estas comuniquem que estejam doentes)
     * Subscreve também a posição atual, caso entre alguém nela depois deste momento(e comunique que esteja doente)
     */
    @Override
    public void run() {
        this.socket.connect("tcp://localhost:6102"); /* para ligar-se à conexão respetiva no servidor front-end */

        if(pessoas_contactadas != null) {
            for (String p : this.pessoas_contactadas)
                this.socket.subscribe("infetado:" + p);
        }
        this.socket.subscribe("infetado:" + this.distrito + ":(" + this.posicao_atual.getX() + "," + this.posicao_atual.getY() + ")");

        try {
            while (this.ativo == 1) {
                byte[] msgRec = this.socket.recv();
                String msg = new String(msgRec);
                String[] infos = msg.split(":");
                /* Tratar da msg */
                switch (infos[0]) {
                    case "infetado":
                        System.out.println("\n--- NOTIFICAÇÃO ----");
                        System.out.println("Esteve em contacto com um utilizador infetado.");
                        break;
                    case "saida":
                        System.out.println("\n--- NOTIFICAÇÃO ----");
                        System.out.println("A localização " + infos[2] + " do distrito " + infos[1] + " acabou de ficar sem pessoas.");
                        break;
                    case "ocorrencia":
                        System.out.println("\n--- NOTIFICAÇÃO ----");
                        System.out.println("O distrito " + infos[1] + " acabou de ter mais um infetado.");
                        break;
                    case "diminuicao":
                        System.out.println("\n--- NOTIFICAÇÃO ----");
                        System.out.println("A localização " + infos[2] + " do distrito " + infos[1] + " sofreu uma diminuição de concentração de pessoas");
                        break;
                    default:
                        System.out.println("\n--- NOTIFICAÇÃO ----");
                        System.out.println("A localização " + infos[2] + " do distrito " + infos[1] + " sofreu um aumento de concentração de pessoas");
                        break;
                }
            }
        }
        catch (Exception e){
            System.out.println("Erro");
        } finally {
            this.context.term();
        }
    }
}
