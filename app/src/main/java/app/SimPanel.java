package app;
import javax.swing.JPanel;

import javax.swing.*;
import java.awt.*;

public class SimPanel extends JPanel{
    //Slider
    private JSlider sldMass;
    private JLabel  valMass;
    private int mass;
    
    // Colors
    private static final Color BG       = new Color(18, 18, 22);
    private static final Color PANEL_BG = new Color(24, 24, 30);
    private static final Color BORDER_C = new Color(45, 45, 55);
    private static final Color ACCENT   = new Color(40, 160, 255);
    private static final Color TEXT     = new Color(210, 215, 230);
    private static final Color DIM_TEXT = new Color(120, 125, 140);
    private static final Color GREEN    = new Color(80, 220, 120);
    private static final Color RED_C    = new Color(255, 80, 60);
    private static final Color YELLOW   = new Color(255, 200, 60);



    public SimPanel() {
	add(makeParamsPanel());

	setBackground(BG);
	setPreferredSize(new Dimension(280, 900));
    }

    public JPanel makeParamsPanel() {

	JPanel p = section("Parameters");
	p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));

	valMass = new JLabel("250 kg");
	
	sldMass = slider(p, "Mass", valMass,      5, 500, 250, 1);
	sldMass.addChangeListener(e -> {
	    mass = sldMass.getValue();
	    valMass.setText(mass + "kg");
	});

	return p;
    }

    private JPanel section(String title) {
        JPanel outer = new JPanel();
        outer.setBackground(PANEL_BG);
        outer.setLayout(new BoxLayout(outer, BoxLayout.Y_AXIS));
        outer.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_C, 1),
            BorderFactory.createEmptyBorder(6, 10, 8, 10)
        ));

        JLabel hdr = new JLabel(title);
        hdr.setFont(new Font("Monospaced", Font.BOLD, 10));
        hdr.setForeground(DIM_TEXT);
        hdr.setBorder(BorderFactory.createEmptyBorder(0, 0, 6, 0));
        outer.add(hdr);
        return outer;
    }


    private JSlider slider(JPanel parent, String label, JLabel valueLabel,
                           int min, int max, int val, int divisor) {
        JPanel row = new JPanel(new BorderLayout(4, 0));
        row.setBackground(PANEL_BG);
        row.setBorder(BorderFactory.createEmptyBorder(4, 0, 4, 0));

        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Monospaced", Font.PLAIN, 10));
        lbl.setForeground(DIM_TEXT);
        lbl.setPreferredSize(new Dimension(90, 16));

        valueLabel.setFont(new Font("Monospaced", Font.BOLD, 10));
        valueLabel.setForeground(ACCENT);
        valueLabel.setPreferredSize(new Dimension(56, 16));
        valueLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        JSlider sld = new JSlider(min, max, val);
        sld.setBackground(PANEL_BG);
        UIManager.put("Slider.thumb", ACCENT);
        sld.setForeground(ACCENT);

        row.add(lbl,        BorderLayout.WEST);
        row.add(sld,        BorderLayout.CENTER);
        row.add(valueLabel, BorderLayout.EAST);
        parent.add(row);
        return sld;
    }



}


