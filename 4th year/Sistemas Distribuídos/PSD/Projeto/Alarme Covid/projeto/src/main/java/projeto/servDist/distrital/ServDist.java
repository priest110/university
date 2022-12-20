package projeto.servDist.distrital;

import org.apache.http.entity.ContentType;
import org.zeromq.ZMQ;
import java.io.IOException;
import java.util.*;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class ServDist implements Runnable {
    private int id;
    private ZMQ.Socket socket;
    private Publisher pub;
    private Historico[][] historicos;
    private List<String> utilizadores;
    private List<String> infetados;
    private List<String> possiveis_infetados;

    public ServDist(int id, int port, Publisher pub){
        this.id = id;
        this.pub = pub;
        int i, j;
        this.historicos = new Historico[5][5];
        for(i = 0; i < 5; i++) {
            for (j = 0; j < 5; j++) {
                this.historicos[i][j] = new Historico();
            }
        }
        this.utilizadores = new ArrayList<>();
        this.infetados = new ArrayList<>();
        this.possiveis_infetados = new ArrayList<>();
        ZMQ.Context context = ZMQ.context(1);
        this.socket = context.socket(ZMQ.REP);
        this.socket.bind("tcp://localhost:" + port);

        System.out.println("Servidor distrital " + this.id + " a correr!");
    }

    @Override
    public void run() {
        while(true){
            byte[] msgRec = this.socket.recv(); /* Recebe pedido do frontend */
            System.out.println("Servidor Dist " + this.id + " recebeu um pedido");
            String str = new String(msgRec);
            String[] infos = str.split("\\s+");
            int coordX = Integer.parseInt(infos[1]);
            int coordY = Integer.parseInt(infos[2]);
            String user = infos[3];

            switch (infos[0]) {
                case "\\posicao": /* Após fazer login, a posição do utilizador é adicionada ao histórico */
                    historicos[coordX][coordY].adiciona_user(user);
                    synchronized (this.pub) {
                        String notificacao;
                        notificacao = "aumento:" + infos[4] + ":(" + coordX + "," + coordY + ")";
                        this.pub.sendMesssage(notificacao);
                    }
                    if(!this.utilizadores.contains(user)) {
                        this.utilizadores.add(user);
                        int num = historicos[coordX][coordY].mais_pessoas();
                        String query = infos[4] + " " + coordX + " " + coordY + " " + num;
                        pedido_http("add/utilizador", query);
                    }
                    ArrayList<String> pessoas_contactadas = this.historicos[coordX][coordY].contactou_com(user,0);
                    str = String.join(", ", pessoas_contactadas) + "\n";
                    break;
                case "\\opcao":
                    String opcao = infos[4];
                    if (opcao.equals("1")) {
                        int total = historicos[coordX][coordY].total_pessoas();
                        str = "--- Total de pessoas: " + total + " ---\n";
                    }
                    else{
                        ArrayList<String> contactados = this.historicos[coordX][coordY].contactou_com(user,1);
                        int total = historicos[coordX][coordY].total_pessoas();
                        synchronized (this.pub){
                            String notificacao;
                            notificacao = "infetado:" + user;
                            this.pub.sendMesssage(notificacao);
                            notificacao = "infetado:" + infos[5] + ":(" + coordX + "," + coordY + ")";
                            this.pub.sendMesssage(notificacao);
                            notificacao = "ocorrencia:" + infos[5];
                            this.pub.sendMesssage(notificacao);
                            notificacao = "diminuicao:" + infos[5] + ":(" + coordX + "," + coordY + ")";
                            this.pub.sendMesssage(notificacao);
                            if(total == 0) {
                                notificacao = "saida:" + infos[5] + ":(" + coordX + "," + coordY + ")";
                                this.pub.sendMesssage(notificacao);
                            }
                        }
                        if(!this.infetados.contains(user)) {
                            this.infetados.add(user);
                            pedido_http("add/infetado",infos[5]);
                        }
                        for(String c : contactados){
                            if(!this.possiveis_infetados.contains((c))){
                                this.possiveis_infetados.add(user);
                                pedido_http("add/possivel_infetado",infos[5]);
                            }
                        }
                        str = "--- QUARENTENA ---\n";
                    }
                    break;
                case "\\logout": // Logout fo utilizador
                    historicos[coordX][coordY].remove_user(user);
                    System.out.println(historicos[coordX][coordY].toString());
                    int total = historicos[coordX][coordY].total_pessoas();
                    if(total == 0) {
                        synchronized (this.pub) {
                            String notificacao;
                            notificacao = "saida:" + infos[4] + ":(" + coordX + "," + coordY + ")";
                            this.pub.sendMesssage(notificacao);
                        }
                    }
                    synchronized (this.pub) {
                        String notificacao;
                        notificacao = "diminuicao:" + infos[4] + ":(" + coordX + "," + coordY + ")";
                        this.pub.sendMesssage(notificacao);
                    }
                    break;
                default:
                    System.out.println("Erro no infos[0]");
                    break;
            }
            this.socket.send(str); /* Envia resposta ao servidor frontend */
        }
    }

    /**
     * Pedido http ao diretório
     * @param uri do pedido
     * @param query do pedido
     */
    public void pedido_http(String uri, String query) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("http://localhost:8080/diretorio/" + uri);

        HttpEntity stringEntity = new StringEntity(query, ContentType.APPLICATION_JSON);
        httpPost.setEntity(stringEntity);
        try {
            httpclient.execute(httpPost);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     *  Main da classe ServDist.
     *  Cria a conexão com o servidor frontend.
     *  Publisher vai tratar das notificaçõs públicas para os clientes.
     * @param args
     */
    public static void main(String[] args) {
        /* Inicializa o Publisher */
        ZMQ.Context context = ZMQ.context(1);
        ZMQ.Socket socket = context.socket(ZMQ.PUB);
        socket.bind("tcp://*:6101");
        Publisher pub = new Publisher(context, socket);

        /* Criar os vários servidores distritais*/
        ServDist s1 = new ServDist(1, 5551, pub);
        ServDist s2 = new ServDist(2, 5552, pub);
        ServDist s3 = new ServDist(3, 5553, pub);
        ServDist s4 = new ServDist(4, 5554, pub);
        ServDist s5 = new ServDist(5, 5555, pub);
        ServDist s6 = new ServDist(6, 5556, pub);
        ServDist s7 = new ServDist(7, 5557, pub);
        ServDist s8 = new ServDist(8, 5558, pub);
        ServDist s9 = new ServDist(9, 5559, pub);
        ServDist s10 = new ServDist(10, 5560, pub);
        ServDist s11 = new ServDist(11, 5561, pub);
        ServDist s12 = new ServDist(12, 5562, pub);
        ServDist s13 = new ServDist(13, 5563, pub);
        ServDist s14 = new ServDist(14, 5564, pub);
        ServDist s15 = new ServDist(15, 5565, pub);
        ServDist s16 = new ServDist(16, 5566, pub);
        ServDist s17 = new ServDist(17, 5567, pub);
        ServDist s18 = new ServDist(18, 5568, pub);

        /* Inicializar as threads */
        Thread t1 = new Thread(s1);
        Thread t2 = new Thread(s2);
        Thread t3 = new Thread(s3);
        Thread t4 = new Thread(s4);
        Thread t5 = new Thread(s5);
        Thread t6 = new Thread(s6);
        Thread t7 = new Thread(s7);
        Thread t8 = new Thread(s8);
        Thread t9 = new Thread(s9);
        Thread t10 = new Thread(s10);
        Thread t11 = new Thread(s11);
        Thread t12 = new Thread(s12);
        Thread t13 = new Thread(s13);
        Thread t14 = new Thread(s14);
        Thread t15 = new Thread(s15);
        Thread t16 = new Thread(s16);
        Thread t17 = new Thread(s17);
        Thread t18 = new Thread(s18);

        /* Correr as threads */
        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
        t6.start();
        t7.start();
        t8.start();
        t9.start();
        t10.start();
        t11.start();
        t12.start();
        t13.start();
        t14.start();
        t15.start();
        t16.start();
        t17.start();
        t18.start();
    }
}
