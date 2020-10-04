# Gravity3D - By Randall Britten
3D Gravity simulator (old code from 2002, some minor revision in 2015).

![gravity3d - screenshot - v0 1 1 - alpha](https://cloud.githubusercontent.com/assets/3136161/7761435/dceb4fb4-0079-11e5-9e29-9e3decc4b7c3.png)

This is a Java application for playing with a simulation of classical Newtonian gravity in 3D.  The simulation is interactive, and the visualisation can be rotated, panned and zoomed in/out.  The simulation can also be viewed in 2D by starting the 2D version.  In 3D mode, use the "Random" or "One-by-one" buttons ("Physics System" tab) to add objects.  In 2D version, click or alt-drag to add (or use the same dialogs as 3D version).  Rudimentary support for loading and saving is also available, but use your file manager to make copies of the data file (`gravity.dat`) if you want to keep multiple files.

Building and running 3D version on a Mac in October 2020:
(Assumes recent java and Apache Ant installed.)

```
git clone https://github.com/vertexcite/gravity3d.git 
cd gravity3d
ant get.deps
ant
cd out/artifacts/gravity_3d_jars/
./launcher
```

If you have Nix, getting java and ant is easily done, e.g. :
```
nix-shell -p ant jdk14
```

Requirements:
- Java v1.8 or later.
- 3D version depends on Java 3D (http://jogamp.org/deployment/java3d/).
- 2D version needs just `vecmath.jar` from the set of Java 3D libraries.

"Executables" (pre-releases) available for Mac OS X, (these assume Java is available on your system) - see http://github.com/vertexcite/gravity3d .  

For running the downloaded 2D version:

```
cd Gravity3D.app/Contents/MacOS
./launcher
```

Todo: package 2D version in a plain zip, since running using `.app` seems to be a hassle on recent MacOS versions.
