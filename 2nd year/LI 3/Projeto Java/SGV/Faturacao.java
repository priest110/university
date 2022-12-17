import java.util.*;
import java.io.*;
import static java.lang.System.out;
import java.util.stream.Collectors;
import java.util.*;
import java.io.*;
import static java.lang.System.out;
import java.util.stream.Collectors;
import java.util.AbstractMap.SimpleEntry;
/**
 * Write a description of class Faturacao here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Faturacao implements IFaturacao, Serializable
{
    private Map<IProduto, List<IFatura>> faturacao;
    
    /**
     * Construtor para objetos da classe Faturacao (por omissao)
     */
    public Faturacao(){
        this.faturacao = new TreeMap<IProduto, List<IFatura>>(new ComparadorProdutos());
    }
    
    /**
     * Construtor para objetos da classe Faturacao (parameterizado)
     * 
     * @param  fat   a Faturacao
     */
    public Faturacao(Map<IProduto, List<IFatura>> fat){
        this.setFaturacao(fat);
    }
    
    /**
     * Construtor para objetos da classe Faturacao (de copia)
     * 
     * @param  f   o Faturacao
     */
    public Faturacao(Faturacao f){
        this.faturacao = f.getFaturacao();
    }
    
    /**
     * Metodo que devolve a faturacao de um conjunto de dados
     * 
     * @return     a faturacao dos dados
     */
    public Map<IProduto, List<IFatura>> getFaturacao(){
        Map<IProduto, List<IFatura>> aux = new TreeMap<>(new ComparadorProdutos());
        List<IFatura> x = new ArrayList<IFatura>() ;
        for(IProduto em : this.faturacao.keySet()){
            x = this.determinadaFaturas(em);
            aux.put(em.clone(), x);
        }
        return aux;
    }
    
    /**
     * Metodo que altera a faturacao de um conjunto de cados
     * 
     * @param  fat   a nova faturacao
     */
    public void setFaturacao(Map<IProduto, List<IFatura>> fat){
        this.faturacao = fat.values().stream()
                                .collect(Collectors
                                .toMap((p -> p.get(0).getCodProd()), (f) ->  f = clonar(f)));
    }
    
    /**
     * Metodo que duplica os Faturacao
     * 
     * @return   o clone dos Faturacao
     */
    public Faturacao clone(){
        return new Faturacao(this);
    }
    
    /**
     * Metodo que verifica se dois conjuntos de Faturacao sao iguais
     * 
     * @param  o   o objeto a comparar
     * 
     * @return     o resultado da comparacao dos objetos
     */
    public boolean equals(Object o){
        if(o == this)
            return true;
        if(o == null || o.getClass() != this.getClass())
            return false;
        else{
            Faturacao d = (Faturacao) o;
            return(this.faturacao.equals(d.getFaturacao()));
        }
    }
    
    /**
     * Metodo que converte um conjunto de Faturacao para uma string
     * 
     * @return     o conjunto de Faturacao em string
     */
    public String toString(){
        String aux = new String();
        for(List<IFatura> f : this.faturacao.values())
            for(IFatura fa : f)
                aux += fa.toString();
        return aux;
    }
    
    /**
     * Metodo que devolve o codigo de hash para um proprietario
     * 
     * @return     o hashcode
     */
    public int hashCode(){
        int hash = 7; 
        for(List<IFatura> f : this.faturacao.values())
            for(IFatura fa : f)
                hash = 31 * hash + fa.hashCode();
        return hash;        
    }
    
    /**
     * Metodo que duplica uma Lista de Faturas
     * 
     * @param   x   Lista de Faturas
     * 
     * @return   o clone da lista
     */
    private List<IFatura> clonar(List<IFatura> x){
        List<IFatura> aux = new ArrayList<IFatura>();
        for(IFatura f : x)
            aux.add(f.clone());
        return aux;
    }
    
    /**
     * Metodo que insere uma Fatura numa Arvore de Faturas
     * 
     * @param   fatt    Arvore de Faturas
     * @param   key     Chave da fatura a inserir
     * @param   value   Fatura a inserir
     * 
     * @return  IFilialAux caso seja convertida, null caso contrario
     */
    public void addValues(IProduto key, IFatura value) {
        List<IFatura> x = null;
        if (this.faturacao.containsKey(key)){
            x = this.faturacao.get(key);
            if(x == null)
                x = new ArrayList<IFatura>();
            x.add(value.clone());  
        }     
        else {
            x = new ArrayList<IFatura>();
            x.add(value.clone());               
        }
        this.faturacao.put(key.clone(),x);
    }
    
    /**
     * Metodo que devolve a lista de faturas de determinado Produto
     * 
     * @return   lista de faturas
     */
    public List<IFatura> determinadaFaturas(IProduto p){
        List<IFatura> ret = new ArrayList<>();
        for(IFatura fat : this.faturacao.get(p))
            ret.add(fat.clone());
        return ret;
    }
    
    /**
     * Metodo que verifica se um dado produto foi comprado
     * 
     * @param   pro  o produto a verificar
     */
    public boolean existeFatura(IProduto pro){
        return this.faturacao.containsKey(pro);
    }
    
    /**
     * Metodo que devolve o top de X produtos mais vendidos
     * 
     * @param  x   o numero de produtos que pretendemos obter
     * 
     * @return  a lista com os produtos
     */
    public List<IParProdQuantos> topXMaisVendidos(int x){
        List<IParProdQuantos> ret = new ArrayList<>();
        int quantos = 0;
        IParProdQuantos par = new ParProdQuantos();
        
        for(IProduto p : this.faturacao.keySet()){
            quantos = 0;
            for(IFatura f: this.faturacao.get(p)){
                quantos += f.getQuantidade();
            }
            
            if(ret.size() < x){
                par = new ParProdQuantos(quantos, p);
                ret.add(par.clone());
            }
            else{
                IParProdQuantos aux = par.menorQuantidade(ret);
                if (quantos > aux.getQuantos()){
                    ret.remove(aux);
                    par = new ParProdQuantos(quantos, p);
                    ret.add(par.clone());
                }
            }       
        }
        Collections.sort(ret, new ComparadorParProdQuantos());
        return ret;
    }
    
    /**
     * Metodo que devolve todos os produtos que possuem vendas
     * 
     * @return  o numero de produtos
     */
    public int prodsVendidos(){
        return this.faturacao.size();
    }
    
    /**
     * Metodo que devolve o numero total de vendas de valor 0 e a faturacao total
     */
    public SimpleEntry<Integer, Double> comprasA0EFaturacaoTotal(){
        int zero = 0;
        double total = 0;
        for(List<IFatura> lis : this.faturacao.values()){
            for(IFatura fat : lis){
                if((fat.getQuantidade() * fat.getPreco()) == 0)
                    zero++;
                total += (fat.getQuantidade() * fat.getPreco());
            }
        }
        
        return new SimpleEntry<Integer, Double>(zero, total);
    }
}
