package Comum;

public class UsernameExistenteException extends Exception{
    public UsernameExistenteException() {
        super("O username inserido já existe");
    }

    public UsernameExistenteException(String s) {
        super(s);
    }
}
