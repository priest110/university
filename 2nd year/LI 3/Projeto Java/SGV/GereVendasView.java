import java.util.stream.*;
import java.util.*;
import java.io.*;
import static java.lang.System.out;
import java.util.stream.Collectors;
import java.awt.Dimension;
import java.awt.Toolkit;

/**
 * GereVendasView - implementa um menu de texto
 * 
 * @author (Ana Rita Rosendo, Gonçalo Esteves, Rui Oliveira) 
 * @version 21 de maio de 2019
 */
public class GereVendasView implements InterGereVendasView, Serializable
{
    // variáveis de instância
    private List<String> opcoes;
    
    /**
     * Construtor para objetos da classe GereVendasView.
     */
    public GereVendasView() {
        this.opcoes = new ArrayList<String>();
    }
    
    public GereVendasView(GereVendasView g){
        this.opcoes = g.getOpcoes();
    }
    
    public List<String> getOpcoes(){
        List<String> aux = new ArrayList<>();
        
        for(String m : this.opcoes){
            aux.add(m);
        }
         return aux;
    }
    
    public void setOpcoes(String[] opcoes){
       this.opcoes = Arrays.asList(opcoes);
    }
    
    /**
     * Metodo que duplica a InterGereVendasView
     * 
     * @return   o clone da InterGereVendasView
     */
    public GereVendasView clone(){
        return new GereVendasView(this);
    }
    
    /** 
     * Metodo que apresenta o menu.
     */
    private void mostraMenu() {
        out.println("\n *** GestVendas *** ");
        for (int i=0; i < this.opcoes.size(); i++) {
            out.print(i+1);
            out.print(" - ");
            out.println(this.opcoes.get(i));
        }
        out.println("0 - Sair");
    }
    
    /**
     * Metodo que le uma opçao do menu.
     * 
     * @return opcao lida
     */
    private int leMenu() {
        int opcao; 
        
        out.print("Opção: ");
        try {
            opcao = Input.lerInt();
        }
        catch (InputMismatchException e) { // Não foi inscrito um inteiro
            opcao = -1;
        }
        if (opcao < 0 || opcao > this.opcoes.size()) {
            out.println("Opção Inválida!");
            opcao = -1;
        }
        return opcao;
    }
    
    /**
     * Método para apresentar o menu e ler uma opção.
     * 
     * @return opcao escolhida
     */
    private int executa() {
        mostraMenu();
        int opcao = leMenu();
        if(opcao == -1)
            executa();
        return opcao;
    }

    /**
     * Executa o menu inicial e invoca o método correspondente à opção seleccionada.
     */    
    public int menu_Inicial() {
        String[] opcoes_Inicial = {"Ler ficheiros.", "Consultas estatisticas.", 
                                   "Consultas interativas.", "Guardar estado atual.", 
                                   "Carregar estado."};
        setOpcoes(opcoes_Inicial);
        
        return executa();
    }  
    
    /**
     * Executa o menu principal e invoca o método correspondente à opção seleccionada.
     */    
    public int menu_Queries() {
        String[] opcoes_Queries = {"Lista ordenada alfabeticamente com os códigos dos produtos nunca comprados e o seu respectivo total.", 
            "Dado um mês válido, determinar o nº total global de vendas realizadas e o nº total de clientes distintos que as fizeram.", 
            "Dado um código de cliente, determinar, para cada mês, quantas compras fez, quantos produtos distintos comprou e quanto gastou no total.",
            "Dado o código de um produto existente, determinar, mês a mês, quantas vezes foi comprado, por quantos clientes diferentes e o total facturado.", 
            "Dado o código de um cliente, determinar a lista de códigos de produtos que mais comprou (e quantos).",
            "Determinar o conjunto dos X produtos mais vendidos em todo o ano indicando o número total de distintos clientes que o compraram.",
            "Determinar, para cada filial, a lista dos três maiores compradores em termos de dinheiro facturado.",
            "Determinar os códigos dos X clientes que compraram mais produtos diferentes, indicando quantos.",
            "Dado o código de um produto que deve existir, determinar o conjunto dos X clientes que mais o compraram e, para cada um, qual o valor gasto.",
            "Determinar mês a mês, e para cada mês filial a filial, a facturação total com cada produto."};
        setOpcoes(opcoes_Queries);
        
        return executa();
    }  
    
