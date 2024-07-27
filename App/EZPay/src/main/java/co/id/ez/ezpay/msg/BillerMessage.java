/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.msg;

import co.id.ez.ezpay.enums.RequestType;
import com.json.JSONObject;

/**
 *
 * @author lutfi
 */
public abstract class BillerMessage {
    
    private final RequestType reqtype;
    private final String moduleid;

    public abstract JSONObject getBodyRequest();
    public abstract String getPath();
    public abstract String getCommand();
    
    public BillerMessage(RequestType reqtype, String module) {
        this.reqtype = reqtype;
        this.moduleid = module;
    }

    public RequestType getReqtype() {
        return reqtype;
    }
    
    public String getMessageStream(){
        return getBodyRequest().toString();
    }

    public String getModuleid() {
        return moduleid;
    }
    
    public boolean isNeedHeader(){
        return true;
    }
    
}
