package Servidor;

import Comum.*;

import java.io.File;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;

public class Servidor {
    private ListaUtilizadores utilizadores;
    private ListaMusicas musicas;

    final static int MAXDOWN = 5; // Limite de descargas simultâneas

    /**
     * Método construtor
     */
    public Servidor() {
        this.utilizadores = new ListaUtilizadores();
        this.musicas = new ListaMusicas();
        // Criamos a diretoria para onde vamos guardar os files da música
        new File("plataforma").mkdir();
    }

    /**
     * Login de um utilizador
     * @param user      do utilizador
     * @param pass      do utilizador
     * @return      do utilizador
     * @throws UtilizadorJaLogado
     * @throws DadosInvalidosException
     */
    protected Utilizador login(String user, String pass) throws UtilizadorJaLogado, DadosInvalidosException {
        if (!this.utilizadores.utilizadorValido(user, pass))
            throw new DadosInvalidosException();
        Utilizador u = this.utilizadores.getUtilizador(user);
        u.lock();
        if (u.isLogged()){
            u.unlock();
            throw new UtilizadorJaLogado();
        }
        u.setLogged(true);
        u.unlock();
        return u;
    }

    /**
     * Registo de um utilizador
     * @param user      do utilizador
     * @param pass      do utilizador
     * @throws UsernameExistenteException
     */
    protected void registar(String user, String pass) throws UsernameExistenteException {
        this.utilizadores.registarUtilizador(user, pass);
    }

    /**
     * Publicar música
     * @param titulo        da música
     * @param interprete    da música
     * @param ano           da música
     * @param etiquetas     da música
     * @return      id da música carregada
     */
    protected int publicarMusica(String titulo, String interprete, int ano, String[] etiquetas){
        return this.musicas.publicarMusica(titulo, interprete, ano, etiquetas);
    }

    /**
     * Procurar as músicas que têm determinada etiqueta
     * @param   etiqueta        da música
     * @return      map das músicas
     * @throws EtiquetaInvalidaException
     */
    protected Map<Integer, Musica> musicaByEtiqueta(String etiqueta) throws EtiquetaInvalidaException {
        return this.musicas.musicaByEtiqueta(etiqueta);
    }

    /**
     * Verifica se existe na lista de músicas alguma com determinado ID, caso exista vai devolver a mesma, e incrementar o nº de vezes
     * que foi descarregada.
     * @param id        da música a descarregar
     * @return      música a descarregar
     * @throws IDInvalidoException
     */
    protected Musica existeID(int id) throws IDInvalidoException {
        return this.musicas.existeID(id);
    }

    /**
     * Main da classe Servidor
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        Servidor server = new Servidor();
        Tarefa tarefa = new Tarefa(server);
        ServerSocket servidor = new ServerSocket(12345);

        /* Classe com as variáveis de condição para o controlo do número de downloads feitos de forma simultânea */
        DownloadLock down = new DownloadLock(MAXDOWN);

        /* Classe com as variáveis de condição para enviar e receber notificações de músicas novas na plataforma */
        NotificationLock note = new NotificationLock();
        /* Thread do servidor responsável por avisar as threads(criada após o login de um utilizador) que foi publicada uma música na plataforma*/
        Thread n = new Thread(new NotificationSendThread(note));
        n.start();

        int i = 0;
        while(true){
            Socket cliente = servidor.accept(); /* Socket que liga o cliente ao servidor */
            Socket cliente_notificacao = servidor.accept(); /* Socket que liga a parte do cliente que recebe as notificações ao servidor */

            System.out.println("Ligou-se um cliente ao servidor.");

            Thread c = new Thread(new WorkThread(cliente, tarefa, down, note)); /* Thread responsável pela execução daquilo que o cliente pretende, ao interagir com o servidor */
            c.start();
            Thread c_notificacao = new Thread(new NotificationToClientThread(cliente_notificacao, note)); /* Thread responsável pelo envio de notificações ao cliente */
            c_notificacao.start();
        }
    }
}