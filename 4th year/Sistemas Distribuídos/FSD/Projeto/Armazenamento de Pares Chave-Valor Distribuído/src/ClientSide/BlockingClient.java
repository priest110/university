package ClientSide;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/*
*
* Simples wrapper do ClientStub que é bloqueante
*
* */


public class BlockingClient {

    private ClientStub cs;

    public BlockingClient(String toAddress, String fromAddress)
    {
        this.cs = new ClientStub(toAddress, fromAddress);
    }

    public void put(Map<Long,byte[]> values) throws ExecutionException, InterruptedException
    {
        this.cs.put(values).get();
    }

    public Map<Long,byte[]> get(Collection<Long> keys) throws ExecutionException, InterruptedException
    {
        return this.cs.get(keys).get();
    }
}
