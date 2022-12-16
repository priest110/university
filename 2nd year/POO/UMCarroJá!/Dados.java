import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Collections;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.Date;
import java.time.LocalDate;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Dados - Registo com todos os veiculos, clientes e proprietarios existentes no sistema.
 * 
 * @author (Ana Rita Rosendo, Gonçalo Esteves, Rui Oliveira) 
 * @version 21 de maio de 2019
 */
public class Dados implements Serializable{
    private Map<String, Cliente> clientes; //email, cliente
    private Map<String, Proprietario> proprietarios; //email, proprietario
    private Map<String, Veiculo> veiculos; //matricula, veiculo

    /**
     * Construtor para objetos da classe Dados (por omissao)
     */
    public Dados(){
        this.clientes = new TreeMap<>();
        this.proprietarios = new TreeMap<>();
        this.veiculos = new TreeMap<>();
    }
    
    /**
     * Construtor para objetos da classe Dados (parameterizado)
     * 
     * @param  cli   os clientes
     * @param  pro   os proprietarios
     * @param  vei   os veiculos
     */
    public Dados(Map<String, Cliente> cli, Map<String, Proprietario> pro, Map<String, Veiculo> vei){
        this.setClientes(cli);
        this.setProprietarios(pro);
        this.setVeiculosTodos(vei);
    }
    
    /**
     * Construtor para objetos da classe Dados (de copia)
     * 
     * @param  d   os dados
     */
    public Dados(Dados d){
        this.clientes = d.getClientes();
        this.proprietarios = d.getProprietarios();
        this.veiculos = d.getVeiculosTodos();
    }

    /**
     * Metodo que devolve os clientes de um conjunto de dados
     * 
     * @return     os clientes dos dados
     */
    public Map<String, Cliente> getClientes(){
        Map<String, Cliente> aux = new TreeMap<>();
        for(String em : this.clientes.keySet()){
            Cliente c = this.clientes.get(em);
            aux.put(em, c.clone());
        }
        return aux;
    }
    
    /**
     * Metodo que altera os clientes de um conjunto de cados
     * 
     * @param  cli   os novos clientes
     */
    public void setClientes(Map<String, Cliente> cli){
        this.clientes = cli.values().stream()
                           .collect(Collectors
                           .toMap((cliente -> cliente.getEmail()), (cliente) -> cliente.clone()));
    }
    
    /**
     * Metodo que devolve os proprietarios de um conjunto de dados
     * 
     * @return     os proprietarios dos dados
     */
    public Map<String, Proprietario> getProprietarios(){
        Map<String, Proprietario> aux = new TreeMap<>();
        for(String em : this.proprietarios.keySet()){
            Proprietario p = this.proprietarios.get(em);
            aux.put(em, p.clone());
        }
        return aux;
    }
    
    /**
     * Metodo que altera os proprietarios de um conjunto de cados
     * 
     * @param  pro   os novos proprietarios
     */
    public void setProprietarios(Map<String, Proprietario> pro){
        this.proprietarios = pro.values().stream()
                                .collect(Collectors
                                .toMap((prop -> prop.getEmail()), (prop) -> prop.clone()));
    }
    
    /**
     * Metodo que devolve os veiculos de um conjunto de dados
     * 
     * @return     os veiculos dos dados
     */
    public Map<String, Veiculo> getVeiculosTodos(){
        Map<String, Veiculo> aux = new TreeMap<>();
        for(String m : this.veiculos.keySet()){
            Veiculo v = this.veiculos.get(m);
            aux.put(m, v.clone());
        }
        return aux;
    }
    
    /**
     * Metodo que altera os veiculos de um conjunto de cados
     * 
     * @param  vei   os novos veiculos
     */
    public void setVeiculosTodos(Map<String, Veiculo> vei){
        this.veiculos = vei.values().stream()
                           .collect(Collectors
                           .toMap((veiculo -> veiculo.getMatricula()), (veiculo) -> veiculo.clone()));
    }
    
    /**
     * Metodo que duplica os dados
     * 
     * @return   o clone dos dados
     */
    public Dados clone(){
        return new Dados(this);
    }
    
