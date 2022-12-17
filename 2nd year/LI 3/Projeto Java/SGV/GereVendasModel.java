import java.util.*;
import java.io.*;
import static java.lang.System.out;
import java.util.stream.Collectors;
import java.util.AbstractMap.SimpleEntry;
/**
 * Escreva a descrição da classe GereVendasModel aqui.
 * 
 * @author (seu nome) 
 * @version (número de versão ou data)
 */
public class GereVendasModel implements InterGereVendasModel, Serializable
{   
    private ICatProd catProd;
    private ICatCli catCli;
    private IFaturacao faturacao;
    private IFilial filial1;
    private IFilial filial2;
    private IFilial filial3;
    String fichVendas;
    int vendasInvalidas;
   
    /**
     * Construtor para objetos da classe GereVendasModel (por omissao)
     */
    public GereVendasModel(){
        this.catProd = new CatProd();
        this.catCli  = new CatCli();
        this.faturacao = new Faturacao();
        this.filial1 = new Filial();
        this.filial2 = new Filial();
        this.filial3 = new Filial();
        this.fichVendas = "n/a";
        this.vendasInvalidas = 0;
    }
    
    /**
     * Construtor para objetos da classe GereVendasModel (parameterizado)
     * 
     * @param  cli   os catCli
     * @param  pro   os catProd
     */
    public GereVendasModel(ICatProd pro, ICatCli cli, IFaturacao fat, IFilial f1, IFilial f2, IFilial f3, String n, int vi){
        this.setCatCli(cli);
        this.setCatProd(pro);
        this.setFaturacao(fat);
        this.setFilial1(f1);
        this.setFilial2(f2);
        this.setFilial3(f3);
        this.setFichVendas(n);
        this.setVendasInvalidas(vi);
    }
    
    /**
     * Construtor para objetos da classe GereVendasModel (de copia)
     * 
     * @param  d   o GereVendasModel
     */
    public GereVendasModel(GereVendasModel d){
        this.catCli = d.getCatCli();
        this.catProd = d.getCatProd();
        this.faturacao = d.getFaturacao();
        this.filial1 = d.getFilial1();
        this.filial2 = d.getFilial2();
        this.filial3 = d.getFilial3();
        this.fichVendas = d.getFichVendas();
        this.vendasInvalidas = d.getVendasInvalidas();
    }
    
    /**
     * Metodo que devolve o catCli de um conjunto de CatCli
     * 
     * @return     o catCli dos CatCli
     */
    public ICatCli getCatCli(){
        return this.catCli.clone();
    }
    
    /**
     * Metodo que altera os catCli de um conjunto de cados
     * 
     * @param  cli   o novo catCli
     */
    public void setCatCli(ICatCli cli){
        this.catCli = cli.clone();
    }
    
    /**
     * Metodo que devolve o catProd de um conjunto de GereVendasModel
     * 
     * @return     o catProd dos GereVendasModel
     */
    public ICatProd getCatProd(){
        return this.catProd.clone();
    }
    
    /**
     * Metodo que altera o catProd de um conjunto de cados
     * 
     * @param  pro   o novo catProd
     */
    public void setCatProd(ICatProd pro){
        this.catProd = pro.clone();
    }
    
    /**
     * Metodo que devolve a faturacao de um conjunto de dados
     * 
     * @return     a faturacao dos dados
     */
    public IFaturacao getFaturacao(){
        return this.faturacao.clone();
    }
    
    /**
     * Metodo que altera a faturacao de um conjunto de cados
     * 
     * @param  fat   a nova faturacao
     */
    public void setFaturacao(IFaturacao fat){
        this.faturacao = fat.clone();
    }
    
    /**
     * Metodo que devolve a filial1 de um conjunto de dados
     * 
     * @return     a filial1 dos dados
     */
    public IFilial getFilial1(){
        return this.filial1.clone();
    }
    
    /**
     * Metodo que altera a filial1 de um conjunto de cados
     * 
     * @param  f   a nova filial1
     */
    public void setFilial1(IFilial f){
        this.filial1 = f.clone();
    }
    
    /**
     * Metodo que devolve a filial2 de um conjunto de dados
     * 
     * @return     a filial2 dos dados
     */
    public IFilial getFilial2(){
        return this.filial2.clone();
    }
    
