package projeto.servDist.distrital;


public class Localizacao {
    private int x;
    private int y;
    private int utilizadores;

    public Localizacao(int x, int y){
        this.x = x;
        this.y = y;
        this.utilizadores = 0;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getUtilizadores() {
        return utilizadores;
    }

    public void setUtilizadores(int utilizadores) {
        this.utilizadores = utilizadores;
    }

    @Override
    public String toString() {
        return "Localizacao{" +
                "x=" + x +
                ", y=" + y +
                ", n=" + utilizadores +
                '}';
    }
}
