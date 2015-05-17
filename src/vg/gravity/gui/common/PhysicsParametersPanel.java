package vg.gravity.gui.common;

import gravity.GravityController;
import gravity.GravityForceLaw;

import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import simulation.mechanical.ForceLaw;
import simulation.mechanical.springs.SpringDampedForceLaw;



/**
 * Title:        Gravity 3D
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:
 * @author Randall Britten
 * @version 1.0
 */

public class PhysicsParametersPanel extends JPanel {

   private boolean usingGravity = true;
   private ForceLaw gravityForceLaw = new GravityForceLaw();
   private ForceLaw springForceLaw1 = new SpringDampedForceLaw();
   private ForceLaw currentForceLaw = springForceLaw1;



   private GravityController gController;
   JTextField jText_ScaleFactor = new JTextField();
   JButton jButton_SwitchForce = new JButton();
   JButton jButton_coMove = new JButton();
   JLabel jLabel2 = new JLabel();
   public PhysicsParametersPanel() {
      this.gController = gController;
      try {
         jbInit();
      }
      catch(Exception e) {
         e.printStackTrace();
      }
   }
   private void jbInit() throws Exception {
      jText_ScaleFactor.setToolTipText("Force law scale factor");
      jText_ScaleFactor.setText("           0.1");
      jText_ScaleFactor.setHorizontalAlignment(SwingConstants.RIGHT);
      jText_ScaleFactor.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(ActionEvent e) {
            jText_ScaleFactor_actionPerformed(e);
         }
      });
      jButton_SwitchForce.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(ActionEvent e) {
            jButton_SwitchForce_actionPerformed(e);
         }
      });
      jButton_SwitchForce.setText("Switch Force");
      jButton_SwitchForce.setMnemonic('F');
      jButton_coMove.setActionCommand("jButton_CoMove");
      jButton_coMove.setMnemonic('M');
      jButton_coMove.setText("Co Move");
      jButton_coMove.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(ActionEvent e) {
            jButton_coMove_actionPerformed(e);
         }
      });
      jLabel2.setText("Force scale factor");
      this.add(jLabel2, null);
      this.add(jText_ScaleFactor, null);
      this.add(jButton_SwitchForce, null);
      this.add(jButton_coMove, null);
   }
   void jButton_SwitchForce_actionPerformed(ActionEvent e) {
      usingGravity = !usingGravity;
      if (usingGravity) {
         currentForceLaw = gravityForceLaw;
         gController.getMultiBodySimulator().setForceLaw(gravityForceLaw);
         jButton_SwitchForce.setText("Force to spring");
      } else {
         currentForceLaw = springForceLaw1;
         gController.getMultiBodySimulator().setForceLaw(springForceLaw1);
         jButton_SwitchForce.setText("Force to gravity");
      }
         jText_ScaleFactor.setText("" + currentForceLaw.getScaleFactor());
   }
   void jButton_coMove_actionPerformed(ActionEvent e) {
      gController.getMultiBodySimulator().coMove();
   }

   void jText_ScaleFactor_actionPerformed(ActionEvent e) {
      currentForceLaw.setScaleFactor(Double.parseDouble(jText_ScaleFactor.getText().trim()));
   }
   public void setGController(GravityController newGController) {
      gController = newGController;
   }
   public GravityController getGController() {
      return gController;
   }

}