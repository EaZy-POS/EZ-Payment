/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.gateway.message.postpaid;

import co.id.ez.gateway.message.BillerRequest;
import java.util.UUID;

/**
 *
 * @author lutfi
 */
public class PLNPostpaidInquiryRequest extends BillerRequest {

    private String idpel;
    private boolean Detail;

    public String getIdpel() {
        return idpel;
    }

    public void setIdpel(String idpel) {
        this.idpel = idpel;
    }

    public boolean isDetail() {
        return Detail;
    }

    public void setDetail(boolean detail) {
        this.Detail = detail;
    }

    @Override
    public String getMessageStream() {
        return super.getMessageStream() + "&idpel=" + idpel + "&Detail=" + Detail;
    }

    @Override
    public String getRemarks() {
        return super.getRemarks() + " idpel " + idpel;
    }

    @Override
    public String contructSimulatorResponse() {
        String refnum = UUID.randomUUID().toString().replace("-", "").toUpperCase();
        String result = "{\n"
                + "\"status\": \"SUCCESS\", "
                + "\"rc\": \"0000\", "
                + "\"rcm\": \"[0] CEK TAGIHAN SUKSES ke:532110059808 refnum:" + refnum + "\", "
                + "\"text\": \"" + idpel + "\\nDU'MMY-VSI-532110059\\nR1/450\\n2 BLN\\nAGU23,SEP23\\nRP TAG PLN RP 475.085\\nADM RP 10\\nTOTAL RP 475.095\\n\", "
                + "\"refnum\": \"" + refnum + "\", "
                + "\"subid\": \"" + idpel + "\", "
                + "\"nama\": \"DU'MMY-VSI-" + idpel + "\", "
                + "\"bulan\": \"2 BLN\", "
                + "\"blth\": \"AGU23, SEP23\", "
                + "\"admin\": 10, "
                + "\"tagihan\": 475085, "
                + "\"total\": 475095, "
                + "\"tarif\": \"R1/450\", "
                + "\"bln_1\": \"AGU23\", "
                + "\"meter_1\": \"00888800-00889900\", "
                + "\"tagbln_1\": 324654, "
                + "\"admin_1\": 5, "
                + "\"penalty_1\": 9000, "
                + "\"bln_2\": \"SEP23\", "
                + "\"meter_2\": \"00888800-00889900\", "
                + "\"tagbln_2\": 132431, "
                + "\"admin_2\": 5, "
                + "\"penalty_2\": 9000, "
                + "\"bln_3\": \"-\", "
                + "\"meter_3\": \"0\", "
                + "\"tagbln_3\": 0, "
                + "\"admin_3\": 0, "
                + "\"penalty_3\": 0, "
                + "\"bln_4\": \"-\", "
                + "\"meter_4\": \"0\", "
                + "\"tagbln_4\": 0, "
                + "\"admin_4\": 0, "
                + "\"penalty_4\": 0, "
                + "\"trxid\": \"" + getTrxid() + "\"\n"
                + "}";

        return result;
    }

    @Override
    public String getModuleCode() {
        return "PLN";
    }
    
    @Override
    public String getComand() {
        return "INQ";
    }

    @Override
    public String getModule() {
        return "PLN";
    }

}
