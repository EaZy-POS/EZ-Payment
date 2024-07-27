/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.msg.pre;

import co.id.ez.ezpay.enums.RequestType;
import co.id.ez.ezpay.msg.BillerMessage;
import com.json.JSONObject;
import java.math.BigDecimal;

/**
 *
 * @author Lutfi
 */
public class PrepaidInquiry extends BillerMessage{

    private String msn;
    private BigDecimal nominal;
    
    public PrepaidInquiry() {
        super(RequestType.PREPAID, "PRE");
    }

    @Override
    public JSONObject getBodyRequest(){
        JSONObject req = new JSONObject();
        req.put("MSN", msn)
                .put("nominal", nominal.toPlainString())
                .put("command", getCommand())
                .put("modul", getModuleid());
        return req;
    }

    @Override
    public String getPath() {
        return "/api/prepaid/inq";
    }

    @Override
    public String getCommand() {
        return "INQ";
    }

    public String getMsn() {
        return msn;
    }

    public void setMsn(String msn) {
        this.msn = msn;
    }

    public BigDecimal getNominal() {
        return nominal;
    }

    public void setNominal(BigDecimal nominal) {
        this.nominal = nominal;
    }

}
