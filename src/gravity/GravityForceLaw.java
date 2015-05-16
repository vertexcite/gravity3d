package gravity;

import simulation.mechanical.AccelerationHolder;
import simulation.mechanical.ForceLaw;
import javax.vecmath.Vector3d;
import vgnumerical.Utility;

/**
 * Title:        Gravity 3D
 * Description:
 * Implements the Newtonian inverse r squared gravity force law.
 * Copyright:    Copyright (c) 2002
 * Company:
 * @author Randall Britten
 * @version 1.0
 */

public class GravityForceLaw implements ForceLaw {
   public double gravityConstant = 6.6726;
   public double repulsionConstant = 0;
   public double repulsionPower = 4;
   public double repulsionLimit = 10;
   public void interactionAcceleration (PointMass g1, PointMass g2, AccelerationHolder accelerationHolder) {
      Vector3d[] workspace = {new Vector3d(), new Vector3d()};
      interactionAcceleration (g1, g2, accelerationHolder, workspace);
   }

   public void interactionAcceleration (PointMass g1, PointMass g2, AccelerationHolder accelerationHolder, Vector3d[] workspace) {

      double minImpactFreeDistanceSquared = Utility.square(g1.getRadius()+g2.getRadius());

      Vector3d displacementVector12 = workspace[0];  /** @todo must be a way not to have to construct object here */
      displacementVector12.sub(g2.position, g1.position);


      double distanceSquared = displacementVector12.lengthSquared();


      //        //Try get surfaces to be repulsion point.  Seems to add too much repuls   ion
      //        double surfaceDistance = Math.sqrt(distanceSquared) - g1.getRadius() - g2.getRadius();
      //        if (surfaceDistance <= 0) {
      //          surfaceDistance = (g1.getRadius() + g2.getRadius() ) /2.0/repulsionLimit;
      //        }
      //        //double distanceToRepulsionPower = Math.pow(surfaceDistance, repulsionPower);

      //        double distanceToRepulsionPower = Math.pow(distanceSquared, repulsionPower/2);
      //Some form of close up repulsion law


      double attractionForceMassOne = gravityConstant/distanceSquared * scaleFactor;
      //        double repulsionForce = repulsionConstant/distanceToRepulsionPower;
      double repulsionForce = 0;
      //        if (repulsionForce > 100) {
      //          repulsionForce = 100;
      //          System.out.println("" + timeElapsed + " Repulsion " + i + "," + j);
      //        }
      //
      //        if (attractionForceMassOne > 100) {
      //          attractionForceMassOne = 100;
      //          System.out.println("" + timeElapsed + " Attraction " + i + "," + j);
      //        }

      Vector3d directionVector12 = displacementVector12;
      directionVector12.normalize();

      // Desparately trying to deal with collisions.
      /**
       * The collision only affects component along directionVector12.
       * Trying something similar to damped spring.
       */
      double dampForce = 0;
      if (distanceSquared < minImpactFreeDistanceSquared) {
         repulsionForce = attractionForceMassOne;

//         collision(g1, g2);

         // This is from code for Damped spring force.
         // Relative velocity of g1 toward g2 (i.e. from g2's frame of reference)
         Vector3d relativeVelocity12 = workspace[1]; /** @todo must find way to do this without object burn */
         relativeVelocity12.sub(g1.velocity, g2.velocity);

         // Damping only works on component of velocity along the direction from g1 to g2.
         dampForce =  -dampFactor * relativeVelocity12.dot(directionVector12);

      }
      double forceMassOne = attractionForceMassOne - repulsionForce;




      // f1 is force felt by g1.  f1 = forceMassOne * m2 * m1
      // a1 is acceleration due to f1.  f1 = m1 * a1 therefore a1 = f1/m1 = forceMassOne * m2
      // Likewise for a2
      // a1 is g1AccelerationScalar and a2 is g2AccelerationScalar
      double g1AccelerationScalar = forceMassOne * g2.mass + dampForce/g1.mass;
      double g2AccelerationScalar = forceMassOne * g1.mass + dampForce/g2.mass;


      accelerationHolder.object1Acceleration.scale(g1AccelerationScalar, directionVector12);
      accelerationHolder.object2Acceleration.scale(-g2AccelerationScalar, directionVector12);


   }

//   protected void collision(PointMass g1, PointMass g2) {
//      // The reduction of the velocity aims to sap kinetic energy from the system, bit of an experiment.
//      g1.velocity.scale(0.9);
//      g2.velocity.scale(0.9);
//
//   }

   public void setGravityConstant(double newGravityConstant) {
      gravityConstant = newGravityConstant;
   }
   public double getGravityConstant() {
      return gravityConstant;
   }
   public void setRepulsionConstant(double newRepulsionConstant) {
      repulsionConstant = newRepulsionConstant;
   }
   public double getRepulsionConstant() {
      return repulsionConstant;
   }
   public void setRepulsionPower(double newRepulsionPower) {
      repulsionPower = newRepulsionPower;
   }
   public double getRepulsionPower() {
      return repulsionPower;
   }

   public void setScaleFactor(double newScaleFactor) {
      scaleFactor = newScaleFactor;
   }
   public double getScaleFactor() {
      return scaleFactor;
   }
   private double scaleFactor = 10;

   private double dampFactor = 1;
   public void setDampFactor(double newDampFactor) {
      dampFactor = newDampFactor;
   }
   public double getDampFactor() {
      return dampFactor;
   }



}