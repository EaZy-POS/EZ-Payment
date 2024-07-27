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
public class DailyRecapVoucherModel implements DataTable{
    
    private final String number;
    private final String tanggal;
    private final String idtransaksi;
    private final String reffnum;
    private final String kasir;
    private final String tujuan;
    private final String voucherid;
    private final String vouchername;
    private final String provider;
    private final String serialnumber;
    private final String nominal;
    private final String hargajual;

    public DailyRecapVoucherModel(String number, String tanggal, String idtransaksi, String reffnum, 
            String kasir, String tujuan, String voucherid, String vouchername, 
            String provider, String serialnumber, String nominal, String hargajual) {
        this.number = number;
        this.tanggal = tanggal;
        this.idtransaksi = idtransaksi;
        this.reffnum = reffnum;
        this.kasir = kasir;
        this.tujuan = tujuan;
        this.voucherid = voucherid;
        this.vouchername = vouchername;
        this.provider = provider;
        this.serialnumber = serialnumber;
        this.nominal = nominal;
        this.hargajual = hargajual;
    }

    public String getNumber() {
        return number;
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

    public String getTujuan() {
        return tujuan;
    }

    public String getVoucherid() {
        return voucherid;
    }

    public String getVouchername() {
        return vouchername;
    }

    public String getProvider() {
        return provider;
    }

    public String getSerialnumber() {
        return serialnumber;
    }

    public String getNominal() {
        return nominal;
    }

    public String getHargajual() {
        return hargajual;
    }

    public String getTanggal() {
        return tanggal;
    }

    @Override
    public Object[] getArrayData() {
        return new Object[]{
            number,
            tanggal,
            idtransaksi,
            reffnum,
            kasir,
            tujuan,
            voucherid,
            vouchername,
            provider,
            serialnumber,
            nominal,
            hargajual
        };
    }

        
}
