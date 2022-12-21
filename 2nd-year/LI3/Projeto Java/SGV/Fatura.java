import static java.lang.System.out;
import java.util.*;
import java.io.*;
/**
 * Fatura - classe que guarda a informacao de uma Fatura
 * 
 * @author (Ana Rita Rosendo, Goncalo Esteves, Rui Oliveira) 
 * @version 3 de maio de 2019
 */
public class Fatura implements IFatura, Serializable{
    IProduto codProd;
    double preco;
    int quantidade;
    String tipo;
    
    /**
     * Construtor para objetos da classe Fatura (por omissao)
     */
    public Fatura(){
        this.codProd = new Produto();
        this.preco = 0;
        this.quantidade = 0;
        this.tipo = "n/a";
    }
    
    /**
     * Construtor para objetos da classe Fatura (por parametrizacao)
     */
    public Fatura(IProduto prod, double pre, int quant, String t){
        this.setCodProd(prod);
        this.setPreco(pre);
        this.setQuantidade(quant);
        this.setTipo(t);
    }
    
    /**
     * Construtor para objetos da classe Fatura (por copia)
     */
    public Fatura(Fatura v){
        this.codProd = v.getCodProd();
        this.preco = v.getPreco();
        this.quantidade = v.getQuantidade();
        this.tipo = v.getTipo();
    }
    
    /**
     * Metodo que devolve o codigo de produto da Fatura
     *
     * @return        o codigo do produto 
     */
    public IProduto getCodProd(){
        return this.codProd.clone();
    }
    
    /**
     * Metodo que devolve preco unitario de cada produto da Fatura
     *
     * @return        o preco
     */
    public double getPreco(){
        return this.preco;
    }
    
    /**
     * Metodo que devolve a quantidade de produtos da Fatura
     *
     * @return        o codigo do produto 
     */
    public int getQuantidade(){
        return this.quantidade;
    }
    
    /**
     * Metodo que devolve o tipo da Fatura
     *
     * @return        o tipo
     */
    public String getTipo(){
        return this.tipo;
    }
    
    /**
     * Metodo que altera o codigo de produto da Fatura
     *
     * @param  codigo      o codigo do produto 
     */
    public void setCodProd(IProduto codigo){
        this.codProd = codigo.clone();
    }
    
    /**
     * Metodo que altera o preco da Fatura
     *
     * @param   preco     o preco
     */
    public void setPreco(Double p){
        this.preco = p;
    }
    
    /**
     * Metodo que altera a quantidade da Fatura
     *
     * @param  quantos     a quantidade da Fatura
     */
    public void setQuantidade(int quantos){
        this.quantidade = quantos;
    }
    
    /**
     * Metodo que altera o tipo da Fatura
     *
     * @param   tipo     o tipo
     */
    public void setTipo(String t){
        this.tipo = t;
    }
    
    /**
     * Metodo que converte uma Fatura numa string
     * 
     * @return a Fatura convertida
     */
    public String toString(){
        return ("Codigo do Produto: " + this.codProd.toString() + ";\n"
                + "Preco: " + this.preco + ";\n"
                + "Quantidade: " + this.quantidade + ";\n"
                + "Tipo de Fatura: " + this.tipo + ";\n");
    }
    
    /**
     * Metodo que verifica se duas Faturas sao iguais
     * 
     * @param  Fatura   a Fatura a comparar
     * @return o resultado da comparacao
     */
    public boolean equals(Object o){
        if(o == null || o.getClass() != this.getClass())
            return false;
        
        if(o == this)
            return true;
        
        else{
            Fatura v = (Fatura) o;
            return(this.codProd.equals(v.getCodProd())
                   && this.preco == v.getPreco()
                   && this.quantidade == v.getQuantidade()
                   && this.tipo.equals(v.getTipo()));
        }
    }
    
    /**
     * Metodo que duplica uma Fatura
     * 
     * @return o clone
     */
    public Fatura clone (){
        return new Fatura(this);
    }
    
    /**
     * Metodo que calcula o hashcode de uma Fatura
     * 
     * @return o hashcode
     */
    public int hashcode(){
        int hash = 7; 
        hash = 31 * hash + this.codProd.hashCode();
        hash = 31 * hash + this.quantidade;
        hash = 31 * hash + this.tipo.hashCode();
        long aux = Double.doubleToLongBits(this.preco);
        hash = 31 * hash + (int)(aux ^ (aux >>> 32));
        return hash;
    }
    
    /**
     * Metodo que implementa a ordem natural de comparacao de instancias de Produto
     */
    public int compareTo(IFatura f){
        return f.getCodProd().compareTo(this.codProd);
    }
}

