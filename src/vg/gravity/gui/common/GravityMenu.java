package vg.gravity.gui.common;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * Title:        Gravity 3D
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:
 * @author Randall Britten
 * @version 1.0
 */

public class GravityMenu {
   JMenuItem jMenuItem6 = new JMenuItem();
   JMenuItem jMenuItem5 = new JMenuItem();
   JMenu jMenu4 = new JMenu();
   JMenuItem jMenuItem4 = new JMenuItem();
   JMenuBar jMenuBar1 = new JMenuBar();
   JMenu jMenu3 = new JMenu();
   JMenu jMenu2 = new JMenu();
   JMenuItem jMenuItem3 = new JMenuItem();
   JMenu jMenu1 = new JMenu();
   JMenuItem jMenuItem2 = new JMenuItem();
   JMenuItem jMenuItem1 = new JMenuItem();

   public GravityMenu() {
      try {
         jbInit();
      }
      catch(Exception e) {
         e.printStackTrace();
      }
   }
   private void jbInit() throws Exception {
      jMenu4.setText("View");
      jMenu4.setMnemonic('V');
      jMenuItem5.setText("Settings");
      jMenuItem5.setMnemonic('S');
      jMenuItem6.setText("Display metrics");
      jMenuItem6.setMnemonic('D');
      jMenuItem4.setMnemonic('R');
      jMenuItem4.setText("Random");
      jMenu3.setMnemonic('P');
      jMenu3.setText("Physical");
      jMenu2.setMnemonic('A');
      jMenu2.setText("Animation");
      jMenuItem3.setMnemonic('S');
      jMenuItem3.setText("Random\'s Settings");
      jMenu1.setMnemonic('S');
      jMenu1.setText("Solver");
      jMenuItem2.setMnemonic('S');
      jMenuItem2.setText("Settings");
      jMenuItem1.setMnemonic('S');
      jMenuItem1.setText("Settings");
      jMenu4.add(jMenuItem5);
      jMenuBar1.add(jMenu1);
      jMenuBar1.add(jMenu2);
      jMenuBar1.add(jMenu3);
      jMenuBar1.add(jMenu4);
      jMenu3.add(jMenuItem3);
      jMenu3.add(jMenuItem4);
      jMenu3.add(jMenuItem6);
      jMenu2.add(jMenuItem2);
      jMenu1.add(jMenuItem1);
   }
}