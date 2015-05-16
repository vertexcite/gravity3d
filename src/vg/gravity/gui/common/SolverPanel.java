package vg.gravity.gui.common;

import javax.swing.*;
import java.awt.event.*;

import gravity.GravityController;

/**
 * Title:        Gravity 3D
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:
 * @author Randall Britten
 * @version 1.0
 */

public class SolverPanel extends JPanel {

   private GravityController gController;
   JTextField jTextField_stepSize = new JTextField();
   JLabel jLabel1 = new JLabel();
   public SolverPanel() {
      this.gController = gController;
      try {
         jbInit();
      }
      catch(Exception e) {
         e.printStackTrace();
      }
   }
   private void jbInit() throws Exception {
      jTextField_stepSize.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(ActionEvent e) {
            jTextField_stepSize_actionPerformed(e);
         }
      });
      jTextField_stepSize.setHorizontalAlignment(SwingConstants.RIGHT);
      jTextField_stepSize.setText("              0.01");
      jTextField_stepSize.setToolTipText("Step size");
      jLabel1.setText("Solver step size");
      this.add(jTextField_stepSize, null);
      this.add(jLabel1, null);
   }
   void jTextField_stepSize_actionPerformed(ActionEvent e) {
      gController.setSimulationTimeStep(Double.parseDouble(jTextField_stepSize.getText().trim()));
   }
   public void setGController(GravityController newGController) {
      gController = newGController;
   }
   public GravityController getGController() {
      return gController;
   }
}