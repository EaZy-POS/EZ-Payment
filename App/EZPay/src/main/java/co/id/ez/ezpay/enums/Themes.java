/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.enums;

/**
 *
 * @author Lutfi
 */
public enum Themes {
    
    PRIMARY_COLOUR("#1B262C", 27, 38, 44),
    SECONDARY_COLOUR("#0F4C75"),
    THIRDTH_COLOUR("#3282B8"),
    MAIN_COLOR_1("#8f94fb"),
    MAIN_COLOR_2("#4e54c8");
    ;
    
    private final String value;
    private final float[] rgb;

    private Themes(String value, float... rgb) {
        this.value = value;
        this.rgb = rgb;
    }

    public String getValue() {
        return value;
    }
    
    public float getRed(){
        return rgb[0];
    }
    
    public float getGreen(){
        return rgb[1];
    }
    
    public float getBluw(){
        return rgb[2];
    }
}
