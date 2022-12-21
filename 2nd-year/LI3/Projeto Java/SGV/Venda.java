import static java.lang.System.out;
import java.util.*;
import java.io.*;
/**
 * Venda - classe que guarda a informacao de uma venda
 * 
 * @author (Ana Rita Rosendo, Goncalo Esteves, Rui Oliveira) 
 * @version 3 de maio de 2019
 */
public class Venda implements IVenda, Serializable{
    private IProduto codProd;
    private double preco;
    private int quantidade;
    private String tipo;
    private ICliente codCli;
    private int mes;
    private int filial;
    
    /**
     * Construtor para objetos da classe Produto (por omissao)
     */
    public Venda(){
        this.codProd = new Produto();
        this.preco = 0;
        this.quantidade = 0;
        this.tipo = "n/a";
        this.codCli = new Cliente();
        this.mes = 0;
        this.filial = 0;
    }
    
    /**
     * Construtor para objetos da classe Produto (por parametrizacao)
     */
    public Venda(IProduto prod, double pre, int quant, String t, ICliente cli, int m, int fil){
        this.setCodProd(prod);
        this.setPreco(pre);
        this.setQuantidade(quant);
        this.setTipo(t);
        this.setCodCli(cli);
        this.setMes(m);
        this.setFilial(fil);
    }
    
    /**
     * Construtor para objetos da classe Produto (por copia)
     */
    public Venda(Venda v){
        this.codProd = v.getCodProd();
        this.preco = v.getPreco();
        this.quantidade = v.getQuantidade();
        this.tipo = v.getTipo();
        this.codCli = v.getCodCli();
        this.mes = v.getMes();
        this.filial = v.getFilial();
    }
    
    /**
     * Metodo que devolve o codigo de produto da venda
     *
     * @return        o codigo do produto 
     */
    public IProduto getCodProd(){
        return this.codProd.clone();
    }
    
    /**
     * Metodo que devolve preco unitario de cada produto da venda
     *
     * @return        o preco
     */
    public double getPreco(){
        return this.preco;
    }
    
    /**
     * Metodo que devolve a quantidade de produtos da venda
     *
     * @return        o codigo do produto 
     */
    public int getQuantidade(){
        return this.quantidade;
    }
    
    /**
     * Metodo que devolve o tipo da venda
     *
     * @return        o tipo
     */
    public String getTipo(){
        return this.tipo;
    }
    
    /**
     * Metodo que devolve o codigo de cliente da venda
     *
     * @return        o codigo do cliente
     */
    public ICliente getCodCli(){
        return this.codCli.clone();
    }
    
    /**
     * Metodo que devolve o mes da venda
     *
     * @return        o mes
     */
    public int getMes(){
        return this.mes;
    }
    
    /**
     * Metodo que devolve a filial da venda
     *
     * @return        a filial
     */
    public int getFilial(){
        return this.filial;
    }
    
    /**
     * Metodo que altera o codigo de produto da venda
     *
     * @param  codigo      o codigo do produto 
     */
    public void setCodProd(IProduto codigo){
        this.codProd = codigo.clone();
    }
    
    /**
     * Metodo que altera o preco da venda
     *
     * @param   preco     o preco
     */
    public void setPreco(Double p){
        this.preco = p;
    }
    
    /**
     * Metodo que altera a quantidade da venda
     *
     * @param  quantos     a quantidade da venda
     */
    public void setQuantidade(int quantos){
        this.quantidade = quantos;
    }
    
    /**
     * Metodo que altera o tipo da venda
     *
     * @param   tipo     o tipo
     */
    public void setTipo(String t){
        this.tipo = t;
    }
    
    /**
     * Metodo que altera o codigo de cliente da venda
     *
     * @param    codigo    o codigo do cliente 
     */
    public void setCodCli(ICliente codigo){
        this.codCli = codigo.clone();
    }
    
    /**
     * Metodo que altera o mes da venda
     *
     * @param  mes     o mes da venda
     */
    public void setMes(int m){
        this.mes = m;
    }
    
    /**
     * Metodo que altera a filial da venda
     *
     * @param  filial     a filial da venda
     */
    public void setFilial(int f){
        this.filial = f;
    }
    
