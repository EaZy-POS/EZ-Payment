/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.msg.profile;

import co.id.ez.ezpay.enums.RequestType;
import co.id.ez.ezpay.msg.BillerMessage;
import com.json.JSONObject;

/**
 *
 * @author Lutfi
 */
public abstract class ProfileDataRequest extends BillerMessage{
    
    
    public ProfileDataRequest() {
        super(RequestType.PROFILE, "");
    }

    public abstract Type getType();

    @Override
    public JSONObject getBodyRequest(){
        JSONObject req = new JSONObject();
        return req;
    }

    @Override
    public String getCommand() {
        return "";
    }

    public enum Type{
        GET_PROFILE,
    }
}
