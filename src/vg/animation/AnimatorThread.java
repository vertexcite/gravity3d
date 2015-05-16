
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

   private boolean animationActive = true;
   private boolean animationPaused = false;
   private double timestep = 0.05;
   private AnimatedObject animatee;

   public double getTimestep() {
      return timestep;
   }
   public void setTimestep(double newTimestep) {
      timestep = newTimestep;
   }

   public void run() {
      try {
         while(animationActive) {
            synchronized (this) {
               while(animationPaused && animationActive) {
                  wait();
               }

               if (animationActive) {
                  Thread.sleep((long)(timestep*1000));
                  animatee.animate();
                  //         System.out.println("About to call gController.repaint() from Animator");
               }
            }
         }
      } catch (InterruptedException e) {
         // animationPaused = true;
      }
   }

   public synchronized void restart() {
      animationPaused = false;
      notify();
   }

   public synchronized void pause() {
      animationPaused = true;
   }

   public synchronized void stopAnimation() {
      animationActive = false;
      notify();
   }

}