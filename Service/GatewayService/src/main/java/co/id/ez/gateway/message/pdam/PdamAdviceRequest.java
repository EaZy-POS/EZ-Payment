/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.gateway.message.pdam;

/**
 *
 * @author lutfi
 */
public class PdamAdviceRequest extends PdamInquiryRequest{
    
    public void setInqRequest(PdamInquiryRequest inqReq){
        setIdpel(inqReq.getIdpel());
        setBiller(inqReq.getBiller());
        setDetail(isDetail());
    }

    @Override
    public String getMessageStream() {
        return super.getMessageStream();
    }
    
    @Override
    public String getComand() {
        return "ADV";
    }
}
