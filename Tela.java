import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;

public class Tela extends JFrame implements ActionListener {
   private JTextField txttemperatura;
   private JLabel lbltemperatura;
   private JButton btnResultado, btnCarregar, btnSair;
   
   public Tela(){
      super ("Temperaturas");
      JPanel painelDadosEntrada = new JPanel (new GridLayout (1, 1));
      JPanel painelEntrada = new JPanel (new FlowLayout());
      txttemperatura = new JTextField(10);
      lbltemperatura = new JLabel("QuantasTemperaturas?"); 
      painelDadosEntrada.add(lbltemperatura);
      painelDadosEntrada.add(txttemperatura);
      painelEntrada.add(painelDadosEntrada);
      JPanel painelBotoes = new JPanel(new FlowLayout());     
      btnCarregar = new JButton("Carregar");
      btnResultado = new JButton("Resultado");
      btnSair = new JButton("Sair");
      painelBotoes.add(btnCarregar);
      painelBotoes.add(btnResultado);
      painelBotoes.add(btnSair);
      Container caixa = getContentPane();
      caixa.setLayout(new BorderLayout());
      caixa.add(painelEntrada, BorderLayout.WEST);
      caixa.add(painelBotoes, BorderLayout.SOUTH);
      btnResultado.addActionListener(this);
      btnCarregar.addActionListener(this);
      btnSair.addActionListener(this);
      setSize(600, 300);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setLocationRelativeTo(null);
      setVisible(true);
      }
      public void actionPerformed(ActionEvent e) {
      if (e.getSource() == btnCarregar) {
      try{
      ConexaoBD b = new ConexaoBD();
      Connection conn = b.conectar();
      Temperatura t = new Temperatura();
      String sqlInsert = "SELECT valor FROM TEMPERATURA WHERE id <= ?";
      try(PreparedStatement ps = conn.prepareStatement(sqlInsert)){
         int id = Integer.parseInt(txttemperatura.getText());
         t.setId(id);
         ps.setInt(1, t.getId());
         ResultSet rs = ps.executeQuery();
         String aux = "";
         while(rs.next()) {
         double valor = rs.getDouble("valor");
         aux += String.format("Temperatura: %f-", valor);
         txttemperatura.setText(aux);
         }
         Termometro te = new Termometro();
           //txttemperatura.setText(te.media(t[id])); 
           //txttemperatura.setText(te.maior(t[id]));
           //txttemperatura.setText(te.menor(t[id]));  
         }       
            
       }catch(Exception e1){
      System.out.println("Erro ao Acessar o Banco");
      
      }
      
      } else if (e.getSource() == btnResultado) {
      String texto = txttemperatura.getText();
      JOptionPane.showMessageDialog(null, texto);
 
      
      }else {
      System.exit(0);
      }
      }
      }
      
      
      