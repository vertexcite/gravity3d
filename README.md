# Gravity3D - By Randall Britten
3D Java simulator (old code from 2002, some minor revision in 2015).

![gravity3d - screenshot - v0 1 1 - alpha](https://cloud.githubusercontent.com/assets/3136161/7761435/dceb4fb4-0079-11e5-9e29-9e3decc4b7c3.png)

This is a pure Java application for playing with a simulation of classical Newtonian gravity in 3D.  The simulation is interactive, and the visualisation can be rotated, panned and zoomed in/out.  The simulation can also be viewed in 2D by starting the 2D version.  In 3D mode, use the "Random" or "One-by-one" buttons ("Physics System" tab) to add objects.  In 2D version, click or alt-drag to add (or use the same dialogs as 3D version).  Rudimentary support for loading and saving is also available, but use your file manager to make copies of the data file (`gravity.dat`) if you want to keep multiple files.

Requirements:
- Java v1.7 or v1.8.  (One way to manage multiple Java versions: https://github.com/shyiko/jabba )
- 3D version depends on Java 3D (http://jogamp.org/deployment/java3d/).
- 2D version needs just `vecmath.jar` from the set of Java 3D libraries.

Executables (pre-releases) available for Mac OS X, (these assume Java is available on your system) - see http://github.com/vertexcite/gravity3d .  These only work if the system default Java version is 1.7 or 1.8.  

Otherwise:

```
# This worked, there may be other JDK's that also work fine: 
# Download jdk-8u211-macosx-x64.dmg from Oracle ( https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html )
jabba install 1.8.0-custom=dmg+file:///Users/myuser/Downloads/jdk-8u211-macosx-x64.dmg
jabba use 1.8.0-custom
cd Gravity3D.app/Contents/MacOS
./launcher
```
