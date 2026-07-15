/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author acer
 */
  // Change to your package name

import java.awt.*;
import javax.swing.*;

public class GlassPanel extends JPanel {

    public GlassPanel() {
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {

        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        // Glass Color (White with transparency)
        g2.setColor(new Color(255, 255, 255, 80));

        // Rounded Panel
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);

        // White Border
        g2.setStroke(new BasicStroke(2));
        g2.setColor(new Color(255, 255, 255, 120));
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 30, 30);

        g2.dispose();

        super.paintComponent(g);
    }
}
