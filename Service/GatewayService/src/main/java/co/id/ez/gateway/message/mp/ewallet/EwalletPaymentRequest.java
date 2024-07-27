/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.gateway.message.mp.ewallet;

import co.id.ez.gateway.message.mp.MPPaymentRequest;

/**
 *
 * @author RCS
 */
public class EwalletPaymentRequest extends MPPaymentRequest {

    public EwalletPaymentRequest(String comand, String modul) {
        super(comand, modul);
    }

    @Override
    public String getMessageStream() {
        return super.getBasicMessageStream() + "&nohp=" + getInput1() + "&produk=" + getBiller()
                + "&nominal=" + getInput2() + "&totaltag=" + getAmount();
    }
    
    @Override
    public String getRemarks() {
        return super.getRemarks("Pembayaran") + " destnum " + getInput1() + "(" + getBiller() + ")";
    }

}
