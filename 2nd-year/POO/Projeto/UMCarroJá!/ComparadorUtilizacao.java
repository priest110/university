import java.util.Comparator;


public class ComparadorUtilizacao implements Comparator<Cliente> {
    
    public int compare (Cliente c1, Cliente c2) {
        double totalC1 = 0.0;
        double totalC2 = 0.0;
        for(Aluguer a : c1.getAlugueres()){
            totalC1 += a.getKm();
        }
        for(Aluguer b : c2.getAlugueres()){
            totalC2 += b.getKm();
        }
        if(totalC1 < totalC2)
            return -1;
        else if(totalC1 == totalC2)
            return 0;
        else
            return 1;
    }
}