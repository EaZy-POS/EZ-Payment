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
public class DeleteMitra extends CentralDataRequest{
    
    private String client_id;

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }
    
    @Override
    public String getPath() {
        return "/api/mitra/delete";
    }

    @Override
    public Type getType() {
        return Type.DELETE_MITRA;
    }

    @Override
    public JSONObject getBodyRequest() {
        JSONObject req = super.getBodyRequest();
        req.put("client_id", client_id);
        return req;
    }
    
}
