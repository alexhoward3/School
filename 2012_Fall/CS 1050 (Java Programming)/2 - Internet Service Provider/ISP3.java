import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ISP3{
  JLabel label;
  public static void main(String[] args){
  ISP3 isp3 = new ISP3();
  }

  public ISP3(){
  JFrame frame = new JFrame("Radio button selection");
  JRadioButton packA = new JRadioButton("Package A");
  JRadioButton packB = new JRadioButton("Package B");
  JRadioButton packC = new JRadioButton("Package C");
  
  JPanel panel = new JPanel();
  panel.add(packA);
  panel.add(packB);
  panel.add(packC);

  ButtonGroup bg = new ButtonGroup();
  bg.add(packA);
  bg.add(packB);
  bg.add(packC);

  packA.addActionListener(new MyAction());
  packB.addActionListener(new MyAction());
  packC.addActionListener(new MyAction());

  frame.add(panel, BorderLayout.NORTH);

  frame.setSize(400, 400);
  frame.setVisible(true);
  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  public class MyAction implements ActionListener{
  public void actionPerformed(ActionEvent e){
  label.setText(e.getActionCommand());
  JOptionPane.showMessageDialog(null,"This is the " + e.getActionCommand() + 
" radio button.");
  }
  }
}