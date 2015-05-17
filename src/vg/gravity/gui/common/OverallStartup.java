package vg.gravity.gui.common;

import gravity.GravityController;
import gravity.GravityGui;
import gravity.gravity2D.Gravity2DStarter;
import gravity.gravity3D.Gravity3DStarter;

import java.lang.reflect.InvocationTargetException;

import javax.swing.SwingUtilities;

import vg.gravity.GravityModelStartup;

/**
 * Title:        Gravity 3D
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:
 * @author Randall Britten
 * @version 1.0
 */

public class OverallStartup {

   public static void main(String[] args) throws InvocationTargetException, InterruptedException {
      GravityModelStartup gravityModelStartup = new GravityModelStartup();
      gravityModelStartup.startup();
      GravityController gController = gravityModelStartup.getGravityController();

      final boolean spaceIs3d = (args.length > 0 && args[0].equals("3D"));
      SwingUtilities.invokeAndWait(new Runnable() {
  	    public void run() {
	      if (spaceIs3d) {
	
	         Gravity3DStarter gravity3DStarter = new Gravity3DStarter();
	         gravity3DStarter.startup(gController);
	         GravityGui gravityGui = gravity3DStarter.getGravityGui();
	         gController.setGravityGui(gravityGui);
	      }
	      else {
	         Gravity2DStarter gravity2DStarter = new Gravity2DStarter();
	         gravity2DStarter.startup(gController);
	         GravityGui gravityGui = gravity2DStarter.getGravityGui();
	         gController.setGravityGui(gravityGui);
          }
        }
      });


      // Start off with a random cluster of 10 objects.
      gController.randomSetup(100,1,10,20,10,!spaceIs3d);
      gController.startAnimatorThread();

   }
}