import java.util.Comparator;

public class ComparadorDistancia implements Comparator<Cliente> {
    
    public int compare (Cliente c1, Cliente c2) {
        return (int) (c1.getAlugueres().size() - c2.getAlugueres().size());
    }
}
