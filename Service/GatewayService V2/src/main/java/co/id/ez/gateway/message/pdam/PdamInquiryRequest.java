/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.gateway.message.pdam;

import co.id.ez.gateway.message.BillerRequest;
import java.util.UUID;

/**
 *
 * @author lutfi
 */
public class PdamInquiryRequest extends BillerRequest {

    private String idpel;
    private String biller;
    private boolean detail;

    public String getIdpel() {
        return idpel;
    }

    public void setIdpel(String idpel) {
        this.idpel = idpel;
    }

    public String getBiller() {
        return biller;
    }

    public void setBiller(String biller) {
        this.biller = biller;
    }

    public boolean isDetail() {
        return detail;
    }

    public void setDetail(boolean detail) {
        this.detail = detail;
    }

    @Override
    public String getMessageStream() {
        return super.getMessageStream()
                + "&idpel=" + idpel + "&biller=" + biller + "&detail=" + detail;
    }

    @Override
    public String getRemarks() {
        return super.getRemarks() + " idpel " + idpel + "(" + biller + ")";
    }
    
    @Override
    public String getModuleCode() {
        return "PAM";
    }
    
    @Override
    public String getModule() {
        return "pdam";
    }
    
    @Override
    public String getComand() {
        return "INQ";
    }

    @Override
    public String contructSimulatorResponse() {
        String tRefnum = UUID.randomUUID().toString().replace("-", "").toUpperCase();
        String result = "{\"status\":\"SUCCESS\","
                + "\"rc\":\"0000\","
                + "\"rcm\":\"[0] Cek Tagihan Sukses\","
                + "\"text\":\"2734211010100\\nDmmy'Smltor.Name-273\\n1 BLN\\nMAR24\\nRP TAG PDAM RP 278.500\\nADM RP 5\\nTOTAL RP 278.505\\n\","
                + "\"refnum\":\"" + tRefnum + "\","
                + "\"subid\":\"" + getIdpel() + "\","
                + "\"name\":\"Dmmy'Smltor.Name-" + getIdpel() + "\","
                + "\"totalperiod\":\"1 BLN\","
                + "\"billperiod\":\"MAR24\","
                + "\"admincharge\":\"5\","
                + "\"transamount\":\"278505\","
                + "\"bln_1\":\"MAR24\","
                + "\"tagihan_1\":45000,"
                + "\"stamp_1\":6000,"
                + "\"penalty_1\":0,"
                + "\"danameter_1\":0,"
                + "\"vat_1\":0,"
                + "\"waste_1\":0,"
                + "\"admpdam_1\":5000,"
                + "\"adm_1\":5,"
                + "\"installment_1\":222500,"
                + "\"mtrstart_1\":220,"
                + "\"mtrend_1\":250,"
                + "\"mtruse_1\":30,"
                + "\"bln_2\":\"-\","
                + "\"tagihan_2\":0,"
                + "\"stamp_2\":0,"
                + "\"penalty_2\":0,"
                + "\"danameter_2\":0,"
                + "\"vat_2\":0,"
                + "\"waste_2\":0,"
                + "\"admpdam_2\":0,"
                + "\"adm_2\":0,"
                + "\"installment_2\":0,"
                + "\"mtrstart_2\":0,"
                + "\"mtrend_2\":0,"
                + "\"mtruse_2\":0,"
                + "\"bln_3\":\"-\","
                + "\"tagihan_3\":0,"
                + "\"stamp_3\":0,"
                + "\"penalty_3\":0,"
                + "\"danameter_3\":0,"
                + "\"vat_3\":0,"
                + "\"waste_3\":0,"
                + "\"admpdam_3\":0,"
                + "\"adm_3\":0,"
                + "\"installment_3\":0,"
                + "\"mtrstart_3\":0,"
                + "\"mtrend_3\":0,"
                + "\"mtruse_3\":0,"
                + "\"bln_4\":\"-\","
                + "\"tagihan_4\":0,"
                + "\"stamp_4\":0,"
                + "\"penalty_4\":0,"
                + "\"danameter_4\":0,"
                + "\"vat_4\":0,"
                + "\"waste_4\":0,"
                + "\"admpdam_4\":0,"
                + "\"adm_4\":0,"
                + "\"installment_4\":0,"
                + "\"mtrstart_4\":0,"
                + "\"mtrend_4\":0,"
                + "\"mtruse_4\":0,"
                + "\"trxid\":\"" + getTrxid() + "\""
                + "}";

        return result;
    }

}
