/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.database.entity;

import co.id.ez.database.enums.Operator;
import co.id.ez.database.query.QueryConditional;

/**
 *
 * @author RCS
 */
public class WhereField extends DBField{
    private final QueryConditional cCond;
    private final Operator cOp;
    
    public WhereField(String feldName, String fieldValue, Operator pOp, QueryConditional pCond) {
        super(feldName, fieldValue);
        this.cCond = pCond;
        this.cOp = pOp;
    }
    
    public WhereField(String feldName, String fieldValue, Operator pOp) {
        super(feldName, fieldValue);
        this.cCond = null;
        this.cOp = pOp;
    }
    
    public WhereField(String feldName, String fieldValue, Operator pOp, QueryConditional pCond, boolean isDef) {
        super(feldName, fieldValue, isDef);
        this.cCond = pCond;
        this.cOp = pOp;
    }
    
    public WhereField(String feldName, String fieldValue, Operator pOp, boolean isDef) {
        super(feldName, fieldValue, isDef);
        this.cCond = null;
        this.cOp = pOp;
    }

    public String getConditional() {
        return cCond == null ? " AND " :" "+cCond.name()+" ";
    }
    
    public String getOperator(){
        return cOp.value();
    }

    @Override
    public String getFieldValue() {
        if (cOp == Operator.CONTAINS) {
            return "%"+super.getFieldValue()+"%";
        }
        
        if (cOp == Operator.LIKE) {
            return super.getFieldValue()+"%";
        }
        
        return super.getFieldValue();
    }
    
}
