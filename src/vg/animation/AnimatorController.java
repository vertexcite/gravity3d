package vg.animation;

/**
 * Title:        Gravity 3D
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:
 * @author Randall Britten
 * @version 1.0
 */

public interface AnimatorController {
   public double getTimestep();
   public void setTimestep(double newTimestep);
   public void restart();
   public void pause();
}