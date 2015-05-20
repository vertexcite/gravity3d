package gravity.gravity2D;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Gravity1Frame_AboutBox extends JDialog implements ActionListener {

   JPanel panel1 = new JPanel();
   JPanel panel2 = new JPanel();
   JPanel insetsPanel1 = new JPanel();
   JPanel insetsPanel2 = new JPanel();
   JPanel insetsPanel3 = new JPanel();
   JButton button1 = new JButton();
   JLabel imageLabel = new JLabel();
   JLabel label1 = new JLabel();
   JLabel label2 = new JLabel();
   JLabel label3 = new JLabel();
   JLabel label4 = new JLabel();
   JLabel label5 = new JLabel();
   BorderLayout borderLayout1 = new BorderLayout();
   BorderLayout borderLayout2 = new BorderLayout();
   FlowLayout flowLayout1 = new FlowLayout();
   GridLayout gridLayout1 = new GridLayout();
   String product = "Gravity Simulation";
   String version = "v0.1.1 alpha";
   String copyright = "Copyright (c) 2001-2015";
   String comments = "";
   public Gravity1Frame_AboutBox(Frame parent) {
      super(parent);
      enableEvents(AWTEvent.WINDOW_EVENT_MASK);
      try {
         jbInit();
      }
      catch(Exception e) {
         e.printStackTrace();
      }
      pack();
   }
   /**Component initialization*/
   private void jbInit() throws Exception  {
      //imageLabel.setIcon(new ImageIcon(Gravity1Frame_AboutBox.class.getResource("[Your Image]")));
      this.setTitle("About");
      setResizable(false);
      panel1.setLayout(borderLayout1);
      panel2.setLayout(borderLayout2);
      insetsPanel1.setLayout(flowLayout1);
      insetsPanel2.setLayout(flowLayout1);
      insetsPanel2.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
      gridLayout1.setRows(5);
      gridLayout1.setColumns(1);
      label1.setText(product);
      label2.setText("By Randall Britten"); // TODO Centralise this info (and copyright etc).
      label3.setText("Copyright (c) 2001-2015");
      label4.setText("Version: " + version);
      label5.setText("github.com/vertexcite");
      insetsPanel3.setLayout(gridLayout1);
      insetsPanel3.setBorder(BorderFactory.createEmptyBorder(10, 60, 10, 10));
      button1.setText("Ok");
      button1.addActionListener(this);
      insetsPanel2.add(imageLabel, null);
      panel2.add(insetsPanel2, BorderLayout.WEST);
      this.getContentPane().add(panel1, null);
      insetsPanel3.add(label1, null);
      insetsPanel3.add(label2, null);
      insetsPanel3.add(label3, null);
      insetsPanel3.add(label4, null);
      insetsPanel3.add(label5, null);
      panel2.add(insetsPanel3, BorderLayout.CENTER);
      insetsPanel1.add(button1, null);
      panel1.add(insetsPanel1, BorderLayout.SOUTH);
      panel1.add(panel2, BorderLayout.NORTH);
   }
   /**Overridden so we can exit when window is closed*/
   protected void processWindowEvent(WindowEvent e) {
      if (e.getID() == WindowEvent.WINDOW_CLOSING) {
         cancel();
      }
      super.processWindowEvent(e);
   }
   /**Close the dialog*/
   void cancel() {
      dispose();
   }
   /**Close the dialog on a button event*/
   public void actionPerformed(ActionEvent e) {
      if (e.getSource() == button1) {
         cancel();
      }
   }
}