    /**
     * Metodo que altera a filial2 de um conjunto de cados
     * 
     * @param  f   a nova filial2
     */
    public void setFilial2(IFilial f){
        this.filial2 = f.clone();
    }
    
    /**
     * Metodo que devolve a filial3 de um conjunto de dados
     * 
     * @return     a filial3 dos dados
     */
    public IFilial getFilial3(){
        return this.filial3.clone();
    }
    
    /**
     * Metodo que altera a filial3 de um conjunto de cados
     * 
     * @param  f   a nova filial3
     */
    public void setFilial3(IFilial f){
        this.filial3 = f.clone();
    }
    
    /**
     * Metodo que devolve o nome do ultimo ficheiro de vendas lido
     * 
     * @return  o nome do ficheiro de vendas
     */
    public String getFichVendas(){
        return this.fichVendas;
    }
    
    /**
     * Metodo que altera o nome do ultimo ficheiro de vendas lido
     * 
     * @param  n   o novo nome
     */
    public void setFichVendas(String n){
        this.fichVendas = n;
    }
    
    /**
     * Metodo que devolve o numero de vendas invalidas lidas
     * 
     * @return  o numero de vendas invalidas lidas
     */
    public int getVendasInvalidas(){
        return this.vendasInvalidas;
    }
    
    /**
     * Metodo que altera o numero de vendas invalidas lidas
     * 
     * @param  n   o novo numero
     */
    public void setVendasInvalidas(int n){
        this.vendasInvalidas = n;
    }
    
    /**
     * Metodo que duplica o GereVendasModel
     * 
     * @return   o clone do GereVendasModel
     */
    public GereVendasModel clone(){
        return new GereVendasModel(this);
    }
    
    /**
     * Metodo que verifica se dois conjuntos de GereVendasModel sao iguais
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
            GereVendasModel d = (GereVendasModel) o;
            return(this.catCli.equals(d.getCatCli())
                   && this.catProd.equals(d.getCatProd())
                   && this.faturacao.equals(d.getFaturacao())
                   && this.filial1.equals(d.getFilial1())
                   && this.filial2.equals(d.getFilial2())
                   && this.filial3.equals(d.getFilial3()));
        }
    }
    
    /**
     * Metodo que converte um conjunto de GereVendasModel para uma string
     * 
     * @return     o conjunto de GereVendasModel em string
     */
    public String toString(){
        String aux = new String();
        aux += this.catCli.toString();
        aux += this.catProd.toString();
        aux += this.faturacao.toString();
        aux += this.filial1.toString();
        aux += this.filial2.toString();
        aux += this.filial3.toString();
        return aux;
    }
    
    /**
     * Metodo que devolve o codigo de hash para um proprietario
     * 
     * @return     o hashcode
     */
    public int hashCode(){
        int hash = 7; 
        hash = 31 * hash + this.catCli.hashCode();
        hash = 31 * hash + this.catProd.hashCode();
        hash = 31 * hash + this.faturacao.hashCode();
        hash = 31 * hash + this.filial1.hashCode();
        hash = 31 * hash + this.filial2.hashCode();
        hash = 31 * hash + this.filial3.hashCode();
        return hash;        
    }
    
    
    /*
                             ***** METODOS DE LEITURAS DE FICHEIROS *****
    */
    
    /**
     * Metodo que converte um ficheiro num Set de Produtos
     * 
     * @return     o conjunto de clientes num Set
     */
    public void lerFichWithBuff_Produtos(String fichtxt){
        this.catProd = new CatProd();
        
        BufferedReader inFile = null;
        String linha = null;
        
        IProduto p = new Produto();
        try{
            inFile = new BufferedReader(new FileReader(fichtxt));
            while((linha = inFile.readLine()) != null){
                if(p.prodValido(linha) == true){
                    IProduto x = new Produto(linha);
                    this.catProd.addProduto(x);
                }
            }
        }
        catch(IOException exc){
            out.println(exc);
        }
    }
    
