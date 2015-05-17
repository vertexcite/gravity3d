package gravity.gravity2D;

import gravity.GravityController;
import gravity.GravityGui;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;

import vg.animation.gui.AnimationPanel;
import vg.gravity.gui.common.FilePanel;
import vg.gravity.gui.common.PhysicsAllPanel;
import vg.gravity.gui.common.PhysicsParametersPanel;
import vg.gravity.gui.common.SolverPanel;

public class Gravity2DFrame extends JFrame implements GravityGui {
   JPanel contentPane;
   JMenuBar jMenuBar1 = new JMenuBar();
   JMenu jMenuFile = new JMenu();
   JMenuItem jMenuFileExit = new JMenuItem();
   JMenu jMenuHelp = new JMenu();
   JMenuItem jMenuHelpAbout = new JMenuItem();
   ImageIcon image1;
   ImageIcon image2;
   ImageIcon image3;
   JLabel statusBar = new JLabel();
   BorderLayout borderLayout1 = new BorderLayout();
   Gravity2DCanvas gravity2DCanvas;

   GravityController gController;

   /**Construct the frame*/
   public Gravity2DFrame(GravityController gController) {
      this.gController = gController;
      gravity2DCanvas = new Gravity2DCanvas();
//      gController.setGravityGui(gravityCanvas11);
      enableEvents(AWTEvent.WINDOW_EVENT_MASK);
      try {
         jbInit();
      }
      catch(Exception e) {
         e.printStackTrace();
      }
      this.setVisible(true);
   }
   /**Component initialization*/
   private void jbInit() throws Exception  {
      //setIconImage(Toolkit.getDefaultToolkit().createImage(Gravity1Frame.class.getResource("[Your Icon]")));
      contentPane = (JPanel) this.getContentPane();
      contentPane.setLayout(borderLayout1);
      this.setSize(new Dimension(800, 400));
      this.setTitle("Gravity Simulation");
      statusBar.setText(" ");
      jMenuFile.setText("File");
      jMenuFileExit.setText("Exit");
      jMenuFileExit.addActionListener(new ActionListener()  {
         public void actionPerformed(ActionEvent e) {
            jMenuFileExit_actionPerformed(e);
         }
      });
      jMenuHelp.setText("Help");
      jMenuHelpAbout.setText("About");
      jMenuHelpAbout.addActionListener(new ActionListener()  {
         public void actionPerformed(ActionEvent e) {
            jMenuHelpAbout_actionPerformed(e);
         }
      });
      gravity2DCanvas.setXShift(0);
      gravity2DCanvas.setYShift(0);
      gravity2DCanvas.setGController(gController);
      gController.setAnimationTimeStep(0.02);
      //gController.setSimulationTimeStep(0.5);
      jPanel_DroppedMass.setLayout(flowLayout1);
      physicsAllPanel1.setGController(gController);
      filePanel.setGController(gController);
      jTextField_nextMass.setToolTipText("Mass of next object created.");
      jTextField_nextMass.setText("                1");
      jTextField_nextMass.setHorizontalAlignment(SwingConstants.RIGHT);
      jTextField_nextMass.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(ActionEvent e) {
            jTextField_nextMass_actionPerformed(e);
         }
      });
      solverPanel1.setGController(gController);
      jButton_ZoomIn.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(ActionEvent e) {
            jButton_ZoomIn_actionPerformed(e);
         }
      });
      jButton_ZoomIn.setToolTipText("Zoom In");
      jButton_ZoomIn.setMnemonic('=');
      jButton_ZoomIn.setText("+");
      physicsParametersPanel1.setGController(gController);
      jButton_ZoomOut.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(ActionEvent e) {
            jButton_ZoomOut_actionPerformed(e);
         }
      });
      jButton_ZoomOut.setToolTipText("Zoom out");
      jButton_ZoomOut.setMnemonic('-');
      jButton_ZoomOut.setText("-");
      animationPanel1.setAnimationController(gController);
      jLabel1.setText("Mass of next object added");
      jCheckBox_showVelocityVectors.setText("Show Velocity Vectors");
      jCheckBox_showVelocityVectors.setMnemonic('V');
      jCheckBox_showVelocityVectors.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(ActionEvent e) {
            jCheckBox_showVelocityVectors_actionPerformed(e);
         }
      });
      jCheckBox_ShowTimeElapsed.setText("Show Time Elapsed");
      jCheckBox_ShowTimeElapsed.setMnemonic('T');
      jCheckBox_ShowTimeElapsed.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(ActionEvent e) {
            jCheckBox_ShowTimeElapsed_actionPerformed(e);
         }
      });
      jCheckBox_ShowPointMassCount.setText("Show Point Mass Count");
      jCheckBox_ShowPointMassCount.setMnemonic('C');
      jCheckBox_ShowPointMassCount.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(ActionEvent e) {
            jCheckBox_ShowPointMassCount_actionPerformed(e);
         }
      });
      jMenuFile.add(jMenuFileExit);
      jMenuHelp.add(jMenuHelpAbout);
      jMenuBar1.add(jMenuFile);
      jMenuBar1.add(jMenuHelp);
      this.setJMenuBar(jMenuBar1);
      contentPane.add(statusBar, BorderLayout.SOUTH);
      contentPane.add(gravity2DCanvas, BorderLayout.CENTER);
      contentPane.add(jToolBar6, BorderLayout.NORTH);
      jToolBar6.add(jTabbedPane1, null);
      jTabbedPane1.add(jPanel_ZoomControl, "Zoom Control");
      jPanel_ZoomControl.add(jButton_ZoomIn, null);
      jPanel_ZoomControl.add(jButton_ZoomOut, null);
      jTabbedPane1.add(jPanel_DroppedMass, "Dropped Mass");
      jPanel_DroppedMass.add(jLabel1, null);
      jPanel_DroppedMass.add(jTextField_nextMass, null);
      jTabbedPane1.add(physicsAllPanel1, "Physics System");
      jTabbedPane1.add(physicsParametersPanel1, "Physics Parameters");
      jTabbedPane1.add(solverPanel1, "Numerical Solver");
      jTabbedPane1.add(animationPanel1, "Animation");
      jTabbedPane1.add(filePanel, "File");
      jTabbedPane1.add(jPanel_Guides, "Guides");
      jPanel_Guides.add(jCheckBox_showVelocityVectors, null);
      jPanel_Guides.add(jCheckBox_ShowTimeElapsed, null);
      jPanel_Guides.add(jCheckBox_ShowPointMassCount, null);

   }
   /**File | Exit action performed*/
   public void jMenuFileExit_actionPerformed(ActionEvent e) {
      System.exit(0);
   }
   /**Help | About action performed*/
   public void jMenuHelpAbout_actionPerformed(ActionEvent e) {
      Gravity1Frame_AboutBox dlg = new Gravity1Frame_AboutBox(this);
      Dimension dlgSize = dlg.getPreferredSize();
      Dimension frmSize = getSize();
      Point loc = getLocation();
      dlg.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x, (frmSize.height - dlgSize.height) / 2 + loc.y);
      dlg.setModal(true);
      dlg.show();
   }
   /**Overridden so we can exit when window is closed*/
   protected void processWindowEvent(WindowEvent e) {
      super.processWindowEvent(e);
      if (e.getID() == WindowEvent.WINDOW_CLOSING) {
         jMenuFileExit_actionPerformed(null);
      }
   }

   void jButton_Clear_actionPerformed(ActionEvent e) {
      gController.clear();
      gravity2DCanvas.repaint();
   }

   void jButton_ZoomIn_actionPerformed(ActionEvent e) {
      double z = gravity2DCanvas.getZoomFactor();
      z *= 1.1;
      gravity2DCanvas.setZoomFactor(z);
      gravity2DCanvas.repaint();
   }

   void jButton_ZoomOut_actionPerformed(ActionEvent e) {
      double z = gravity2DCanvas.getZoomFactor();
      z /= 1.1;
      gravity2DCanvas.setZoomFactor(z);
      gravity2DCanvas.repaint();
   }

   void jTextField_nextMass_actionPerformed(ActionEvent e) {
      try {
         gravity2DCanvas.setNextMass(Double.parseDouble(jTextField_nextMass.getText() ));
      } catch (NumberFormatException ex) {
         jTextField_nextMass.setText("" + gravity2DCanvas.getNextMass() );
      }
   }

