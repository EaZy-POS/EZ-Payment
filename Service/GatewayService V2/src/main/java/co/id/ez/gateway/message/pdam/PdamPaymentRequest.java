/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.gateway.message.pdam;

/**
 *
 * @author lutfi
 */
public class PdamPaymentRequest extends PdamInquiryRequest {

    @Override
    public String getMessageStream() {
        return super.getMessageStream();
    }
    
    @Override
    public String getComand() {
        return "PAY";
    }

    @Override
    public String contructSimulatorResponse() {
        String tRefnum = getRefnum();
        String result = "{"
                + "\"status\": \"SUCCESS\", "
                + "\"rc\": \"0000\", "
                + "\"rcm\": \"[0] Pembayaran Sukses ke:" + getIdpel() + " refnum:" + tRefnum + "\", "
                + "\"text\": \"BLN MAR24\\nRP 278.505,00\\nADM RP 5\\nSUKSES\", "
                + "\"dt\": \"2024-03-25 10:20:09\", "
                + "\"refnum\": \"93752DAAEF4C4D44BC04F728CAB73A16\", "
                + "\"subid\": \"" + getIdpel() + "\", "
                + "\"name\": \"Dmmy'Smltor.Name-" + getIdpel() + "\", "
                + "\"subaddress\": \"\", "
                + "\"subsegmen\": \"3A\", "
                + "\"admincharges\": 5, "
                + "\"adminpdam\": 5000, "
                + "\"transamount\": 278505, "
                + "\"mtrstr\": 220, "
                + "\"mtrend\": 250, "
                + "\"billmonth\": \"MAR24\", "
                + "\"billpdam\": 45000, "
                + "\"billtotal\": 278500, "
                + "\"installment\": 222500, "
                + "\"waste\": 0, "
                + "\"stamp\": 6000, "
                + "\"vat\": 0, "
                + "\"danameter\": 0, "
                + "\"penalty\": 0, "
                + "\"info\": \"PT AETRA MENYATAKAN RESI INI MERUPAKAN BUKTI PEMBAYARAN YANG SAH\", "
                + "\"bln_1\": \"MAR24\", "
                + "\"tagihan_1\": 45000, "
                + "\"stamp_1\": 6000, "
                + "\"penalty_1\": 0, "
                + "\"danameter_1\": 0, "
                + "\"vat_1\": 0, "
                + "\"waste_1\": 0, "
                + "\"admpdam_1\": 5000, "
                + "\"adm_1\": 5, "
                + "\"installment_1\": 222500, "
                + "\"mtrstart_1\": 220, "
                + "\"mtrend_1\": 250, "
                + "\"mtruse_1\": 30, "
                + "\"bln_2\": \"-\", "
                + "\"tagihan_2\": 0, "
                + "\"stamp_2\": 0, "
                + "\"penalty_2\": 0, "
                + "\"danameter_2\": 0, "
                + "\"vat_2\": 0, "
                + "\"waste_2\": 0, "
                + "\"admpdam_2\": 0, "
                + "\"adm_2\": 0, "
                + "\"installment_2\": 0, "
                + "\"mtrstart_2\": 0, "
                + "\"mtrend_2\": 0, "
                + "\"mtruse_2\": 0, "
                + "\"bln_3\": \"-\", "
                + "\"tagihan_3\": 0, "
                + "\"stamp_3\": 0, "
                + "\"penalty_3\": 0, "
                + "\"danameter_3\": 0, "
                + "\"vat_3\": 0, "
                + "\"waste_3\": 0, "
                + "\"admpdam_3\": 0, "
                + "\"adm_3\": 0, "
                + "\"installment_3\": 0, "
                + "\"mtrstart_3\": 0, "
                + "\"mtrend_3\": 0, "
                + "\"mtruse_3\": 0, "
                + "\"bln_4\": \"-\", "
                + "\"tagihan_4\": 0, "
                + "\"stamp_4\": 0, "
                + "\"penalty_4\": 0, "
                + "\"danameter_4\": 0, "
                + "\"vat_4\": 0, "
                + "\"waste_4\": 0, "
                + "\"admpdam_4\": 0, "
                + "\"adm_4\": 0, "
                + "\"installment_4\": 0, "
                + "\"mtrstart_4\": 0, "
                + "\"mtrend_4\": 0, "
                + "\"mtruse_4\": 0, "
                + "\"trxid\": \"" + getTrxid() + "\"}";
        return result;
    }
}
