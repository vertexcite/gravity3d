package vgnumerical;

/**
 * Title:        Gravity 3D
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:
 * @author Randall Britten
 * @version 1.0
 */

public interface IvpOdeSystemSolver {


   /**
    * Request the solver to perform one step of the solution.
    *
    * @param y reference to Vector holding the initial state, and
    * where the result will be written (helps prevent object burn).
    * @param requestedTimeStep the timeStep requested.  The return value is the actual
    * timeStep that was used.
    * @param time The time value representing the point in time where y has its value.
    * @param solutionStepInfo a holder of the step sized used and error estimate info from this step.
    * @return the step size used.
    * If this is zero, it means the minStepsize limit was enforced,
    * and solution was abandoned.
    */
   public void solve(double requestedTimeStep, double time, Vector y, SolutionStepInfo solutionStepInfo);
   /**
    * Same as above method, but does not allow caller to interrogate step size used or error estimate via solutionStepHolder.
    */
   public void solve(double timeStep, double time, Vector y);
   public IvpOdeSystemFunction getSystemFunction();
   public void setSystemFunction(IvpOdeSystemFunction systemFunction);
   public void setTolerance(double newTolerance);
   public double getTolerance();
   public void setMaxStepSize(double newMaxStepSize);
   public double getMaxStepSize();
   public void setMinStepSize(double newMinStepSize);
   public double getMinStepSize();

   /**
    * AutoStepAdjust property, if available on solver.
    *
    */
   public void setAutoStepAdjust(boolean newAutoStepAdjust);
   public boolean isAutoStepAdjust();

   /**
    * A structure containing info on the
    * step size used and the error estimate.
    */
   public static class SolutionStepInfo {
      public double stepSizeUsed;
      public double errorEstimate;
   }

}
