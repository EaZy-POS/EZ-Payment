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
public class CentralVoucherProductModel implements DataTable {

    private int number;
    private final String id, voucherid, type, providerId, provider, voucherName, status, aksi;
    private final BigDecimal price, markup, base_price;

    public CentralVoucherProductModel(int number, String id, String voucherid,
            String providerId, String voucherName, String provider, String type,
            BigDecimal base_price, BigDecimal markup, BigDecimal price, String status, String aksi) {
        this.number = number;
        this.id = id;
        this.voucherid = voucherid;
        this.voucherName = voucherName;
        this.type = type;
        this.price = price;
        this.status = status;
        this.aksi = aksi;
        this.provider = provider;
        this.providerId = providerId;
        this.base_price = base_price;
        this.markup = markup;
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

    public String getVoucherid() {
        return voucherid;
    }

    public String getType() {
        return type;
    }

    public String getVoucherName() {
        return voucherName;
    }

    public String getStatus() {
        return status;
    }

    public String getAksi() {
        return aksi;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getProvider() {
        return provider;
    }

    public String getProviderId() {
        return providerId;
    }

    public BigDecimal getMarkup() {
        return markup;
    }

    public BigDecimal getBase_price() {
        return base_price;
    }

    @Override
    public Object[] getArrayData() {
        return new Object[]{
            number,
            voucherid,
            voucherName,
            provider,
            type,
            Common.formatRupiah(base_price.doubleValue(), false),
            Common.formatRupiah(markup.doubleValue(), false),
            Common.formatRupiah(price.doubleValue(), false),
            status,
            aksi
        };
    }
}
