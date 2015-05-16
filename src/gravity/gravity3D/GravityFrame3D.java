package gravity.gravity3D;

import gravity.*;
import simulation.mechanical.springs.SpringForceLaw;
import simulation.mechanical.springs.SpringDampedForceLaw;
import simulation.mechanical.ForceLaw;
import gravity.GravityGui;

import javax.vecmath.Vector3d;
import javax.vecmath.Point3d;

import com.sun.j3d.utils.behaviors.mouse.MouseBehavior;
import com.sun.j3d.utils.behaviors.mouse.MouseTranslate;
import com.sun.j3d.utils.behaviors.mouse.MouseZoom;
import vgjava3d.MouseRotate2;
import java.applet.Applet;
import java.awt.event.*;
import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.geometry.Cone;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.universe.*;
import javax.media.j3d.*;
import javax.vecmath.*;

import javax.swing.*;
import java.awt.*;

import java.util.List;
import java.util.ArrayList;
import vg.gravity.gui.common.*;
import vg.animation.gui.*;

/**
 * Title:        Gravity 3D Simulation
 * Description:
 * Copyright:    Copyright (c) 2001 Randall Britten
 * Company:
 * @author        Randall Britten
 * @version 1.0
 */




public class GravityFrame3D extends JFrame implements GravityObjectFactory, GravityGui {

   private VirtualUniverse u = null;
   private ViewingPlatform viewingPlatform;
   private Locale locale;


   Appearance ap = new Appearance();
   TransformGroup rootTransGroup = new TransformGroup();
   TransformGroup viewTfm;
   GravityCanvas3D gravityCanvas;
   MouseRotate2 behaviorRotate;

   public BranchGroup createSceneGraph(VirtualUniverse u) {
      // Create the root of the branch graph
      BranchGroup objRoot = new BranchGroup();


      //      ViewingPlatform viewingPlatform = u.getViewingPlatform();
      viewTfm = viewingPlatform.getViewPlatformTransform();
      // Create bounds for following behaviours.
      BoundingSphere mbounds = new BoundingSphere(new Point3d(0.0,0.0,0.0), 100000.0);
      //      viewingPlatform.setCapability(viewingPlatform.ALLOW_BOUNDS_WRITE);
      //      viewingPlatform.setBounds(mbounds);



      // Create the rotate behavior node
      behaviorRotate = new MouseRotate2();
      behaviorRotate.setFactor(0.0020d);
      behaviorRotate.setTransformGroup(rootTransGroup);
      objRoot.addChild(behaviorRotate);
      behaviorRotate.setSchedulingBounds(mbounds);

      // Create the zoom behavior node
      MouseZoom behaviorZTranslate = new MouseZoom();
      behaviorZTranslate.setFactor(2);
      behaviorZTranslate.setTransformGroup(rootTransGroup);
      objRoot.addChild(behaviorZTranslate);
      behaviorZTranslate.setSchedulingBounds(mbounds);

      // Create the translate behavior node
      MouseTranslate behaviorXYTranslate = new MouseTranslate();
      behaviorXYTranslate.setFactor(2);
      behaviorXYTranslate.setTransformGroup(rootTransGroup);
      objRoot.addChild(behaviorXYTranslate);
      behaviorXYTranslate.setSchedulingBounds(mbounds);

      rootTransGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
      rootTransGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);

      BranchGroup spotLightBG = new BranchGroup();
      SpotLight spotLight = new SpotLight();
      spotLight.setBounds(mbounds);
      spotLight.setColor(new Color3f(1f, 0, 0));
      spotLight.setEnable(true);
      spotLight.setDirection(0, 0, -1);
      spotLightBG.addChild(spotLight);
      viewTfm.addChild( spotLightBG );


      //      ap.setTransparencyAttributes(new TransparencyAttributes(TransparencyAttributes.NICEST, 0.9f));

      PolygonAttributes poly1 = new PolygonAttributes();
      //            poly1.setPolygonMode(PolygonAttributes.POLYGON_LINE);
//      poly1.setCullFace(PolygonAttributes.CULL_NONE);
      //      poly1.setBackFaceNormalFlip(true);
      ap.setPolygonAttributes(poly1);
      ap.setMaterial(new Material() );

      objRoot.addChild(rootTransGroup);

      rootTransGroup.setCapability(TransformGroup.ALLOW_CHILDREN_EXTEND);
      rootTransGroup.setCapability(TransformGroup.ALLOW_CHILDREN_READ);
      rootTransGroup.setCapability(TransformGroup.ALLOW_CHILDREN_WRITE);

