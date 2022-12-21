import java.util.Comparator;
import java.io.*;

public class ComparadorProdutos implements Comparator<IProduto>, Serializable {

    public int compare(IProduto p1, IProduto p2){
        return p1.getCodigo().compareTo(p2.getCodigo());
    }
 }
