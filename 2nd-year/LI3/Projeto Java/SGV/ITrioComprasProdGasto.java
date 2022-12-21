import java.io.*;
/**
 * Write a description of class ITrioComprasProdGasto here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public interface ITrioComprasProdGasto extends Serializable{
    /**
     * Metodo que devolve o array do numero de compras de um determinado trio
     *
     * @return        o nº de compras
     */
    public int[] getCompras();
    
    /**
     * Metodo que devolve o array do numero de produtos de um determinado trio
     *
     * @return        o nº de produtos
     */
    public int[] getProdutos();
    
    /**
     * Metodo que devolve o array dos gastos de um determinado trio
     *
     * @return        o nº de gastos
     */
    public double[] getGasto();
    
    /**
     * Metodo que junta um trio dado ao trio
     * 
     * @param   trio   o trio a juntar
     * @param   n   o tamanho dos arrays do trio dado 
     */
    public void fundeTrios(ITrioComprasProdGasto trio, int n);
}
