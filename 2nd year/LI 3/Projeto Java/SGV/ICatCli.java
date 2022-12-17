import java.util.*;
import java.io.*;
import static java.lang.System.out;
import java.util.stream.Collectors;

/**
 * Write a description of class ICatCli here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public interface ICatCli extends Serializable
{
    /**
     * Metodo que devolve o catCli de um conjunto de CatCli
     * 
     * @return     o catCli dos CatCli
     */
    public Set<ICliente> getCatCli();
    
    /**
     * Metodo que altera os catCli de um conjunto de cados
     * 
     * @param  cli   o novo catCli
     */
    public void setCatCli(Set<ICliente> cli);
    
    /**
     * Metodo que duplica os CatCli
     * 
     * @return   o clone dos CatCli
     */
    public CatCli clone();
    
    /**
     * Metodo que verifica se dois conjuntos de CatCli sao iguais
     * 
     * @param  o   o objeto a comparar
     * 
     * @return     o resultado da comparacao dos objetos
     */
    public boolean equals(Object o);
    
    /**
     * Metodo que converte um conjunto de CatCli para uma string
     * 
     * @return     o conjunto de CatCli em string
     */
    public String toString();
    
    /**
     * Metodo que devolve o codigo de hash para um produto
     * 
     * @return     o hashcode
     */
    public int hashCode();
    
    /**
     * Metodo que, dado um cliente, o adiciona ao catalogo
     * 
     * @param   cli  o novo cliente
     */
    public void addCliente(ICliente cli);
    
    /**
     * Metodo que verifica se um dado cliente existe
     * 
     * @param   cli  o cliente a verificar
     */
    public boolean existeCliente(ICliente cli);
    
    /**
     * Metodo que devolve o numero de clientes registados
     * 
     * @return  o numero de clientes
     */
    public int numeroClientes();
}
