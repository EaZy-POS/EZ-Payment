/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.central.util;

import co.id.ez.system.core.etc.EncryptionService;
import com.json.JSONObject;
import java.util.UUID;

/**
 *
 * @author Lutfi
 */
public class MitraAccess {
    private final String cServiceKey = "0xdef09x70";
    
    public String generateNewMitra(String userName, String password){
        String tClientID = generateClientID();
        String tKey = generateClientKey();
        String tEncUser = getEncryptedString(userName, tKey);
        String tEncPass = getEncryptedPassword(tKey, password, tClientID, tEncUser);
        
        JSONObject result = new JSONObject();
        JSONObject user = new JSONObject()
                .put("username", userName)
                .put("password", password)
                .put("client_id", tClientID)
                .put("encrypted_client", getEncryptedString(tClientID, cServiceKey))
                .put("secret_key", tKey)
                .put("encrypted_key", getEncryptedString(tKey, cServiceKey));
        
        JSONObject access = new JSONObject()
                .put("encrypted_user", tEncUser)
                .put("encrypted_pass", tEncPass);
        result.put("user", user)
                .put("access", access);
        
        return result.toString();
    }
    
    private String generateClientID(){
        String[] tUids = UUID.randomUUID().toString().split("-");
        return tUids[0] + tUids[2];
    }
    
    private String generateClientKey(){
        String[] tKeys = UUID.randomUUID().toString().split("-");
        String[] tKeys0 = UUID.randomUUID().toString().split("-");
        return tKeys[1] + "-" + tKeys0[2] + "-" + tKeys[0] + "-" + tKeys0[3];
    }
    
    private String getEncryptedString(String target, String key){
        return EncryptionService.encryptor().Base64Encrypt(
                EncryptionService.encryptor().encrypt(target, key)
        );
    }
    
    private String getEncryptedPassword(String key, String password, String clienid, String userid){
        String tEncryptedPassword = EncryptionService.encryptor().encrypt(password, key);
        tEncryptedPassword = EncryptionService.encryptor().encrypt(
                key, clienid, userid, tEncryptedPassword);
        return tEncryptedPassword;
    }
}
