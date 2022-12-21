package ServerSide;

import io.atomix.utils.net.Address;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;


//Esta classe é elementar e só lida com os pedidos diretamente da instância do servidor com que está a lidar. Mais nada.
public class ServerStorage
{
    private Map<Long, byte[]> map;
    private ReentrantLock lock;

    public ServerStorage()
    {
        this.map = new HashMap<>();
        this.lock = new ReentrantLock();
    }

    /* LOCKS */
    public void lock()
    {
        this.lock.lock();
    }

    public void unlock()
    {
        this.lock.unlock();
    }


    /* PUT & GET */
    public synchronized void put(Map<Long,byte[]> values)
    {
        for(Map.Entry<Long, byte[]> e : values.entrySet())
        {
            this.map.put(e.getKey(), e.getValue());
        }
    }

    public synchronized Map<Long,byte[]> get(Collection<Long> keys)
    {
        Map<Long,byte[]> ret = new HashMap<>();
        for(Long k : keys)
        {
            byte[] ba = map.get(k);
            if(ba != null)
            {
                ret.put(k,ba);
            }
        }
        return ret;
    }

    /* Para propósitos de Debug */
    public void showState()
    {
        for(Map.Entry<Long,byte[]> e : this.map.entrySet())
        {
            System.out.println(e.getKey() + " : " + (new String(e.getValue())));
        }
    }
}
