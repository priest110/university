public class Main {
    public static void main(String args[]){
        double[] filial1_pedidos = new double[]{0.0488, 0.0860, 0.1396, 0.1432, 0.1200, 0.1076, 0.1012, 0.0860, 0.0568, 0.0436, 0.0344,0.0264,0.0064};
        double[] filial1_entregas = new double[]{0.0396, 0.0816, 0.1188, 0.1484 , 0.1388 , 0.1116, 0.0892, 0.0832, 0.0676, 0.0616 , 0.0336, 0.0208, 0.0052};
        double[] filial2_pedidos = new double[]{0.0800, 0.2020, 0.2396, 0.2152, 0.1392, 0.0728, 0.0316, 0.0132, 0.0052, 0.0008, 0.0000, 0.0004, 0.0000};
        double[] filial2_entregas = new double[]{0.0116, 0.0644, 0.1228, 0.1756, 0.1896, 0.1632, 0.1188, 0.0820, 0.0392, 0.0192, 0.0092, 0.0028, 0.0016};

        GIAluguer x = new GIAluguer(filial1_pedidos, filial1_entregas, filial2_pedidos, filial2_entregas);
        //x.print_matriz();
        x.resolver();
    }
}
