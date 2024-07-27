/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.msg.pos;

import com.json.JSONObject;
import java.math.BigDecimal;

/**
 *
 * @author Lutfi
 */
public class PospaidAdvice extends PospaidInquiry{

    private String trxId;
    private BigDecimal amount;
    
    @Override
    public JSONObject getBodyRequest(){
        JSONObject req = super.getBodyRequest();
        req.put("trxid", getTrxid())
                .put("amount", amount.doubleValue())
                .put("detail", "true");
        return req;
    }

    @Override
    public String getPath() {
        return "/api/postpaid/adv";
    }

    @Override
    public String getCommand() {
        return "ADV";
    }

    public String getTrxid() {
        return trxId;
    }

    public void setTrxid(String trxId) {
        this.trxId = trxId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

}
