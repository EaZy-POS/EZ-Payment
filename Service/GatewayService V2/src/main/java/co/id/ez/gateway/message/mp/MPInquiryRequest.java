/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.gateway.message.mp;

import co.id.ez.gateway.message.BillerRequest;
import co.id.ez.system.core.ex.ServiceException;
import co.id.ez.system.core.rc.RC;
import java.util.UUID;

/**
 *
 * @author RCS
 */
public class MPInquiryRequest extends BillerRequest {

    private String input1, input2, input3;
    private String biller;

    public String getInput1() {
        return input1;
    }

    public void setInput1(String input1) {
        this.input1 = input1;
    }

    public String getBiller() {
        return biller;
    }

    public void setBiller(String biller) {
        this.biller = biller;
    }

    public String getInput2() {
        return input2;
    }

    public void setInput2(String input2) {
        this.input2 = input2;
    }

    public String getInput3() {
        return input3;
    }

    public void setInput3(String input3) {
        this.input3 = input3;
    }

    @Override
    public String getMessageStream() {
        return super.getMessageStream() + "&input1=" + input1 + "&biller=" + biller
                + (input2 != null ? input2.equals("") ? "&input2=" + input2 : "" : "")
                + (input3 != null ? input3.equals("") ? "&input3=" + input3 : "" : "");
    }

    @Override
    public String getRemarks() {
        return super.getRemarks() + " idpel " + input1 + "(" + biller + ")";
    }
    
    @Override
    public String getModuleCode() {
        return "MTP";
    }

    @Override
    public String getModule() {
        return "gp";
    }

    @Override
    public String getComand() {
        return "INQ";
    }

    @Override
    public String contructSimulatorResponse() {
        switch (biller) {
            case "bpjs":
                return getBPJSSimulator();
            default:
                throw new ServiceException(RC.ERROR_UNREGISTERED_PRODUCT, "biller tidak ditemukan");
        }
    }

    private String getBPJSSimulator() {
        String refnum = UUID.randomUUID().toString().replace("-", "").toUpperCase();
        String result = "{\n"
                + "\"status\": \"SUCCESS\", "
                + "\"rc\": \"0000\", "
                + "\"rcm\": \"[0] Cek Tagihan Sukses ke:8889991010400 refnum:" + refnum + "\", "
                + "\"text\": \"INFORMASI TAGIHAN BPJS KESEHATAN-FNT\\nID PEL:" + input1 + "\\n\", "
                + "\"refnum\": \"" + refnum + "\", "
                + "\"input1\": \"" + input1 + "\", "
                + "\"jmltagihan\": 255000,\n"
                + "\"admin\": 2500,\n"
                + "\"totaltag\": 257500,\n"
                + "\"data\": {\n"
                + "  \"NO_VA\": \"8888800000100\", "
                + "  \"NAMA_PELANGGAN\": \"EKI HENDRAWANBRATA\", "
                + "  \"JML_BULAN\": \"01\", "
                + "  \"NO_TELEPON\": \"089655089782\", "
                + "  \"JUMLAH_PESERTA\": \"3\", "
                + "  \"TOTAL_SALDO\": \"0\", "
                + "  \"TOTAL_PREMI\": \"255000\", "
                + "  \"ID_TRANSAKSI\": \"" + refnum + "\"\n"
                + "},\n"
                + "\"trxid\": \"" + getTrxid() + "\"\n"
                + "}";

        return result;
    }

}
