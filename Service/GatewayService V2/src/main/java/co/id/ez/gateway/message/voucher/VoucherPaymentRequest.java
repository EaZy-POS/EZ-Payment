/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.gateway.message.voucher;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 *
 * @author RCS
 */
public class VoucherPaymentRequest extends VoucherInquiryRequest {

    public VoucherPaymentRequest(String comand, String modul) {
        super(comand, modul);
    }

    @Override
    public String getMessageStream() {
        return super.getMessageStream();
    }

    @Override
    public String contructSimulatorResponse() {
        String result = "{ "
                + "  \"status\": \"SUCCESS\", "
                + "  \"rc\": \"0000\", "
                + "  \"rcm\": \"[0] Pembayaran Sukses ke:"+getTujuan()+"\", "
                + "  \"text\": \"Isi pulsa TN5 berhasil untuk nomor "+getTujuan()+" sebesar 5.000, refnum "+getRefnum()+" pada tgl "+new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date())+"\", "
                + "  \"tanggal\": \"2024-01-05 21:05:03\", "
                + "  \"refnum\": \""+getRefnum()+"\", "
                + "  \"voucherid\": \""+getVoucherId()+"\", "
                + "  \"nomor\": \""+getTujuan()+"\", "
                + "  \"nominal\": 5000, "
                + "  \"harga\": 5825, "
                + "  \"serialnumber\": \""+UUID.randomUUID().toString().replace("-", "").substring(0, 16)+"\", "
                + "  \"trxid\": \""+getTrxid()+"\" "
                + "}";

        return result;
    }

}
