/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.central.enums;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Lutfi
 */
public enum RequiredFields {
    REGISTRASI(Fields.mitra_name, Fields.address, Fields.city, 
            Fields.province, Fields.country, Fields.contact_person, 
            Fields.phone, Fields.wa_number, Fields.email, Fields.tagline),
    AKTIFASI_MITRA(Fields.client_id, Fields.secret_key),
    UPDATE_MITRA(Fields.mitra_name, Fields.address, Fields.city, 
            Fields.province, Fields.country, Fields.contact_person, 
            Fields.phone, Fields.wa_number, Fields.email, Fields.tagline, Fields.id),
    MINISTATEMENT(Fields.from_date, Fields.until_date),
    DELETE_MITRA(Fields.client_id),
    TOPUP(Fields.client_id, Fields.amount, Fields.noreff)
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
