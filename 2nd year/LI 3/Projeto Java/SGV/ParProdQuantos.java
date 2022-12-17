import java.io.*;
import java.lang.Object;
import java.util.List;
import java.util.ArrayList;

/**
 * Escreva a descrição da classe parProdQuantos aqui.
 * 
 * @author (seu nome) 
 * @version (número de versão ou data)
 */
public class ParProdQuantos implements IParProdQuantos, Serializable{
    int quantos;
    IProduto prod;

    /**
     * Construtor para objetos da classe parProdQuantos
     */
    public ParProdQuantos()
    {
        this.quantos = 0;
        this.prod = new Produto();
    }

    public ParProdQuantos(int q, IProduto p){
        this.quantos = q;
        this.prod = p.clone();
    }
    
    public ParProdQuantos(ParProdQuantos p){
        this.quantos = p.getQuantos();
        this.prod = p.getProd();
    }
    
    /**
     * Metodo que devolve o codigo de produto do par
     *
     * @return        o codigo do produto 
     */
    public int getQuantos(){
        return this.quantos;
    }
    
    /** Metodo que converte um produto numa string
     * 
     * @return o produto convertido
     */
    public IProduto getProd(){
        return prod.clone();
    }
    
    /**
     * Metodo que altera o nº de vezes que o produto do par foi comprado
     *
     * @param   q   o nº de vezes que o produto do par foi comprado
     */
    public void setQuantos(int q){
        this.quantos = q;
    }
    
    /**
     * Metodo que altera o codigo de produto do par
     *
     * @param  codigo      o codigo do produto 
     */
    public void setProd(IProduto p){
        this.prod = p.clone();
    }
    
    /**
     * Metodo que duplica um ParProdQuantos
     * 
     * @return o clone
     */
    public ParProdQuantos clone(){
        return new ParProdQuantos(this);
    }
    
    /** Metodo que converte um par de produtos numa string
     * 
     * @return o produto convertido
     */
    public String toString(){
        return(this.prod.toString() + "Comprado " + this.quantos + " vezes.\n");
    }
    
    /**
     * Metodo que verifica se dois pares sao iguais
     * 
     * @param  o   o objeto a comparar
     * 
     * @return     o resultado da comparacao dos objetos
     */
    public boolean equals (Object o){
        if (o == this)
            return true;
        else if (o == null || o.getClass() != this.getClass())
            return false;
        else{
            IParProdQuantos aux = (ParProdQuantos) o;
            return (aux.getQuantos() == this.quantos && aux.getProd().equals(this.prod));
        }
    }
    
    /**
     * Metodo que devolve a quantidade atual de determinado Produto de um Par de uma lista de pares
     * 
     * @param   total   Lista de pares
     * @param   l   Lista de Produtos
     * @param   x   Filial a analisar
     * 
     * @return      quantidade atual
     */
    public int quantidade_Atual(List<IParProdQuantos> total, List<IProduto> l , IFilialAux x){
        return total.get(l.indexOf(x.getCodProd())).getQuantos();
    }
    
    /**
     * Metodo que remove determinado Par de uma lista de pares
     * 
     * @param   total   Lista de pares
     * @param   l   Lista de Produtos
     * @param   x   Filial a analisar
     */
    public void remover(List<IParProdQuantos> total, List<IProduto> l , IFilialAux x){
        total.remove(total.get(l.indexOf(x.getCodProd())));
    }
    
    /**
     * Metodo que determina o par com menor quantidade
     * 
     * @param  l   a lista a analisar
     * @return  o par desejado
     */
    public IParProdQuantos menorQuantidade(List<IParProdQuantos> l){
        IParProdQuantos par = new ParProdQuantos();
        int min = Integer.MAX_VALUE;
        for(IParProdQuantos p : l){
            if(min > p.getQuantos()){
                par = p.clone();
                min = p.getQuantos();
            }
        }
        return par;
    }
}
