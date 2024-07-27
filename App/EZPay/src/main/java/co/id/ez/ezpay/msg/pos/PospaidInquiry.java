/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.msg.pos;

import co.id.ez.ezpay.enums.RequestType;
import co.id.ez.ezpay.msg.BillerMessage;
import com.json.JSONObject;
/**
 *
 * @author Lutfi
 */
public class PospaidInquiry extends BillerMessage{

    private String idpel;
    
    public PospaidInquiry() {
        super(RequestType.POSTPAID, "PLN");
    }

    @Override
    public JSONObject getBodyRequest(){
        JSONObject req = new JSONObject();
        req.put("idpel", idpel)
                .put("detail", "true")
                .put("command", getCommand())
                .put("modul", getModuleid());
        return req;
    }

    @Override
    public String getPath() {
        return "/api/postpaid/inq";
    }

    @Override
    public String getCommand() {
        return "INQ";
    }

    public String getIDPelanggan() {
        return idpel;
    }

    public void setIDPelanggan(String msn) {
        this.idpel = msn;
    }

}