    /**
     * Metodo que verifica se dois conjuntos de dados sao iguais
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
            Dados d = (Dados) o;
            return(this.clientes.equals(d.getClientes())
                   && this.proprietarios.equals(d.getProprietarios())
                   && this.veiculos.equals(d.getVeiculosTodos()));
        }
    }
    
    /**
     * Metodo que converte um conjunto de dados para uma string
     * 
     * @return     o conjunto de dados em string
     */
    public String toString(){
        String aux = new String();
        for(Cliente c : this.clientes.values())
            aux += c.toString();
        for(Proprietario p : this.proprietarios.values())
            aux += p.toString();
        for(Veiculo v : this.veiculos.values())
            aux += v.toString();
        return aux;
    }
    
    /**
     * Metodo que devolve o codigo de hash para um proprietario
     * 
     * @return     o hashcode
     */
    public int hashCode(){
        int hash = 7; 
        for(Cliente c : this.clientes.values())
            hash = 31 * hash + c.hashCode();
        for(Proprietario p : this.proprietarios.values())
            hash = 31 * hash + p.hashCode();
        for(Veiculo v : this.veiculos.values())
            hash = 31 * hash + v.hashCode();
        return hash;
    }
    
     /*
                *******METODOS DE REGISTO*******
     */   
    
    /**
     * Metodo que insere um Cliente no conjunto de dados
     * 
     * @param  cli   o novo cliente
     */
    public void registarCliente (Cliente cli){
        this.clientes.put(cli.getEmail(), cli.clone());
    }
    
    /**
     * Metodo que insere um Proprietario no conjunto de dados
     * 
     * @param  pro   o novo proprietario
     */
    public void registarProprietario (Proprietario pro){
        this.proprietarios.put(pro.getEmail(), pro.clone());
    }
    
    /**
     * Metodo que insere um veiculo num conjunto de dados
     * 
     * @param  vei   o veiculo a ser inserido
     * @param  pro   o proprietario do veiculo
     */
    public void insereVeiculo(Veiculo vei, Proprietario pro){
        Proprietario p = this.proprietarios.get(pro.getEmail());
        p.adicionaVeiculo(vei.clone());
        this.proprietarios.put(p.getEmail(), p.clone());
        this.veiculos.put(vei.getMatricula(), vei.clone());
    }
    
    public void insereAluguerPro(String email, String matricula, Aluguer a){
        Proprietario p = this.proprietarios.get(email);
        
        if(p != null){
            p.insereAluguer(a.clone());
            for(Veiculo v : p.getVeiculos())
                if(v.getMatricula().equals(matricula)){
                    v.insereAluguer(a.clone());
                    this.registarProprietario(p.clone());
                    this.insereVeiculo(v.clone(), p.clone());
                    break;
                }
        }  
    }
    
    public void insereAluguerCliente(String email, Aluguer a){
        Cliente c = this.clientes.get(email);
        
        if(c != null){
                c.insereAluguer(a.clone());
                this.registarCliente(c.clone());
        }
    }
    
    
     /*
                *******METODOS DE VALIDACAO DE LOGIN*******
     */   
    
    /**
     * Metodo que valida o login de um cliente
     * 
     * @param  email   o email do cliente
     * @param  pass   a password do cliente
     * 
     * @return  true se os dados forem validos, false em caso contrario
     */
    public boolean loginCliente(String email, String pass){
        if(this.clientes.get(email).dadosValidosCliente(email, pass) == true)
            return true;
        return false;
    }
    
    /**
     * Metodo que valida o email
     * 
     * @param  email   o email
     * 
     * @return  true se o email for valido, false em caso contrario
     */
    public boolean emailValido (String email) throws EmailInvalidoException {
        try {
            existeEmail(email);
        }
        catch (EmailInexistenteException e) {
            if ((email.contains(".com") || email.contains(".pt")) && email.contains("@") )
                return true;
            else 
                throw new EmailInvalidoException(email);
        }
            
        return false;
    }
    
    /**
     * Metodo que verifica a existencia do email 
     * 
     * @param  email   o email 
     * 
     * @return  true se o email existir, false em caso contrario
     */
    public boolean existeEmail (String email) throws EmailInexistenteException {
        Cliente c = this.clientes.get(email);
        
        if (c == null) {
            Proprietario m = this.proprietarios.get(email);
                if(m == null)
                    throw new EmailInexistenteException(email);
                else
                    return true;
        }
            
        return false;
    }
    
