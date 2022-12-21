package ClientSide;

import Messages.ClientMessage;
import io.atomix.cluster.messaging.MessagingConfig;
import io.atomix.cluster.messaging.impl.NettyMessagingService;
import io.atomix.utils.net.Address;
import io.atomix.utils.serializer.Serializer;

import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ClientStub
{
    private NettyMessagingService ms;
    private ScheduledExecutorService es;
    private Address toAddr;
    private Address fromAddr;
    private Serializer s;
    private Map<Integer,CompletableFuture<Void>> putRequests;
    private Map<Integer,CompletableFuture<Map<Long,byte[]>>> getRequests;
    private Integer lastId;

    //Construtor parametrizado vai ter os registerHandlers para quando as operações acabarem e obtivermos resposta
    public ClientStub(String toAddress, String fromAddress)
    {
        this.toAddr = Address.from(Integer.parseInt(toAddress));
        this.fromAddr = Address.from(Integer.parseInt(fromAddress));

        this.es = Executors.newScheduledThreadPool(1);
        this.ms = new NettyMessagingService("Client", this.fromAddr, new MessagingConfig());
        this.s = Serializer.builder().withTypes(ClientMessage.class, Map.class, Collection.class).build();

        this.putRequests = new HashMap<>();
        this.getRequests = new HashMap<>();

        this.lastId = 0;

        //Timeout com espaço de tempo generoso
        es.schedule(()->{
            System.out.println("Client timeout!");
        }, 365, TimeUnit.DAYS);

        //Para lidar com a finalização de um PUT
        ms.registerHandler("put-completed", (a,m)->{
            ClientMessage msg = this.s.decode(m);

            synchronized (putRequests)
            {
                if(this.putRequests.containsKey(msg.getId()))
                {
                    this.putRequests.get(msg.getId()).complete(null);
                    this.putRequests.remove(msg.getId());
                }
            }

            System.out.println("Put completed");
        }, this.es);

        //Para lidar com a finalização de um GET
        ms.registerHandler("get-completed", (a,m)->{
            ClientMessage msg = this.s.decode(m);
            Map<Long,byte[]> ret = (Map<Long,byte[]>)(msg.getObj());

            synchronized (getRequests)
            {
                if(this.getRequests.containsKey(msg.getId()))
                {
                    this.getRequests.get(msg.getId()).complete(ret);
                    this.getRequests.remove(msg.getId());
                }
            }

            System.out.println("Get Completed");
        }, this.es);

        ms.start();
    }

    public CompletableFuture<Void> put(Map<Long,byte[]> values)
    {
        CompletableFuture<Void> cf = new CompletableFuture<>();
        ClientMessage msg = new ClientMessage(values, ++this.lastId);

        ms.sendAsync(toAddr, "put-request", this.s.encode(msg))
                .thenRun(() -> {
                    synchronized (putRequests)
                    {
                        this.putRequests.put(msg.getId(), cf);
                    }
                    System.out.println("Put request sent");
                })
                .exceptionally( e -> {
                    e.printStackTrace();
                    return null;
                });
        return cf;
    }

    public CompletableFuture<Map<Long,byte[]>> get(Collection<Long> keys)
    {
        CompletableFuture<Map<Long,byte[]>> cf = new CompletableFuture<>();
        ClientMessage msg = new ClientMessage(keys, ++this.lastId);

        ms.sendAsync(toAddr, "get-request", this.s.encode(msg))
                .thenRun(() -> {
                    synchronized (getRequests)
                    {
                        this.getRequests.put(msg.getId(), cf);
                    }
                    System.out.println("Get request sent");
                })
                .exceptionally(e -> {
                    e.printStackTrace();
                    return null;
                });
        return cf;
    }


}
