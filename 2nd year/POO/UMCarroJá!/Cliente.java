import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;
/**
 * Cliente - pessoa que solicita e efetua o aluguer de um carro.
 * 
 * @author (Ana Rita Rosendo, Gonçalo Esteves, Rui Oliveira) 
 * @version 29 de abril de 2019
 */
public class Cliente extends Dados implements Serializable{
    private String email;
    private String nome;
    private String password;
    private String morada;
    private LocalDate ddn;
    private int nif;
    private double localX;
    private double localY;
    private List <Aluguer> alugueres;

    /**
     * Construtor para objetos da classe Cliente (por omissao)
     */
    public Cliente(){
        this.email = "n/a";
        this.nome = "n/a";
        this.password = "n/a";
        this.morada = "n/a";
        this.ddn = LocalDate.now();
        this.nif = 0;
        this.localX = 0;
        this.localY = 0;
        this.alugueres = new ArrayList <>();
    }
    
    /**
     * Construtor para objetos da classe Cliente (parametrizado).
     * 
     * @param  em   o email
     * @param  nm   o nome
     * @param  pw   a password
     * @param  mr   a morada
     * @param  data   a data de nascimento
     * @param  n   o nif do cliente
     * @param  x   a coordenada X da localizacao
     * @param  y   a coordenada Y da localizacao
     * @param  al   os alugueres
     */
    public Cliente(String em, String nm, String pw, String mr, LocalDate data, int n, double x, double y, List<Aluguer> al){
        this.setEmail(em);
        this.setNome(nm);
        this.setPassword(pw);
        this.setMorada(mr);
        this.setDDN(data);
        this.setNif(n);
        this.setLocalX(x);
        this.setLocalY(y);
        this.setAlugueres(al);
    }
    
    /**
     * Construtor para objetos da classe Cliente (de copia)
     * 
     * @param  c   o cliente
     */
    public Cliente(Cliente c){
        this.email = c.getEmail();
        this.nome = c.getNome();
        this.password = c.getPassword();
        this.morada = c.getMorada();
        this.ddn = c.getDDN();
        this.nif = c.getNif();
        this.localX = c.getLocalX();
        this.localY = c.getLocalY();
        this.alugueres = c.getAlugueres();
    }
    
    /**
     * Metodo que devolve o email de um dado cliente
     * 
     * @return     o email do cliente 
     */
    public String getEmail(){
        return this.email;
    }
    
    /**
     * Metodo que altera o email de um dado cliente
     * 
     * @param  em   o novo email 
     */
    public void setEmail(String em){
        this.email = em;
    }
    
    /**
     * Metodo que devolve o nome de um dado cliente
     * 
     * @return     o nome do cliente 
     */
    public String getNome(){
        return this.nome;
    }
    
    /**
     * Metodo que altera o nome de um dado cliente
     * 
     * @param  nm   o novo nome 
     */
    public void setNome(String nm){
        this.nome = nm;
    }
    
    /**
     * Metodo que devolve a password de um dado cliente
     * 
     * @return     a password do cliente 
     */
    public String getPassword(){
        return this.password;
    }
    
    /**
     * Metodo que altera a password de um dado cliente
     * 
     * @param  pd   a nova password 
     */
    public void setPassword(String pd){
        this.password = pd;
    }
    
    /**
     * Metodo que devolve a morada de um dado cliente
     * 
     * @return     a morada do cliente 
     */
    public String getMorada(){
        return this.morada;
    }
    
    /**
     * Metodo que altera a morada de um dado cliente
     * 
     * @param  mr   a nova morada
     */
    public void setMorada(String mr){
        this.morada = mr;
    }
    
    /**
     * Metodo que devolve a data de nascimento de um dado cliente
     * 
     * @return     a data de nascimento do cliente 
     */
    public LocalDate getDDN(){
        return this.ddn;
    }
    
    /**
     * Metodo que altera a data de nascimento de um dado cliente
     * 
     * @param  data   a nova data de nascimento 
     */
    public void setDDN(LocalDate data){
        this.ddn = data;
    }
    
    /**
     * Metodo que devolve o nif de um dado cliente
     * 
     * @return     o nif do cliente 
     */
    public int getNif(){
        return this.nif;
    }
    
    /**
     * Metodo que altera o nif de um dado cliente
     * 
     * @param  data   o novo nif 
     */
    public void setNif(int n){
        this.nif = n;
    }
    
    /**
     * Metodo que devolve a coordenada X de um dado cliente
     * 
     * @return     a coordenada X do cliente 
     */
    public double getLocalX(){
        return this.localX;
    }
    
    /**
     * Metodo que altera a coordenada X de um dado cliente
     * 
     * @param  x   a nova coordenada X
     */
    public void setLocalX(double x){
        this.localX = x;
    }
    
    /**
     * Metodo que devolve a coordenada Y de um dado cliente
     * 
     * @return     a coordenada Y do cliente 
     */
    public double getLocalY(){
        return this.localY;
    }
    
