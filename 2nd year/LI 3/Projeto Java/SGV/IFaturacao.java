import java.util.*;
import java.io.*;
import static java.lang.System.out;
import java.util.stream.Collectors;
import java.util.AbstractMap.SimpleEntry;
/**
 * Write a description of class IFaturacao here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public interface IFaturacao extends Serializable
{
    /**
     * Metodo que devolve a faturacao de um conjunto de dados
     * 
     * @return     a faturacao dos dados
     */
    public Map<IProduto, List<IFatura>> getFaturacao();
    
    /**
     * Metodo que altera a faturacao de um conjunto de cados
     * 
     * @param  fat   a nova faturacao
     */
    public void setFaturacao(Map<IProduto, List<IFatura>> fat);
    
    /**
     * Metodo que duplica os Faturacao
     * 
     * @return   o clone dos Faturacao
     */
    public Faturacao clone();
    
    /**
     * Metodo que verifica se dois conjuntos de Faturacao sao iguais
     * 
     * @param  o   o objeto a comparar
     * 
     * @return     o resultado da comparacao dos objetos
     */
    public boolean equals(Object o);
    
    /**
     * Metodo que converte um conjunto de Faturacao para uma string
     * 
     * @return     o conjunto de Faturacao em string
     */
    public String toString();
    
    /**
     * Metodo que devolve o codigo de hash para um proprietario
     * 
     * @return     o hashcode
     */
    public int hashCode();
    
    /**
     * Metodo que insere uma Fatura numa Arvore de Faturas
     * 
     * @param   fatt    Arvore de Faturas
     * @param   key     Chave da fatura a inserir
     * @param   value   Fatura a inserir
     * 
     * @return  IFilialAux caso seja convertida, null caso contrario
     */
    public void addValues(IProduto key, IFatura value);
    
    /**
     * Metodo que devolve a lista de faturas de determinado Produto
     * 
     * @return   lista de faturas
     */
    public List<IFatura> determinadaFaturas(IProduto p);
    
    /**
     * Metodo que verifica se um dado produto foi comprado
     * 
     * @param   pro  o produto a verificar
     */
    public boolean existeFatura(IProduto pro);
    
    /**
     * Metodo que devolve o top de X produtos mais vendidos
     * 
     * @param  x   o numero de produtos que pretendemos obter
     * 
     * @return  a lista com os produtos
     */
    public List<IParProdQuantos> topXMaisVendidos(int x);
    
    /**
     * Metodo que devolve todos os produtos que possuem vendas
     * 
     * @return  o numero de produtos
     */
    public int prodsVendidos();
    
    /**
     * Metodo que devolve o numero total de vendas de valor 0 e a faturacao total
     */
    public SimpleEntry<Integer, Double> comprasA0EFaturacaoTotal();
}
