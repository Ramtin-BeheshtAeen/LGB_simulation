package org.example.app;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.SwingNode;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import com.jme3.math.Vector3f;
import com.jme3.system.AppSettings;
import com.jme3.system.JmeCanvasContext;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;

public class MainApp extends Application {

    private static final int SIM_WIDTH = 1024;
    private static final int SIM_HEIGHT = 768;
    private static final int SIM_FPS = 60;
    private static final float TARGET_SPEED = 8f;
    private static final float AIRPLANE_SPEED = 15f;
    private static final float LEAD_FACTOR = 0.7f;

    private LaserSimulation jmeApp;
    private boolean simulationOpen = false;

    @Override
    public void start(Stage stage) {
        Label statusLabel = new Label("Status: not started");
        Button startButton = new Button("Start Simulation");
        startButton.setOnAction(e -> {
            if (!simulationOpen) {
                statusLabel.setText("Status: opening simulation...");
                openSimulationWindow();
                simulationOpen = true;
                statusLabel.setText("Status: simulation open");
            }
        });

        VBox root = new VBox(15, startButton, statusLabel);
        root.setPadding(new Insets(20));

        Scene scene = new Scene(root, 320, 120);
        stage.setTitle("Simulation Dashboard");
        stage.setScene(scene);
        stage.show();
    }

    private void openSimulationWindow() {
        jmeApp = new LaserSimulation(
                new Vector3f(TARGET_SPEED, 0, 0),
                new Vector3f(AIRPLANE_SPEED, 0, 0),
                LEAD_FACTOR,
                new Vector3f(0, 35, 80)
        );

        AppSettings settings = new AppSettings(true);
        settings.setAudioRenderer(null);
        settings.setResolution(SIM_WIDTH, SIM_HEIGHT);
        settings.setFrameRate(SIM_FPS);
        settings.setRenderer(AppSettings.LWJGL_OPENGL32);

        jmeApp.setSettings(settings);
        jmeApp.createCanvas();

        JmeCanvasContext ctx = (JmeCanvasContext) jmeApp.getContext();
        Canvas jmeCanvas = ctx.getCanvas();

        JPanel swingPanel = new JPanel(new BorderLayout());
        swingPanel.add(jmeCanvas, BorderLayout.CENTER);
        swingPanel.setPreferredSize(new Dimension(1024, 768));

        SwingNode swingNode = new SwingNode();
        SwingUtilities.invokeLater(() -> swingNode.setContent(swingPanel));

        VBox sideBar = new VBox(15);
        sideBar.setPadding(new Insets(20));
        sideBar.setStyle("-fx-background-color: #2b2b2b; -fx-min-width: 200px;");

        Button bombButton = new Button("Release Bomb");
        bombButton.setMaxWidth(Double.MAX_VALUE);
        bombButton.setOnAction(e -> jmeApp.enqueue(jmeApp::triggerBomb));

        Button stopButton = new Button("Stop Simulation");
        stopButton.setMaxWidth(Double.MAX_VALUE);
        stopButton.setOnAction(e -> {
            jmeApp.stop();
            simulationOpen = false;
        });

        sideBar.getChildren().addAll(new Label("Missile Sim"), bombButton, stopButton);

        BorderPane simulationRoot = new BorderPane();
        simulationRoot.setCenter(swingNode);
        simulationRoot.setLeft(sideBar);

        Scene simulationScene = new Scene(simulationRoot, 1280, 800);

        Stage simulationStage = new Stage();
        simulationStage.setTitle("Missile Intercept Simulation");
        simulationStage.setScene(simulationScene);
        simulationStage.setOnCloseRequest(e -> {
            jmeApp.stop();
            simulationOpen = false;
        });
        simulationStage.show();

        // start jME rendering after slight delay for Linux
        new Thread(() -> {
            try {
                Thread.sleep(700);
                jmeApp.startCanvas();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }).start();
    }

    public static void main(String[] args) {
        System.setProperty("jdk.gtk.version", "3");
        launch(args);
    }
}
