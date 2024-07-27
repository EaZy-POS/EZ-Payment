/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.msg.data.module;

import com.json.JSONObject;

/**
 *
 * @author Lutfi
 */
public class UpdateModule extends AddModule{
    
    private String id;
    private String status;
    
    @Override
    public String getPath() {
        return "/api/module/update";
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
    public Type getType() {
        return Type.UPDATE_MODULE;
    }

    @Override
    public JSONObject getBodyRequest() {
        JSONObject req = super.getBodyRequest();
        req.put("id", getId());
        req.put("status", getStatus());
        return req;
    }

}
