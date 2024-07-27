/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.gateway.message.prepaid;

import co.id.ez.gateway.message.BillerRequest;
import co.id.ez.system.core.ex.ServiceException;
import co.id.ez.system.core.rc.RC;
import com.json.JSONObject;
import java.util.UUID;

/**
 *
 * @author RCS
 */
public class PlnPrepaidInquiryRequest extends BillerRequest{
    
    private String MSN;
    private String nominal;
    
    public PlnPrepaidInquiryRequest(String comand, String modul) {
        super(comand, modul);
    }

    public String getMSN() {
        return MSN;
    }

    public void setMSN(String MSN) {
        this.MSN = MSN;
    }

    public String getNominal() {
        return nominal;
    }

    public void setNominal(String nominal) {
        this.nominal = nominal;
    }

    @Override
    public String getMessageStream() {
        return super.getMessageStream()+ "&MSN=" + MSN + "&nominal=" + nominal ;
    }

    @Override
    public String getRemarks() {
        return super.getRemarks() + " msn "+ MSN +"("+nominal+")"; 
    }
    
    @Override
    public String contructSimulatorResponse() {
        
        if(!nominal.equalsIgnoreCase("20000")){
            throw new ServiceException(RC.ERROR_UNREGISTERED_MERCHANT_TYPE, "Denom "+ nominal +" tidak terdaftar");
        }
        
        String tRefnum = UUID.randomUUID().toString().replace("-", "").toUpperCase();
        JSONObject tObject = new JSONObject();
        tObject.put("status", "SUCCESS");
        tObject.put("rc", "0000");
        tObject.put("rcm", "[0] CEK TAGIHAN SUKSES ke:"+MSN+" refnum:"+tRefnum+"");
        tObject.put("text", MSN + "\\nTestDu''mmy&\\nR3/6600 VA\\nToken Unsold: 0\\nAdm 5,00\\n*Denom Belum Termasuk Admin");
        tObject.put("msn", MSN);
        tObject.put("subid", "773333333333");
        tObject.put("nama", "TestDu''mmy&Panjang773333");
        tObject.put("tarif", "R3/6600");
        tObject.put("admin", "5");
        tObject.put("unsold", "0");
        tObject.put("denomunsold", "");
        tObject.put("distcode", "77");
        tObject.put("sunit", "77000");
        tObject.put("suphone", "123");
        tObject.put("maxkwh", "30000");
        tObject.put("trxid", getTrxid());
        tObject.put("refnum", UUID.randomUUID().toString().replace("-", "").toUpperCase());
        
        return tObject.toString();
    }
}
