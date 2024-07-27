/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.model.data.search;

import co.id.ez.ezpay.interfaces.Model;
import java.math.BigDecimal;

/**
 *
 * @author Lutfi
 */
public class VoucherModel implements Model{

    private final String voucher_id;
    private final String voucher_name;
    private final String provider;
    private final String prefix;
    private final BigDecimal price_sale;

    public VoucherModel(String voucher_id, String voucher_name, String provider, String prefix, BigDecimal price_sale) {
        this.voucher_id = voucher_id;
        this.voucher_name = voucher_name;
        this.provider = provider;
        this.prefix = prefix;
        this.price_sale = price_sale;
    }

    public String getVoucher_id() {
        return voucher_id;
    }

    public String getVoucher_name() {
        return voucher_name;
    }

    public String getProvider() {
        return provider;
    }

    public BigDecimal getPrice_sale() {
        return price_sale;
    }

    public String getPrefix() {
        return prefix;
    }
    
    @Override
    public String getKey(){
        return getPrefix() + "#" +getValue();
    }

    @Override
    public String toString() {
        return "VoucherModel{" + "voucher_id=" + voucher_id 
                + ", voucher_name=" + voucher_name 
                + ", provider=" + provider 
                + ", price_sale=" + price_sale + '}';
    }

    @Override
    public String getValue() {
        return getVoucher_id() + "-" + getVoucher_name() + " ("+getProvider()+")";
    }

    @Override
    public String getInfo() {
        return getValue();
    }
    
    
}
