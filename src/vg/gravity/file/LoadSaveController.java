package vg.gravity.file;

import gravity.GravityController;
import gravity.PointMass;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

/**
 * Title:        Gravity 3D
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:
 * @author Randall Britten
 * @version 1.0
 */

public class LoadSaveController {

   public void load(GravityController gController) throws FileNotFoundException, IOException {
      System.out.println("Loading");
      boolean wasPaused = gController.isPaused();
      gController.pause();
      gController.clear();
      BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("gravity.dat")));
      String line = br.readLine();
      int lineCount = 0;
      double value = 0;
      PointMass pm = new PointMass();
      while (line != null) {
         value = Double.parseDouble(line);
         switch (lineCount%7) {
            case 0:
               pm.position.x = value;
               break;
            case 1:
               pm.position.y = value;
               break;
            case 2:
               pm.position.z = value;
               break;
            case 3:
               pm.velocity.x = value;
               break;
            case 4:
               pm.velocity.y = value;
               break;
            case 5:
               pm.velocity.z = value;
               break;
            case 6:
               pm.mass = value;
               gController.addPointMass(pm);
               pm = new PointMass();
               break;
            default:
               break;
         }

         line = br.readLine();
         lineCount++;
      }
      br.close();
      if (!wasPaused) {
         gController.restart();
      }
      System.out.println("Loaded, lines read: " + lineCount);
      System.out.println("Simulation point massses: " + gController.getMultiBodySimulator().getPointMasses().size());
   }

   public void save(GravityController gController) throws FileNotFoundException {
      boolean wasPaused = gController.isPaused();
      gController.pause();
//      System.out.println("Saving");
      List gMasses = gController.getMultiBodySimulator().getPointMasses();
      System.out.println("Size: " + gMasses.size() );

      Iterator it = gMasses.iterator();
      FileOutputStream fos = new FileOutputStream("gravity.dat");
      PrintWriter pw = new PrintWriter(fos);
//      pw = new PrintWriter(System.out);

      while (it.hasNext() ) {
         PointMass pm = (PointMass)it.next();
         pw.println(pm.position.x);
         pw.println(pm.position.y);
         pw.println(pm.position.z);

         pw.println(pm.velocity.x);
         pw.println(pm.velocity.y);
         pw.println(pm.velocity.z);

         pw.println(pm.mass);

      }
      pw.close();

      if (!wasPaused) {
         gController.restart();
      }




   }
}







