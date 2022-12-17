import java.util.Comparator;
import java.io.*;

public class ComparadorParCliQuantos implements Comparator<IParCliQuantos>, Serializable {

    public int compare(IParCliQuantos c1, IParCliQuantos c2){
        if(c1.getQuantos() > c2.getQuantos())
            return -1;
        else if(c1.getQuantos() < c2.getQuantos())
            return 1;
        else
            return c1.getCli().compareTo(c2.getCli());
    }
 }

