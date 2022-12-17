import java.util.*;
import java.io.*;
import static java.lang.System.out;
import java.util.stream.Collectors;

/**
 * Write a description of class CatProd here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class CatProd implements ICatProd, Serializable
{
    private Set<IProduto> catProd;
    
    /**
     * Construtor para objetos da classe CatProd (por omissao)
     */
    public CatProd(){
        this.catProd  = new TreeSet<IProduto>(new ComparadorProdutos());
    }
    
    /**
     * Construtor para objetos da classe CatProd (parameterizado)
     * 
     * @param  pro   os CatProd
     */
    public  CatProd(Set<IProduto> pro){
        this.setCatProd(pro);
    }
    
    /**
     * Construtor para objetos da classe CatProd (de copia)
     * 
     * @param  d   o CatProd
     */
    public CatProd (CatProd c){
        this.catProd = c.getCatProd();
    }
    
    /**
     * Metodo que devolve o CatProd de um conjunto de CatProd
     * 
     * @return     o CatProd dos CatProd
     */
    public Set<IProduto> getCatProd(){
        Set<IProduto> aux = new TreeSet<IProduto>(new ComparadorProdutos());
        for(IProduto c : this.catProd){
            aux.add(c.clone());
        }
        return aux;
    }
    
    /**
     * Metodo que altera os CatProd de um conjunto de cados
     * 
     * @param  pro   o novo CatProd
     */
    public void setCatProd(Set<IProduto> pro){
        this.catProd = new TreeSet<>(new ComparadorProdutos());
        for(IProduto pr : pro)
            this.catProd.add(pr.clone());
    }
    
    /**
     * Metodo que duplica os CatProd
     * 
     * @return   o clone dos CatProd
     */
    public CatProd clone(){
        return new CatProd(this);
    }
    
    /**
     * Metodo que verifica se dois conjuntos de CatProd sao iguais
     * 
     * @param  o   o objeto a comparar
     * 
     * @return     o resultado da comparacao dos objetos
     */
    public boolean equals(Object o){
        if(o == this)
            return true;
        if(o == null || o.getClass() != this.getClass())
            return false;
        else{
            CatProd d = (CatProd) o;
            return(this.catProd.equals(d.getCatProd()));
        }
    }
    
    /**
     * Metodo que converte um conjunto de CatProd para uma string
     * 
     * @return     o conjunto de CatProd em string
     */
    public String toString(){
        String aux = new String();
        for(IProduto c : this.catProd)
            aux += c.toString();
        return aux;
    }
    
    /**
     * Metodo que devolve o codigo de hash para um produto
     * 
     * @return     o hashcode
     */
    public int hashCode(){
        int hash = 7; 
        for(IProduto c : this.catProd)
            hash = 31 * hash + c.hashCode();
        return hash;        
    }
    
    /**
     * Metodo que, dado um produto, o adiciona ao catalogo
     * 
     * @param   pro  o novo produto
     */
    public void addProduto(IProduto pro){
        this.catProd.add(pro.clone());
    }
    
    /**
     * Metodo que verifica se um dado produto existe
     * 
     * @param   pro  o produto a verificar
     */
    public boolean existeProduto(IProduto pro){
        return this.catProd.contains(pro);
    }
    
    /**
     * Metodo que devolve o numero total de produtos existentes
     * 
     * @return  o numero de produtos
     */
    public int numeroProdutos(){
        return this.catProd.size();
    }
}

