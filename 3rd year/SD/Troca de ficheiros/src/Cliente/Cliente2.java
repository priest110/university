package Cliente;

import Comum.*;

import java.io.*;
import java.net.ConnectException;
import java.net.Socket;

public class Cliente2 {
    protected final Socket socket_cliente;
    protected DataInputStream in;
    protected DataOutputStream out;


    final int OK = 100;
    final int ERR_DadosInvalidos = 101;
    final int ERR_UtilizadorExisttente = 102;
    final int ERR_UtilizadorJaLogado = 103;
    final int ERR_SemMusicasComEstaEtiqueta = 104;
    final int ERR_IDInexistente = 105;
    final int DEAD = 200;
    final int MAXSIZE = 200000;

    /**
     * Método construtor
     * @param host      do socket do cliente
     * @param port      do socket do cliente
     * @throws IOException
     */
    public Cliente2(String host, int port) throws IOException{
        this.socket_cliente = new Socket(host, port);
        this.out = new DataOutputStream(socket_cliente.getOutputStream());
        this.in = new DataInputStream(socket_cliente.getInputStream());
    }

    /**
     * Disconecta o cliente do servidor
     * @throws IOException
     */
    public void disconnect() throws IOException{
        this.out.close();
        this.in.close();
        this.socket_cliente.close();
    }

    /**
     * Passa a informação do cliente para o servidor do login que está a ser feito
     * @param user      do Utilizador
     * @param pass      do Utilizador
     * @throws DadosInvalidosException
     * @throws UtilizadorJaLogado
     * @throws IOException
     */
    public void login(String user, String pass) throws DadosInvalidosException, UtilizadorJaLogado, IOException{
        this.out.writeUTF("1-" + user + "-" + pass);
        this.out.flush();

        int x = new Integer(this.in.readUTF());
        if(x == ERR_DadosInvalidos)
            throw new DadosInvalidosException();
        else if (x == ERR_UtilizadorJaLogado)
            throw new UtilizadorJaLogado();
    }

    /**
     * Passa a informação do cliente para o servidor do registo que está a ser feito
     * @return      true, caso esteja
     * @throws IOException
     */
    public boolean isLoggedin() throws IOException{
        this.out.writeUTF("2");
        this.out.flush();

        int x = new Integer(this.in.readUTF());
        return (x == OK);
    }

    /**
     * Passa a informação do cliente para o servidor do registo que está a ser feito
     * @param user      do Utilizador
     * @param pass      do Utilizador
     * @throws UsernameExistenteException
     * @throws IOException
     */
    public void registar(String user, String pass) throws UsernameExistenteException, IOException {
        this.out.writeUTF("3-" + user + "-" + pass);
        this.out.flush();

        int x = new Integer(this.in.readUTF());
        if(x == ERR_UtilizadorExisttente)
            throw new UsernameExistenteException();
        System.out.println(user + " o teu registo foi efetuado com sucesso!");
    }

    /**
     * Passa a informação do cliente para o  servidor da publicação de uma música
     * @param conteudo      da música
     * @param titulo        da música
     * @param interprete    da música
     * @param ano           da música
     * @param etiquetas     da música
     * @throws IOException
     * @throws FileInexistenteException
     */
    public void publicarMusica(String conteudo, String titulo, String interprete, int ano, String etiquetas) throws FileInexistenteException, IOException {
        File file = new File(conteudo);
        if(!file.exists())
            throw new FileInexistenteException();

        this.out.writeUTF("4-" + titulo + "-" + interprete + "-" + ano + "-" + etiquetas);
        this.out.flush();

        // Adicionar o conteúdo à aplicação
        FileInputStream fis = new FileInputStream(file);
        byte[] buf = new byte[MAXSIZE];
        int readNum;
        int tam_ficheiro = (int)file.length();
        this.out.writeUTF(Integer.toString(tam_ficheiro));
        this.out.flush();
        while (tam_ficheiro > 0) {
            readNum = fis.read(buf, 0, buf.length);
            tam_ficheiro -= readNum;
            this.out.write(buf, 0, readNum); //no doubt here is 0
            this.out.flush();
        }
        fis.close();

        String linha = this.in.readUTF();
        System.out.println("ID: " + linha);
    }

    /**
     * Passa a informação do cliente para o  servidor da procura de músicas através de uma etiqueta
     * @param etiqueta      da música
     * @throws      IOException
     * @throws EtiquetaInvalidaException
     */
    public void musicaByEtiqueta(String etiqueta) throws IOException, EtiquetaInvalidaException {
        this.out.writeUTF("5-" + etiqueta);
        this.out.flush();

        int x = new  Integer(this.in.readUTF());
        if(x == ERR_SemMusicasComEstaEtiqueta)
            throw new EtiquetaInvalidaException();

        StringBuilder sb = new StringBuilder();
        String linha;
        while (!(linha = this.in.readUTF()).equals("---"))
            sb.append(linha).append('\n');
        System.out.println(sb.toString());
    }

