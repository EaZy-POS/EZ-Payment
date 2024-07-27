/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.msg.data.modulesyslist;

import com.json.JSONObject;

/**
 *
 * @author Lutfi
 */
public class UpdateModuleSyslist extends AddModuleSyslist{

    private String id;
    
    @Override
    public String getPath() {
        return "/api/module/syslist/update";
    }

    public String getID() {
        return id;
    }

    public void setID(String modulID) {
        this.id = modulID;
    }

    @Override
    public JSONObject getBodyRequest() {
        JSONObject req = super.getBodyRequest();
        req.put("id", id);
        return req;
    }

    @Override
    public Type getType() {
        return Type.UPDATE_MODULE_SYSLIST;
    }

}
