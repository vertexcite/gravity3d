package vgnumerical;

/**
 * Description:
 * Implements Runge-Kutta-Fehlberg (RKF45).
 * Reference texts: Burden and Faires, Lambert.
 * Note that this algorithm differs from Burden and Fairs in that the solution is not abandoned
 * if the tolerance cannot be met withing the stepSize min, max limits,
 * rather, the solution proceeds using the min step size, and accepting the larger error.
 *
 * Copyright:    Copyright (c) 2002
 * Company:
 * @author Randall Britten
 * @version 1.0
 */

public class RungeKuttaFehlberg45 implements IvpOdeSystemSolver {

   Vector k1, k2, k3, k4, k5, k6, workspace;

   int lastOrder;

   /**
    * Perhaps the compiler sees they are final and optimises in the results.
    * If not, use this to generate code.
    * @todo look at the bytecode to see.
    */
   private final double[] butcher0   = {1d/4};
   private final double[] butcher1   = {3d/32,       9d/32};
   private final double[] butcher2   = {1932d/2197, -7200d/2197,  7296d/2197};
   private final double[] butcher3   = {439d/216,   -8,           3680d/513,   -845d/4104};
   private final double[] butcher4   = {-8d/27,      2,          -3544d/2565,   1859d/4104,  -11d/40};
   private final double[] butcherErr = {1d/360,      0,          -128d /4275,  -2197d/75240, 1d/50,   2d/55};
   private final double[] butcherSol = {25d/216,     0,           1408d/2565,   2197d/4104,  -1d/5};
   // NB the last two lines are swapped around compared to the way it is printed in Lambert.
   // Also there is a line in lambert in between them that is not used here, it is the
   // higher order method which is subtracted from the solution in order to get the error estimate.

   private final double[][] butcherArray = {
      butcher0,
      butcher1,
      butcher2,
      butcher3,
      butcher4,
      butcherErr,
      butcherSol
   };

   /**
    *
    */
   static public void main(String[] args) {
      RungeKuttaFehlberg45 r = new RungeKuttaFehlberg45();
      for (int i=0; i<r.butcherArray.length; i++) {
         for (int j = 0; j < r.butcherArray[i].length; j++) {
            System.out.print("\t\t" + r.butcherArray[i][j]);
         }
         System.out.println();
      }
   }

   /**
    * dummy so that solve(double, double, Vector) is not slowed down by having
    * to construct object.
    */
   private static IvpOdeSystemSolver.SolutionStepInfo dummy = new IvpOdeSystemSolver.SolutionStepInfo();
   public void solve(double timeStep, double time, Vector y) {
      solve(timeStep, time, y, dummy);
   }


