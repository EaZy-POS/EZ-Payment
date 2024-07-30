/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.gateway.message.mp.ewallet;

import co.id.ez.gateway.message.mp.MPAdviceRequest;


/**
 *
 * @author RCS
 */
public class EwalletAdviceRequest extends MPAdviceRequest{
    
    @Override
    public String getMessageStream() {
        return super.getBasicMessageStream() + "&nohp=" + getInput1() + "&produk=" + getBiller()
                + "&nominal=" + getInput2() + "&totaltag=" + getAmount();
    }
    
    @Override
    public String getRemarks() {
        return super.getRemarks("Pembayaran") + " destnum " + getInput1() + "(" + getBiller() + ")";
    }
    
    @Override
    public String getModule() {
        return "ewallet";
    }
    
    @Override
    public String getComand() {
        return "ADV";
    }
}
