package app;

import com.jme3.app.SimpleApplication;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;

public class JavaFxUI {

    private static JavaFxUI instance;
    private final Group root = new Group();
    private Scene scene;

    private JavaFxUI() {
        // Start the toolkit so we don't crash
        try {
            Platform.startup(() -> {});
        } catch (IllegalStateException e) { }
    }

    public static void initialize(SimpleApplication app) {
        if (instance == null) {
            instance = new JavaFxUI();
            // Create a transparent scene the size of the game window
            int width = app.getContext().getSettings().getWidth();
            int height = app.getContext().getSettings().getHeight();
            instance.scene = new Scene(instance.root, width, height, Color.TRANSPARENT);
        }
    }

    public static JavaFxUI getInstance() {
        return instance;
    }

    public void attachChild(Button button) {
        // This is where the magic happens! We add the button to the 'root'
        Platform.runLater(() -> {
            button.setLayoutX(100); // Move it so it's not stuck in the corner
            button.setLayoutY(100);
            root.getChildren().add(button);
            System.out.println("Button added to JavaFX Root!");
        });
    }
}