import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.InputMismatchException;

/**
 * Menu - implementa um menu de texto
 * 
 * @author (Ana Rita Rosendo, Gonçalo Esteves, Rui Oliveira) 
 * @version 21 de maio de 2019
 */
public class Menu {
    // variáveis de instância
    private List<String> opcoes;
    
    /**
     * Construtor para objetos da classe Menu.
     */
    public Menu(String[] opcoes) {
        this.opcoes = Arrays.asList(opcoes);
    }
    
    /** 
     * Metodo que apresenta o menu.
     */
    private void mostraMenu() {
        System.out.println("\n ** UMCARROJA ");
        for (int i=0; i < this.opcoes.size(); i++) {
            System.out.print(i+1);
            System.out.print(" - ");
            System.out.println(this.opcoes.get(i));
        }
        System.out.println("0 - Sair");
    }
    
    /**
     * Metodo que le uma opçao do menu.
     * 
     * @return opcao lida
     */
    public int leMenu() {
        int opcao; 
        Scanner in = new Scanner(System.in);
        
        System.out.print("Opção: ");
        try {
            opcao = in.nextInt();
        }
        catch (InputMismatchException e) { // Não foi inscrito um inteiro
            opcao = -1;
        }
        if (opcao < 0 || opcao > this.opcoes.size()) {
            System.out.println("Opção Inválida!");
            opcao = -1;
        }
        return opcao;
    }
    
    /**
     * Método para apresentar o menu e ler uma opção.
     * 
     * @return opcao escolhida
     */
    public int executa() {
        mostraMenu();
        int opcao = leMenu();
        if(opcao == -1)
            executa();
        return opcao;
    }

}
