package gravity;

import vg.animation.*;

/**
 * Title:        Gravity Simulation
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author Randall Britten
 * @version 1.0
 */

public class GravityController implements AnimatedObject, AnimatorController {
   private AnimatorThread animatorThread;
   private GravityGui gravityGui;
   private MultiBodySimulator multibodySim;

   private double animationTimeStep;
   private double simulationTimeStep;


   public GravityController() {
      animatorThread = new AnimatorThread(this);
      animatorThread.setDaemon(true);
   }

   public void startAnimatorThread() {
      animatorThread.start();
   }

   public void animate() {
      multibodySim.nextIteration();
//      System.out.println("Gcontroller.animate()");
      if (gravityGui != null) {
//         System.out.println("About to call gCanvas.repaint() from GravityController.");
         gravityGui.updateFrame();
      }
   }

   public void setAnimationTimeStep(double newAnimationTimeStep) {
      animationTimeStep = newAnimationTimeStep;
      animatorThread.setTimestep(animationTimeStep);

   }
   public double getAnimationTimeStep() {
      return animationTimeStep;
   }
   public void setSimulationTimeStep(double newSimulationTimeStep) {
      simulationTimeStep = newSimulationTimeStep;
      multibodySim.setTimeStep(newSimulationTimeStep);
      multibodySim.getSolver().setMaxStepSize(newSimulationTimeStep);
   }
   public double getSimulationTimeStep() {
      return simulationTimeStep;
   }

   public void clear() {
      animatorThread.pause();
      multibodySim.clear();
      gravityGui.clear();
      gravityGui.updateFrame();
   }

   public MultiBodySimulator getMultiBodySimulator() {
      return multibodySim;
   }

   public void setMultibodySimulator(MultiBodySimulator newMultibodySim) {
      multibodySim = newMultibodySim;
   }
   public void setGravityGui(GravityGui newGravityGui) {
      gravityGui = newGravityGui;
   }
   public GravityGui getGravityGui() {
      return gravityGui;
   }

   public void addPointMass(PointMass g) {
      multibodySim.addPointMass(g);
   }

   public void randomSetup(int count, double minMass, double maxMass, double initialAreaRadius, double velocityRange, boolean inXYPlane) {
      boolean wasPaused = paused;
      animatorThread.pause();

      clear();

      if (inXYPlane) {
         multibodySim.randomSetup2D(count, minMass, maxMass, initialAreaRadius, velocityRange);
      }
      else {
         multibodySim.randomSetup3D(count, minMass, maxMass, initialAreaRadius, velocityRange);
      }

      if (!wasPaused) {
         animatorThread.restart();
      }
      else {
         gravityGui.updateFrame();
      }

   }

   private boolean paused = false;
   public void pauseAnimation() {
      paused = true;
      animatorThread.pause();
   }

   public void restartAnimation() {
      paused = false;
      animatorThread.restart();
   }
   public double getTimestep() {
      return animatorThread.getTimestep();
   }
   public void setTimestep(double newTimestep) {
      animatorThread.setTimestep(newTimestep);
   }
   public void restart() {
      paused = false;
      animatorThread.restart();
   }
   public void pause() {
      paused = true;
      animatorThread.pause();
   }
   public boolean isPaused() {
      return paused;
   }



}
