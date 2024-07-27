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
public class AddVoucherProvider extends CentralDataRequest{
    
    private String provider_name;
    
    @Override
    public String getPath() {
        return "/api/voucher/provider/add";
    }

    @Override
    public Type getType() {
        return Type.ADD_PROVIDER_VOUCHER;
    }

    public String getProvider_name() {
        return provider_name;
    }

    public void setProvider_name(String provider_name) {
        this.provider_name = provider_name;
    }

    @Override
    public JSONObject getBodyRequest() {
        JSONObject req = super.getBodyRequest();
        req.put("provider_name", provider_name);
        return req;
    }
}
