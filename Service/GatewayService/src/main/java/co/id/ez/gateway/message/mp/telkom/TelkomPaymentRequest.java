/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.gateway.message.mp.telkom;

import co.id.ez.gateway.message.mp.MPPaymentRequest;

/**
 *
 * @author RCS
 */
public class TelkomPaymentRequest extends MPPaymentRequest {

    public TelkomPaymentRequest(String comand, String modul) {
        super(comand, modul);
    }

    @Override
    public String getMessageStream() {
        return super.getBasicMessageStream() + "&nopel=" + getInput1();
    }

    @Override
    public String getRemarks() {
        return super.getRemarks("Pembayaran") + " nopel " + getInput1() + "(Telkom)";
    }

}
