/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.gateway.resource.handler.mpmodule;

import co.id.ez.gateway.message.BillerRequest;
import co.id.ez.gateway.message.mp.ewallet.EwalletAdviceRequest;
import co.id.ez.gateway.message.mp.ewallet.EwalletInquiryRequest;
import co.id.ez.gateway.message.mp.ewallet.EwalletPaymentRequest;
import co.id.ez.system.core.log.LogService;
import com.json.JSONObject;

/**
 *
 * @author Lutfi
 */
public class EwalletModule extends MPModule{

    public EwalletModule() {
        super("EWALLET");
    }

    @Override
    public BillerRequest constructBillerRequest(JSONObject request) {
        if(request.getString("command").equalsIgnoreCase("INQ")){
            EwalletInquiryRequest inqRequest = new EwalletInquiryRequest(request.getString("command"), request.getString("modul"));
            LogService.getInstance(this).trace().log("Construct inquiry request to biller, for modul: "+ request.getString("modul").toUpperCase());
            inqRequest.setInput1(request.getString("input1"));
            inqRequest.setInput2(request.getString("input2"));
            inqRequest.setBiller(request.getString("biller"));
            return inqRequest;
        }
        
        if(request.getString("command").equalsIgnoreCase("PAY")){
            EwalletPaymentRequest inqRequest = new EwalletPaymentRequest(request.getString("command"), request.getString("modul"));
            inqRequest.setInput1(request.getString("input1"));
            inqRequest.setBiller(request.getString("biller"));
            inqRequest.setInput2(request.has("input2") ? request.getString("input2") : null);
            inqRequest.setTrxid(request.getString("trxid"));
            inqRequest.setAmount(request.get("amount").toString());
            return inqRequest;
        }
        
        if(request.getString("command").equalsIgnoreCase("ADV")){
            EwalletAdviceRequest inqRequest = new EwalletAdviceRequest(request.getString("command"), request.getString("modul"));
            inqRequest.setInput1(request.getString("input1"));
            inqRequest.setInput2(request.has("input2") ? request.getString("input2") : null);
            inqRequest.setBiller(request.getString("biller"));
            inqRequest.setTrxid(request.getString("trxid"));
            inqRequest.setAmount(request.get("amount").toString());
            return inqRequest;
        }
        return null;
    }

    @Override
    public JSONObject constructSuccessfullResponse(JSONObject pRequest, String pResponse) {
        JSONObject tResp = super.constructSuccessfullResponse(pRequest, pResponse);
        tResp.remove("nohp");
        tResp.remove("produk");
        tResp.remove("nominal");
        
        return tResp;
    }
    
}
