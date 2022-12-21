import java.io.*;
import java.util.List;

/**
 * Escreva a descrição da interface IParCliQuantos aqui.
 * 
 * @author (seu nome) 
 * @version (número da versão ou data)
 */

public interface IParCliQuantos extends Serializable
{
    /**
     * Metodo que devolve o codigo de cliente do par
     *
     * @return        o codigo do cliente
     */
    public int getQuantos();
    
    /** Metodo que converte um cliente numa string
     * 
     * @return o produto convertido
     */
    public ICliente getCli();
    
    /**
     * Metodo que altera o nº de vezes que o cliente do par efetuou compras
     *
     * @param   q   o nº de vezes que o cliente do par efetuou compras
     */
    public void setQuantos(int q);
    
    /**
     * Metodo que altera o codigo de cliente do par
     *
     * @param  codigo      o codigo do cliente 
     */
    public void setCli(ICliente p);
    
    /** Metodo que converte um par de clientes numa string
     * 
     * @return o produto convertido
     */
    public String toString();
    
    /**
     * Metodo que duplica um ParCliQuantos
     * 
     * @return o clone
     */
    public ParCliQuantos clone();
    
    /**
     * Metodo que devolve a quantidade atual de determinado Produto de um Par de uma lista de pares
     * 
     * @param   total   Lista de pares
     * @param   l   Lista de Clientes
     * @param   x   Filial a analisar
     * 
     * @return      quantidade atual
     */
    public int quantidade_Atual(List<IParCliQuantos> total, List<ICliente> l , IFilialAux x);
    
    /**
     * Metodo que remove determinado Par de uma lista de pares
     * 
     * @param   total   Lista de pares
     * @param   l   Lista de Clientes
     * @param   x   Filial a analisar
     */
    public void remover(List<IParCliQuantos> total, List<ICliente> l , IFilialAux x);
    
    /**
     * Metodo que verifica se dois pares sao iguais
     * 
     * @param  o   o objeto a comparar
     * 
     * @return     o resultado da comparacao dos objetos
     */
    public boolean equals (Object o);
    
    /**
     * Metodo que determina o par com menor quantidade
     * 
     * @param  l   a lista a analisar
     * @return  o par desejado
     */
    public IParCliQuantos menorQuantidade(List<IParCliQuantos> l);
}
