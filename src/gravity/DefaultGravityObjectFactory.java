package gravity;
import javax.vecmath.Vector3d;
import javax.vecmath.Point3d;

/**
 * Title:        Gravity 3D
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:
 * @author Randall Britten
 * @version 1.0
 */

public class DefaultGravityObjectFactory implements GravityObjectFactory {

   public PointMass createGravityObject(double mass, Point3d position, Vector3d velocity) {
      return new PointMass(mass, position, velocity);
   }
}