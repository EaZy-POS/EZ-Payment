/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.central.util;

import co.id.ez.system.core.etc.EncryptionService;
import com.json.JSONObject;
/**
 *
 * @author Lutfi
 */
public class RequestMapping{
    private final String client_id, module_id, secret_key, auth, user_id, password, path;

    public RequestMapping(
            String client_id, 
            String module_id, 
            String secret_key, 
            String auth, 
            String userid, 
            String password,
            String path
    ) {
        this.client_id = client_id;
        this.module_id = module_id;
        this.secret_key = secret_key;
        this.auth = auth;
        this.user_id = userid;
        this.password = password;
        this.path = path;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getClient_id() {
        return client_id;
    }

    public String getModule_id() {
        return module_id;
    }

    public String getSecret_key() {
        return secret_key;
    }

    public String getAuth() {
        return auth;
    }

    public String getPassword() {
        return password;
    }

    public String getPath() {
        return path;
    }

    @Override
    public String toString() {
        return "RequestMapping{" + "mitra_id=" + client_id + ", product_id=" + module_id 
                + ", mitra_key=" + secret_key + ", auth=" + auth  + ", path=" + path
                + ", user_id=" + user_id + '}';
    }

    public JSONObject getAuthRequest(){
        JSONObject pRequest = new JSONObject();
        pRequest.put("path", getPath());
        pRequest.put("client_id", getClient_id());
        pRequest.put("secret_key", getSecret_key());
        pRequest.put("module_id", getModule_id());
        pRequest.put("user_id", EncryptionService.encryptor().Base64Encrypt(
                EncryptionService.encryptor()
                        .encrypt(getUser_id(), getSecret_key())
        )
        );
        pRequest.put("password", EncryptionService.encryptor().Base64Encrypt(
                EncryptionService.encryptor()
                        .encrypt(getPassword(), getSecret_key())
        )
        );
        
        return pRequest;
    }
}
