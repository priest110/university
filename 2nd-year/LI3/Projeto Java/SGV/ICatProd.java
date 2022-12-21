import java.util.*;
import java.io.*;
import static java.lang.System.out;
import java.util.stream.Collectors;
/**
 * Write a description of class ICatProd here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public interface ICatProd extends Serializable
{
    /**
     * Metodo que devolve o CatProd de um conjunto de CatProd
     * 
     * @return     o CatProd dos CatProd
     */
    public Set<IProduto> getCatProd();
    
    /**
     * Metodo que altera os CatProd de um conjunto de cados
     * 
     * @param  pro   o novo CatProd
     */
    public void setCatProd(Set<IProduto> pro);
    
    /**
     * Metodo que duplica os CatProd
     * 
     * @return   o clone dos CatProd
     */
    public CatProd clone();
    
    /**
     * Metodo que verifica se dois conjuntos de CatProd sao iguais
     * 
     * @param  o   o objeto a comparar
     * 
     * @return     o resultado da comparacao dos objetos
     */
    public boolean equals(Object o);
    
    /**
     * Metodo que converte um conjunto de CatProd para uma string
     * 
     * @return     o conjunto de CatProd em string
     */
    public String toString();
    
    /**
     * Metodo que devolve o codigo de hash para um produto
     * 
     * @return     o hashcode
     */
    public int hashCode();
    
    /**
     * Metodo que, dado um produto, o adiciona ao catalogo
     * 
     * @param   pro  o novo produto
     */
    public void addProduto(IProduto pro);
    
    /**
     * Metodo que verifica se um dado produto existe
     * 
     * @param   pro  o produto a verificar
     */
    public boolean existeProduto(IProduto pro);
    
    /**
     * Metodo que devolve o numero total de produtos existentes
     * 
     * @return  o numero de produtos
     */
    public int numeroProdutos();
}
