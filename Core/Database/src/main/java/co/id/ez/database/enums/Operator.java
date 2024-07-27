/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.database.enums;

/**
 *
 * @author RCS
 */
public enum Operator {
    
    EQUALS(" = "),
    LESS_THAN(" < "),
    MORE_THAN(" > "),
    LESS_OR_EQUALS_THAN(" <= "),
    MORE_OR_EQUALS_THAN(" >= "),
    BETWEEN(" BETWEEN "),
    CONTAINS(" LIKE "),
    LIKE(CONTAINS.value());
    
    private final String cOperator;

    private Operator(String cOperator) {
        this.cOperator = cOperator;
    }

    public String value() {
        return cOperator;
    }
    
}
