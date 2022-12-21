
/**
 * Exceçao para o caso do email inserido nao ser valido.
 *
 * @author (Ana Rita Rosendo, Gonçalo Esteves, Rui Oliveira) 
 * @version 21 de maio de 2019
 */
public class EmailInvalidoException extends Exception
{
    public EmailInvalidoException(){
        super();
    }
    
    public EmailInvalidoException(String s){
        super(s);
    }
}