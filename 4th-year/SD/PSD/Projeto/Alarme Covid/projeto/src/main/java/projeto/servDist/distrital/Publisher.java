package projeto.servDist.distrital;

import org.zeromq.ZMQ;

public class Publisher {
    private ZMQ.Context context;
    private ZMQ.Socket socket;

    public Publisher(ZMQ.Context c, ZMQ.Socket s){
        this.context = c;
        this.socket = s;
    }

    public synchronized void sendMesssage(String msg){
        System.out.println("Notificação enviada: " + msg);
        this.socket.send(msg);
    }

}
