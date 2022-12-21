import java.text.DecimalFormat;
import java.util.Arrays;

public class GIAluguer {
    /* Probabilidade(matriz transição) após um dia de trabalho */
    private final double[][] P; /* Matriz transição */

    /* Ganhos(matriz transição) após um dia de trabalho */
    private final double[][] R_0T; /* Ganho entre estados(0T) */
    private final double[][] R_1faz1; /* Ganho entre estados(1T) da filial 1 que faz 1 transferência */
    private final double[][] R_2faz1; /* Ganho entre estados(1T) da filial 2 que faz 1 transferência */
    private final double[][] R_1faz2; /* Ganho entre estados(1T) da filial 1 que faz 2 transferências */
    private final double[][] R_2faz2; /* Ganho entre estados(1T) da filial 2 que faz 2 transferências */
    private final double[][] R_1faz3; /* Ganho entre estados(1T) da filial 1 que faz 3 transferências */
    private final double[][] R_2faz3; /* Ganho entre estados(1T) da filial 2 que faz 3 transferências */

    /* Esperança de Custos */
    private final double[][] Qn_0T;
    private final double[][] Qn_1faz1;
    private final double[][] Qn_2faz1;
    private final double[][] Qn_1faz2;
    private final double[][] Qn_2faz2;
    private final double[][] Qn_1faz3;
    private final double[][] Qn_2faz3;

    private DecimalFormat df = new DecimalFormat("#.####");
    private DecimalFormat df2 = new DecimalFormat("#.##");
    private final double ERRO = 0.1;


    public GIAluguer(double[] p1_pedidos, double[] p1_entregas, double[] p2_pedidos, double[] p2_entregas) {
        /* Matrizes transição */
        this.P = matrizTransicao(p1_pedidos, p1_entregas, p2_pedidos, p2_entregas);

        /* 0 transferências(R's e Q's) */
        this.R_0T = matrizGanhos(0,1);
        this.Qn_0T = matriz_Q(this.P, R_0T);

        /* 1 transferência(R's e Q's)  */
        this.R_1faz1 = matrizGanhos(1,1);
        this.R_2faz1  = matrizGanhos(1, 2);
        this.Qn_1faz1 = matriz_Q(this.P, this.R_1faz1);
        this.Qn_2faz1  = matriz_Q(this.P, this.R_2faz1);

        /* 2 transferências(R's e Q's)  */
        this.R_1faz2 = matrizGanhos(2,1);
        this.R_2faz2  = matrizGanhos(2, 2);
        this.Qn_1faz2 = matriz_Q(this.P, this.R_1faz2);
        this.Qn_2faz2  = matriz_Q(this.P, this.R_2faz2);

        /* 3 transferências(R's e Q's)  */
        this.R_1faz3 = matrizGanhos(3,1);
        this.R_2faz3  = matrizGanhos(3, 2);
        this.Qn_1faz3 = matriz_Q(this.P, this.R_1faz3);
        this.Qn_2faz3  = matriz_Q(this.P, this.R_2faz3);
    }

