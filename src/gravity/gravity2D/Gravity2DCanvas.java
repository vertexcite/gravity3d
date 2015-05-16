package gravity.gravity2D;

import javax.vecmath.Vector3d;
import javax.vecmath.Point3d;

import gravity.*;

import java.util.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.JToolBar;


/**
 * Title:        Gravity Simulation
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author Randall Britten
 * @version 1.0
 */

public class Gravity2DCanvas extends Canvas {

   GravityController gController;

   public Gravity2DCanvas() {
      this.gController = gController;
      try {
         jbInit();
      }
      catch(Exception e) {
         e.printStackTrace();
      }
   }

   public void update(Graphics g) {
      paint(g);
   }


   private double zoomFactor = 10;
   private int xShift = 100;
   private int yShift = 100;



   int previousWidth;
   int previousHeight;
   int currentWidth;
   int currentHeight;

   public void paint(Graphics g1) {

      currentWidth = getWidth();
      currentHeight = getHeight();
      //Dump old offscreenimage if size has changed.
      if ( !( (currentWidth == previousWidth) && ( currentHeight == previousHeight )) ) {
         offscreenImage = null;
      }
      if (offscreenImage == null) {
         offscreenImage = createImage(currentWidth,currentHeight);
      }
      previousWidth = currentWidth;
      previousHeight = currentHeight;

      Graphics g = offscreenImage.getGraphics();



      g.setColor(Color.white);
      g.fillRect(0,0,getWidth(), getHeight());
      g.setColor(Color.black);
      MultiBodySimulator multibodySim = gController.getMultiBodySimulator();
      java.util.List gs = multibodySim.getPointMasses();

      // Show point mass count.
      if (showPointMassCount) {
         g.drawString("" + gs.size(), 0, getHeight());
      }
      // Show time elapsed
      if (showTimeElapsed) {
         g.drawString("" + multibodySim.getTimeElapsed(), 0, 15);
      }

      for (int i=0; i<gs.size(); i++) {
         PointMass go = (PointMass)gs.get(i);
         double gRad = go.getRadius();
         double centrex = (zoomFactor * (go.position.x  + xShift) + currentWidth/2 + moveX);
         double centrey = (zoomFactor * (go.position.y  + yShift) + currentHeight/2 + moveY);

         // Draw object
         g.drawOval(
         (int)(centrex -gRad * zoomFactor ),
         (int)(centrey -gRad * zoomFactor ),
         (int)(zoomFactor * 2 * gRad),
         (int)(zoomFactor * 2 * gRad)
         );


         // Draw velocity Vector
         if (showVelocityVectors) {
            g.drawLine(
            (int)centrex, (int)centrey,
            (int)( centrex + go.velocity.x * zoomFactor * 3.0),
            (int)( centrey + go.velocity.y * zoomFactor * 3.0)
            );
         }
      }

      g1.drawImage(offscreenImage, 0,0, this);
   }
   public void setZoomFactor(double newZoomFactor) {
      zoomFactor = newZoomFactor;
   }
   public double getZoomFactor() {
      return zoomFactor;
   }
   public void setXShift(int newXShift) {
      xShift = newXShift;
   }
   public int getXShift() {
      return xShift;
   }
   public void setYShift(int newYShift) {
      yShift = newYShift;
   }
   public int getYShift() {
      return yShift;
   }
   private void jbInit() throws Exception {

      PanMouseListener panMouseListener = new PanMouseListener();
      this.addMouseListener(panMouseListener);
      this.addMouseMotionListener(panMouseListener);

      ThrowMouseListener throwMouseListener = new ThrowMouseListener();
      this.addMouseListener(throwMouseListener);
      this.addMouseMotionListener(throwMouseListener);

      this.addMouseListener(new DropMouseListener() );

   }


   int moveX;
   int moveY;


   int dragOriginX;
   int dragOriginY;
   private double nextMass = 1;

   public void setNextMass(double newNextMass) {
      nextMass = newNextMass;
   }
   public double getNextMass() {
      return nextMass;
   }


   Image offscreenImage;
   private boolean showVelocityVectors;
   private boolean showTimeElapsed;
   private boolean showPointMassCount;