    /**
     * Metodo que verifica a existencia da matricula
     * 
     * @param  matr   a matricula
     * 
     * @return  true se a matricula existir, false em caso contrario
     */
    public boolean existeMatricula (String matr) throws MatriculaInexistenteException {
        Veiculo v = this.veiculos.get(matr);
        
        if (v == null){ 
            throw new MatriculaInexistenteException(matr);
        }
        else
            return true;
    }
    
    /**
     * Metodo que valida o login de um proprietario
     * 
     * @param  email   o email do proprietario
     * @param  pass   a password do proprietario
     * 
     * @return  true se os dados forem validos, false em caso contrario
     */
    public boolean loginProprietario(String email, String pass){
        if(this.proprietarios.get(email).dadosValidosProprietario(email, pass) == true)
            return true;
        return false;
    }
    
    
    /*
                *******METODOS AUXILIARES*******
     */   
    
    public List<Cliente> top10_Utilizacao() {
        List<Cliente> top10 = new ArrayList<Cliente>();
        top10 = this.getClientes().values().stream().collect(Collectors.toList());
        Collections.sort(top10, new ComparadorUtilizacao());
        Collections.reverse(top10);
        top10 = top10.stream().limit(10).collect(Collectors.toList());
        return top10;
    }
    
    public List<Cliente> top10_Distancia() {
        List<Cliente> top10 = new ArrayList<Cliente>();
        top10 = this.getClientes().values().stream().collect(Collectors.toList());
        Collections.sort(top10, new ComparadorDistancia());
        Collections.reverse(top10);
        top10 = top10.stream().limit(10).collect(Collectors.toList());
        return top10;
    }
    
    /**
     * Metodo que dado o email, diz-nos a que Cliente corresponde o email, caso corresponda
     * 
     * @param  email o email a testar
     * 
     * @return Cliente caso corresponda o email, null caso contrario
     */
    public Cliente mailToCliente(String email){
        Map<String, Cliente> aux = this.getClientes();
        Cliente n = aux.get(email);
        return n;    
    }
    
    /**
     * Metodo que dado o veiculo, diz-nos a que Proprietario corresponde o veiculo, caso corresponda
     * 
     * @param  v o veiculo a testar
     * 
     * @return Proprietario caso corresponda o veiculo, null caso contrario
     */
    public Proprietario veiculoToProprietario(String matricula){
        List<Veiculo> vs = new ArrayList<Veiculo>();
        Proprietario n = null;
        for(Proprietario p : this.proprietarios.values()){
            for(Veiculo vei : p.getVeiculos()){
                if(matricula.equals(vei.getMatricula())){
                    n = p.clone();
                    break;
                }
            }
            if(n != null)
                break;
        }
        return n;    
    }
    
    /**
     * Metodo que dado o email, diz-nos a que Proprietario corresponde o email, caso corresponda
     * 
     * @param  email o email a testar
     * 
     * @return Proprietario caso corresponda o email, null caso contrario
     */
    public Proprietario mailToProprietario(String email){
        Map<String, Proprietario> aux = this.getProprietarios();
        Proprietario n = aux.get(email);
        return n;    
    }
    
    /**
     * Metodo que dado o email, diz-nos a que Proprietario corresponde o email, caso corresponda
     * 
     * @param  email o email a testar
     * 
     * @return Proprietario caso corresponda o email, null caso contrario
     */
    public Veiculo matriculaToVeiculo(String m){
        Map<String, Veiculo> aux = this.getVeiculosTodos();
        Veiculo n = aux.get(m);
        return n;    
    }
   
    /**
     * Metodo que devolve a lista de veiculos do tipo CarroGasolina
     * 
     * @return lista de veiculos do tipo CarroGasolina, null caso contrario
     */
    public List<Veiculo> tipoCarroGasolina() throws VeiculoInexistenteException{
         List<Veiculo> lista =  new ArrayList<Veiculo>();
        try{
            for(Veiculo v : veiculosDisponiveis()){
                if(v instanceof CarroGasolina)
                    lista.add(v);
            }
        }
        catch(VeiculoInexistenteException e){
            System.out.println("Nao se encontra nenhum veiculo disponivel.\nTente mais tarde.");
        }
        
        if(lista.isEmpty())
            throw new VeiculoInexistenteException();
            
        return lista;    
    }
    
