/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.msg.pam;

import com.json.JSONObject;
import java.math.BigDecimal;

/**
 *
 * @author Lutfi
 */
public class PDAMAdvice extends PDAMInquiry{

    private String trxid;
    private BigDecimal amount;
    
    @Override
    public JSONObject getBodyRequest(){
        JSONObject req = super.getBodyRequest();
        req.put("trxid", getTrxid())
                .put("amount", getAmount().doubleValue());
        return req;
    }

    @Override
    public String getPath() {
        return "/api/pdam/adv";
    }

    @Override
    public String getCommand() {
        return "ADV";
    }

    public String getTrxid() {
        return trxid;
    }

    public void setTrxid(String trxid) {
        this.trxid = trxid;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

}
