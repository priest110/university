package projeto.servDist.cliente;

import projeto.servDist.distrital.Localizacao;
import org.zeromq.ZMQ;
import java.io.*;
import java.net.Socket;
import java.util.*;

public class Client {
    private ArrayList<Localizacao> possiveis_localizacoes;
    private ArrayList<String> distritos;
    private String utilizador;
    private String pass;
    private String distrito;
    private Localizacao posicao_atual;
    private BufferedReader in;
    private PrintWriter out;
    private BufferedReader reader;
    private ZMQ.Socket subscriber;
    private Map<String, Notificacao> notificacoesAtivas; /* Tipo de notificação, List<distrito_name> */

    public Client(String hostname, int port) throws IOException {
        this.possiveis_localizacoes = new ArrayList<>();
        int i, j;
        for(i = 0; i < 5; i++)
            for(j = 0; j < 5; j++)
                possiveis_localizacoes.add(new Localizacao(i,j));

        this.distritos = new ArrayList<>(Arrays.asList("Aveiro","Beja","Braga","Braganca","Castelo_Branco","Coimbra","Evora","Faro","Guarda","Leiria","Lisboa","Portalegre","Porto","Santarem","Setubal","Viana_do_Castelo","Vila_Real","Viseu"));

        System.out.println("> A conectar ao servidor frontend...");
        Socket socket = new Socket(hostname, port);
        System.out.println("> Conexão aceite!");

        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new PrintWriter(socket.getOutputStream());
        this.reader = new BufferedReader(new InputStreamReader(System.in));

        this.notificacoesAtivas = new HashMap<>();
    }

    /**
     * Menu de login
     * @throws IOException excepcao
     */
    public void login() throws IOException{
        System.out.print("Utilizador: ");
        this.utilizador = this.reader.readLine();
        System.out.print("Pass: ");
        this.pass = this.reader.readLine();

        this.out.println("\\login " + this.utilizador + " " + this.pass);
        this.out.flush();

        String resposta = this.in.readLine(); /* resposta == distrito */
        try {
            int dist = Integer.parseInt(resposta);
            this.distrito = this.distritos.get(dist-1);
            login_sucesso();
        }catch (NumberFormatException e){
            System.out.println(resposta);
            autentica();
        }
    }

    /**
     * Handler do login, caso seja efetuado com sucesso
     * Começa por gerar uma localização aleatória do cliente e comunica-a ao frontend, que após contactat o servidor distrital, devolve a lista de todos os utilizadores que já contactou
     * @throws IOException excepcao
     */
    public void login_sucesso() throws IOException{
        int randomIndex = 1;
        //int randomIndex = (new Random()).nextInt(this.possiveis_localizacoes.size());
        this.posicao_atual = this.possiveis_localizacoes.get(randomIndex);
        this.out.println("\\posicao " + this.posicao_atual.getX() + " " + this.posicao_atual.getY() + " " + this.utilizador + " " + this.distrito);
        this.out.flush();
        String resposta = this.in.readLine();

        String[] contactou_com = null;
        if(!resposta.isEmpty())
            contactou_com = resposta.split(",");

        /* Inicializa o Subscriber e apresenta o menu do cliente */
        int ativo = 1;
        ZMQ.Context context = ZMQ.context(1);
        this.subscriber = context.socket(ZMQ.SUB);
        Subscriber sub = new Subscriber(context, this.subscriber, ativo, contactou_com, this.posicao_atual, this.distritos, this.distrito);
        Thread t = new Thread(sub);
        t.start();

        menu();

        ativo = 0;
        this.subscriber.close();
        System.exit(0);
    }

    /**
     * Menu de registo do utilizador
     * @throws IOException excepcao
     */
    public void registo() throws IOException{
        System.out.print("Utilizador: ");
        this.utilizador = this.reader.readLine();
        System.out.print("Pass: ");
        this.pass = this.reader.readLine();
        System.out.println("--- Insere agora o distrito a qual pertences ---");
        System.out.println(" Aveiro(1) Beja(2) Braga(3) Bragança(4) Castelo Branco(5)\n Coimbra(6) Évora(7) Faro(8) Guarda(9) Leiria(10)\n Lisboa(11) Portalegre(12) Porto(13) Santarém(14) Setúbal(15)\n Viana do Castelo(16) Vila Real(17) Viseu(18)");
        System.out.print("Distrito: ");
        String dist = this.reader.readLine();

        int dist_aux;
        try {
            dist_aux = Integer.parseInt(dist);
            if(dist_aux > 0 && dist_aux < 19){
                this.distrito = dist;
                this.out.println("\\register " + this.utilizador + " " + this.pass + " " + this.distrito);
                this.out.flush();

                String resposta = this.in.readLine();
                System.out.println(resposta);
            }
            else
                System.out.println("--- Tem de inserir valores entre 1 e 18, inclusive ---");
        }catch (NumberFormatException e){
            System.out.println("--- Tem de inserir valores entre 1 e 18, inclusive ---");
        }
        autentica();
    }

