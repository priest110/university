package Comum;

public class EtiquetaInvalidaException extends Exception{
    public EtiquetaInvalidaException() {
        super("Não existe nenhuma música com a etiqueta inserida.");
    }

    public EtiquetaInvalidaException(String s) {
        super(s);
    }
}
