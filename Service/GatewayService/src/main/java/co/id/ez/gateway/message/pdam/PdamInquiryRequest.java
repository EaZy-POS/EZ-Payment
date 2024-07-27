/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.gateway.message.pdam;

import co.id.ez.gateway.message.BillerRequest;

/**
 *
 * @author RCS
 */
public class PdamInquiryRequest extends BillerRequest{
    private String idpel;
    private String biller;
    private boolean detail;
    
    public PdamInquiryRequest(String comand, String modul) {
        super(comand, modul);
    }

    public String getIdpel() {
        return idpel;
    }

    public void setIdpel(String idpel) {
        this.idpel = idpel;
    }

    public String getBiller() {
        return biller;
    }

    public void setBiller(String biller) {
        this.biller = biller;
    }

    public boolean isDetail() {
        return detail;
    }

    public void setDetail(boolean detail) {
        this.detail = detail;
    }

    @Override
    public String getMessageStream() {
        return super.getMessageStream()+ 
                "&idpel=" + idpel + "&biller=" + biller + "&detail=" + detail;
    }

    @Override
    public String getRemarks() {
        return super.getRemarks() + " idpel "+ idpel +"("+biller+")"; 
    }
}
