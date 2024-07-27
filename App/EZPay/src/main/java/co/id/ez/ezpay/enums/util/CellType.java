/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.enums.util;

/**
 *
 * @author RCS
 */
public enum CellType {
    EDITABLE_OBJECT(true),
    OBJECT(false),
    CHECK(true),
    ACTIONPANE(true),
    WRAP_TEXT(false);
    
    private final boolean isEditable;

    private CellType(boolean isEditable) {
        this.isEditable = isEditable;
    }

    public boolean isIsEditable() {
        return isEditable;
    }   
}
