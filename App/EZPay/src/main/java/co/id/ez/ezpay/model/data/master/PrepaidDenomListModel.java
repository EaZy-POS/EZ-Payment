/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.model.data.master;

import co.id.ez.ezpay.app.Common;
import co.id.ez.ezpay.interfaces.DataTable;
import java.math.BigDecimal;

/**
 *
 * @author Lutfi
 */
public class PrepaidDenomListModel implements DataTable{

    private final int number;
    private final String deskripsi, status;
    private final BigDecimal harga_jual, denom;

    public PrepaidDenomListModel(int number, BigDecimal denom, String deskripsi, 
            BigDecimal harga_jual, String status) {
        this.number = number;
        this.denom = denom;
        this.deskripsi = deskripsi;
        this.status = status;
        this.harga_jual = harga_jual;
    }

    public int getNumber() {
        return number;
    }

    public BigDecimal getDenom() {
        return denom;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public String getStatus() {
        return status;
    }

    public BigDecimal getHarga_jual() {
        return harga_jual;
    }

    
    @Override
    public Object[] getArrayData() {
        return new Object[]{number, Common.formatRupiah(denom.doubleValue()), 
            deskripsi, 
            Common.formatRupiah(harga_jual.doubleValue()),status};
    }
    
}