    /**
     * Metodo que devolve a lista de veiculos do tipo CarroGasoleo
     * 
     * @return lista de veiculos do tipo CarroGasoleo, null caso contrario
     */
    public List<Veiculo> tipoCarroGasoleo() throws VeiculoInexistenteException{
         List<Veiculo> lista =  new ArrayList<Veiculo>();
        try{
            for(Veiculo v : veiculosDisponiveis()){
                if(v instanceof CarroGasoleo)
                    lista.add(v);
            }
        }
        catch(VeiculoInexistenteException e){
            System.out.println("Nao se encontra nenhum veiculo disponivel.\nTente mais tarde.");
        }
        
        if(lista.isEmpty())
            throw new VeiculoInexistenteException();
            
        return lista;    
    }
    
    /**
     * Metodo que devolve a lista de veiculos do tipo CarroHibrido
     * 
     * @return lista de veiculos do tipo CarroHibrido, null caso contrario
     */
    public List<Veiculo> tipoCarroHibrido() throws VeiculoInexistenteException{
         List<Veiculo> lista =  new ArrayList<Veiculo>();;
        try{
            for(Veiculo v : veiculosDisponiveis()){
                if(v instanceof CarroHibrido)
                    lista.add(v);
            }
        }
        catch(VeiculoInexistenteException e){
            System.out.println("Nao se encontra nenhum veiculo disponivel.\nTente mais tarde.");
        }
        
        if(lista.isEmpty())
            throw new VeiculoInexistenteException();
            
        return lista;    
    }
    
    /**
     * Metodo que devolve a lista de veiculos do tipo CarroEletrico
     * 
     * @return lista de veiculos do tipo CarroEletrico, null caso contrario
     */
    public List<Veiculo> tipoCarroEletrico() throws VeiculoInexistenteException{
        List<Veiculo> lista =  new ArrayList<Veiculo>();
        try{
            for(Veiculo v : veiculosDisponiveis()){
                if(v instanceof CarroEletrico)
                    lista.add(v);
            }
        }
        catch(VeiculoInexistenteException e){
            System.out.println("Nao se encontra nenhum veiculo disponivel.\nTente mais tarde.");
        }
        
        if(lista.isEmpty())
            throw new VeiculoInexistenteException();
            
        return lista;    
    }
    
    /**
     * Metodo que devolve a distancia entre dois pontos 
     * 
     * @param  x1 a coordenada x atual
     * @param  y1 a coordenada y atual
     * @param  x2 a coordenada x destino
     * @param  y2 a coordenada y destino
     * 
     * @return dist percorrida de um ponto ao outro
     */
    public double dist(double x1, double y1, double x2, double y2) {       
        return Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
    }   
    
    public List<Veiculo> veiculosDisponiveis() throws VeiculoInexistenteException{
        List<Veiculo> disponiveis = new ArrayList<Veiculo>();
        Map<String, Veiculo> vei = this.getVeiculosTodos();
        disponiveis = vei.values().stream().filter(l -> l.getDisponivel()).collect(Collectors.toList());
                                    
        if (disponiveis.isEmpty())
            throw new VeiculoInexistenteException();
        
        return disponiveis;    
    }
    
    /**
     * Metodo que devolve o veiculo disponivel mais proximo das coordenadas x e y
     * 
     * @param  veiculos lista de veiculos da aplicacao
     * @param  c Cliente que procura o carro mais proximo
     * @param  x1 a coordenada x atual
     * @param  y1 a coordenada y atual
     * 
     * @return proximo veiculo mais proximo
     */
    public Veiculo proximo (List<Veiculo> disponiveis, Cliente c, double x, double y) {
        double minimo = Double.MAX_VALUE, d;
        Veiculo vei = null;
                                      
        for (Veiculo v : disponiveis) {
            d = dist(x, y, v.getLocalX(), v.getLocalY());
            if (d < minimo){
                minimo = d;
                vei = v.clone();
            }
        }

        return vei;
    }
    
