/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.central.handler;

import co.id.ez.central.resource.CommonHanlder;
import static co.id.ez.central.resource.CommonHanlder.reqMap;
import co.id.ez.system.core.etc.EncryptionService;
import com.json.JSONObject;
import java.util.UUID;

/**
 *
 * @author RCS
 */
public abstract class MessageHandler extends CommonHanlder{
    
    public MessageHandler() {
        this.corelation_id = UUID.randomUUID().toString().replace("-", "");
    }
    
    public void setTraceID(String m_traceid) {
        this.corelation_id = m_traceid;
    }
    
    public abstract String performHandler(JSONObject request);
    
    public String getDasAccount(){
        return getDasAccount(
                reqMap.getClient_id(),
                reqMap.getSecret_key()
        );
    }
    
    public String getDasAccount(String pClientID, String pKey){
        return EncryptionService.encryptor().Base64Encrypt(
                EncryptionService.encryptor()
                        .encrypt(
                                pClientID, 
                                pKey
                        )
        );
    }
}
