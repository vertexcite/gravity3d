package vgnumerical;

/**
 * Copyright:    Copyright (c) 2001
 * @author Randall Britten
 * @version 1.0
 *
 * Performance conscious convenient Runge-Kutta 1/6 1/3 1/3 1/6 System solver.
 *
 */


public class RungeKutta implements IvpOdeSystemSolver {

   Vector k1, k2, k3, k4;
   int lastOrder;

   public void solve(double timeStep, double time, Vector y) {
      solve(timeStep, time, null);
   }

   /**
    * Note that solutionStepInfo is not used.
    */
   public void solve(double timeStep, double time, Vector y, IvpOdeSystemSolver.SolutionStepInfo solutionStepInfo) {
      solutionStepInfo = null;

      int order = y.elements.length;

      if (order == 0) return;

      if (order != lastOrder) {
         lastOrder = order;
         k1 = new Vector(new double[order]);
         k2 = new Vector(new double[order]);
         k3 = new Vector(new double[order]);
         k4 = new Vector(new double[order]);
      }

      // Note the solution is placed into k1.
      f.evaluate(time, y, k1);
      k1.scaleYourself(timeStep);

      f.evaluate(time + timeStep/2, y.linearCombineConstruct(1, 0.5, k1), k2);
      k2.scaleYourself(timeStep);

      f.evaluate(time + timeStep/2, y.linearCombineConstruct(1, 0.5, k2), k3);
      k3.scaleYourself(timeStep);

      f.evaluate(time + timeStep, y.addConstruct(k3), k4);

      y.addToYourself(k1.scaleYourself(1.0/6));
      y.addToYourself(k2.scaleYourself(1.0/3));
      y.addToYourself(k3.scaleYourself(1.0/3));
      y.addToYourself(k4.scaleYourself(1.0/6));

      return;

   }

   public void setSystemFunction(IvpOdeSystemFunction newSystemFunction) {
      f = newSystemFunction;
   }
   /**
    * The Object that has the system function that the solver is solving.
    */
   public IvpOdeSystemFunction getSystemFunction() {
      return f;
   }
   private IvpOdeSystemFunction f;

   public void setTolerance(double newTolerance) {
      tolerance = newTolerance;
   }
   public double getTolerance() {
      return tolerance;
   }
   public void setMaxStepSize(double newMaxStepSize) {
      maxStepSize = newMaxStepSize;
   }
   public double getMaxStepSize() {
      return maxStepSize;
   }
   public void setMinStepSize(double newMinStepSize) {
      minStepSize = newMinStepSize;
   }
   public double getMinStepSize() {
      return minStepSize;
   }
   private double tolerance;
   private double maxStepSize;
   private double minStepSize;

   public void setAutoStepAdjust(boolean newAutoStepAdjust) {
      System.out.println("This solver (RungeKutta) does not have auto step adjust");
   }
   public boolean isAutoStepAdjust() {
      System.out.println("This solver (RungeKutta) does not have auto step adjust");
      return false;
   }




}