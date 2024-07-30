/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.gateway.resource.handler.mpmodule;

import co.id.ez.gateway.message.BillerRequest;
import co.id.ez.gateway.message.mp.telkom.TelkomAdviceRequest;
import co.id.ez.gateway.message.mp.telkom.TelkomInquiryRequest;
import co.id.ez.gateway.message.mp.telkom.TelkomPaymentRequest;
import co.id.ez.gateway.resource.MessageType;
import com.json.JSONObject;
import java.math.BigDecimal;

/**
 *
 * @author Lutfi
 */
public class TelkomModule extends MPModule{

    public TelkomModule() {
        super("TELKOM");
    }

    @Override
    public BillerRequest constructBillerRequest(JSONObject request, MessageType pMsgType) {
        if(pMsgType == MessageType.INQUIRY){
            TelkomInquiryRequest inqRequest = new TelkomInquiryRequest();
            inqRequest.setInput1(request.getString("input1"));
            return inqRequest;
        }
        
        if(pMsgType == MessageType.PAYMENT){
            TelkomPaymentRequest inqRequest = new TelkomPaymentRequest();
            inqRequest.setInput1(request.getString("input1"));
            inqRequest.setTrxid(request.getString("trxid"));
            inqRequest.setAmount(request.get("amount").toString());
            return inqRequest;
        }
        
        if(pMsgType == MessageType.ADVICE){
            TelkomAdviceRequest inqRequest = new TelkomAdviceRequest();
            inqRequest.setInput1(request.getString("input1"));
            inqRequest.setTrxid(request.getString("trxid"));
            inqRequest.setAmount(request.get("amount").toString());
            return inqRequest;
        }
        return null;
    }

    @Override
    public JSONObject constructSuccessfullResponse(JSONObject pRequest, String pResponse) {
        JSONObject tBillerResponse = new JSONObject(pResponse);
        String[] tBasicParam = new String[]{
            "rc",
            "rcm",
            "status",
            "refnum",
            "text",
            "trxid"
        };
        
        JSONObject tResp = pRequest;
        for (String basicParam : tBasicParam) {
            tResp.put(basicParam, tBillerResponse.get(basicParam));
        }
        
        BigDecimal tTagihan = new BigDecimal(tBillerResponse.getDouble("tagihan"));
        BigDecimal tAdmin = new BigDecimal(tBillerResponse.getDouble("admin"));
        BigDecimal tTotalTagihan = new BigDecimal(tBillerResponse.getDouble("total"));
        String tSubReg = tBillerResponse.getString("subreg");
        String tNama = tBillerResponse.getString("nama");
        String tSubID = tBillerResponse.getString("subid");
        String tJmlBulan = tBillerResponse.get("jmlbulan").toString();
        String tPeriode = tBillerResponse.has("periode") 
                ? tBillerResponse.getString("periode")
                : tBillerResponse.has("period")
                ? tBillerResponse.getString("period")
                : "";
        
        JSONObject tData = new JSONObject()
                .put("ID_REGISTRASI", tSubReg)
                .put("ID_PELANGGAN", tSubID)
                .put("NAMA", tNama)
                .put("JML_BULAN", tJmlBulan)
                .put("PERIODE", tPeriode);
        
        tResp.put("data", tData);
        tResp.put("jmltagihan", tTagihan.doubleValue());
        tResp.put("admin", tAdmin.doubleValue());
        tResp.put("totaltag", tTotalTagihan.doubleValue());
        
        return tResp;
    }
    
}
