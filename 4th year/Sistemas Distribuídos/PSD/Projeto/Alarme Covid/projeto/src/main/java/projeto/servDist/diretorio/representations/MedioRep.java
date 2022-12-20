package projeto.servDist.diretorio.representations;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MedioRep {
    public double medio;

    @JsonCreator
    public MedioRep(@JsonProperty("medio") double medio){
        this.medio = medio;
    }
}
