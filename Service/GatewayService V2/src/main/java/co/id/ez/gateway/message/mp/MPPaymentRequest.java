/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.gateway.message.mp;

import co.id.ez.system.core.ex.ServiceException;
import co.id.ez.system.core.rc.RC;

/**
 *
 * @author lutfi
 */
public class MPPaymentRequest extends MPInquiryRequest {

    private String amount;

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
    public String getComand() {
        return "PAY";
    }

    @Override
    public String contructSimulatorResponse() {
        switch (getBiller()) {
            case "bpjs":
                return getBPJSSimulator();
            default:
                throw new ServiceException(RC.ERROR_UNREGISTERED_PRODUCT, "biller tidak ditemukan");
        }
    }

    private String getBPJSSimulator() {
        String refnum = getRefnum();
        String result = "{ "
                + "\"totaltag\": \"257500\", "
                + "\"biller\": \"bpjs\", "
                + "\"amount\": 257500, "
                + "\"data\": { "
                + "\"NO_TELEPON\": \"089655089782\", "
                + "\"JML_BULAN\": \"01\", "
                + "\"JUMLAH_PESERTA\": \"3\", "
                + "\"TOTAL_SALDO\": \"0\", "
                + "\"NAMA_PELANGGAN\": \"EKI HENDRAWANBRATA\", "
                + "\"ID_TRANSAKSI\": \"" + refnum + "\", "
                + "\"NO_VA\": \"8888800000100\", "
                + "\"TOTAL_PREMI\": \"255000\" "
                + "}, "
                + "\"input1\": \"" + getInput1() + "\", "
                + "\"admin\": \"2500\", "
                + "\"modul\": \"gp\", "
                + "\"trxid\": \"" + getTrxid() + "\", "
                + "\"command\": \"PAY\", "
                + "\"rc\": \"0000\", "
                + "\"infotext\": \"HARAP STRUK INI DISIMPAN SEBAGAI TANDA PEMBAYARAN YANG SAH.INFO LEBIH LANJUT HUB CALL CENTER BPJS Kesehatan 1500-400\", "
                + "\"refnum\": \"" + refnum + "\", "
                + "\"text\": \"PEMBAYARAN SUKSES BPJS KESEHATAN-FNT\\n05/01/2024 00:23\\nID PEL:" + getInput1() + "\\n\", "
                + "\"rcm\": \"[0] Pembayaran Sukses ke:8889991010400 refnum:" + refnum + "\", "
                + "\"jmltagihan\": \"255000\", "
                + "\"status\": \"SUCCESS\" "
                + "}";

        return result;
    }
}
