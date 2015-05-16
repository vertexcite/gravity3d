package simulation.mechanical;

import gravity.PointMass;
import javax.vecmath.Vector3d;

/**
 * Title:        Gravity 3D
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:
 * @author Randall Britten
 * @version 1.0
 */

public interface ForceLaw {
   /**
    * This method is the method called by whatever the ForceLaw is plugged into.
    * This version of the overloaded method supports passing in a workspace, hoping to reduce object burn.
    * @param g1 The first object
    * @param g2 The second object
    * @param accelerationHolder The Holder calculated values are stored, allowing better efficiency, since this method will not need to construct any objects.
    * @param workspace An array of Vector3d objects that can be used as workspace so that this method does not need to create these workspace objects.  This should help reduce object burn.
    * @return Rather than use return to a referenced object, better efficiency is gained
    * by using accelerationHolder.
    */
   public void interactionAcceleration (PointMass g1, PointMass g2, AccelerationHolder accelerationHolder, Vector3d[] workspace);

   /**
    * This method is the method called by whatever the ForceLaw is plugged into.
    * @param g1 The first object
    * @param g2 The second object
    * @param accelerationHolder The Holder calculated values are stored, allowing better efficiency, since this method will not need to construct any objects.
    * @return Rather than use return to a referenced object, better efficiency is gained
    * by using accelerationHolder.
    */
   public void interactionAcceleration (PointMass g1, PointMass g2, AccelerationHolder accelerationHolder);

   public void setScaleFactor(double scaleFactor);
   public double getScaleFactor();

}