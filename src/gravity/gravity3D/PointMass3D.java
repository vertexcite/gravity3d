package gravity.gravity3D;

import gravity.PointMass;

import javax.media.j3d.TransformGroup;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

/**
 * Gravity object with added responsibility to store ref to its
 * Java3D transformGroup.
 *
 * Copyright:    Copyright (c) 2002
 * Company:
 * @author Randall Britten
 * @version 1.0
 */

public class PointMass3D extends PointMass {

   public PointMass3D(double mass, Point3d position, Vector3d velocity, TransformGroup transformGroup ) {
      super(mass, position, velocity);
      this.transformGroup = transformGroup;
   }

   private TransformGroup transformGroup;

   public TransformGroup getTransformGroup() {
      return transformGroup;
   }

   public void setTransformGroup(TransformGroup newTransformGroup) {
      transformGroup = newTransformGroup;
   }


}