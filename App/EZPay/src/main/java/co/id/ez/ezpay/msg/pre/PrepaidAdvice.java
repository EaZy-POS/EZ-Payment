/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.msg.pre;

import com.json.JSONObject;
import java.math.BigDecimal;

/**
 *
 * @author Lutfi
 */
public class PrepaidAdvice extends PrepaidInquiry{

    private String trxId;
    private BigDecimal hargaJual;
    
    @Override
    public JSONObject getBodyRequest(){
        JSONObject req = super.getBodyRequest();
        req.put("trxid", getTrxid())
                .put("harga_jual", getHargaJual().doubleValue());
        return req;
    }

    @Override
    public String getPath() {
        return "/api/prepaid/adv";
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

    public BigDecimal getHargaJual() {
        return hargaJual;
    }

    public void setHargaJual(BigDecimal hargaJual) {
        this.hargaJual = hargaJual;
    }

}
