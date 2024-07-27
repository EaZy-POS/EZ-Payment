/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.msg.pam;

import co.id.ez.ezpay.enums.RequestType;
import co.id.ez.ezpay.msg.BillerMessage;
import com.json.JSONObject;

/**
 *
 * @author Lutfi
 */
public class PDAMInquiry extends BillerMessage{

    private String biller;
    private String idpel;
    
    public PDAMInquiry() {
        super(RequestType.PDAM, "PDAM");
    }

    @Override
    public JSONObject getBodyRequest(){
        JSONObject req = new JSONObject();
        req.put("idpel", idpel)
                .put("biller", biller)
                .put("detail", true)
                .put("command", getCommand())
                .put("modul", getModuleid());
        return req;
    }

    @Override
    public String getPath() {
        return "/api/pdam/inq";
    }

    @Override
    public String getCommand() {
        return "INQ";
    }

    public String getBiller() {
        return biller;
    }

    public void setBiller(String biller) {
        this.biller = biller;
    }

    public String getIdpel() {
        return idpel;
    }

    public void setIdpel(String idpel) {
        this.idpel = idpel;
    }

}
