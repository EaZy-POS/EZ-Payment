/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.msg.data.voucher.provider.prefix;

import com.json.JSONObject;

/**
 *
 * @author Lutfi
 */
public class UpdateVoucherProviderPrefix extends AddVoucherProviderPrefix{
    
    String id, status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    @Override
    public String getPath() {
        return "/api/voucher/provider/prefix/update";
    }

    @Override
    public Type getType() {
        return Type.UPDATE_PREFIX_PROVIDER_VOUCHER;
    }

    @Override
    public JSONObject getBodyRequest() {
        JSONObject req =  super.getBodyRequest();
        req.put("status", status);
        req.put("id", id);
        
        return req;
    }
}