    /**
     * Metodo que converte uma venda numa string
     * 
     * @return a venda convertida
     */
    public String toString(){
        return ("Codigo do Produto: " + this.codProd.toString() + ";\n"
                + "Preco: " + this.preco + ";\n"
                + "Quantidade: " + this.quantidade + ";\n"
                + "Tipo de Venda: " + this.tipo + ";\n"
                + "Codigo do Cliente: " + this.codCli.toString() + ";\n"
                + "Mes: " + this.mes + ";\n"
                + "Filial: " + this.filial + ";\n");
    }
    
    /**
     * Metodo que verifica se duas vendas sao iguais
     * 
     * @param  venda   a venda a comparar
     * @return o resultado da comparacao
     */
    public boolean equals(Object o){
        if(o == null || o.getClass() != this.getClass())
            return false;
        
        if(o == this)
            return true;
        
        else{
            Venda v = (Venda) o;
            return(this.codProd.equals(v.getCodProd())
                   && this.preco == v.getPreco()
                   && this.quantidade == v.getQuantidade()
                   && this.tipo.equals(v.getTipo())
                   && this.codCli.equals(v.getCodCli())
                   && this.mes == v.getMes()
                   && this.filial == v.getFilial());
        }
    }
    
    /**
     * Metodo que duplica uma venda
     * 
     * @return o clone
     */
    public Venda clone (){
        return new Venda(this);
    }
    
    /**
     * Metodo que calcula o hashcode de uma venda
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
        hash = 31 * hash + this.filial;
        long aux = Double.doubleToLongBits(this.preco);
        hash = 31 * hash + (int)(aux ^ (aux >>> 32));
        return hash;
    }
    
    /**
     * Metodo que verifica se uma dada venda e valida
     * 
     * @param   linha   que vai ser convertida em IVenda
     * @param   produtos    set de produtos
     * @param   clientes    set de clientes
     * 
     * @return  IVenda caso seja valida, null caso contrario
     */
    public IVenda linhaToVenda(String linha, ICatCli catCli, ICatProd catProd){
        String codProd, codCli, tipo;
        int mes = 0;
        int filial = 0;
        int quant = 0;
        double preco = 0;
        String[] campos = linha.split(" ");
        
        codProd = campos[0];
        tipo = campos[3];
        codCli = campos[4];
        
       
        IProduto p = new Produto(codProd);
        ICliente c = new Cliente(codCli);
        if(catProd.existeProduto(p) == false || catCli.existeCliente(c) == false)
            return null;
         
        if(p.prodValido(codProd) == false)
            return null;
            
        if(c.cliValido(codCli) == false)
            return null;
        
        if(tipo.equals("N") == false && tipo.equals("P") == false)
            return null;
        
        try{
            preco = Double.parseDouble(campos[1]);
        }
        catch(InputMismatchException e){
            return null;
        }
        catch(NumberFormatException e){
            return null;
        }
        
        if(preco < 0.0 || preco > 999.99)
            return null;
        
        try{
            quant = Integer.parseInt(campos[2]);
        }
        catch(InputMismatchException e){
            return null;
        }
        catch(NumberFormatException e){
            return null;
        }
        
        if(quant < 1 || quant > 200)
            return null;
        
        try{
            mes = Integer.parseInt(campos[5]);
        }
        catch(InputMismatchException e){
            return null;
        }
        catch(NumberFormatException e){
            return null;
        }
        
        if(mes < 1 || mes > 12)
            return null;
        
        try{
            filial = Integer.parseInt(campos[6]);
        }
        catch(InputMismatchException e){
            return null;
        }
        catch(NumberFormatException e){
            return null;
        }
        
        if(filial < 1 || filial > 3)
            return null;
            
        IProduto prod = new Produto(codProd);
        ICliente cli = new Cliente(codCli);
            
        return new Venda(prod, preco, quant, tipo, cli, mes, filial);
    }
    
    /**
     * Metodo que converte uma IVenda numa IFatura
     * 
     * @param   v   IVenda a converter
     * 
     * @return  IFatura caso seja convertida, null caso contrario
     */
    public IFatura vendaToFatura(){
        return new Fatura(this.getCodProd(), this.getPreco(), this.getQuantidade(), this.getTipo());
    }
    
    /**
     * Metodo que converte uma IVenda numa IFilialAux
     * 
     * @param   v   IVenda a converter
     * 
     * @return  IFilialAux caso seja convertida, null caso contrario
     */
    public IFilialAux vendaToFilial(){
        return new FilialAux(this.getCodProd(), this.getPreco(), this.getQuantidade(), this.getTipo(), this.getCodCli(), this.getMes());
    }
}
