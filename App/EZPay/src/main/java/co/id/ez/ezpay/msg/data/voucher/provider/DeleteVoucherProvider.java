/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.msg.data.voucher.provider;

import co.id.ez.ezpay.msg.data.CentralDataRequest;
import com.json.JSONObject;

/**
 *
 * @author Lutfi
 */
public class DeleteVoucherProvider extends CentralDataRequest{
    
    private String provider_id;

    public String getProvider_id() {
        return provider_id;
    }

    public void setProvider_id(String provider_id) {
        this.provider_id = provider_id;
    }
    
    @Override
    public String getPath() {
        return "/api/voucher/provider/delete";
    }

    @Override
    public Type getType() {
        return Type.UPDATE_PROVIDER_VOUCHER;
    }
    
    @Override
    public JSONObject getBodyRequest() {
        JSONObject req = super.getBodyRequest();
        req.put("provider_id", getProvider_id());
        return req;
    }
}
