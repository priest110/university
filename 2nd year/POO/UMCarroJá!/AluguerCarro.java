import java.time.LocalDate;
import java.io.Serializable;
/**
 * AluguerCarro - registo de uma viagem.
 * 
 * @author (Ana Rita Rosendo, Gonçalo Esteves, Rui Oliveira) 
 * @version 29 de abril de 2019
 */
public class AluguerCarro implements Aluguer, Serializable{
    private String emailCli;
    private String emailPro;
    private String matricula;
    private double km;
    private double custo;
    private double pendente;//pode ser 0->nao esta pendente, 1->esta a espera de resposta do proprietario,
                         //2->ja foi confirmado pelo proprietario, 3->foi negado pelo proprietario
    private double localX;
    private double localY;

    /**
     * Construtor para objetos da classe AluguerCarro (por omissao)
     */
    public AluguerCarro(){
        this.emailCli = "n/a";
        this.emailPro = "n/a";
        this.matricula = "n/a";
        this.km = 0;
        this.custo = 0;
        this.pendente = 0;
        this.localX = 0;
        this.localY = 0;
    }
    
    /**
     * Construtor para objetos da classe AluguerCarro (parametrizado).
     * 
     * @param  ec   o email do cliente
     * @param  ep   o email do proprietario
     * @param  m   a matricula
     * @param  k   os quilometros percorridos
     * @param  c   o custo da viagem
     * @param  p   o estado do aluguer
     * @param  x   a coordenada x de destino
     * @param  y   a coordenada y de destino
     */
    public AluguerCarro(String ec, String ep, String m, double k, double c, double p, double x, double y){
        this.setEmailCli(ec);
        this.setEmailPro(ep);
        this.setMatricula(m);
        this.setKm(k);
        this.setCusto(c);
        this.setPendente(p);
        this.setLocalX(x);
        this.setLocalY(y);
    }
    
    /**
     * Construtor para objetos da classe AluguerCarro (de copia)
     * 
     * @param  a   o aluguer
     */
    public AluguerCarro(AluguerCarro a){
        this.emailCli = a.getEmailCli();
        this.emailPro = a.getEmailPro();
        this.matricula = a.getMatricula();
        this.km = a.getKm();
        this.custo = a.getCusto();
        this.pendente = a.getPendente();
        this.localX = a.getLocalX();
        this.localY = a.getLocalY();
    }
    
    /**
     * Metodo que devolve o email do cliente
     * 
     * @return     o email do cliente 
     */
    public String getEmailCli(){
        return this.emailCli;
    }
    
    /**
     * Metodo que altera o email do cliente
     * 
     * @param  em   o novo email 
     */
    public void setEmailCli(String em){
        this.emailCli = em;
    }
    
    /**
     * Metodo que devolve o nome do proprietario
     * 
     * @return     o nome do proprietario 
     */
    public String getEmailPro(){
        return this.emailPro;
    }
    
    /**
     * Metodo que altera o nome do proprietario
     * 
     * @param  em   o novo email
     */
    public void setEmailPro(String em){
        this.emailPro = em;
    }
    
    /**
     * Metodo que devolve a matricula
     * 
     * @return     a matricula 
     */
    public String getMatricula(){
        return this.matricula;
    }
    
    /**
     * Metodo que altera a matricula
     * 
     * @param  m   a nova matricula
     */
    public void setMatricula(String m){
        this.matricula = m;
    }
    
    /**
     * Metodo que devolve os quilometros percorridos
     * 
     * @return     os quilometros percorridos 
     */
    public double getKm(){
        return this.km;
    }
    
    /**
     * Metodo que altera os quilometros percorridos
     * 
     * @param  k   os quilometros percorridos
     */
    public void setKm(double k){
        this.km = k;
    }
    
    /**
     * Metodo que devolve o custo do aluguer
     * 
     * @return     o custo da viagem 
     */
    public double getCusto(){
        return this.custo;
    }
    
    /**
     * Metodo que altera o custo do aluguer
     * 
     * @param  c   o custo da viagem
     */
    public void setCusto(double c){
        this.custo = c;
    }
    
    /**
     * Metodo que devolve o estado do aluguer
     * 
     * @return     o estado do aluguer
     */
    public double getPendente(){
        return this.pendente;
    }
    
    /**
     * Metodo que altera o estado do aluguer
     * 
     * @param  c   o estado do aluguer
     */
    public void setPendente(double p){
        this.pendente = p;
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
     * Metodo que duplica o aluguer
     * 
     * @return     o clone do aluguer
     */
    public AluguerCarro clone(){
        return new AluguerCarro(this);
    }
    
    /**
     * Metodo que verifica se dois alugueres sao iguais
     * 
     * @param  o   o objeto a comparar
     * 
     * @return     o resultado da comparacao dos dois alugueres
     */
    public boolean equals(Object o){
        if(o == this)
            return true;
        if(o == null || o.getClass() != this.getClass())
            return false;
        else{
            AluguerCarro a = (AluguerCarro) o;
            return(a.getEmailCli().equals(this.getEmailCli())
                   && a.getEmailPro().equals(this.getEmailPro())
                   && a.getMatricula().equals(this.getMatricula())
                   && a.getKm() == this.getKm()
                   && a.getCusto() == this.getCusto())
                   && a.getPendente() == this.getPendente()
                   && a.getLocalX() == this.getLocalX()
                   && a.getLocalY() == this.getLocalY();
        }
    }
    
    /**
     * Metodo que converte um cliente para uma string
     * 
     * @return     o cliente em string
     */
    public String toString(){
        String aux = "Email do Cliente: " + this.emailCli + ";\n" 
                     + "Email do Proprietario: " + this.emailPro + ";\n"
                     + "Matricula: " +  this.matricula + ";\n"
                     + "Quilometros: " + this.km + ";\n"
                     + "Custo: " + this.custo + ";\n"
                     + "Estado: " + this.pendente + ";\n"
                     + "Destino X " + this.localX + ";\n"
                     + "Destino Y: " + this.localY + ";\n";
        return aux;
    }
    
    /**
     * Metodo que devolve o codigo de hash para um cliente
     * 
     * @return     o hashcode
     */
    public int hashCode(){
        int hash = 7; 
        hash = 31 * hash + emailCli.hashCode();
        hash = 31 * hash + emailPro.hashCode();
        hash = 31 * hash + matricula.hashCode();
        long aux = Double.doubleToLongBits(km);
        hash = 31 * hash + (int)(aux ^ (aux >>> 32));
        long aux1 = Double.doubleToLongBits(custo);
        hash = 31 * hash + (int)(aux1 ^ (aux1 >>> 32));
        long aux2 = Double.doubleToLongBits(pendente);
        hash = 31 * hash + (int)(aux2 ^ (aux2 >>> 32));
        long aux3 = Double.doubleToLongBits(localX);
        hash = 31 * hash + (int)(aux3 ^ (aux3 >>> 32));
        long aux4 = Double.doubleToLongBits(localY);
        hash = 31 * hash + (int)(aux4 ^ (aux4 >>> 32));
        return hash;
    }
    
    /**
     * Metodo que implementa a ordem natural de comparacao de instancias de AluguerCarro
     */
    public int compareTo(AluguerCarro c){
        return c.getMatricula().compareTo(this.matricula);
    }
}
