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
public class AddModuleSyslist extends CentralDataRequest{

    private String modulID, pathSyslist, status;
    
    @Override
    public String getPath() {
        return "/api/module/syslist/add";
    }

    public String getModulID() {
        return modulID;
    }

    public void setModulID(String modulID) {
        this.modulID = modulID;
    }

    public String getPathSyslist() {
        return pathSyslist;
    }

    public void setPathSyslist(String pathSyslist) {
        this.pathSyslist = pathSyslist;
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
        req.put("module_id", modulID);
        req.put("path", pathSyslist);
        req.put("status", status);
        return req;
    }

    @Override
    public Type getType() {
        return Type.ADD_MODULE_SYSLIST;
    }

}