    /**
     * Executa o menu principal e invoca o método correspondente à opção seleccionada.
     */    
    public int menu_Estatisticas() {
        String[] opcoes_Estatisticas = {"Dados referentes ao ultimo ficheiro de vendas lido.", 
            "Dados registados nas estruturas"};
        setOpcoes(opcoes_Estatisticas);
        
        return executa();
    }
    
    /**
     * Executa o menu secundario das estatisticas
     */
    public int menu_EstatisticasSec(){
        String[] opcoes_secundarias = {"Total de compras por mes", "Faturacao por mes e filial", "Numero de clientes que compraram por mes e filial"};
        setOpcoes(opcoes_secundarias);
        
        return executa();
    }
    
    /**
     * Executa o menu principal e invoca o método correspondente à opção seleccionada.
     */    
    public int menu_Ficheiros() {
        out.println("-Que ficheiro de vendas pretende ler?-");
        String[] opcoes_Ficheiros = {"Vendas_1M.txt","Vendas_3M.txt","Vendas_5M.txt"};
        setOpcoes(opcoes_Ficheiros);
        
        return executa();
    } 
    
    /**
     * Mostra ao utilizador o resultado da query 1
     * 
     * @param   l   lista de produtos
     */  
    public void query1(List<IProduto> l){
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        
        int numProdsLinha = 20;
        int numLinhasPorPag = 10;
        int numlinhas = l.size()/numProdsLinha;
        int numPags = numlinhas/numLinhasPorPag;
        
        out.println("Total de produtos nunca comprados: " + l.size());
        out.println("Que pagina pretende ler? (0 a " + numPags + ")");
        int pag = Input.lerInt();
        
        int flag = 1;
        while(flag == 1){
            if(pag < 0 || pag > numPags)
                flag = 0;
                
            else{
                int n = 0;
                String aux = "";
                out.println("Pagina " + pag + ":");
                int contador = pag * (numProdsLinha * numLinhasPorPag);
                int limite = (pag + 1) * (numProdsLinha * numLinhasPorPag);
                if(limite > l.size())
                    limite = l.size();
                int flag2 = 1;
                
                for(; (contador < limite) && (flag2 == 1); contador++){
                    if(n < numLinhasPorPag){
                        if((aux.length() + 7) <= numProdsLinha*7){
                            IProduto p = l.get(contador);
                            aux += p.getCodigo();
                            aux += " ";
                        }
                        else{
                            out.println(aux + "\n");
                            aux = "";
                            n++;
                            contador--;
                        }
                    }
                    else
                        flag2 = 0;
                }
                
                if(contador == limite)
                    out.println(aux + "\n");
                
                out.println("Acabar a leitura(0)/ Proxima Pagina(1)/ Pagina Anterior(2)");
                int pro = Input.lerInt();
                
                if(pro == 1)
                    pag++;
                else if(pro == 2)
                    pag--;
                else
                    flag = 0;
            }
        }
    }
    
    /**
     * Mostra ao utilizador o resultado da query 2
     * 
     * @param   mes   dado pelo utilizador
     * @param   total  array com todos os totais necessarios
     */  
    public void query2(int mes, int[] total){
        int total_Vendas = total[0] + total[2] + total [4];
        int total_Clientes =  total[6];
        out.println("No mes " + mes + " foram efetuadas " + total_Vendas + " vendas por um total de " + total_Clientes + " clientes distintos:");
        out.println("  Filial 1 -> " + total[0] + " vendas por " + total[1] + " clientes;");
        out.println("  Filial 2 -> " + total[2] + " vendas por " + total[3] + " clientes;");
        out.println("  Filial 3 -> " + total[4] + " vendas por " + total[5] + " clientes;");
    }
    
    /**
     * Mostra ao utilizador o resultado da query 3
     * 
     * @param   t   trio dado pelo utilizador
     * @param   cliente    cliente dado pelo utilizador
     */  
    public void query3(ITrioComprasProdGasto t, String cliente){
        int[] comp = t.getCompras();
        int[] prod = t.getProdutos();
        double[] gast = t.getGasto();
        
        for(int i = 1; i < 13; i++){
            if(comp[i-1] != 0)
                out.println("- Mes " + i + ": o cliente " + cliente + " fez " + comp[i-1] + " compras, nas quais comprou " + prod[i-1] + " produtos distintos. Gastou " + gast[i-1] + " euros");
        }
        out.println("No total do ano, gastou " + DoubleStream.of(gast).sum() + " euros.");
    }
    
