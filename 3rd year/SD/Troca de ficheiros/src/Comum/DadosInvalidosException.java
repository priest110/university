package Comum;

public class DadosInvalidosException extends Exception{
    public DadosInvalidosException() {
        super("Os dados inseridos são inválidos!");
    }

    public DadosInvalidosException(String s) {
        super(s);
    }
}
