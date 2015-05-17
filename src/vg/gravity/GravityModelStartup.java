package vg.gravity;

import gravity.GravityController;
import gravity.MultiBodySimulator;
import vgnumerical.IvpOdeSystemSolver;
import vgnumerical.RungeKuttaFehlberg45;


/**
 * Title:        Gravity 3D
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:
 * @author Randall Britten
 * @version 1.0
 */

public class GravityModelStartup {
   public void startup() {

      gravityController = new GravityController();
      MultiBodySimulator multiBodySimulator = new MultiBodySimulator();

      IvpOdeSystemSolver solver = new RungeKuttaFehlberg45();
//      IvpOdeSystemSolver solver = new RungeKutta();

      solver.setMaxStepSize(0.1);
      solver.setMinStepSize(0.001);
      solver.setTolerance(5);
      multiBodySimulator.setSolver(solver);
      gravityController.setMultibodySimulator(multiBodySimulator);

   }
   public GravityController getGravityController() {
      return gravityController;
   }
   private GravityController gravityController;
}