    /**
     * Metodo que devolve o veiculo disponivel mais barato
     * 
     * @param  veiculos lista de veiculos da aplicacao
     * @param  c Cliente que procura o carro mais barato
     * @param  x1 a coordenada x atual
     * @param  y1 a coordenada y atual
     * 
     * @return barato veiculo mais barato
     */
    public Veiculo barato (List<Veiculo> disponiveis, Cliente c, double x, double y){
        double minimo = Double.MAX_VALUE, d;
        Veiculo vei = null;
        
        for (Veiculo v : disponiveis) {
            d = v.getPreco();
            if (d < minimo){
                minimo = d;
                vei = v.clone();
            }
        }

        return vei;
    }
    
    /**
     * Metodo que devolve o veiculo disponivel mais barato numa condicionante de distancia
     * 
     * @param  veiculos lista de veiculos da aplicacao
     * @param  c Cliente que procura o carro mais barato tendo em conta a distancia que pretende fazer a pe no maximo
     * @param  x1 a coordenada x atual
     * @param  y1 a coordenada y atual
     * @param dist a distancia que o cliente pretende fazer no maximo ate ao carro
     * 
     * @return baratoProximo veiculo mais barato com a condicionante de distancia
     */
    public Veiculo baratoProximo (List<Veiculo> disponiveis, Cliente c, double x, double y, double dist){
        double minimo = Double.MAX_VALUE, dpreco, d;
        Veiculo vei = null;
                         
        for (Veiculo v : disponiveis) {
            d = dist(x, y, v.getLocalX(), v.getLocalY()); 
            dpreco = v.getPreco();
            if (dpreco < minimo && dist >= d){
                minimo = dpreco;
                vei = v.clone();
            }
        }

        return vei;
    }
    
    /**
     * Metodo que devolve o veiculo com a autonomia desejada
     * 
     * @param  veiculos lista de veiculos da aplicacao
     * @param  c Cliente que procura o carro com determinada autonomia
     * @param  x1 a coordenada x atual
     * @param  y1 a coordenada y atual
     * @param autonomia desejada pelo Cliente
     * 
     * @return autonomia veiculo com determinada autonomia
     */
    public Veiculo autonomia(List<Veiculo> disponiveis, Cliente c, double x, double y, double autonomia){
        double d;
        Veiculo vei = null;
        
        for (Veiculo v : disponiveis) {
            d = v.determinaAutonomia();
            if (autonomia == d){
                vei = v.clone();
                break;
            }
        }
        return vei;
    }   
    
    /*
                *******METODOS DE VALIDACAO*******
     */   
    
    /**
     * Metodo que verifica se um nome e valido
     * 
     * @param  nome o nome a testar
     * 
     * @return true caso corresponda a um nome verdadeiro, false caso contrario
     */
    public boolean nomeValido (String nome) throws NomeInvalidoException {
        int i;
        char c;
        
        for (i = 0; i < nome.length(); i++) {
            c = nome.charAt(i);
            if (!Character.isLetter(c) && c != ' ')
                throw new NomeInvalidoException(nome);
        }

        return true;
    }
    
    /**
     * Metodo que verifica se a password de confirmaçao coincide com a inserida 
     * 
     * @param  password a password inserida
     * @param  pass a password a confirmar se coincide
     * 
     * @return true caso correspondam, false caso contrario
     */
    public boolean passwordValida (String password, String pass) throws PasswordInvalidaException {
        if (password.equals(pass))
            return true;
        else
            throw new PasswordInvalidaException(pass);
    }
    
    /*
                *******METODOS QUE ENVOLVEM FICHEIROS*******
     */   
    
    /**
     * Metodo que abre um ficheiro
     * 
     * @return Dados do ficheiro lido
     */
     public static Dados abrirFicheiro(String nomeFicheiro) throws FileNotFoundException, IOException, ClassNotFoundException, ClassCastException{
        FileInputStream in = new FileInputStream(nomeFicheiro);
        ObjectInputStream o = new ObjectInputStream(in);
        Dados d = (Dados) o.readObject();
        o.close();
        return d;
    }
    
