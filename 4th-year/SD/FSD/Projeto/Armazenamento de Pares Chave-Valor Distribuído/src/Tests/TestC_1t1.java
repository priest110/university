package Tests;

import ClientSide.ClientStub;
import ServerSide.ServerStub;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class TestC_1t1 {

    private static Integer MAX_ITER = 100;

    /*
    * Todos os clientes são de forwarders diferentes
    *
    * */

    public static void main(String[] args) throws InterruptedException, ExecutionException
    {

        //Cada Cliente manda para o forwarder respetivo
        ClientStub c1 = new ClientStub("12344", "1234");
        ClientStub c2 = new ClientStub("12343", "1233");
        ClientStub c3 = new ClientStub("12342", "1232");
        ClientStub c4 = new ClientStub("12341", "1231");
        ClientStub c5 = new ClientStub("12340", "1230");

        Map<Long, byte[]> pt1 = new HashMap<>();
        Map<Long, byte[]> pt2 = new HashMap<>();
        Map<Long, byte[]> pt3 = new HashMap<>();
        Map<Long, byte[]> pt4 = new HashMap<>();
        Map<Long, byte[]> pt5 = new HashMap<>();

        Collection<Long> col1 = new ArrayList<>();
        Collection<Long> col2 = new ArrayList<>();
        Collection<Long> col3 = new ArrayList<>();
        Collection<Long> col4 = new ArrayList<>();
        Collection<Long> col5 = new ArrayList<>();

        col2.add(1L);
        col2.add(2L);
        col2.add(3L);

        col3.add(1L);
        col3.add(2L);
        col3.add(3L);

        pt4.put(0L, "1".getBytes());
        pt4.put(1L, "2".getBytes());
        pt4.put(2L, "3".getBytes());
        pt4.put(3L, "4".getBytes());
        pt4.put(4L, "5".getBytes());

        pt5.put(0L, "6".getBytes());
        pt5.put(1L, "7".getBytes());
        pt5.put(2L, "8".getBytes());
        pt5.put(3L, "9".getBytes());
        pt5.put(4L, "10".getBytes());

        pt1.put(0L, "11".getBytes());
        pt1.put(1L, "12".getBytes());
        pt1.put(2L, "13".getBytes());
        pt1.put(3L, "14".getBytes());
        pt1.put(4L, "15".getBytes());

        for(int i = 0; i < MAX_ITER; i++)
        {
            c1.put(pt1).get();
            //c2.get(col2).get();
            c3.get(col3).get();
            //c4.put(pt4).get();
            c5.put(pt5).get();
        }

        Collection<Long> keys = new ArrayList<>();
        keys.add(0L);
        keys.add(1L);

        Thread.sleep(6000);

        Map<Long, byte[]> cf1 = c5.get(keys).get();

        for(Map.Entry<Long, byte[]> e : cf1.entrySet())
            System.out.println("CF1: " + e.getKey() + " : " + new String(e.getValue()));


        System.out.println("Finished!");
    }
}
