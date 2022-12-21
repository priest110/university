import java.util.Comparator;
import java.io.*;

public class ComparadorParProdQuantos implements Comparator<IParProdQuantos>, Serializable {

    public int compare(IParProdQuantos c1, IParProdQuantos c2){
        if(c1.getQuantos() > c2.getQuantos())
            return -1;
        else if(c1.getQuantos() < c2.getQuantos())
            return 1;
        else
            return c1.getProd().compareTo(c2.getProd());
    }
}


