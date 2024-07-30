/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.gateway.message.prepaid;

import com.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author lutfi
 */
public class PlnPrepaidPaymentRequest extends PlnPrepaidInquiryRequest{
    
    @Override
    public String getComand() {
        return "PAY";
    }

    
    @Override
    public String contructSimulatorResponse() {
        
        String tRefnum = getRefnum();
        JSONObject tObject = new JSONObject();
        tObject.put("status", "SUCCESS");
        tObject.put("rc", "0000");
        tObject.put("rcm", "[0] CEK TAGIHAN SUKSES ke:"+getMSN()+" refnum:"+tRefnum+"");
        tObject.put("text", new SimpleDateFormat("dd/MM/YY HH:mm").format(new Date()) 
                +"\\n"+tRefnum
                +"\\nTestDu''mmy&\\n"+getMSN()+"\\773333333333\\nR1/7000 VA\\nRP 20.005\\nKWH35,2\\nSTROOM/TOKEN 5552 2784 9512 9995 5212\\nADM 5,00");
        tObject.put("msn", getMSN());
        tObject.put("refnum", tRefnum);
        tObject.put("idpel", "773333333333");
        tObject.put("nama", "TestDu''mmy&Panjang773333");
        tObject.put("tarifdaya", "R3/6600");
        tObject.put("unsold", "0");
        tObject.put("distcode", "77");
        tObject.put("sunit", "77000");
        tObject.put("suphone", "123");
        tObject.put("maxkwh", "30000");
        tObject.put("nominal", "20005");
        tObject.put("denomunsold", "");
        tObject.put("jmlkwh", "35.2");
        tObject.put("token", "5552-2784-9512-9995-5212");
        tObject.put("ppn", "0");
        tObject.put("ppj", "0");
        tObject.put("admin", "5");
        tObject.put("angsuran", "2400");
        tObject.put("materai", "0");
        tObject.put("info", "Informasi Hubungi Call Center 123 Atau hubungi PLN Terdekat");
        tObject.put("rptoken", "17600");
        tObject.put("trxid", getTrxid());
        
        return tObject.toString();
    }
}
