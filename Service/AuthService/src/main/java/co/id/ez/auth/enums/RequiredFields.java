/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.auth.enums;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author RCS
 */
public enum RequiredFields {
    AUTH(Fields.client_id, Fields.secret_key, Fields.user_id, Fields.password, Fields.module_id),
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
