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
public class DailyRecapMPModel implements DataTable{
    
    private final String number;
    private final String biller;
    private final String tanggal;
    private final String idtransaksi;
    private final String reffnum;
    private final String kasir;
    private final String subid;
    private final String detail;
    private final String total;

    public DailyRecapMPModel(String number, String biller, String tanggal, String idtransaksi, String reffnum, String kasir, String subid, String detail, String total) {
        this.number = number;
        this.biller = biller;
        this.tanggal = tanggal;
        this.idtransaksi = idtransaksi;
        this.reffnum = reffnum;
        this.kasir = kasir;
        this.subid = subid;
        this.detail = detail;
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

    public String getDetail() {
        return detail;
    }

    public String getTotal() {
        return total;
    }

    public String getBiller() {
        return biller;
    }

    
    @Override
    public Object[] getArrayData() {
        return new Object[]{
            number,
            biller,
            tanggal,
            idtransaksi,
            reffnum,
            kasir,
            subid,
            detail,
            total
        };
    }

        
}
