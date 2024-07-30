/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.gateway.resource.handler.mpmodule;

import co.id.ez.gateway.message.BillerRequest;
import co.id.ez.gateway.message.mp.MPAdviceRequest;
import co.id.ez.gateway.message.mp.MPInquiryRequest;
import co.id.ez.gateway.message.mp.MPPaymentRequest;
import co.id.ez.gateway.resource.MessageType;
import com.json.JSONObject;

/**
 *
 * @author Lutfi
 */
public class GeneralPaymentModule extends MPModule{

    public GeneralPaymentModule() {
        super("GENERALPAYMENT");
    }

    @Override
    public BillerRequest constructBillerRequest(JSONObject request, MessageType pMsgType) {
        if(pMsgType == MessageType.INQUIRY){
            MPInquiryRequest inqRequest = new MPInquiryRequest();
            inqRequest.setInput1(request.getString("input1"));
            inqRequest.setInput2(request.has("input2") ? request.getString("input2") : null);
            inqRequest.setInput3(request.has("input3") ? request.getString("input3") : null);
            inqRequest.setBiller(request.getString("biller"));
            return inqRequest;
        }
        
        if(pMsgType == MessageType.PAYMENT){
            MPPaymentRequest inqRequest = new MPPaymentRequest();
            inqRequest.setInput1(request.getString("input1"));
            inqRequest.setBiller(request.getString("biller"));
            inqRequest.setInput2(request.has("input2") ? request.getString("input2") : null);
            inqRequest.setInput3(request.has("input3") ? request.getString("input3") : null);
            inqRequest.setTrxid(request.getString("trxid"));
            inqRequest.setAmount(request.get("amount").toString());
            return inqRequest;
        }
        
        if(pMsgType == MessageType.ADVICE){
            MPAdviceRequest inqRequest = new MPAdviceRequest();
            inqRequest.setInput1(request.getString("input1"));
            inqRequest.setInput2(request.has("input2") ? request.getString("input2") : null);
            inqRequest.setInput3(request.has("input3") ? request.getString("input3") : null);
            inqRequest.setBiller(request.getString("biller"));
            inqRequest.setTrxid(request.getString("trxid"));
            inqRequest.setAmount(request.get("amount").toString());
            return inqRequest;
        }
        return null;
    }
    
}
