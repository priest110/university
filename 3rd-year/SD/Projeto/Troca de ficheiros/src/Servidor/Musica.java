package Servidor;

import java.util.concurrent.locks.ReentrantLock;

public class Musica {
    private String titulo;
    private String interprete;
    private int ano;
    private String[] etiquetas;
    private int vezes_descarregada;

    /**
     * Método construtor
     * @param titulo        da música
     * @param interprete    da música
     * @param ano           da música
     * @param etiquetas     da música
     * @param vezes_descarregada    nº de vezes que a música foi descarregada
     */
    public Musica(String titulo, String interprete, int ano, String[] etiquetas, int vezes_descarregada) {
        this.titulo = titulo;
        this.interprete = interprete;
        this.ano = ano;
        this.etiquetas = etiquetas;
        this.vezes_descarregada = vezes_descarregada;
    }

    /**
     * Get do título da música
     */
    public String getTitulo() {
        return this.titulo;
    }

    /**
     * Get do intérprete da música
     */
    public String getInterprete() {
        return this.interprete;
    }

    /**
     * Get do ano da música
     */
    public int getAno() {
        return this.ano;
    }

    /**
     * Get das etiquetas da música
     */
    public String[] getEtiquetas() {
        return this.etiquetas;
    }

    /**
     * Get do nº de vezes que a música foi descarregada
     */
    public int getVezes_descarregada() {
        return this.vezes_descarregada;
    }

    /**
     * Set do nº de vezes que a música foi descarregada.
     */
    public void setVezes_descarregada(int vezes_descarregada) {
        this.vezes_descarregada = vezes_descarregada;
    }

}