      //      rootTransGroup.setTransform(new Transform3D(new Matrix3d(), new Vector3d(), 1d));




      // Create bounds for following lights
      BoundingSphere bounds = new BoundingSphere(new Point3d(0.0,0.0,0.0), 1e40);

      // Set up the global lights

      AmbientLight aLgt = new AmbientLight(
      new Color3f(0.4f, 0.4f, 0.4f)
      );
      aLgt.setInfluencingBounds(bounds);

      // Blue from y+
      DirectionalLight lgt1 = new DirectionalLight(
      new Color3f(0f, 0f, 1f),
      new Vector3f(0f, -1f, 0f)
      );

      //Red from y-
      DirectionalLight lgt2 = new DirectionalLight(
      new Color3f(1f, 0f, 0f),
      new Vector3f(0f,1f,0f)
      );

      //Green from x-
      DirectionalLight lgt3 = new DirectionalLight(
      new Color3f(0f, 1f, 0f),
      new Vector3f(1f,0f,0f)
      );

      lgt1.setInfluencingBounds(bounds);
      lgt2.setInfluencingBounds(bounds);
      lgt3.setInfluencingBounds(bounds);

      objRoot.addChild(aLgt);
      rootTransGroup.addChild(lgt1);
      rootTransGroup.addChild(lgt2);
      rootTransGroup.addChild(lgt3);



      // Have Java 3D perform optimizations on this scene graph.
      objRoot.compile();


