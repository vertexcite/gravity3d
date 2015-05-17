package vg.gravity.gui.common;

import gravity.GravityController;

import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JPanel;

import vg.gravity.file.LoadSaveController;


/**
 * Title:        Gravity 3D
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:
 * @author Randall Britten
 * @version 1.0
 */

public class FilePanel extends JPanel {
   JButton jButton_Save = new JButton();
   JButton jButton_Load = new JButton();
   private GravityController gController;
   private LoadSaveController loadSaveController = new LoadSaveController();

   public FilePanel() {
      try {
         jbInit();
      }
      catch(Exception e) {
         e.printStackTrace();
      }
   }
   private void jbInit() throws Exception {
      jButton_Save.setMnemonic('S');
      jButton_Save.setText("Save");
      jButton_Save.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(ActionEvent e) {
            jButton_Save_actionPerformed(e);
         }
      });
      jButton_Load.setMnemonic('L');
      jButton_Load.setText("Load");
      jButton_Load.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(ActionEvent e) {
            jButton_Load_actionPerformed(e);
         }
      });
      this.add(jButton_Load, null);
      this.add(jButton_Save, null);
   }
   public void setGController(gravity.GravityController newGController) {
      gController = newGController;
   }
   public gravity.GravityController getGController() {
      return gController;
   }

   void jButton_Load_actionPerformed(ActionEvent e) {
      try {
         loadSaveController.load(gController);
      }
      catch (FileNotFoundException ex) {
         System.out.println("File not found");
         ex.printStackTrace();
      }
      catch (IOException ex) {
         System.out.println("IO Problem");
         ex.printStackTrace();
      }
   }

   void jButton_Save_actionPerformed(ActionEvent e) {
      try {
          loadSaveController.save(gController);
      }
      catch (FileNotFoundException ex) {
         System.out.println("File not found");
         ex.printStackTrace();
      }

   }
}