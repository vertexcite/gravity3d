package gravity.gravity3D;


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import gravity.GravityController;
import gravity.MultiBodySimulator;
import gravity.GravityGui;

/**
 * Title:        Gravity 3D
 * Description:
 * This class is a controller for the Startup use case which is used by the
 * 3D visualisation use case.
 *
 * Copyright:    Copyright (c) 2002
 * Company:
 * @author Randall Britten
 * @version 1.0
 */

public class Gravity3DStarter {

   public void startup(GravityController gController) {
      GravityFrame3D frame = new GravityFrame3D(gController);
      gravityGui = frame;

      // When Multibody sim creates new objects, they need their 3d transforms,
      // so use the factory to create GravityObject3D subclass instances.
      // GravityFrame3D implements the GravityObjectFactory interface.
      gController.getMultiBodySimulator().setGravityObjectFactory(frame);



      //Validate frames that have preset sizes
      //Pack frames that have useful preferred size info, e.g. from their layout
      /** @todo Figure out if this bit of JBuilder code is really needed. */
      boolean packFrame = false;
      if (packFrame) {
         frame.pack();
      }
      else {
         frame.validate();
      }
      //Center the window
      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      Dimension frameSize = frame.getSize();
      if (frameSize.height > screenSize.height) {
         frameSize.height = screenSize.height;
      }
      if (frameSize.width > screenSize.width) {
         frameSize.width = screenSize.width;
      }
      frame.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
      frame.setVisible(true);
   }
   public GravityGui getGravityGui() {
      return gravityGui;
   }
   private GravityGui gravityGui;
}