package simulation.mechanical.springs;

import gravity.PointMass;
import simulation.mechanical.AccelerationHolder;
import simulation.mechanical.ForceLaw;
import javax.vecmath.Vector3d;
import vgnumerical.Utility;

/**
 * Description:
 * Models a spring system where the springs are damped in the direction of the spring.
 * Copyright:    Copyright (c) 2002
 * Company:
 * @author Randall Britten
 * @version 1.0
 */
public class SpringDampedForceLaw implements ForceLaw {

   public void interactionAcceleration(PointMass g1, PointMass g2, AccelerationHolder accelerationHolder) {
      Vector3d[] workspace = {new Vector3d(), new Vector3d()};
      interactionAcceleration (g1, g2, accelerationHolder, workspace);
   }

   public void interactionAcceleration (PointMass g1, PointMass g2, AccelerationHolder accelerationHolder, Vector3d[] workspace) {
      Vector3d displacementVector12 = workspace[0];
      displacementVector12.sub(g2.position, g1.position);

      double minImpactFreeDistanceSquared = Utility.square(g1.getRadius()+g2.getRadius());

      double distance = displacementVector12.length();

      double springForce = distance*springConstant-restDistance;

      // Desparately trying to deal with collisions.
//      System.out.println(distanceSquared + " " + minImpactFreeDistanceSquared + " " + g1.getRadius() + " " +  g2.getRadius()   + " " +  g1.position  + " " +  g2.position);
//      if (distanceSquared < minImpactFreeDistanceSquared) {
//         collision(g1, g2);
//      }

      Vector3d directionVector12 = displacementVector12;
      directionVector12.normalize();

      // Relative velocity of g1 toward g2 (i.e. from g2's frame of reference)
      Vector3d relativeVelocity12 = new Vector3d(); /** @todo there must be a way to eliminate object construction here */
      relativeVelocity12.sub(g1.velocity, g2.velocity);

      // Damping only works on component of velocity along the direction from g1 to g2.
      double dampForce =  -dampFactor * relativeVelocity12.dot(directionVector12);

      double netForce = springForce + dampForce;

      accelerationHolder.object1Acceleration.scale(netForce/g1.mass*scaleFactor, directionVector12);
      accelerationHolder.object2Acceleration.scale(-netForce/g2.mass*scaleFactor, directionVector12);


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

   private double dampFactor = 1;
   public void setDampFactor(double newDampFactor) {
      dampFactor = newDampFactor;
   }
   public double getDampFactor() {
      return dampFactor;
   }

}