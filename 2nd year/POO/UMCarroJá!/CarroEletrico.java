import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;
/**
 * CarroEletrico
 * 
 * @author (Ana Rita Rosendo, Gonçalo Esteves, Rui Oliveira) 
 * @version 29 de abril de 2019
 */
public class CarroEletrico extends Veiculo implements Serializable{
    private double consumo;
    private double bateria;
    private double bateriaMax;

    /**
     * Construtor para objetos da classe CarroEletrico (por omissao)
     */
    public CarroEletrico(){
        super();
        this.consumo = 0;
        this.bateria = 0;
        this.bateriaMax = 0;
    }
    
    /**
     * Construtor para objetos da classe CarroEletrico (por parametrizacao)
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
     * @param  b   a bateria
     * @param  bm   a bateria maxima
     */
    public CarroEletrico(String m, double v, double p, List<Aluguer> al, double cla, double tf, double x, double y, boolean d, double con, double b, double bm){
        super(m, v, p, al, cla, tf, x, y, d);
        this.setConsumo(con);
        this.setBateria(b);
        this.setBateriaMax(bm);
    }
    
    /**
     * Construtor para objetos da classe CarroEletrico (por copia)
     * 
     * @param  v   o carro a copiar
     */
    public CarroEletrico(CarroEletrico v){
        super(v);
        this.consumo = v.getConsumo();
        this.bateria = v.getBateria();
        this.bateriaMax = v.getBateriaMax();
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
     * Metodo que devolve a bateria do carro
     * 
     * @return     a bateria do carro
     */
    public double getBateria(){
        return this.bateria;
    }
    
    /**
     * Metodo que altera a bateria
     * 
     * @param  y   a nova bateria
     */
    public void setBateria(double y){
        this.bateria = y;
    }
    
    /**
     * Metodo que devolve a bateria maxima do carro
     * 
     * @return     a bateria maxima do carro
     */
    public double getBateriaMax(){
        return this.bateriaMax;
    }
    
    /**
     * Metodo que altera a bateria maxima
     * 
     * @param  y   a nova bateria maxima
     */
    public void setBateriaMax(double y){
        this.bateriaMax = y;
    }
    
    /**
     * Metodo que duplica o carro
     * 
     * @return     o clone do carro
     */
    public CarroEletrico clone(){
        return new CarroEletrico(this);
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
            CarroEletrico c = (CarroEletrico) o;
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
                   && c.getBateria() == this.getBateria()
                   && c.getBateriaMax() == this.getBateriaMax());
                   
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
                     + "Bateria: " + this.getBateria() + ";\n"
                     + "BateriaMax: " + this.getBateriaMax() + ";\n"
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
        double aut = ((this.bateria)/(this.bateriaMax))*100;
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
        double apos = this.bateria - (this.consumo * d); //combustivel novo
        double aut = (apos/(this.bateriaMax))*100;
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
        setBateria(this.bateria - (this.consumo * d)); //bateria novo
    }
}
