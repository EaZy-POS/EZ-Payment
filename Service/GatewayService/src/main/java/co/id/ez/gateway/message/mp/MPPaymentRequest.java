/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.gateway.message.mp;

/**
 *
 * @author RCS
 */
public class MPPaymentRequest extends MPInquiryRequest{
    private String amount;
    
    public MPPaymentRequest(String comand, String modul) {
        super(comand, modul);
    }
    
    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    @Override
    public String getMessageStream() {
        return super.getMessageStream()+ "&amount=" + amount;
    }

}
