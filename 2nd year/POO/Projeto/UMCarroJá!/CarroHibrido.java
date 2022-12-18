
import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;
/**
 * CarroHibrido
 *
 * @author (Ana Rita Rosendo, Gonçalo Esteves, Rui Oliveira) 
 * @version 1 de maio de 2019
 */
public class CarroHibrido extends Veiculo implements Serializable{
    private double consumoBat;
    private double bateria;
    private double bateriaMax;
    private double consumoComb;
    private double combustivel;
    private double combustivelMax;
    /**
     * Construtor para objetos da classe CarroHibrido (por omissao)
     */
    public CarroHibrido(){
        super();
        this.consumoBat = 0;
        this.bateria = 0;
        this.bateriaMax = 0;
        this.consumoComb = 0;
        this.combustivel = 0;
        this.combustivelMax = 0;
    }
    
    /**
     * Construtor para objetos da classe CarroHibrido (por parametrizacao)
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
     * @param  conb   o consumo de bateria por km
     * @param  b   a bateria
     * @param  bm   a bateria maxima
     * @param  conc   o consumo de combustivel por km
     * @param  cc   o combustivel 
     * @param  cm   o combustivel maximo
     */
    public CarroHibrido(String m, double v, double p, List<Aluguer> al, double cla, double tf, double x, double y, boolean d, double conb, double b, double bm, double conc, double cc, double cm){
        super(m, v, p, al, cla, tf, x, y, d);
        this.setConsumoBat(conb);
        this.setBateria(b);
        this.setBateriaMax(bm);
        this.setConsumoComb(conc);
        this.setCombustivel(cc);
        this.setCombustivelMax(cm);
    }
    
    /**
     * Construtor para objetos da classe CarroHibrido (por copia)
     * 
     * @param  v   o carro a copiar
     */
    public CarroHibrido(CarroHibrido v){
        super(v);
        this.consumoBat = v.getConsumoBat();
        this.bateria = v.getBateria();
        this.bateriaMax = v.getBateriaMax();
        this.consumoComb = v.getConsumoComb();
        this.combustivel = v.getCombustivel();
        this.combustivelMax = v.getCombustivelMax();
    }

    /**
     * Metodo que devolve o consumo de bateria por km do carro
     * 
     * @return     o consumo por km 
     */
    public double getConsumoBat(){
        return this.consumoBat;
    }
    
    /**
     * Metodo que altera o consumo de bateria por km de um carro
     *
     * @param  x   o novo consumo 
     */
    public void setConsumoBat(double x){
        this.consumoBat = x;
    }
    
    /**
     * Metodo que devolve o consumo de combustivel por km do carro
     * 
     * @return     o consumo por km 
     */
    public double getConsumoComb(){
        return this.consumoComb;
    }
    
    /**
     * Metodo que altera o consumo de combustivel por km de um carro
     *
     * @param  x   o novo consumo 
     */
    public void setConsumoComb(double x){
        this.consumoComb = x;
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
    public CarroHibrido clone(){
        return new CarroHibrido(this);
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
            CarroHibrido c = (CarroHibrido) o;
            return(c.getMatricula().equals(this.getMatricula())
                   && c.getVelocidade() == this.getVelocidade()                   
                   && c.getPreco() == this.getPreco()
                   && c.getAlugueresCarro().equals(this.getAlugueresCarro())
                   && c.getClassificacaoCarro() == this.getClassificacaoCarro()
                   && c.getTotalFaturado() == this.getTotalFaturado()
                   && c.getLocalX() == this.getLocalX()
                   && c.getLocalX() == this.getLocalY()
                   && c.getDisponivel() == this.getDisponivel()
                   && c.getConsumoBat() == this.getConsumoBat()
                   && c.getBateria() == this.getBateria()
                   && c.getBateriaMax() == this.getBateriaMax()
                   && c.getConsumoComb() == this.getConsumoComb()
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
                     + "Consumo de Bateria: " + this.getConsumoBat() + ";\n"
                     + "Bateria: " + this.getBateria() + ";\n"
                     + "BateriaMax: " + this.getBateriaMax() + ";\n"
                     + "Consumo de Combustivel: " + this.getConsumoComb() + ";\n"
                     + "Combustivel: " + this.getCombustivel() + ";\n"
                     + "CombustiveMax: " + this.getCombustivelMax() + ";\n"
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
        double aut = ((this.bateria)/(this.bateriaMax)/2 + (this.combustivel)/(this.combustivelMax)/2)*100;
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
        double aposBat = this.bateria - (this.consumoBat * d/2); //bateria nova
        double aposComb = this.combustivel - (this.consumoComb * d/2);//combustivel novo
        double aut = ((aposBat/(this.bateriaMax))/2 + (aposComb/(this.combustivelMax))/2)*100;
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
        setCombustivel(this.combustivel - (this.consumoComb * d/2));//combustivel novo
        setBateria(this.bateria - (this.consumoBat * d/2)); //bateria nova
    }
}
