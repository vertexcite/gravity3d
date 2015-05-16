package simulation.mechanical;

import javax.vecmath.Vector3d;

/**
 * AccelerationHolder allows for returning of two acceleration vector
 * objects with better efficiency than creating the holder and vectors each
 * time a forcelaw is called, rather the caller can create on holder and reuse it.
 *
 * Note that data members are public since this class is acting as a struct.
 *
 * Copyright:    Copyright (c) 2002
 * @author Randall Britten
 * @version 1.0
 */

public class AccelerationHolder {
   /**
    * Constructs the constituent acceleration vector objects.
    */
   public AccelerationHolder() {
      // Written in so that it can be Javadoc'd
   }

   /**
    * Holder for first object's acceleration vector component for the interaction between
    * these two objects.
    */
   public Vector3d object1Acceleration = new Vector3d();

   /**
    * Holder for second object's acceleration vector component for the interaction between
    * these two objects.
    */
   public Vector3d object2Acceleration = new Vector3d();
}
