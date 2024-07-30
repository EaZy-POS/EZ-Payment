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
import co.id.ez.gateway.resource.MessageType;
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
    public BillerRequest constructBillerRequest(JSONObject request, MessageType pMsgType) {
        if(pMsgType == MessageType.INQUIRY){
            EwalletInquiryRequest inqRequest = new EwalletInquiryRequest();
            LogService.getInstance(this).trace().log("Construct inquiry request to biller, for modul: "+ request.getString("modul").toUpperCase());
            inqRequest.setInput1(request.getString("input1"));
            inqRequest.setInput2(request.getString("input2"));
            inqRequest.setBiller(request.getString("biller"));
            return inqRequest;
        }
        
        if(pMsgType == MessageType.PAYMENT){
            EwalletPaymentRequest payRequest = new EwalletPaymentRequest();
            payRequest.setInput1(request.getString("input1"));
            payRequest.setBiller(request.getString("biller"));
            payRequest.setInput2(request.has("input2") ? request.getString("input2") : null);
            payRequest.setTrxid(request.getString("trxid"));
            payRequest.setAmount(request.get("amount").toString());
            return payRequest;
        }
        
        if(pMsgType == MessageType.ADVICE){
            EwalletAdviceRequest advRequest = new EwalletAdviceRequest();
            advRequest.setInput1(request.getString("input1"));
            advRequest.setInput2(request.has("input2") ? request.getString("input2") : null);
            advRequest.setBiller(request.getString("biller"));
            advRequest.setTrxid(request.getString("trxid"));
            advRequest.setAmount(request.get("amount").toString());
            return advRequest;
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
