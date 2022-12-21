package Tests;

import ClientSide.BlockingClient;
import ClientSide.ClientStub;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class Example {
    public static void main(String[] args) throws InterruptedException, ExecutionException
    {
        ClientStub c1 = new ClientStub("12344", "1200");
        ClientStub c2 = new ClientStub("12343", "1201");

        Map<Long, byte[]> m1 = new HashMap<>();
        Collection<Long> cl2 = new ArrayList<>();

        m1.put(1L, "1".getBytes());
        m1.put(2L, "2".getBytes());

        cl2.add(1L);
        cl2.add(2L);

        c1.put(m1).get();//O get obriga a esperar até acabar antes de avançar

        m1.clear();

        m1 = c2.get(cl2).get();

        for(Map.Entry<Long,byte[]> e : m1.entrySet())
            System.out.println(e.getKey() + " : " + (new String(e.getValue())));

    }
}