      return objRoot;
   }

   /**
    * Called by the factory method createGravityObject in this class.
    */
   TransformGroup addSphere(double radius, double x, double y, double z) {
      BranchGroup bg = new BranchGroup();
      bg.setCapability(BranchGroup.ALLOW_DETACH);
      Sphere sphere = new Sphere((float)radius);
      sphere.setAppearance(ap);

      Transform3D transform = new Transform3D();
      transform.setTranslation(new Vector3d(x, y, z) );
      TransformGroup transGroup = new TransformGroup(transform);
      transGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
      transGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

      transGroup.addChild(sphere);
      bg.addChild(transGroup);
      rootTransGroup.addChild(bg);

      return transGroup;

   }

   public PointMass createGravityObject(double mass, Point3d position, Vector3d velocity) {
      double radius = Math.pow(mass, 1.0/3.0); // Assume density of 1.

      TransformGroup transformGroup = addSphere(radius, position.x, position.y, position.z);
      PointMass3D gravityObject3D = new PointMass3D(mass, position, velocity, transformGroup);

      return gravityObject3D;

   }


   GravityController gController;


   public void destroy() {
      u.removeAllLocales();
   }

   public GravityFrame3D(GravityController gController) {
      this.gController = gController;

      try {
         jbInit();
//         gController.setGravityGui(gravityCanvas);
      }
      catch(Exception e) {
         e.printStackTrace();
      }
      this.setVisible(true);
   }
   private void jbInit() throws Exception {

      this.getContentPane().setLayout(new BorderLayout());
      this.setTitle("Gravity 3D by Randall Britten, Copyright (c) 2002");
      gravityCanvas = new GravityCanvas3D(gController);

      Canvas3D c = new Canvas3D(SimpleUniverse.getPreferredConfiguration());



      jPanel_3DView.setLayout(flowLayout1);
      physicsAllPanel1.setGController(gController);
      filePanel.setGController(gController);
      jButton_ResetView.setMnemonic('V');
      jButton_ResetView.setText("Reset View");
      jButton_ResetView.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(ActionEvent e) {
            jButton_ResetView_actionPerformed(e);
         }
      });
      jButton_Rotated.setMnemonic('T');
      jButton_Rotated.setText("Target of rotate");
      jButton_Rotated.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(ActionEvent e) {
            jButton_Rotated_actionPerformed(e);
         }
      });
      solverPanel1.setGController(gController);
      physicsParametersPanel1.setGController(gController);
      animationPanel1.setAnimationController(gController);
      getContentPane().add("Center", c);

      // Create a simple scene and attach it to the virtual universe
      SimpleUniverse su = new SimpleUniverse(c);

      //      System.out.println("Backclip:"+ c.getView().getBackClipDistance());
      c.getView().setBackClipDistance(1e5);


      u = su;

      viewingPlatform = su.getViewingPlatform();
      //      u = createUniverse(c);

      BranchGroup scene = createSceneGraph(u);



      // This will move the ViewPlatform back a bit so the
      // objects in the scene can be viewed.
      viewingPlatform.setNominalViewingTransform();
      Transform3D vtf3D = new Transform3D();
      vtf3D.transform(new Vector3d(0,0,100000));
      viewingPlatform.getViewPlatformTransform().setTransform(vtf3D);

      //      locale.addBranchGraph(scene);
      su.addBranchGraph(scene);




      this.getContentPane().add(jToolBar1, BorderLayout.NORTH);
      jToolBar1.add(jTabbedPane1, null);
      jTabbedPane1.add(animationPanel1, "Animation");
      jTabbedPane1.add(jPanel_3DView, "3D View");
      jPanel_3DView.add(jButton_ResetView, null);
      jPanel_3DView.add(jButton_Rotated, null);
      jTabbedPane1.add(solverPanel1, "Numerical Solver");
      jTabbedPane1.add(physicsParametersPanel1, "Physics Parameters");
      jTabbedPane1.add(physicsAllPanel1, "Physics System");
      jTabbedPane1.add(filePanel, "File");
      this.setSize(1000,700);

      resetView();


   }

   public void clear() {
      this.gravityCanvas.clear();


      int num = rootTransGroup.numChildren();
      // Bit of a hack, don't remove the 3 lights from rootTrans.
      for (int i=num-1; i>=3; i--) {
         //         System.out.println("Removing:" + i);
         rootTransGroup.removeChild(i);
      }


   }


   VirtualUniverse createUniverse(Canvas3D c) {
      VirtualUniverse vu = new VirtualUniverse();
      locale = new Locale(vu);
      BranchGroup vbg = new BranchGroup();
      View view = new View();
      viewingPlatform = new ViewingPlatform();
      locale.addBranchGraph(viewingPlatform);
      ViewPlatform viewPlatform = viewingPlatform.getViewPlatform();
//      PhysicalBody physicalBody = new PhysicalBody();
//      PhysicalEnvironment physicalEnvironment = new PhysicalEnvironment();
      view.attachViewPlatform(viewPlatform);
//      view.setPhysicalBody(physicalBody);
//      view.setPhysicalEnvironment(physicalEnvironment);
      view.addCanvas3D(c);

      return vu;


   }


   /**Overridden so we can exit when window is closed*/
   protected void processWindowEvent(WindowEvent e) {
      super.processWindowEvent(e);
      if (e.getID() == WindowEvent.WINDOW_CLOSING) {
         System.exit(0);
      }
   }









   private void jButton_ResetView_actionPerformed(ActionEvent e) {
      resetView();
   }

   private void resetView() {
      Matrix3d identity3d = new Matrix3d();
      identity3d.setIdentity();

      TransformGroup viewTransGroup = viewingPlatform.getViewPlatformTransform();
      viewTransGroup.setTransform(new Transform3D(identity3d, new Vector3d(0,0,100), 1.0) );
      rootTransGroup.setTransform(new Transform3D());
   }

   // view rotated true means view rotated, false means root trans rotated.
   private boolean viewRotated = true;
   void jButton_Rotated_actionPerformed(ActionEvent e) {
      viewRotated = !viewRotated;
      if (viewRotated) {
         behaviorRotate.setTransformGroup(viewingPlatform.getViewPlatformTransform());
         behaviorRotate.setInvert(true);
         jButton_Rotated.setText("Target Rotated to root");
      } else {
         behaviorRotate.setTransformGroup(rootTransGroup);
         behaviorRotate.setInvert(false);
         jButton_Rotated.setText("Target Rotated to view");
      }
   }

   public void updateFrame() {
      gravityCanvas.repaint();
      repaint();
   }


   List toolbars = new ArrayList();
   int toolbarCount;
   JToolBar jToolBar1 = new JToolBar();
   JPanel jPanel_3DView = new JPanel();
   PhysicsAllPanel physicsAllPanel1 = new PhysicsAllPanel();
   JButton jButton_ResetView = new JButton();
   JTabbedPane jTabbedPane1 = new JTabbedPane();
   JButton jButton_Rotated = new JButton();
   SolverPanel solverPanel1 = new SolverPanel();
   FlowLayout flowLayout1 = new FlowLayout();
   PhysicsParametersPanel physicsParametersPanel1 = new PhysicsParametersPanel();
   AnimationPanel animationPanel1 = new AnimationPanel();
   FilePanel filePanel = new FilePanel();



}
