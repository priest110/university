import java.util.*;
import java.io.*;
import static java.lang.System.out;
import java.util.stream.Collectors;
import java.util.AbstractMap.SimpleEntry;
/**
 * Escreva a descrição da interface InterGereVendasModel aqui.
 * 
 * @author (seu nome) 
 * @version (número da versão ou data)
 */

public interface InterGereVendasModel extends Serializable
{
    /**
     * Metodo que devolve o catCli de um conjunto de GereVendasModel
     * 
     * @return     o catCli dos GereVendasModel
     */
    public ICatCli getCatCli();
    
    /**
     * Metodo que altera os catCli de um conjunto de cados
     * 
     * @param  cli   o novo catCli
     */
    public void setCatCli(ICatCli cli);
    
    /**
     * Metodo que devolve o catProd de um conjunto de GereVendasModel
     * 
     * @return     o catProd dos GereVendasModel
     */
    public ICatProd getCatProd();
    
    /**
     * Metodo que altera o catProd de um conjunto de cados
     * 
     * @param  pro   o novo catProd
     */
    public void setCatProd(ICatProd pro);
    
    /**
     * Metodo que devolve a faturacao de um conjunto de dados
     * 
     * @return     a faturacao dos dados
     */
    public IFaturacao getFaturacao();
    
    /**
     * Metodo que altera a faturacao de um conjunto de cados
     * 
     * @param  fat   a nova faturacao
     */
    public void setFaturacao(IFaturacao fat);
    
    /**
     * Metodo que devolve a filial1 de um conjunto de dados
     * 
     * @return     a filial1 dos dados
     */
    public IFilial getFilial1();
    
    /**
     * Metodo que altera a filial1 de um conjunto de cados
     * 
     * @param  f   a nova filial1
     */
    public void setFilial1(IFilial  f);
    
    /**
     * Metodo que devolve a filial2 de um conjunto de dados
     * 
     * @return     a filial2 dos dados
     */
    public IFilial  getFilial2();
    
    /**
     * Metodo que altera a filial2 de um conjunto de cados
     * 
     * @param  f   a nova filial2
     */
    public void setFilial2(IFilial  f);
    
    /**
     * Metodo que devolve a filial3 de um conjunto de dados
     * 
     * @return     a filial3 dos dados
     */
    public IFilial  getFilial3();
    
    /**
     * Metodo que altera a filial3 de um conjunto de cados
     * 
     * @param  f   a nova filial3
     */
    public void setFilial3(IFilial  f);
    
    /**
     * Metodo que duplica o InterGereVendasModel
     * 
     * @return   o clone do InterGereVendasModel
     */
    public InterGereVendasModel clone();
    
    /**
     * Metodo que verifica se dois conjuntos de GereVendasModel sao iguais
     * 
     * @param  o   o objeto a comparar
     * 
     * @return     o resultado da comparacao dos objetos
     */
    public boolean equals(Object o);
    
    /**
     * Metodo que converte um conjunto de GereVendasModel para uma string
     * 
     * @return     o conjunto de GereVendasModel em string
     */
    public String toString();
    
    /**
     * Metodo que devolve o codigo de hash para um proprietario
     * 
     * @return     o hashcode
     */
    public int hashCode();
    
    /*
                             ***** METODOS DE LEITURAS DE FICHEIROS *****
    */
    
    /**
     * Metodo que converte um ficheiro num Set de Produtos
     * 
     * @return     o conjunto de clientes num Set
     */
    public void lerFichWithBuff_Produtos(String fichtxt);
    
    /**
     * Metodo que converte um ficheiro num Set de Clientes
     * 
     * @return     o conjunto de clientes num Set
     */
    public void lerFichWithBuff_Clientes(String fichtxt);
    
    /**
     * Metodo que converteum ficheiro numa Arvore de Faturas
     * 
     * @return     o conjunto de faturas em arvores
     */
    public void lerFichWithBuff_Vendas(String fichtxt);
    
    /*
                             ***** METODOS AUXILIARES DE CARREGAMENTO DE DADOS *****
    */
    
    /**
     * Metodo que carregas os dados dos ficheiros para as estruturas representativas
     * 
     * @param  fichVendas  o ficheiro de vendas a ler
     */
    public void carregarDados(String fichVendas);
    
    /*
                             ***** METODOS QUERIES *****
    */
    
    /**
     * Metodo que devolve a query 1
     * 
     * @return  lista de produtos nunca comprados
     */
    public List<IProduto> query1();

    /**
     * Metodo que devolve a query 2
     * 
     * @param  mes   o mes a analisar
     * 
     * @return  array com as informacoes necessarias (1ªpos, 3ªpos e 5ªpos - numero de vendas nas filiais 1, 2 e 3, respetivamente; 
     * 2ªpos, 4ªpos e 6ªpos - numero de clientes que compraram nas filiais 1, 2 e 3; 7ªpos - numero de clientes que fizeram compras em pelo menos uma filial)
     */
    public int[] query2(int mes);
    
    /**
     * Metodo que devolve a query 3
     * 
     * @param   c   Cliente a analisar
     * 
     * @return   trio   do nº de vendas e nº de produtos
     */
    public ITrioComprasProdGasto query3(ICliente c);
    
    /**
     * Metodo que devolve a query 4
     * 
     * @param   p   Produto a analisar
     * 
     * @return   trio   do nº de compras e nº de clientes
     */
    public ITrioComprasProdGasto query4(IProduto p);
    
    /**
     * Metodo que devolve a query 5
     * 
     * @param   c   Cliente a analisar
     * 
     * @return   par   do produto e nº de vendas do mesmo
     */
     public List<IParProdQuantos> query5(ICliente c);
     
     
    /**
     * Metodo que devolve a query 6;
     * 
     * @param  x   o numero de produtos que se pretendeobter
     * 
     * @return  lista com todos os pares de produto/nºclientes que compraram (a lista esta ordenada por ordem decrescente dos produtos mais comprados!!)
     */
    public List<IParProdQuantos> query6(int x);
    
    /**
     * Metodo que devolve a query 7;
     * 
     * @return uma lista composta por 3 maps de clientes/total faturado, cada uma com 3 clientes
     */
    public List<Map<ICliente, Double>> query7();
    
    /**
     * Metodo que devolve a query 8
     * 
     * @param  x   o numero de clientes que se pretende obter
     * @return  a lista ordenada com os clientes
     */
    public List<IParCliQuantos> query8(int x);
    
    /**
     * Metodo que devolve a query 9
     * 
     * @param  p   o produto a analisar
     * @return  a lista com os pares cliente/quantidade
     */
    public List<IParCliQuantos> query9(IProduto p, int x);
    
    /**
     * Metodo que devolve a query 10
     * 
     * @return  a lista com 3 maps, um para cada filial, que associa os clientes ao seu total faturado
     */
    public List<Map<IProduto, double[]>> query10();
    
    /**
     * Metodo que devolve a estatistica 1
     * 
     * @return  a string com as informacoes organizadas
     */
    public String estatistica1();
    
    /**
     * Metodo que devolve a estatistica 2
     * 
     * @return  o triplo com as informacoes de cada mes
     */
    public List<ITrioComprasProdGasto> estatistica2();
    
    /**
     * Metodo que devolve o catalogo de produtos em lista
     * 
     * @return a lista
     */
    public List<IProduto> catProdEmLista();
}
