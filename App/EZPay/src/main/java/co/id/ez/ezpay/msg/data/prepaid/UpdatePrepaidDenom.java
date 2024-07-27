/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.msg.data.prepaid;

import com.json.JSONObject;
import java.math.BigDecimal;

/**
 *
 * @author Lutfi
 */
public class UpdatePrepaidDenom extends AddPrepaidDenom{
    
    private String id, status;
    
    @Override
    public String getPath() {
        return "/api/prepaid/denom/update";
    }

    @Override
    public Type getType() {
        return Type.UPDATE_DENOM_PREPAID;
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
    
    
}
