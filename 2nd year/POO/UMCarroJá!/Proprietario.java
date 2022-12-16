import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;
/**
 * Proprietario - dono de um ou mais carros, que e prestador do serviço.
 * 
 * @author (Ana Rita Rosendo, Gonçalo Esteves, Rui Oliveira) 
 * @version 29 de abril de 2019
 */
public class Proprietario extends Dados implements Serializable{
    private String email;
    private String nome;
    private String password;
    private String morada;
    private LocalDate ddn;
    private int nif;
    private double classificacao;
    private List<Aluguer> alugueres;
    private List<Veiculo> veiculos;

    /**
     * Construtor para objetos da classe Proprietario (por omissao)
     */
    public Proprietario(){
        this.email = "n/a";
        this.nome = "n/a";
        this.password = "n/a";
        this.morada = "n/a";
        this.ddn = LocalDate.now();
        this.nif = 0;
        this.classificacao = 0;
        this.alugueres = new ArrayList<>();
        this.veiculos = new ArrayList<>();
    }
    
    /**
     * Construtor para objetos da classe Proprietario (parametrizado).
     * 
     * @param  em   o email
     * @param  nm   o nome
     * @param  pw   a password
     * @param  mr   a morada
     * @param  data   a data de nascimento
     * @param  n   o nif do proprietario
     * @param  cla   a classificacao do proprietario
     * @param  al   os alugueres
     * @param  v   os veiculos
     */
    public Proprietario(String em, String nm, String pw, String mr, LocalDate data, int n, double cla, List<Aluguer> al, List<Veiculo> v){
        this.setEmail(em);
        this.setNome(nm);
        this.setPassword(pw);
        this.setMorada(mr);
        this.setDDN(data);
        this.setNif(n);
        this.setClassificacao(cla);
        this.setAlugueres(al);
        this.setVeiculos(v);
    }
    
    /**
     * Construtor para objetos da classe Proprietario (de copia)
     * 
     * @param  p   o proprietario
     */
    public Proprietario(Proprietario c){
        this.email = c.getEmail();
        this.nome = c.getNome();
        this.password = c.getPassword();
        this.morada = c.getMorada();
        this.ddn = c.getDDN();
        this.nif = c.getNif();
        this.classificacao = c.getClassificacao();
        this.alugueres = c.getAlugueres();
        this.veiculos = c.getVeiculos();
    }
    
    /**
     * Metodo que devolve o email de um dado proprietario
     * 
     * @return     o email do proprietario
     */
    public String getEmail(){
        return this.email;
    }
    
    /**
     * Metodo que altera o email de um dado proprietario
     * 
     * @param  em   o novo email 
     */
    public void setEmail(String em){
        this.email = em;
    }
    
    /**
     * Metodo que devolve o nome de um dado proprietario
     * 
     * @return     o nome do proprietario
     */
    public String getNome(){
        return this.nome;
    }
    
    /**
     * Metodo que altera o nome de um dado proprietario
     * 
     * @param  nm   o novo nome 
     */
    public void setNome(String nm){
        this.nome = nm;
    }
    
    /**
     * Metodo que devolve a password de um dado proprietario
     * 
     * @return     a password do proprietario
     */
    public String getPassword(){
        return this.password;
    }
    
    /**
     * Metodo que altera a password de um dado proprietario
     * 
     * @param  pd   a nova password 
     */
    public void setPassword(String pd){
        this.password = pd;
    }
    
    /**
     * Metodo que devolve a morada de um dado proprietario
     * 
     * @return     a morada do proprietario
     */
    public String getMorada(){
        return this.morada;
    }
    
    /**
     * Metodo que altera a morada de um dado proprietario
     * 
     * @param  mr   a nova morada
     */
    public void setMorada(String mr){
        this.morada = mr;
    }
    
    /**
     * Metodo que devolve a data de nascimento de um dado proprietario
     * 
     * @return     a data de nascimento do proprietario
     */
    public LocalDate getDDN(){
        return this.ddn;
    }
    
    /**
     * Metodo que altera a data de nascimento de um dado proprietario
     * 
     * @param  data   a nova data de nascimento 
     */
    public void setDDN(LocalDate data){
        this.ddn = data;
    }
    
    /**
     * Metodo que devolve o nif de um dado proprietario
     * 
     * @return     o nif do proprietario 
     */
    public int getNif(){
        return this.nif;
    }
    
    /**
     * Metodo que altera o nif de um dado proprietario
     * 
     * @param  data   o novo nif 
     */
    public void setNif(int n){
        this.nif = n;
    }
    
    /**
     * Metodo que devolve a classificacao de um dado proprietario
     * 
     * @return     a classificacao do proprietario
     */
    public double getClassificacao(){
        return this.classificacao;
    }
    
    /**
     * Metodo que altera a classificacao de um dado proprietario
     * 
     * @param  cla   a nova classificacao 
     */
    public void setClassificacao(double cla){
        this.classificacao = cla;
    }
    
    /**
     * Metodo que devolve os alugueres de um proprietario
     * 
     * @return     os alugueres do proprietario
     */
    public List<Aluguer> getAlugueres(){
        List<Aluguer> aux = new ArrayList<>();
        for(Aluguer a : this.alugueres)
            aux.add(a.clone());
        return aux;
    }
    
    /**
     * Metodo que altera os alugueres de um proprietario
     * 
     * @param  al   os novos alugueres 
     */
    public void setAlugueres(List<Aluguer> al){
        this.alugueres = new ArrayList<>();
        for(Aluguer a : al)
            this.alugueres.add(a.clone());
    }
    
