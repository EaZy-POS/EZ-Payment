/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.util.swings;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;

/**
 *
 * @author Lutfi
 */
public class PanelDecorator {
    public static void paint(JPanel compnent){
        Graphics2D g2 = (Graphics2D) compnent.getGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        GradientPaint g = new GradientPaint(0, 0, Color.decode("#1CB5E0"), 0, compnent.getHeight(), Color.decode("#000046"));
        g2.setPaint(g);
        g2.fillRoundRect(0, 0, compnent.getWidth(), compnent.getHeight(), 15, 15);
        g2.fillRect(compnent.getWidth() - 20, 0, compnent.getWidth(), compnent.getHeight());
        compnent.paintComponents(compnent.getGraphics());
    }
}
