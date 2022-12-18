package Servidor;

import Comum.UsernameExistenteException;

import java.io.*;
import java.net.Socket;

public class WorkThread implements Runnable{
    private Socket cliente;
    private Utilizador utilizador;
    private Tarefa tarefa;
    private DownloadLock lock;
    private NotificationLock lock_note;

    protected DataInputStream in;
    protected DataOutputStream out;

    /**
     * Método construtor
     *
     * @param c         socket do cliente
     * @param t         tarefa a executar
     * @param lock      lock da classe DownloadLock
     * @param lock_note lock da classe NotificationLock
     * @throws      IOException
     */
    public WorkThread(Socket c, Tarefa t, DownloadLock lock, NotificationLock lock_note) throws IOException {
        this.cliente = c;
        this.utilizador = null;
        this.tarefa = t;
        this.lock = lock;
        this.lock_note = lock_note;
        this.in = new DataInputStream(cliente.getInputStream());
        this.out = new DataOutputStream(cliente.getOutputStream());
    }

    /**
     * Get do lock da classe DownloadLock
     * @return      do lock
     */
    public DownloadLock getLock() {
        return this.lock;
    }

    /**
     * Get do lock da classe NotificationLock
     * @return      do lock
     */
    public NotificationLock getLock_note() {
        return this.lock_note;
    }

    /**
     * Get do utilizador
     * @return      do utilizador
     */
    public Utilizador getUtilizador() {
        return this.utilizador;
    }

    /**
     * Set do utilizador
     * @param utilizador    a dar set
     */
    public void setUtilizador(Utilizador utilizador) {
        this.utilizador = utilizador;
    }

    /**
     * Run da thread
     */
    public void run(){
        try {
            String cmd;
            while ((cmd = in.readUTF()) != null) {
                System.out.println("Li do socket: " + cmd);
                String resposta = this.tarefa.execute(this,cmd);
                out.writeUTF(resposta);
                out.flush();
            }
        }catch(IOException | UsernameExistenteException io){
            System.err.println("Utilizador Saiu: " + io.getMessage());
        }finally {
            try {
                if(this.utilizador != null)
                    this.utilizador.setLogged(false);
                cliente.shutdownOutput();
                cliente.shutdownInput();
                cliente.close();
            }catch (IOException io){
                io.printStackTrace();
            }
        }
    }
}
