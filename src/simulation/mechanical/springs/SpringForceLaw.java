package simulation.mechanical.springs;

import gravity.PointMass;
import simulation.mechanical.AccelerationHolder;
import simulation.mechanical.ForceLaw;
import javax.vecmath.Vector3d;
import javax.vecmath.Point3d;
import vgnumerical.Utility;

/**
 * Title:        Gravity 3D
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:
 * @author Randall Britten
 * @version 1.0
 */

public class SpringForceLaw implements ForceLaw {
   public void interactionAcceleration(PointMass g1, PointMass g2, AccelerationHolder accelerationHolder) {
      Vector3d[] workspace = {new Vector3d(), new Vector3d()};
      interactionAcceleration (g1, g2, accelerationHolder, workspace);
   }

   public void interactionAcceleration (PointMass g1, PointMass g2, AccelerationHolder accelerationHolder, Vector3d[] workspace) {
      /**
       * @todo There must be a way of avoiding constructing a vector here.
       */
      Vector3d displacementVector12 = workspace[0];
      displacementVector12.sub(g2.position, g1.position);

      double minImpactFreeDistanceSquared = Utility.square(g1.getRadius()+g2.getRadius());

//      double distanceSquared = displacementVector12.lengthSquared();
//      double distance = Math.sqrt(distanceSquared);
      double distance = g2.position.distance(g1.position);
      double force = distance*springConstant-restDistance;

      // Desparately trying to deal with collisions.
//      System.out.println(distanceSquared + " " + minImpactFreeDistanceSquared + " " + g1.getRadius() + " " +  g2.getRadius()   + " " +  g1.position  + " " +  g2.position);

//      if (distanceSquared < minImpactFreeDistanceSquared) {
//         collision(g1, g2);
//      }

//      Vector3d directionVector12 = displacementVector12.scale(1/Math.sqrt(distanceSquared));
      Vector3d directionVector12 = displacementVector12;
      directionVector12.normalize();

//      accelerationHolder.object1Acceleration = directionVector12.scale(force/g1.mass*scaleFactor);
//      accelerationHolder.object2Acceleration = directionVector12.scale(-force/g2.mass*scaleFactor);
      accelerationHolder.object1Acceleration.scale(force/g1.mass*scaleFactor, directionVector12);
      accelerationHolder.object1Acceleration.scale(-force/g2.mass*scaleFactor, directionVector12);




   }


   private int collCount =0;
   protected void collision(PointMass g1, PointMass g2) {
      // The reduction of the felocity aims to sap kinetic energy from the system, bit of an experiment.
//      System.out.println("Collision: " + ++collCount);
      g1.velocity.scale(0.8);
      g2.velocity.scale(0.8);

   }


   public void setScaleFactor(double newScaleFactor) {
      scaleFactor = newScaleFactor;
   }
   public double getScaleFactor() {
      return scaleFactor;
   }
   public void setRestDistance(double newRestDistance) {
      restDistance = newRestDistance;
   }
   public double getRestDistance() {
      return restDistance;
   }
   public void setSpringConstant(double newSpringConstant) {
      springConstant = newSpringConstant;
   }
   public double getSpringConstant() {
      return springConstant;
   }
   private double scaleFactor = 0.002;
   private double restDistance = 5;
   private double springConstant = 1;
}