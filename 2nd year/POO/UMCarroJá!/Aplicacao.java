import java.io.IOException;
import java.util.Scanner;
import java.util.Date;
import java.time.LocalDate;
import java.util.InputMismatchException;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.io.File;
import java.util.List;
import java.util.ArrayList;
import java.lang.Math;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.time.ZoneId;

public class Aplicacao {

    // Menus da aplicação
    private Menu menuInicial;
    private Menu menuRegisto;
    private Menu menuCliente;
    private Menu menuProprietario;
    private Menu menuViagem;
    private Menu menuCarro;
    private Menu menuPosViagem;
    
    private Dados d = new Dados();
    
    
    /**
     * Construtor.
     * 
     * Cria os menus e a camada de negócio.
     */
    
    private Aplicacao() {
        String[] opcoes_Inicial = {"Login", "Registo", "Guardar Estado", "Carregar logs"};
        String[] opcoes_Registo = {"Registar como cliente", "Registar como proprietario"};
        String[] opcoes_Cliente = {"Ver perfil", "Efetuar Viagem", 
                                  "Todos os alugueres efetuados"};
        String[] opcoes_Proprietario = {"Ver Perfil", "Novo veiculo", "Abastecer veiculo",
                                       "Alterar o preço por km",
                                       "Lista dos 10 clientes que mais utilizam o sistema(em numero de vezes)",
                                       "Lista dos 10 clientes que mais utilizam o sistema(em kms)",
                                       "Alterar Disponibilidade de um veiculo",
                                       "Todos os alugueres efetuados",
                                       "Total faturado por um veiculo"};
        String[] opcoes_Viagem = {"Solicitar veiculo mais próximo","Solicitar veiculo mais barato",
                 "Solicitar carro mais barato dentro de uma determinada distância a percorrer a pé",
            "Solicitar um veiculo específico", "Solicitar veiculo com determinada autonomia"};
        String[] opcoes_Carro = {"Carro a gasolina", "Carro hibrido", "Carro eletrico", "Carro a gasoleo"};
                                         
        this.menuInicial = new Menu(opcoes_Inicial);
        this.menuRegisto = new Menu(opcoes_Registo);
        this.menuCliente = new Menu(opcoes_Cliente);
        this.menuProprietario = new Menu(opcoes_Proprietario);
        this.menuViagem = new Menu(opcoes_Viagem);
        this.menuCarro = new Menu(opcoes_Carro);
        
    }
    
    
    /**
     * Carrega os dados
     */
    
    private void carrega() {
        try {
            this.d = Dados.abrirFicheiro("backup.txt");
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
            System.out.println("Dados nao lidos!\nFicheiro nao encontrado.");
            d = new Dados();
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Dados nao lidos!\nFicheiro com formato desconhecido.");
            d = new Dados();
        }
        catch (IOException e) {
            e.printStackTrace();
            System.out.println("Dados nao lidos!\nErro de leitura.");
            d = new Dados();
        }
        catch (ClassCastException e) {
            e.printStackTrace();
            System.out.println("Dados nao lidos!\nErro de formato.");
            d = new Dados();
        }
    }
    
    /**
     * Guarda os dados
     */
    
