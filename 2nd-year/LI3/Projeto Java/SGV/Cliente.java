import java.util.*;
import java.io.*;
import static java.lang.System.out;
import java.util.stream.Collectors;

/**
 *  Cliente - classe que guarda a informacao de um cliente
 * 
 * @author (Ana Rita Rosendo, Goncalo Esteves, Rui Oliveira) 
 * @version 3 de maio de 2019
 */
public class Cliente implements ICliente, Serializable{
    private String codigo;

    /**
     * Construtor para objetos da classe Cliente (por omissao)
     */
    public Cliente(){
        this.codigo = "n/a";
    }
    
    /**
     * Construtor para objetos da classe Cliente (por omissao)
     * 
     * @param  cod   o codigo do cliente
     */
    public Cliente(String cod){
        this.setCodigo(cod);
    }
    
    /**
     * Construtor para objetos da classe Cliente (por copia)
     * 
     * @param  c   o cliente a copiar
     */
    public Cliente(Cliente c){
        this.codigo = c.getCodigo();
    }

    /**
     * Metodo que retorna o codigo de um cliente
     * 
     * @return     o codigo do cliente
     */
    public String getCodigo(){
        return this.codigo;
    }
    
    /**
     * Metodo que retorna o codigo de um cliente
     * 
     * @param  cod   o codigo do cliente
     */
    public void setCodigo(String cod){
        this.codigo = cod;
    }
    
    /** Metodo que converte um produto numa string
     * 
     * @return o produto convertido
     */
    public String toString(){
        return ("Codigo do Cliente: " + this.codigo + ";\n");
    }
    
    /**
     * Metodo que verifica se dois clientes sao iguais
     * 
     * @param  o   o objeto
     * @return o resultado da comparacao
     */
    public boolean equals(Object o){
        if(o == null || o.getClass() != this.getClass())
            return false;
        
        if(o == this)
            return true;
        
        else{
            Cliente p = (Cliente) o;
            return(this.codigo.equals(p.getCodigo()));
        }
    }
    
    /**
     * Metodo que duplica um cliente
     * 
     * @return o clone
     */
    public Cliente clone (){
        return new Cliente(this);
    }
    
    /**
     * Metodo que calcula o hashcode de um cliente
     * 
     * @return o hashcode
     */
    public int hashcode(){
        int hash = 7; 
        hash = 31 * hash + this.codigo.hashCode();
        return hash;
    }
    
    /**
     * Metodo que implementa a ordem natural de comparacao de instancias de Cliente
     */
    public int compareTo(ICliente c){
        return c.getCodigo().compareTo(this.codigo);
    }
    
    /**
     * Metodo que verifica se um dado cliente e valido
     * 
     * @param   s   string a verificar
     * 
     * @return  o    resultado da observacao (true ou false)
     */
    public boolean cliValido(String s){
        char a;
        
        if(s.length() != 5)
            return false;
            
        a = s.charAt(0);       
        if(a < 'A' || a > 'Z')
            return false;
        
        a = s.charAt(1);
        if(a < '1' || a > '5')
            return false;
            
        if(a == '5'){
            if(s.charAt(2) != '0' || s.charAt(3) != '0' || s.charAt(4) != '0')
                return false;
        }
            
        else{
            for(int i = 2; i < 5; i++){
                a = s.charAt(i);
                if(a < '0' || a > '9')
                    return false;
            }
        }
        
        return true;
    }
}
