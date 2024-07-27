/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.gateway.message.voucher;

import co.id.ez.gateway.message.BillerRequest;
import co.id.ez.system.core.ex.ServiceException;
import co.id.ez.system.core.rc.RC;
import java.math.BigDecimal;
import java.util.UUID;

/**
 *
 * @author LUTFI
 */
public class VoucherInquiryRequest extends BillerRequest{
    private String tujuan, voucherId;
    public VoucherInquiryRequest(String comand, String modul) {
        super(comand, modul);
    }

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
        
        if(!voucherId.equalsIgnoreCase("TN5")){
            throw new ServiceException(RC.ERROR_UNREGISTERED_MERCHANT_TYPE, "Produk "+ voucherId +" tidak terdaftar");
        }
        
        String tRefnum = UUID.randomUUID().toString().replace("-", "").toUpperCase();
        BigDecimal tNominal = new BigDecimal(5000);
        BigDecimal tHarga = new BigDecimal(5825);
        
        String result = "{ "
                + "  \"rc\": \"0000\", "
                + "  \"harga\": "+tHarga.toPlainString()+", "
                + "  \"nominal\": "+tNominal.toPlainString()+", "
                + "  \"refnum\": \""+tRefnum+"\", "
                + "  \"text\": \"Cek pulsa "+voucherId+" berhasil dengan nominal 5000, refnum "+tRefnum+"\", "
                + "  \"rcm\": \"[0] Cek Tagihan Sukses\", "
                + "  \"nomor\": \""+getTujuan()+"\", "
                + "  \"trxid\": \""+getTrxid()+"\", "
                + "  \"status\": \"SUCCESS\" "
                + "}";
        
        return result;
    }

    @Override
    public String getRemarks() {
        return super.getRemarks() + " tujuan "+ tujuan +"("+voucherId+")"; 
    }
}
