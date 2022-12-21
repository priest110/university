package projeto.servDist.diretorio.representations;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Top5Rep {
    public List<String> distritos;
    public List<String> localizacoes;

    @JsonCreator
    public Top5Rep(@JsonProperty("distritos") List<String> distritos, @JsonProperty("localizacoes") List<String> localizacoes){
        this.distritos = distritos;
        this.localizacoes = localizacoes;
    }
}