    private void guarda() {
        try {
            this.d.guardaFicheiro("backup.txt");
        }                  
        catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Dados nao guardados!\nFicheiro nao encontrado.");
            d = new Dados();
        }
        catch (IOException e) {
            e.printStackTrace();
            System.out.println("Dados nao guardados!\nErro de escrita.");
            d = new Dados();
        }
    }
    
    /*
                ******* MENUS *******
     */   
    
    /**
     * Executa o menu principal e invoca o método correspondente à opção seleccionada.
     */    
    private void menu_Inicial() {
        carrega();
        int r = 1;
        int log = 0;
        while(r == 1){
            int op = menuInicial.executa();
            switch(op){
                case 1: login();
                            break;
                case 2: menu_Registo();
                            break;
                case 3: guarda();
                            break;
                case 4: try {
                            this.d.carregaLogs("logsPOO_carregamentoInicial.bak");
                        }                  
                        catch (FileNotFoundException e) {
                            e.printStackTrace();
                            System.out.println("Dados nao guardados!\nFicheiro nao encontrado.");
                        }
                        catch (IOException e) {
                            e.printStackTrace();
                            System.out.println("Dados nao guardados!\nErro de escrita.");
                        }
                            break;
                case 0: System.out.println("See ya");
                        r = 0;
                            break;
            }
        }
        guarda();
    }  
    
    /**
     * Metodo responsavel por distinguir o registo que seguidamente sera feito(Cliente ou Proprietario)
     */
     private void menu_Registo() {
        int op = menuRegisto.executa();
        switch(op){
            case 1: registoCorP(1);
                        break;
            case 2: registoCorP(2);
                        break;
            case 0:     break;
        }
        if(op == -1)
            menu_Registo();    
    }
    
    /**
     * Main cria a aplicação e invoca o método exec
     */
    public static void main(String[] args) {
       new Aplicacao().menu_Inicial();
    }
    
    /**
     * Metodo que disponibiliza um menu de opçoes ao Cliente apos login
     * 
     * @param  c o Cliente que acabou de dar login
     */
    private void menu_Cliente (Cliente c) {
        int r = 1;
        if(c.verificaAluguerPendente()){
            Aluguer a = c.aluguerPendente();
            System.out.println("O teu pedido de aluguer foi aceite. \n");
            Veiculo v = d.matriculaToVeiculo(a.getMatricula());
            efetuarViagem(c, v, a.getLocalX(), a.getLocalY());
        }
        
        if(c.verificaAluguerRejeitado()){
            System.out.println("O teu pedido de aluguer nao foi aceite. \n");
            Aluguer aux2 = c.aluguerPendente3();
            d.removeAluguer(aux2.getEmailCli(), aux2.getEmailPro(), aux2.getMatricula(), aux2);
                
        }
        while(r == 1){
            int op = menuCliente.executa();
            switch(op){
                case 0: r = 0;
                            break;
                case 1: perfilCli(c);
                            break;
                case 2: menu_Carro(null, c);
                        break;
                case 3: todos_AlugueresCli(c);
                        break;
            }
            c = d.mailToCliente(c.getEmail());
        }
    }         
    
    /**
     * Metodo que disponibiliza um menu de opçoes ao Proprietario apos login
     * 
     * @param  p o Proprietario que acabou de dar login
     */
    private void menu_Proprietario (Proprietario p) {
        int r = 1;
        Scanner in = new Scanner(System.in);
        
        p.verificaAutonomia();
        
        if(p.verificaAluguerPendente()){
            Aluguer a = p.aluguerPendente();
            System.out.println("Tem um pedido de aluguer pendente do cliente " + a.getEmailCli() +". Quer aceitar?\nSim(1)\nNao(0)\n");
            int opcao = in.nextInt();
            if(opcao == 1){
                d.removeAluguer(a.getEmailCli(), a.getEmailPro(), a.getMatricula(), a);
                a.setPendente(2);
                d.alteraAntesViagem(a.getEmailCli(), a.getEmailPro(), a.getMatricula(), a);
            }
            else if(opcao == 0){
                d.removeAluguer(a.getEmailCli(), a.getEmailPro(), a.getMatricula(), a);
                a.setPendente(3);
                d.alteraAntesViagem(a.getEmailCli(), a.getEmailPro(), a.getMatricula(), a);
            }
            else{
                System.out.println("Opçao invalida!\n");
                menu_Proprietario(p);
            }
        }
        while(r == 1){
            int op = menuProprietario.executa();
            switch(op){
                case 0: r = 0;     
                            break;
                case 1: perfilProp(p);
                            break;
                case 2: menu_Carro(p, null);
                            break;
                case 3: abastecer(p);
                            break;
                case 4: alterarPreco(p);
                            break;
                case 5: top10(1);
                        break;
                case 6: top10(2);
                        break;
                case 7: alterarDisponibilidade(p);
                        break;
                case 8: todos_AlugueresPro(p);
                        break;
                case 9: faturaViaturas(p);
                        break;
            }
            p = d.mailToProprietario(p.getEmail());
        } 
    }    
    
    /**
     * Metodo que disponibiliza um menu de opcoes de viagem para o Cliente
     * 
     * @param  c o Cliente que efetuara a viagem
     */
    private void menu_Viagem(Cliente c, List<Veiculo> lista){
        int r = 1;
        while(r == 1){
            int op = menuViagem.executa();
            if(op == 0)
                r = 0;
            else{    
                System.out.println("Preencha as coordenadas correspondentes à sua localização.");
                double x = validaCoord("x");
                double y = validaCoord("y");
                System.out.println("Preencha as coordenadas correspondentes ao destino que pretende.");
                double xDestino = validaCoord("x");
                double yDestino = validaCoord("y");
        
                tipoAluguer(c, lista, op, x, y, xDestino, yDestino);
                r = 0;
            }
        }
    }
    
    /**
     * Metodo que disponibiliza o menu dos tipos de carros, de forma a escolher um, para iniciar a inserçao de um novo veiculo
     * 
     * @param  p Proprietario que se encontra no sistema
     */
    private void menu_Carro(Proprietario p, Cliente c){
        Scanner input = new Scanner(System.in);
        int r = 1;
        while(r == 1){
            int op = menuCarro.executa();
            if(c == null){
                switch(op){
                    case 0: r = 0;     
                            break;
                    case 1: tipoCarro(p, 1);
                            break;
                    case 2: tipoCarro(p, 2);
                            break;
                    case 3: tipoCarro(p, 3);
                            break;
                    case 4: tipoCarro(p, 4);
                            break;
               }
            }
            else{
                try{
                    switch(op){
                        case 0: r = 0;     
                                break;
                        case 1: menu_Viagem(c, d.tipoCarroGasolina());
                                break;
                        case 2: menu_Viagem(c, d.tipoCarroHibrido());
                                break;
                        case 3: menu_Viagem(c, d.tipoCarroEletrico());
                                break;
                        case 4: menu_Viagem(c, d.tipoCarroGasoleo());
                                break;
                    }
                }
                catch(VeiculoInexistenteException e){
                    System.out.println("Nao se encontra nenhum veiculo desse tipo disponivel.\nTente mais tarde.");
                    menu_Carro(null, c);
                }
            }
            r = 0;
        }     
    }
    
    /*
                *******METODOS DE REGISTO*******
     */ 
        
    /**
     * Registra um novo Cliente ou Proprietario
     * 
     * @param  opcao Cliente(1) ou Proprietario(2)
     */
    private void registoCorP (int opcao) {
        Scanner in = new Scanner (System.in);
        boolean validade = false;
        String nome = null;
        String email = null;
        String passx2 = null;
        String password = null;
        
        System.out.println("\n*** REGISTO");
        
         while (!validade) {
            System.out.print("Email: ");
            email = in.nextLine();
            try{
                validade = d.emailValido(email);
            } catch(EmailInvalidoException e){
                System.out.println("Email invalido. Introduza um email diferente.");
                validade = false;
            }
         }
        
        validade = false;
        
        while (!validade) {
            System.out.print("Nome: ");
            nome = in.nextLine();
            try{
                validade = d.nomeValido(nome);
            } catch(NomeInvalidoException e){
                System.out.println("Nome invalido. Tente novamente.");
                validade = false;
            }
        }        

        validade = false;
        
        while (!validade) {
            System.out.print("Password: ");
            password = in.nextLine();
            System.out.print("Confirmação da Password: ");
            passx2 = in.nextLine();
            
            try {
                validade = d.passwordValida(password, passx2);
            }
            catch (PasswordInvalidaException e) {
                System.out.println("Erro na password. Insira novamente.");
                validade = false;
            }
        }

        System.out.print("Morada: ");
        String morada = in.nextLine();
                
        String data = null;
        LocalDate date = null;
        while(date == null){
            System.out.println("Data de Nascimento\nExemplo: 05/06/1998");
            data = in.nextLine();
            date = convertToDate(data);
        }
        
        System.out.println("NIF: "); 
        String nif = in.nextLine();     
        
        if (opcao == 1){
            registoCliente(email, nome, password, morada, date, Integer.parseInt(nif)); 
        }
        else
            registoProprietario(email, nome, password, morada, date, Integer.parseInt(nif));
        
        in.close();  
     }    
    
    /**
     * Metodo que regista um cliente na aplicaçao
     * 
     * @param  email o email a registar
     * @param  nome o nome a registar
     * @param  oassword a pass a registar
     * @param  morada a morada a registar
     * @param  data a data a registar
     */
    private void registoCliente (String email, String nome, String password, String morada, LocalDate data, int nif) {       
        Cliente c = new Cliente();
             
        c.setEmail(email);
        c.setNome(nome);
        c.setPassword(password);
        c.setMorada(morada);
        c.setDDN(data);
        c.setNif(nif);
        
        d.registarCliente(c);
    }      
        
    /**
     * Metodo que regista um proprietario na aplicaçao
     * 
     * @param  email o email a registar
     * @param  nome o nome a registar
     * @param  oassword a pass a registar
     * @param  morada a morada a registar
     * @param  data a data a registar
     */
    private void registoProprietario (String email, String nome, String password, String morada, LocalDate data, int nif) {       
        Proprietario p = new Proprietario();
             
        p.setEmail(email);
        p.setNome(nome);
        p.setPassword(password);
        p.setMorada(morada);
        p.setDDN(data);
        p.setNif(nif);
        
        d.registarProprietario(p);
    }
    
    /*
                ******* METODOS DE LOGIN *******
     */ 
    
    /**
     * Login do utilizador(Cliente ou Proprietario)
     */
    
    private void login() {
        Scanner input = new Scanner (System.in);
        String password = null;
        String email = null;
        Cliente c = null;
        Proprietario p = null;
        
        System.out.println("\n*** LOGIN");
        
        while (email == null) {
            System.out.print("Email: ");
            String em = input.nextLine();
            
            try {
                d.existeEmail(em);                
                c = d.mailToCliente(em);
                p = d.mailToProprietario(em);
                break;
            }
            catch (EmailInexistenteException e) {
                System.out.println("Email inexistente.");
                break;
            }
        }
        
        int r = 1;
        
        if(c != null){
            System.out.print("Password: ");
            password = input.nextLine();
            
            if (!(c.getPassword().equals(password))){
                System.out.println("Password incorreta.");
                r = 0;
            }
            
            if(r == 1)
                menu_Cliente(c);
        }
        else if(p != null){
            System.out.print("Password: ");
            password = input.nextLine();
            
            if (!(p.getPassword().equals(password))){
                System.out.println("Password incorreta.");
                r = 0;
            }
            
            if(r == 1)
                menu_Proprietario(p);
        }
        else {
            System.out.println("Usuário não encontrado.");
        }
        
        input.close();
    }
    
    /**
     * Metodo que procura o carro em conforme com a opçao que o Cliente escolheu
     * 
     * @param  c Cliente que se encontra no sistema
     * @param opcao que o Cliente tomou
     * @param  x a coordenada x atual
     * @param  y a coordenada y atual
     * @param  xDestino a coordenada x destino
     * @param  yDestino a coordenada y destino
     */
    private void tipoAluguer(Cliente c, List<Veiculo> disponiveis, int opcao, double x, double y, double xDestino, double yDestino){        
       switch(opcao){
           case 1 : carroMaisProximo(c, disponiveis, x, y, xDestino, yDestino);
                    break;
           case 2 : carroMaisBarato(c, disponiveis, x, y, xDestino, yDestino);            
                    break;
           case 3 : carroBaratoProximo(c, disponiveis, x, y, xDestino, yDestino);
                    break;
           case 4 : carroEspecifico(c, disponiveis, x, y, xDestino, yDestino);
                    break;
           case 5 : carroAutonomia(c, disponiveis, x, y, xDestino, yDestino);
                    break;
        }
     
    }
    
    /**
     * Metodo que encontra o carro mais proximo e inicia o processo de efetuar viagem
     * 
     * @param  c Cliente que se encontra no sistema
     * @param  x a coordenada x atual
     * @param  y a coordenada y atual
     * @param  xDestino a coordenada x destino
     * @param  yDestino a coordenada y destino
     */
    private void carroMaisProximo(Cliente c, List<Veiculo> disponiveis, double x, double y, double xDestino, double yDestino){
        Veiculo maisProximo = d.proximo(disponiveis, c, x, y);
        efetuarViagem(c, maisProximo, xDestino, yDestino);

    }
    
    /**
     * Metodo que encontra o carro mais barato e inicia o processo de efetuar viagem
     * 
     * @param  c Cliente que se encontra no sistema
     * @param  x a coordenada x atual
     * @param  y a coordenada y atual
     * @param  xDestino a coordenada x destino
     * @param  yDestino a coordenada y destino
     */
    private void carroMaisBarato(Cliente c, List<Veiculo> disponiveis, double x, double y, double xDestino, double yDestino){
        Veiculo maisBarato = d.barato(disponiveis, c, x, y);
        efetuarViagem(c, maisBarato, xDestino, yDestino);
    }
    
    /**
     * Metodo que encontra o carro mais barato com condicionante de distancia e inicia o processo de efetuar viagem
     * 
     * @param  c Cliente que se encontra no sistema
     * @param  x a coordenada x atual
     * @param  y a coordenada y atual
     * @param  xDestino a coordenada x destino
     * @param  yDestino a coordenada y destino
     */
    private void carroBaratoProximo(Cliente c, List<Veiculo> disponiveis, double x, double y, double xDestino, double yDestino){
        System.out.println("Insira a distancia que esta disposto/a a a percorrer a pe:");
        Scanner in = new Scanner (System.in);
        double dist = Double.parseDouble(in.nextLine());
                  
        Veiculo maisBarato = d.baratoProximo(disponiveis, c, x, y, dist);
        efetuarViagem(c, maisBarato, xDestino, yDestino);
       
    }
    
    /**
     * Metodo que encontra um carro especifico e inicia o processo de efetuar viagem
     * 
     * @param  c Cliente que se encontra no sistema
     * @param  x a coordenada x atual
     * @param  y a coordenada y atual
     * @param  xDestino a coordenada x destino
     * @param  yDestino a coordenada y destino
     */
    private void carroEspecifico(Cliente c, List<Veiculo> disponiveis, double x, double y, double xDestino, double yDestino){       
        Scanner in = new Scanner(System.in);
        System.out.println("Escolha qual o carro que pretende alugar:");
        
        int i = 0;
        for(Veiculo v : disponiveis){
            System.out.print("veiculo " + i + ": " + v.getMatricula() + "\n" );
            i++;
        }
        int opcao = Integer.parseInt(in.nextLine());
        Veiculo especifico = disponiveis.get(opcao);
        efetuarViagem(c, especifico, xDestino, yDestino);
        
        
    }
    
    /**
     * Metodo que encontra o carro com a autonimia desejada e inicia o processo de efetuar viagem
     * 
     * @param  c Cliente que se encontra no sistema
     * @param  x a coordenada x atual
     * @param  y a coordenada y atual
     * @param  xDestino a coordenada x destino
     * @param  yDestino a coordenada y destino
     */
    private void carroAutonomia(Cliente c, List<Veiculo> disponiveis, double x, double y, double xDestino, double yDestino){
        System.out.println("Insira a autonomia que pretende que o veiculo a alugar tenha:");
        Scanner in = new Scanner (System.in);
        double aut = Double.parseDouble(in.nextLine());
                  
        Veiculo maisAutonomia = d.autonomia(disponiveis, c, x, y, aut);
        efetuarViagem(c, maisAutonomia, xDestino, yDestino);
       
    }
    
    private void efetuarViagem(Cliente c, Veiculo v, double xDestino, double yDestino){
        Scanner input = new Scanner (System.in);
         
        double distanciaCliente_Carro = d.dist(c.getLocalX(), c.getLocalY(), v.getLocalX(), v.getLocalY());
        double distanciaViagem = d.dist(v.getLocalX(), v.getLocalY(), xDestino, yDestino);
        
        if(!validaAutonomia(v, distanciaViagem)){
            System.out.println("O veiculo pretendido nao tem autonomia suficiente para efetuar a viagem.\nVolte a inserir a sua localizaçao e escolha outro veiculo.\n");
            menu_Carro(null, c);
        }
        
        else{
            double tempoCliente_Carro = d.tempo(c.getLocalX(), c.getLocalY(), v.getLocalX(), v.getLocalY(), 4);
            double tempoViagem = d.tempo(v.getLocalX(), v.getLocalY(), xDestino, yDestino, v.getVelocidade());
            double transito = Math.random() * (1.5 - 1.0) + 1.0; 
            double tempoRealViagem = tempoViagem * transito;
        
            Proprietario pro = d.veiculoToProprietario(v.getMatricula());
            
            String emailCli = c.getEmail();
            String emailPro = pro.getEmail();
            String matricula = v.getMatricula();
            
            double custo = v.getPreco() * distanciaViagem;
            if(c.verificaAluguerPendente() == false){
                System.out.println("A viagem tera um custo de " + custo + " euros.\n");
                
                AluguerCarro a = new AluguerCarro(emailCli, emailPro, matricula, distanciaViagem, custo, 1, xDestino, yDestino);
                
                d.alteraAntesViagem(emailCli, emailPro, matricula, a);
                
                  System.out.println("O pedido foi efetuado. Agora o proprietario " + emailPro + " precisa de entrar na sua conta para aceitar ou nao o pedido.\n");
                  /*
                  System.out.println("O cliente chegará à sua localização em "+ tempoCliente_Carro +" segundos.");
                   */
            }
            else{
                System.out.println("A viagem terá uma duração estimada de "+ tempoViagem +" segundos.");
                System.out.println("\n\n");
                System.out.println("***************************************** Boa Viagem *****************************************");
        
                double totalFaturado = v.getTotalFaturado() + custo;
            
                System.out.println("\nA viagem foi efetuada e teve uma duração de " + tempoRealViagem + " segundos.\n");
                double diferencaTempo = tempoRealViagem - tempoViagem;
                if (diferencaTempo != 0)
                    System.out.println("A viagem demorou mais " + diferencaTempo + " segundos que o suposto.\n");
        
                //e suposto aumentarmos tambem o custo
        
                double classificacao = validaClassificacao("(Cliente) Classificacao da viagem: ");
        
                
                /*
                 * Processo do proprietario dar uma classificacao a um cliente.
                 */
                Aluguer aux = c.aluguerPendente();
                d.removeAluguer(emailCli, emailPro, matricula, aux);
                
                AluguerCarro b = new AluguerCarro(emailCli, emailPro, matricula, distanciaViagem, custo, 0, xDestino, yDestino);
                d.alteraAposViagem(emailCli, emailPro, matricula, classificacao, totalFaturado, xDestino, yDestino, distanciaViagem, b);
        
            
            }
            input.close();
        }
    }
   
    /**
     * Disponibiliza o perfil de um Cliente
     * 
     * @param  c o Cliente cujo perfil sera disponibilizado
     */
    private void perfilCli (Cliente c) {
        System.out.println("\n**** PERFIL ");
        System.out.print(c.toString()+"\n");
    }
    
    /**
     * Metodo que insere um novo veiculo no sistema
     * 
     * @param  p Proprietario que se encontra no sistema
     * @param  opcao do tipo de veiculo 
     */
    void tipoCarro(Proprietario p, int opcao){
        Scanner input = new Scanner(System.in);
        String mat = validaMatricula("Matricula: ");
        double velocidade = validaCoordPositiva("Velocidade média por km: ");
        double preco = validaCoordPositiva("Preço por km: ");
        System.out.println("Coordenadas relativas à localização:");
        double x = validaCoord("x");
        double y = validaCoord("y");
        
        double consumo = validaCoordPositiva("Consumo por km: ");
        double combustivelMax = validaCoordPositiva("Combustivel maximo: ");
        double combustivel = validaCombustivel("Combustivel: ", combustivelMax);
        boolean valido = false;          
        
        if (opcao == 1) {
            valido = true;
            CarroGasolina c = new CarroGasolina(mat, velocidade, preco, new ArrayList<>(), 0, 0, 
                                             x, y, true, consumo, combustivel, combustivelMax);
            d.insereVeiculo(c, p); 
        }
        else if (opcao == 2) {
            valido = true;
            double consumoBat = validaCoordPositiva("Consumo de bateria por km: ");
            double combustivelBatMax = validaCoordPositiva("Bateria maxima: ");
            double combustivelBat = validaCombustivel("Bateria: ", combustivelBatMax);
            CarroHibrido c = new CarroHibrido(mat, velocidade, preco, new ArrayList<>(), 0, 0, 
                                       x, y, true, consumoBat, combustivelBat, combustivelBatMax,
                                       consumo, combustivel, combustivelMax);
            d.insereVeiculo(c, p); 
        }    
        else if (opcao == 3) {
            valido = true;
            CarroEletrico c = new CarroEletrico(mat, velocidade, preco, new ArrayList<>(), 0, 0, 
                                             x, y, true, consumo, combustivel, combustivelMax);
            d.insereVeiculo(c, p); 
        }    
        else if (opcao == 4) {
            valido = true;
            CarroGasoleo c = new CarroGasoleo(mat, velocidade, preco, new ArrayList<>(), 0, 0, 
                                             x, y, true, consumo, combustivel, combustivelMax);
            d.insereVeiculo(c, p); 
        }
                                             
    }
    
    /**
     * Disponibiliza o perfil de um Proprietario
     * 
     * @param  p o Proprietario cujo perfil sera disponibilizado
     */
    private void perfilProp (Proprietario p) {
        System.out.println("\n**** PERFIL");
        System.out.print(p.toString()+"\n");
    }
    
    private void top10(int op) {
        List<Cliente> top10 = new ArrayList<Cliente>();
        if(op == 1){
             top10 = d.top10_Utilizacao();
        
             System.out.println("Os 10 clientes que utilizaram mais o sistema: \n");
             for (Cliente c : top10)
                System.out.println(c.toString_top10());
        }
        else{
            top10 = d.top10_Distancia();
        
             System.out.println("Os 10 clientes que percorram mais kms: \n");
             for (Cliente c : top10)
                System.out.println(c.toString_top10());
        }
    }
    
    /*
                *******METODOS DE VALIDAÇAO*******
     */
    
    
    /**
     * Metodo que valida a autonomia apos um deslocamento
     * 
     * @param  v veiculo que se vai deslocar
     * @param  distanciaViagem distancia da viagem
     * 
     * @return true caso a autonomia apos o deslocamento seja superior 0, false caso contrario
     */
    private boolean validaAutonomia(Veiculo v, double distanciaViagem){
        double autonomia = v.autonomiaAposDeslocamento(distanciaViagem);
        if(autonomia < 0)
           return false;
        return true;
    }
    
    /**
     * Metodo que valida um double
     * 
     * @param  xy string que contem o double ou uma frase
     */
    private double validaCoord(String xy) {
        Scanner in = new Scanner (System.in);
        boolean validade = false;
        double x = 0.0;
        
        System.out.println("Coordenada " + xy + ": ");
        while(!validade){
            try{
                validade = true;
                x = Double.parseDouble(in.nextLine());
            }
            catch (InputMismatchException e){
                validade = false;
                System.out.println("Coordenada inválida!");
                x = validaCoord(xy);
            }
        }
        in.close();
        return x;
    }
    
    /**
     * Metodo que valida um double positivo
     * 
     * @param  xy string que contem um double ou uma fras
     */
    private double validaCoordPositiva(String y) {
        Scanner in = new Scanner (System.in);
        boolean validade = false;
        double x = 0.0;
        
        while(!validade){
            try{
                System.out.println(y);
                validade = true;
                x = Double.parseDouble(in.nextLine());
                
                if(x <= 0)
                    validade = false;
            }
            catch (InputMismatchException e){
                validade = false;
                System.out.println("Valor inválido!");
                x = validaCoordPositiva(y);
            }
        }
        in.close();
        return x;
    }
    
    /**
     * Metodo que valida uma matricula
     * 
     * @param  xy string que contem a matricula
     */
    private String validaMatricula(String m){ 
        Scanner in = new Scanner(System.in);
        boolean validade = false;
        String mat = null;
        while(!validade){
            try{
                System.out.println(m);
                validade = true;
                mat = in.nextLine();
                try{
                    if(mat.length() != 8 || d.existeMatricula(mat)){
                        validade = false;
                        System.out.println("Matricula inválida!");
                        mat = validaMatricula(m);
                    }
                }
                catch(MatriculaInexistenteException e){
                    validade = true;
                }
            }
            catch(InputMismatchException e){
                validade = false;
                System.out.println("Matricula inválida!");
                mat = validaMatricula(m);
            }
        }
        in.close();
        return mat; 
    }
    
    /**
     * Metodo que valida uma classificacao
     * 
     * @param  classificacao string que contem a classificacao
     */
    private Double validaClassificacao(String classificacao){ 
        Scanner in = new Scanner(System.in);
        boolean validade = false;
        double x = 0.0;
        while(!validade){
            try{
                System.out.println(classificacao);
                validade = true;
                x = Double.parseDouble(in.nextLine());
                
                if(x < 0 || x > 100){
                    validade = false;
                }
            }
            catch(InputMismatchException e){
                validade = false;
                System.out.println("Classificacao inválida!");
                x = validaClassificacao(classificacao);
            }
        }
        in.close();
        return x; 
    }
    
    /**
     * Metodo que valida um combustivel
     * 
     * @param  y string que contem uma frase
     * @param  com combustivel maximo de um carro
     */
    private double validaCombustivel(String y, double com) {
        Scanner in = new Scanner (System.in);
        boolean validade = false;
        double x = 0.0;
        
        while(!validade){
            try{
                System.out.println(y);
                validade = true;
                x = Double.parseDouble(in.nextLine());
                
                if(x < 0 || x > com)
                    validade = false;
            }
            catch (InputMismatchException e){
                validade = false;
                System.out.println("Valor inválido!");
                x = validaCombustivel(y, com);
            }
        }
        in.close();
        return x;
    }
    
    /*
                *******METODOS AUXILIARES*******
     */
    
    private void faturaViaturas(Proprietario p){
        Scanner in = new Scanner (System.in);
        System.out.println("Que viatura pretende saber o total faturado?\n");
        int i = 0;
        
        if(p.getVeiculos().isEmpty())
             System.out.println("Nao tem qualquer viatura.\n");
        else{     
            for(Veiculo v : p.getVeiculos()){
                System.out.println("Viatura " + i + ":\n" + v.getMatricula());
                i++;
            }
        
            int op = in.nextInt();
            if(op >= 0 && op < i){
                Veiculo v = p.getVeiculos().get(op);
                System.out.println("Total faturado: " + v.getTotalFaturado());
            }
            else
                System.out.println("Opçao invalida. \n");
        }
    }
    
    private void todos_AlugueresPro(Proprietario p){
        System.out.println("Alugueres efetuados:\n");
        int i = 0;
        for(Aluguer a : p.getAlugueres()){
            if(a.getPendente() == 0){
                System.out.println("Aluguer " + i + ":\n" + a.toString());
                i++;
            }
        }
    }
    
    private void todos_AlugueresCli(Cliente c){
        System.out.println("Alugueres efetuados:\n");
        int i = 0;
        for(Aluguer a : c.getAlugueres()){
            if(a.getPendente() == 0){
                System.out.println("Aluguer " + i + ":\n" + a.toString());
                i++;
            }
        }
    }
    
    private void alterarPreco(Proprietario p){
        Scanner in = new Scanner (System.in);
        System.out.println("Que viatura pretende alterar preco?\n");
        int i = 0;
        
        if(p.getVeiculos().isEmpty())
             System.out.println("Nao tem qualquer viatura.\n");
        else{     
            for(Veiculo v : p.getVeiculos()){
                System.out.println("Viatura " + i + ":\n" + v.getMatricula());
                i++;
            }
        
            int op = in.nextInt();
            System.out.println("Novo preco: \n");
            double preco = in.nextDouble();
            Veiculo vei = null;
            if(op >= 0 && op < i){
                vei = p.getVeiculos().get(op);
                vei.setPreco(preco);
                d.insereVeiculo(vei,p);
            }
            else
                System.out.println("Opçao invalida. \n");
        }
    }
    
    private void alterarDisponibilidade(Proprietario p){
        Scanner in = new Scanner (System.in);
        System.out.println("Que viatura pretende alterara disponibilidade?\n");
        int i = 0;
        
        if(p.getVeiculos().isEmpty())
             System.out.println("Nao tem qualquer viatura.\n");
        else{     
            for(Veiculo v : p.getVeiculos()){
                System.out.println("Viatura " + i + ":\n" + v.getMatricula());
                i++;
            }
        
            int op = in.nextInt();
            Veiculo vei = null;
            if(op >= 0 && op < i){
                vei = p.getVeiculos().get(op);
                vei.setDisponivel(!vei.getDisponivel());
                d.insereVeiculo(vei,p);
            }
            else
                System.out.println("Opçao invalida. \n");
        }
    }
    
    private void abastecer(Proprietario p){
        Scanner in = new Scanner (System.in);
        System.out.println("Que viatura pretende abastecer?\n");
        int i = 0;
        
        if(p.getVeiculos().isEmpty())
             System.out.println("Nao tem qualquer viatura.\n");
        else{     
            for(Veiculo v : p.getVeiculos()){
                System.out.println("Viatura " + i + ":\n" + v.getMatricula());
                i++;
            }
        
            int op = in.nextInt();
            Veiculo vei = null;
            if(op >= 0 && op < i){
                vei = p.getVeiculos().get(op);
                if(vei instanceof CarroGasolina){
                    CarroGasolina a = (CarroGasolina) vei;
                    a.setCombustivel(a.getCombustivelMax());
                    a.setDisponivel(true);
                    d.insereVeiculo(a, p);
                }
                if(vei instanceof CarroGasoleo){
                    CarroGasoleo a = (CarroGasoleo) vei;
                    a.setCombustivel(a.getCombustivelMax());
                    a.setDisponivel(true);
                    d.insereVeiculo(a, p);
                }
                if(vei instanceof CarroHibrido){
                    CarroHibrido a = (CarroHibrido) vei;
                    a.setCombustivel(a.getCombustivelMax());
                    a.setBateria(a.getBateriaMax());
                    a.setDisponivel(true);
                    d.insereVeiculo(a, p);
                }
                if(vei instanceof CarroEletrico){
                    CarroEletrico a = (CarroEletrico) vei;
                    a.setBateria(a.getBateriaMax());
                    a.setDisponivel(true);
                    d.insereVeiculo(a, p);
                }
            }
            else
                System.out.println("Opçao invalida. \n");
        }
    }
    
    /**
     * Metodo que converte uma string numa variavel do tipo Date
     * 
     * @param  input a string a converter
     * 
     * @return Date caso o input corresponda a uma data valida no formato valido, null caso o contrario
     */
    private LocalDate convertToDate(String in) {
        Date date = null;
        LocalDate ldate = null;
        if(null == in) {
            return null;
        }
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        try {
            format.setLenient(false);
            date = format.parse(in);
            ldate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        }catch (ParseException e) {
            System.out.println("A data inserida esta invalida");
        }
        return ldate;
    }  
}