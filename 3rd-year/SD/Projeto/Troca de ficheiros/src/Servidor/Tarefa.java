package Servidor;

import Comum.*;
import Comum.UtilizadorJaLogado;

import java.io.*;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Map;

public class Tarefa {
    private Servidor servidor;

    final int OK = 100;
    final int ERR_DadosInvalidos = 101;
    final int ERR_UtilizadorExisttente = 102;
    final int ERR_UtilizadorJaLogado = 103;
    final int ERR_SemMusicasComEstaEtiqueta = 104;
    final int ERR_IDInexistente = 105;
    final int DEAD = 200;
    final int MAXSIZE = 200000; // Tamanho máximo do ficheiro a transferir

    /**
     * Método construtor
     * @param s     servidor
     */
    public Tarefa(Servidor s){
        this.servidor = s;
    }

    /**
     * Executa a tarefa passada como argumento
     * @param tarefa    que o cliente pede
     * @return          resposta em formato string
     */
    public String execute(WorkThread thread, String tarefa) throws UsernameExistenteException, IOException {
        String[] comando = tarefa.trim().split("\\-");
        String resposta = "";
        switch (comando[0]){
            case "1" : // login
                try {
                    thread.setUtilizador(this.servidor.login(comando[1], comando[2]));
                    Thread t = new Thread(new NotificationReceiveThread(thread)); /* Thread que fica à espera de receber sinal da thread do servidor */
                    t.start();
                    resposta = Integer.toString(OK);
                } catch (DadosInvalidosException dadosInvalidos) {
                    resposta = Integer.toString(ERR_DadosInvalidos);
                } catch (UtilizadorJaLogado utilizadorJaLogado) {
                    resposta = Integer.toString(ERR_UtilizadorJaLogado);
                }
                break;
            case "2" : // verificar se utilizador está loggado
                if(thread.getUtilizador() != null)
                    resposta = Integer.toString(OK);
                else
                    resposta = Integer.toString(DEAD);
                break;
            case "3" : // registo
                try{
                    this.servidor.registar(comando[1], comando[2]);
                    resposta = Integer.toString(OK);
                }catch (UsernameExistenteException e){
                    resposta = Integer.toString(ERR_UtilizadorExisttente);
                }
                break;
            case "4" : // publicar música
                int id = this.servidor.publicarMusica(comando[1], comando[2], Integer.parseInt(comando[3]), comando[4].split(" "));

                File filez = new File("plataforma/" + id);
                OutputStream out = new FileOutputStream(filez);
                int nRead;
                String size = thread.in.readUTF();
                int size_int = Integer.parseInt(size);

                byte[] data = new byte[MAXSIZE];
                while (size_int > 0) {
                    nRead = thread.in.read(data, 0, data.length);
                    size_int -= nRead;
                    out.write(data, 0, nRead);
                    out.flush();
                }
                out.close();

                thread.getLock_note().feitaPublicao(comando[1], comando[2]); // é avisado à thread das notificações que foi feita uma publicação

                resposta = Integer.toString(id);
                break;
            case "5" : // procurar músicas segundo uma etiqueta
                try{
                    Map<Integer, Musica> x = this.servidor.musicaByEtiqueta(comando[1]);
                    thread.out.writeUTF(Integer.toString(OK));
                    thread.out.flush();
                    for(Integer a : x.keySet()) {
                        thread.out.writeUTF("Música com id = " + a);
                        thread.out.writeUTF("-Título: " + x.get(a).getTitulo());
                        thread.out.writeUTF("-Intérprete: " + x.get(a).getInterprete());
                        thread.out.writeUTF("-Ano: " + x.get(a).getAno());
                        thread.out.writeUTF("-Nº de etiquetas: " + x.get(a).getEtiquetas().length);
                        thread.out.writeUTF("-Nº de vezes que foi descarregada: " + x.get(a).getVezes_descarregada());
                        thread.out.flush();
                    }
                    resposta = "---";
                }catch (EtiquetaInvalidaException e){
                    resposta = Integer.toString(ERR_SemMusicasComEstaEtiqueta);
                 }
                break;
            case"6" : // download música
                try{thread.getLock().downloadLock();

                    Musica m = this.servidor.existeID(Integer.parseInt(comando[1]));

                    // Acede ao conteúdo da música cujo o ID foi passado
                    File file = new File("plataforma/"+comando[1]);
                    if(!file.exists())
                        throw new FileInexistenteException();

                    FileInputStream fis = new FileInputStream(file);
                    byte[] buf = new byte[MAXSIZE];
                    int readNum;
                    int tam_ficheiro = (int)file.length();
                    thread.out.writeUTF(Integer.toString(tam_ficheiro));
                    thread.out.flush();

                    while (tam_ficheiro > 0) {
                        readNum = fis.read(buf);
                        tam_ficheiro -= readNum;
                        thread.out.write(buf, 0, readNum); //no doubt here is 0
                        thread.out.flush();
                    }
                    fis.close();

                    thread.getLock().downloadUnlock();

                    resposta = Integer.toString(OK);
                } catch (IDInvalidoException | InterruptedException | FileInexistenteException e){
                    resposta = Integer.toString(ERR_IDInexistente);
                }
                break;
            default :
                System.err.println("Tarefa não suportada: " + comando[0]);
                resposta = Integer.toString(DEAD);
                break;
        }
        return resposta;
    }
}
