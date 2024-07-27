/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.gateway.resource.handler.mpmodule;

import co.id.ez.gateway.message.BillerRequest;
import com.json.JSONObject;

/**
 *
 * @author Lutfi
 */
public abstract class MPModule {
    private final String moduleName;

    public MPModule(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getModuleName() {
        return moduleName;
    }
    
    public abstract BillerRequest constructBillerRequest(JSONObject request);
    
    public JSONObject constructSuccessfullResponse(JSONObject pRequest, String pResponse){
        JSONObject tResp = new JSONObject(pResponse);
        pRequest.keySet().stream().map(keys -> {
            return keys;
        }).filter(keys -> (!tResp.has(keys.toString()))).forEachOrdered(keys -> {
            tResp.put(keys.toString(), pRequest.get(keys.toString()));
        });
        
        return tResp;
    }
}
