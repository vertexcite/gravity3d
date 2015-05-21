package gravity;

import java.util.ArrayList;
import java.util.List;

import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

import simulation.mechanical.AccelerationHolder;
import simulation.mechanical.ForceLaw;
import vgnumerical.IvpOdeSystemFunction;
import vgnumerical.IvpOdeSystemSolver;
import vgnumerical.Vector;

/**
 * Title:        Gravity Simulation
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author Randall Britten
 * @version 1.0
 */

public class MultiBodySimulator implements IvpOdeSystemFunction {




   public synchronized void clear() {
      timeElapsed = 0;
//      neededTimeStep = 0.5;
//      timeStep = 0.5;

      pointMasses.clear();
   }


   public MultiBodySimulator() {
      // Do nothing.
   }

   public synchronized void randomSetup2D(int count, double minMass, double maxMass, double initialAreaRadius, double velocityRange) {
      randomSetup(count, minMass, maxMass, initialAreaRadius, initialAreaRadius, 0, velocityRange, velocityRange, 0);

      // Finally, start with the COM velocity and position at zero.
      coMove();

   }

   public synchronized void randomSetup3D(int count, double minMass, double maxMass, double initialAreaRadius, double velocityRange) {
      randomSetup(count, minMass, maxMass, initialAreaRadius, initialAreaRadius, initialAreaRadius, velocityRange, velocityRange, velocityRange);

      // Try get rid of radial component of velocity.
      for (int i=0; i<count; i++) {
         PointMass m = pointMasses.get(i);
         Vector3d posiDir = new Vector3d(m.position);
         posiDir.normalize();
         m.velocity.cross(m.velocity, posiDir);
      }

      // Finally, start with the COM velocity and position at zero.
      coMove();

   }

   /**
    * Create a random initial setup.
    * @param count Number of objects.
    * @param initialAreaRadius Radius of circle within which to distribute objects.
    * @param velocityRange Range within which each component of each object's velocity is randomly chosen.
    */
   public synchronized void randomSetup(int count, double minMass, double maxMass, double xInitialAreaRadius, double yInitialAreaRadius, double zInitialAreaRadius, double xVelocityRange, double yVelocityRange, double zVelocityRange) {
      clear();
      for (int i=0; i<count; i++) {
         Point3d position = new Point3d(
            (2*Math.random()-1) * xInitialAreaRadius,
            (2*Math.random()-1) * yInitialAreaRadius,
            (2*Math.random()-1) * zInitialAreaRadius
         );
         Vector3d velocity = new Vector3d(
            (2*Math.random()-1) * xVelocityRange,
            (2*Math.random()-1) * yVelocityRange,
            (2*Math.random()-1) * zVelocityRange
         );
         //Vector3 velocity = new Vector3(0,0,0);
         double mass = minMass + (maxMass-minMass) * Math.random();


         PointMass g = gravityObjectFactory.createGravityObject(mass, position, velocity);

         pointMasses.add(g);
      }


   }

   public synchronized void coMove() {
      Vector3d COMvelocity = new Vector3d();
      Point3d COMposition = new Point3d();
      double totalMass = 0;
      int count = pointMasses.size();
      for (int i=0; i<count; i++) {
         PointMass g = pointMasses.get(i);
         Vector3d velocity = g.velocity;
         Point3d position = g.position;

         // Keep running total of mass.
         totalMass += g.mass;

         // Running addition of weighted velocity vectors.
         COMvelocity.scaleAdd(g.mass, velocity, COMvelocity);
         COMposition.scaleAdd(g.mass, position, COMposition);
      }

      // Final step to work out Center of mass velocity vector.
      COMvelocity.scale(1.0/totalMass);
      COMposition.scale(1.0/totalMass);

      System.out.println("COM velocity: " +  COMvelocity);
      System.out.println("COM position: " +  COMposition);

      // Translate all velocities by COM velocity negated.
      for (int i=0; i<count; i++) {
         PointMass g = pointMasses.get(i);
         g.velocity.sub(COMvelocity);
         g.position.sub(COMposition);
      }

      angularMomentumPrint();

   }

   /**
    * Assumes COM at origin.
    */
   public synchronized void angularMomentumPrint() {
      Vector3d angularMomentum = new Vector3d();
      Vector3d workspace = new Vector3d();
      Vector3d position = new Vector3d();

      double totalMass = 0;
      int count = pointMasses.size();
      for (int i=0; i<count; i++) {
         PointMass g = pointMasses.get(i);
         Vector3d velocity = g.velocity;
         position.set(g.position.x, g.position.y, g.position.z);

         // Keep running total of mass.
         totalMass += g.mass;

         workspace.cross(position, velocity);
         workspace.scale(g.mass);

         angularMomentum.add(workspace);
      }

      // Final step to work out Center of mass velocity vector.
      angularMomentum.scale(1.0/totalMass);

      System.out.println("Angular Momentum: " +  angularMomentum);

   }


   protected List<PointMass> pointMasses = new ArrayList<PointMass>();
   public synchronized List<PointMass> getPointMasses() {
      return new ArrayList<PointMass>(pointMasses);
   }

   /**
    * Makes a copy of the point mass and uses the factory to construct
    * the point mass that then gets added to the system.
    * @param g The point mass that will be copied and added to the system.
    */
   synchronized public void addPointMass(PointMass g) {
      PointMass g1 = gravityObjectFactory.createGravityObject(g.mass, g.position, g.velocity);
      pointMasses.add(g1);
   }

