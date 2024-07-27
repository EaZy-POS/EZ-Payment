/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.msg.data.modulesyslist;

import co.id.ez.ezpay.msg.data.CentralDataRequest;
import com.json.JSONObject;

/**
 *
 * @author Lutfi
 */
public class DeleteModuleSysList extends CentralDataRequest{

    private String id;
    
    @Override
    public String getPath() {
        return "/api/module/syslist/delete";
    }

    public String getID() {
        return id;
    }

    public void setID(String modulID) {
        this.id = modulID;
    }

    @Override
    public Type getType() {
        return Type.DELETE_MODULE_SYSLIST;
    }

    @Override
    public JSONObject getBodyRequest() {
        JSONObject req = super.getBodyRequest();
        req.put("id", id);
        return req;
    }

}