    /**
     * Metodo que converte um ficheiro num Set de Clientes
     * 
     * @return     o conjunto de clientes num Set
     */
    public void lerFichWithBuff_Clientes(String fichtxt){
        this.catCli = new CatCli();
        
        BufferedReader inFile = null;
        String linha = null;
        
        ICliente c = new Cliente();
        try{
            inFile = new BufferedReader(new FileReader(fichtxt));
            while((linha = inFile.readLine()) != null){
                if(c.cliValido(linha) == true){
                    ICliente x = new Cliente(linha);
                    this.catCli.addCliente(x);
                }
            }
        }
        catch(IOException exc){
            out.println(exc);
        }
        
    }
    
    /**
     * Metodo que converteum ficheiro numa Arvore de Faturas
     * 
     * @return     o conjunto de faturas em arvores
     */
    public void lerFichWithBuff_Vendas(String fichtxt){
        this.faturacao = new Faturacao();
        this.filial1 = new Filial();
        this.filial2 = new Filial();
        this.filial3 = new Filial();
        this.fichVendas = fichtxt;
        
        BufferedReader inFile = null;
        String linha = null;
        IVenda x = new Venda();
        IVenda v = new Venda();
        int inv = 0;
        try{
            inFile = new BufferedReader(new FileReader(fichtxt));
            while((linha = inFile.readLine()) != null){
                x = v.linhaToVenda(linha, this.catCli, this.catProd);
                if(x != null){
                    IFatura f = x.vendaToFatura();
                    IFilialAux fi = x.vendaToFilial();
                    this.faturacao.addValues(f.getCodProd(), f.clone());
                    if(x.getFilial() == 1)
                        this.filial1.addValues(fi.getCodCli(), fi.clone());
                    if(x.getFilial() == 2)
                        this.filial2.addValues(fi.getCodCli(), fi.clone());
                    if(x.getFilial() == 3)
                        this.filial3.addValues(fi.getCodCli(), fi.clone());
                }
                else
                    inv++;
            }
            this.vendasInvalidas = inv;
        }
        
        catch(IOException exc){
            out.println(exc);
        }
        
    }
    
    /*
                             ***** METODOS AUXILIARES DE CARREGAMENTO DE DADOS *****
    */
    
    /**
     * Metodo que carregas os dados dos ficheiros para as estruturas representativas
     */
    public void carregarDados(String fichVendas){
        lerFichWithBuff_Produtos("Produtos.txt");
        
        lerFichWithBuff_Clientes("Clientes.txt");
        
        lerFichWithBuff_Vendas(fichVendas);
    }
    
    /*
                             ***** METODOS QUERIES *****
    */
   
    /**
     * Metodo que devolve a query 1
     * 
     * @return  lista de produtos nunca comprados
     */
    public List<IProduto> query1(){
        Set<IProduto> prods = this.catProd.getCatProd();
        List<IProduto> l = new ArrayList<IProduto>();
        
        for(IProduto p : prods){
            if(this.faturacao.existeFatura(p) == false){
                l.add(p.clone());
            }
        }
        return l;
    }
    
    
    /**
     * Metodo que devolve a query 2
     * 
     * @param  mes   o mes a analisar
     * 
     * @return  array com as informacoes necessarias (1ªpos, 3ªpos e 5ªpos - numero de vendas nas filiais 1, 2 e 3, respetivamente; 
     * 2ªpos, 4ªpos e 6ªpos - numero de clientes que compraram nas filiais 1, 2 e 3; 7ªpos - numero de clientes que fizeram compras em pelo menos uma filial)
     */
    public int[] query2(int mes){
        int num_Vendas = 0;
        int num_Clientes = 0;
        int flag = 0;
        
        SimpleEntry<Integer, List<ICliente>> vendas1 = this.filial1.vendasPorMes(mes);
        SimpleEntry<Integer, List<ICliente>> vendas2 = this.filial2.vendasPorMes(mes);
        SimpleEntry<Integer, List<ICliente>> vendas3 = this.filial3.vendasPorMes(mes);
        
        List<ICliente> aux = new ArrayList<>(vendas1.getValue());
        for(ICliente c : vendas2.getValue()){
            if(!aux.contains(c))
                aux.add(c.clone());
        }
        for(ICliente c : vendas3.getValue()){
            if(!aux.contains(c))
                aux.add(c.clone());
        }
        
        int[] ret = new int[7];
        ret[0] = vendas1.getKey();
        ret[1] = vendas1.getValue().size();
        ret[2] = vendas2.getKey();
        ret[3] = vendas2.getValue().size();
        ret[4] = vendas3.getKey();
        ret[5] = vendas3.getValue().size();
        ret[6] = aux.size();

        return ret;
    }
    
