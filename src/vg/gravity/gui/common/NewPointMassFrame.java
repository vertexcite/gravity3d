package vg.gravity.gui.common;

import gravity.GravityController;
import gravity.PointMass;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;


/**
 * Title:        Gravity 3D
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:
 * @author Randall Britten
 * @version 1.0
 */

public class NewPointMassFrame extends JFrame {
   JPanel jPanel1 = new JPanel();
   GridLayout gridLayout1 = new GridLayout();
   JLabel jLabel1 = new JLabel();
   JTextField jTextField_yPosi = new JTextField();
   JTextField jTextField_xPosi = new JTextField();
   JLabel jLabel3 = new JLabel();
   JTextField jTextField_zPosi = new JTextField();
   JLabel jLabel4 = new JLabel();
   JTextField jTextField_xVelocity = new JTextField();
   JLabel jLabel5 = new JLabel();
   JTextField jTextField_PointMassLabel = new JTextField();
   JLabel jLabel2 = new JLabel();
   JLabel jLabel6 = new JLabel();
   JTextField jTextField_yVelocity = new JTextField();
   JLabel jLabel7 = new JLabel();
   JTextField jTextField_zVelocity = new JTextField();
   JLabel jLabel8 = new JLabel();
   JTextField jTextField_Mass = new JTextField();
   JLabel jLabel9 = new JLabel();
   JTextField jTextField_Radius = new JTextField();
   JPanel jPanel2 = new JPanel();
   JButton jButton_Add = new JButton();

   public NewPointMassFrame() {
      try {
         jbInit();
      }
      catch(Exception e) {
         e.printStackTrace();
      }
   }
   private void jbInit() throws Exception {
      jPanel1.setLayout(gridLayout1);
      jLabel1.setText("Label");
      gridLayout1.setRows(9);
      gridLayout1.setColumns(2);
      jTextField_yPosi.setText("0");
      jTextField_xPosi.setText("0");
      jLabel3.setText("Y position");
      jTextField_zPosi.setText("0");
      jLabel4.setText("Z position");
      jLabel5.setText("X velocity");
      jTextField_PointMassLabel.setText("A Point Mass");
      jLabel2.setText("X position");
      jLabel6.setText("Y velocity");
      jTextField_yVelocity.setText("0");
      jLabel7.setText("Z velocity");
      jTextField_zVelocity.setText("0");
      jLabel8.setText("Mass");
      jTextField_Mass.setText("1");
      jLabel9.setText("Radius");
      jTextField_Radius.setText("1");
      jButton_Add.setMnemonic('A');
      jButton_Add.setText("Add");
      jButton_Add.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(ActionEvent e) {
            jButton_Add_actionPerformed(e);
         }
      });
      jTextField_xVelocity.setText("0");
      jCheckBox_KeepOpen.setText("Don\'t close after add");
      this.getContentPane().add(jPanel1, BorderLayout.CENTER);
      jPanel1.add(jLabel1, null);
      jPanel1.add(jTextField_PointMassLabel, null);
      jPanel1.add(jLabel2, null);
      jPanel1.add(jTextField_xPosi, null);
      jPanel1.add(jLabel3, null);
      jPanel1.add(jTextField_yPosi, null);
      jPanel1.add(jLabel4, null);
      jPanel1.add(jTextField_zPosi, null);
      jPanel1.add(jLabel5, null);
      jPanel1.add(jTextField_xVelocity, null);
      jPanel1.add(jLabel6, null);
      jPanel1.add(jTextField_yVelocity, null);
      jPanel1.add(jLabel7, null);
      jPanel1.add(jTextField_zVelocity, null);
      jPanel1.add(jLabel8, null);
      jPanel1.add(jTextField_Mass, null);
//      jPanel1.add(jLabel9, null);
//      jPanel1.add(jTextField_Radius, null);
      this.getContentPane().add(jPanel2, BorderLayout.SOUTH);
      jPanel2.add(jButton_Add, null);
      jPanel2.add(jCheckBox_KeepOpen, null);
      this.setSize(300,300);
   }

   void jButton_Add_actionPerformed(ActionEvent e) {
      double mass = Double.parseDouble(jTextField_Mass.getText());

      double xPosi = Double.parseDouble(jTextField_xPosi.getText());
      double yPosi = Double.parseDouble(jTextField_yPosi.getText());
      double zPosi = Double.parseDouble(jTextField_zPosi.getText());
      Point3d position = new Point3d(xPosi, yPosi, zPosi);

      double xVel = Double.parseDouble(jTextField_xVelocity.getText());
      double yVel = Double.parseDouble(jTextField_yVelocity.getText());
      double zVel = Double.parseDouble(jTextField_zVelocity.getText());
      Vector3d velocity = new Vector3d(xVel, yVel, zVel);

      PointMass g = new PointMass(mass, position, velocity);

      gController.addPointMass(g);

      setVisible(jCheckBox_KeepOpen.isSelected());
   }

   GravityController gController;
   JCheckBox jCheckBox_KeepOpen = new JCheckBox();
   public void setGController(GravityController newGController) {
      gController = newGController;
   }

}