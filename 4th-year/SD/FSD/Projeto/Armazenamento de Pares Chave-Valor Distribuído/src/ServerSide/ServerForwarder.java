package ServerSide;

import Clock.ScalarLogicalClock;
import Messages.ServerMessage;
import Requests.GetServerRequest;
import Requests.PutServerRequest;
import io.atomix.cluster.messaging.MessagingService;
import io.atomix.utils.net.Address;
import io.atomix.utils.serializer.Serializer;

import java.util.*;

/*
 *
 * Contém as informações que um servidor Forwarder precisa para fazer as separações dos pedidos
 *
 * */

public class ServerForwarder {

    private Map<Long, Address> storages;
    private List<Address> forwarders;
    private int stg_count;
    private ScalarLogicalClock clock;

    public ServerForwarder(String[] storages, String[] forwarders)
    {
        this.storages = new HashMap<>();
        this.forwarders = new ArrayList<>();
        this.clock = new ScalarLogicalClock();

        this.stg_count = storages.length;
        long counter = 0L;

        Arrays.sort(storages);
        for (String addr : storages) {
            this.storages.put((counter++)%(this.stg_count), Address.from(Integer.parseInt(addr)));
        }

        for(String addr : forwarders)
        {
            this.forwarders.add(Address.from(Integer.parseInt(addr)));
        }
    }

    //O forwarder vai precisar de distribuir os dados pelos servidores alvo (o skeleton manda)
    public PutServerRequest scatter_put(Map<Long,byte[]> pairs, Address a, int i)
    {
        Map<Address, Map<Long,byte[]>> ret = new HashMap<>();
        long curr = -1L;
        Address aux_a = null;

        //Repartir o pedido pelos servidores que podem responder
        //Storage Load Balance
        for(Map.Entry<Long,byte[]> e : pairs.entrySet())
        {
            Map<Long, byte[]> aux = new HashMap<>();
            curr = e.getKey()%this.stg_count;
            aux_a = this.storages.get(curr);

            //Se já guardou nesse server
            //vai guardar os par k,v anteriores num map auxiliar
            //vai adicionar tbm o par mais recente a esse map e reconstituir essa key com todos os novos valores
            if(ret.containsKey(aux_a)){
                aux = ret.get(aux_a);
                aux.put(e.getKey(), e.getValue());
                ret.replace(aux_a, aux);
            }
            else{
                aux.put(e.getKey(), e.getValue());
                ret.put(aux_a, aux);
            }
        }
        return new PutServerRequest(ret, a, i);
    }

    public GetServerRequest scatter_get(Collection<Long> keys, Address a, int cmi)
    {
        Map<Address, Collection<Long>> ret = new HashMap<>();
        long curr = -1L;
        Address aux_a = null;

        for(Long key : keys)
        {
            Collection<Long> aux = new ArrayList<>();
            curr = key%this.stg_count;
            aux_a = this.storages.get(curr);

            if(ret.containsKey(aux_a)){
                aux = ret.get(aux_a);
                aux.add(key);
                ret.replace(aux_a, aux);
            }
            else{
                aux.add(key);
                ret.put(aux_a, aux);
            }
        }

        return new GetServerRequest(ret, a, cmi);
    }


    //####################        getNextReq            #####################//
    // Vai verificar se existe algum pedido elegivel para execução na lista: //
    //      -> não faz sentido processar se o FW ainda não tiver recebido    //
    //         mensagens de todos os outros min=0                            //
    //      -> se o timestamp da msg em análise for maior que o minimo       //
    //         conhecido para os outros FWs é ignorada                       //
    //      -> Como a lista de pedidos é igual em todos os FWs garante-se    //
    //         que estes chegam sempre a acordo quanto ao proximo pedido a   //
    //         ser executado que será determinado pelo que tiver o relogio   //
    //         logico mais baixo e menor numero de porta                     //

    public WaitingRequest getNextReq(List<WaitingRequest> waiting, int minimo, int clk){

        WaitingRequest wr = null;

        if(minimo == 0){
            return null;
        }

        WaitingRequest min = waiting.stream().min(Comparator.comparing(WaitingRequest::getTimestamp).thenComparing(WaitingRequest::getPort)).get();

        wr = min;

        if(wr.getTimestamp() >= clk){
            return null;
        }

        return wr;
    }

    //####################        try2Process            ####################//
    // Tenta Executar o pedido atualmente no current (se for GET chama um    //
    // evento lock-given enviado o payload get / se for PUT chama o evento   //
    // lock-given com o payload "put") e devolve o numero de storages usados //
    // iniciando a execução desse pedido.                                    //
    // Se não for nenhum dos 2 significa que não é este server que tem q     //
    // processar e devolve os used_strages a 0                               //


    public int try2Process(WaitingRequest curr, MessagingService ms, Address addr)
    {

        int used_stores = 0;

        if (curr.getPr() != null)
        {
            used_stores = curr.getPr().put_req.size();
            ms.sendAsync(addr, "lock-given", "put".getBytes());
        }
        else if (curr.getGr() != null)
        {

            used_stores = curr.getGr().get_req.size();
            ms.sendAsync(addr, "lock-given", "get".getBytes());
        }
        return used_stores;
    }


    //Para propósitos de teste
    public void showAvailServers()
    {
        this.storages.forEach((k, v) -> System.out.println(k + ":" + v));
    }
}