   protected volatile double timeStep = 0.01;
   public double getTimeStep() {
      return timeStep;
   }
   synchronized public void setTimeStep(double newTimeStep) {
      timeStep = newTimeStep;
      System.out.println("Set timestep:" + timeStep);
   }

//   public void nextIteration() {
//      calcIterationAccelerations();
//      calcPositionChanges();
//      timeElapsed += neededTimeStep;
//   }

   /**
    * Calculates the accelerations for this iteration
    */
   synchronized public void calcIterationAccelerations() {


      for (int i=0; i<pointMasses.size(); i++) {
         (pointMasses.get(i)).zeroAccelleration();
      }

      // Reuse this for each method.  Could have reused over object lifecycle, but then
      // that would eliminate thread safety.
      // That is not saying the class is thread safe, but it is easier to make it thread safe.
      AccelerationHolder accelerationHolder = new AccelerationHolder();


      // workspace helps the force law to not have to create new objects,
      // Which should make a tiny imporvement to performance (GC and object burn).
      Vector3d[] workspace = {new Vector3d(), new Vector3d()};

      // Iterate over interactions between all the gravity objects.
      for (int i=0; i<pointMasses.size(); i++) {
         for (int j=0; j<i; j++) {
            PointMass g1 = pointMasses.get(i);
            PointMass g2 = pointMasses.get(j);

            // Use force plugin
            forceLaw.interactionAcceleration(g1, g2, accelerationHolder, workspace);


            g1.addAcceleration(accelerationHolder.object1Acceleration);
            g2.addAcceleration(accelerationHolder.object2Acceleration);

         }
      }

   }


   protected volatile double neededTimeStep;
   protected volatile double timeElapsed;
   private GravityObjectFactory gravityObjectFactory = new DefaultGravityObjectFactory();

   private ForceLaw forceLaw = new GravityForceLaw();

//   synchronized public void calcPositionChanges() {
//      for (int i=0; i<pointMasses.size(); i++) {
//         ((PointMass)pointMasses.get(i)).iterationPositionChange(neededTimeStep);
//      }
//   }

   public double getTimeElapsed() {
      return timeElapsed;
   }
   public void setNeededTimeStep(double newNeededTimeStep) {
      neededTimeStep = newNeededTimeStep;
   }
   public double getNeededTimeStep() {
      return neededTimeStep;
   }
   public synchronized void setGravityObjectFactory(GravityObjectFactory newGravityObjectFactory) {
      gravityObjectFactory = newGravityObjectFactory;
   }
   public synchronized GravityObjectFactory getGravityObjectFactory() {
      return gravityObjectFactory;
   }
   public synchronized void setForceLaw(ForceLaw newForceLaw) {
      forceLaw = newForceLaw;
   }
   public synchronized ForceLaw getForceLaw() {
      return forceLaw;
   }

   private IvpOdeSystemSolver solver;

//   private double timeStep2 = 0.1;
   /**
    * This is part of object state since nextIteration (who uses it) is synchronized.
    */
   private IvpOdeSystemSolver.SolutionStepInfo stepInfo = new IvpOdeSystemSolver.SolutionStepInfo();
   public synchronized void nextIteration() {
      if (pointMasses.size() < 2) return;


      Vector y = systemToVector();
      solver.solve(timeStep, timeElapsed,  y, stepInfo);
      timeStep = stepInfo.stepSizeUsed;


      timeElapsed += timeStep;

      vectorToSystem(y);
   } // end nextIteration()

   /**
    * Calculates the derivative for Runge-Kutta
    */
   public synchronized void evaluate(double time, Vector y, Vector returnValueHolder) {
      vectorToSystem(y);
      calcIterationAccelerations();
      derivativesToVector(returnValueHolder);
      return;
   }

   private synchronized void vectorToSystem(Vector y) {
      int count = pointMasses.size();

      double[] yelements = y.getElements();

      for (int i=0; i<count; i++) {
         PointMass g = pointMasses.get(i);
         int j = i*6;
         g.position.x = yelements[j+0];
         g.position.y = yelements[j+1];
         g.position.z = yelements[j+2];

         g.velocity.x = yelements[j+3];
         g.velocity.y = yelements[j+4];
         g.velocity.z = yelements[j+5];

      }
   }

   /**
    * Copies the derivatives into the returnValueHolder vector.
    * @param returnValueHolder the Vector into which to place the return values.
    */
   private synchronized void derivativesToVector(Vector returnValueHolder){
      int count = pointMasses.size();

      double[] result = returnValueHolder.getElements();
      for (int i=0; i<count; i++) {
         PointMass g = pointMasses.get(i);
         int j = i*6;
         result[j+0] = g.velocity.x;
         result[j+1] = g.velocity.y;
         result[j+2] = g.velocity.z;
         result[j+3] = g.acceleration.x;
         result[j+4] = g.acceleration.y;
         result[j+5] = g.acceleration.z;
      }

      return;
   }

   private synchronized Vector systemToVector(){
      int count = pointMasses.size();

      double[] result = new double [count * 6];
      for (int i=0; i<count; i++) {
         int j = i*6;
         PointMass g = pointMasses.get(i);
         result[j+0] = g.position.x;
         result[j+1] = g.position.y;
         result[j+2] = g.position.z;
         result[j+3] = g.velocity.x;
         result[j+4] = g.velocity.y;
         result[j+5] = g.velocity.z;
      }
      return new Vector(result);
   }

   public synchronized void setSolver(IvpOdeSystemSolver newSolver) {
      solver = newSolver;
      solver.setSystemFunction(this);

   }
   /**
    * The IVP ODE System Solver to plug-in.
    */
   public synchronized IvpOdeSystemSolver getSolver() {
      return solver;
   }


}