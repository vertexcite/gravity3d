package gravity.gravity3D;

import gravity.*;

import javax.media.j3d.*;
import com.sun.j3d.utils.universe.SimpleUniverse;
import javax.vecmath.Vector3d;


/**
 * This is not actually a real graphic canvas at all, for Java3D,
 * all it does is manipulate the transforms of the Java3D objects
 * that are already in the scene graph.
 * The Java3D Canvas3D object has its own rendering scheduling,
 * and when it wants to, it will re-render, given that the transforms
 * have changed, the new positions will be displayed.
 */
public class GravityCanvas3D {

   public void repaint() {
      //      System.out.println("Here in GravityCanvas3D.repaint()");
      MultiBodySimulator multibodySim = gController.getMultiBodySimulator();
      java.util.List gs = multibodySim.getPointMasses();


      Vector3d currentVector = new Vector3d();
      Transform3D currentTransform = new Transform3D();
      for (int i=0; i<gs.size(); i++) {
         PointMass3D go = (PointMass3D)gs.get(i);
         //         double gRad = go.getRadius();

         // Set object position
         TransformGroup currentTG = go.getTransformGroup();
         currentTG.getTransform(currentTransform);
         currentTransform.get(currentVector);
         currentVector.set(go.position.x, go.position.y, go.position.z);
         currentTransform.set(currentVector);
         currentTG.setTransform(currentTransform);

         //         System.out.println("3DGO: " + i + " posi x,y,z: " + go.position.x + "," + go.position.y + "," + go.position.z);

         // Draw velocity Vector
      }

      //      super.repaint();
   }

   GravityController gController;


   public GravityCanvas3D(GravityController gController) {
      this.gController = gController;
   }

   public void clear() {

   }
}