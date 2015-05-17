package simulation.mechanical.springs;

import gravity.PointMass;
import simulation.mechanical.AccelerationHolder;

/**
 * Models a spring system in a medium that damps velocity in any direction.
 */
public class SpringViscousMediumForceLaw extends SpringForceLaw {

   private double dampFactor = 0.01;
   public void interactionAcceleration(PointMass g1, PointMass g2, AccelerationHolder accelerationHolder) {
      super.interactionAcceleration(g1, g2, accelerationHolder);
//      Vector3d damp1 = g1.velocity.scale(-dampFactor);
//      Vector3d damp2 = g2.velocity.scale(-dampFactor);
//      accelerationHolder.object1Acceleration = accelerationHolder.object1Acceleration.add(damp1);
//      accelerationHolder.object2Acceleration = accelerationHolder.object2Acceleration.add(damp2);
      accelerationHolder.object1Acceleration.scaleAdd(-dampFactor, g1.velocity, accelerationHolder.object1Acceleration);
      accelerationHolder.object2Acceleration.scaleAdd(-dampFactor, g2.velocity, accelerationHolder.object2Acceleration);


   }

}