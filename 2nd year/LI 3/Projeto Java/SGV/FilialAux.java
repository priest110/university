import static java.lang.System.out;
import java.util.*;
import java.io.*;
/**
 * FilialAux - classe que guarda a informacao de uma FilialAux
 * 
 * @author (Ana Rita Rosendo, Goncalo Esteves, Rui Oliveira) 
 * @version 3 de maio de 2019
 */
public class FilialAux implements IFilialAux, Serializable{
    private IProduto codProd;
    private double preco;
    private int quantidade;
    private String tipo;
    private ICliente codCli;
    private int mes;
    
    /**
     * Construtor para objetos da classe Produto (por omissao)
     */
    public FilialAux(){
        this.codProd = new Produto();
        this.preco = 0;
        this.quantidade = 0;
        this.tipo = "n/a";
        this.codCli = new Cliente();
        this.mes = 0;
    }
    
    /**
     * Construtor para objetos da classe Produto (por parametrizacao)
     */
    public FilialAux(IProduto prod, double pre, int quant, String t, ICliente cli, int m){
        this.setCodProd(prod);
        this.setPreco(pre);
        this.setQuantidade(quant);
        this.setTipo(t);
        this.setCodCli(cli);
        this.setMes(m);
    }
    
    /**
     * Construtor para objetos da classe Produto (por copia)
     */
    public FilialAux(FilialAux v){
        this.codProd = v.getCodProd();
        this.preco = v.getPreco();
        this.quantidade = v.getQuantidade();
        this.tipo = v.getTipo();
        this.codCli = v.getCodCli();
        this.mes = v.getMes();
    }
    
    /**
     * Metodo que devolve o codigo de produto da FilialAux
     *
     * @return        o codigo do produto 
     */
    public IProduto getCodProd(){
        return this.codProd.clone();
    }
    
    /**
     * Metodo que devolve preco unitario de cada produto da FilialAux
     *
     * @return        o preco
     */
    public double getPreco(){
        return this.preco;
    }
    
    /**
     * Metodo que devolve a quantidade de produtos da FilialAux
     *
     * @return        o codigo do produto 
     */
    public int getQuantidade(){
        return this.quantidade;
    }
    
    /**
     * Metodo que devolve o tipo da FilialAux
     *
     * @return        o tipo
     */
    public String getTipo(){
        return this.tipo;
    }
    
    /**
     * Metodo que devolve o codigo de cliente da FilialAux
     *
     * @return        o codigo do cliente
     */
    public ICliente getCodCli(){
        return this.codCli.clone();
    }
    
    /**
     * Metodo que devolve o mes da FilialAux
     *
     * @return        o mes
     */
    public int getMes(){
        return this.mes;
    }
    
    /**
     * Metodo que altera o codigo de produto da FilialAux
     *
     * @param  codigo      o codigo do produto 
     */
    public void setCodProd(IProduto codigo){
        this.codProd = codigo.clone();
    }
    
    /**
     * Metodo que altera o preco da FilialAux
     *
     * @param   preco     o preco
     */
    public void setPreco(Double p){
        this.preco = p;
    }
    
    /**
     * Metodo que altera a quantidade da FilialAux
     *
     * @param  quantos     a quantidade da FilialAux
     */
    public void setQuantidade(int quantos){
        this.quantidade = quantos;
    }
    
    /**
     * Metodo que altera o tipo da FilialAux
     *
     * @param   tipo     o tipo
     */
    public void setTipo(String t){
        this.tipo = t;
    }
    
    /**
     * Metodo que altera o codigo de cliente da FilialAux
     *
     * @param    codigo    o codigo do cliente 
     */
    public void setCodCli(ICliente codigo){
        this.codCli = codigo.clone();
    }
    
    /**
     * Metodo que altera o mes da FilialAux
     *
     * @param  mes     o mes da FilialAux
     */
    public void setMes(int m){
        this.mes = m;
    }
    
    /**
     * Metodo que converte uma FilialAux numa string
     * 
     * @return a FilialAux convertida
     */
    public String toString(){
        return ("Codigo do Produto: " + this.codProd.toString() + ";\n"
                + "Preco: " + this.preco + ";\n"
                + "Quantidade: " + this.quantidade + ";\n"
                + "Tipo de FilialAux: " + this.tipo + ";\n"
                + "Codigo do Cliente: " + this.codCli.toString() + ";\n"
                + "Mes: " + this.mes + ";\n");
    }
    
    /**
     * Metodo que verifica se duas FilialAuxs sao iguais
     * 
     * @param  FilialAux   a FilialAux a comparar
     * @return o resultado da comparacao
     */
    public boolean equals(Object o){
        if(o == null || o.getClass() != this.getClass())
            return false;
        
        if(o == this)
            return true;
        
        else{
            FilialAux v = (FilialAux) o;
            return(this.codProd.equals(v.getCodProd())
                   && this.preco == v.getPreco()
                   && this.quantidade == v.getQuantidade()
                   && this.tipo.equals(v.getTipo())
                   && this.codCli.equals(v.getCodCli())
                   && this.mes == v.getMes());
        }
    }
    
    /**
     * Metodo que duplica uma FilialAux
     * 
     * @return o clone
     */
    public FilialAux clone (){
        return new FilialAux(this);
    }
    
    /**
     * Metodo que calcula o hashcode de uma FilialAux
     * 
     * @return o hashcode
     */
    public int hashcode(){
        int hash = 7; 
        hash = 31 * hash + this.codProd.hashCode();
        hash = 31 * hash + this.quantidade;
        hash = 31 * hash + this.tipo.hashCode();
        hash = 31 * hash + this.codCli.hashCode();
        hash = 31 * hash + this.mes;
        long aux = Double.doubleToLongBits(this.preco);
        hash = 31 * hash + (int)(aux ^ (aux >>> 32));
        return hash;
    }
    
    /**
     * Metodo que implementa a ordem natural de comparacao de instancias de Filial
     */
    public int compareTo(IFilialAux f){
        return f.getCodCli().compareTo(this.codCli);
    }
    
    /**
     * Metodo que verifica se uma lista de Filiais tem algum Produto igual a p
     * 
     * @param   p   Produto a verificar
     * @param   x   Lista de Filiais
     * 
     * @return  true caso tenha, false caso contrario
     */
    public boolean contemProduto(IProduto p, List<IFilialAux> x){
        for(IFilialAux f : x)
            if(f.getCodProd().equals(p))
                return true;
        return false;        
    }
    
    /**
     * Metodo que verifica se uma lista de Filiais tem algum Cliente igual a c
     * 
     * @param   p   Produto a verificar
     * @param   x   Lista de Filiais
     * 
     * @return  true caso tenha, false caso contrario
     */
    public boolean contemCliente(ICliente c, List<IFilialAux> x){
        for(IFilialAux f : x)
            if(f.getCodCli().equals(c))
                return true;
        return false;        
    }
    
    /**
     * Metodo que devolve uma lista de Filiais so com produtos iguais
     * 
     * @param   p   Produto a verificar
     * @param   x   Lista de Filiais
     * 
     * @return  lista de Filiais
     */
    public List<IFilialAux> produtosIguais(IProduto p, List<IFilialAux> x){
        List<IFilialAux> aux = new ArrayList<IFilialAux>();
        for(IFilialAux f : x)
            if(f.getCodProd().equals(p))
                aux.add(f);
        return aux;        
    }
}
