/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.gateway.util.enums;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author RCS
 */
public enum RequiredFields {
    VOUCHER_INQUIRY(Fields.command, Fields.modul, Fields.tujuan, Fields.voucherid),
    VOUCHER_PAYMENT(Fields.command, Fields.modul, Fields.tujuan, Fields.voucherid, Fields.trxid, Fields.harga, Fields.harga_jual),
    VOUCHER_ADVICE(VOUCHER_PAYMENT),
    PREPAID_INQUIRY(Fields.command, Fields.modul, Fields.MSN, Fields.nominal),
    PREPAID_PAYMENT(Fields.command, Fields.modul, Fields.MSN, Fields.nominal, Fields.trxid, Fields.harga_jual),
    PREPAID_ADVICE(PREPAID_PAYMENT),
    POSPAID_INQUIRY(Fields.command, Fields.modul, Fields.idpel, Fields.detail),
    POSPAID_PAYMENT(Fields.command, Fields.modul, Fields.idpel, Fields.detail, Fields.amount, Fields.trxid),
    POSPAID_ADVICE(POSPAID_PAYMENT),
    PDAM_INQUIRY(Fields.command, Fields.modul, Fields.idpel, Fields.detail, Fields.biller),
    PDAM_PAYMENT(Fields.command, Fields.modul, Fields.idpel, Fields.detail, Fields.biller, Fields.trxid, Fields.amount),
    PDAM_ADVICE(PDAM_PAYMENT),
    MP_INQUIRY(Fields.command, Fields.modul, Fields.input1, Fields.biller),
    MP_PAYMENT(Fields.command, Fields.modul, Fields.input1, Fields.biller, Fields.amount, Fields.trxid),
    MP_ADVICE(MP_PAYMENT),
    ;
    
    
    private final List<String> fields;
    private RequiredFields(Fields... fields){
        this.fields = new ArrayList<>();
        for (Fields field : fields) {
            this.fields.add(field.name());
        }
    }
    
    private RequiredFields(RequiredFields fields){
        this.fields = new ArrayList<>();
        fields.getFields().forEach(field -> {
            this.fields.add(field);
        });
    }
    
    public List<String> getFields(){
        return fields;
    }
}
