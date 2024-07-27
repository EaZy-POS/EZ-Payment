package co.id.ez.ezpay.util.swings.slideshow;

import java.awt.Component;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;

public class Slideshow {

    private final JPanel panel;
    private final Animator animator;
    private final MigLayout layout;
    private Component componentShow;
    private Component componentOut;

    public Slideshow(JPanel panelTarget) {
        layout = new MigLayout("inset 0");
        panel = panelTarget;
        panel.setOpaque(false);
        TimingTarget target = new TimingTargetAdapter() {
            @Override
            public void begin() {
                componentShow.setVisible(true);
                componentOut.setVisible(true);
            }

            @Override
            public void timingEvent(float fraction) {
                double width = panel.getWidth();
                int location = (int) (width * fraction);
                int locationShow = (int) (width * (1f - fraction));
                layout.setComponentConstraints(componentShow, "pos " + locationShow + " 0 100% 100%, w 100%!");
                layout.setComponentConstraints(componentOut, "pos -" + location + " 0 " + (width - location) + " 100%");
                panel.revalidate();
            }

            @Override
            public void end() {
                componentOut.setVisible(false);
                layout.setComponentConstraints(componentShow, "pos 0 0 100% 100%, width 100%");
            }
        };
        animator = new Animator(500, target);
        animator.setResolution(0);
        animator.setAcceleration(0.5f);
        animator.setDeceleration(0.5f);
    }

    public void slideTo(Component out, Component show) {
        componentOut = out;
        componentShow = show;
        animator.start();
    }
}