    public double[][] matrizTransicao(double[] p1_pedidos, double[] p1_entregas, double[] p2_pedidos, double[] p2_entregas){
        int x, y, i, j;
        double[][] matriz = new double[169][169];
        double p1 = 0, p2 = 0;
        for(int duplosX = 0; duplosX <= 12; duplosX++) { /* Podemos dividir as 169 linhas em 12 conjuntos de linhas (A,B) em que A = 0,..,12 */
            int desdeX = duplosX * 13;
            int ateX   = desdeX + 12;
            for (x = desdeX; x <= ateX; x++) { /* Linha em que nos encontramos, sendo (x-desdeX) o nosso B */
                for (int duplosY = 0; duplosY <= 12; duplosY++) { /* Podemos dividir as 169 colunas em 12 conjuntos de colunas (C,D) em que C = 0,..,12 */
                    int desdeY = duplosY * 13;
                    int ateY   = desdeY + 12;
                    for(y = desdeY; y <= ateY; y++){ /* Coluna em que nos encontramos, sendo (y-desdeY) o nosso D */
                        /* 1º tratamos da parte esquerda de onde parte e onde chega */
                        if(duplosY == 12)
                            for(i = 0; i <= duplosX; i++)
                                for (j = duplosY-duplosX+i; j<=12;j++)
                                    p1 += p1_pedidos[i] *p1_entregas[j];
                        if(duplosX < duplosY && duplosY != 12){
                            j = duplosY - duplosX;
                            for(i = 0; i <= 12; i++){
                                p1 += p1_pedidos[i] * p1_entregas[j];
                                if(j < duplosY) j++;
                            }
                        }
                        if(duplosX >= duplosY && duplosY != 12){
                            j = 0;
                            for(i = duplosX - duplosY; i <= 12; i++){
                                p1 += p1_pedidos[i] * p1_entregas[j];
                                if(j < duplosY) j++;
                            }
                        }
                        /* 2º tratamos da parte direita de onde parte e onde chega */
                        if((y-desdeY) == 12)
                            for(i = 0; i <= (x - desdeX) ; i++)
                                for(j = (y-desdeY)-(x-desdeX)+i; j <= 12; j++)
                                    p2 += p2_pedidos[i] * p2_entregas[j];
                        if((x-desdeX) < (y-desdeY) && (y-desdeY) != 12){
                            j = (y-desdeY) - (x-desdeX);
                            for(i = 0; i <= 12; i++){
                                p2 += p2_pedidos[i] * p2_entregas[j];
                                if(j < (y-desdeY)) j++;
                            }
                        }
                        if((x-desdeX) >= (y-desdeY) && (y-desdeY) != 12){
                            j = 0;
                            for(i = (x-desdeX) - (y-desdeY); i <= 12; i++){
                                p2 += p2_pedidos[i] * p2_entregas[j];
                                if(j < (y-desdeY)) j++;
                            }
                        }
                        /* Depois multiplicamos p1 e p2, uma vez que temos considerar a probabilidade de p1 acontecer e também p2 acontecer */
                        matriz[x][y] = p1 * p2;
                        p1 = 0; p2 = 0;
                    }
                }
            }
        }
        return matriz;
    }