    /**
     * Metodo que devolve a query 3
     * 
     * @param   c   Cliente a analisar
     * 
     * @return   trio   do nº de vendas e nº de produtos
     */
    public ITrioComprasProdGasto query3(ICliente c){
        int[] total_Compras = new int[12];
        int[] total_Produtos = new int[12];
        double[] total_Gasto = new double[12];
        List<IFilialAux> produtos_distintos = new ArrayList<IFilialAux>();
        
        List<IFilialAux> l1 = this.filial1.determinadaFilial(c);
        for(IFilialAux f : l1){
            total_Compras[f.getMes()-1] += 1;
            total_Gasto[f.getMes()-1] += (double)f.getQuantidade() * f.getPreco();
            if(f.contemProduto(f.getCodProd(), produtos_distintos) == false){
                total_Produtos[f.getMes()-1] += 1;
                produtos_distintos.add(f.clone());
            }
        }
        
        List<IFilialAux> l2 = this.filial2.determinadaFilial(c);
        for(IFilialAux f : l2){
            total_Compras[f.getMes()-1] += 1;
            total_Gasto[f.getMes()-1] += (double)f.getQuantidade() * f.getPreco();
            if(f.contemProduto(f.getCodProd(), produtos_distintos) == false){
                total_Produtos[f.getMes()-1] += 1;
                produtos_distintos.add(f.clone());
            }
        }
        
        List<IFilialAux> l3 = this.filial3.determinadaFilial(c);
        for(IFilialAux f : l3){
            total_Compras[f.getMes()-1] += 1;
            total_Gasto[f.getMes()-1] += (double)f.getQuantidade() * f.getPreco();
            if(f.contemProduto(f.getCodProd(), produtos_distintos) == false){
                total_Produtos[f.getMes()-1] += 1;
                produtos_distintos.add(f.clone());
            }
        }
             
        ITrioComprasProdGasto t = new TrioComprasProdGasto(total_Compras, total_Produtos, total_Gasto);
        return t;
    }
    
    /**
     * Metodo que devolve a query 4
     * 
     * @param   p   Produto a analisar
     * 
     * @return   trio   do nº de compras e nº de clientes
     */
    public ITrioComprasProdGasto query4(IProduto p){
        int[] total_Comprado = new int[12];
        int[] total_Clientes = new int[12];
        double[] total_Faturado = new double[12];
        IFilialAux aux = new FilialAux();
        List<IFilialAux> clientes_distintos = new ArrayList<IFilialAux>();
        List<IFilialAux> lista = new ArrayList<IFilialAux>();
        
        for(List<IFilialAux> l1 : this.filial1.getFilial().values()){
            if(aux.contemProduto(p, l1)){
                lista = aux.produtosIguais(p, l1);
                for(IFilialAux f : lista){ 
                    total_Comprado[f.getMes() - 1] += f.getQuantidade();
                    total_Faturado[f.getMes() - 1] += (double)f.getQuantidade() * f.getPreco();
                    total_Clientes[f.getMes() - 1] += 1;
                    clientes_distintos.add(f.clone());
                }
            }
        }
        
        for(List<IFilialAux> l2 : this.filial2.getFilial().values()){
            if(aux.contemProduto(p, l2)){
                lista = aux.produtosIguais(p, l2);
                for(IFilialAux f : lista){ 
                    total_Comprado[f.getMes() - 1] += f.getQuantidade();
                    total_Faturado[f.getMes() - 1] += (double)f.getQuantidade() * f.getPreco();
                    if(aux.contemCliente(f.getCodCli(), clientes_distintos) == false){
                        total_Clientes[f.getMes() - 1] += 1;
                        clientes_distintos.add(f.clone());
                    }
                }
            }
        }
        
        for(List<IFilialAux> l3 : this.filial3.getFilial().values()){
            if(aux.contemProduto(p, l3)){
                lista = aux.produtosIguais(p, l3);
                for(IFilialAux f : lista){ 
                    total_Comprado[f.getMes() - 1] += f.getQuantidade();
                    total_Faturado[f.getMes() - 1] += (double)f.getQuantidade() * f.getPreco();
                    if(aux.contemCliente(f.getCodCli(), clientes_distintos) == false){
                        total_Clientes[f.getMes() - 1] += 1;
                        clientes_distintos.add(f.clone());
                    }
                }
            }
        }
        
        ITrioComprasProdGasto t = new TrioComprasProdGasto(total_Comprado, total_Clientes, total_Faturado);
        return t;    
    }
    
