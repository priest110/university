package Comum;

public class FileInexistenteException extends Exception{
    public FileInexistenteException() {
        super("Não existe nenhuma música para publicar com o conteúdo inserido!");
    }

    public FileInexistenteException(String s) {
        super(s);
    }
}
