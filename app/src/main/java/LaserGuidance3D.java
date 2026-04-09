<<<<<<<< HEAD:app/src/main/java/LaserGuidance3D.java
========
package app;

>>>>>>>> 4f3c6d4ee13f4bb39c406b11691898406733aa12:src/main/java/app/LaserGuidance3D.java
import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;


public class LaserGuidance3D extends SimpleApplication {
  
    @Override
    public void simpleInitApp() {
        Box b = new Box(1, 1, 1);
        Geometry geom = new Geometry("Box", b);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Blue);
        geom.setMaterial(mat);
        rootNode.attachChild(geom); // Adds the cube to the scene
    }
}



