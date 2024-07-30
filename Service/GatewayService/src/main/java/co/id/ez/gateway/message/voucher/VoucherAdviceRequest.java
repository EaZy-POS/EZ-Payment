/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.gateway.message.voucher;

/**
 *
 * @author RCS
 */
public class VoucherAdviceRequest extends VoucherPaymentRequest{
    
    @Override
    public String getComand() {
        return "ADV";
    }
    
}
