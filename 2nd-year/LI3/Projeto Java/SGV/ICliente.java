import java.util.*;
import java.io.*;
import static java.lang.System.out;
import java.util.stream.Collectors;

/**
 * Produto - interface das classe produto
 * 
 * @author (Ana Rita Rosendo, Goncalo Esteves, Rui Oliveira) 
 * @version 3 de maio de 2019
 */

public interface ICliente extends Serializable
{
    /**
     * Metodo que retorna o codigo de um cliente
     * 
     * @return     o codigo do cliente
     */
    public String getCodigo();
    
    /**
     * Metodo que retorna o codigo de um cliente
     * 
     * @param  cod   o codigo do cliente
     */
    public void setCodigo(String cod);
    
    /** Metodo que converte um cliente numa string
     * 
     * @return o cliente convertido
     */
    public String toString();
    
    /**
     * Metodo que verifica se dois clientes sao iguais
     * 
     * @param  o   o objeto
     * @return o resultado da comparacao
     */
    public boolean equals(Object o);
    
    /**
     * Metodo que duplica um cliente
     * 
     * @return o clone
     */
    public ICliente clone ();
    
    /**
     * Metodo que calcula o hashcode de um cliente
     * 
     * @return o hashcode
     */
    public int hashcode();
    
    /**
     * Metodo que implementa a ordem natural de comparacao de instancias de Cliente
     */
    public int compareTo(ICliente c);
    
    /**
     * Metodo que verifica se um dado codigo e valido
     * 
     * @return o resultado da observacao (true ou false)
     */
    public boolean cliValido(String s);
    
}
