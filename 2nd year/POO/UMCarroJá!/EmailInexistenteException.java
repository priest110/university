
/**
 * Exceçao para o caso do email inserido nao existir.
 *
 * @author (Ana Rita Rosendo, Gonçalo Esteves, Rui Oliveira) 
 * @version 21 de maio de 2019
 */
public class EmailInexistenteException extends Exception
{
    public EmailInexistenteException(){
        super();
    }
    
    public EmailInexistenteException(String s){
        super(s);
    }
}