import java.util.*;
import java.io.*;
import static java.lang.System.out;
import java.util.AbstractMap.SimpleEntry;
/**
 * Escreva a descrição da classe GereVendasController aqui.
 * 
 * @author (seu nome) 
 * @version (número de versão ou data)
 */
public class GereVendasController implements InterGereVendasController, Serializable
{
    private InterGereVendasModel model;
    private InterGereVendasView view;
    
    public GereVendasController(){
        this.model = new GereVendasModel();
        this.view  = new GereVendasView();
    }
    
    public void setModel(InterGereVendasModel x){
        this.model = x.clone();
    }
    
    public InterGereVendasModel getModel(){
        return this.model.clone();
    }
    
    public void setView(InterGereVendasView y){
        this.view = y.clone();
    }
    
    public InterGereVendasView getView(){
        return this.view.clone();
    }
    
    public void startController(){
        int r = 1;
        while(r == 1){
            int op = this.view.menu_Inicial();
            switch(op){
                case 1: lerFicheiros();
                            break;
                case 2: estatisticas();
                            break;
                case 3: consultarQueries();
                            break;
                case 4: guardaEstadoFicheiro();
                            break;
                case 5: abreEstadoGuardado();
                            break;            
                case 0: System.out.println("Obrigado pela preferencia. Volte sempre.");
                        r = 0;
                            break;
            }
        }
    }
    
    public void lerFicheiros(){
        int op = view.menu_Ficheiros();
        switch(op){
            case 1: this.model.carregarDados("Vendas_1M.txt");
                        break;
            case 2: this.model.carregarDados("Vendas_3M.txt");
                        break;
            case 3: this.model.carregarDados("Vendas_5M.txt");
                        break;         
            case 0:     break;
            }
    }
    
    public void estatisticas(){
        int r = 1;
        while(r == 1){
            int op = this.view.menu_Estatisticas();
            switch(op){
                case 1 : estatisticas_1();
                         break;
                case 2 : estatisticas_2();
                         break;
                case 0 : r = 0;
                         break;
            }
        }
    }
    
    public void consultarQueries(){        
        int r = 1;
        while(r == 1){
            int op = this.view.menu_Queries();
            switch(op){
                case 1: query_1();
                            break;
                case 2: query_2();
                            break;
                case 3: query_3();
                            break;  
                case 4: query_4();
                            break;
                case 5: query_5();
                            break;
                case 6: query_6();
                            break;
                case 7: query_7();
                            break;
                case 8: query_8();
                            break;
                case 9: query_9();
                            break;   
                case 10: query_10();
                            break;            
                case 0: r = 0;
                            break;
            }
        }
    }
    
    /**
     * Metodo que abre um ficheiro
     * 
     * @return Dados do ficheiro lido
     */
    private static GereVendasController abrirFicheiro(String nomeFicheiro) throws FileNotFoundException, IOException, ClassNotFoundException, ClassCastException{
        FileInputStream in = new FileInputStream(nomeFicheiro);
        ObjectInputStream o = new ObjectInputStream(in);
        GereVendasController d = (GereVendasController) o.readObject();
        o.close();
        return d;
    }
    
    /**
     * Metodo que guarda os dados num ficheiro
     * 
     */
    private void guardaFicheiro(String nomeFicheiro) throws FileNotFoundException, IOException{
        FileOutputStream out = new FileOutputStream(nomeFicheiro);
        ObjectOutputStream o = new ObjectOutputStream(out);
        o.writeObject(this);
        o.flush();
        o.close();
    }
    
    /**
     * Metodo que inicializa um estado previamente guardado em ficheiro
     * 
     * @param   fich  o ficheiro onde esta o ficheiro guardado
     */
    public void abreEstadoGuardado(){
        InterGereVendasController aux = new GereVendasController();
        out.println("Qual o ficheiro onde esta guardado o estado?");
        String fich = Input.lerString();
        try {
            aux = abrirFicheiro(fich);
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
            System.out.println("Dados nao lidos!\nFicheiro nao encontrado.");
            aux = new GereVendasController();
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Dados nao lidos!\nFicheiro com formato desconhecido.");
            aux = new GereVendasController();
        }
        catch (IOException e) {
            e.printStackTrace();
            System.out.println("Dados nao lidos!\nErro de leitura.");
            aux = new GereVendasController();
        }
        catch (ClassCastException e) {
            e.printStackTrace();
            System.out.println("Dados nao lidos!\nErro de formato.");
            aux = new GereVendasController();
        }
        
        this.setModel(aux.getModel());
        this.setView(aux.getView());
    }
    
