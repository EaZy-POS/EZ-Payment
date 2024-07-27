/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.enums.util;

import javax.swing.SwingConstants;

/**
 *
 * @author RCS
 */
public enum Aligment {
    LEFT(SwingConstants.LEFT),
    CENTER(SwingConstants.CENTER),
    RIGHT(SwingConstants.RIGHT);
    
    private final int value;

    private Aligment(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
    
}
