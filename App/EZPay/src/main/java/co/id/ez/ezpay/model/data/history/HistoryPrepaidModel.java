/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.model.data.history;

import co.id.ez.ezpay.interfaces.DataTable;

/**
 *
 * @author Lutfi
 */
public class HistoryPrepaidModel implements DataTable {

    private int number;
    private String tanggal;
    private String trxid;
    private String idpel;
    private String msn;
    private String nama;
    private String nominal;
    private String token;
    private String status, Aksi;

    public HistoryPrepaidModel(int number, String tanggal, String trxid, String idpel, String msn, String nama, String nominal, String token, String status) {
        this.number = number;
        this.tanggal = tanggal;
        this.trxid = trxid;
        this.idpel = idpel;
        this.msn = msn;
        this.nama = nama;
        this.nominal = nominal;
        this.token = token;
        this.status = status;
        this.Aksi = null;
    }

    @Override
    public Object[] getArrayData() {
        Object[] row = new Object[]{
            number,
            tanggal,
            trxid,
            idpel,
            msn,
            nama,
            nominal,
            token,
            status,
            Aksi
        };
        return row;
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

    public String getIdpel() {
        return idpel;
    }

    public void setIdpel(String idpel) {
        this.idpel = idpel;
    }

    public String getMsn() {
        return msn;
    }

    public void setMsn(String msn) {
        this.msn = msn;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNominal() {
        return nominal;
    }

    public void setNominal(String nominal) {
        this.nominal = nominal;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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
