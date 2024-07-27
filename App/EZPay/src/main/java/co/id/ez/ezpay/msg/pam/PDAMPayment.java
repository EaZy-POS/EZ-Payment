/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.msg.pam;

import co.id.ez.ezpay.enums.RequestType;
import co.id.ez.ezpay.msg.BillerMessage;
import co.id.ez.ezpay.msg.BillerResponse;
import com.json.JSONObject;
import java.math.BigDecimal;

/**
 *
 * @author Lutfi
 */
public class PDAMPayment extends BillerMessage{
    
    private final BillerResponse billerResponse;

    public PDAMPayment(BillerResponse billerresponse) {
        super(RequestType.PDAM, "PDAM");
        this.billerResponse = billerresponse;
    }

    @Override
    public JSONObject getBodyRequest() {
        JSONObject tRequest = billerResponse.getPayload();
        JSONObject tPayReq = new JSONObject();
        tPayReq.put("idpel", tRequest.get("idpel").toString())
                .put("biller", tRequest.get("biller").toString())
                .put("trxid", tRequest.get("trxid").toString())
                .put("amount", new BigDecimal(tRequest.get("transamount").toString()).doubleValue())
                .put("detail", true)
                .put("command", getCommand())
                .put("modul", getModuleid())
                ;
        
        return tPayReq;
    }

    @Override
    public String getPath() {
        return "/api/pdam/pay";
    }

    @Override
    public String getCommand() {
        return "PAY";
    }
    
}
