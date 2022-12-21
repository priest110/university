import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;
/**
 * Veiculo
 * 
 * @author (Ana Rita Rosendo, Gonçalo Esteves, Rui Oliveira) 
 * @version 29 de abril de 2019
 */
public abstract class Veiculo extends Dados implements Serializable{
    private String matricula;
    private double velocidade;
    private double preco;
    private List<Aluguer> alugueres;
    private double classificacao;
    private double totalFaturado;
    private double localX;
    private double localY;
    private boolean disponivel;

    /**
     * Construtor para objetos da classe Veiculo (por omissao)
     */
    public Veiculo(){
        this.matricula = "n/a";
        this.velocidade = 0;
        this.preco = 0;
        this.alugueres = new ArrayList<>();
        this.classificacao = 0;
        this.totalFaturado = 0;
        this.localX = 0;
        this.localY = 0;
        this.disponivel = true;
    }
    
    /**
     * Construtor para objetos da classe Veiculo (por parametrizacao)
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
     */
    public Veiculo(String m, double v, double p, List<Aluguer> al, double cla, double tf, double x, double y, boolean d){
        this.setMatricula(m);
        this.setVelocidade(v);
        this.setPreco(p);
        this.setAlugueresCarro(al);
        this.setClassificacaoCarro(cla);
        this.setTotalFaturado(tf);
        this.setLocalX(x);
        this.setLocalY(y);
        this.setDisponivel(d);
    }
    
    /**
     * Construtor para objetos da classe Veiculo (por copia)
     * 
     * @param  v   o veiculo a copiar
     */
    public Veiculo(Veiculo v){
        this.matricula = v.getMatricula();
        this.velocidade = v.getVelocidade();
        this.preco = v.getPreco();
        this.alugueres = v.getAlugueresCarro();
        this.classificacao = v.getClassificacaoCarro();
        this.totalFaturado = v.getTotalFaturado();
        this.localX = v.getLocalX();
        this.localY = v.getLocalY();
        this.disponivel = v.getDisponivel();
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
     * Metodo que devolve a velocidade do veiculo
     * 
     * @return     a velocidade do veiculo
     */
    public double getVelocidade(){
        return this.velocidade;
    }
    
    /**
     * Metodo que altera a velocidade do veiculo
     * 
     * @param  k   a velocidade do veiculo
     */
    public void setVelocidade(double v){
        this.velocidade = v;
    }
    
    /**
     * Metodo que devolve o preço por km
     * 
     * @return     o preço por km
     */
    public double getPreco(){
        return this.preco;
    }
    
    /**
     * Metodo que altera o preço por km
     * 
     * @param  k   o preço por km
     */
    public void setPreco(double p){
        this.preco = p;
    }
    
    /**
     * Metodo que devolve os alugueres de um veiculo
     * 
     * @return     os alugueres do veiculo
     */
    public List<Aluguer> getAlugueresCarro(){
        List<Aluguer> aux = new ArrayList<>();
        for(Aluguer a : this.alugueres)
            aux.add(a.clone());
        return aux;
    }
    
    /**
     * Metodo que altera os alugueres de um veiculo
     * 
     * @param  al   os novos alugueres do veiculo 
     */
    public void setAlugueresCarro(List<Aluguer> al){
        this.alugueres = new ArrayList<>();
        for(Aluguer a : al)
            this.alugueres.add(a.clone());
    }
    
    /**
     * Metodo que devolve a classificacao de um dado veiculo
     * 
     * @return     a classificacao do veiculo
     */
    public double getClassificacaoCarro(){
        return this.classificacao;
    }
    
    /**
     * Metodo que altera a classificacao de um dado veiculo
     * 
     * @param  cla   a nova classificacao do veiculo
     */
    public void setClassificacaoCarro(double cla){
        this.classificacao = cla;
    }
    
    /**
     * Metodo que devolve o total fatarudo por um dado veiculo
     * 
     * @return     o total fatarudo por um dado veiculo
     */
    public double getTotalFaturado(){
        return this.totalFaturado;
    }
    
    /**
     * Metodo que altera o total fatarudo por um dado veiculo
     * 
     * @param  t   o total fatarudo por um dado veiculo
     */
    public void setTotalFaturado(double t){
        this.totalFaturado = t;
    }
    
    /**
     * Metodo que devolve a coordenada X de um dado veiculo
     * 
     * @return     a coordenada X do veiculo
     */
    public double getLocalX(){
        return this.localX;
    }
    
    /**
     * Metodo que altera a coordenada X de um dado veiculo
     * 
     * @param  x   a nova coordenada X do veiculo
     */
    public void setLocalX(double x){
        this.localX = x;
    }
    
    /**
     * Metodo que devolve a coordenada Y de um dado veiculo
     * 
     * @return     a coordenada Y do veiculo
     */
    public double getLocalY(){
        return this.localY;
    }
    
    /**
     * Metodo que altera a coordenada Y de um dado veiculo
     * 
     * @param  y   a nova coordenada Y do veiculo
     */
    public void setLocalY(double y){
        this.localY = y;
    }
    
    /**
     * Metodo que devolve a disponibilidade de um dado veiculo
     * 
     * @return     a disponibilidade do veiculo
     */
    public boolean getDisponivel(){
        return this.disponivel;
    }
    
    /**
     * Metodo que altera a disponibilidade de um dado veiculo
     * 
     * @param  d   a nova disponibilidade do veiculo
     */
    public void setDisponivel(boolean d){
        this.disponivel = d;
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
     * Metodo que verifica a autonomia
     */
    public void verificaAutonomia(){
        if(determinaAutonomia() < 10)
            setDisponivel(false);
    }
    
    /**
     * Metodo que altera a classificacao
     */
    public void alteraClassificacao(double cla){
        double classi = this.classificacao * this.alugueres.size();
        classi = (classi + cla)/(this.alugueres.size() + 1);
        setClassificacaoCarro(classi);
    }
    
    /**
     * Metodo que duplica o veiculo
     * 
     * @return     o clone do veiculo
     */
    public abstract Veiculo clone();
    
    /**
     * Metodo que verifica se dois veiculos sao iguais
     * 
     * @param  o   o objeto a comparar
     * 
     * @return     o resultado da comparacao dos objetos
     */
    public abstract boolean equals(Object o);
    
    /**
     * Metodo que converte um veiculo para uma string
     * 
     * @return     o veiculo em string
     */
    public abstract String toString();
    
    /**
     * Metodo que determina a autonomia do veiculo (quantidade disponivel de combustivel/bateria), em percentagem
     * 
     * @return     a autonomia 
     */
    public abstract double determinaAutonomia();
    
    /**
     * Metodo que determina a autonomia do veiculo apos deslocamento, em percentagem
     * 
     * @param  d   a distancia a percorrer
     * 
     * @return     a autonomia 
     */
    public abstract double autonomiaAposDeslocamento(double d);
    
    /**
     * Metodo que determina o combustivel do veiculo apos deslocamento
     * 
     * @param  d   a distancia a percorrer
     * 
     * @return     o combustivel
     */
    public abstract void combAposDeslocamento(double d);
}