    public double[][] matrizGanhos(int transferencias, int faz){
        int x, y;
        double[][] matriz = new double[169][169];
        double p1 = 0, p2 = 0;
        if(transferencias == 0){
            for(int duplosX = 0; duplosX <= 12; duplosX++) { /* Podemos dividir as 169 linhas em 12 conjuntos de linhas (A,B) em que A = 0,..,12 */
                int desdeX = duplosX * 13;
                int ateX   = desdeX + 12;
                for (x = desdeX; x <= ateX; x++) { /* Linha em que nos encontramos, sendo (x-desdeX) o nosso B */
                    for (int duplosY = 0; duplosY <= 12; duplosY++) { /* Podemos dividir as 169 colunas em 12 conjuntos de colunas (C,D) em que C = 0,..,12 */
                        int desdeY = duplosY * 13;
                        int ateY   = desdeY + 12;
                        for(y = desdeY; y <= ateY; y++) { /* Coluna em que nos encontramos, sendo (y-desdeY) o nosso D */
                            if(duplosY > 8)
                                p1 = 10;
                            if((y-desdeY) > 8)
                                p2 = 10;
                            matriz[x][y] = p1 + p2;
                            p1 = 0; p2 = 0;
                        }
                    }
                }
            }
        }
        else{
            if(faz == 1) {
                for (int duplosX = 0; duplosX <= 12; duplosX++) { /* Podemos dividir as 169 linhas em 12 conjuntos de linhas (A,B) em que A = 0,..,12 */
                    int desdeX = duplosX * 13;
                    int ateX = desdeX + 12;
                    for (x = desdeX; x <= ateX; x++) { /* Linha em que nos encontramos, sendo (x-desdeX) o nosso B */
                        for (int duplosY = 0; duplosY <= 12; duplosY++) { /* Podemos dividir as 169 colunas em 12 conjuntos de colunas (C,D) em que C = 0,..,12 */
                            int desdeY = duplosY * 13;
                            int ateY = desdeY + 12;
                            for (y = desdeY; y <= ateY; y++) { /* Coluna em que nos encontramos, sendo (y-desdeY) o nosso D */
                                p1 = 0;
                                p2 = 0;
                                if(duplosY == 0 || (duplosY == 1 && (transferencias == 2 || transferencias == 3)) || (duplosY == 2 && transferencias == 3)) /* Transferência não acontece */
                                    p1 = 0;
                                else{                                                                                                                       /* Transferência pode acontecer */
                                    if((y-desdeY) > 12-transferencias) {                                                                                    /* Transferência não acontece( a outra filial não tem capacidade para receber) */
                                        if (duplosY > 8)
                                            p1 = 10;
                                        else
                                            p1 = 0;
                                    }
                                    else{                                                                                                                   /* Transferência acontece */
                                        if(duplosY > 8 + transferencias)
                                            p1 = 10 + 7*transferencias;
                                        else
                                            p1 = 7 * transferencias;
                                    }
                                }
                                if ((p1 != 0 && p1 != 10)) { /* Verificamos se a transferência vai acontecer e, caso aconteça, vemos as consequências dos ganhos na filial que as recebe */
                                    if ((y - desdeY) > 8 - transferencias)
                                        p2 = 10;
                                }
                                else {
                                    if ((y - desdeY) > 8)
                                        p2 = 10;
                                }
                                matriz[x][y] = p1 + p2;
                            }
                        }
                    }
                }
            }
            else{
                for (int duplosX = 0; duplosX <= 12; duplosX++) { /* Podemos dividir as 169 linhas em 12 conjuntos de linhas (A,B) em que A = 0,..,12 */
                    int desdeX = duplosX * 13;
                    int ateX = desdeX + 12;
                    for (x = desdeX; x <= ateX; x++) { /* Linha em que nos encontramos, sendo (x-desdeX) o nosso B */
                        for (int duplosY = 0; duplosY <= 12; duplosY++) { /* Podemos dividir as 169 colunas em 12 conjuntos de colunas (C,D) em que C = 0,..,12 */
                            int desdeY = duplosY * 13;
                            int ateY = desdeY + 12;
                            for (y = desdeY; y <= ateY; y++) { /* Coluna em que nos encontramos, sendo (y-desdeY) o nosso D */
                                p1 = 0;
                                p2 = 0;
                                if((y-desdeY) == 0 || ((y-desdeY) == 1 && (transferencias == 2 || transferencias == 3)) || ((y-desdeY) == 2 && transferencias == 3))
                                    p2 = 0;
                                else{                                                                                                                       /* Transferência pode acontecer */
                                    if(duplosY > 12-transferencias) {                                                                                       /* Transferência não acontece( a outra filial não tem capacidade para receber) */
                                        if ((y-desdeY) > 8)
                                            p2 = 10;
                                        else
                                            p2 = 0;
                                    }
                                    else{                                                                                                                   /* Transferência acontece */
                                        if((y-desdeY) > 8 + transferencias)
                                            p2 = 10 + 7*transferencias;
                                        else
                                            p2 = 7 * transferencias;
                                    }
                                }
                                if ((p2 != 0 && p2 != 10)) {
                                    if (duplosY > 8 - transferencias)
                                        p1 = 10;
                                }
                                else {
                                    if (duplosY > 8)
                                        p1 = 10;
                                }
                                matriz[x][y] = p1 + p2;
                            }
                        }
                    }
                }
            }
        }

        return  matriz;
    }

    public double[][] matriz_Q(double[][] P, double [][] R){
        int x, y;
        double[][] matriz = new double[169][1];
        for(x = 0; x <= 168; x++)
            for(y = 0; y <= 168; y++)
                matriz[x][0] += P[x][y] * R[x][y];
        return matriz;
    }

    public double[][] soma_matrizes(double[][] Q1, double[][] Q2){
        int x;
        double[][] matriz = new double[169][1];
        for(x = 0; x <= 168; x++)
            matriz[x][0] = Q1[x][0]+ Q2[x][0];
        return matriz;
    }

    public double[][] multiplica_matrizes(double[][] P, double[][] F){
        int x;
        double[][] matriz = new double[169][1];
        for(x = 0; x <= 168; x++)
            for(int i = 0; i <= 168; i++)
                matriz[x][0] += P[x][i] * F[i][0];
        return matriz;
    }

