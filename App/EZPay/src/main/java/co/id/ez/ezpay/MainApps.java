/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay;

import co.id.ez.ezpay.app.Common;
import co.id.ez.ezpay.view.base.SplashScreen;
import com.jtattoo.plaf.graphite.GraphiteLookAndFeel;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Properties;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Lutfi
 */
public class MainApps {

    public static final MainApps instance = new MainApps();
    private static final Properties props = System.getProperties();

    public static void main(String[] args) {
        try {
            System.setOut(new PrintStream(new FileOutputStream("logs/application.out")));
            props.load(instance.getClass().getResourceAsStream("/properties.properties"));
            instance.setLookAndFeel(false);
            new SplashScreen().setVisible(true);
        } catch (IOException ex) {
            Common.errorLog("[Exception] Failed read application properties", ex);
            Common.showMessage("Fatal Error!\n" + ex.getMessage(), JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }catch (Exception ex) {
            Common.errorLog("[Exception] Failed start application", ex);
            Common.showMessage("Fatal Error!\n" + ex.getMessage(), JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }

    public void setLookAndFeel(boolean isDecorate) {
        try {
            props.put("windowDecoration", isDecorate ? "on" : "off");
            GraphiteLookAndFeel.setCurrentTheme(props);
            UIManager.setLookAndFeel(new GraphiteLookAndFeel());
            UIManager.put("TabbedPane.contentOpaque", false);
        } catch (UnsupportedLookAndFeelException ex) {
            Common.errorLog("[UnsupportedLookAndFeelException] Failed start application", ex);
        }
    }
}
