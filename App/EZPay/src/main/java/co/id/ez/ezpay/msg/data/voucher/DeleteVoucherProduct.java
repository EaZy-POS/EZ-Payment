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
public class DeleteVoucherProduct extends CentralDataRequest{
    
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getPath() {
        return "/api/voucher/product/delete";
    }

    @Override
    public Type getType() {
        return Type.DELETE_PRODUK_VOUCHER;
    }

    @Override
    public JSONObject getBodyRequest() {
        JSONObject req =  super.getBodyRequest();
        req.put("id", id);
        
        return req;
    }
}
