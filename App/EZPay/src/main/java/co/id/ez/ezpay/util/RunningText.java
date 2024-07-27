/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.util;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JLabel;

/**
 *
 * @author Lutfi
 */
public class RunningText {

    private Thread runner;
    
    private int x;
    private int y;

    public void start(JLabel target) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        int start = target.getWidth() - (target.getWidth() - (target.getText().length() + 230));
        x = - start;
        y = target.getY();
        
        runner = new Thread(() -> {
            while (true) {
                if (x >= width) {
                    x = - start;
                }
                x++;
                new Thread(()->{
                    target.setLocation(x, y);
                }).start();

                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                }
            }
        });
        runner.start();
    }

    public void stop(){
        if(runner != null){
            runner.stop();
        }
    }
}
