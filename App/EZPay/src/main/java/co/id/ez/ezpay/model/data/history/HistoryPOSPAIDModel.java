/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.model.data.history;

import co.id.ez.ezpay.interfaces.DataTable;

/**
 *
 * @author RCS
 */
public class HistoryPOSPAIDModel implements DataTable {

    private int number;
    private String tanggal;
    private String trxid;
    private String idpel;
    private String nama;
    private String tarif;
    private String tagihan;
    private String status, Aksi;

    public HistoryPOSPAIDModel(int number, String tanggal, String trxid, String idpel, 
            String nama, String tarif, String tagihan, String status) {
        this.number = number;
        this.tanggal = tanggal;
        this.trxid = trxid;
        this.idpel = idpel;
        this.nama = nama;
        this.tarif = tarif;
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

    public String getIdpel() {
        return idpel;
    }

    public void setIdpel(String idpel) {
        this.idpel = idpel;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getTarif() {
        return tarif;
    }

    public void setTarif(String tarif) {
        this.tarif = tarif;
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
    public Object[] getArrayData() {
        Object[] row = new Object[]{
            number,
            tanggal,
            trxid,
            idpel,
            nama,
            tarif,
            tagihan,
            status,
            Aksi
        };
        return row;
    }
    
    @Override
    public boolean isSuccess() {
        return getStatus().equalsIgnoreCase("sukses");
    }

}
