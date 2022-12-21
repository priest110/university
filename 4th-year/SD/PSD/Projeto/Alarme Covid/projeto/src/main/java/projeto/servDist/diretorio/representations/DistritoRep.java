package projeto.servDist.diretorio.representations;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DistritoRep {
    public String nome;
    public int utilizadores;
    public int infetados;

    @JsonCreator
    public DistritoRep(@JsonProperty("nome") String nome, @JsonProperty("utilizadores") int utilizadores, @JsonProperty("infetados") int infetados){
        this.nome = nome;
        this.utilizadores = utilizadores;
        this.infetados = infetados;
    }
}