    /**
     * Menu do utilizador, após login com sucesso
     * @throws IOException excepcao
     */
    public void menu() throws IOException {
        boolean logado = true;
        while (logado) {
            System.out.println("\n----------- ALARMECOVID -----------\n");
            System.out.println("1-Obter o número de pessoas numa dada localização do distrito");
            System.out.println("2-Comunicar que se encontra doente");
            System.out.println("3-Pretendo registar/cancelar o interesse em receber notificações");
            System.out.println("4-Logout");
            System.out.print("Escolha uma das opções: ");
            String opcao = this.reader.readLine();

            switch (opcao) {
                case "1": {
                    Localizacao l = escolhePosicao();
                    this.out.println("\\opcao " + l.getX() + " " + l.getY() + " " + this.utilizador + " " + opcao + " " + this.distrito);
                    this.out.flush();

                    String resposta = this.in.readLine();
                    System.out.println(resposta);
                    break;
                }
                case "2": {
                    logado = false;
                    this.subscriber.unsubscribe("infetado:" + this.distrito + ":(" + this.posicao_atual.getX() + "," + this.posicao_atual.getY() + ")");
                    this.out.println("\\opcao " + this.posicao_atual.getX() + " " + this.posicao_atual.getY() + " " + this.utilizador + " " + opcao + " " + this.distrito);
                    this.out.flush();

                    String resposta = this.in.readLine();
                    System.out.println(resposta);
                    break;
                }
                case "3":
                    notification_menu();
                    break;
                case "4": {
                    this.out.println("\\logout " + this.posicao_atual.getX() + " " + this.posicao_atual.getY() + " " + this.utilizador + " " + this.distrito);
                    this.out.flush();

                    String resposta = this.in.readLine();
                    System.out.println("See ya");
                    logado = false;
                    break;
                }
                default:
                    System.out.println("--- Opção errada! ---");
                    break;
            }
        }
    }

    /**
     * Menu de autenticação do utilizador
     * @throws IOException excepcao
     */
    public void autentica() throws IOException {
        System.out.println("\n----------- ALARMECOVID -----------\n");
        System.out.println("1-Login");
        System.out.println("2-Registo");
        System.out.print("Escolha uma das opções: ");
        String opcao = this.reader.readLine();

        if(opcao.equals("1"))
            login();
        else if(opcao.equals("2"))
            registo();
        else{
            System.out.println("--- Opção errada!");
            autentica();
        }
    }

    /**
     * Menu de notificações
     * @throws IOException excepcao
     */
    public void notification_menu() throws IOException{
        boolean opcao_valida = true, ocorrencia = false;
        String opcao, acao = null;

        while(opcao_valida) {
            opcao_valida = false;
            System.out.println("\n----------- ALARMECOVID -----------\n");
            System.out.println("1-Notificar quando deixar de haver pessoas numa dada localização de um dado distrito");
            System.out.println("2-Notificar sobre o aumento da concentração de pessoas numa dada localização de um dado distrito");
            System.out.println("3-Notificar acerca da diminuição da concentração de pessoas numa dada localização de um dado distrito");
            System.out.println("4-Notificar em relação à ocorrência de mais um infetado num dado distrito");
            System.out.print("Escolha uma das opções: ");
            opcao = this.reader.readLine();
            switch (opcao) {
                case "1":
                    acao = "saida";
                    break;
                case "2":
                    acao = "aumento";
                    break;
                case "3":
                    acao = "diminuicao";
                    break;
                case "4":
                    acao = "ocorrencia";
                    ocorrencia = true;
                    break;
                default:
                    opcao_valida = true;
                    System.out.println("--- Opção errada ---");
                    break;
            }
        } opcao_valida = true;
        while (opcao_valida) {
            opcao_valida = false;
            System.out.println("1-Ativar notificações\n2-Desativar notificações");
            opcao = this.reader.readLine();
            boolean aux = true;

            if (opcao.equals("1")) {
                ArrayList<String> distritos = escolheDistritos();
                Localizacao l;
                if(ocorrencia)
                    l = null;
                else l = escolhePosicao();
                Notificacao n = new Notificacao(distritos, l);
                this.notificacoesAtivas.put(acao, n);
                for (String distrito : n.getDistritos())
                    aux = aux && ativarNotificacoes(acao, distrito, l);
                if(aux)
                    System.out.println("--- Notificações ativadas ---");
            } else if (opcao.equals("2")) {
                Notificacao n = this.notificacoesAtivas.get(acao);
                for (String distrito : n.getDistritos())
                    aux = aux && desativarNotificacoes(acao, distrito, n.getLocalizacao());
                if(aux)
                    System.out.println("--- Notificações desativadas ---");
            } else {
                System.out.println("--- Opcão errada! ---");
                opcao_valida = true;
            }

        }
    }

