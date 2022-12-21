import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;
/**
 * CarroGasolina
 * 
 * @author (Ana Rita Rosendo, Gonçalo Esteves, Rui Oliveira) 
 * @version 29 de abril de 2019
 */
public class CarroGasolina extends Veiculo implements Serializable{
    private double consumo;
    private double combustivel;
    private double combustivelMax;

    /**
     * Construtor para objetos da classe CarroGasolina (por omissao)
     */
    public CarroGasolina(){
        super();
        this.consumo = 0;
        this.combustivel = 0;
        this.combustivelMax = 0;
    }
    
    /**
     * Construtor para objetos da classe CarroGasolina (por parametrizacao)
     * 
     * @param  m   a matricula
     * @param  v   a velocidade por km
     * @param  p   o preco por km
     * @param  al   os alugueres
     * @param  cla   a classificacao
     * @param  tf   o total faturado
     * @param  x   a coordenada x da localizacao
     * @param  y   a coordenada y da localizacao
     * @param  d   a disponibilidade
     * @param  c   o consumo por km
     * @param  com   o combustivel
     * @param  comm   o combustivel maximo
     */
    public CarroGasolina(String m, double v, double p, List<Aluguer> al, double cla, double tf, double x, double y, boolean d, double con, double com, double comm){
        super(m, v, p, al, cla, tf, x, y, d);
        this.setConsumo(con);
        this.setCombustivel(com);
        this.setCombustivelMax(comm);
    }
    
    /**
     * Construtor para objetos da classe CarroGasoleo (por copia)
     * 
     * @param  v   o carro a copiar
     */
    public CarroGasolina(CarroGasolina v){
        super(v);
        this.consumo = v.getConsumo();
        this.combustivel = v.getCombustivel();
        this.combustivelMax = v.getCombustivelMax();
    }

    /**
     * Metodo que devolve o consumo por km do carro
     * 
     * @return     o consumo por km 
     */
    public double getConsumo(){
        return this.consumo;
    }
    
    /**
     * Metodo que altera o consumo por km de um carro
     *
     * @param  x   o novo consumo 
     */
    public void setConsumo(double x){
        this.consumo = x;
    }
    
    /**
     * Metodo que devolve o combustivel do carro
     * 
     * @return     o combustivel do carro
     */
    public double getCombustivel(){
        return this.combustivel;
    }
    
    /**
     * Metodo que altera o combustivel
     * 
     * @param  y   o novo combustivel
     */
    public void setCombustivel(double y){
        this.combustivel = y;
    }
    
    /**
     * Metodo que devolve o combustivel maximo do carro
     * 
     * @return     o combustivel maximo do carro
     */
    public double getCombustivelMax(){
        return this.combustivelMax;
    }
    
    /**
     * Metodo que altera o combustivel maximo
     * 
     * @param  y   o novo combustivel maximo
     */
    public void setCombustivelMax(double y){
        this.combustivelMax = y;
    }
    
    /**
     * Metodo que duplica o carro
     * 
     * @return     o clone do carro
     */
    public CarroGasolina clone(){
        return new CarroGasolina(this);
    }
    
    /**
     * Metodo que verifica se dois carros sao iguais
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
            CarroGasolina c = (CarroGasolina) o;
            return(c.getMatricula().equals(this.getMatricula())
                   && c.getVelocidade() == this.getVelocidade()                   
                   && c.getPreco() == this.getPreco()
                   && c.getAlugueresCarro().equals(this.getAlugueresCarro())
                   && c.getClassificacaoCarro() == this.getClassificacaoCarro()
                   && c.getTotalFaturado() == this.getTotalFaturado()
                   && c.getLocalX() == this.getLocalX()
                   && c.getLocalX() == this.getLocalY()
                   && c.getDisponivel() == this.getDisponivel()
                   && c.getConsumo() == this.getConsumo()
                   && c.getCombustivel() == this.getCombustivel()
                   && c.getCombustivelMax() == this.getCombustivelMax());
                   
        }
    }
    
    /**
     * Metodo que converte um carro para uma string
     * 
     * @return     o carro em string
     */
    public String toString(){
        String aux = "Matricula " + this.getMatricula() + ";\n" 
                     + "Velocidade: " + this.getVelocidade() + ";\n"
                     + "Preco: " + this.getPreco() + ";\n"
                     + "Classificacao " + this.getClassificacaoCarro()+ ";\n"
                     + "Total faturado: " + this.getTotalFaturado() + ";\n"
                     + "LocalX: " + this.getLocalX() + ";\n"
                     + "LocalY: " + this.getLocalY() + ";\n"
                     + "Disponibilidade: " + this.getDisponivel() + ";\n"
                     + "Consumo: " + this.getConsumo() + ";\n"
                     + "Combustivel: " + this.getCombustivel() + ";\n"
                     + "CombustivelMax: " + this.getCombustivelMax() + ";\n"
                     + "Alugueres: \n";
        for(Aluguer a : this.getAlugueresCarro())
            aux += a.toString() + "\n";
        return aux;
    }
    
    /**
     * Metodo que determina a autonomia do carro (quantidade disponivel de bateria), em percentagem
     * 
     * @return     a autonomia 
     */
    public double determinaAutonomia(){
        double aut = ((this.combustivel)/(this.combustivelMax))*100;
        return aut;
    }
    
    /**
     * Metodo que determina a autonomia do veiculo apos deslocamento, em percentagem
     * 
     * @param  d   a distancia a percorrer
     * 
     * @return     a autonomia 
     */
    public double autonomiaAposDeslocamento(double d){
        double apos = this.combustivel - (this.consumo * d); //combustivel novo
        double aut = (apos/(this.combustivelMax))*100;
        return aut;
    }
    
    /**
     * Metodo que determina o combustivel do veiculo apos deslocamento
     * 
     * @param  d   a distancia a percorrer
     * 
     * @return     o combustivel
     */
    public void combAposDeslocamento(double d){
        setCombustivel(this.combustivel - (this.consumo * d)); //combustivel novo
    }
}