   /**
    * Implements RungeKuttaFelburg45, with the option of autoStepSize adjustment.
    *
    * @param y reference to Vector holding the initial state, and
    * where the result will be written (helps prevent object burn).
    * @param timeStep the timeStep requested.
    * @return the step size used, i.e. the solution may not be at the time point
    * requested by the caller.  The caller must then decide whether to call again,
    * or use the solution at the time point provided.
    *
    */
   public void solve(double timeStep, double time, Vector y, IvpOdeSystemSolver.SolutionStepInfo solutionStepInfo) {
      boolean retry = true;
//      System.out.println("First entry: timeStep=" + timeStep);

      double errorEstimate = 0;
      while (retry) {

         int order = y.elements.length;

         if (order == 0) return;

         if (order != lastOrder) {
            lastOrder = order;
            k1 = new Vector(new double[order]);
            k2 = new Vector(new double[order]);
            k3 = new Vector(new double[order]);
            k4 = new Vector(new double[order]);
            k5 = new Vector(new double[order]);
            k6 = new Vector(new double[order]);
            workspace = new Vector(new double[order]);
         }

         // Calculate k1
         // Note: the solution to the call to f.evaluate is placed into the last parameter, k1 in this case.
         f.evaluate(time, y, k1);
         k1.scaleYourself(timeStep);

         // Calculate k2
         // Below was written as "k2 = f.evaluate(time + timeStep/4, y.linearCombineConstruct(1, 0.25, k1));"
         // Changed to reduce object burn.
         workspace.copy(y);  // Saving object burn costs this extra copy-over step.
         workspace.linearCombineYourself(1, butcherArray[0][0], k1);
         f.evaluate(time + timeStep/4, workspace, k2);
         k2.scaleYourself(timeStep);


         // Calculate k3
         // Below was written as "k3 = f.evaluate(time + timeStep*3d/8, y.linearCombineConstruct(1, 3d/32, k1).linearCombineConstruct(1, 9d/32, k2));"
         // Changed to reduce object burn.
         workspace.linearCombineYourself(1, (butcherArray[1][0] - butcherArray[0][0]), k1); // workspace was (y+1/4*k1), after this it is ((y+3/32*k1)
         workspace.linearCombineYourself(1, butcherArray[1][1], k2);
         f.evaluate(time + timeStep*3d/8, workspace, k3);
         k3.scaleYourself(timeStep);

         // Calculate k4
         // Below was written as "k4 = f.evaluate(time + timeStep*12d/13, y.linearCombineConstruct(1, 1932d/2197, k1).linearCombineConstruct(1, -7200d/2197, k2).linearCombineConstruct(1, 7296d/2197, k3));"
         // Changed to reduce object burn.
         workspace.linearCombineYourself(1, (butcherArray[2][0] - butcherArray[1][0]), k1);
         workspace.linearCombineYourself(1, (butcherArray[2][1] - butcherArray[1][1]), k2);
         workspace.linearCombineYourself(1, (butcherArray[2][2]), k3);
         f.evaluate(time + timeStep*12d/13, workspace, k4);
         k4.scaleYourself(timeStep);

         // Calculate k5
         // Below was written as "k5 = f.evaluate(time + timeStep, y.linearCombineConstruct(1, 439d/216, k1).linearCombineConstruct(1, -8, k2).linearCombineConstruct(1, 3680f/513, k3).linearCombineConstruct(1, 845d/4104, k4) );"
         // Changed to reduce object burn.
         workspace.linearCombineYourself(1, (butcherArray[3][0] - butcherArray[2][0]), k1);
         workspace.linearCombineYourself(1, (butcherArray[3][1] - butcherArray[2][1]), k2);
         workspace.linearCombineYourself(1, (butcherArray[3][2] - butcherArray[2][2]), k3);
         workspace.linearCombineYourself(1, (butcherArray[3][3]), k4);
         f.evaluate(time + timeStep, workspace, k5);
         k5.scaleYourself(timeStep);

         // Calculate k6
         // Below was written as "k6 = f.evaluate(time + timeStep/2, y.linearCombineConstruct(1, -8f/27, k1).linearCombineConstruct(1, 2, k2).linearCombineConstruct(1, -3544d/2565, k3).linearCombineConstruct(1, 1859d/4104, k4).linearCombineConstruct(1, -11d/40, k5)  );"
         // Changed to reduce object burn.
         workspace.linearCombineYourself(1, (butcherArray[4][0] - butcherArray[3][0]), k1);
         workspace.linearCombineYourself(1, (butcherArray[4][1] - butcherArray[3][1]), k2);
         workspace.linearCombineYourself(1, (butcherArray[4][2] - butcherArray[3][2]), k3);
         workspace.linearCombineYourself(1, (butcherArray[4][3] - butcherArray[3][3]), k4);
         workspace.linearCombineYourself(1, (butcherArray[4][4]), k5);
         f.evaluate(time + timeStep, workspace, k6);
         k6.scaleYourself(timeStep);

         // Calc error estimate
         workspace.linearCombineYourself(1, (butcherArray[5][0] - butcherArray[4][0]), k1);
         workspace.linearCombineYourself(1, (butcherArray[5][1] - butcherArray[4][1]), k2);
         workspace.linearCombineYourself(1, (butcherArray[5][2] - butcherArray[4][2]), k3);
         workspace.linearCombineYourself(1, (butcherArray[5][3] - butcherArray[4][3]), k4);
         workspace.linearCombineYourself(1, (butcherArray[5][4] - butcherArray[4][4]), k5);
         workspace.linearCombineYourself(1, (butcherArray[5][5]), k6);
         errorEstimate = workspace.modulus()/timeStep;
//         System.out.println("Error Estimate: " + errorEstimate);



         // Adjust stepsize if using auto step size
         if (autoStepAdjust) {
            // Calc step adjustment factor
            double stepAdjustmentFactor = 0.84*Math.pow(tolerance/errorEstimate, 0.25);
//          System.out.println("stepAdjustmentfactor: " + stepAdjustmentFactor);

            if (stepAdjustmentFactor < 0.1) stepAdjustmentFactor = 0.1;
            if (stepAdjustmentFactor > 4)   stepAdjustmentFactor = 4;
            timeStep *= stepAdjustmentFactor;
   //         System.out.println("Adjusted timestep: " + timeStep);

            // Deal with max stepsize
            if (timeStep > maxStepSize) timeStep = maxStepSize;

            // Deal with minimum stepsize.
            if (timeStep < minStepSize) timeStep = minStepSize;
         }

         // Save answer if error withing tolerance,
         // or we are not using auto step size
         // or we are forced to use the minStepSize
         if (!autoStepAdjust || errorEstimate <= tolerance || timeStep == minStepSize) {
//            System.out.println(" ******** Accepting answer ********\n\n");
            retry = false;
            y.linearCombineYourself(1, butcherArray[6][0], k1);
            y.linearCombineYourself(1, butcherArray[6][2], k3);
            y.linearCombineYourself(1, butcherArray[6][3], k4);
            y.linearCombineYourself(1, butcherArray[6][4], k5);
         }

//         System.out.println("End of loop: timestep: " + timeStep);
      } // while retry

//      System.out.println("Returning: timestep=" + timeStep);
      solutionStepInfo.stepSizeUsed = timeStep;
      solutionStepInfo.errorEstimate = errorEstimate;

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
   public void setAutoStepAdjust(boolean newAutoStepAdjust) {
      autoStepAdjust = newAutoStepAdjust;
   }
   public boolean isAutoStepAdjust() {
      return autoStepAdjust;
   }
   private IvpOdeSystemFunction f;
   private double tolerance;
   private double maxStepSize;
   private double minStepSize;
   private boolean autoStepAdjust=false;

}