    /**
     * Mostra ao utilizador o resultado da query 4
     * 
     * @param   t   trio dado pelo utilizador
     * @param   produto    cliente dado pelo utilizador
     */  
    public void query4(ITrioComprasProdGasto t, String produto){
        int[] comp = t.getCompras();
        int[] prod = t.getProdutos();
        double[] gast = t.getGasto();
        
        for(int i = 1; i < 13; i++){
            if(comp[i-1] != 0)
                out.println("- Mes " + i + ": o cliente " + produto + " foi " + comp[i-1] + " vezes comprado, por " + prod[i-1] + " clientes distintos. Com este produto, foi faturado " + gast[i-1] + " euros.");
        }
        out.println("No total do ano, com este produto foi faturado " + DoubleStream.of(gast).sum() + " euros.");
    }
    
    
    /**
     * Mostra ao utilizador o resultado da query 5
     * 
     * @param   p   par dado pelo utilizador
     */  
    public void query5(List<IParProdQuantos> aux){
        int n = aux.size();
        int prodPorPag = 10;
        int nPags = n/prodPorPag;
        
        out.println("Que pagina pretende ler? (0 a " + nPags + ")");
        int pag = Input.lerInt();
        int flag = 1;
        int contador;
        int limite;
        IParProdQuantos par = new ParProdQuantos();
        while(flag == 1){
            if(pag < 0 || pag > nPags)
                flag = 0;
            else{
                contador = pag*prodPorPag;
                limite = (pag + 1)*prodPorPag;
                if(limite > n)
                    limite = n;
                
                for(; contador < limite; contador++){
                    par = aux.get(contador);
                    out.println(par.toString());
                }
                
                if(contador == limite)
                    out.println("\n");
                
                out.println("Acabar a leitura(0)/ Proxima Pagina(1)/ Pagina Anterior(2)");
                int pro = Input.lerInt();
                
                if(pro == 1)
                    pag++;
                else if(pro == 2)
                    pag--;
                else
                    flag = 0;
            }
        }
    }
    
    /**
     * Mostra ao utilizador os pares de Produtos comprados e numero de clientes que o compraram 
     * 
     * @param  aux   a lista com os pares de produto/numero de clientes
     */
    public void query6(List<IParProdQuantos> aux){
        int n = aux.size();
        int prodPorPag = 10;
        int nPags = n/prodPorPag;
        
        out.println("Que pagina pretende ler? (0 a " + nPags + ")");
        int pag = Input.lerInt();
        int flag = 1;
        int contador;
        int limite;
        IParProdQuantos par = new ParProdQuantos();
        while(flag == 1){
            if(pag < 0 || pag > nPags)
                flag = 0;
            else{
                contador = pag*prodPorPag;
                limite = (pag + 1)*prodPorPag;
                if(limite > n)
                    limite = n;
                
                for(; contador < limite; contador++){
                    par = aux.get(contador);
                    out.println(par.getProd().toString() + "Nº de Clientes que compraram: " + par.getQuantos() + "\n");
                }
                
                if(contador == limite)
                    out.println("\n");
                
                out.println("Acabar a leitura(0)/ Proxima Pagina(1)/ Pagina Anterior(2)");
                int pro = Input.lerInt();
                
                if(pro == 1)
                    pag++;
                else if(pro == 2)
                    pag--;
                else
                    flag = 0;
            }
        }
    }
    
    /**
     * Mostra ao utilizador o top 3 de clientes que mais dinheiro gastaram nas 3 filiais
     * 
     * @param lis  a lista com as informacoes
     */
    public void query7(List<Map<ICliente, Double>> lis){
        int i = 0;
        ICliente cli = new Cliente();
        Filial f = new Filial();
        for(Map<ICliente, Double> map : lis){
            out.println("Filial " + (i+1) + ":");
            for(int j = map.size(); j > 0; j--){
                cli = f.cliMenosGasto(map);
                out.println(cli.toString() + "Total de dinheiro gasto: " + map.get(cli));
                map.remove(cli);
            }
            out.print("\n");
            i++;
        }
    }
    
