package co.id.ez.ezpay.util.swings;

import co.id.ez.ezpay.enums.Themes;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JPanel;

public class RoundPanel extends JPanel {

    private boolean isGradienPaint = true;

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
        repaint();
    }

    private int round;

    public RoundPanel() {
        setOpaque(false);
    }

    public void unGradient() {
        isGradienPaint = false;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Area area = new Area(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), round, round));
        g2.setColor(getBackground());
        g2.fill(area);
        if (isGradienPaint) {
            area.subtract(new Area(new Rectangle2D.Double(0, 0, getWidth(), getHeight() - 3)));
            g2.setPaint(new GradientPaint(0, 0, Color.decode(Themes.MAIN_COLOR_1.getValue()), getWidth(), 0, Color.decode(Themes.MAIN_COLOR_2.getValue())));
            g2.fill(area);
            g2.dispose();
        }
        super.paintComponent(g);
    }
}
