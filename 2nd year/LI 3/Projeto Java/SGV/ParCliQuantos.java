import java.io.*;
import java.lang.Object;
import java.util.List;
import java.util.ArrayList;

/**
 * Escreva a descrição da classe parcliQuantos aqui.
 * 
 * @author (seu nome) 
 * @version (número de versão ou data)
 */
public class ParCliQuantos implements IParCliQuantos, Serializable{
    int quantos;
    ICliente cli;

    /**
     * Construtor para objetos da classe ParCliQuantos
     */
    public ParCliQuantos()
    {
        this.quantos = 0;
        this.cli = new Cliente();
    }
    
    /**
     * Construtor para objetos da classe ParCliQuantos(por parametrizacao)
     */
    public ParCliQuantos(int q, ICliente p){
        this.quantos = q;
        this.cli = p.clone();
    }
    
    /**
     * Construtor para objetos da classe ParCliQuantos (por copia)
     */
    public ParCliQuantos(ParCliQuantos p){
        this.quantos = p.getQuantos();
        this.cli = p.getCli();
    }
    
    /**
     * Metodo que devolve o codigo de cliente do par
     *
     * @return        o codigo do cliente
     */
    public int getQuantos(){
        return this.quantos;
    }
    
    /** Metodo que converte um cliente numa string
     * 
     * @return o produto convertido
     */
    public ICliente getCli(){
        return cli.clone();
    }
    
    /**
     * Metodo que altera o nº de vezes que o cliente do par efetuou compras
     *
     * @param   q   o nº de vezes que o cliente do par efetuou compras
     */
    public void setQuantos(int q){
        this.quantos = q;
    }
    
    /**
     * Metodo que altera o codigo de cliente do par
     *
     * @param  codigo      o codigo do cliente 
     */
    public void setCli(ICliente p){
        this.cli = p.clone();
    }
    
    /** Metodo que converte um par de clientes numa string
     * 
     * @return o produto convertido
     */
    public String toString(){
        return(this.cli.toString() + "Comprou " + this.quantos + " vezes.\n");
    }
    
    /**
     * Metodo que duplica um ParProdQuantos
     * 
     * @return o clone
     */
    public ParCliQuantos clone(){
        return new ParCliQuantos(this);
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
            IParCliQuantos aux = (ParCliQuantos) o;
            return (aux.getQuantos() == this.quantos && aux.getCli().equals(this.cli));
        }
    }
    
    /**
     * Metodo que determina o par com menor quantidade
     * 
     * @param  l   a lista a analisar
     * @return  o par desejado
     */
    public IParCliQuantos menorQuantidade(List<IParCliQuantos> l){
        IParCliQuantos par = new ParCliQuantos();
        int min = Integer.MAX_VALUE;
        for(IParCliQuantos p : l){
            if(min > p.getQuantos()){
                par = p.clone();
                min = p.getQuantos();
            }
        }
        return par;
    }
    
    /**
     * Metodo que devolve a quantidade atual de determinado Produto de um Par de uma lista de pares
     * 
     * @param   total   Lista de pares
     * @param   l   Lista de Clientes
     * @param   x   Filial a analisar
     * 
     * @return      quantidade atual
     */
    public int quantidade_Atual(List<IParCliQuantos> total, List<ICliente> l , IFilialAux x){
        return total.get(l.indexOf(x.getCodCli())).getQuantos();
    }
    
    /**
     * Metodo que remove determinado Par de uma lista de pares
     * 
     * @param   total   Lista de pares
     * @param   l   Lista de Clientes
     * @param   x   Filial a analisar
     */
    public void remover(List<IParCliQuantos> total, List<ICliente> l , IFilialAux x){
        total.remove(total.get(l.indexOf(x.getCodCli())));
    }
}

