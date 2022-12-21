import java.util.*;
import java.io.*;
import static java.lang.System.out;
import java.util.stream.Collectors;
/**
 * InterGereVendasView - implementa um menu de texto
 * 
 * @author (Ana Rita Rosendo, Gonçalo Esteves, Rui Oliveira) 
 * @version 21 de maio de 2019
 */
public interface InterGereVendasView extends Serializable
{
    /**
     * Metodo que devolve as opcoes da InterGereVendasView
     * 
     * @return     as opcoes da InterGereVendasView
     */
    public List<String> getOpcoes();
    
    /**
     * Metodo que altera as opcoes da InterGereVendasView
     * 
     * @param   opcoes   as novas opcoes da InterGereVendasView
     */
    public void setOpcoes(String[] opcoes);
    
    /**
     * Metodo que duplica a InterGereVendasView
     * 
     * @return   o clone da InterGereVendasView
     */
    public GereVendasView clone();
     
    /**
     * Executa o menu inicial e invoca o método correspondente à opção seleccionada.
     */    
    public int menu_Inicial();
    
    /**
     * Executa o menu das queries e invoca o método correspondente à opção seleccionada.
     */    
    public int menu_Queries();
    
    /**
     * Executa o menu das estatisticas e invoca o método correspondente à opção seleccionada.
     */    
    public int menu_Estatisticas();
    
    /**
     * Executa o menu de ficheiros e invoca o método correspondente à opção seleccionada.
     */    
    public int menu_Ficheiros();
    
    /**
     * Mostra ao utilizador o resultado da query 1
     * 
     * @param   l   lista de produtos
     */  
    public void query1(List<IProduto> l);
    
    /**
     * Mostra ao utilizador o resultado da query 2
     * 
     * @param   mes   dado pelo utilizador
     * @param   total  array com todos os totais necessarios
     */  
    public void query2(int mes, int[] total);

    /**
     * Mostra ao utilizador o resultado da query 3
     * 
     * @param   t   trio dado pelo utilizador
     * @param   cliente    cliente dado pelo utilizador
     */  
    public void query3(ITrioComprasProdGasto t, String cliente);
    
    /**
     * Mostra ao utilizador o resultado da query 4
     * 
     * @param   t   trio dado pelo utilizador
     * @param   produto    cliente dado pelo utilizador
     */  
    public void query4(ITrioComprasProdGasto t, String produto);
    
    /**
     * Mostra ao utilizador o resultado da query 5
     * 
     * @param   p   par dado pelo utilizador
     */  
    public void query5(List<IParProdQuantos> p);
    
    /**
     * Mostra ao utilizador os pares de Produtos comprados e numero de clientes que o compraram 
     * 
     * @param  aux   a lista com os pares de produto/numero de clientes
     */
    public void query6(List<IParProdQuantos> aux);
    
    /**
     * Mostra ao utilizador o top 3 de clientes que mais dinheiro gastaram nas 3 filiais
     * 
     * @param lis  a lista com as informacoes
     */
    public void query7(List<Map<ICliente, Double>> lis);
    
    /**
     * Mosta ao utilizador o top X de clientes com mais produtos diferentes comprados
     * 
     * @param  lis   a lista com os pares cliente/numero de prods diferentes comprados
     */
    public void query8(List<IParCliQuantos> lis);
    
    /**
     * Mostra ao utilizador o resultado da query 9
     * 
     * @param   aux   par dado pelo utilizador
     */  
    public void query9(List<IParCliQuantos> aux); 
    
    /**
     * Mostra ao utilizador o resultado da query10
     * 
     * @param  aux   a lista com os 3 maps (um por filial) com os codigos de produto associados ao total faturado por mes
     * @param  s   o set com as todos os produtos existentes
     */
    public void query10(List<Map<IProduto, double[]>> aux, List<IProduto> s);
    
    /**
     * Mostra ao utilizador as estatisticas, por mes, de total faturado, numero total de compras e numero de clientes que efetuaram compras
     * 
     * @param  trio   lista com os tres trios com as informacoes
     */
    public void estatisticas2(List<ITrioComprasProdGasto> trio, int n);
    
    /**
     * Mostra ao utilizador as informacoes guardadas nas estruturas;
     * 
     * @param  s   a string com as informacoes
     */
    public void estatisticas1(String s);
}
