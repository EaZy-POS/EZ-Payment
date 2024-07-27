/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.util.swings.input;

import co.id.ez.ezpay.app.Common;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import javax.swing.JTextField;

/**
 *
 * @author RCS
 */
public class InputTextType {

    public static void makeNumericInput(JTextField... fields) {
        for (JTextField field : fields) {
            field.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent evt) {
                    char c = evt.getKeyChar();
                    if (!(Character.isDigit(c)
                            || c == KeyEvent.VK_BACK_SPACE
                            || c == KeyEvent.VK_DELETE)) {
                        evt.consume();
                        Toolkit.getDefaultToolkit().beep();
                    }
                }

                @Override
                public void keyTyped(KeyEvent e) {
                    keyPressed(e);
                }
            });
        }
    }
    
    public static void makeNumericInput(int length, JTextField... fields) {
        for (JTextField field : fields) {
            field.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent evt) {
                    char c = evt.getKeyChar();
                    if (!(Character.isDigit(c)
                            || c == KeyEvent.VK_BACK_SPACE
                            || c == KeyEvent.VK_DELETE) || field.getText().length() <= length) {
                        evt.consume();
                        Toolkit.getDefaultToolkit().beep();
                    }
                }

                @Override
                public void keyTyped(KeyEvent e) {
                    keyPressed(e);
                }
            });
        }
    }
    
    public static void makeLimitedInput(int length, JTextField... fields) {
        for (JTextField field : fields) {
            field.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent evt) {
                    char c = evt.getKeyChar();
                    if (field.getText().length() <= length) {
                        evt.consume();
                        Toolkit.getDefaultToolkit().beep();
                    }
                }

                @Override
                public void keyTyped(KeyEvent e) {
                    keyPressed(e);
                }
            });
        }
    }
    
    public static void makeCurrencyInput(JTextField... fields) {
        for (JTextField field : fields) {
            field.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent evt) {
                    char c = evt.getKeyChar();
                    if (!(Character.isDigit(c)
                            || c == KeyEvent.VK_BACK_SPACE
                            || c == KeyEvent.VK_DELETE
                            || c == KeyEvent.VK_LEFT
                            || c == KeyEvent.VK_RIGHT)) {
                        evt.consume();
                        Toolkit.getDefaultToolkit().beep();
                    }else{
                        String val = field.getText().replace(".", "").replace(",", "");
                        if (val.length() > 0) {
                            BigDecimal amt = new BigDecimal(val);
                            field.setText(Common.formatRupiah(amt.doubleValue(), false));
                        }else{
                            field.setText("0");
                        }
                    }
                }

                @Override
                public void keyTyped(KeyEvent e) {
                    keyPressed(e);
                }

                @Override
                public void keyReleased(KeyEvent e) {
                    keyPressed(e);
                }
            });
        }
    }

}
