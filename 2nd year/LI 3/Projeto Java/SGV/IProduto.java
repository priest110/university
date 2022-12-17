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

public interface IProduto extends Serializable{
    /**
     * Metodo que retorna o codigo de um produto
     * 
     * @return     o codigo do produto
     */
    public String getCodigo();
    
    /**
     * Metodo que retorna o codigo de um produto
     * 
     * @param  cod   o codigo do produto
     */
    public void setCodigo(String cod);
    
    /** Metodo que converte um produto numa string
     * 
     * @return o produto convertido
     */
    public String toString();
    
    /**
     * Metodo que verifica se dois produtos sao iguais
     * 
     * @param  o   o objeto
     * @return o resultado da comparacao
     */
    public boolean equals(Object o);
    
    /**
     * Metodo que duplica um produto
     * 
     * @return o clone
     */
    public IProduto clone ();
    
    /**
     * Metodo que calcula o hashcode de um produto
     * 
     * @return o hashcode
     */
    public int hashcode();
    
    /**
     * Metodo que implementa a ordem natural de comparacao de instancias de Produto
     */
    public int compareTo(IProduto c);
    
    /**
     * Metodo que verifica se um dado codigo e valido
     * 
     * @param   s   string a verificar
     * 
     * @return  o   resultado da observacao (true ou false)
     */
    public boolean prodValido(String s);
}