    /**
     * Metodo que devolve a query 5
     * 
     * @param   c   Cliente a analisar
     * 
     * @return   par   do produto e nº de vendas do mesmo
     */
     public List<IParProdQuantos> query5(ICliente c){
        List<IProduto> l = new ArrayList<IProduto>();
        List<IFilialAux> f1 = this.filial1.determinadaFilial(c);
        List<IFilialAux> f2 = this.filial2.determinadaFilial(c);
        List<IFilialAux> f3 = this.filial3.determinadaFilial(c);
        IParProdQuantos i = new ParProdQuantos();
        List<IParProdQuantos> total = new ArrayList<IParProdQuantos>();
        
        f1.addAll(f2);
        f1.addAll(f3);
        f1 = f1.stream().
                sorted((IFilialAux l1, IFilialAux l2) -> l2.getCodProd().compareTo(l1.getCodProd())).
                collect(Collectors.toList());
        int t = 0;
        for(IFilialAux x : f1){
            if(l.contains((x.getCodProd())) == false){
                i = new ParProdQuantos(x.getQuantidade(), x.getCodProd());
                total.add(i.clone());
                l.add(x.getCodProd().clone());
            }
            else{
                t = i.quantidade_Atual(total, l, x) + x.getQuantidade();
                i.remover(total, l, x);
                i = new ParProdQuantos(t, x.getCodProd());
                total.add(i.clone());
            }
        }
        
        total = total.stream().
                sorted(Comparator.comparing(IParProdQuantos::getQuantos).
                reversed()).collect(Collectors.toList());
                
                
        return total;
    }
    
    
    /**
     * Metodo que devolve a query 6;
     * 
     * @param  x   o numero de produtos que se pretendeobter
     * 
     * @return  lista com todos os pares de produto/nºclientes que compraram (a lista esta ordenada por ordem decrescente dos produtos mais comprados!!)
     */
    public List<IParProdQuantos> query6(int x){
        List<IParProdQuantos> aux = new ArrayList<>();
        List<IParProdQuantos> ret = new ArrayList<>();
        
        aux = this.faturacao.topXMaisVendidos(x);
        
        List<ICliente> fil1 = new ArrayList<>();
        List<ICliente> fil2 = new ArrayList<>();
        List<ICliente> fil3 = new ArrayList<>();

        for(IParProdQuantos par : aux){
            fil1 = filial1.todosCliComprProd(par.getProd());
            fil2 = filial2.todosCliComprProd(par.getProd());
            fil3 = filial3.todosCliComprProd(par.getProd());
            
            for(ICliente cli : fil2){
                if(!fil1.contains(cli))
                    fil1.add(cli.clone());
            }
            for(ICliente cli : fil3){
                if(!fil1.contains(cli))
                    fil1.add(cli.clone());
            }
            
            IParProdQuantos paraux = new ParProdQuantos(fil1.size(), par.getProd());
            ret.add(paraux.clone());
        }
        return ret;
    }
    
    /**
     * Metodo que devolve a query 7;
     * 
     * @return uma lista composta por 3 maps de clientes/total faturado, cada uma com 3 clientes
     */
    public List<Map<ICliente, Double>> query7(){
        List<Map<ICliente, Double>> lista = new ArrayList<>();
        lista.add(this.filial1.getTop3());
        lista.add(this.filial2.getTop3());
        lista.add(this.filial3.getTop3());
        return lista;
    }
    
    /**
     * Metodo auxiliar da query 3
     * 
     * @param   l   Lista a remover os duplicados
     * 
     * @return   lista sem duplicados       
     */
    private  List<IFilialAux> removeDuplicados(List<IFilialAux> l) 
    {  
        ArrayList<IFilialAux> aux = new ArrayList<IFilialAux>(); 
  
        for (IFilialAux f : l) { 
            if (!f.contemProduto(f.getCodProd(), aux)) { 
                aux.add(f.clone()); 
            } 
        } 
        
        return aux; 
    } 
    
