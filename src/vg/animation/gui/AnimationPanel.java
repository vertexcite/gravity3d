package vg.animation.gui;

import java.awt.event.ActionEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;

import vg.animation.AnimatorController;


/**
 * Title:        Gravity 3D
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:
 * @author Randall Britten
 * @version 1.0
 */

public class AnimationPanel extends JPanel {

   private AnimatorController animationController;
   JToggleButton jToggle_Pause = new JToggleButton();
   JTextField jTextField_AnimationFPS = new JTextField();
   JLabel jLabel3 = new JLabel();
   public AnimationPanel() {
      try {
         jbInit();
      }
      catch(Exception e) {
         e.printStackTrace();
      }
   }
   private void jbInit() throws Exception {
      jToggle_Pause.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(ActionEvent e) {
            jToggle_Pause_actionPerformed(e);
         }
      });
      jToggle_Pause.setMnemonic('P');
      jToggle_Pause.setText("Pause");
      jTextField_AnimationFPS.setToolTipText("Animation frames per second attempted");
      jTextField_AnimationFPS.setText("        20");
      jTextField_AnimationFPS.setHorizontalAlignment(SwingConstants.RIGHT);
      jTextField_AnimationFPS.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(ActionEvent e) {
            jTextField_AnimationFPS_actionPerformed(e);
         }
      });
      jLabel3.setText("Attempted frame rate");
      this.add(jToggle_Pause, null);
      this.add(jLabel3, null);
      this.add(jTextField_AnimationFPS, null);
   }
   void jToggle_Pause_actionPerformed(ActionEvent e) {
      pauseRestart();
   }
   void jTextField_AnimationFPS_actionPerformed(ActionEvent e) {
      animationController.setTimestep(1d/Double.parseDouble(jTextField_AnimationFPS.getText().trim()));
   }

   void pauseRestart() {
      if (jToggle_Pause.isSelected()) {
         animationController.pause();
         jToggle_Pause.setText("Pause Restart");
      } else {
         animationController.restart();
         jToggle_Pause.setText("Pause");
      }
   }
   public void setAnimationController(AnimatorController newAnimationController) {
      animationController = newAnimationController;
   }
   public AnimatorController getAnimationController() {
      return animationController;
   }


}