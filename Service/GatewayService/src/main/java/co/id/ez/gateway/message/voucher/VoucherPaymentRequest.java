/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.gateway.message.voucher;

import com.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 *
 * @author RCS
 */
public class VoucherPaymentRequest extends VoucherInquiryRequest{

    @Override
    public String getMessageStream() {
        return super.getMessageStream();
    }

    @Override
    public String contructSimulatorResponse() {

        JSONObject tObject = new JSONObject(super.contructSimulatorResponse());
        tObject.put("rcm", "[0] Pembayaran Sukses ke:"+getTujuan());
        tObject.put("text", "Isi pulsa "+getVoucherId()+" berhasil "
                + "untuk nomor "+getTujuan()+" pada tgl "
                + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
        tObject.put("serialnumber", UUID.randomUUID().toString());
        tObject.put("serialnumber", UUID.randomUUID().toString());
        
        return tObject.toString();
    }

    @Override
    public String getComand() {
        return "PAY";
    }
}
