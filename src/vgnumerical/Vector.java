package vgnumerical;


/**
 * Title:        Gravity Simulation
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author Randall Britten
 * @version 1.0
 */

public class Vector {

   public Vector(double[] elements) {
      this.elements = elements;
   }

   /**
    * Copies all elements from a into this Vector's elements.
    * Does not check size compatibility, exception thrown if a is smaller
    * than this.
    * @throws ArrayIndexOutOfBoundsException if otherVector is smaller than this.
    */
   public void copy(Vector otherVector) {
      for (int i=0; i<elements.length; i++) {
         elements[i] = otherVector.elements[i];
      }
   }

   double[] elements;
   public double[] getElements() {
      return elements;
   }
   public void setElements(double[] newElements) {
      elements = newElements;
   }

   public Vector scaleYourself(double factor) {
      for (int i=0; i<elements.length; i++ ) {
         elements[i] *= factor;
      }
      return this;
   }

   public Vector addToYourself(Vector addend) {
      for (int i=0; i<elements.length; i++ ) {
         elements[i] += addend.elements[i];
      }
      return this;
   }

   public Vector scaleConstruct(double factor) {
      Vector result = new Vector(new double[this.elements.length] );
      for (int i=0; i<elements.length; i++ ) {
         result.elements[i] = factor * elements[i];
      }
      return result;
   }

   public Vector addConstruct(Vector b) {
      Vector result = new Vector(new double[this.elements.length] );
      for (int i=0; i<elements.length; i++ ) {
         result.elements[i] = elements[i] + b.elements[i];
      }
      return result;
   }

   public Vector linearCombineConstruct(double scaleThis, double scaleB, Vector b) {
      Vector result = new Vector(new double[this.elements.length] );
      for (int i=0; i<elements.length; i++ ) {
         result.elements[i] = scaleThis * elements[i] + scaleB * b.elements[i];
      }
      return result;
   }

   public Vector linearCombineYourself(double scaleThis, double scaleB, Vector b) {
      for (int i=0; i<elements.length; i++ ) {
         elements[i] = scaleThis * elements[i] + scaleB * b.elements[i];
      }
      return this;
   }

   public double modulusSquared() {
      double modSquared = 0;
      for (int i = 0; i < elements.length; i++) {
         modSquared += Utility.square(elements[i]);
      }
      return modSquared;
   }

   public double modulus() {
      return Math.sqrt(modulusSquared());
   }


}
