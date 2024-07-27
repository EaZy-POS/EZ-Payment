/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.database.entity;

import java.util.Objects;

/**
 *
 * @author RCS
 */
public class DBField {
    private final String feldName, fieldValue;
    private final boolean isDefault;

    public DBField(String feldName, String fieldValue) {
        this.feldName = feldName;
        this.fieldValue = fieldValue;
        isDefault = false;
    }
    
    public DBField(String feldName, String fieldValue, boolean isDef) {
        this.feldName = feldName;
        this.fieldValue = fieldValue;
        this.isDefault = isDef;
    }

    public String getFeldName() {
        return feldName;
    }

    public String getFieldValue() {
        return fieldValue;
    }

    public boolean isIsDefault() {
        return isDefault;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.feldName);
        hash = 89 * hash + Objects.hashCode(this.fieldValue);
        hash = 89 * hash + (this.isDefault ? 1 : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DBField other = (DBField) obj;
        if (this.isDefault != other.isDefault) {
            return false;
        }
        if (!Objects.equals(this.feldName, other.feldName)) {
            return false;
        }
        if (!Objects.equals(this.fieldValue, other.fieldValue)) {
            return false;
        }
        return true;
    }
}
