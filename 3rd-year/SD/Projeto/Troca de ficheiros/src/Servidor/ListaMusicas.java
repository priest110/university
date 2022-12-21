package Servidor;

import Comum.EtiquetaInvalidaException;
import Comum.IDInvalidoException;

import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

public class ListaMusicas {
    private Map<Integer, Musica> musicas; // lista de mús icas
    private Integer id; // id da próxima música que for adicionada
    private ReentrantLock lockLista; // lock do objeto ListaMusicas

    /**
     * Método construtor
     */
    public ListaMusicas(){
        this.musicas = new HashMap<>();
        this.id = 0;
        this.lockLista = new ReentrantLock();
    }

    /**
     * Adicionar uma música à lista de músicas
     * @param titulo        da música
     * @param interprete    da música
     * @param ano           da música
     * @param etiquetas     da música
     * @return  id da musica inserida
     */
    public int publicarMusica(String titulo, String interprete, int ano, String[] etiquetas){
        this.lockLista.lock();
        int id_Criado = this.id++;
        this.musicas.put(id_Criado, new Musica(titulo, interprete, ano, etiquetas, 0));
        this.lockLista.unlock();
        return id_Criado;
    }

    /**
     * Devolve a lista de músicas que têm uma determinada etiqueta
     * @param etiqueta      a procurar
     * @return  lista de músicas(em map) que contém a etiqueta
     * @throws EtiquetaInvalidaException
     */
    public Map<Integer,Musica> musicaByEtiqueta(String etiqueta) throws EtiquetaInvalidaException {
        Map<Integer, Musica> x = new HashMap<>();
        this.lockLista.lock();
        for(Integer a : this.musicas.keySet()){
            String[] etiquetas = this.musicas.get(a).getEtiquetas();
            List<String> list = Arrays.asList(etiquetas);
            if(list.contains(etiqueta))
                x.put(a, this.musicas.get(a));
        }
        this.lockLista.unlock();
        if(x.isEmpty())
            throw  new EtiquetaInvalidaException();
        return x;
    }

    /**
     * Verifica se a lista de músicas contém alguma com determinado id
     * @param id        a verificar
     * @return  música associada ao id, caso haja
     * @throws  IDInvalidoException
     */
    public Musica existeID(int id) throws IDInvalidoException {
        this.lockLista.lock();
        Musica x = this.musicas.get(id);
        if(x == null) {
            this.lockLista.unlock();
            throw new IDInvalidoException();
        }
        this.musicas.get(id).setVezes_descarregada(x.getVezes_descarregada()+1);
        this.lockLista.unlock();
        return x;
    }

}
