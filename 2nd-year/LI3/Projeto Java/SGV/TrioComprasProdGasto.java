import java.io.*;

/**
 * Write a description of class TrioComprasProdGasto here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class TrioComprasProdGasto implements Serializable, ITrioComprasProdGasto
{
    private int[] compras;
    private int[] produtos;
    private double[] gasto;
    
    /**
     * Construtor para objetos da classe TrioComprasProdGasto (por parametrizacao)
     */
    public TrioComprasProdGasto(int[] compras, int[] produtos, double[] gasto){
        this.compras = compras.clone();
        this.produtos = produtos.clone();
        this.gasto = gasto.clone();
    }
    
    /**
     * Metodo que devolve o array do numero de compras de um determinado trio
     *
     * @return        o nº de compras
     */
    public int[] getCompras(){
        return this.compras;
    }
    
    /**
     * Metodo que devolve o array do numero de produtos de um determinado trio
     *
     * @return        o nº de produtos
     */
    public int[] getProdutos(){
        return this.produtos;
    }
    
    /**
     * Metodo que devolve o array dos gastos de um determinado trio
     *
     * @return        o nº de gastos
     */
    public double[] getGasto(){
        return this.gasto;
    }
    
    /**
     * Metodo que junta um trio dado ao trio
     * 
     * @param   trio   o trio a juntar
     * @param   n   o tamanho dos arrays do trio dado 
     */
    public void fundeTrios(ITrioComprasProdGasto trio, int n){
        int[] com = trio.getCompras();
        int[] pro = trio.getProdutos();
        double[] gas = trio.getGasto();
        
        for(int i = 0; i < n; i++){
            this.compras[i] += com[i];
            this.produtos[i] += pro[i];
            this.gasto[i] += gas[i];
        }
    }
}
