
/**
 * Exceçao para o caso do nome inserido nao ser valido.
 *
 * @author (Ana Rita Rosendo, Gonçalo Esteves, Rui Oliveira) 
 * @version 21 de maio de 2019
 */
    public class NomeInvalidoException extends Exception{
        public NomeInvalidoException(){
            super();
        }
    
        public NomeInvalidoException(String s){
            super(s);
        }
    }
