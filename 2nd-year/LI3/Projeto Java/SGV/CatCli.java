import java.util.*;
import java.io.*;
import static java.lang.System.out;
import java.util.stream.Collectors;

/**
 * Write a description of class CatCli here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class CatCli implements ICatCli, Serializable
{
    private Set<ICliente> catCli;
    
    /**
     * Construtor para objetos da classe CatCli (por omissao)
     */
    public CatCli(){
        this.catCli  = new TreeSet<ICliente>(new ComparadorClientes());
    }
    
    /**
     * Construtor para objetos da classe CatCli (parameterizado)
     * 
     * @param  cli   os catCli
     */
    public  CatCli(Set<ICliente> cli){
        this.setCatCli(cli);
    }
    
    /**
     * Construtor para objetos da classe CatCli (de copia)
     * 
     * @param  d   o CatCli
     */
    public CatCli (CatCli c){
        this.catCli = c.getCatCli();
    }
    
    /**
     * Metodo que devolve o catCli de um conjunto de CatCli
     * 
     * @return     o catCli dos CatCli
     */
    public Set<ICliente> getCatCli(){
        Set<ICliente> aux = new TreeSet<ICliente>(new ComparadorClientes());
        for(ICliente c : this.catCli){
            aux.add(c.clone());
        }
        return aux;
    }
    
    /**
     * Metodo que altera os catCli de um conjunto de cados
     * 
     * @param  cli   o novo catCli
     */
    public void setCatCli(Set<ICliente> cli){
        this.catCli = new TreeSet<>(new ComparadorClientes());
        for(ICliente cl : cli)
            this.catCli.add(cl.clone());
    }
    
    /**
     * Metodo que duplica os CatCli
     * 
     * @return   o clone dos CatCli
     */
    public CatCli clone(){
        return new CatCli(this);
    }
    
    /**
     * Metodo que verifica se dois conjuntos de CatCli sao iguais
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
            CatCli d = (CatCli) o;
            return(this.catCli.equals(d.getCatCli()));
        }
    }
    
    /**
     * Metodo que converte um conjunto de CatCli para uma string
     * 
     * @return     o conjunto de CatCli em string
     */
    public String toString(){
        String aux = new String();
        for(ICliente c : this.catCli)
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
        for(ICliente c : this.catCli)
            hash = 31 * hash + c.hashCode();
        return hash;        
    }
    
    /**
     * Metodo que, dado um cliente, o adiciona ao catalogo
     * 
     * @param   cli  o novo cliente
     */
    public void addCliente(ICliente cli){
        this.catCli.add(cli.clone());
    }
    
    /**
     * Metodo que verifica se um dado cliente existe
     * 
     * @param   cli  o cliente a verificar
     */
    public boolean existeCliente(ICliente cli){
        return this.catCli.contains(cli);
    }
    
    /**
     * Metodo que devolve o numero de clientes registados
     * 
     * @return  o numero de clientes
     */
    public int numeroClientes(){
        return this.catCli.size();
    }
}
