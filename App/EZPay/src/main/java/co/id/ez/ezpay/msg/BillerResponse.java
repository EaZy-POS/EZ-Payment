/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.msg;

import co.id.ez.system.core.rc.RC;
import com.json.JSONObject;

/**
 *
 * @author RCS
 */
public class BillerResponse {
    private final JSONObject payload;
    private final RC responseCode;
    private final String trxid, refnum;

    public BillerResponse(JSONObject payload) {
        this.payload = payload;
        this.refnum = this.payload.has("refnum") ? this.payload.getString("refnum") : "";
        this.trxid = this.payload.has("trxid") ? this.payload.getString("trxid") : "";
        this.responseCode = RC.parseResponseCodeString(this.payload.getString("rc").substring(3));
    }

    public JSONObject getPayload() {
        return payload;
    }

    public RC getResponseCode() {
        return responseCode;
    }

    public String getTrxid() {
        return trxid;
    }

    public String getRefnum() {
        return refnum;
    }
    
    
}
