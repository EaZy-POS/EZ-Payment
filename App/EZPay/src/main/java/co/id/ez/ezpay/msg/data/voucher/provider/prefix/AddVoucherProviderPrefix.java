/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.msg.data.voucher.provider.prefix;

import co.id.ez.ezpay.msg.data.CentralDataRequest;
import com.json.JSONObject;

/**
 *
 * @author Lutfi
 */
public class AddVoucherProviderPrefix extends CentralDataRequest{
    
    String provider_id, prefix;

    public String getProvider_id() {
        return provider_id;
    }

    public void setProvider_id(String provider_id) {
        this.provider_id = provider_id;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
    
    @Override
    public String getPath() {
        return "/api/voucher/provider/prefix/add";
    }

    @Override
    public Type getType() {
        return Type.ADD_PREFIX_PROVIDER_VOUCHER;
    }

    @Override
    public JSONObject getBodyRequest() {
        JSONObject req =  super.getBodyRequest();
        req.put("provider_id", provider_id);
        req.put("prefix", prefix);
        
        return req;
    }
}