    /**
     * Metodo que devolve a query 8
     * 
     * @param  x   o numero de clientes que se pretende obter
     * @return  a lista ordenada com os clientes
     */
    public List<IParCliQuantos> query8(int x){
        List<IProduto> fil1 = new ArrayList<>();
        List<IProduto> fil2 = new ArrayList<>();
        List<IProduto> fil3 = new ArrayList<>();
        List<IParCliQuantos> ret = new ArrayList<>();
        
        for(ICliente cli : this.catCli.getCatCli()){
            fil1 = this.filial1.numeroProdsDiferentes(cli);
            fil2 = this.filial2.numeroProdsDiferentes(cli);
            fil3 = this.filial3.numeroProdsDiferentes(cli);
            
            for(IProduto p : fil2){
                if(!fil1.contains(p))
                    fil1.add(p.clone());
            }
            
            for(IProduto p : fil3){
                if(!fil1.contains(p))
                    fil1.add(p.clone());
            }
            
            IParCliQuantos aux = new ParCliQuantos();
            
            if(ret.size() < x){
                aux = new ParCliQuantos(fil1.size(), cli.clone());
                ret.add(aux.clone());
            }
            else{
                aux = aux.menorQuantidade(ret);
                if(fil1.size() > aux.getQuantos()){
                    ret.remove(aux);
                    aux = new ParCliQuantos(fil1.size(), cli.clone());
                    ret.add(aux.clone());
                }
            }
        }
        
        Collections.sort(ret, new ComparadorParCliQuantos());
        
        return ret;
    }
    
    public List<IParCliQuantos> query9(IProduto p, int x){
        List<ICliente> l = new ArrayList<ICliente>();
        
        IParCliQuantos i = new ParCliQuantos();
        List<IParCliQuantos> total = new ArrayList<IParCliQuantos>();
        
        int t = 0;
        for(List<IFilialAux> lista : this.filial1.getFilial().values()){
            lista = lista.stream().
                sorted((IFilialAux f1, IFilialAux f2) -> f2.getCodProd().compareTo(f1.getCodProd())).
                collect(Collectors.toList());
            for(IFilialAux fi : lista){
                if(fi.getCodProd().equals(p)){
                    if(l.contains(fi.getCodProd()) == false){
                        i = new ParCliQuantos(fi.getQuantidade(), fi.getCodCli());
                        total.add(i.clone());
                        l.add(fi.getCodCli());
                    }
                    else{
                        t = i.quantidade_Atual(total, l, fi) + fi.getQuantidade();
                        i.remover(total, l, fi);
                        i = new ParCliQuantos(t, fi.getCodCli());
                        total.add(i.clone());
                    }
                }
            }
        }
        
        for(List<IFilialAux> lista : this.filial2.getFilial().values()){
            lista = lista.stream().
                sorted((IFilialAux f1, IFilialAux f2) -> f2.getCodProd().compareTo(f1.getCodProd())).
                collect(Collectors.toList());
            for(IFilialAux fi : lista){
                if(fi.getCodProd().equals(p)){
                    if(l.contains(fi.getCodProd()) == false){
                        i = new ParCliQuantos(fi.getQuantidade(), fi.getCodCli());
                        total.add(i.clone());
                        l.add(fi.getCodCli());
                    }
                    else{
                        t = i.quantidade_Atual(total, l, fi) + fi.getQuantidade();
                        i.remover(total, l, fi);
                        i = new ParCliQuantos(t, fi.getCodCli());
                        total.add(i.clone());
                    }
                }
            }
        }
        
        for(List<IFilialAux> lista : this.filial3.getFilial().values()){
            lista = lista.stream().
                sorted((IFilialAux f1, IFilialAux f2) -> f2.getCodProd().compareTo(f1.getCodProd())).
                collect(Collectors.toList());
            for(IFilialAux fi : lista){
                if(fi.getCodProd().equals(p)){
                    if(l.contains(fi.getCodProd()) == false){
                        i = new ParCliQuantos(fi.getQuantidade(), fi.getCodCli());
                        total.add(i.clone());
                        l.add(fi.getCodCli());
                    }
                    else{
                        t = i.quantidade_Atual(total, l, fi) + fi.getQuantidade();
                        i.remover(total, l, fi);
                        i = new ParCliQuantos(t, fi.getCodCli());
                        total.add(i.clone());
                    }
                }
            }
        }  
                
        total = total.stream().
                sorted(Comparator.comparing(IParCliQuantos::getQuantos).
                reversed()).collect(Collectors.toList());
                
        int cont = 0;
        List<IParCliQuantos> ret = new ArrayList<>();
        for(IParCliQuantos parc : total){
            if(cont < x){
                ret.add(parc.clone());
                cont++;
            }
            else
                break;
        }
        return ret;
    
    }
    