   class DropMouseListener extends MouseAdapter {
      public void mouseClicked(MouseEvent e) {
         double x = (e.getX()-currentWidth/2)/zoomFactor-xShift;
         double y = (e.getY()-currentHeight/2)/zoomFactor-yShift;
         Point3d positionV = new Point3d(x, y, 0);
         double xv = 0; //Math.random()*0.2 - 0.1;
         double yv = 0; //Math.random()*0.2 - 0.1;
         Vector3d velocityV = new Vector3d(xv, yv, 0);
         PointMass g = new PointMass(nextMass, positionV,  velocityV);
         gController.getMultiBodySimulator().addPointMass(g);
         repaint();
      }
   }

   /**
    * Inner class for mouse panning control.
    */
   class PanMouseListener extends MouseAdapter implements MouseMotionListener {
      public void mouseMoved(MouseEvent e) {
      }

      public void mouseDragged(MouseEvent e) {
         if (!e.isAltDown() ) {
            moveX = e.getX() - dragOriginX;
            moveY = e.getY() - dragOriginY;
            repaint();
         }

      }

      public void mousePressed(MouseEvent e) {
         if (!e.isAltDown() ) {
            dragOriginX = e.getX();
            dragOriginY = e.getY();
         }
      }
      public void mouseReleased(MouseEvent e) {
         if (!e.isAltDown() ) {
            xShift += moveX/zoomFactor;
            yShift += moveY/zoomFactor;
            moveX = 0;
            moveY = 0;
            repaint();
         }
      }
   }


   /**
    * Inner class for mouse panning control.
    */
   class ThrowMouseListener extends MouseAdapter implements MouseMotionListener {
      public void mouseMoved(MouseEvent e) {
      }


      int throwX, throwY, throwDragOriginX, throwDragOriginY;
      boolean throwingStarted, throwingDragged;

      public void mousePressed(MouseEvent e) {
         if (e.isAltDown() ) {
            throwingStarted = true;
            throwingDragged = false;
            throwDragOriginX = e.getX();
            throwDragOriginY = e.getY();
         }
      }

      public void mouseDragged(MouseEvent e) {
         if (e.isAltDown() && throwingStarted) {
            throwingDragged = true;
         } else {
            throwingDragged = false;
            throwingStarted = false;
         }
      }

      public void mouseReleased(MouseEvent e) {

         if (e.isAltDown() && throwingStarted && throwingDragged ) {
            throwX = e.getX() - throwDragOriginX;
            throwY = e.getY() - throwDragOriginY;
            double xVelocity = throwX/zoomFactor/10;
            double yVelocity = throwY/zoomFactor/10;
            double x = (e.getX() -currentWidth/2)/zoomFactor-xShift;
            double y = (e.getY() -currentHeight/2)/zoomFactor-yShift;
            Point3d positionV = new Point3d(x, y, 0);
            Vector3d velocityV = new Vector3d(xVelocity, yVelocity, 0);
            PointMass g = new PointMass(nextMass, positionV,  velocityV);
            gController.getMultiBodySimulator().addPointMass(g);
            repaint();


         }
         throwingStarted = false;
         throwingDragged = false;
      }
   }
   public void clear() {
      setXShift(0);
      setYShift(0);
   }
   public void setGController(GravityController newGController) {
      gController = newGController;
   }
   public GravityController getGController() {
      return gController;
   }
   public void setShowVelocityVectors(boolean newShowVelocityVectors) {
      showVelocityVectors = newShowVelocityVectors;
   }
   public boolean isShowVelocityVectors() {
      return showVelocityVectors;
   }
   public void setShowTimeElapsed(boolean newShowTimeElapsed) {
      showTimeElapsed = newShowTimeElapsed;
   }
   public boolean isShowTimeElapsed() {
      return showTimeElapsed;
   }
   public void setShowPointMassCount(boolean newShowPointMassCount) {
      showPointMassCount = newShowPointMassCount;
   }
   public boolean isShowPointMassCount() {
      return showPointMassCount;
   }



} // End outer class



