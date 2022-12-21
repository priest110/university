import java.io.*;
import java.util.*;
/**
 * Escreva a descrição da interface IParProdQuantos aqui.
 * 
 * @author (seu nome) 
 * @version (número da versão ou data)
 */

public interface IParProdQuantos extends Serializable
{
    /**
     * Metodo que devolve o codigo de produto do par
     *
     * @return        o codigo do produto 
     */
    public int getQuantos();
    
    /** Metodo que converte um produto numa string
     * 
     * @return o produto convertido
     */
    public IProduto getProd();
    
    /**
     * Metodo que altera o nº de vezes que o produto do par foi comprado
     *
     * @param   q   o nº de vezes que o produto do par foi comprado
     */
    public void setQuantos(int q);
    
    /**
     * Metodo que altera o codigo de produto do par
     *
     * @param  codigo      o codigo do produto 
     */
    public void setProd(IProduto p);
    
    /**
     * Metodo que duplica um ParProdQuantos
     * 
     * @return o clone
     */
    public ParProdQuantos clone();
    
    /** Metodo que converte um par de produtos numa string
     * 
     * @return o produto convertido
     */
    public String toString();
    
    /**
     * Metodo que verifica se dois pares sao iguais
     * 
     * @param  o   o objeto a comparar
     * 
     * @return     o resultado da comparacao dos objetos
     */
    public boolean equals (Object o);
    
    public int quantidade_Atual(List<IParProdQuantos> total, List<IProduto> l , IFilialAux x);
    
    public void remover(List<IParProdQuantos> total, List<IProduto> l , IFilialAux x);
    
    /**
     * Metodo que determina o par com menor quantidade
     * 
     * @param  l   a lista a analisar
     * @return  o par desejado
     */
    public IParProdQuantos menorQuantidade(List<IParProdQuantos> l);
}