    /**
     * Escolhe 3 distritos da lista de distritos para serem ativadas notificações
     * @return  lista com os 3 distritos
     */
    public ArrayList<String> escolheDistritos() throws IOException {
        while(true) {
            System.out.println("--- Inserir 3 distritos dos quais pretendes receber notificações ---");
            System.out.println(" Aveiro(1) Beja(2) Braga(3) Bragança(4) Castelo Branco(5)\n Coimbra(6) Évora(7) Faro(8) Guarda(9) Leiria(10)\n Lisboa(11) Portalegre(12) Porto(13) Santarém(14) Setúbal(15)\n Viana do Castelo(16) Vila Real(17) Viseu(18)\n");
            System.out.print("1º Distrito: ");
            String dist = this.reader.readLine();
            System.out.print("2º Distrito: ");
            String dist2 = this.reader.readLine();
            System.out.print("3º Distrito: ");
            String dist3 = this.reader.readLine();

            try {
                int d = Integer.parseInt(dist);
                int d1 = Integer.parseInt(dist2);
                int d2 = Integer.parseInt(dist3);
                if (d > 0 && d < 19 && d1 > 0 && d1 < 19 && d2 > 0 && d2 < 19 && d != d1 && d1 != d2 && d != d2)
                    return new ArrayList<>(Arrays.asList(this.distritos.get(d-1), this.distritos.get(d1-1), this.distritos.get(d2-1)));
            } catch (NumberFormatException e) {
                System.out.println("--- Valores inválidos ---");
            }
        }
    }

    /**
     * Escolhe uma posição
     * @return posicao
     * @throws IOException excepcao
     */
    public Localizacao escolhePosicao() throws IOException{
        while (true) {
            System.out.println("\n--- Inserir localização ---");
            System.out.println("- São apenas aceites valores entre 0 e 4 para as duas coordenadas da localização -");
            System.out.print("Coordenada X: ");
            String x = this.reader.readLine();
            System.out.print("Coordenada Y: ");
            String y = this.reader.readLine();
            try {
                int d = Integer.parseInt(x);
                int d1 = Integer.parseInt(y);
                if (d >= 0 && d < 5 && d1 >= 0 && d1 < 5)
                    return new Localizacao(d, d1);
            } catch (NumberFormatException e) {
                System.out.println("--- Valores inválidos ---");
            }
        }
    }

    /**
     * Ativa notificações relativamente a determinado tópico
     * @param acao do tópidoc
     * @param distrito do tópico
     * @param l do tópico
     * @return true se for bem sucedida
     */
    public boolean ativarNotificacoes (String acao, String distrito, Localizacao l) {
        if(!acao.equals("ocorrencia"))
            return this.subscriber.subscribe(acao + ":" + distrito + ":(" + l.getX() + "," + l.getY() + ")");
        else return this.subscriber.subscribe(acao + ":" + distrito);
    }

    /**
     * Desativa notificações relativamente a determinado tópico
     * @param acao do tópidoc
     * @param distrito do tópico
     * @param l do tópico
     * @return true se for bem sucedida
     */
    public boolean desativarNotificacoes (String acao, String distrito, Localizacao l) {
        if(!acao.equals("ocorrencia"))
            return this.subscriber.unsubscribe(acao + ":" + distrito + ":(" + l.getX() + "," + l.getY() + ")");
        else
            return this.subscriber.unsubscribe(acao + ":" + distrito);
    }

    /**
     * Main do Cliente
     * @param args da main
     * @throws IOException excepcao
     */
    public static void main (String[] args) throws IOException {
        Client c = new Client("localhost", 3000);
        c.autentica();
    }
}
