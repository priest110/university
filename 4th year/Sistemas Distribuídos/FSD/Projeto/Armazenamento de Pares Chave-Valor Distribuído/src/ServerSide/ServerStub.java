package ServerSide;

import Clock.ScalarLogicalClock;
import Messages.ServerMessage;
import Messages.ClientMessage;
import Requests.GetServerRequest;
import Requests.PutServerRequest;
import io.atomix.cluster.messaging.MessagingConfig;
import io.atomix.cluster.messaging.impl.NettyMessagingService;
import io.atomix.utils.net.Address;
import io.atomix.utils.serializer.Serializer;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

class WaitingRequest
{

    private int timestamp;
    private PutServerRequest pr;
    private GetServerRequest gr;
    private int port;


    public WaitingRequest(int t)
    {
        this.timestamp = t;

    }

    public WaitingRequest(int t, int p)
    {
        this.timestamp = t;
        this.port = p;
        this.pr = null;
        this.gr = null;
    }


    public WaitingRequest(PutServerRequest put_req, int t, int porta)
    {
        this.gr = null;
        this.pr = put_req;
        this.timestamp = t;
        this.port = porta;
    }

    public WaitingRequest(GetServerRequest get_req, int t, int porta)
    {
        this.gr = get_req;
        this.pr = null;
        this.timestamp = t;
        this.port = porta;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public int getPort() {
        return port;
    }

    public PutServerRequest getPr() {
        return pr;
    }

    public GetServerRequest getGr() {
        return gr;
    }

}

public class ServerStub
{
    private Address addr;
    private NettyMessagingService ms;
    private ScheduledExecutorService es;
    private Serializer s;
    private ServerForwarder sf;
    private ServerStorage ss;
    private Integer last_req_id;
    private Map<Integer, PutServerRequest> put_requests;
    private Map<Integer, GetServerRequest> get_requests;
    private ScalarLogicalClock clock;
    private List<WaitingRequest> waitingRequests;
    private List<Address> st_addresses;
    private List<Address> fw_addresses;
    private List<Address> fw_addresses_broadcast;
    private Integer cnt;
    private WaitingRequest current;
    private Integer local_port;
    private Map<Integer, Integer> maxProc;
    private Integer storages_used;


    /*
     * addr -> Próprio endereço
     * storages -> Endereços das Storages
     * forwarders -> Endereços dos Forwarders
     * isForwarder -> Se é Forwarder ou não
     * */

