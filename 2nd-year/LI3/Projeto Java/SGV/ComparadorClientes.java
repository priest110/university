import java.util.Comparator;
import java.io.*;

public class ComparadorClientes implements Comparator<ICliente>, Serializable {

    public int compare(ICliente c1, ICliente c2){
        return c1.getCodigo().compareTo(c2.getCodigo());
    }
 }

