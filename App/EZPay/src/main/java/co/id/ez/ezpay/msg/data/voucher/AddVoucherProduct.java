/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.msg.data.voucher;

import co.id.ez.ezpay.msg.data.CentralDataRequest;
import com.json.JSONObject;

/**
 *
 * @author Lutfi
 */
public class AddVoucherProduct extends CentralDataRequest{
    
    String provider_id, voucher_id, voucher_name, product_type, price;

    public String getProvider_id() {
        return provider_id;
    }

    public void setProvider_id(String provider_id) {
        this.provider_id = provider_id;
    }

    public String getVoucher_id() {
        return voucher_id;
    }

    public void setVoucher_id(String voucher_id) {
        this.voucher_id = voucher_id;
    }

    public String getVoucher_name() {
        return voucher_name;
    }

    public void setVoucher_name(String voucher_name) {
        this.voucher_name = voucher_name;
    }

    public String getProduct_type() {
        return product_type;
    }

    public void setProduct_type(String product_type) {
        this.product_type = product_type;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
    
    @Override
    public String getPath() {
        return "/api/voucher/product/add";
    }

    @Override
    public Type getType() {
        return Type.ADD_PRODUK_VOUCHER;
    }

    @Override
    public JSONObject getBodyRequest() {
        JSONObject req =  super.getBodyRequest();
        req.put("provider_id", provider_id);
        req.put("voucher_id", voucher_id);
        req.put("voucher_name", voucher_name);
        req.put("product_type", product_type);
        req.put("price", price);
        
        return req;
    }
}
