/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.model.data.central;

import co.id.ez.ezpay.app.Common;
import co.id.ez.ezpay.interfaces.DataTable;
import java.math.BigDecimal;

/**
 *
 * @author Lutfi
 */
public class CentralPrepaidDenomModel implements DataTable{

    private int number;
    private final String id, denom, description, status, aksi;
    private BigDecimal sale_price;

    public CentralPrepaidDenomModel(int number, String id, String denom, String description, BigDecimal sale_price, String status, String aksi) {
        this.number = number;
        this.id = id;
        this.denom = denom;
        this.description = description;
        this.sale_price = sale_price;
        this.status = status;
        this.aksi = aksi;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getId() {
        return id;
    }

    public String getDenom() {
        return denom;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getSale_price() {
        return sale_price;
    }

    public String getStatus() {
        return status;
    }

    public String getAksi() {
        return aksi;
    }

    
    @Override
    public Object[] getArrayData() {
        return new Object[]{number, denom, description, Common.formatRupiah(sale_price.doubleValue(), false), status, aksi};
    }
}