    /**
     * Metodo que guarda os dados num ficheiro
     * 
     */
    public void guardaFicheiro(String nomeFicheiro) throws FileNotFoundException, IOException{
        FileOutputStream out = new FileOutputStream(nomeFicheiro);
        ObjectOutputStream o = new ObjectOutputStream(out);
        o.writeObject(this);
        o.flush();
        o.close();
    }
    
    public void carregaLogs(String fich) throws FileNotFoundException, IOException{
        List<String> dadosS = Dados.lerLogs(fich);
        dadosS.forEach(s -> this.adicionaLinha(s));
    }
    
    private static List<String> lerLogs(String fich) throws FileNotFoundException, IOException{
        List<String> linhas = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(fich));
        String linha;
        
        while((linha = br.readLine()) != null && (linha.equals("Logs")) == false);
        while((linha = br.readLine()) != null && (linha.equals("\n")) == false){
            linhas.add(linha);
        }
        br.close();
        return linhas;
    }   
    
    private void adicionaLinha(String csv){
        String[] divisor;
        String tipo, aux, nome, email, morada, matricula, carro;
        int nif;
        double cx, cy, velocidade, preco, consumo, autonomia;
        
        divisor = csv.split(":");
        tipo = divisor[0];
        aux = divisor[1];
        
        switch(tipo){
            case "NovoProp": divisor = aux.split(",");
                             nome = divisor[0];
                             nif = Integer.parseInt(divisor[1]);
                             email = divisor[2];
                             morada = divisor[3];
                             Proprietario p = new Proprietario(email, nome, "na", morada, LocalDate.now(), nif, 0, new ArrayList<>(), new ArrayList<>());
                             this.registarProprietario(p);
                             break;
                             
            case "NovoCliente": divisor = aux.split(",");
                                nome = divisor[0];
                                nif = Integer.parseInt(divisor[1]);
                                email = divisor[2];
                                morada = divisor[3];
                                cx = Double.parseDouble(divisor[4]);
                                cy = Double.parseDouble(divisor[5]);
                                Cliente c = new Cliente(email, nome, "na", morada, LocalDate.now(), nif, cx, cy, new ArrayList<>());
                                this.registarCliente(c);
                                break;
                                
            case "NovoCarro" : divisor = aux.split(",");
                               carro = divisor[0];
                               matricula = divisor[2];
                               nif = Integer.parseInt(divisor[3]);
                               velocidade = Double.parseDouble(divisor[4]);
                               preco = Double.parseDouble(divisor[5]);
                               consumo = Double.parseDouble(divisor[6]);
                               autonomia = Double.parseDouble(divisor[7]);
                               autonomia = autonomia*consumo;
                               cx = Double.parseDouble(divisor[8]);
                               cy = Double.parseDouble(divisor[9]);
                               switch(carro){
                                   case "Electrico" : CarroEletrico ce = new CarroEletrico(matricula, velocidade, preco, new ArrayList<>(), 0, 0, cx, cy, true, consumo, autonomia, autonomia);
                                                      Proprietario p1 = this.nifToProprietario(nif);
                                                      this.insereVeiculo(ce, p1);
                                                      break;
                                   case "Hibrido" : consumo = consumo/2;
                                                    autonomia = autonomia/2;
                                                    CarroHibrido ch = new CarroHibrido(matricula, velocidade, preco, new ArrayList<>(), 0, 0, cx, cy, true, consumo, autonomia, autonomia, consumo, autonomia, autonomia);
                                                    Proprietario p2 = this.nifToProprietario(nif);
                                                    this.insereVeiculo(ch, p2);
                                                    break;
                                   case "Gasolina" : CarroGasolina cga = new CarroGasolina(matricula, velocidade, preco, new ArrayList<>(), 0, 0, cx, cy, true, consumo, autonomia, autonomia);
                                                     Proprietario p3 = this.nifToProprietario(nif);
                                                     this.insereVeiculo(cga, p3);
                                                     break;
                                   case "Gasoleo" : CarroGasoleo cgo = new CarroGasoleo(matricula, velocidade, preco, new ArrayList<>(), 0, 0, cx, cy, true, consumo, autonomia, autonomia);
                                                    Proprietario p4 = this.nifToProprietario(nif);
                                                    this.insereVeiculo(cgo, p4);
                                                    break;
                               }
                               break;
                               
            case "Aluguer" : divisor = aux.split(",");
                             nif = Integer.parseInt(divisor[0]);
                             cx = Double.parseDouble(divisor[1]);
                             cy = Double.parseDouble(divisor[2]);
                             carro = divisor[3];
                             String preferencia = divisor[4];
                             List<Veiculo> disp = new ArrayList<>();
                             
                             try{
                                 switch(carro){
                                     case "Gasolina": disp = this.tipoCarroGasolina();
                                             break;
                                     case "Electrico": disp = this.tipoCarroHibrido();
                                             break;
                                     case "Hibrido": disp = this.tipoCarroEletrico();
                                             break;
                                 }
                             }
                             catch(VeiculoInexistenteException e){
                                 System.out.println("Nao se encontra nenhum veiculo desse tipo disponivel.\nTente mais tarde.");
                             }
                             Veiculo veiAux = null;
                             Cliente cAux = this.nifToCliente(nif);
                                 
                             if(!(disp.isEmpty())){
                                 if(cAux.getNif() == nif){
                                     switch(preferencia){
                                         case "MaisPerto" : veiAux = this.proximo(disp, cAux, cx, cy);
                                         case "MaisBarato" : veiAux = this.barato(disp, cAux, cx, cy);
                                     }
                                 }
                             }
                             if(veiAux != null){
                                 this.efetuarAluguer(cAux, veiAux, cx, cy);
                             }
                             
                             break;
                             
            case "Classificar" : divisor = aux.split(",");
                                 double classificacao = Double.parseDouble(divisor[1]);
                                 if(divisor[0].length() == 8){
                                     matricula = divisor[0];
                                     Veiculo vei = this.veiculos.get(matricula);
                                     if(vei != null){
                                         vei.alteraClassificacao(classificacao);
                                         Proprietario paux = this.veiculoToProprietario(vei.getMatricula());
                                         this.insereVeiculo(vei, paux);
                                     }
                                 }
                                 else if(divisor[0].length() == 9){
                                     nif = Integer.parseInt(divisor[0]);
                                     Cliente c2 = this.nifToCliente(nif);
                                     Proprietario paux = this.nifToProprietario(nif);
                                     if(c2.getNif() == nif){
                                         //c.alteraClassificacao(classificacao);
                                         //this.clientes.put(c.getEmail(), c);
                                     }
                                     else if(paux.getNif() == nif){
                                         paux.alteraClassificacao(classificacao);
                                         this.proprietarios.put(paux.getEmail(), paux);
                                     }
                                 }
                                 break;
        }
    }
    
    private void efetuarAluguer(Cliente c, Veiculo v, double xDestino, double yDestino){
        
        double distanciaCliente_Carro = this.dist(c.getLocalX(), c.getLocalY(), v.getLocalX(), v.getLocalY());
        double distanciaViagem = this.dist(v.getLocalX(), v.getLocalY(), xDestino, yDestino);
        
        if(validaAutonomia(v, distanciaViagem)){
            double tempoCliente_Carro = tempo(c.getLocalX(), c.getLocalY(), v.getLocalX(), v.getLocalY(), 4);
            double tempoViagem = tempo(v.getLocalX(), v.getLocalY(), xDestino, yDestino, v.getVelocidade());
            double transito = Math.random() * (1.5 - 1.0) + 1.0; 
            double tempoRealViagem = tempoViagem * transito;
        
            double custo = v.getPreco() * distanciaViagem;
        
            double totalFaturado = v.getTotalFaturado() + custo;
    
            Proprietario pro = this.veiculoToProprietario(v.getMatricula());
            
            String emailCli = c.getEmail();
            String emailPro = pro.getEmail();
            String matricula = v.getMatricula();
            AluguerCarro a = new AluguerCarro(emailCli, emailPro, matricula, distanciaViagem, custo, 0, xDestino, yDestino);
            
            this.alteraAposViagem2(emailCli, emailPro, matricula, totalFaturado, xDestino, yDestino, distanciaViagem, a);
        }
    }
    
    /**
     * Metodo que valida a autonomia apos um deslocamento
     * 
     * @param  v veiculo que se vai deslocar
     * @param  distanciaViagem distancia da viagem
     * 
     * @return true caso a autonomia apos o deslocamento seja superior 0, false caso contrario
     */
    public boolean validaAutonomia(Veiculo v, double distanciaViagem){
        double autonomia = v.autonomiaAposDeslocamento(distanciaViagem);
        if(autonomia < 0)
           return false;
        return true;
    }
    
    public Proprietario nifToProprietario(int nif){
        Proprietario ret = new Proprietario();
        for(Proprietario p : this.proprietarios.values()){
            if(p.getNif() == nif){
                ret = p.clone();
                break;
            }
        }
        return ret;
    }
    
    public Cliente nifToCliente(int nif){
        Cliente ret = new Cliente();
        for(Cliente c : this.clientes.values()){
            if(c.getNif() == nif){
                ret = c.clone();
                break;
            }
        }
        return ret;
    }
    
     
    /**
     * Metodo que devolve o tempo resultante do deslocamento entre dois pontos
     * 
     * @param  x a coordenada x atual
     * @param  y a coordenada x atual
     * @param  xDestino a coordenada x do destino
     * @param  yDestino a coordenada y do destino
     * @param  velocidade do veiculo
     * 
     * @return tempo do deslocamento entre dois pontos
     */
    public double tempo (double x, double y, double xDestino, double yDestino, double velocidade){
        double distancia = this.dist(x, y, xDestino, yDestino);
        return distancia/(velocidade/3600);
    }
    
    public void removeAluguer(String emailCli, String emailPro, String matricula, Aluguer a){
        Veiculo v = this.veiculos.get(matricula);
        Proprietario pro = this.proprietarios.get(emailPro);
        Cliente c = this.clientes.get(emailCli);
        
        List<Aluguer> lista = v.getAlugueresCarro();
        lista.remove(a);
        v.setAlugueresCarro(lista);
        
        List<Aluguer> list = pro.getAlugueres();
        list.remove(a);
        pro.setAlugueres(list);
        
        List<Aluguer> lis = c.getAlugueres();
        lis.remove(a);
        c.setAlugueres(lis);
    }
    
    public void alteraAntesViagem(String emailCli, String emailPro, String matricula, Aluguer a){
        Veiculo v = this.veiculos.get(matricula);
        Proprietario pro = this.proprietarios.get(emailPro);
        
        this.registarProprietario(pro);
        this.insereVeiculo(v, pro);
        
        this.insereAluguerCliente(emailCli,a.clone());
            
        this.insereAluguerPro(emailPro, matricula, a.clone());
    }
    
    public void alteraAposViagem(String emailCli, String emailPro, String matricula, double classificacao, double totalFaturado, double xDestino, double yDestino, double distanciaViagem, Aluguer a){
        Veiculo v = this.veiculos.get(matricula);
        Proprietario pro = this.proprietarios.get(emailPro);
        
        pro.alteraClassificacao(classificacao);
        
        v.alteraClassificacao(classificacao);    
        v.setTotalFaturado(totalFaturado);//faturado na viagem
        v.setLocalX(xDestino);//nova coordenada x
        v.setLocalY(yDestino);//nova coordenada y
        v.combAposDeslocamento(distanciaViagem); //novo combustivel
        v.verificaAutonomia();

        this.registarProprietario(pro);
        this.insereVeiculo(v, pro);
        
        this.insereAluguerCliente(emailCli,a.clone());
            
        this.insereAluguerPro(emailPro, matricula, a.clone());
    }
    
    public void alteraAposViagem2(String emailCli, String emailPro, String matricula, double totalFaturado, double xDestino, double yDestino, double distanciaViagem, Aluguer a){
        Veiculo v = this.veiculos.get(matricula);
        Proprietario pro = this.proprietarios.get(emailPro);
        
        v.setTotalFaturado(totalFaturado);//faturado na viagem
        v.setLocalX(xDestino);//nova coordenada x
        v.setLocalY(yDestino);//nova coordenada y
        v.combAposDeslocamento(distanciaViagem); //novo combustivel
        v.verificaAutonomia();

        this.registarProprietario(pro);
        this.insereVeiculo(v, pro);
        
        this.insereAluguerCliente(emailCli,a.clone());
            
        this.insereAluguerPro(emailPro, matricula, a.clone());
    }
}

