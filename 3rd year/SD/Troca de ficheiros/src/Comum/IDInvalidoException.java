package Comum;

public class IDInvalidoException extends Exception {
    public IDInvalidoException() {
        super("O id inserido não existe.");
    }
    public IDInvalidoException(String s) {
        super(s);
    }
}
