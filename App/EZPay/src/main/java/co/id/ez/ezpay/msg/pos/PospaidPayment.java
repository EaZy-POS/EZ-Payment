/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.msg.pos;

import co.id.ez.ezpay.enums.RequestType;
import co.id.ez.ezpay.msg.BillerMessage;
import co.id.ez.ezpay.msg.BillerResponse;
import com.json.JSONObject;
/**
 *
 * @author Lutfi
 */
public class PospaidPayment extends BillerMessage{
    
    private final BillerResponse billerResponse;

    public PospaidPayment(BillerResponse billerresponse) {
        super(RequestType.POSTPAID, "PLN");
        this.billerResponse = billerresponse;
    }

    @Override
    public JSONObject getBodyRequest() {
        JSONObject tRequest = billerResponse.getPayload();
        JSONObject tPayReq = new JSONObject();
        tPayReq.put("trxid", tRequest.get("trxid").toString())
                .put("detail", "true")
                .put("amount", tRequest.get("total").toString())
                .put("idpel", tRequest.get("idpel").toString())
                .put("modul", getModuleid())
                .put("command", getCommand());
        
        return tPayReq;
    }

    @Override
    public String getPath() {
        return "/api/postpaid/pay";
    }

    @Override
    public String getCommand() {
        return "PAY";
    }
    
    
    
}
