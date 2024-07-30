/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.gateway.message.mp.telkom;

import co.id.ez.gateway.message.mp.MPInquiryRequest;

/**
 *
 * @author lutfi
 */
public class TelkomInquiryRequest extends MPInquiryRequest {

    @Override
    public String getMessageStream() {
        return super.getBasicMessageStream()+ "&nopel=" + getInput1();
    }

    @Override
    public String getModuleCode() {
        return "TKM";
    }

    @Override
    public String getModule() {
        return "ph";
    }
    
    @Override
    public String getComand() {
        return "INQ";
    }
}
