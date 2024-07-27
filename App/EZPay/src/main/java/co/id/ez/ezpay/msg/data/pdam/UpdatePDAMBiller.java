/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.msg.data.pdam;

import com.json.JSONObject;

/**
 *
 * @author Lutfi
 */
public class UpdatePDAMBiller extends AddPDAMBiller{
    
    private String id, status;
    
    @Override
    public String getPath() {
        return "/api/pdam/biller/update";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public JSONObject getBodyRequest() {
        JSONObject req = super.getBodyRequest(); 
        req.put("id", id);
        req.put("status", status);
        return req;
    }
    
    @Override
    public Type getType() {
        return Type.UPDATE_BILLER_PDAM;
    }
}
