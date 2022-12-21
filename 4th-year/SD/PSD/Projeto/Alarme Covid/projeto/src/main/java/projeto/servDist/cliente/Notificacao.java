package projeto.servDist.cliente;

import java.util.ArrayList;
import projeto.servDist.distrital.Localizacao;

public class Notificacao {
    private ArrayList<String> distritos;
    private Localizacao localizacao;

    public Notificacao(ArrayList<String> distritos, Localizacao l){
        this.distritos = distritos;
        this.localizacao = l;
    }

    public ArrayList<String> getDistritos() {
        return distritos;
    }

    public Localizacao getLocalizacao() {
        return localizacao;
    }
}
