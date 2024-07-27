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
    public class VoucherProductModel implements DataTable{

    private final int number;
    private final String voucherid, vouchername, provider, tipe, status, Aksi;
    private final BigDecimal harga, harga_jual;

    public VoucherProductModel(
            int number, 
            String voucherid, 
            String vouchername, 
            String provider, 
            BigDecimal harga,
            BigDecimal harga_jual, 
            String tipe, 
            String status) {
        this.number = number;
        this.voucherid = voucherid;
        this.vouchername = vouchername;
        this.provider = provider;
        this.status = status;
        this.harga_jual = harga_jual;
        this.harga = harga;
        this.tipe = tipe;
        this.Aksi = null;
    }

    public int getNumber() {
        return number;
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

    public String getStatus() {
        return status;
    }

    public BigDecimal getHarga_jual() {
        return harga_jual;
    }

    public String getTipe() {
        return tipe;
    }

    public BigDecimal getHarga() {
        return harga;
    }
    
    @Override
    public Object[] getArrayData() {
        return new Object[]{
            number, 
            voucherid, 
            vouchername, 
            provider, 
            tipe, 
            Common.formatRupiah(harga.doubleValue()),
            Common.formatRupiah(harga_jual.doubleValue()),
            status,
            Aksi
        };
    }
    
}