    /**
     * Passa a informação do cliente para o servidor do download de uma música
     * @param   id      do cliente
     * @throws      IOException
     * @throws      IDInvalidoException
     */
    public void downloadMusica(int id) throws IOException, IDInvalidoException {
        this.out.writeUTF("6-" + id);
        this.out.flush();

        File file = new File(""+id);
        OutputStream down_file = new FileOutputStream(file);
        int nRead;
        String size = this.in.readUTF();
        int size_int = Integer.parseInt(size);

        byte[] data = new byte[MAXSIZE];
        while (size_int > 0 && (nRead = this.in.read(data, 0, (int)Math.min(data.length, size_int))) != -1) {
            down_file.write(data,0,nRead);
            size_int -= nRead;
        }
        down_file.close();

        int x = new  Integer(this.in.readUTF());
        if(x == ERR_IDInexistente)
            throw new IDInvalidoException();
    }


    /**
     * Autentica um utilizador, através de login ou registo.
     * @throws IOException
     */
    public void autentica() throws IOException{
        System.out.println();
        System.out.println("---- Charles Mansion Music ----");
        System.out.println("Escolha a opção que pretende:");
        System.out.println("1 - Login");
        System.out.println("2 - Registar");
        System.out.println("0 - Sair");
        String user, pass;
        BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
        switch (Input.lerInt()){
            case 1:
                System.out.print("Username: ");
                user = Input.lerString();
                System.out.print("Password: ");
                pass = Input.lerString();
                try {
                    this.login(user, pass);
                } catch (DadosInvalidosException | UtilizadorJaLogado ex) {
                    System.out.println(ex.getMessage());
                }
                break;
            case 2:
                System.out.print("Username: ");
                user = Input.lerString();
                System.out.print("Password: ");
                pass = Input.lerString();;
                try {
                    this.registar(user, pass);
                } catch (UsernameExistenteException ex) {
                    System.out.println(ex.getMessage());
                }
                break;
            case 0:
                System.out.println("Adeus!");
                this.disconnect();
                throw new IOException();
            default:
                System.out.print("Opção Inválida!");
                break;
        }
    }

    /**
     * Menu de opções da plataforma
     * @throws IOException
     */
    public void menu() throws IOException{
        boolean sair = false;
        while(!sair){
            System.out.println();
            System.out.println("---- Charles Mansion Music ----");
            System.out.println("Escolha a opção que pretende:");
            System.out.println("1 - Publicar música");
            System.out.println("2 - Procurar música");
            System.out.println("3 - Descarregar música");
            System.out.println("0 - Sair");
            String conteudo, titulo, interprete, etiquetas, etiqueta;
            int ano, id;
            switch (Input.lerInt()){
                case 1:
                    System.out.print("Conteúdo: ");
                    conteudo = Input.lerString();
                    System.out.print("Título: ");
                    titulo = Input.lerString();
                    System.out.print("Intérprete: ");
                    interprete = Input.lerString();
                    System.out.print("Ano: ");
                    ano = Input.lerInt();
                    System.out.print("Etiquetas: ");
                    etiquetas = Input.lerString();
                    try {
                        this.publicarMusica(conteudo, titulo, interprete, ano, etiquetas);
                    } catch (FileInexistenteException ex) {
                        System.out.println(ex.getMessage());
                    }
                    break;
                case 2:
                    System.out.print("Etiqueta: ");
                    etiqueta = Input.lerString();
                    try {
                        this.musicaByEtiqueta(etiqueta);
                    } catch (EtiquetaInvalidaException ex) {
                        System.out.println(ex.getMessage());
                    }
                    break;
                case 3:
                    System.out.print("Id da música a descarregar: ");
                    id = Input.lerInt();
                    try {
                        this.downloadMusica(id);
                    } catch (IDInvalidoException ex) {
                        System.out.println(ex.getMessage());
                    }
                    break;
                case 0:
                    System.out.println("Adeus!");
                    this.disconnect();
                    sair = true;
                    break;
                default:
                    System.out.println("A opção escolhida é inválida.");
                    break;
            }
        }
    }

    /**
     * Main da classe Cliente
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException{
        try {
            Cliente c = new Cliente("127.0.0.1", 12345); /* Trata da interligação da interface com os comandos executados pelo utilizador, comunicando estes com o sevidor */

            Cliente c_notificacao = new Cliente("127.0.0.1", 12345); /* Trata de receber notificações acerca de publicações de músicas novas */
            Thread n = new Thread(new NotificationClientThread(c_notificacao.in));
            n.start();

            while (!c.isLoggedin()){
                try {
                    c.autentica();
                }catch (IOException e){
                    n.interrupt();
                    c_notificacao.disconnect();
                    return;
                }
            }
            c.menu();

            n.interrupt();
            c_notificacao.disconnect();
        }catch (ConnectException e){
            System.err.println("O servidor não se encontra conectável.");
        }
    }
}
