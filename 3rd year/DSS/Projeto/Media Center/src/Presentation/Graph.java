package smc.Presentation;
import smc.Business.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Graph implements ActionListener {
    private SMC smc;

    private JFrame f;
    private JLabel ti,imageLabel,user,pass,er;
    private ImageIcon image;
    private JTextField usert;
    private JPasswordField passt;
    private JButton log,con,back,log1,close;


    public Graph(SMC s){
        this.smc = s;

        f=new JFrame("MiEI Media Center");
        Image icon = Toolkit.getDefaultToolkit().getImage("C:\\DSS\\miei.jpg");
        image = new ImageIcon("C:\\DSS\\miei.jpg");
        imageLabel = new JLabel(image);
        ti=new JLabel("MiEI Media Center");
        log = new JButton("Login");
        con = new JButton("Aceder como convidado");
        close = new JButton("Sair");
        log1 = new JButton("Login");
        back = new JButton("Voltar");
        user = new JLabel("Usuário:");
        pass = new JLabel("Password:");
        usert = new JTextField();
        passt = new JPasswordField();
        er = new JLabel("Dados inválidos.");

        ti.setBounds(125,20, 200,80);
        log.setBounds(80,235,200,30);
        con.setBounds(80,270,200,30);
        close.setBounds(80,305,200,30);
        log1.setBounds(80,310,90,30);
        back.setBounds(182,310,90,30);
        user.setBounds(80,235,200,30);
        pass.setBounds(80,270,200,30);
        usert.setBounds(150,237, 120,30);
        passt.setBounds(150,272, 120,30);
        imageLabel.setBounds(80,80, 200,140);
        er.setBounds(130,315, 200,80);
        f.setIconImage(icon);
        f.add(ti);f.add(log);f.add(con);f.add(close);f.add(imageLabel); f.add(er);
        f.add(log1);f.add(back);f.add(user);f.add(pass);f.add(usert);f.add(passt);
        log.addActionListener(this);
        con.addActionListener(this);
        close.addActionListener(this);
        log1.addActionListener(this);
        back.addActionListener(this);
        f.setSize(390,450);
        f.setLayout(null);
        f.setVisible(true);
        back.setVisible(false);
        log1.setVisible(false);
        user.setVisible(false);
        pass.setVisible(false);
        usert.setVisible(false);
        passt.setVisible(false);
        er.setVisible(false);
    }
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == log) {
            logFrame();
        } else if (e.getSource() == con) {
            f.dispose();
            GraphUser convframe = new GraphUser(this.smc, new Administrador());
            convframe.convFrame();
        } else if (e.getSource() == close){
            f.dispose();
        }else if (e.getSource() == log1){

            String user = usert.getText();
            String pass = String.valueOf(passt.getPassword());

            try {
                // Utilizador usr = smc.iniciarSessao(user, pass);
                f.dispose();
                GraphUser userframe = new GraphUser(this.smc, new Administrador());
                // if(usr instanceof Administrador)
                userframe.userFrame();
                //  else
                //userframe.adminFrame();
            }
            catch (Exception ex) {
                this.er.setVisible(true);
            }


        } else if (e.getSource() == back){
            backFrame();
        }
    }

    public void logFrame() {
        log.setVisible(false);
        con.setVisible(false);
        close.setVisible(false);
        back.setVisible(true);
        log1.setVisible(true);
        user.setVisible(true);
        pass.setVisible(true);
        usert.setVisible(true);
        passt.setVisible(true);
    }
    public void backFrame() {
        log.setVisible(true);
        con.setVisible(true);
        close.setVisible(true);
        back.setVisible(false);
        log1.setVisible(false);
        user.setVisible(false);
        pass.setVisible(false);
        usert.setVisible(false);
        passt.setVisible(false);
        er.setVisible(false);
    }
}

