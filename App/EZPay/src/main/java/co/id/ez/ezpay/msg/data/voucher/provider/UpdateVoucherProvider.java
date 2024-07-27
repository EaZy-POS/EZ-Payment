/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.msg.data.voucher.provider;

import com.json.JSONObject;

/**
 *
 * @author Lutfi
 */
public class UpdateVoucherProvider extends AddVoucherProvider{
    
    private String provider_id, status;

    public String getProvider_id() {
        return provider_id;
    }

    public void setProvider_id(String provider_id) {
        this.provider_id = provider_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    @Override
    public String getPath() {
        return "/api/voucher/provider/update";
    }

    @Override
    public Type getType() {
        return Type.UPDATE_PROVIDER_VOUCHER;
    }
    
    @Override
    public JSONObject getBodyRequest() {
        JSONObject req = super.getBodyRequest();
        req.put("provider_id", getProvider_id());
        req.put("status", status);
        return req;
    }
}
