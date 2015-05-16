package gravity;

import javax.vecmath.Vector3d;
import javax.vecmath.Point3d;

/**
 * Title:        Gravity Simulation
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author Randall Britten
 * @version 1.0
 */

public class PointMass {
   public PointMass() {
   }

   public PointMass(double mass, Point3d position, Vector3d velocity) {
      this.position = position;
      this.velocity = velocity;
      this.mass = mass;
   }

   public double mass = 1;
   public Point3d position = new Point3d();
   public Vector3d velocity = new Vector3d();
   public Vector3d acceleration = new Vector3d();

   public void addAcceleration(Vector3d acceleration) {
      this.acceleration.x += acceleration.x;
      this.acceleration.y += acceleration.y;
      this.acceleration.z += acceleration.z;
   }

   public void zeroAccelleration() {
      this.acceleration.x = 0;
      this.acceleration.y = 0;
      this.acceleration.z = 0;
   }

   double averageVelocity;
   double maxVelocity;
//   public void iterationPositionChange(double timestep) {
//      // Euler method?.
//      Vector3 velocityChange = acceleration.scale(timestep);
//
//      velocity = velocity.add(velocityChange);
//      position = position.add(velocity);
//   }

   double radius = 0;
   double density = 1;
   public double getRadius() {
      if (radius == 0.0) {
         radius = Math.pow(mass/density, 1.0/3.0);
      }
      return radius;
   }
   public void setMass(double newMass) {
      mass = newMass;
   }
   public double getMass() {
      return mass;
   }
}
