/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.gateway.message.postpaid;

import co.id.ez.gateway.message.BillerRequest;


/**
 *
 * @author RCS
 */
public class PLNPostpaidInquiryRequest extends BillerRequest{
    private String idpel;
    private boolean Detail;
    
    public PLNPostpaidInquiryRequest(String comand, String modul) {
        super(comand, modul);
    }

    public String getIdpel() {
        return idpel;
    }

    public void setIdpel(String idpel) {
        this.idpel = idpel;
    }

    public boolean isDetail() {
        return Detail;
    }

    public void setDetail(boolean detail) {
        this.Detail = detail;
    }

    @Override
    public String getMessageStream() {
        return super.getMessageStream()+"&idpel=" + idpel + "&Detail=" + Detail;
    }

    @Override
    public String getRemarks() {
        return super.getRemarks() + " idpel "+ idpel; 
    }
}
