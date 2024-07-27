/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.gateway.util.enums;

/**
 *
 * @author Lutfi
 */
public enum TableName {
 
    PDAM("trx_pdam_tranmain"),
    VOUCHER("trx_voucher_tranmain"),
    MUTLI_PAYMENT("trx_multipayment_tranmain"),
    PLN_POSTPAID("trx_pospaid_tranmain"),
    PLN_PREPAID("trx_prepaid_tranmain"),
    LOG_DOWN("log_down_message"),
    LOG_BILLER("log_biller_message"),
    ;
    
    private final String mTranmain;
    private TableName(String ptranmain){
        this.mTranmain = ptranmain;
    }
    
    public String get(){
        return mTranmain;
    }
}
