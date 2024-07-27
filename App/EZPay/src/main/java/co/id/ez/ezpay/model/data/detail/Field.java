/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.model.data.detail;

import co.id.ez.ezpay.enums.util.Aligment;

/**
 *
 * @author Lutfi
 */
public class Field {

    private final String value, padding;
    private final int length;
    private final Aligment aligment;

    public Field(String value, int length, Aligment aligment) {
        this(value, length, aligment, " ");
    }

    public Field(String value, int length, Aligment aligment, String padding) {
        this.value = value;
        this.length = length;
        this.aligment = aligment;
        this.padding = padding;
    }

    public String getValue() {
        return value;
    }

    public int getLength() {
        return length;
    }

    public Aligment getAligment() {
        return aligment;
    }

    public String getPadding() {
        return padding;
    }

    public static Field bind(String value) {
        return new Field(value, -1, Aligment.CENTER, " ");
    }
    
    public static Field bind(String value, int length) {
        return new Field(value, length, Aligment.CENTER, " ");
    }
    
    public static Field bind(String value, int length, String pad) {
        return new Field(value, length, Aligment.CENTER, pad);
    }
    
    public static Field bind(String value, int length, Aligment aligment) {
        return new Field(value, length, aligment, " ");
    }

    public static Field bind(String value, int length, Aligment aligment, String padding) {
        return new Field(value, length, aligment, padding);
    }
}
