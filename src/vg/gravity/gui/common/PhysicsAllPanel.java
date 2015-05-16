package vg.gravity.gui.common;

import javax.swing.*;
import java.awt.event.*;
import java.awt.BorderLayout;

import gravity.GravityController;

/**
 * Title:        Gravity 3D
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:
 * @author Randall Britten
 * @version 1.0
 */

public class PhysicsAllPanel extends JPanel {

   private GravityController gController;
   public PhysicsAllPanel () {
      try {
         jbInit();
      }
      catch(Exception e) {
         e.printStackTrace();
      }
   }
   JFrame randomSettingsDialogue = new RandomSettings();
   NewPointMassFrame newPointMassDialogue = new NewPointMassFrame();
   private void jbInit() throws Exception {


      jButton_Random.setMnemonic('R');
      jButton_Random.setText("Random ...");
      jButton_Random.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(ActionEvent e) {
            jButton_Random_actionPerformed(e);
         }
      });

      jButton_Clear.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(ActionEvent e) {
            jButton_Clear_actionPerformed(e);
         }
      });

      jButton_OneByOne.setMnemonic('O');
      jButton_OneByOne.setText("One by One ...");
      jButton_OneByOne.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(ActionEvent e) {
            jButton_OneByOne_actionPerformed(e);
         }
      });
      this.add(jButton_Random, null);
      this.add(jButton_OneByOne, null);
      this.add(jButton_Clear, null);


      jButton_Clear.setToolTipText("Clear");
      jButton_Clear.setMnemonic('C');
      jButton_Clear.setText("Clear");

   }


   void jButton_Clear_actionPerformed(ActionEvent e) {
      gController.clear();
   }

   double randomVelocityRange;
   public void setRandomVelocityRange(double newRandomVelocityRange) {
      randomVelocityRange = newRandomVelocityRange;
   }
   public double getRandomVelocityRange() {
      return randomVelocityRange;
   }
   public void setGController(GravityController newGController) {
      gController = newGController;
      newPointMassDialogue.setGController(gController);
   }
   public GravityController getGController() {
      return gController;
   }

   private int maxMass = 10;
   JButton jButton_Random = new JButton();
   JButton jButton_OneByOne = new JButton();
   JButton jButton_Clear = new JButton();


   public class RandomSettings extends JFrame {
      JPanel jPanel1 = new JPanel();
      JCheckBox jCheckBox2D = new JCheckBox();
      JTextField jTextField_VelocityRange = new JTextField();
      JButton jButton_Random = new JButton();
      JTextField jTextField_MassRangeMax = new JTextField();
      JTextField jTextField_numberRandom = new JTextField();
      JLabel jLabel5 = new JLabel();
      JLabel jLabel4 = new JLabel();
      JPanel jPanel5 = new JPanel();
      JPanel jPanel4 = new JPanel();
      JLabel jLabel1 = new JLabel();
      JPanel jPanel3 = new JPanel();
      JPanel jPanel2 = new JPanel();

      public RandomSettings() {
         try {
            jbInit();
         }
         catch(Exception e) {
            e.printStackTrace();
         }
      }
      private void jbInit() throws Exception {
         jCheckBox2D.setText("In plane");
         jCheckBox2D.setToolTipText("Check whether random placement must only be in XY plane");
         jTextField_VelocityRange.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
               jTextField_VelocityRange_actionPerformed(e);
            }
         });
         jTextField_VelocityRange.setHorizontalAlignment(SwingConstants.RIGHT);
         jTextField_VelocityRange.setText("        5");
         jTextField_VelocityRange.setToolTipText("Velicity range for random");
         jButton_Random.setToolTipText("Create random objects");
         jButton_Random.setMnemonic('R');
         jButton_Random.setText("Random");
         jButton_Random.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
               jButton_Random_actionPerformed(e);
            }
         });
         jTextField_MassRangeMax.setText("         10");
         jTextField_MassRangeMax.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
               jTextField_MassRangeMax_actionPerformed(e);
            }
         });
         jTextField_numberRandom.setToolTipText("Number of objects to create for Random");
         jTextField_numberRandom.setText("         10");
         jTextField_numberRandom.setHorizontalAlignment(SwingConstants.RIGHT);
         jLabel5.setText("Random velocity range");
         jLabel4.setText("Number new random objects");
         jLabel1.setText("Mass range max");
         this.getContentPane().add(jPanel5, BorderLayout.NORTH);
         jPanel5.add(jPanel3, null);
         jPanel3.add(jCheckBox2D, null);
         jPanel3.add(jLabel5, null);
         jPanel3.add(jTextField_VelocityRange, null);
         jPanel5.add(jPanel4, null);
         jPanel4.add(jLabel1, null);
         jPanel4.add(jTextField_MassRangeMax, null);
         jPanel5.add(jPanel2, null);
         jPanel2.add(jLabel4, null);
         jPanel2.add(jTextField_numberRandom, null);
         jPanel5.add(jPanel1, null);
         jPanel1.add(jButton_Random, null);
         this.setSize(300,200);
      }
      void jButton_Random_actionPerformed(ActionEvent e) {
         int count = Integer.parseInt(jTextField_numberRandom.getText().trim());
         boolean inXYPlane = jCheckBox2D.isSelected();
         gController.randomSetup(count, 1, maxMass, 20, randomVelocityRange, inXYPlane);
         this.setVisible(false);
      }

      void jTextField_VelocityRange_actionPerformed(ActionEvent e) {
         randomVelocityRange = Double.parseDouble(jTextField_VelocityRange.getText().trim());
      }

      void jTextField_MassRangeMax_actionPerformed(ActionEvent e) {
         maxMass = Integer.parseInt(jTextField_MassRangeMax.getText().trim());
      }


   }

   void jButton_Random_actionPerformed(ActionEvent e) {
      randomSettingsDialogue.setVisible(true);
   }

   void jButton_OneByOne_actionPerformed(ActionEvent e) {
      newPointMassDialogue.setVisible(true);
   }



}