/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.gateway.message.pdam;

/**
 *
 * @author RCS
 */
public class PdamPaymentRequest extends PdamInquiryRequest{
    
    public PdamPaymentRequest(String comand, String modul) {
        super(comand, modul);
    }

    @Override
    public String getMessageStream() {
        return super.getMessageStream();
    }
}