    /**
     * Metodo que devolve a query 10
     * 
     * @return  a lista com 3 maps, um para cada filial, que associa os clientes ao seu total faturado
     */
    public List<Map<IProduto, double[]>> query10(){
        Map<IProduto, double[]> fil1 = this.filial1.todosProdsEFat();
        Map<IProduto, double[]> fil2 = this.filial2.todosProdsEFat();
        Map<IProduto, double[]> fil3 = this.filial3.todosProdsEFat();
        
        List<Map<IProduto, double[]>> ret = new ArrayList<>();
        ret.add(fil1);
        ret.add(fil2);
        ret.add(fil3);
        
        return ret;
    }
    
    /**
     * Metodo que devolve a estatistica 1
     * 
     * @return  a string com as informacoes organizadas
     */
    public String estatistica1(){
        String s = "";
        
        int numProds = this.catProd.numeroProdutos();
        int proVendidos = this.faturacao.prodsVendidos();
        int numCli = this.catCli.numeroClientes();
        
        Set<ICliente> fil1 = this.filial1.clientesCompradores();
        Set<ICliente> fil2 = this.filial2.clientesCompradores();
        Set<ICliente> fil3 = this.filial3.clientesCompradores();
        
        for(ICliente cli : fil2){
            if(!fil1.contains(cli))
                fil1.add(cli.clone());
        }
        for(ICliente cli : fil3){
            if(!fil1.contains(cli))
                fil1.add(cli.clone());
        }
        
        SimpleEntry<Integer, Double> total = this.faturacao.comprasA0EFaturacaoTotal();
        
        s += "Ficheiro em memoria: " + this.fichVendas +"\n";
        s += "Numero de vendas invalidas lidas: " + this.vendasInvalidas + "\n";
        s += "Numero total de produtos: " + numProds + "\n";
        s += "Numero total de produtos comprados: " + proVendidos + "\n";
        s += "Numero total de produtos nao comprados: " + (numProds - proVendidos) + "\n";
        s += "Numero total de clientes: " + numCli + "\n";
        s += "Numero total de clientes compradores: " + fil1.size() + "\n";
        s += "Numero total de clientes nao compradores: " + (numCli - fil1.size()) + "\n";
        s += "Numero de compras de valor total igual a 0: " + total.getKey() + "\n";
        s += "Faturacao total: " + total.getValue();
        
        return s;
    }
    
    /**
     * Metodo que devolve a estatistica 2
     * 
     * @return  o triplo com as informacoes de cada mes
     */
    public List<ITrioComprasProdGasto> estatistica2(){
        int[] aux1 = new int[12];
        double[] aux2 = new double[12];
        ITrioComprasProdGasto ret1 = new TrioComprasProdGasto(aux1, aux1, aux2);
        ITrioComprasProdGasto ret2 = new TrioComprasProdGasto(aux1, aux1, aux2);
        ITrioComprasProdGasto ret3 = new TrioComprasProdGasto(aux1, aux1, aux2);
        
        ret1 = this.filial1.determinaEstatisticas();
        ret2 = this.filial2.determinaEstatisticas();
        ret3 = this.filial3.determinaEstatisticas();
        
        List<ITrioComprasProdGasto> ret = new ArrayList<>();
        ret.add(ret1);
        ret.add(ret2);
        ret.add(ret3);
        
        return ret;
    }
    
    /**
     * Metodo que devolve o catalogo de produtos em lista
     * 
     * @return a lista
     */
    public List<IProduto> catProdEmLista(){
        List<IProduto> lis = new ArrayList<>();
        for(IProduto pro : this.catProd.getCatProd())
            lis.add(pro.clone());
        lis.sort(new ComparadorProdutos());
        return lis;
    }
}