    public void resolver(){
        double[][] Fn = new double[169][1];
        double[][] Fn_anterior = new double[169][1];
        double[][] Vn_0T;
        double[][] Vn_1T_from1;
        double[][] Vn_1T_from2;
        double[][] Vn_2T_from1;
        double[][] Vn_2T_from2;
        double[][] Vn_3T_from1;
        double[][] Vn_3T_from2;
        double[][] Dn = new double[169][1];
        String[] decisoes = new String[169];

        int i = 0;
        do{
            for(int j = 0; j <= 168; j++)
                Fn_anterior[j][0] = Fn[j][0];

            Vn_0T = soma_matrizes(this.Qn_0T, multiplica_matrizes(this.P, Fn_anterior));
            Vn_1T_from1 = soma_matrizes(this.Qn_1faz1, multiplica_matrizes(this.P, Fn_anterior));
            Vn_1T_from2 = soma_matrizes(this.Qn_2faz1, multiplica_matrizes(this.P, Fn_anterior));
            Vn_2T_from1 = soma_matrizes(this.Qn_1faz2, multiplica_matrizes(this.P, Fn_anterior));
            Vn_2T_from2 = soma_matrizes(this.Qn_2faz2, multiplica_matrizes(this.P, Fn_anterior));
            Vn_3T_from1 = soma_matrizes(this.Qn_1faz3, multiplica_matrizes(this.P, Fn_anterior));
            Vn_3T_from2 = soma_matrizes(this.Qn_2faz3, multiplica_matrizes(this.P, Fn_anterior));

            for(int j = 0; j <= 168; j++) {
                Fn[j][0] = minimo(Vn_0T[j][0], Vn_1T_from1[j][0], Vn_1T_from2[j][0], Vn_2T_from1[j][0], Vn_2T_from2[j][0], Vn_3T_from1[j][0], Vn_3T_from2[j][0]);
                decisoes[j] = index(Vn_0T[j][0], Vn_1T_from1[j][0], Vn_1T_from2[j][0], Vn_2T_from1[j][0], Vn_2T_from2[j][0], Vn_3T_from1[j][0], Vn_3T_from2[j][0]);
                Dn[j][0] = Fn[j][0] - Fn_anterior[j][0];
            }

            System.out.println("*** ITERAÇÃO nº"+ i + " ****");
            System.out.println("* Fn *");
            for(int j = 0; j <= 168;j++){
                System.out.println("[" + df.format(Fn[j][0]) + "]");
            }
            System.out.println("* Dn *");
            for(int j = 0; j <= 168;j++){
                System.out.println("[" + df.format(Dn[j][0]) + "]");
            }
            int contador = 0;
            for(int f1 = 0; f1 <= 12; f1++) {
                for(int f2 = 0; f2 <= 12; f2++){
                    System.out.println("Para o estado (" + f1 + ", " + f2 + "): " + decisoes[contador]);
                    contador++;
                }
            }

            i++;
        } while(verifica(Dn));
    }

    public boolean verifica(double[][] Dn){
        for(int i=1; i <= 168;i++){
            if(Math.abs(Dn[i][0] - Dn[i-1][0]) > ERRO)
                return  true;
        }
        return false;
    }

    public double minimo(double a, double b, double c, double d, double e, double f, double g){
        return Math.min(a, Math.min(b, Math.min(c, Math.min(d, Math.min(e, Math.min(f, g))))));
    }

    public String index(double a, double b, double c, double d, double e, double f, double g){
        double m = minimo(a,b,c,d,e,f,g);
        if(a == m)
            return "0 transferências";
        else if(b == m)
            return "1 transferência da filial 1 para a 2";
        else if(c == m)
            return "1 transferência da filial 2 para a 1";
        else if(d == m)
            return "2 transferências da filial 1 para a 2";
        else if(e == m)
            return "2 transferências da filial 2 para a 1";
        else if(f == m)
            return "3 transferências da filial 1 para a 2";
        else if(g == m)
            return "3 transferências da filial 2 para a 1";
        return "erro";
    }

    public void print_matriz(){
        int i, j;
        double contador = 0;
        System.out.println("Matriz de transição(P):");
        for(i = 0; i <= 168; i++) {
            for (j = 0; j <= 168; j++) {
                contador += this.R_1faz1[i][j];
                System.out.print(df.format(this.R_1faz1[i][j]) + " ");
            }
            System.out.println();
           // System.out.println("Soma da linha:" + df2.format(contador));
            contador = 0;
        }
        System.out.println();
    }

}
