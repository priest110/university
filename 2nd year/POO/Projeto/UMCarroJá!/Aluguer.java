import java.io.Serializable;
/**
 * Aluguer - interface onde vao ser guardados os dados de um aluguer
 * 
 * @author (seu nome) 
 * @version (número da versão ou data)
 */

public interface Aluguer extends Serializable{  
    /**
     * Metodo que devolve o email do cliente
     * 
     * @return     o email do cliente 
     */
    public String getEmailCli();
    
    /**
     * Metodo que altera o email do cliente
     * 
     * @param  em   o novo email 
     */
    public void setEmailCli(String em);
    
    /**
     * Metodo que devolve o nome do proprietario
     * 
     * @return     o nome do proprietario 
     */
    public String getEmailPro();
    
    /**
     * Metodo que altera o nome do proprietario
     * 
     * @param  em   o novo email
     */
    public void setEmailPro(String em);
    
    /**
     * Metodo que devolve a matricula
     * 
     * @return     a matricula 
     */
    public String getMatricula();
    
    /**
     * Metodo que altera a matricula
     * 
     * @param  m   a nova matricula
     */
    public void setMatricula(String m);
    
    /**
     * Metodo que devolve os quilometros percorridos
     * 
     * @return     os quilometros percorridos 
     */
    public double getKm();
    
    /**
     * Metodo que altera os quilometros percorridos
     * 
     * @param  k   os quilometros percorridos
     */
    public void setKm(double k);
    
    /**
     * Metodo que devolve o custo da viagem
     * 
     * @return     o custo da viagem 
     */
    public double getCusto();
    
    /**
     * Metodo que altera o custo de uma viagem
     * 
     * @param  c   o custo da viagem
     */
    public void setCusto(double c);
    
    /**
     * Metodo que devolve o estado do aluguer
     * 
     * @return     o estado do aluguer
     */
    public double getPendente();
    
    /**
     * Metodo que altera o estado do aluguer
     * 
     * @param  c   o estado do aluguer
     */
    public void setPendente(double p);
    
    /**
     * Metodo que devolve a coordenada X de um dado cliente
     * 
     * @return     a coordenada X do cliente 
     */
    public double getLocalX();
    
    /**
     * Metodo que altera a coordenada X de um dado cliente
     * 
     * @param  x   a nova coordenada X
     */
    public void setLocalX(double x);
    
    /**
     * Metodo que devolve a coordenada Y de um dado cliente
     * 
     * @return     a coordenada Y do cliente 
     */
    public double getLocalY();
    
    /**
     * Metodo que altera a coordenada Y de um dado cliente
     * 
     * @param  y   a nova coordenada Y
     */
    public void setLocalY(double y);
    
    /**
     * Metodo que duplica o aluguer
     * 
     * @return     o clone do aluguer
     */
    public Aluguer clone();
    
    /**
     * Metodo que verifica se dois alugueres sao iguais
     * 
     * @param  o   o objeto a comparar
     * 
     * @return     o resultado da comparacao dos dois alugueres
     */
    public boolean equals(Object o);
    
    /**
     * Metodo que converte um cliente para uma string
     * 
     * @return     o cliente em string
     */
    public String toString();
    
    /**
     * Metodo que devolve o codigo de hash para um cliente
     * 
     * @return     o hashcode
     */
    public int hashCode();
    
    /**
     * Metodo que implementa a ordem natural de comparacao de instancias de AluguerCarro
     */
    public int compareTo(AluguerCarro c);
}
