package projeto.servDist.distrital;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Historico {
    private Integer id;                                /* id da próxima lista que for adicionada */
    private Map<Integer, ArrayList<String>> map_users; /* map com os users que estiveram ao mesmo numa determinada localização */

    public Historico(){
        this.id = 0;
        this.map_users = new HashMap<>();
    }

    /**
     * Determinado utilizador(infetado(1) ou não(0)) verifica com quem contactou
     * @param user  infetado
     * @return  Lista de possíveis infetados
     */
    public ArrayList<String> contactou_com(String user, int infetado) {
        ArrayList<String> possiveis_infetados = new ArrayList<>();
        ArrayList<String> aux;
        System.out.println(this.map_users.toString());
        for (Integer i : this.map_users.keySet()) {
            aux = this.map_users.get(i);
            if (aux.contains(user)) {
                for (String nome : aux)
                    if (!possiveis_infetados.contains(nome) && !nome.equals(user))
                        possiveis_infetados.add(nome);
            }
        }
        if(infetado == 1)
            remove_user(user); /* É como se fizesse logout, já não pode contactar o frontend */
        return possiveis_infetados;
    }

    /**
     * Devolve o nº total de pessoas que estão na locaização à qual pertence o histórico
     * O raciocínio é o seguinte: Vai à lista do histórico e devolve o tamanho da última lista de utilizadores do histórico(a atual)
     * @return  total de pessoas
     */
    public int total_pessoas(){
        if(this.map_users != null && !this.map_users.isEmpty())
            return  this.map_users.get(this.map_users.size()-1).size();
        else return 0;
    }

    /**
     * Adiciona um utilizador ao histórico.
     *  - à última lista caso haja histórico
     *  - cria uma nova lista com o utilizador e adiciona ao histórico
     * @param user  a adicionar
     */
    public void adiciona_user(String user){
        ArrayList<String> lista;
        if(!this.map_users.isEmpty()){
            lista = this.map_users.get(this.map_users.size()-1);
            lista.add(user);
            this.map_users.put(id, lista);
        }
        else{
            lista = new ArrayList<>();
            lista.add(user);
            int id_criado = this.id++;
            this.map_users.put(id_criado, lista);
        }
    }

    /**
     * Remove o utilizador da última lista do histórico e adiciona esta mesma lista(agora atualizada) ao histórico
     * @param user  a remover
     */
    public void remove_user(String user){
        ArrayList<String> lista = this.map_users.get(this.map_users.size()-1);
        lista.remove(user);
        if(!lista.isEmpty()) {
            int id_criado = this.id++;
            this.map_users.put(id_criado, lista);
        }
    }

    /**
     * Devolve o maior número de pessoas em simultâneo de uma dada localização
     * @return maior
     */
    public int mais_pessoas(){
        int max = 0;
        for(ArrayList<String> al : this.map_users.values()){
            if(al.size() > max)
                max = al.size();
        }
        return max;
    }

    /**
     * Converte um histórico numa string
     * @return string
     */
    public String toString() {
        StringBuilder mapAsString = new StringBuilder("{");
        for(Integer i : this.map_users.keySet()){
            mapAsString.append(i).append("=").append(this.map_users.get(i)).append(", ");
        } //mapAsString.delete(mapAsString.length()-2,mapAsString.length()).append("}");
        return  mapAsString.toString();
    }
}