    /**
     * Mosta ao utilizador o top X de clientes com mais produtos diferentes comprados
     * 
     * @param  lis   a lista com os pares cliente/numero de prods diferentes comprados
     */
    public void query8(List<IParCliQuantos> aux){
        int n = aux.size();
        int prodPorPag = 10;
        int nPags = n/prodPorPag;
        
        out.println("Que pagina pretende ler? (0 a " + nPags + ")");
        int pag = Input.lerInt();
        int flag = 1;
        int contador;
        int limite;
        IParCliQuantos par = new ParCliQuantos();
        while(flag == 1){
            if(pag < 0 || pag > nPags)
                flag = 0;
            else{
                contador = pag*prodPorPag;
                limite = (pag + 1)*prodPorPag;
                if(limite > n)
                    limite = n;
                
                for(; contador < limite; contador++){
                    par = aux.get(contador);
                    out.println(par.getCli().toString() + "Numero de diferentes produtos comprados: " + par.getQuantos() + "\n"); 
                }
                
                if(contador == limite)
                    out.println("\n");
                
                out.println("Acabar a leitura(0)/ Proxima Pagina(1)/ Pagina Anterior(2)");
                int pro = Input.lerInt();
                
                if(pro == 1)
                    pag++;
                else if(pro == 2)
                    pag--;
                else
                    flag = 0;
            }
        }
    }
    
    /**
     * Mostra ao utilizador o resultado da query 9
     * 
     * @param   aux   par dado pelo utilizador
     */  
    public void query9(List<IParCliQuantos> aux){
        int n = aux.size();
        int prodPorPag = 10;
        int nPags = n/prodPorPag;
        
        out.println("Que pagina pretende ler? (0 a " + nPags + ")");
        int pag = Input.lerInt();
        int flag = 1;
        int contador;
        int limite;
        IParCliQuantos par = new ParCliQuantos();
        while(flag == 1){
            if(pag < 0 || pag > nPags)
                flag = 0;
            else{
                contador = pag*prodPorPag;
                limite = (pag + 1)*prodPorPag;
                if(limite > n)
                    limite = n;
                
                for(; contador < limite; contador++){
                    par = aux.get(contador);
                    out.println(par.toString());
                }
                
                if(contador == limite)
                    out.println("\n");
                
                out.println("Acabar a leitura(0)/ Proxima Pagina(1)/ Pagina Anterior(2)");
                int pro = Input.lerInt();
                
                if(pro == 1)
                    pag++;
                else if(pro == 2)
                    pag--;
                else
                    flag = 0;
            }
        }
    }
    
    /**
     * Mostra ao utilizador o resultado da query10
     * 
     * @param  aux   a lista com os 3 maps (um por filial) com os codigos de produto associados ao total faturado por mes
     * @param  s   o set com as todos os produtos existentes
     */
    public void query10(List<Map<IProduto, double[]>> aux, List<IProduto> s){
        int r = 1;
        while(r == 1){
            out.println("Qual mes pretende analisar? (0 para acabar com a pesquisa)");
            int op = Input.lerInt();
            if(op < 1 || op > 12)
                r = 0;
            else{
                mostrarTotalProdsMes(op, aux, s);
            }
        }
    }
    
