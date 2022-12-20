package projeto.servDist.diretorio;

import projeto.servDist.distrital.Localizacao;

import java.util.*;

public class Diretorio {
    private Map<String, Double> utilizadores;                       /* Distrito, Nº de utilizadores */
    private Map<String, Double> infetados;                          /* Distrito, Nº de infetados */
    private Map<String, Double> possiveis_infetados;                /* Distrito, Nº de possíveis infetados */
    private Map<String, Map<Integer, Localizacao>> max_users_local; /* Distrito -> Localização, Nº de utilizadores */
    private List<String> distritos;                                 /* Lista de distritos */

    public Diretorio() {
        this.utilizadores = new HashMap<>();
        this.infetados = new HashMap<>();
        this.possiveis_infetados = new HashMap<>();
        this.max_users_local = new HashMap<>();
        this.distritos = new ArrayList<>(Arrays.asList("Aveiro", "Beja", "Braga", "Braganca", "Castelo_Branco", "Coimbra", "Evora", "Faro", "Guarda", "Leiria", "Lisboa", "Portalegre", "Porto", "Santarem", "Setubal", "Viana_do_Castelo", "Vila_Real", "Viseu"));
        List<Localizacao> localizacoes = new ArrayList<>();
        for(int i = 0; i < 5; i++){
            for (int j = 0; j < 5; j++){
                localizacoes.add(new Localizacao(i,j));
            }
        }
        int id = 0;
        int id_criado; /* id da próxima localização que for adicionada ao max_users_local */
        for (String distrito : this.distritos) {
            this.utilizadores.put(distrito, 0.0);
            this.infetados.put(distrito, 0.0);
            this.possiveis_infetados.put(distrito, 0.0);

            Map<Integer, Localizacao> aux = new HashMap<>();
            for(Localizacao l : localizacoes){
                id_criado = id++;
                aux.put(id_criado,l);
            } id = 0;
            this.max_users_local.put(distrito, aux);
        }
    }

    /**
     * Incrementa lista de utilizadores de um dado distrito no diretório
     * Veririfica se o máximo de pessoas numa dada localização de um dado distrito mudou, e, caso sim, adiciona ao map respetivo do diretório
     * @param query do pedido HTTP
     */
    public void putUtilizadores(String query){
        String[] infos = query.split("\\s+");
        String distrito = infos[0];
        int coordX = Integer.parseInt(infos[1]), coordY = Integer.parseInt(infos[2]), num = Integer.parseInt(infos[3]);
        this.utilizadores.put(infos[0], this.utilizadores.get(distrito) + 1.0);

        Map<Integer,Localizacao> aux = this.max_users_local.get(distrito);
        int max, i = 0;
        for(Localizacao l : aux.values()){
            if(l.getX() == coordX && l.getY() == coordY){
                max = l.getUtilizadores();
                if(num > max) {
                    max = num;
                    l.setUtilizadores(max);
                    aux.put(i, l);
                    this.max_users_local.put(distrito, aux);
                }
                else break;
            }
            i++;
        }
    }

    /**
     * Incrementa lista de infetados de um dado distrito no diretório
     * @param distrito do pedido HTTP
     */
    public void putInfetados(String distrito){
        this.infetados.put(distrito, this.infetados.get(distrito) + 1.0);
    }

    /**
     * Incrementa lista de possíveis infetados de um dado distrito no diretório
     * @param distrito do pedido HTTP
     */
    public void putPossiveisInfetados(String distrito){
        this.possiveis_infetados.put(distrito, this.possiveis_infetados.get(distrito) + 1.0);
    }

    /**
     * Devolve o top5 dos distritos com maior rácio de infetados/utilizadores
     * @return lista dos 5 distritos
     */
    public List<String> top5_racio(){
        double racio, user, inf;
        Map<String,Double> map = new HashMap<>();
        for(String distrito : this.distritos){
            user = this.getUtilizadores().get(distrito);
            inf = this.getInfetados().get(distrito);
            if(user != 0)
                racio = inf/user;
            else racio = 0;
            map.put(distrito,racio);
        }
        map = sortByValue(false, map);
        List<String> keys = new ArrayList<>(map.keySet());
        List<String> top5 = new ArrayList<>();
        for(int i = 0; i < 5; i++){
            top5.add(keys.get(i));
        }
        return top5;
    }

    /**
     * Devolve o top 5 das localizações que tiveram o maior número de pessoas em simultâneo
     * @return lista das 5 localizações
     */
    public List<String> top5_locais(){
        Map<Integer,Localizacao> map = new HashMap<>();
        int id = 0, id_aux = 0;
        for(Map<Integer,Localizacao> m : this.max_users_local.values()){
            map.put(id, m.get(id_aux));
            id++;
            id_aux++;
        }
        map = sortByValue_v2(false,map);
        List<String> top5 = new ArrayList<>();
        for(Integer key : map.keySet()){
            top5.add("(" + map.get(key).getX() + "," + map.get(key).getY() + ")");
            if(top5.size() == 5) break;
        }
        return top5;
    }

    /**
     * Devolve o numéro médio de possíveis infetados
     * @return número médio
     */
    public double possiveis_infetados(){
        double sum = 0, aux;
        for(String distrito : this.distritos){
            aux = this.possiveis_infetados.get(distrito);
            sum += aux;
        }
        return sum/this.distritos.size();
    }

    /**
     * Algoritmo que faz sort de um map com base no nº de users de uma localização
     * @param order descendente ou ascendente
     * @param map a fazer sort
     * @return map ordenado
     */
    public Map<Integer, Localizacao> sortByValue_v2(boolean order, Map<Integer, Localizacao> map){
        List<Map.Entry<Integer,Localizacao>> list = new LinkedList<Map.Entry<Integer,Localizacao>>(map.entrySet()); /* converte HashMap em List */
        Collections.sort(list, new Comparator<Map.Entry<Integer,Localizacao>>() {      /* sorting dos elems da List */
            public int compare(Map.Entry<Integer, Localizacao> o1, Map.Entry<Integer, Localizacao> o2) {
                if (order)
                    return Integer.compare(o1.getValue().getUtilizadores(), o2.getValue().getUtilizadores());
                else
                    return Integer.compare(o2.getValue().getUtilizadores(),o1.getValue().getUtilizadores());
            }
        });
        Map<Integer,Localizacao> sortedMap = new LinkedHashMap<Integer, Localizacao>();
        for (Map.Entry<Integer, Localizacao> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }

    /**
     * Algoritmo que faz sort de um map com base no rácio de infetados/utilizadores
     * @param order descendente ou ascendente
     * @param map a fazer sort
     * @return map ordenado
     */
    public Map<String,Double> sortByValue(boolean order, Map<String,Double> map) {
        List<Map.Entry<String, Double>> list = new LinkedList<Map.Entry<String, Double>>(map.entrySet()); /* converte HashMap em List */
        Collections.sort(list, new Comparator<Map.Entry<String, Double>>() {      /* sorting dos elems da List */
            public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2) {
                if (order)
                    return o1.getValue().compareTo(o2.getValue());
                else
                    return o2.getValue().compareTo(o1.getValue());
            }
        });
        Map<String, Double> sortedMap = new LinkedHashMap<String, Double>();
        for (Map.Entry<String, Double> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }

    public Map<String, Double> getUtilizadores() {
        return utilizadores;
    }

    public Map<String, Double> getInfetados() {
        return infetados;
    }
}

