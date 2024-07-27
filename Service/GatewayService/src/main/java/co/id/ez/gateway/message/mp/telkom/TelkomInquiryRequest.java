/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.gateway.message.mp.telkom;

import co.id.ez.gateway.message.mp.MPInquiryRequest;

/**
 *
 * @author RCS
 */
public class TelkomInquiryRequest extends MPInquiryRequest {

    public TelkomInquiryRequest(String comand, String modul) {
        super(comand, modul);
    }

    @Override
    public String getMessageStream() {
        return super.getBasicMessageStream()+ "&nopel=" + getInput1();
    }

}