//   void jButton_Random_actionPerformed(ActionEvent e) {
//
//      paused = true;
//      pauseRestart();
//
//      gController.randomSetup2D(70, 1, 2, 200, 1.0);
//      gravity2DCanvas.setXShift(0);
//      gravity2DCanvas.setYShift(0);
//   }

//   void jToggle_Pause_actionPerformed(ActionEvent e) {
//      pauseRestart();
//   }

   private boolean paused = false;


//   void pauseRestart() {
//      paused = !paused;
//      if (paused) {
//         gController.pauseAnimation();
//         jToggle_Pause.setText("Pause Restart");
//         jToggle_Pause.setSelected(true);
//
//      } else {
//         gController.restartAnimation();
//         jToggle_Pause.setText("Pause");
//         jToggle_Pause.setSelected(false);
//      }
//   }


   List toolbars = new ArrayList();
   int toolbarCount;
   JToolBar jToolBar6 = new JToolBar();
   JPanel jPanel_DroppedMass = new JPanel();
   PhysicsAllPanel physicsAllPanel1 = new PhysicsAllPanel();
   JTextField jTextField_nextMass = new JTextField();
   JTabbedPane jTabbedPane1 = new JTabbedPane();
   SolverPanel solverPanel1 = new SolverPanel();
   FlowLayout flowLayout1 = new FlowLayout();
   JButton jButton_ZoomIn = new JButton();
   PhysicsParametersPanel physicsParametersPanel1 = new PhysicsParametersPanel();
   JButton jButton_ZoomOut = new JButton();
   AnimationPanel animationPanel1 = new AnimationPanel();
   JLabel jLabel1 = new JLabel();
   JPanel jPanel_ZoomControl = new JPanel();
   FilePanel filePanel = new FilePanel();
   JPanel jPanel_Guides = new JPanel();
   JCheckBox jCheckBox_showVelocityVectors = new JCheckBox();
   JCheckBox jCheckBox_ShowTimeElapsed = new JCheckBox();
   JCheckBox jCheckBox_ShowPointMassCount = new JCheckBox();


   public void clear() {
      // Do nothing, this renderer clears for every frame.
   }

   public void updateFrame() {
      gravity2DCanvas.repaint();
   }

   void jCheckBox_showVelocityVectors_actionPerformed(ActionEvent e) {
      gravity2DCanvas.setShowVelocityVectors(jCheckBox_showVelocityVectors.isSelected() );
   }

   void jCheckBox_ShowPointMassCount_actionPerformed(ActionEvent e) {
      gravity2DCanvas.setShowPointMassCount(jCheckBox_ShowPointMassCount.isSelected());
   }

   void jCheckBox_ShowTimeElapsed_actionPerformed(ActionEvent e) {
      gravity2DCanvas.setShowTimeElapsed(jCheckBox_ShowTimeElapsed.isSelected());
   }


}