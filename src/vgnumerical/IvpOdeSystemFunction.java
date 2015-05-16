package vgnumerical;

/**
 * Description:
 * Collaborates with IvpSystemSolver.
 *
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author Randall Britten
 * @version 1.0
 */

public interface IvpOdeSystemFunction {
   /**
    * Evaluate the system at the given inputs.
    * The returned vector is not referenced later by IvpSystemSolver.
    * This allows the implementor of this function to reuse the object to
    * store the return values, reducing object burn.
    * @param time parameter input to function.
    * @param y system state variables input to function.
    * @param returnValueHolder output, function stores result here rather
    * than constructing new vector for return.
    */
   public void evaluate(double time, Vector y, Vector returnValueHolder);
}
