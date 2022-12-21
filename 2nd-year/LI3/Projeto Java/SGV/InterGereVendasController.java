import java.util.*;
import java.io.*;
import static java.lang.System.out;
import java.util.stream.Collectors;
/**
 * Escreva a descrição da interface InterGereVendasController aqui.
 * 
 * @author (seu nome) 
 * @version (número da versão ou data)
 */

public interface InterGereVendasController extends Serializable
{
    //primeira coisa que o controlador faz quando executado (funcao start) e mostrar o menu
    
    //a unica pergunta sobre clientes: existe ou nao
    
    public void setModel(InterGereVendasModel x);
    
    public void setView(InterGereVendasView y);
    
    public InterGereVendasModel getModel();
    
    public InterGereVendasView getView();
    
    public void startController();
    
    public void lerFicheiros();
    
    public void consultarQueries();
    
    /**
     * Metodo que inicializa um estado previamente guardado em ficheiro
     * 
     * @param   fich  o ficheiro onde esta o ficheiro guardado
     */
    public void abreEstadoGuardado();
    
    /**
     * Metodo que guarda um estado num ficheiro
     * 
     * @param   fich  o ficheiro onde e para guardar o ficheiro
     */
    public void guardaEstadoFicheiro();
    
}
