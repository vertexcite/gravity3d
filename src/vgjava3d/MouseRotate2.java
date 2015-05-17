package vgjava3d;


/**
 * Title:        Gravity 3D
 * Description:
 * Copyright:    Copyright (c) 2002
 * Company:
 * @author Randall Britten
 * @version 1.0
 */

public class MouseRotate2 extends com.sun.j3d.utils.behaviors.mouse.MouseRotate {
   public MouseRotate2() {
   }
   public void setInvert(boolean invert) {
      this.invert = invert;
   }
   public boolean getInvert() {
      return invert;
   }
}