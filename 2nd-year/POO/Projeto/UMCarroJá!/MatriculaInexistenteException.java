
/**
 * Exceçao para o caso de nenhum exister veiculo para determinada condiçao.
 *
 * @author (Ana Rita Rosendo, Gonçalo Esteves, Rui Oliveira) 
 * @version 21 de maio de 2019
 */
public class MatriculaInexistenteException extends Exception
{
    public MatriculaInexistenteException(){
        super();
    }
    
    public MatriculaInexistenteException(String s){
        super(s);
    }
}
