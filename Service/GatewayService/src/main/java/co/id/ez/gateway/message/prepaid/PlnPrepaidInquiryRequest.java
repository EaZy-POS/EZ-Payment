/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.gateway.message.prepaid;

import co.id.ez.gateway.message.BillerRequest;

/**
 *
 * @author lutfi
 */
public class PlnPrepaidInquiryRequest extends BillerRequest{
    
    private String MSN;
    private String nominal;
    
    public String getMSN() {
        return MSN;
    }

    public void setMSN(String MSN) {
        this.MSN = MSN;
    }

    public String getNominal() {
        return nominal;
    }

    public void setNominal(String nominal) {
        this.nominal = nominal;
    }

    @Override
    public String getMessageStream() {
        return super.getMessageStream()+ "&MSN=" + MSN + "&nominal=" + nominal ;
    }

    @Override
    public String getRemarks() {
        return super.getRemarks() + " msn "+ MSN +"("+nominal+")"; 
    }

    @Override
    public String getModuleCode() {
        return "PRE";
    }
    
    @Override
    public String getComand() {
        return "INQ";
    }

    @Override
    public String getModule() {
        return "PRE";
    }
}
