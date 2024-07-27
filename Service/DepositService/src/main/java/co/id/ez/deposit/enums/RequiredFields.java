/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.deposit.enums;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author RCS
 */
public enum RequiredFields {
    JOURNAL_DEPOSIT(Fields.das_id, Fields.jurnal, Fields.amount, Fields.refnum, Fields.remarks),
    BALANCE(Fields.das_id),
    CREATE_ACCOUNT(Fields.das_id),
    STATEMENT(Fields.das_id, Fields.from_date, Fields.until_date)
    ;
    
    private final List<String> fields;
    private RequiredFields(Fields... fields){
        this.fields = new ArrayList<>();
        for (Fields field : fields) {
            this.fields.add(field.name());
        }
    }
    
    public List<String> getFields(){
        return fields;
    }
}
