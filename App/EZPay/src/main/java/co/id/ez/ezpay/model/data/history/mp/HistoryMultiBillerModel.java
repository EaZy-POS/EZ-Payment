/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.model.data.history.mp;

import co.id.ez.ezpay.interfaces.DataTable;

/**
 *
 * @author Lutfi
 */
public abstract class HistoryMultiBillerModel implements DataTable {

    private int number;
    private String tanggal;
    private String trxid;
    private String idpel1;
    private String idpel2;
    private String idpel3;
    private String detail;
    private String biller;
    private String tagihan;
    private String status;
    private String Aksi;

    public void create(int number, String tanggal, String trxid, 
            String idpel1, String idpel2, String idpel3, 
            String detail, String biller, String tagihan, 
            String status) {
        this.number = number;
        this.tanggal = tanggal;
        this.trxid = trxid;
        this.idpel1 = idpel1;
        this.idpel2 = idpel2;
        this.idpel3 = idpel3;
        this.detail = detail;
        this.biller = biller;
        this.tagihan = tagihan;
        this.status = status;
        this.Aksi = null;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getTrxid() {
        return trxid;
    }

    public void setTrxid(String trxid) {
        this.trxid = trxid;
    }

    public String getIdpel1() {
        return idpel1;
    }

    public void setIdpel1(String idpel1) {
        this.idpel1 = idpel1;
    }

    public String getIdpel2() {
        return idpel2;
    }

    public void setIdpel2(String idpel2) {
        this.idpel2 = idpel2;
    }

    public String getIdpel3() {
        return idpel3;
    }

    public void setIdpel3(String idpel3) {
        this.idpel3 = idpel3;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getBiller() {
        return biller;
    }

    public void setBiller(String biller) {
        this.biller = biller;
    }

    public String getTagihan() {
        return tagihan;
    }

    public void setTagihan(String tagihan) {
        this.tagihan = tagihan;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAksi() {
        return Aksi;
    }

    public void setAksi(String Aksi) {
        this.Aksi = Aksi;
    }

    @Override
    public boolean isSuccess() {
        return getStatus().equalsIgnoreCase("sukses");
    }

}