    /**
     * Metodo que guarda um estado num ficheiro
     * 
     * @param   fich  o ficheiro onde e para guardar o ficheiro
     */
    public void guardaEstadoFicheiro(){
        out.println("Qual o ficheiro onde deve se guardado o estado?");
        String fich = Input.lerString();
        try {
            this.guardaFicheiro(fich);
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
            System.out.println("Dados nao guardados!\nFicheiro nao encontrado.");
        }
        catch (IOException e) {
            e.printStackTrace();
            System.out.println("Dados nao guardados!\nErro de escrita.");
        }
    }
    
    public void estatisticas_1(){
        String ret = this.model.estatistica1();
        this.view.estatisticas1(ret);
    }
    
    public void estatisticas_2(){
        List<ITrioComprasProdGasto> ret = this.model.estatistica2();
        this.view.estatisticas2(ret, 12);
    }
    
    /**
     * Metodo responsavel por comunicar a query 1 do model para o view
     */
    public void query_1(){
        Crono.start();
        List<IProduto> l = this.model.query1();
        out.println(Crono.stop());
        this.view.query1(l);
    }
    
    /**
     * Metodo responsavel por comunicar a query 2 do model para o view
     */
    public void query_2(){
        out.print("Indique o mes que prente analisar: ");
        int mes = Input.lerInt();
        Crono.start();
        int[] total = this.model.query2(mes);
        out.println(Crono.stop());
        this.view.query2(mes, total);
       
    }
    
    /**
     * Metodo responsavel por comunicar a query 3 do model para o view
     */
    public void query_3(){
        out.print("Indique o codigo do Cliente que prente analisar: ");
        String s = Input.lerString();
        Crono.start();
        ICliente c = new Cliente(s);
        ITrioComprasProdGasto t = this.model.query3(c);
        out.println(Crono.stop());
        this.view.query3(t, s);
    }
    
    /**
     * Metodo responsavel por comunicar a query 4 do model para o view
     */
    public void query_4(){
        out.print("Indique o codigo do Produto que prente analisar: ");
        String s = Input.lerString();
        Crono.start();
        IProduto p = new Produto(s);
        ITrioComprasProdGasto t = this.model.query4(p);
        out.println(Crono.stop());
        this.view.query4(t, s);
    }
    
    /**
     * Metodo responsavel por comunicar a query 5 do model para o view
     */
    public void query_5(){
        out.print("Indique o codigo do Cliente que prente analisar: ");
        String s = Input.lerString();
        Crono.start();
        ICliente c = new Cliente(s);
        List<IParProdQuantos> l = this.model.query5(c);
        out.println(Crono.stop());
        this.view.query5(l);
    }
    
    /**
     * Metodo responsavel por comunicar a query 6 do model para o view
     */
    public void query_6(){
        out.println("Quantos produtos pretende analisar?");
        int x = Input.lerInt();
        Crono.start();
        List<IParProdQuantos> aux = this.model.query6(x);
        out.println(Crono.stop());
        this.view.query6(aux);
    }
    
    /**
     * Metodo responsavel por comunicar a query 7 do model para o view
     */
    public void query_7(){
        Crono.start();
        List<Map<ICliente, Double>> aux = this.model.query7();
        out.println(Crono.stop());
        this.view.query7(aux);
    }
    
    /**
     * Metodo responsavel por comunicar a query 8 do model para o view
     */
    public void query_8(){
        out.println("Quantos clientes pretende analisar?");
        int x = Input.lerInt();
        Crono.start();
        List <IParCliQuantos> aux = this.model.query8(x);
        out.println(Crono.stop());
        this.view.query8(aux);
    }
    
    /**
     * Metodo responsavel por comunicar a query 9 do model para o view
     */
    public void query_9(){
        out.print("Indique o codigo do Produto que prente analisar: ");
        String s = Input.lerString();
        out.println("Quantos clientes pretende analisar?");
        int x = Input.lerInt();
        Crono.start();
        IProduto p = new Produto(s);
        List<IParCliQuantos> l = this.model.query9(p, x);
        out.println(Crono.stop());
        this.view.query9(l);
    }
    
    /**
     * Metodo responsavel por comunicar a query 10 do model para o view
     */
    public void query_10(){
        Crono.start();
        List<Map<IProduto, double[]>> ret = this.model.query10();
        out.println(Crono.stop());
        this.view.query10(ret, this.model.catProdEmLista());
    }
}
