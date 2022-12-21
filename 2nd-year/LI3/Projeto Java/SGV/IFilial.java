import static java.lang.System.out;
import java.util.*;
import java.io.*;
import java.util.AbstractMap.SimpleEntry;
/**
 * Write a description of class IFIliais here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public interface IFilial extends Serializable
{
    /**
     * Metodo que devolve a filial1 de um conjunto de dados
     * 
     * @return     a filial1 dos dados
     */
    public Map<ICliente, List<IFilialAux>> getFilial();
    
    /**
     * Metodo que altera a filial1 de um conjunto de cados
     * 
     * @param  f   a nova filial1
     */
    public void setFilial(Map<ICliente, List<IFilialAux>> f);
    
    /**
     * Metodo que duplica os Filial
     * 
     * @return   o clone dos Filial
     */
    public Filial clone();
    
    /**
     * Metodo que verifica se dois conjuntos de Filial sao iguais
     * 
     * @param  o   o objeto a comparar
     * 
     * @return     o resultado da comparacao dos objetos
     */
    public boolean equals(Object o);
    
    /**
     * Metodo que converte um conjunto de Filial para uma string
     * 
     * @return     o conjunto de Filial em string
     */
    public String toString();
    
    /**
     * Metodo que devolve o codigo de hash para um proprietario
     * 
     * @return     o hashcode
     */
    public int hashCode();
    
    /**
     * Metodo que insere uma Filial numa Arvore de Filiais
     * 
     * @param   filiais    Arvore de Filiais
     * @param   key     Chave da fatura a inserir
     * @param   value   Filial a inserir
     * 
     * @return  IFilialAux caso seja convertida, null caso contrario
     */
    public void addValues(ICliente key, IFilialAux value);
    
    /**
     * Metodo que devolve a lista de filiais de determinado CLiente
     * 
     * @return   lista de Filiais
     */
    public List<IFilialAux> determinadaFilial(ICliente c);
    
    /**
     * Metodo que, dado um mes, calcula o total de vendas realizadas nesse mes e os clientes que as realizaram
     * 
     * @param  mes   mes a analisar
     * @return o total de vendas e os clientes que as realizaram
     */
    public SimpleEntry<Integer, List<ICliente>> vendasPorMes(int mes);
    
    /**
     * Metodo que devolve uma lista com todos os clientes que compraram um determinado produto
     * 
     * @param  pro   o produto a ser analisado
     * @return  a lista com todos os clientes que compraram o produto
     */
    public List<ICliente> todosCliComprProd(IProduto pro);
    
    /**
     * Metodo que devolve um map com os 3 maiores compradores na filial (em termos de dinheiro faturado)
     * 
     * @return  o map com os 3 maiores compradores
     */
    public Map<ICliente, Double> getTop3();
    
    /**
     * Metodo que devolve o cliente que gastou menos dinheiro, dos guardados num map
     * 
     * @param  map   o mapa a analisar
     * @return o cliente
     */
    public ICliente cliMenosGasto(Map<ICliente, Double> map);
    
    /**
     * Metodo que devolve uma lista com todos os diferentes produtos que um dado cliente comprou
     * @param  cli   o cliente a analisar
     * @return  a lista com os produtos que comprou
     */
    public List<IProduto> numeroProdsDiferentes(ICliente cli);
    
    /** 
     * Metodo que devolve um triplo com as informacoes relativas ao numero total de compras, faturacao e numero de clientes distintos, por mes
     * 
     * @return  o triplo com as informacoes
     */
    public ITrioComprasProdGasto determinaEstatisticas();
    
    /**
     * Metodo que devolve todos os clientes que compraram na filial
     * 
     * @return a lista com os clientes
     */
    public Set<ICliente> clientesCompradores();
    
    /**
     * Metodo que determina o map com todos os produtos comprados na filial, associados ao total faturado
     * 
     * @return  o map com os produtos associados aos totais
     */
    public Map<IProduto, double[]> todosProdsEFat();
}
