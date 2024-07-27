/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.msg.data.mitra;

import co.id.ez.ezpay.msg.data.CentralDataRequest;
import com.json.JSONObject;

/**
 *
 * @author Lutfi
 */
public class ActivateMitra extends CentralDataRequest{
    
    private String client_id, client_key;

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getClient_key() {
        return client_key;
    }

    public void setClient_key(String client_key) {
        this.client_key = client_key;
    }
    
    @Override
    public String getPath() {
        return "/api/mitra/aktivasi";
    }

    @Override
    public Type getType() {
        return Type.AKTIFASI_MITRA;
    }

    @Override
    public JSONObject getBodyRequest() {
        JSONObject req = super.getBodyRequest();
        req.put("client_id", client_id);
        req.put("secret_key", client_key);
        return req;
    }
    
}
