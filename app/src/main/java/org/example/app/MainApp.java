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

import com.jme3.app.SimpleApplication;
import com.jme3.app.StatsAppState;
import com.jme3.math.Vector3f;
import com.jme3.system.AppSettings;
import com.jme3.system.JmeCanvasContext;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;


// Change "SimpleApplication" to "LaserGuidance3D"
public class MainApp { 

    public static void main(String[] args) {
        //**!** */ 1. Start JavaFX toolkit first
        Platform.startup(() -> {});
        LaserGuidance3D app = new LaserGuidance3D(); // Start THIS class
        
        //javaFX Panel Setting
        AppSettings settings = new AppSettings(true);
        settings.setFrameRate(60);
        
        app.setSettings(settings);
        // Use Canvas mode to allow overlaying
        //createCanvas() + startCanvas() won't work with jme3-lwjgl3
        //app.createCanvas(); 
        //app.startCanvas();
	
	//-- Right Column: control Panel --------------------------------------
	SimPanel panel = new SimPanel();
	app.setPanel(panel);
	panel.setBorder(new MatteBorder(0, 1, 0, 0, new color(45, 45, 58)));


        app.start();//start after setting up
    

    // 3. Open JavaFX window separately
    Platform.runLater(() -> {
            Stage stage = new Stage();
            Button btn = new Button("Launch");
            stage.setScene(new Scene(new VBox(btn), 300, 600));
            stage.show();
        });
    }
}
