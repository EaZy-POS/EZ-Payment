/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.msg.vcr;

import com.json.JSONObject;
import java.math.BigDecimal;

/**
 *
 * @author Lutfi
 */
public class VoucherAdvice extends VoucherInquiry{
    
    private String trxid;
    private BigDecimal nominal, harga, harga_jual;
    
    @Override
    public String getPath() {
        return "/api/voucher/adv";
    }

    @Override
    public String getCommand() {
        return "ADV";
    }

    @Override
    public JSONObject getBodyRequest() {
        JSONObject req = super.getBodyRequest();
        req.put("trxid", getTrxid());
        req.put("nominal", getNominal().doubleValue());
        req.put("harga", getHarga().doubleValue());
        req.put("harga_jual", getHarga_jual().doubleValue());
        return req;
    }

    public String getTrxid() {
        return trxid;
    }

    public void setTrxid(String trxid) {
        this.trxid = trxid;
    }

    public BigDecimal getNominal() {
        return nominal;
    }

    public void setNominal(BigDecimal nominal) {
        this.nominal = nominal;
    }

    public BigDecimal getHarga() {
        return harga;
    }

    public void setHarga(BigDecimal harga) {
        this.harga = harga;
    }

    public BigDecimal getHarga_jual() {
        return harga_jual;
    }

    public void setHarga_jual(BigDecimal harga_jual) {
        this.harga_jual = harga_jual;
    }
    
}
