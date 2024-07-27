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
public enum ReceiptFormat {
    
    SIZE_58_MM(58, 11, 2, 19),
    SIZE_75_MM(75, 17, 2, 29),
    SIZE_80_MM(80, 20, 2, 31),
    ;
    
    private final int paperSize, labelSize, dashSize, valueSize;

    private ReceiptFormat(int paperSize, int labelSize, int dashSize, int valueSize) {
        this.paperSize = paperSize;
        this.labelSize = labelSize;
        this.dashSize = dashSize;
        this.valueSize = valueSize;
    }

    public int getPaperSize() {
        return paperSize;
    }

    public int getLabelSize() {
        return labelSize;
    }

    public int getDashSize() {
        return dashSize;
    }

    public int getValueSize() {
        return valueSize;
    }
    
    public static ReceiptFormat parse(int paperSize){
        for (ReceiptFormat value : values()) {
            if(value.getPaperSize() == paperSize){
                return value;
            }
        }
        
        throw new IllegalStateException("Unknown printer format!");
    }
}
