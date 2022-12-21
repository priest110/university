import java.util.*;
import java.io.*;
import static java.lang.System.out;
import java.util.stream.Collectors;

/**
 * Produto - classe que guarda a informacao de um produto
 * 
 * @author (Ana Rita Rosendo, Goncalo Esteves, Rui Oliveira) 
 * @version 3 de maio de 2019
 */
public class Produto implements IProduto, Serializable{
    private String codigo;

    /**
     * Construtor para objetos da classe Produto (por omissao)
     */
    public Produto(){
        this.codigo = "n/a";
    }
    
    /**
     * Construtor para objetos da classe Produto (por omissao)
     * 
     * @param  cod   o codigo do produto
     */
    public Produto(String cod){
        this.setCodigo(cod);
    }
    
    /**
     * Construtor para objetos da classe Produto (por copia)
     * 
     * @param  p   o produto a copiar
     */
    public Produto(Produto p){
        this.codigo = p.getCodigo();
    }

    /**
     * Metodo que retorna o codigo de um produto
     * 
     * @return     o codigo do produto
     */
    public String getCodigo(){
        return this.codigo;
    }
    
    /**
     * Metodo que retorna o codigo de um produto
     * 
     * @param  cod   o codigo do produto
     */
    public void setCodigo(String cod){
        this.codigo = cod;
    }
    
    /** Metodo que converte um produto numa string
     * 
     * @return o produto convertido
     */
    public String toString(){
        return ("Codigo do Produto: " + this.codigo + ";\n");
    }
    
    /**
     * Metodo que verifica se dois produtos sao iguais
     * 
     * @param  venda   o produto
     * @return o resultado da comparacao
     */
    public boolean equals(Object o){
        if(o == null || o.getClass() != this.getClass())
            return false;
        
        if(o == this)
            return true;
        
        else{
            Produto p = (Produto) o;
            return(this.codigo.equals(p.getCodigo()));
        }
    }
    
    /**
     * Metodo que duplica um produto
     * 
     * @return o clone
     */
    public Produto clone (){
        return new Produto(this);
    }
    
    /**
     * Metodo que calcula o hashcode de um produto
     * 
     * @return o hashcode
     */
    public int hashcode(){
        int hash = 7; 
        hash = 31 * hash + this.codigo.hashCode();
        return hash;
    }
    
    /**
     * Metodo que implementa a ordem natural de comparacao de instancias de Produto
     */
    public int compareTo(IProduto c){
        return c.getCodigo().compareTo(this.codigo);
    }
    
    /**
     * Metodo que verifica se um dado codigo e valido
     * 
     * @param   s   string a verificar
     * 
     * @return  o   resultado da observacao (true ou false)
     */
    public boolean prodValido(String s){
        char a;
        
        if(s.length() != 6)
            return false;
            
        a = s.charAt(0);       
        if(a < 'A' || a > 'Z')
            return false;
            
        a = s.charAt(1);       
        if(a < 'A' || a > 'Z')
            return false;
        
        a = s.charAt(2);
        if(a < '1' || a > '9')
            return false;
            
        for(int i = 3; i < 6; i++){
            a = s.charAt(i);
            if(a < '0' || a > '9')
                return false;
        }
        
        return true;
    }
}
