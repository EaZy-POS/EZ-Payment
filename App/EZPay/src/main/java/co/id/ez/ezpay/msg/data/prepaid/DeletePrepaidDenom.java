/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.msg.data.prepaid;

import co.id.ez.ezpay.msg.data.CentralDataRequest;
import com.json.JSONObject;

/**
 *
 * @author Lutfi
 */
public class DeletePrepaidDenom extends CentralDataRequest{
    
    private String id;
    
    @Override
    public String getPath() {
        return "/api/prepaid/denom/delete";
    }

    @Override
    public Type getType() {
        return Type.DELETE_DENOM_PREPAID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public JSONObject getBodyRequest() {
        JSONObject req = super.getBodyRequest();
        req.put("id", id);
        
        return req;
    }
    
    
}
