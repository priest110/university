import static java.lang.System.out;
import java.util.*;
import java.io.*;
/**
 * IVenda - interface das classes venda
 * 
 * @author (Ana Rita Rosendo, Goncalo Esteves, Rui Oliveira) 
 * @version 3 de maio de 2019
 */

public interface IVenda extends Serializable{
    /**
     * Metodo que devolve o codigo de produto da venda
     *
     * @return        o codigo do produto 
     */
    public IProduto getCodProd();
    
    /**
     * Metodo que devolve preco unitario de cada produto da venda
     *
     * @return        o preco
     */
    public double getPreco();
    
    /**
     * Metodo que devolve a quantidade de produtos da venda
     *
     * @return        o codigo do produto 
     */
    public int getQuantidade();
    
    /**
     * Metodo que devolve o tipo da venda
     *
     * @return        o tipo
     */
    public String getTipo();
    
    /**
     * Metodo que devolve o codigo de cliente da venda
     *
     * @return        o codigo do cliente
     */
    public ICliente getCodCli();
    
    /**
     * Metodo que devolve o mes da venda
     *
     * @return        o mes
     */
    public int getMes();
    
    /**
     * Metodo que devolve a filial da venda
     *
     * @return        a filial
     */
    public int getFilial();
    
    /**
     * Metodo que altera o codigo de produto da venda
     *
     * @param  codigo      o codigo do produto 
     */
    public void setCodProd(IProduto codigo);
    
    /**
     * Metodo que altera o preco da venda
     *
     * @param   preco     o preco
     */
    public void setPreco(Double preco);
    
    /**
     * Metodo que altera a quantidade da venda
     *
     * @param  quantos     a quantidade da venda
     */
    public void setQuantidade(int quantos);
    
    /**
     * Metodo que altera o tipo da venda
     *
     * @param   tipo     o tipo
     */
    public void setTipo(String tipo);
    
    /**
     * Metodo que altera o codigo de cliente da venda
     *
     * @param    codigo    o codigo do cliente 
     */
    public void setCodCli(ICliente codigo);
    
    /**
     * Metodo que altera o mes da venda
     *
     * @param  mes     o mes da venda
     */
    public void setMes(int mes);
    
    /**
     * Metodo que altera a filial da venda
     *
     * @param  filial     a filial da venda
     */
    public void setFilial(int filial);
    
    /**
     * Metodo que converte uma venda numa string
     * 
     * @return a venda convertida
     */
    public String toString();
    
    /**
     * Metodo que verifica se duas vendas sao iguais
     * 
     * @param  o   o objeto
     * @return o resultado da comparacao
     */
    public boolean equals(Object o);
    
    /**
     * Metodo que duplica uma venda
     * 
     * @return o clone
     */
    public IVenda clone ();
    
    /**
     * Metodo que calcula o hashcode de uma venda
     * 
     * @return o hashcode
     */
    public int hashcode();
    
    /**
     * Metodo que verifica se uma dada venda e valida
     * 
     * @param   linha   que vai ser convertida em IVenda
     * @param   catCli    clientes
     * @param   catProd    produtos
     * 
     * @return  IVenda caso seja valida, null caso contrario
     */
    public IVenda linhaToVenda(String linha, ICatCli catCli, ICatProd catProd);
    
    /**
     * Metodo que converte uma IVenda numa IFatura
     * 
     * @param   v   IVenda a converter
     * 
     * @return  IFatura caso seja convertida, null caso contrario
     */
    public IFatura vendaToFatura();
    
    /**
     * Metodo que converte uma IVenda numa IFilialAux
     * 
     * @param   v   IVenda a converter
     * 
     * @return  IFilialAux caso seja convertida, null caso contrario
     */
    public IFilialAux vendaToFilial();
}
