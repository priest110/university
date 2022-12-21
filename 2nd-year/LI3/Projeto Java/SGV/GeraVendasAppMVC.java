import java.util.*;
import java.io.*;
import static java.lang.System.out;
import java.util.stream.Collectors;
/**
 * Escreva a descrição da classe GeraVendasAppMVC aqui.
 * 
 * @author (seu nome) 
 * @version (número de versão ou data)
 */
public class GeraVendasAppMVC
{   
    public static void main(String[] args){
        InterGereVendasModel model = new GereVendasModel();
        model.carregarDados("Vendas_1M.txt");  //metodo que vai ler os ficheiros de dados e guardar no model
        
        if(model == null){
            out.println("Erro a ler os ficheiros.\n");
            System.exit(-1);
        }
        
        //ja temos a model inicializada, e precido inicializar o view
        
        InterGereVendasView view = new GereVendasView();
        
        //View vai ter um metodo que quando necessario vai ser chamado
        //para navegar nos ficheiros (e o navegador de C versao Java), ou seja,
        //o navegador esta na classe view
        
        InterGereVendasController control = new GereVendasController();
        
        control.setModel(model);
        control.setView(view);
        control.startController();
        //control.exit(0);
    }
}
