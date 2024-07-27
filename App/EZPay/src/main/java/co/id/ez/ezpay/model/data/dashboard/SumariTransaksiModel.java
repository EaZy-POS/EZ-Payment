/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.model.data.dashboard;

import co.id.ez.ezpay.interfaces.DataTable;

/**
 *
 * @author Lutfi
 */
public class SumariTransaksiModel implements DataTable{
    
    private final String number;
    private final String tanggal;
    private final String modul;
    private final String jmlTransaksi;
    private final String total;

    public SumariTransaksiModel(String number, String modul, String tanggal, String jmlTransaksi, String total) {
        this.number = number;
        this.modul = modul;
        this.jmlTransaksi = jmlTransaksi;
        this.total = total;
        this.tanggal = tanggal;
    }

    public String getTanggal() {
        return tanggal;
    }

    public String getNumber() {
        return number;
    }

    public String getModul() {
        return modul;
    }

    public String getJmlTransaksi() {
        return jmlTransaksi;
    }

    public String getTotal() {
        return total;
    }
    
    @Override
    public Object[] getArrayData() {
        return new Object[]{
            number,
            modul,
            tanggal,
            jmlTransaksi,
            total
        };
    }

        
}
