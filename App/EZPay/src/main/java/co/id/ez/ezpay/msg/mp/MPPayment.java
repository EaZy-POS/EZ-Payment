/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.msg.mp;

import co.id.ez.ezpay.enums.RequestType;
import co.id.ez.ezpay.model.data.search.MultiPaymentBiller;
import co.id.ez.ezpay.msg.BillerMessage;
import co.id.ez.ezpay.msg.BillerResponse;
import com.json.JSONObject;

/**
 *
 * @author Lutfi
 */
public class MPPayment extends BillerMessage{
    
    private final BillerResponse billerResponse;
    private final MultiPaymentBiller biller;

    public MPPayment(BillerResponse billerresponse, MultiPaymentBiller biller) {
        super(RequestType.parse(biller.getBiller()), "GP");
        this.billerResponse = billerresponse;
        this.biller = biller;
    }

    @Override
    public JSONObject getBodyRequest() {
        JSONObject tResp = billerResponse.getPayload();
        
        JSONObject tRequest = new JSONObject();
        tRequest.put("modul", getModuleid())
                .put("biller", biller.getBiller())
                .put("command", getCommand())
                .put("trxid", tResp.getString("trxid"))
                .put("amount", tResp.getDouble("totaltag"));
        
        if(tResp.has("input1")){
            tRequest.put("input1", tResp.getString("input1"));
        }
        
        if(tResp.has("input2")){
            tRequest.put("input2", tResp.getString("input2"));
        }
        
        if(tResp.has("input3")){
            tRequest.put("input3", tResp.getString("input3"));
        }
        
        return tRequest;
    }

    @Override
    public String getPath() {
        return "/api/mp/pay";
    }

    @Override
    public String getCommand() {
        return "PAY";
    }
    
    
    
}
