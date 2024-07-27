/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.msg.data.module;

import co.id.ez.ezpay.msg.data.CentralDataRequest;
import com.json.JSONObject;

/**
 *
 * @author Lutfi
 */
public class AddModule extends CentralDataRequest{

    private String modulID, modulName;
    private boolean isNeedSign;
    
    @Override
    public String getPath() {
        return "/api/module/add";
    }

    public String getModulID() {
        return modulID;
    }

    public void setModulID(String modulID) {
        this.modulID = modulID;
    }

    public String getModulName() {
        return modulName;
    }

    public void setModulName(String modulName) {
        this.modulName = modulName;
    }

    public boolean isNeedSign() {
        return isNeedSign;
    }

    public void setNeedSign(boolean isNeedSign) {
        this.isNeedSign = isNeedSign;
    }

    @Override
    public Type getType() {
        return Type.ADD_MODULE;
    }

    @Override
    public JSONObject getBodyRequest() {
        JSONObject req = super.getBodyRequest();
        req.put("module_id", modulID);
        req.put("module_name", modulName);
        req.put("is_need_sign", isNeedSign);
        return req;
    }

}