    public ServerStub(String addr, String[] storages, String[] forwarders, Boolean isForwarder)
    {
        this.addr = Address.from(Integer.parseInt(addr));

        this.es = Executors.newScheduledThreadPool(1);
        this.ms = new NettyMessagingService("Server", this.addr, new MessagingConfig());
        this.s = Serializer.builder().withTypes(ClientMessage.class, ServerMessage.class).build();

        this.clock = new ScalarLogicalClock();

        /* FORWARD SERVERS */
        if(isForwarder)
        {
            this.put_requests = new HashMap<>();
            this.get_requests = new HashMap<>();
            this.last_req_id = 0;
            this.ss = null;
            this.sf = new ServerForwarder(storages, forwarders);
            this.st_addresses = new ArrayList<>();
            this.fw_addresses = new ArrayList<>();
            this.fw_addresses_broadcast = new ArrayList<>();
            this.waitingRequests = new ArrayList<>();
            //O Current vai ser uma abstração dos locks
            this.cnt = 0;
            this.current = null;
            this.local_port = Integer.parseInt(addr.substring(addr.lastIndexOf(":") + 1));
            this.maxProc = new HashMap<>();
            this.storages_used = 0;

            for(String endr : storages) {
                this.st_addresses.add(Address.from(Integer.parseInt(endr)));
            }

            for(String endr : forwarders){
                this.fw_addresses_broadcast.add(Address.from(Integer.parseInt(endr)));
                if(!endr.equals(addr)){
                    this.fw_addresses.add(Address.from(Integer.parseInt(endr)));
                    this.maxProc.put(Integer.parseInt(endr), 0);
                }
            }


            //#######################   PEDIDOS CLIENTE   #######################//
            //-> Eventos Originados por Pedidos do Cliente, o FW que o recebe irá
            //   adicionar o pedido à sua fila e fazem um Broadcast para que todos
            //   os outros FWs tenham noção do pedido e o incluam na sua lista

            //#######################   PUT REQUEST       #######################//
            ms.registerHandler("put-request", (a,m)->{

                this.clock.local_event();

                ClientMessage msg = this.s.decode(m);
                PutServerRequest new_pr = this.sf.scatter_put((Map<Long, byte[]>)(msg.getObj()), a, msg.getId());
                new_pr.setId(++(this.last_req_id));

                this.put_requests.put(new_pr.getId(), new_pr);

                //Teste - Adiciona a Fila Local
                this.waitingRequests.add(new WaitingRequest(new_pr, this.clock.getLTime(), local_port));

                ServerMessage sm = new ServerMessage(local_port, this.clock.getLTime(), "null");

                //Adiciona à fila dos outros FW
                for(int i = 0; i < fw_addresses_broadcast.size(); i++) {

                    ms.sendAsync(fw_addresses_broadcast.get(i), "new_event", this.s.encode(sm));
                }

                this.clock.local_event();

            }, this.es);


            //#######################   GET REQUEST       #######################//
            ms.registerHandler("get-request", (a,m)->{

                this.clock.local_event();

                ClientMessage msg = this.s.decode(m);
                GetServerRequest new_gr = this.sf.scatter_get((Collection<Long>)(msg.getObj()), a, msg.getId());
                new_gr.setId(++(this.last_req_id));

                this.get_requests.put(new_gr.getId(), new_gr);

                //Adiciona à Fila Local
                this.waitingRequests.add(new WaitingRequest(new_gr, this.clock.getLTime(), local_port));

                ServerMessage sm = new ServerMessage(local_port, this.clock.getLTime(), "null");
                //Adiciona à fila dos outros FW
                for(int i = 0; i < fw_addresses_broadcast.size(); i++) {
                    ms.sendAsync(fw_addresses_broadcast.get(i), "new_event", this.s.encode(sm));
                }

                this.clock.local_event();

            },this.es);


            //#######################   NOVOS EVENTOS   #######################//
            //-> Evento Disparado pelo:
            //   -> Processamento do FW recetor de um pedido do Cliente: todos
            //   os FWs serão notificados incluindo o próprio de modo a poder
            //   iniciar a execução o mais depressa possível ou quando tal
            //   não for possível por incumpriemento da condição
            //   Local Clock > Min conhecido dos outros FWs, após o Clock Update
            //
            //  -> Pelo Processamento de Pedidos que já se encontrem em espera,
            //    sendo que nesse caso é recebida uma msg com payload identificativo
            //    processing

            ms.registerHandler("new_event", (a,m)->{

                ServerMessage msg = this.s.decode(m);
                int min = 0;
                int remoteClock = msg.getTimestamp();
                int remotePort = msg.getPort();

                //Se for NOVO EVENTO
                if(!msg.getPayload().equals("processing"))
                {
                    //Verificar se o evento vem de um FW remoto: Se o evento foi originadado pelo próprio FW
                    // não se atualiza a lista, nem faz sentido enviar pedidos de clock update pq acabamos de fazer broadcast
                    // Apenas atualizar o Clock
                    if(local_port != remotePort){

                        //Atualizar os Minimos Conhecidos
                        this.maxProc.put(remotePort, remoteClock);
                        this.clock.remote_event(remoteClock);

                        //Adicionar à fila de Espera, será criado um Waiting Req com o os pedidos a null
                        //De forma a que cada Forwarder consiga perceber se tem que processar ou não
                        this.waitingRequests.add(new WaitingRequest(remoteClock, remotePort));

                        //Obter o ultimo minimo conhecido para todos os processos
                        min = Collections.min(maxProc.values());

                        //Enviar resposta explicita de Clock Update para todos os outros FWs
                        //Este Broadcast irá permitir quer FWs sem carga não bloqueiem o sistema,
                        //Se a carga fosse bem distribuida entre FWs a melhor alternativa seria apenas enviar para
                        // o FW que originou o evento
                        for(int i = 0; i < fw_addresses.size(); i++) {
                            ms.sendAsync(fw_addresses.get(i), "clock-min-update", String.valueOf(this.clock.getLTime()).getBytes());
                        }

                    }else {
                        this.clock.local_event();
                    }

                    /////////////////
                    //Só faz sentido atualizar o current se o pedido já tiver sido processado e removido
                    if(this.current == null)
                    {

                        //Irá definir o próximo pedido a ser executado se houver algum elegivel
                        current = this.sf.getNextReq(waitingRequests, min, this.clock.getLTime());

                        //Irá tentar executar o request definido no current, se não for
                        // o FW responsável por esse processamento, GR,PR = null, storages_used vai ser 0
                        // e irá aguardar a remoção desse pedido da lista e nova invocação de processamento ou novo pedido,
                        // Se for o resposável irá obter a nossa abstração de Lock e Executar
                        storages_used = this.sf.try2Process(current, this.ms, this.addr);
                    }
                }
                else //Se for PROCESSAMENTO de itens na lista de espera
                    {

                    remoteClock = msg.getTimestamp();
                    this.clock.remote_event(remoteClock);

                    //Só faz sentido atualizar o current se o pedido já tiver sido processado e removido
                    if(current == null)
                    {
                        min = Collections.min(maxProc.values());
                        current = this.sf.getNextReq(waitingRequests, min, this.clock.getLTime());

                        storages_used = this.sf.try2Process(current, this.ms, this.addr);
                    }
                }
            }, this.es);


            //#######################   LOCK OBTIDA   #######################//
            //-> Evento Disparado pela obtenção de um Lock por um FW :
            //   -> Irá verficar o tipo de Pedido (PUT / GET) e executá-lo,
            //   chamando as funções de put/get scatter nos Storages

            ms.registerHandler("lock-given", (a,m)->{
                this.clock.local_event();
                this.cnt++;

                String str = new String(m, StandardCharsets.UTF_8);

                if(str.compareTo("put") == 0){

                    int k = 0;
                    int s = current.getPr().put_req.entrySet().size()-1;

                    for(Map.Entry<Address, Map<Long, byte[]>> e : current.getPr().put_req.entrySet()){
                        ServerMessage sm = new ServerMessage(e.getValue(), current.getPr().getId(), this.clock.getLTime());
                        if(k == s) {
                            ms.sendAsync(e.getKey(), "put-scatter", this.s.encode(sm))
                                    .thenRun(() -> {
                                        this.clock.local_event();
                                    });
                        }else{
                            ms.sendAsync(e.getKey(), "put-scatter", this.s.encode(sm));
                            k++;
                        }
                    }

                }else if(str.compareTo("get") == 0){ //Realiza Operação de GET

                    int k = 0;
                    int s = current.getGr().get_req.entrySet().size() - 1;

                    for (Map.Entry<Address, Collection<Long>> e : current.getGr().get_req.entrySet()) {

                        ServerMessage sm = new ServerMessage(e.getValue(), current.getGr().getId(), this.clock.getLTime());

                        if (k == s)
                            ms.sendAsync(e.getKey(), "get-scatter", this.s.encode(sm))
                                    .thenRun(() -> { this.clock.local_event(); });
                        else {
                            ms.sendAsync(e.getKey(), "get-scatter", this.s.encode(sm));
                            k++;
                        }
                    }
                }

                this.cnt = 0;

            }, this.es);

            //#######################   RESPOSTAS DOS SSs   #######################//
            //-> Evento Disparado pela respostas dos Storage Servers aos pedidos
            //   -> Irá enviar a resposta aos clientes, remover o pedido executado da lista,
            //      libertar o seu lock e notificar todos os outros para fazerem o mesmo

            //      AFTER PUT REQUEST       //
            ms.registerHandler("put-gather", (a,m)->{

                this.clock.local_event();
                cnt++;

                //Modifica o Registo de PRs Local || Manda msg p cliente//
                Integer id = this.s.decode(m);
                PutServerRequest pr = this.put_requests.get(id);
                pr.recievedOne();
                ClientMessage msg_ret = new ClientMessage(null, pr.getMsg_cl_id());

                if(pr.isFinished()){

                    for(int i = 0; i < fw_addresses.size(); i++)
                        ms.sendAsync(fw_addresses.get(i), "unlock", String.valueOf(this.clock.getLTime()).getBytes());

                    cnt = 0;
                    storages_used = 0;

                    this.waitingRequests.remove(current);
                    this.current = null;


                    ms.sendAsync(pr.getSender(), "put-completed", this.s.encode(msg_ret));

                    this.put_requests.remove(id);
                }
                else{
                    this.put_requests.replace(id, pr);
                }

            }, this.es);

            //      AFTER GET REQUEST       //
            ms.registerHandler("get-gather", (a,m)->{

                this.clock.local_event();
                cnt++;

                ServerMessage msg = this.s.decode(m);
                GetServerRequest gr = this.get_requests.get(msg.getId());
                gr.addToResponse((Map<Long, byte[]>)(msg.getObj()));
                ClientMessage cl_msg = new ClientMessage(gr.get_res, gr.getCl_msg_id());

                if(gr.isFinished()){
                    ms.sendAsync(gr.getSender(), "get-completed", this.s.encode(cl_msg));
                    this.get_requests.remove(msg.getId());

                    for(int i = 0; i < fw_addresses.size(); i++) {
                        ms.sendAsync(fw_addresses.get(i), "unlock", String.valueOf(this.clock.getLTime()).getBytes());
                    }

                    cnt = 0;
                    storages_used = 0;

                    this.waitingRequests.remove(current);
                    this.current = null;
                }
                else{
                    this.get_requests.replace(msg.getId(), gr);
                }

            }, this.es);

            //#######################   EVENTO DE UNLOCK   #######################//
            //-> Evento Disparado após final da execução de um pedido
            // e notificação ao cliente, informa os restantes FWs que um FW
            // libertou o Lock e portanto o pedido foi executado e pode ser
            // removido da lista, se houver mais pedidos em espera, será gerado
            // um evento de modo a processá-los

            ms.registerHandler("unlock", (a,m)->{

                int porta = Integer.parseInt(a.toString().substring(a.toString().lastIndexOf(":") + 1));
                int rec_clk = Integer.parseInt(new String(m, StandardCharsets.UTF_8));
                maxProc.put(porta, rec_clk);

                this.clock.remote_event(rec_clk);

                this.waitingRequests.remove(current);
                this.current = null;

                if(waitingRequests.size() != 0){

                    ServerMessage sm = new ServerMessage(local_port, this.clock.getLTime(), "processing");

                    for(int i = 0; i < fw_addresses_broadcast.size(); i++) {
                        ms.sendAsync(fw_addresses_broadcast.get(i), "new_event", this.s.encode(sm));
                    }
                }

            }, this.es);


            //#######################   EVENTO DE UPDATE GERAL DE CLOCKS   #######################//
            //-> Evento Disparado paraa gerar Respostas Explicitas e dessa forma evitar que
            //   o programa pare por servidores que não respondem por não terem carga:
            //   Depois de atualizar os relógios vai verificar se a execução de determinado pedido
            //   pelos FWs já é possível

            ms.registerHandler("clock-min-update", (a,m)->{

                int porta = Integer.parseInt(a.toString().substring(a.toString().lastIndexOf(":") + 1));
                int rec_clk = Integer.parseInt(new String(m, StandardCharsets.UTF_8));

                //if(maxProc.get(porta) <= rec_clk){
                maxProc.put(porta, rec_clk);
                //}

                int min = Collections.min(maxProc.values());
                this.clock.remote_event(rec_clk);

                if(waitingRequests.size() != 0 && this.current == null){

                    current = this.sf.getNextReq(waitingRequests, min, this.clock.getLTime());

                    //# try2Process #//
                    storages_used = this.sf.try2Process(current, this.ms, this.addr);
                }
            }, this.es);

        }
        /* STORAGE SERVERS */
        else{
            this.get_requests = null;
            this.put_requests = null;
            this.ss = new ServerStorage();
            this.sf = null;
            this.last_req_id = -1;


            //Vai fazer o put no storage
            ms.registerHandler("put-scatter", (a,m) -> {

                ServerMessage msg = this.s.decode(m);

                Map<Long,byte[]> map = (Map<Long,byte[]>)(msg.getObj());

                //this.clock.remote_event(msg.getTimestamp());

                this.ss.lock();

                this.ss.put(map);

                //Evento de envio -> atualizar o relógio
                ms.sendAsync(a, "put-gather", this.s.encode(msg.getId()))
                        .thenRun(()->{
                            this.clock.local_event();
                        });

                this.ss.unlock();

            }, this.es);

            //Vai fazer o GET dos values
            ms.registerHandler("get-scatter", (a,m)->{

                ServerMessage msg = this.s.decode(m);
                Collection<Long> col = (Collection<Long>)(msg.getObj());

                this.ss.lock();

                Map<Long,byte[]> ret = this.ss.get(col);

                ms.sendAsync(a, "get-gather", this.s.encode(new ServerMessage(ret, msg.getId(), msg.getTimestamp())))
                        .thenRun(() -> {
                            this.clock.local_event();
                        });

                this.ss.unlock();

            }, this.es);
        }

        this.ms.start();
    }

    /* PARA DEBUG */

    public void showStorage()
    {
        if(this.ss != null) this.ss.showState();
    }

    public void showForwarder()
    {
        if(this.sf != null) this.sf.showAvailServers();
    }

    public void showClock(){
        System.out.println(addr.toString() + " : " + this.clock.getLTime());
    }
}
