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
public class DailyRecapPDAMModel implements DataTable{
    
    private final String number;
    private final String tanggal;
    private final String idtransaksi;
    private final String reffnum;
    private final String kasir;
    private final String subid;
    private final String name;
    private final String tarif;
    private final String bulan;
    private final String total;

    public DailyRecapPDAMModel(String number, String tanggal, String idtransaksi, String reffnum, String kasir, String subid, String name, String tarif, String bulan, String total) {
        this.number = number;
        this.tanggal = tanggal;
        this.idtransaksi = idtransaksi;
        this.reffnum = reffnum;
        this.kasir = kasir;
        this.subid = subid;
        this.name = name;
        this.tarif = tarif;
        this.bulan = bulan;
        this.total = total;
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

    public String getName() {
        return name;
    }

    public String getTarif() {
        return tarif;
    }

    public String getBulan() {
        return bulan;
    }

    public String getTotal() {
        return total;
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
            name,
            tarif,
            bulan,
            total
        };
    }

        
}
