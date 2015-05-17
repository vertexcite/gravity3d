package gravity.gravity2D;

import gravity.GravityController;
import gravity.GravityGui;

import java.awt.Dimension;
import java.awt.Toolkit;

public class Gravity2DStarter {



   public void startup(GravityController gController) {


      Gravity2DFrame frame = new Gravity2DFrame(gController);
      gravityGui = frame;

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