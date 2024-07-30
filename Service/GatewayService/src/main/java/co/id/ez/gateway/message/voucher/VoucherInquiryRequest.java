/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.gateway.message.voucher;

import co.id.ez.gateway.message.BillerRequest;
import com.json.JSONObject;
import java.math.BigDecimal;
import java.util.UUID;

/**
 *
 * @author LUTFI
 */
public class VoucherInquiryRequest extends BillerRequest{
    private String tujuan, voucherId;

    public String getTujuan() {
        return tujuan;
    }

    public void setTujuan(String tujuan) {
        this.tujuan = tujuan;
    }

    public String getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(String voucherId) {
        this.voucherId = voucherId;
    }

    @Override
    public String getMessageStream() {
        return super.getMessageStream()+ "&tujuan=" + tujuan + "&voucherId=" + voucherId;
    }

    @Override
    public String contructSimulatorResponse() {
        
        String tRefnum = UUID.randomUUID().toString().replace("-", "").toUpperCase();
        BigDecimal tNominal = new BigDecimal(5000);
        BigDecimal tHarga = new BigDecimal(5825);
        
        JSONObject tObject = new JSONObject();
        tObject.put("status", "SUCCESS");
        tObject.put("rc", "0000");
        tObject.put("rcm", "[0] Cek Tagihan Sukses");
        tObject.put("text", "Cek pulsa "+voucherId+" berhasil dengan nominal "+tNominal+", refnum "+ tRefnum);
        tObject.put("nomor", tujuan);
        tObject.put("harga", tHarga.doubleValue());
        tObject.put("nominal", tNominal.doubleValue());
        tObject.put("trxid", getTrxid());
        tObject.put("trxid", getTrxid());
        
        return tObject.toString();
    }

    @Override
    public String getRemarks() {
        return super.getRemarks() + " tujuan "+ tujuan +"("+voucherId+")"; 
    }

    @Override
    public String getModuleCode() {
        return "VCR";
    }

    @Override
    public String getComand() {
        return "INQ";
    }

    @Override
    public String getModule() {
        return "ISI";
    }
}