    public void mostrarTotalProdsMes(int mes, List<Map<IProduto, double[]>> aux, List<IProduto> s){
        double[] vazio = new double[12];
        for(int i = 0; i < 12; i++)
            vazio[i] = 0;
        double[] ajuda;
        int nLinhas = 38;
        int nProdutos = 3;
        int nPags = (s.size()/(nLinhas*nProdutos));
        int r = 1;
        Map<IProduto, double[]> fil1 = aux.get(0);
        Map<IProduto, double[]> fil2 = aux.get(1);
        Map<IProduto, double[]> fil3 = aux.get(2);
        
        int tamanhoMax = 20;
        
        int contador, limite;
        String esc = "";
        IProduto pro = new Produto();
        out.println("Que pagina pretende ler? (0 a " + nPags + ")");
        int pag = Input.lerInt();
        
        while(r == 1){
            if(pag < 0 || pag > nPags)
                r = 0;
            else{
                contador = pag*(nProdutos*nLinhas);
                limite = (pag + 1)*(nProdutos*nLinhas);
                if(limite > s.size())
                    limite = s.size();
                while(contador < limite){
                    for(int j = 0; j < nLinhas && contador < limite; j++){
                        for(int i = 0; i < nProdutos && contador < limite; i++){
                            pro = s.get(contador);
                            
                            esc += (pro.getCodigo() + " - ");
                            
                            ajuda = fil1.getOrDefault(pro, vazio);
                            esc += (ajuda[mes-1] + " ");
                            while(esc.length() < (tamanhoMax + 9))
                                esc += " ";
                                
                            ajuda = fil2.getOrDefault(pro, vazio);
                            esc += (ajuda[mes-1] + " ");
                            while(esc.length() < ((2*tamanhoMax) + 9))
                                esc += " ";
                                
                            ajuda = fil3.getOrDefault(pro, vazio);
                            esc += (ajuda[mes-1] + " ");
                            while(esc.length() < ((3*tamanhoMax) + 9))
                                esc += " ";
                            esc += "|";
                            contador++;
                            out.print(esc);                            
                            esc = "";
                        }
                        out.println("\n");
                    }
                }
                
                if(contador == limite)
                    out.println("\n");
                
                out.println("Acabar a leitura(0)/ Proxima Pagina(1)/ Pagina Anterior(2)");
                int p = Input.lerInt();
                
                if(p == 1)
                    pag++;
                else if(p == 2)
                    pag--;
                else
                    r = 0;
            }
        }
        
    }
    
    /**
     * Mostra ao utilizador as estatisticas, por mes, de total faturado, numero total de compras e numero de clientes que efetuaram compras
     * 
     * @param  trio   lista com os tres trios com as informacoes
     */
    public void estatisticas2(List<ITrioComprasProdGasto> trio, int n){
        ITrioComprasProdGasto trio1 = trio.get(0);
        ITrioComprasProdGasto trio2 = trio.get(1);
        ITrioComprasProdGasto trio3 = trio.get(2);
        
        int[] compras1 = trio1.getCompras();
        int[] compras2 = trio2.getCompras();
        int[] compras3 = trio3.getCompras();
        
        double[] fat1 = trio1.getGasto();
        double[] fat2 = trio2.getGasto();
        double[] fat3 = trio3.getGasto();
        
        int[] clientes1 = trio1.getProdutos();
        int[] clientes2 = trio2.getProdutos();
        int[] clientes3 = trio3.getProdutos();
        
        int r = 1;
        while(r == 1){
            int op = menu_EstatisticasSec();
            switch(op){
                case 1 : for(int i = 0; i < n; i++){
                            out.println("Mes " + (i+1) + ":");
                            out.println("Total de compras: " + (compras1[i] +compras2[i] + compras3[i]));
                         }
                         break;
                case 2 : double fatTotal = 0;
                         out.println("              Filial 1      /       Filial 2       /       Filial 3");
                         for(int i = 0; i < n; i++){
                            if(i < 9)
                                out.println("Mes: " + (i+1) + " - " + fat1[i] + "  " + fat2[i] + "  " + fat3[i]);
                            else
                                out.println("Mes:" + (i+1) + " - " + fat1[i] + "  " + fat2[i] + "  " + fat3[i]);
                            fatTotal += fat1[i];
                            fatTotal += fat2[i];
                            fatTotal += fat3[i];
                         }
                         out.println("Total faturado: " + fatTotal);
                         break;
                case 3 : out.println("          Filial 1/Filial 2/Filial 3");
                         for(int i = 0; i < n; i++){
                            if(i < 9)
                                out.println("Mes: " + (i+1) + "  -  " + clientes1[i] + "    " + clientes2[i] + "    " + clientes3[i]);
                            else
                                out.println("Mes:" + (i+1) + "  -  " + clientes1[i] + "    " + clientes2[i] + "    " + clientes3[i]);
                         }
                         break;
                case 0 : r = 0;
                         break;
            }
        }
    }
    
    /**
     * Mostra ao utilizador as informacoes guardadas nas estruturas;
     * 
     * @param  s   a string com as informacoes
     */
    public void estatisticas1(String s){
        out.println(s);
    }
}
