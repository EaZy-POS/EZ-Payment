/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.util.swings.input;

import co.id.ez.ezpay.enums.util.InputType;
import java.math.BigDecimal;
import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JTextField;

/**
 *
 * @author lutfi
 */
public class InputTextVerifier extends InputVerifier{

    private final InputType type;

    public InputTextVerifier(InputType type) {
        this.type = type;
    }
    
    @Override
    public boolean verify(JComponent input) {
        String text = ((JTextField) input).getText();
        try {
            BigDecimal value = new BigDecimal(text);
            return (value.scale() <= Math.abs(4)); 
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public InputType getType() {
        return type;
    }
    
}
