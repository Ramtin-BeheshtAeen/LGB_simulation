import com.jme3.system.AppSettings;

import javax.swing.*;

import javax.swing.SwingUtilities;
import java.awt.BorderLayout;


// Change "SimpleApplication" to "LaserGuidance3D"
public class MainApp { 

    public static void main(String[] args) {
        LaserGuidance3D app = new LaserGuidance3D(); // Start THIS class
        
        //javaFX Panel Setting
        AppSettings settings = new AppSettings(true);
        settings.setFrameRate(60);
        
        app.setSettings(settings);
	
	SwingUtilities.invokeLater( () -> {
	    try {UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName()); }
	    catch (Exception ignored) {}
	    
	    JFrame frame = new JFrame("Simulation");

 	    SimPanel panel = new SimPanel();
 //            panel.setBorder(new MatteBorder(0, 1, 0, 0, new Color(45, 45, 58)));
	    
	    //app.setPanel(panel);
	    frame.add(panel, BorderLayout.CENTER);
	    frame.pack();
	    frame.setLocationRelativeTo(null);
	    frame.setVisible(true);
	    new Thread(() -> app.start(), "jME-start").start();
	});
    }

}
