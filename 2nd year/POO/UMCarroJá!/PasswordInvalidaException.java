
/**
 * Exceçao para o caso da password inserido nao ser valida.
 *
 * @author (Ana Rita Rosendo, Gonçalo Esteves, Rui Oliveira) 
 * @version 21 de maio de 2019
 */
public class PasswordInvalidaException extends Exception
{
    public PasswordInvalidaException(){
        super();
    }
    
    public PasswordInvalidaException(String s){
        super(s);
    }
}
