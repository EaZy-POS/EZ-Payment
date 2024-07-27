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
public class MonthlyRecapModel implements DataTable{
    
    private final String number;
    private final String bulan;
    private final String tanggal;
    private final String jmltransaki;
    private final String total;

    public MonthlyRecapModel(String number, String bulan, String tanggal, String jmltransaki, String total) {
        this.number = number;
        this.bulan = bulan;
        this.tanggal = tanggal;
        this.jmltransaki = jmltransaki;
        this.total = total;
    }

    public String getNumber() {
        return number;
    }

    public String getBulan() {
        return bulan;
    }

    public String getTanggal() {
        return tanggal;
    }

    public String getJmltransaki() {
        return jmltransaki;
    }

    public String getTotal() {
        return total;
    }
    
    @Override
    public Object[] getArrayData() {
        return new Object[]{
            number,
            bulan,
            tanggal,
            jmltransaki,
            total
        };
    }

        
}