    /**
     * Metodo que devolve os veiculos de um proprietario
     * 
     * @return     os veiculos do proprietario
     */
    public List<Veiculo> getVeiculos(){
        List<Veiculo> aux = new ArrayList<>();
        for(Veiculo v : this.veiculos)
            aux.add(v.clone());
        return aux;
    }
    
    /**
     * Metodo que altera os alugueres de um proprietario
     * 
     * @param  al   os novos alugueres 
     */
    public void setVeiculos(List<Veiculo> vs){
        this.veiculos = new ArrayList<>();
        for(Veiculo v : vs)
            this.veiculos.add(v.clone());
    }
    
    /**
     * Metodo que duplica o proprietario
     * 
     * @return     o clone do proprietario
     */
    public Proprietario clone(){
        return new Proprietario(this);
    }
    
    /**
     * Metodo que verifica se dois proprietarios sao iguais
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
            Proprietario p = (Proprietario) o;
            return(p.getEmail().equals(this.getEmail())
                   && p.getNome().equals(this.getNome())
                   && p.getPassword().equals(this.getPassword())
                   && p.getMorada().equals(this.getMorada())
                   && p.getDDN().equals(this.getDDN())
                   && p.getNif() == this.getNif()
                   && p.getClassificacao() == this.getClassificacao()
                   && p.getAlugueres().equals(this.alugueres)
                   && p.getVeiculos().equals(this.veiculos));
        }
    }
    
    /**
     * Metodo que converte um proprietario para uma string
     * 
     * @return     o proprietario em string
     */
    public String toString(){
        String aux = "Email: " + this.email + ";\n" 
                     + "Nome: " + this.nome + ";\n"
                     + "Password: " + this.password + ";\n"
                     + "Morada: " + this.morada + ";\n"
                     + "Data de Nascimento: " + this.ddn.toString() + ";\n"
                     + "Nif: " + this.nif + ";\n"
                     + "Classificacao: " + this.classificacao + ";\n"
                     + "Alugueres: \n";
        for(Aluguer a : this.alugueres)
            aux += a.toString() + "\n";
        aux += "Veiculos: \n";
        for(Veiculo v : this.veiculos)
            aux += v.toString() + "\n";
        return aux;
    }
    
    /**
     * Metodo que devolve o codigo de hash para um proprietario
     * 
     * @return     o hashcode
     */
    public int hashCode(){
        int hash = 7; 
        hash = 31 * hash + email.hashCode();
        hash = 31 * hash + nome.hashCode();
        hash = 31 * hash + password.hashCode();
        hash = 31 * hash + morada.hashCode();
        hash = 31 * hash + ddn.hashCode();
        hash = 31 * hash + nif;
        long aux = Double.doubleToLongBits(classificacao);
        hash = 31 * hash + (int)(aux ^ (aux >>> 32));
        for(Aluguer a : this.alugueres)
            hash = 31 * hash + a.hashCode();
        for(Veiculo v : this.veiculos)
            hash = 31 * hash + v.hashCode();
        return hash;       
    }
    
    /**
     * Metodo que implementa a ordem natural de comparacao de instancias de Proprietario
     */
    public int compareTo(Proprietario p){
        return p.getEmail().compareTo(this.email);
    }
    
    /**
     * Metodo que verifica se o email e a password dados sao os do proprietario
     * 
     * @param  ema   o email a comparar
     * @param  pass   a password a comparar
     * 
     * @return  o resultado da comparacao
     */
    public boolean dadosValidosProprietario(String ema, String pass){
        return(this.email.equals(ema) && this.password.equals(pass));
    }
    
    /**
     * Metodo que adiciona um veiculo ao conjunto de veiculos de um proprietario
     * 
     * @param  vei   o veiculo a adicionar
     */
    public void adicionaVeiculo(Veiculo v){
        for(Veiculo a : this.veiculos){
            if(a.getMatricula().equals(v.getMatricula())){
                this.veiculos.remove(a);
                break;
            }
        }
        this.veiculos.add(v.clone());
    }
    
    /**
     * Metodo que insere um aluguer na lista de alugueres
     * 
     * @param  a   aluguer a ser inserido
     */
    public void insereAluguer(Aluguer a){
        this.alugueres.add(a.clone());
    }
    
    /**
     * Metodo que insere um aluguer na lista de alugueres
     * 
     */
    public boolean verificaAluguerPendente(){
        for(Aluguer a : this.getAlugueres()){
            if(a.getPendente() == 1)
                return true;
        }
        return false;
    }
    
    /**
     * Metodo que devolve o aluguer pendente
     * 
     * @param  a   aluguer a ser inserido
     */
    public Aluguer aluguerPendente(){
        Aluguer b = null;
        for(Aluguer a : this.getAlugueres()){
            if(a.getPendente() == 1)
                b = a;
        }
        return b;
    }
    
    /**
     * Metodo que devolve a autonomia
     * 
     * @param  a   aluguer a ser inserido
     */
    public void verificaAutonomia(){
        for(Veiculo v : this.getVeiculos()){
            if(v.determinaAutonomia() < 10)
                System.out.println("O veiculo " + v.getMatricula() + " esta com a autonomia inferior a 10.\n");
        }
    }
    
    
    /**
     * Metodo que altera a classificacao
     */
    public void alteraClassificacao(double cla){
        double classi = this.classificacao * this.alugueres.size();
        classi = (classi + cla)/(this.alugueres.size() + 1);
        setClassificacao(classi);
    }
}