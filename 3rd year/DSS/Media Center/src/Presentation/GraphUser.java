package smc.Presentation;

import smc.Business.Administrador;
import smc.Business.SMC;
import smc.Business.Utilizador;
import smc.Business.UtilizadorInexistente;

import javax.swing.*;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GraphUser implements ActionListener {
    private SMC smc;
    private Utilizador usr;

    //variaveis da editframe
    private JFrame o;
    private JLabel nome, email, password, nomeedit, passedit, dadosInv;
    private JTextField nome1, password1;
    private JButton alteranome, alterapass, confirm, envDados;
    private JList cont;

    //variaveis do graphuser
    private JFrame g;
    private JLabel user, cat, rep;
    private JButton play, altpass, up, mud, amg1, amg2, amg3, criapl, reg1, reg2, endS;

    //variaveis da mudança de categoria
    private JFrame b;
    private JLabel categoria, nome2;
    private JCheckBox rock, pop, indie, metal, pimba;


    //variaveis dos amigos
    private JFrame t1;
    private JList<String> list, list1;
    private JLabel enviarP, pedidosA, nomeutilizador, potenciaisA, pedidoEnv1, pedidoEnv2;
    private JTextField nomeutilizador2;
    private JButton voltar, aceitar, rejeitar, enviarP1;
    private DefaultListModel<String> listaPed, listaPot;

    //registar e eliminar frame
    private JFrame p;
    private JLabel registar, registarpass, registaremail, eliminar, erroDados;
    private JTextField registar1, registaremail1, eliminar1;
    private JPasswordField registarpass1;
    private JButton validarDados, confirma;
    private JCheckBox adminBox, comumBox;

    //upload
    private JFrame er;
    private JLabel succ;

    //reproduzirConteudo
    private JFrame r;
    private JLabel reprodm;
    private JButton cparticular, cplaylist, caleatorio, shufflem, reproduz, confirma2, back;
    private DefaultListModel<String> listaCont, listaCont1;
    private JList<String> listaContBox, listaContBox1;

    //criar playlist
    private JFrame pl;
    private JLabel nomePlay, artista;
    private JTextField nomePlay1, artista1;
    private JButton criaplaylist, randompl, generopl, artistpl, confirma1, confirma3, back1,playlistend;
    private JCheckBox rock1, hh1, blues1, classico1, eletronica1, fado1, funk1, grunge1, jazz1, kizomba1, pop1, reggae1;
    private DefaultListModel<String> listaGenM, listaArtM;
    private JList<String> listaGen, listaArt;
    private String categoria1;


    public GraphUser(SMC s, Utilizador u) {
        this.smc = s;
        this.usr = u;


        g = new JFrame("MiEI Media Center");
        Image icon = Toolkit.getDefaultToolkit().getImage("C:\\DSS\\miei.jpg");

        user = new JLabel(u.getNome());

        if (usr instanceof Administrador)
            cat = new JLabel("Administrador");
        else cat = new JLabel("Utilizador");

        rep = new JLabel("Reproduzir conteúdo:");
        play = new JButton("Play");
        altpass = new JButton("Editar conta");
        up = new JButton("Upload");
        mud = new JButton("Mudar categoria do conteúdo");
        amg1 = new JButton("Enviar pedido");
        amg2 = new JButton("Pedidos de amizade");
        amg3 = new JButton("Potenciais amigos");
        criapl = new JButton("Criar playlist");
        reg1 = new JButton("Registar utilizador");
        reg2 = new JButton("Eliminar utilizador");
        endS = new JButton("Terminar sessão");
        user.setBounds(130, 10, 200, 80);
        cat.setBounds(165, 20, 200, 30);
        rep.setBounds(35, 90, 150, 30);
        play.setBounds(200, 90, 170, 30);
        altpass.setBounds(30, 130, 170, 30);
        up.setBounds(200, 130, 170, 30);
        mud.setBounds(30, 170, 340, 30);
        amg1.setBounds(30, 210, 340, 30);
        amg2.setBounds(30, 250, 340, 30);
        amg3.setBounds(30, 290, 340, 30);
        criapl.setBounds(30, 330, 340, 30);
        reg1.setBounds(30, 250, 340, 30);
        reg2.setBounds(30, 290, 340, 30);
        endS.setBounds(30, 370, 340, 30);
        g.setIconImage(icon);
        g.add(user);
        g.add(cat);
        g.add(rep);
        g.add(play);
        g.add(altpass);
        g.add(up);
        g.add(mud);
        g.add(amg1);
        g.add(amg2);
        g.add(amg3);
        g.add(criapl);
        g.add(reg1);
        g.add(reg2);
        g.add(endS);
        endS.addActionListener(this);
        amg1.addActionListener(this);
        amg2.addActionListener(this);
        amg3.addActionListener(this);
        criapl.addActionListener(this);
        reg1.addActionListener(this);
        reg2.addActionListener(this);
        altpass.addActionListener(this);
        up.addActionListener(this);
        mud.addActionListener(this);
        play.addActionListener(this);
        g.setSize(410, 500);
        g.setLayout(null);
        g.setVisible(true);
    }

    public void actionPerformed(ActionEvent f) {
        if (f.getSource() == endS) {
            g.dispose();
            new Graph(this.smc);
        } else if (f.getSource() == altpass) {
            editFrame();
        } else if (f.getSource() == reg1) {
            AdminFrame();
            registauser();
        } else if (f.getSource() == reg2) {
            AdminFrame();
            removeuser();
        } else if (f.getSource() == up) {
            fazUpload();
        }
        else if (f.getSource() == criapl) {
            CriaPlaylist();
        }

        //mudar cat
        else if (f.getSource() == mud) {
            categoriaFrame();
        } else if (f.getSource() == confirm) {
            String cat = new String();

            if (rock.isSelected()) cat = "Rock";
            else if (metal.isSelected()) cat = "Metal";
            else if (pop.isSelected()) cat = "Pop";
            else if (pimba.isSelected()) cat = "Pimba";
            else if (indie.isSelected()) cat = "Indie";

            smc.alterarConteudo(this.usr.getNome(), cont.getSelectedValue().toString(), cat);

        }


        //edit
        else if (f.getSource() == confirm) {
            if (nome1.isVisible()) {
                try {
                    smc.alterarConta(usr.getNome(), nome1.getText(), 0);
                    dadosInv.setText("Alterações feitas com sucesso.");
                    dadosInv.setVisible(true);
                } catch (Exception ex) {
                    dadosInv.setText("O nome já existe.");
                    dadosInv.setVisible(true);
                }
            }

            if (password1.isVisible()) {
                try {
                    smc.alterarConta(usr.getNome(), password1.getText(), 1);
                    dadosInv.setText("Alterações feitas com sucesso.");
                    dadosInv.setVisible(true);
                } catch (Exception ignored) {
                }
            }
            o.dispose();
        } else if (f.getSource() == alteranome) {
            alterarnome();
        } else if (f.getSource() == alterapass) {
            alterapass();
        }


        // amigos

        else if (f.getSource() == amg1) {
            AmgFrame();
            enviarPedido();
        } else if (f.getSource() == amg2) {
            AmgFrame();
            pedidosAmizade();
            ;
        } else if (f.getSource() == amg3) {
            AmgFrame();
            potenciaisAmigos();
        } else if (f.getSource() == voltar) {
            if (this.nomeutilizador2.isVisible()) {
                String pedUsr = nomeutilizador2.getText();

                try {
                    this.smc.enviarPedido(this.usr.getNome(), pedUsr);
                    pedidoEnv1 = new JLabel("Pedido enviado com sucesso.");
                    pedidoEnv1.setVisible(true);

                } catch (Exception e) {
                    pedidoEnv1 = new JLabel("O utilizador não existe");
                    pedidoEnv1.setVisible(true);

                }
            }

            t1.dispose();
        } else if (f.getSource() == aceitar) {
            smc.responderPedido(this.usr.getNome(), 1, list.getSelectedValue());
        } else if (f.getSource() == rejeitar) {
            smc.responderPedido(this.usr.getNome(), 0, list.getSelectedValue());
        } else if (f.getSource() == enviarP1) {
            try {
                smc.enviarPedido(this.usr.getNome(), list1.getSelectedValue());
                pedidoEnv2.setText("Pedido enviado com sucesso!");
                pedidoEnv2.setVisible(true);

            } catch (Exception ex) {
                pedidoEnv2.setText("O utilizador não existe");
                pedidoEnv2.setVisible(true);
            }
        }


        //admins
        else if (f.getSource() == validarDados) {
            if (registarpass1.isVisible()) {
                String usr = registar1.getText();
                String email = registaremail1.getText();
                String pass = registarpass1.getPassword().toString();

                try {
                    if (this.smc.validar(usr, email, pass)) {
                        this.erroDados.setVisible(false);
                        this.comumBox.setVisible(true);
                        this.adminBox.setVisible(true);
                        this.validarDados.setVisible(false);
                        this.confirma.setVisible(true);
                    }
                } catch (Exception e) {
                    this.erroDados.setVisible(true);
                }
            }
        } else if (f.getSource() == confirma) {
            if (registarpass1.isVisible()) {
                String usr = registar1.getText();
                String email = registaremail1.getText();
                String pass = registarpass1.getPassword().toString();

                if (adminBox.isSelected() && comumBox.isSelected()) {
                    erroDados = new JLabel("Selecione apenas uma opção");
                    erroDados.setVisible(true);
                } else if (adminBox.isSelected()) {
                    this.smc.registarAdmin(usr, email, pass);
                    p.dispose();
                } else if (comumBox.isSelected()) {
                    this.smc.registarComum(usr, email, pass);
                    p.dispose();
                }
            } else {
                String usr = registar1.getText();

                try {
                    this.smc.eliminarUtilizador(usr);
                    p.dispose();
                } catch (Exception e) {
                    erroDados.setVisible(true);
                }
            }
        }

        //reproduzir conteúdo
        else if (f.getSource() == play) {
            Reproduzir();
        } else if (f.getSource() == cparticular) {
            partFrame();
        } else if (f.getSource() == cplaylist) {
            playlistFrame();
            listaContBox1.setVisible(true);
        } else if (f.getSource() == caleatorio) {
            noFrame();
            playlistrandom();
        } else if (f.getSource() == back) {
            r.dispose();
        } else if (f.getSource() == shufflem) {
            String c = listaContBox1.getSelectedValue();
            this.smc.reproduzirPlaylist(c,this.usr.getNome(),true);
        } else if (f.getSource() == reproduz) {

            if (listaContBox.isVisible()) {
                String c = listaContBox.getSelectedValue();
                this.smc.reproduzirConteudo(c);
            }
            else if(listaContBox1.isVisible()) {
                String c = listaContBox1.getSelectedValue();
                this.smc.reproduzirPlaylist(c,this.usr.getNome(),false);
            }
            else {
                this.smc.aleatorio();
            }
        }


        //criarplaylist

        else if (f.getSource() == randompl){

        } else if (f.getSource() == generopl){
            generoplFrame();
        } else if (f.getSource() == artistpl){
            artistaplFrame();
        } else if (f.getSource() == confirma1){
            if(rock1.isSelected()) {
                categoria1 = "Rock";
            }
            else if (hh1.isSelected()) {
                categoria1 = "Hip Hop";
            }
            else if (blues1.isSelected()) {
                categoria1 = "Blues";
            }
            else if (classico1.isSelected()) {
                categoria1 = "Clássico";
            }
            else if (eletronica1.isSelected()) {
                categoria1 = "Eletrónica";
            }
            else if (fado1.isSelected()) {
                categoria1 = "Fado";
            }
            else if (funk1.isSelected()) {
                categoria1 = "Funk";
            }
            else if (grunge1.isSelected()) {
                categoria1 = "Grunge";
            }
            else if (jazz1.isSelected()) {
                categoria1 = "Jazz";
            }
            else if (kizomba1.isSelected()) {
                categoria1 = "Kizomba";
            }
            else if (pop1.isSelected()) {
                categoria1 = "Pop";
            }
            else if (reggae1.isSelected()) {
                categoria1 = "Reggae";
            }

            ArrayList<String> playL = new ArrayList<>(this.smc.playlistGenero(this.usr.getNome(), nome1.getText(), categoria1));
            for(String c : playL)
                listaGenM.addElement(c);

            listaGen = new JList<>(listaGenM);

            generoplFrame1();


        } else if (f.getSource() == confirma3){

            ArrayList<String> playL = new ArrayList<>(this.smc.playlistArtista(this.usr.getNome(), nome1.getText(), artista1.getText()));
            for(String c : playL)
                listaArtM.addElement(c);

            listaArt = new JList<>(listaArtM);

            artistaplFrame1();
        }  else if (f.getSource() == back1){
            firstplFrame();
        } else if (f.getSource() == playlistend){
            if(listaGen.isVisible()) {
                this.smc.addConteudo(this.usr.getNome(), nome1.getText(),listaGen.getSelectedValuesList(),categoria1);
            }
            else if(listaArt.isVisible()) {
                this.smc.adConteudo(this.usr.getNome(), nome1.getText(), listaArt.getSelectedValuesList(), artista1.getText());
            }

            pl.dispose();
        }
    }

    public void adminFrame() {
        up.setVisible(false);
        mud.setVisible(false);
        amg1.setVisible(false);
        amg2.setVisible(false);
        amg3.setVisible(false);
        rep.setVisible(false);
        play.setVisible(false);
    }

    public void convFrame() {
        altpass.setVisible(false);
        up.setVisible(false);
        amg1.setVisible(false);
        amg2.setVisible(false);
        amg3.setVisible(false);
        mud.setVisible(false);
        reg1.setVisible(false);
        reg2.setVisible(false);
        criapl.setVisible(false);
    }

    public void userFrame() {
        reg1.setVisible(false);
        reg2.setVisible(false);
    }

    public void otherFrame() {
        user.setVisible(false);
        cat.setVisible(false);
        altpass.setVisible(false);
        up.setVisible(false);
        mud.setVisible(false);
        rep.setVisible(false);
        play.setVisible(false);
        amg1.setVisible(false);
        amg2.setVisible(false);
        amg3.setVisible(false);
        reg1.setVisible(false);
        reg2.setVisible(false);
        endS.setVisible(false);
    }

    private void editFrame() {
        o = new JFrame("MiEI Media Center");
        Image icon = Toolkit.getDefaultToolkit().getImage("C:\\DSS\\miei.jpg");
        nome = new JLabel("Nome : " + usr.getNome());
        email = new JLabel("Email: " + usr.getEmail());
        password = new JLabel("Password: " + usr.getPassword());
        password = new JLabel("Password:");
        nomeedit = new JLabel("Novo nome");
        passedit = new JLabel("Nova password");
        dadosInv = new JLabel("Default");
        nome1 = new JTextField();
        password1 = new JPasswordField();
        alteranome = new JButton("Alterar nome");
        alterapass = new JButton("Altera password");
        envDados = new JButton("Enviar dados");
        confirm = new JButton("Confirmar");


        nome.setBounds(70, 30, 200, 30);
        email.setBounds(70, 45, 200, 30);
        password.setBounds(70, 60, 200, 30);
        nomeedit.setBounds(30, 110, 150, 30);
        nome1.setBounds(30, 135, 340, 30);
        passedit.setBounds(30, 110, 150, 30);
        password1.setBounds(30, 135, 340, 30);
        alteranome.setBounds(30, 290, 340, 30);
        alterapass.setBounds(30, 330, 340, 30);
        confirm.setBounds(30, 370, 340, 30);
        dadosInv.setBounds(70, 75, 150, 30);
        envDados.setBounds(30, 330, 340, 30);
        o.setIconImage(icon);
        o.add(nome);
        o.add(email);
        o.add(password);
        o.add(dadosInv);
        o.add(nomeedit);
        o.add(passedit);
        o.add(nome1);
        o.add(password1);
        o.add(envDados);
        o.add(alteranome);
        o.add(alterapass);
        o.add(confirm);
        alteranome.addActionListener(this);
        alterapass.addActionListener(this);
        confirm.addActionListener(this);
        envDados.addActionListener(this);
        o.setSize(410, 500);
        o.setLayout(null);
        o.setVisible(true);
        dadosInv.setVisible(false);
        envDados.setVisible(false);
        nomeedit.setVisible(false);
        passedit.setVisible(false);
        nome1.setVisible(false);
        password1.setVisible(false);
    }


    private void alterarnome() {
        dadosInv.setVisible(false);
        password1.setVisible(false);
        passedit.setVisible(false);
        nomeedit.setVisible(true);
        nome1.setVisible(true);
        envDados.setVisible(false);
    }

    private void alterapass() {
        dadosInv.setVisible(false);
        nomeedit.setVisible(false);
        nome1.setVisible(false);
        password1.setVisible(true);
        passedit.setVisible(true);
        envDados.setVisible(false);
    }


    public void AmgFrame() {
        t1 = new JFrame("MiEI Media Center");
        Image icon = Toolkit.getDefaultToolkit().getImage("C:\\DSS\\miei.jpg");
        enviarP = new JLabel("Enviar pedido");
        nomeutilizador = new JLabel("Nome do utilizador");
        pedidosA = new JLabel("Pedidos de amizade");
        potenciaisA = new JLabel("Potenciais amigos");
        pedidoEnv1 = new JLabel("Default");
        pedidoEnv2 = new JLabel("Default");

        listaPed = new DefaultListModel<String>();
        ArrayList<String> pedAmiz = new ArrayList<>(this.smc.pendentes(this.usr.getNome()));
        for (String p : pedAmiz)
            listaPed.addElement(p);

        listaPot = new DefaultListModel<String>();
        ArrayList<String> pedPot = new ArrayList<>(this.smc.potenciaisAmigos(this.usr.getNome()));
        for (String p : pedPot)
            listaPot.addElement(p);

        list = new JList<>(listaPed);
        list1 = new JList<>(listaPot);
        nomeutilizador2 = new JTextField();
        enviarP1 = new JButton("Enviar pedido");
        voltar = new JButton("Confirmar");
        aceitar = new JButton("Aceitar");
        rejeitar = new JButton("Rejeitar");
        enviarP.setBounds(165, 10, 200, 80);
        pedidosA.setBounds(30, 90, 150, 30);
        potenciaisA.setBounds(30, 90, 150, 30);
        nomeutilizador.setBounds(35, 90, 150, 30);
        nomeutilizador2.setBounds(30, 120, 340, 30);
        list.setBounds(30, 120, 340, 60);
        list1.setBounds(30, 120, 340, 60);
        aceitar.setBounds(30, 330, 170, 30);
        rejeitar.setBounds(200, 330, 170, 30);
        enviarP1.setBounds(30, 330, 340, 30);
        pedidoEnv1.setBounds(30, 120, 200, 80);
        pedidoEnv2.setBounds(30, 220, 200, 30);
        voltar.setBounds(30, 370, 340, 30);
        t1.setIconImage(icon);
        t1.add(list1);
        t1.add(potenciaisA);
        t1.add(enviarP);
        t1.add(nomeutilizador);
        t1.add(nomeutilizador2);
        t1.add(list);
        t1.add(pedidosA);
        t1.add(pedidoEnv1);
        t1.add(aceitar);
        t1.add(rejeitar);
        t1.add(pedidoEnv2);
        t1.add(enviarP1);
        t1.add(voltar);
        voltar.addActionListener(this);
        enviarP1.addActionListener(this);
        aceitar.addActionListener(this);
        rejeitar.addActionListener(this);
        t1.setSize(410, 500);
        t1.setLayout(null);
        t1.setVisible(true);
    }


    public void enviarPedido() {
        list.setVisible(false);
        pedidosA.setVisible(false);
        aceitar.setVisible(false);
        rejeitar.setVisible(false);
        list1.setVisible(false);
        potenciaisA.setVisible(false);
        enviarP1.setVisible(false);
        pedidoEnv1.setVisible(false);
        pedidoEnv2.setVisible(false);

    }

    public void pedidosAmizade() {
        enviarP.setVisible(false);
        nomeutilizador.setVisible(false);
        nomeutilizador2.setVisible(false);
        list1.setVisible(false);
        potenciaisA.setVisible(false);
        enviarP1.setVisible(false);
        pedidoEnv1.setVisible(false);
        pedidoEnv2.setVisible(false);

    }

    public void potenciaisAmigos() {
        enviarP.setVisible(false);
        nomeutilizador.setVisible(false);
        nomeutilizador2.setVisible(false);
        list.setVisible(false);
        pedidosA.setVisible(false);
        aceitar.setVisible(false);
        rejeitar.setVisible(false);
        pedidoEnv1.setVisible(false);
        pedidoEnv2.setVisible(false);

    }


    //Upload
    public void fazUpload() {
        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        jfc.setDialogTitle("Escolha o ficheiro");
        jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        jfc.setMultiSelectionEnabled(true);
        int returnValue = jfc.showOpenDialog(null);
        ArrayList<String> ficheiros = new ArrayList<String>();
        ArrayList<String> paths = new ArrayList<String>();

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            for (File file : jfc.getSelectedFiles()) {
                ficheiros.add(file.getName());
            }

            for (File file : jfc.getSelectedFiles()) {
                paths.add(file.getPath());
            }

            try {
                smc.upload(ficheiros, paths, this.usr.getNome());

                er = new JFrame("Erro");
                Image icon = Toolkit.getDefaultToolkit().getImage("C:\\DSS\\miei.jpg");
                succ = new JLabel("Já existe um vídeo com esse nome.");
                succ.setBounds(30, 30, 150, 30);
                er.setIconImage(icon);
                er.add(succ);
                er.setSize(200, 200);
                er.setLayout(null);
                er.setVisible(true);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
    //admin frame


    public void AdminFrame() {
        o = new JFrame("MiEI Media Center");
        Image icon = Toolkit.getDefaultToolkit().getImage("C:\\DSS\\miei.jpg");
        registar = new JLabel("Nome");
        registaremail = new JLabel("Email");
        registarpass = new JLabel("Password");
        eliminar = new JLabel("Nome");
        erroDados = new JLabel("O utilizador já existe.");
        registar1 = new JTextField();
        registaremail1 = new JTextField();
        eliminar1 = new JTextField();
        registarpass1 = new JPasswordField();
        validarDados = new JButton("Validar dados");
        confirma = new JButton("Confirmar");
        adminBox = new JCheckBox("Administrador");
        comumBox = new JCheckBox("Comum");

        registar.setBounds(30, 30, 150, 30);
        erroDados.setBounds(30, 230, 200, 30);
        registar1.setBounds(30, 55, 340, 30);
        registaremail.setBounds(30, 95, 150, 30);
        registaremail1.setBounds(30, 120, 340, 30);
        registarpass.setBounds(30, 160, 150, 30);
        registarpass1.setBounds(30, 185, 340, 30);
        eliminar.setBounds(30, 30, 150, 30);
        eliminar1.setBounds(30, 55, 340, 30);
        validarDados.setBounds(30, 370, 340, 30);
        adminBox.setBounds(30, 225, 150, 30);
        comumBox.setBounds(200, 225, 100, 30);
        confirma.setBounds(30, 370, 340, 30);


        o.setIconImage(icon);
        o.add(registar);
        o.add(registar1);
        o.add(registaremail);
        o.add(registaremail1);
        o.add(registarpass);
        o.add(registarpass1);
        o.add(eliminar);
        o.add(eliminar1);
        o.add(erroDados);
        o.add(validarDados);
        o.add(confirma);
        o.add(adminBox);
        o.add(comumBox);
        validarDados.addActionListener(this);
        confirma.addActionListener(this);
        o.setSize(410, 500);
        o.setLayout(null);
        o.setVisible(true);
    }

    public void registauser() {
        confirma.setVisible(false);
        adminBox.setVisible(false);
        comumBox.setVisible(false);
        eliminar.setVisible(false);
        eliminar1.setVisible(false);
        erroDados.setVisible(false);
    }

    public void removeuser() {
        erroDados.setVisible(false);
        validarDados.setVisible(false);
        adminBox.setVisible(false);
        comumBox.setVisible(false);
        registar.setVisible(false);
        registar1.setVisible(false);
        registarpass.setVisible(false);
        registarpass1.setVisible(false);
        registaremail.setVisible(false);
        registaremail1.setVisible(false);
    }


    public void categoriaFrame() {
        b = new JFrame("MiEI Media Center");
        Image icon = Toolkit.getDefaultToolkit().getImage("C:\\DSS\\miei.jpg");
        categoria = new JLabel("Categoria");
        confirm = new JButton("Confirmar");
        rock = new JCheckBox("Rock");
        pop = new JCheckBox("Pop");
        indie = new JCheckBox("Indie");
        metal = new JCheckBox("Metal");
        pimba = new JCheckBox("Pimba");
        nome2 = new JLabel("Nome");
        confirm.setBounds(30, 370, 340, 30);

        ArrayList aux = new ArrayList(smc.biblioteca(this.usr.getNome()));
        cont = new JList(aux.toArray());
        categoria.setBounds(30, 95, 340, 30);
        rock.setBounds(30, 130, 100, 30);
        pop.setBounds(30, 160, 100, 30);
        indie.setBounds(30, 190, 100, 30);
        metal.setBounds(140, 130, 100, 30);
        pimba.setBounds(140, 160, 100, 30);
        nome2.setBounds(30, 230, 150, 30);
        cont.setBounds(30, 260, 340, 90);
        b.setIconImage(icon);
        b.add(categoria);
        b.add(rock);
        b.add(pop);
        b.add(indie);
        b.add(metal);
        b.add(pimba);
        b.add(nome2);
        b.add(cont);
        b.add(confirm);
        confirm.addActionListener(this);
        b.setSize(410, 500);
        b.setLayout(null);
        b.setVisible(true);
    }

    //reproduzir
    public void Reproduzir() {
        r = new JFrame("MiEI Media Center");
        Image icon = Toolkit.getDefaultToolkit().getImage("C:\\DSS\\miei.jpg");
        reprodm = new JLabel("Modo de Reprodução");
        cparticular = new JButton("Conteúdo particular");
        cplaylist = new JButton("Conteúdo de playlist");
        caleatorio = new JButton("Conteúdo aleatório");
        reproduz = new JButton("Reproduzir");
        shufflem = new JButton("Modo Shuffle");
        back = new JButton("Voltar");
        listaCont = new DefaultListModel<String>();
        listaCont1 = new DefaultListModel<String>();

        ArrayList<String> lista = new ArrayList<>();
        try {
            //         lista = new ArrayList<String>(this.smc.existeConteudo());
        } catch (Exception e) {
            noFrame();
        }

        ArrayList<String> lista1 = new ArrayList<>();
        try {
            //          lista1 = new ArrayList<String>(this.smc.existePlaylists(this.usr.getNome()).keySet());
        } catch (Exception e) {
            cplaylist.setVisible(false);
        }

        for (String c : lista)
            listaCont.addElement(c);

        for (String c : lista1)
            listaCont1.addElement(c);

        listaContBox = new JList<>(listaCont);
        listaContBox1 = new JList<>(listaCont1);

        reprodm.setBounds(135, 60, 340, 30);
        cparticular.setBounds(30, 100, 340, 30);
        listaContBox.setBounds(30, 140, 340, 150);
        listaContBox1.setBounds(30, 140, 340, 150);
        cplaylist.setBounds(30, 140, 340, 30);
        caleatorio.setBounds(30, 180, 340, 30);
        shufflem.setBounds(30, 300, 340, 30);
        back.setBounds(30, 370, 340, 30);
        reproduz.setBounds(30, 410, 340, 30);
        r.setIconImage(icon);
        r.add(reprodm);
        r.add(cparticular);
        r.add(listaContBox);
        r.add(cplaylist);
        r.add(caleatorio);
        r.add(shufflem);
        r.add(back);
        r.add(reproduz);
        r.add(listaContBox1);
        cparticular.addActionListener(this);
        cplaylist.addActionListener(this);
        caleatorio.addActionListener(this);
        shufflem.addActionListener(this);
        back.addActionListener(this);
        reproduz.addActionListener(this);
        r.setSize(410, 500);
        r.setLayout(null);
        r.setVisible(true);
        shufflem.setVisible(false);
        listaContBox.setVisible(false);
        listaContBox1.setVisible(false);
        reproduz.setVisible(false);
    }

    public void partFrame() {
        listaContBox.setVisible(true);
        listaContBox1.setVisible(false);
        reproduz.setVisible(true);
        cplaylist.setVisible(false);
        caleatorio.setVisible(false);
    }

    public void playlistFrame() {
        cparticular.setVisible(false);
        cplaylist.setVisible(false);
        caleatorio.setVisible(false);
        listaContBox.setVisible(false);
        listaContBox1.setVisible(true);
        shufflem.setVisible(true);
        reproduz.setVisible(true);
    }

    public void playlistrandom() {
        reproduz.setVisible(true);
    }

    public void noFrame() {
        cparticular.setVisible(false);
        cplaylist.setVisible(false);
        caleatorio.setVisible(false);
    }


    //criar playlist
    public void CriaPlaylist() {
        pl = new JFrame("MiEI Media Center");
        Image icon = Toolkit.getDefaultToolkit().getImage("C:\\DSS\\miei.jpg");
        nome = new JLabel("Nome da Playlist");
        randompl = new JButton("Playlist aleatória");
        generopl = new JButton("Playlist por Género");
        artistpl = new JButton("Playlist por Artista");
        rock1 = new JCheckBox("Rock");
        hh1 = new JCheckBox("Hip Hop");
        blues1 = new JCheckBox("Blues");
        classico1 = new JCheckBox("Clássico");
        eletronica1 = new JCheckBox("Eletrónica");
        fado1 = new JCheckBox("Fado");
        funk1 = new JCheckBox("Funk");
        grunge1 = new JCheckBox("Grunge");
        jazz1 = new JCheckBox("Jazz");
        kizomba1 = new JCheckBox("Kizomba");
        pop1 = new JCheckBox("Pop");
        reggae1 = new JCheckBox("Reggae");
        artista = new JLabel("Artista");
        confirma1 = new JButton("Confirmar");
        confirma3 = new JButton("Confirmar");
        back1 = new JButton("Voltar");
        playlistend = new JButton("Finalizar playlist");

        listaGenM = new DefaultListModel<String>();
        listaArtM = new DefaultListModel<String>();
        listaGen = new JList<>();
        listaArt = new JList<>();
        listaGen.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        listaArt.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);


        nome1 = new JTextField();
        artista1 = new JTextField();
        randompl.setBounds(30, 90, 340, 30);
        generopl.setBounds(30, 130, 340, 30);
        artistpl.setBounds(30, 170, 340, 30);
        nome.setBounds(30, 20, 150, 30);
        nome1.setBounds(30, 45, 340, 30);
        rock1.setBounds(30, 165, 100, 30);
        hh1.setBounds(30, 190, 100, 30);
        blues1.setBounds(30, 215, 100, 30);
        classico1.setBounds(30, 240, 100, 30);
        eletronica1.setBounds(30, 265, 100, 30);
        fado1.setBounds(30, 290, 100, 30);
        funk1.setBounds(150, 165, 100, 30);
        grunge1.setBounds(150, 190, 100, 30);
        jazz1.setBounds(150, 215, 100, 30);
        kizomba1.setBounds(150, 240, 100, 30);
        pop1.setBounds(150, 265, 100, 30);
        reggae1.setBounds(150, 290, 100, 30);
        artista.setBounds(30, 200, 100, 30);
        artista1.setBounds(30, 245, 340, 30);
        confirma1.setBounds(30, 370, 340, 30);
        confirma3.setBounds(30, 370, 340, 30);
        playlistend.setBounds(30, 370, 340, 30);
        back1.setBounds(30, 330, 340, 30);
        listaGen.setBounds(30, 165, 340, 155);
        listaArt.setBounds(30, 165, 340, 155);
        pl.setIconImage(icon);
        pl.add(nome);
        pl.add(nome1);
        pl.add(randompl);
        pl.add(generopl);
        pl.add(artistpl);
        pl.add(rock1);
        pl.add(hh1);
        pl.add(blues1);
        pl.add(classico1);
        pl.add(eletronica1);
        pl.add(fado1);
        pl.add(funk1);
        pl.add(grunge1);
        pl.add(jazz1);
        pl.add(kizomba1);
        pl.add(pop1);
        pl.add(reggae1);
        pl.add(artista);
        pl.add(artista1);
        pl.add(back1);
        pl.add(listaGen);
        pl.add(listaArt);
        pl.add(confirma1);
        pl.add(confirma3);
        pl.add(playlistend);

        randompl.addActionListener(this);generopl.addActionListener(this);artistpl.addActionListener(this);
        back1.addActionListener(this);
        confirma1.addActionListener(this);confirma3.addActionListener(this);
        playlistend.addActionListener(this);

        pl.setSize(410, 500);
        pl.setLayout(null);
        pl.setVisible(true);
        rock1.setVisible(false);
        hh1.setVisible(false);
        blues1.setVisible(false);
        classico1.setVisible(false);
        eletronica1.setVisible(false);
        fado1.setVisible(false);
        funk1.setVisible(false);
        grunge1.setVisible(false);
        jazz1.setVisible(false);
        kizomba1.setVisible(false);
        pop1.setVisible(false);
        reggae1.setVisible(false);
        artista.setVisible(false);
        artista1.setVisible(false);
        back1.setVisible(false);
        listaGen.setVisible(false);
        listaArt.setVisible(false);
        confirma1.setVisible(false);
        confirma3.setVisible(false);
        playlistend.setVisible(false);
        nome.setVisible(false);
        nome1.setVisible(false);
    }

    public void firstplFrame() {
        nome.setVisible(false);
        nome1.setVisible(false);
        randompl.setVisible(true);
        generopl.setVisible(true);
        artistpl.setVisible(true);
        rock1.setVisible(false);
        hh1.setVisible(false);
        blues1.setVisible(false);
        classico1.setVisible(false);
        eletronica1.setVisible(false);
        fado1.setVisible(false);
        funk1.setVisible(false);
        grunge1.setVisible(false);
        jazz1.setVisible(false);
        kizomba1.setVisible(false);
        pop1.setVisible(false);
        reggae1.setVisible(false);
        artista.setVisible(false);
        artista1.setVisible(false);
        back1.setVisible(false);
        listaGen.setVisible(false);
        listaArt.setVisible(false);
        confirma1.setVisible(false);
        confirma3.setVisible(false);
        playlistend.setVisible(false);

    }

    public void criaplFrame() {
        randompl.setVisible(true);
        generopl.setVisible(true);
        artistpl.setVisible(true);
        back1.setVisible(true);
        criaplaylist.setVisible(false);
        nome.setVisible(false);

    }

    public void generoplFrame() {
        back1.setVisible(true);
        confirma1.setVisible(true);
        rock1.setVisible(true);
        hh1.setVisible(true);
        blues1.setVisible(true);
        classico1.setVisible(true);
        eletronica1.setVisible(true);
        fado1.setVisible(true);
        funk1.setVisible(true);
        grunge1.setVisible(true);
        jazz1.setVisible(true);
        kizomba1.setVisible(true);
        pop1.setVisible(true);
        reggae1.setVisible(true);
        listaGen.setVisible(true);
        listaArt.setVisible(false);
        randompl.setVisible(false);
        artistpl.setVisible(false);
        playlistend.setVisible(false);
        nome.setVisible(true);
        nome1.setVisible(true);
        generopl.setVisible(false);
    }

    public void artistaplFrame() {
        artista.setVisible(true);
        artista1.setVisible(true);
        back1.setVisible(true);
        listaGen.setVisible(false);
        listaArt.setVisible(false);
        confirma3.setVisible(true);
        randompl.setVisible(false);
        artistpl.setVisible(false);
        generopl.setVisible(false);
        playlistend.setVisible(false);
        nome.setVisible(true);
        nome1.setVisible(true);
    }

    public void generoplFrame1() {
        nome.setVisible(false);
        nome1.setVisible(false);
        back1.setVisible(true);
        confirma1.setVisible(false);
        rock1.setVisible(false);
        hh1.setVisible(false);
        blues1.setVisible(false);
        classico1.setVisible(false);
        eletronica1.setVisible(false);
        fado1.setVisible(false);
        funk1.setVisible(false);
        grunge1.setVisible(false);
        jazz1.setVisible(false);
        kizomba1.setVisible(false);
        pop1.setVisible(false);
        reggae1.setVisible(false);
        listaGen.setVisible(true);
        listaArt.setVisible(false);
        randompl.setVisible(false);
        artistpl.setVisible(false);
        generopl.setVisible(false);
        playlistend.setVisible(true);
    }

    public void artistaplFrame1() {
        nome.setVisible(false);
        nome1.setVisible(false);
        artista.setVisible(false);
        artista1.setVisible(false);
        back1.setVisible(true);
        listaGen.setVisible(false);
        listaArt.setVisible(true);
        confirma3.setVisible(false);
        randompl.setVisible(false);
        artistpl.setVisible(false);
        generopl.setVisible(false);
        playlistend.setVisible(true);
    }
}
