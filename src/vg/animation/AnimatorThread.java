
package vg.animation;


/**
 * Description:
 * Animation Thread.  Collaborates with AnimatedObject
 * This thread, once started, periodically notifies Animated Object.
 *
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author Randall Britten
 * @version 1.0
 */

public class AnimatorThread extends Thread {
   public AnimatorThread(AnimatedObject animatee) {
      this.animatee = animatee;
   }

   volatile private boolean animationActive = true;
   volatile private boolean animationPaused = false;
   volatile private double timestep = 0.05;
   private AnimatedObject animatee;

   public double getTimestep() {
      return timestep;
   }
   public void setTimestep(double newTimestep) {
      timestep = newTimestep;
   }

  public void run() {
    try {
      while (animationActive) {
        while (animationPaused && animationActive) {
          synchronized (this) {
            wait();
          }
        }

        if (animationActive) {
          Thread.sleep((long) (timestep * 1000));
          animatee.animate();
          // System.out.println("About to call gController.repaint() from Animator");
        }
      }
    } catch (InterruptedException e) {
      // animationPaused = true;
    }
  }

   public void restart() {
      animationPaused = false;
      synchronized (this) {
        notify();
      }
   }

   public void pause() {
      animationPaused = true;
   }

   public void stopAnimation() {
      animationActive = false;
      synchronized (this) {
        notify();
      }
   }

}