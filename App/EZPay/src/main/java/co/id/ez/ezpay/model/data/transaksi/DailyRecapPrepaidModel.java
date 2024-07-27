/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.model.data.transaksi;

import co.id.ez.ezpay.interfaces.DataTable;

/**
 *
 * @author Lutfi
 */
public class DailyRecapPrepaidModel implements DataTable{
    
    private final String number;
    private final String tanggal;
    private final String idtransaksi;
    private final String reffnum;
    private final String kasir;
    private final String subid;
    private final String msn;
    private final String name;
    private final String tarif;
    private final String nominal;
    private final String kwh;
    private final String token;
    private final String hargajual;

    public DailyRecapPrepaidModel(String number, String tanggal, String idtransaksi, String reffnum, String kasir, String subid, String msn, String name, String tarif, String nominal, String kwh, String token, String hargajual) {
        this.number = number;
        this.tanggal = tanggal;
        this.idtransaksi = idtransaksi;
        this.reffnum = reffnum;
        this.kasir = kasir;
        this.subid = subid;
        this.msn = msn;
        this.name = name;
        this.tarif = tarif;
        this.nominal = nominal;
        this.kwh = kwh;
        this.token = token;
        this.hargajual = hargajual;
    }

    public String getNumber() {
        return number;
    }

    public String getTanggal() {
        return tanggal;
    }

    public String getIdtransaksi() {
        return idtransaksi;
    }

    public String getReffnum() {
        return reffnum;
    }

    public String getKasir() {
        return kasir;
    }

    public String getSubid() {
        return subid;
    }

    public String getMsn() {
        return msn;
    }

    public String getName() {
        return name;
    }

    public String getTarif() {
        return tarif;
    }

    public String getNominal() {
        return nominal;
    }

    public String getKwh() {
        return kwh;
    }

    public String getToken() {
        return token;
    }

    public String getHargajual() {
        return hargajual;
    }

    
    @Override
    public Object[] getArrayData() {
        return new Object[]{
            number,
            tanggal,
            idtransaksi,
            reffnum,
            kasir,
            subid,
            msn,
            name,
            tarif,
            nominal,
            kwh,
            token,
            hargajual
        };
    }

        
}