    /**
     * Metodo que altera a coordenada Y de um dado cliente
     * 
     * @param  y   a nova coordenada Y
     */
    public void setLocalY(double y){
        this.localY = y;
    }
    
    /**
     * Metodo que devolve os alugueres de um dado cliente
     * 
     * @return     os alugueres do cliente 
     */
    public List<Aluguer> getAlugueres(){
        List<Aluguer> aux = new ArrayList<>();
        for(Aluguer a : this.alugueres)
            aux.add(a.clone());
        return aux;
    }
    
    /**
     * Metodo que altera os alugueres de um dado cliente
     * 
     * @param  al   os novos alugueres
     */
    public void setAlugueres(List<Aluguer> al){
        this.alugueres = new ArrayList<>();
        for(Aluguer a : al)
            this.alugueres.add(a.clone());
    }
    
    /**
     * Metodo que duplica o cliente
     * 
     * @return     o clone do cliente
     */
    public Cliente clone(){
        return new Cliente(this);
    }
    
    /**
     * Metodo que verifica se dois clientes sao iguais
     * 
     * @param  o   o objeto a comparar
     * 
     * @return     o resultado da comparacao dos dois clientes
     */
    public boolean equals(Object o){
        if(o == this)
            return true;
        if(o == null || o.getClass() != this.getClass())
            return false;
        else{
            Cliente c = (Cliente) o;
            return(c.getEmail().equals(this.getEmail())
                   && c.getNome().equals(this.getNome())
                   && c.getPassword().equals(this.getPassword())
                   && c.getMorada().equals(this.getMorada())
                   && c.getDDN().equals(this.getDDN())
                   && c.getNif() == this.getNif()
                   && c.getLocalX() == this.getLocalX()
                   && c.getLocalY() == this.getLocalY()
                   && c.getAlugueres().equals(this.alugueres));
        }
    }
    
    /**
     * Metodo que converte um cliente para uma string
     * 
     * @return     o cliente em string
     */
    public String toString(){
        String aux = "Email: " + this.email + ";\n" 
                     + "Nome: " + this.nome + ";\n"
                     + "Password: " + this.password + ";\n"
                     + "Morada: " + this.morada + ";\n"
                     + "Data de Nascimento: " + this.ddn.toString() + ";\n"
                     + "Nif: " + this.nif + ";\n"
                     + "Coordenada X: " + this.localX + ";\n"
                     + "Coordenada Y: " + this.localY + ";\n"
                     + "Alugueres: \n";
        for(Aluguer a : this.alugueres)
            aux += a.toString() + "\n";
        return aux;
    }
    
    /**
     * Metodo que devolve o numero de alugueres de um cliente
     * 
     * @return     o cliente em string
     */
    public String toString_top10(){
        String aux = "O cliente " + this.email + " usou o sistema " + this.getAlugueres().size() + " vezes\n";
        return aux;
    }
    
    /**
     * Metodo que devolve o numero de alugueres de um cliente
     * 
     * @return     o cliente em string
     */
    public String toString_top10_distancia(){
        double km = 0.0;
        for(Aluguer a : this.getAlugueres()){
            km += a.getKm();
        }
        String aux = "O cliente " + this.email + " percorreu " + km + " km\n";
        return aux;
    }
    
    /**
     * Metodo que devolve o codigo de hash para um cliente
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
        long aux = Double.doubleToLongBits(localX);
        hash = 31 * hash + (int)(aux ^ (aux >>> 32));
        long aux1 = Double.doubleToLongBits(localY);
        hash = 31 * hash + (int)(aux1 ^ (aux1 >>> 32));
        for(Aluguer a : this.alugueres)
            hash = 31 * hash + a.hashCode();
        return hash;
    }
    
    /**
     * Metodo que implementa a ordem natural de comparacao de instancias de Cliente
     */
    public int compareTo(Cliente c){
        return c.getEmail().compareTo(this.email);
    }
    
    /**
     * Metodo que verifica se o email e a password dados sao os do cliente
     * 
     * @param  ema   o email a comparar
     * @param  pass   a password a comparar
     * 
     * @return  o resultado da comparacao
     */
    public boolean dadosValidosCliente(String ema, String pass){
        return(this.email.equals(ema) && this.password.equals(pass));
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
            if(a.getPendente() == 2)
                return true;
        }
        return false;
    }
    
    /**
     * Metodo que insere um aluguer na lista de alugueres
     * 
     */
    public boolean verificaAluguerRejeitado(){
        for(Aluguer a : this.getAlugueres()){
            if(a.getPendente() == 3)
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
            if(a.getPendente() == 2)
                b = a;
        }
        return b;
    }
    
    /**
     * Metodo que devolve o aluguer pendente 3
     * 
     * @param  a   aluguer a ser inserido
     */
    public Aluguer aluguerPendente3(){
        Aluguer b = null;
        for(Aluguer a : this.getAlugueres()){
            if(a.getPendente() == 3)
                b = a;
        }
        return b;
    }
}
