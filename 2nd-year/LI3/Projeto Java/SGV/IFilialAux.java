import static java.lang.System.out;
import java.util.*;
import java.io.*;
/**
 * IFilialAux - interface das classes filial
 * 
 * @author (Ana Rita Rosendo, Goncalo Esteves, Rui Oliveira) 
 * @version 3 de maio de 2019
 */

public interface IFilialAux extends Serializable{
    /**
     * Metodo que devolve o codigo de produto da filial
     *
     * @return        o codigo do produto 
     */
    public IProduto getCodProd();
    
    /**
     * Metodo que devolve preco unitario de cada produto da filial
     *
     * @return        o preco
     */
    public double getPreco();
    
    /**
     * Metodo que devolve a quantidade de produtos da filial
     *
     * @return        o codigo do produto 
     */
    public int getQuantidade();
    
    /**
     * Metodo que devolve o tipo da filial
     *
     * @return        o tipo
     */
    public String getTipo();
    
    /**
     * Metodo que devolve o codigo de cliente da filial
     *
     * @return        o codigo do cliente
     */
    public ICliente getCodCli();
    
    /**
     * Metodo que devolve o mes da filial
     *
     * @return        o mes
     */
    public int getMes();
    
    /**
     * Metodo que altera o codigo de produto da filial
     *
     * @param  codigo      o codigo do produto 
     */
    public void setCodProd(IProduto codigo);
    
    /**
     * Metodo que altera o preco da filial
     *
     * @param   preco     o preco
     */
    public void setPreco(Double preco);
    
    /**
     * Metodo que altera a quantidade da filial
     *
     * @param  quantos     a quantidade da filial
     */
    public void setQuantidade(int quantos);
    
    /**
     * Metodo que altera o tipo da filial
     *
     * @param   tipo     o tipo
     */
    public void setTipo(String tipo);
    
    /**
     * Metodo que altera o codigo de cliente da filial
     *
     * @param    codigo    o codigo do cliente 
     */
    public void setCodCli(ICliente codigo);
    
    /**
     * Metodo que altera o mes da filial
     *
     * @param  mes     o mes da filial
     */
    public void setMes(int mes);
    
    /**
     * Metodo que converte uma filial numa string
     * 
     * @return a filial convertida
     */
    public String toString();
    
    /**
     * Metodo que verifica se duas filials sao iguais
     * 
     * @param  o   o objeto
     * @return o resultado da comparacao
     */
    public boolean equals(Object o);
    
    /**
     * Metodo que duplica uma filial
     * 
     * @return o clone
     */
    public IFilialAux clone ();
    
    /**
     * Metodo que calcula o hashcode de uma filial
     * 
     * @return o hashcode
     */
    public int hashcode();
    
    /**
     * Metodo que verifica se uma lista de Filiais tem algum Produto igual a p
     * 
     * @param   p   Produto a verificar
     * @param   x   Lista de Filiais
     * 
     * @return  true caso tenha, false caso contrario
     */
    public boolean contemProduto(IProduto p, List<IFilialAux> x);
    
    /**
     * Metodo que verifica se uma lista de Filiais tem algum Cliente igual a c
     * 
     * @param   p   Produto a verificar
     * @param   x   Lista de Filiais
     * 
     * @return  true caso tenha, false caso contrario
     */
    public boolean contemCliente(ICliente c, List<IFilialAux> x);
    
    /**
     * Metodo que devolve uma lista de Filiais so com produtos iguais
     * 
     * @param   p   Produto a verificar
     * @param   x   Lista de Filiais
     * 
     * @return  lista de Filiais
     */
    public List<IFilialAux> produtosIguais(IProduto p, List<IFilialAux> x);
}

