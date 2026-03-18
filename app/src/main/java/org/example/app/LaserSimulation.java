package org.example.app;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Sphere;
import com.jme3.system.AppSettings;

public class LaserSimulation extends SimpleApplication {

    private Geometry target, airplane, bomb;
    private Geometry camTarget = null;

    private Vector3f targetVelocity;
    private Vector3f airplaneVelocity;
    private float leadFactor;
    private Vector3f initialCamLocation;

    private boolean bombReleased = false;

    public LaserSimulation() {
        this(new Vector3f(8f, 0, 0), new Vector3f(15f, 0, 0), 0.7f, new Vector3f(0, 35, 80));
    }

    public LaserSimulation(Vector3f targetVelocity, Vector3f airplaneVelocity, float leadFactor, Vector3f initialCamLocation) {
        this.targetVelocity = targetVelocity;
        this.airplaneVelocity = airplaneVelocity;
        this.leadFactor = leadFactor;
        this.initialCamLocation = initialCamLocation;

        // Disable the splash screen and settings dialog
        this.setShowSettings(false);
    }

    @Override
    public void simpleInitApp() {
        // Basic camera and background
        cam.setLocation(initialCamLocation);
        cam.lookAt(Vector3f.ZERO, Vector3f.UNIT_Y);
        viewPort.setBackgroundColor(ColorRGBA.DarkGray);

        // Floor
        Box floorBox = new Box(200, 0.1f, 200);
        Geometry floor = new Geometry("Floor", floorBox);
        Material floorMat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        floorMat.setColor("Color", ColorRGBA.Gray);
        floor.setMaterial(floorMat);
        rootNode.attachChild(floor);

        // Entities
        target = createBox("Target", ColorRGBA.Red, new Vector3f(-50, 1, 0));
        airplane = createBox("Airplane", ColorRGBA.Blue, new Vector3f(-60, 50, 0));

        flyCam.setMoveSpeed(100);

        // Leave stats on for debugging if needed
        setDisplayFps(true);
        setDisplayStatView(true);

        // If this fails, the environment is missing GLX/OpenGL support
        try {
            stateManager.detach(stateManager.getState(com.jme3.app.StatsAppState.class));
        } catch (Exception ex) {
            // ignore - may not be available before context init
        }
    }

    private Geometry createBox(String name, ColorRGBA color, Vector3f pos) {
        Box b = new Box(1, 1, 1);
        Geometry geo = new Geometry(name, b);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", color);
        geo.setMaterial(mat);
        geo.setLocalTranslation(pos);
        rootNode.attachChild(geo);
        return geo;
    }

    public void triggerBomb() {
        if (!bombReleased) {
            Sphere sphere = new Sphere(16, 16, 0.5f);
            bomb = new Geometry("Bomb", sphere);
            Material bombMat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
            bombMat.setColor("Color", ColorRGBA.Yellow);
            bomb.setMaterial(bombMat);
            bomb.setLocalTranslation(airplane.getLocalTranslation().clone());
            rootNode.attachChild(bomb);
            bombReleased = true;
        }
    }

    public void setTargetVelocity(Vector3f vel) {
        targetVelocity = vel;
    }

    public void setAirplaneVelocity(Vector3f vel) {
        airplaneVelocity = vel;
    }

    public void setLeadFactor(float leadFactor) {
        this.leadFactor = leadFactor;
    }

    public void setInitialCamLocation(Vector3f camLocation) {
        this.initialCamLocation = camLocation;
    }

    @Override
    public void simpleUpdate(float tpf) {
        target.move(targetVelocity.mult(tpf));
        airplane.move(airplaneVelocity.mult(tpf));

        if (camTarget != null) {
            Vector3f desired = camTarget.getLocalTranslation().add(new Vector3f(0, 10, 30));
            cam.setLocation(cam.getLocation().interpolateLocal(desired, 0.1f));
            cam.lookAt(camTarget.getLocalTranslation(), Vector3f.UNIT_Y);
        }

        if (bombReleased && bomb != null) {
            Vector3f leadVector = targetVelocity.mult(leadFactor);
            Vector3f laserSpot = target.getLocalTranslation().add(leadVector);
            Vector3f bombDir = laserSpot.subtract(bomb.getLocalTranslation()).normalizeLocal();
            bomb.move(bombDir.mult(30f * tpf));

            if (bomb.getLocalTranslation().y <= 1.0f) {
                rootNode.detachChild(bomb);
                bombReleased = false;
                bomb = null;
            }
        }
    }
}