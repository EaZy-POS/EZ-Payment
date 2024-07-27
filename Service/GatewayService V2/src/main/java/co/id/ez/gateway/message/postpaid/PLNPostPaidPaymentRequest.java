/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.gateway.message.postpaid;

import java.util.UUID;

/**
 *
 * @author RCS
 */
public class PLNPostPaidPaymentRequest extends PLNPostpaidInquiryRequest {

    private String amount;

    public PLNPostPaidPaymentRequest(String comand, String modul) {
        super(comand, modul);
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    @Override
    public String getMessageStream() {
        return super.getMessageStream() + "&amount=" + amount;
    }

    @Override
    public String contructSimulatorResponse() {
        String refnum = getRefnum();
        String result = "{\n"
                + "\"status\": \"SUCCESS\", "
                + "\"rc\": \"0000\", "
                + "\"rcm\": \"[0] PEMBAYARAN SUKSES ke:532110059808 refnum:" + refnum + "\", "
                + "\"text\": \"20/03/24 14:01\\n" + refnum + "\\n532110059808\\nDU'MMY-VSI-" + getIdpel() + "\\nR1/450\\nBLN AGU23,SEP23\\nRP 475.085\\nADM RP 10\\nTOTAL RP 475.095\\nSUKSES\", "
                + "\"date\": \"2024-03-20 14:01:40\", "
                + "\"refnum\": \"" + refnum + "\", "
                + "\"subid\": \"" + getIdpel() + "\", "
                + "\"nama\": \"DU'MMY-VSI-" + getIdpel() + "\", "
                + "\"tarif\": \"R1/450\", "
                + "\"standmeter\": \"1777600-1779800\", "
                + "\"blth\": \"AGU23, SEP23\", "
                + "\"ppn\": 0,\n"
                + "\"denda\": 18000,\n"
                + "\"tagihan\": 475085,\n"
                + "\"admin\": 10,\n"
                + "\"total\": 475095,\n"
                + "\"info1\": \"Informasi Hubungi Call Center: 123 Atau Hub. PLN Terdekat:\", "
                + "\"info2\": \"Terima Kasih\", "
                + "\"bln_1\": \"AGU23\", "
                + "\"meter_1\": \"00888800-00889900\", "
                + "\"tagbln_1\": 324654,\n"
                + "\"admin_1\": 5,\n"
                + "\"penalty_1\": 9000,\n"
                + "\"bln_2\": \"SEP23\", "
                + "\"meter_2\": \"00888800-00889900\", "
                + "\"tagbln_2\": 132431,\n"
                + "\"admin_2\": 5,\n"
                + "\"penalty_2\": 9000,\n"
                + "\"bln_3\": \"-\", "
                + "\"meter_3\": \"0\", "
                + "\"tagbln_3\": 0,\n"
                + "\"admin_3\": 0,\n"
                + "\"penalty_3\": 0,\n"
                + "\"bln_4\": \"-\", "
                + "\"meter_4\": \"0\", "
                + "\"tagbln_4\": 0,\n"
                + "\"admin_4\": 0,\n"
                + "\"penalty_4\": 0,\n"
                + "\"trxid\": \"" + getTrxid() + "\"\n"
                + "}";

        return result;
    }

}
