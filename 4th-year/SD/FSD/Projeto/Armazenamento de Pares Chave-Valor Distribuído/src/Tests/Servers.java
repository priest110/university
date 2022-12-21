package Tests;

import ServerSide.ServerStub;

public class Servers {
    public static void main(String[] args) throws InterruptedException
    {
        //Storages
        ServerStub s1 = new ServerStub("12345", new String[]{"12345", "12346", "12347"}, new String[]{"12344", "12343", "12342", "12341", "12340", "12339", "12338"}, false);
        ServerStub s2 = new ServerStub("12346", new String[]{"12346", "12345", "12347"}, new String[]{"12344", "12343", "12342", "12341", "12340", "12339", "12338"}, false);
        ServerStub s3 = new ServerStub("12347", new String[]{"12347", "12345", "12346"}, new String[]{"12344", "12343", "12342", "12341", "12340", "12339", "12338"}, false);


        //Forwarders
        ServerStub f1 = new ServerStub("12344", new String[]{"12345", "12346", "12347"}, new String[]{"12344", "12343", "12342", "12341", "12340", "12339", "12338"}, true);
        ServerStub f2 = new ServerStub("12343", new String[]{"12345", "12346", "12347"}, new String[]{"12344", "12343", "12342", "12341", "12340", "12339", "12338"}, true);
        ServerStub f3 = new ServerStub("12342", new String[]{"12345", "12346", "12347"}, new String[]{"12344", "12343", "12342", "12341", "12340", "12339", "12338"}, true);
        ServerStub f4 = new ServerStub("12341", new String[]{"12345", "12346", "12347"}, new String[]{"12344", "12343", "12342", "12341", "12340", "12339", "12338"}, true);
        ServerStub f5 = new ServerStub("12340", new String[]{"12345", "12346", "12347"}, new String[]{"12344", "12343", "12342", "12341", "12340", "12339", "12338"}, true);
        ServerStub f6 = new ServerStub("12339", new String[]{"12345", "12346", "12347"}, new String[]{"12344", "12343", "12342", "12341", "12340", "12339", "12338"}, true);
        ServerStub f7 = new ServerStub("12338", new String[]{"12345", "12346", "12347"}, new String[]{"12344", "12343", "12342", "12341", "12340", "12339", "12338"}, true);

        for(int i = 0; i < 100000; i++)
        {
            Thread.sleep(10000);
            System.out.println("-----------------------------");
            System.out.println("S0:");
            s1.showStorage();
            System.out.println("S1:");
            s2.showStorage();
            System.out.println("S2:");
            s3.showStorage();
            System.out.println("-----------------------------");
        }
    }
}
