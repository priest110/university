import static java.lang.System.out;
import java.util.*;
import java.io.*;
import java.util.stream.Collectors;
import java.util.AbstractMap.SimpleEntry;
/**
 * Write a description of class Filiais here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Filial implements IFilial, Serializable
{
    private Map<ICliente, List<IFilialAux>> filial;
    
    /**
     * Construtor para objetos da classe Filial (por omissao)
     */
    public Filial(){
        this.filial = new TreeMap<ICliente, List<IFilialAux>>(new ComparadorClientes());
    }
    
    /**
     * Construtor para objetos da classe Filial (parameterizado)
     * 
     * @param  f1   a Filial1
     * 
     */
    public Filial(Map<ICliente, List<IFilialAux>> f1){
        this.setFilial(f1);
    }
    
    /**
     * Construtor para objetos da classe Filial (de copia)
     * 
     * @param  d   o Filial
     */
    public Filial(Filial f){
        this.filial = f.getFilial();
    }

    /**
     * Metodo que devolve a filial1 de um conjunto de dados
     * 
     * @return     a filial1 dos dados
     */
    public Map<ICliente, List<IFilialAux>> getFilial(){
        Map<ICliente, List<IFilialAux>> aux = new TreeMap<>(new ComparadorClientes());
        List<IFilialAux> x = new ArrayList<IFilialAux>() ;
        for(ICliente em : this.filial.keySet()){
            x = this.determinadaFilial(em);
            aux.put(em.clone(), x);
        }
        return aux;
    }
    
    /**
     * Metodo que altera a filial1 de um conjunto de cados
     * 
     * @param  f   a nova filial1
     */
    public void setFilial(Map<ICliente, List<IFilialAux>> f){
        this.filial = f.values().stream()
                                .collect(Collectors
                                .toMap((p -> p.get(0).getCodCli()), (fi) -> fi = clonar(fi)));
    }
    
    /**
     * Metodo que duplica os Filial
     * 
     * @return   o clone dos Filial
     */
    public Filial clone(){
        return new Filial(this);
    }
    
    /**
     * Metodo que verifica se dois conjuntos de Filial sao iguais
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
            Filial d = (Filial) o;
            return(this.filial.equals(d.getFilial()));
        }
    }
    
    /**
     * Metodo que converte um conjunto de Filial para uma string
     * 
     * @return     o conjunto de Filial em string
     */
    public String toString(){
        String aux = new String();
        for(List<IFilialAux> f1 : this.filial.values())
            for(IFilialAux fi : f1)
                aux += fi.toString();
        return aux;
    }
    
    /**
     * Metodo que devolve o codigo de hash para um proprietario
     * 
     * @return     o hashcode
     */
    public int hashCode(){
        int hash = 7;
        for(List<IFilialAux> f1 : this.filial.values())
            for(IFilialAux fi : f1)
                hash = 31 * hash + fi.hashCode();
        return hash;        
    }
    
    /**
     * Metodo que duplica uma Lista de Filiais
     * 
     * @param   x   Lista de Filiais
     * 
     * @return   o clone da lista
     */
    private List<IFilialAux> clonar(List<IFilialAux> x){
        List<IFilialAux> aux = new ArrayList<IFilialAux>();
        for(IFilialAux f : x)
            aux.add(f.clone());
        return aux;
    }
    
    /**
     * Metodo que insere uma Filial numa Arvore de Filiais
     * 
     * @param   filiais    Arvore de Filiais
     * @param   key     Chave da fatura a inserir
     * @param   value   Filial a inserir
     * 
     * @return  IFilialAux caso seja convertida, null caso contrario
     */
    public void addValues(ICliente key, IFilialAux value) {
        List<IFilialAux> x = null;
        if (this.filial.containsKey(key)){
            x = this.filial.get(key);
            if(x == null)
                x = new ArrayList<IFilialAux>();
            x.add(value.clone());  
        }     
        else {
            x = new ArrayList<IFilialAux>();
            x.add(value.clone());               
        }
        this.filial.put(key.clone(),x);
    }
    
    /**
     * Metodo que devolve a lista de filiais de determinado CLiente
     * 
     * @return   lista de Filiais
     */
    public List<IFilialAux> determinadaFilial(ICliente c){
        List<IFilialAux> ret = new ArrayList<>();
        for(IFilialAux fil : this.filial.get(c))
            ret.add(fil.clone());
        return ret;
    }
    
    /**
     * Metodo que, dado um mes, calcula o total de vendas realizadas nesse mes e os clientes que as realizaram
     * 
     * @param  mes   mes a analisar
     * @return o total de vendas e os clientes que as realizaram
     */
    public SimpleEntry<Integer, List<ICliente>> vendasPorMes(int mes){
        int vendas = 0;
        List<ICliente> lis = new ArrayList<>();
        for(List<IFilialAux> l : this.filial.values()){
            for(IFilialAux f : l){
                if (f.getMes() == mes){
                    vendas++;
                    if(!lis.contains(f.getCodCli()))
                        lis.add(f.getCodCli());
                }
            }
        }
        SimpleEntry<Integer, List<ICliente>> ret = new SimpleEntry<> (vendas, lis);
        return ret;
    }
    
    /**
     * Metodo que devolve uma lista com todos os clientes que compraram um determinado produto
     * 
     * @param  pro   o produto a ser analisado
     * @return  a lista com todos os clientes que compraram o produto
     */
    public List<ICliente> todosCliComprProd(IProduto pro){
        List<ICliente> lis = new ArrayList<>();
        
        for(ICliente cli : this.filial.keySet()){
            for(IFilialAux fil : this.filial.get(cli)){
                if(fil.getCodProd().equals(pro)){
                    lis.add(cli.clone());
                    break;
                }
            }
        }
        
        return lis;
    }
    
    /**
     * Metodo que devolve um map com os 3 maiores compradores na filial (em termos de dinheiro faturado)
     * 
     * @return  o map com os 3 maiores compradores
     */
    public Map<ICliente, Double> getTop3(){
        Map<ICliente, Double> map = new TreeMap<ICliente, Double>(new ComparadorClientes());
        double fat = 0;
        for(ICliente cli : this.filial.keySet()){
            fat = 0;
            for(IFilialAux fil : this.filial.get(cli)){
                fat += (fil.getPreco() * fil.getQuantidade());
            }
            
            if(map.size() < 3){
                map.put(cli.clone(), fat);
            }
            
            else{
                ICliente cli2 = cliMenosGasto(map);
                if(fat > map.get(cli2)){
                    map.remove(cli2);
                    map.put(cli.clone(), fat);
                }
            }
        }
          
        return map;
    }
                
    /**
     * Metodo que devolve o cliente que gastou menos dinheiro, dos guardados num map
     * 
     * @param  map   o mapa a analisar
     * @return o cliente
     */
    public ICliente cliMenosGasto(Map<ICliente, Double> map){
        double fat = Double.MAX_VALUE;
        ICliente c = new Cliente();
        for(ICliente cli : map.keySet()){
            if(fat > map.get(cli)){
                fat = map.get(cli);
                c = cli.clone();
            }
        }
        return c;
    }
    
    /**
     * Metodo que devolve uma lista com todos os diferentes produtos que um dado cliente comprou
     * @param  cli   o cliente a analisar
     * @return  a lista com os produtos que comprou
     */
    public List<IProduto> numeroProdsDiferentes(ICliente cli){
        List<IProduto> ret = new ArrayList<>();
        if(this.filial.containsKey(cli)){
            for(IFilialAux fil : this.filial.get(cli)){
                if(!ret.contains(fil.getCodProd()))
                    ret.add(fil.getCodProd());
            }
        }
        return ret;
    }
    
    /** 
     * Metodo que devolve um triplo com as informacoes relativas ao numero total de compras, faturacao e numero de clientes distintos, por mes
     * 
     * @return  o triplo com as informacoes
     */
    public ITrioComprasProdGasto determinaEstatisticas(){
        int[] compras = new int[12];
        int[] clientes = new int[12];
        double[] fat = new double[12];
        
        int i;
        for(i = 0; i < 12; i++){
            compras[i] = 0;
            clientes[i] = 0;
            fat[i] = 0;
        }
        
        int[] aux = new int[12];
        
        for(List<IFilialAux> lis : this.filial.values()){
            for(i = 0; i < 12; i++)
                aux[i] = 0;
                
            for(IFilialAux fil : lis){
                fat[fil.getMes()-1] += (fil.getPreco() * fil.getQuantidade());
                compras[fil.getMes()-1] += fil.getQuantidade();
                
                if(aux[fil.getMes()-1] == 0){
                    clientes[fil.getMes()-1]++;
                    aux[fil.getMes()-1] = 1;
                }
            }
        }
        
        return new TrioComprasProdGasto(compras, clientes, fat);
    }
    
    /**
     * Metodo que devolve todos os clientes que compraram na filial
     * 
     * @return a lista com os clientes
     */
    public Set<ICliente> clientesCompradores(){
        Set<ICliente> ret = new TreeSet<>(new ComparadorClientes());
        for(ICliente cli : this.filial.keySet())
            ret.add(cli.clone());
        return ret;
    }
    
    /**
     * Metodo que determina o map com todos os produtos comprados na filial, associados ao total faturado
     * 
     * @return  o map com os produtos associados aos totais
     */
    public Map<IProduto, double[]> todosProdsEFat(){
        Map<IProduto, double[]> ret = new TreeMap<>(new ComparadorProdutos());
        
        for(List<IFilialAux> lis : this.filial.values()){
            for(IFilialAux fil : lis){
                if(ret.containsKey(fil.getCodProd())){
                    double[] total = ret.get(fil.getCodProd());
                    total[fil.getMes()-1] += (fil.getPreco() * fil.getQuantidade());
                    ret.put(fil.getCodProd(), total);
                }
                else{
                    double[] novo = new double[12];
                    for(int i = 0; i < 12; i++)
                        novo[i] = 0;
                    novo[fil.getMes()-1] += (fil.getPreco() * fil.getQuantidade());
                    ret.put(fil.getCodProd(), novo);
                }
            }
        }
        
        return ret;
    }
}
