import java.util.*;
import java.io.*;
import static java.lang.System.out;
import java.util.stream.Collectors;

/**
 * IFatura - interface das classes fatura
 * 
 * @author (Ana Rita Rosendo, Goncalo Esteves, Rui Oliveira) 
 * @version 3 de maio de 2019
 */
public interface IFatura extends Serializable
{
    /**
     * Metodo que devolve o codigo de produto da fatura
     *
     * @return        o codigo do produto 
     */
    public IProduto getCodProd();
    
    /**
     * Metodo que devolve preco unitario de cada produto da fatura
     *
     * @return        o preco
     */
    public double getPreco();
    
    /**
     * Metodo que devolve a quantidade de produtos da fatura
     *
     * @return        o codigo do produto 
     */
    public int getQuantidade();
    
    /**
     * Metodo que devolve o tipo da fatura
     *
     * @return        o tipo
     */
    public String getTipo();
   
    /**
     * Metodo que altera o codigo de produto da fatura
     *
     * @param  codigo      o codigo do produto 
     */
    public void setCodProd(IProduto codigo);
    
    /**
     * Metodo que altera o preco da fatura
     *
     * @param   preco     o preco
     */
    public void setPreco(Double preco);
    
    /**
     * Metodo que altera a quantidade da fatura
     *
     * @param  quantos     a quantidade da fatura
     */
    public void setQuantidade(int quantos);
    
    /**
     * Metodo que altera o tipo da fatura
     *
     * @param   tipo     o tipo
     */
    public void setTipo(String tipo);
    
    /**
     * Metodo que converte uma fatura numa string
     * 
     * @return a fatura convertida
     */
    public String toString();
    
    /**
     * Metodo que verifica se duas faturas sao iguais
     * 
     * @param  o   o objeto
     * @return o resultado da comparacao
     */
    public boolean equals(Object o);
    
    /**
     * Metodo que duplica uma fatura
     * 
     * @return o clone
     */
    public IFatura clone ();
    
    /**
     * Metodo que calcula o hashcode de uma fatura
     * 
     * @return o hashcode
     */
    public int hashcode();
    
    /**
     * Metodo que implementa a ordem natural de comparacao de instancias de Produto
     */
    public int compareTo(IFatura f);
}
