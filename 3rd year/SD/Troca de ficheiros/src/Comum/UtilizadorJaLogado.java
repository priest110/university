package Comum;

public class UtilizadorJaLogado extends Exception {
    public UtilizadorJaLogado() {
        super("O utilizador inserido já está loggado.");
    }

    public UtilizadorJaLogado(String s) {
        super(s);
    }
}
