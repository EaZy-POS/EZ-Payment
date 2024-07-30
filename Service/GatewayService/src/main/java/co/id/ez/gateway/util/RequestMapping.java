/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.gateway.util;

import co.id.ez.system.core.etc.EncryptionService;
import com.json.JSONObject;
import java.math.BigDecimal;
import java.util.LinkedList;

/**
 *
 * @author RCS
 */
public class RequestMapping{
    private final String client_id, module_id, secret_key, auth, user_id, password, path;
    private String product;
    private boolean isDetail;
    private LinkedList<JSONObject> tranmaindata;
    private BigDecimal amount;

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

    public LinkedList<JSONObject> getTranmaindata() {
        return tranmaindata;
    }

    public void setTranmaindata(LinkedList<JSONObject> tranmaindata, BigDecimal amount) {
        this.tranmaindata = tranmaindata;
        setAmount(amount);
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public boolean isDetail() {
        return isDetail;
    }

    public void setDetail(boolean isDetail) {
        this.isDetail = isDetail;
    }
    
    public String getPath() {
        return path;
    }

    @Override
    public String toString() {
        return "RequestMapping{" + "mitra_id=" + client_id + ", product_id=" + module_id 
                + ", mitra_key=" + secret_key + ", auth=" + auth + ", product=" + product 
                + ", user_id=" + user_id + ", tranmaindata=" + tranmaindata.toString() 
                + ", path=" + path + ", amount=" + amount + '}';
    }

    public JSONObject getAuthRequest(){
        JSONObject pRequest = new JSONObject();
        pRequest.put("path", getPath());
        pRequest.put("client_id", getClient_id());
        pRequest.put("secret_key", getSecret_key());
        pRequest.put("module_id", getModule_id());
        pRequest.put("product", getProduct());
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
    
    public JSONObject getJournalRequest(String pJournal, String pRefnum, String pRemark, BigDecimal pAmount){
        JSONObject pRequest = new JSONObject();
        pRequest.put("das_id", EncryptionService.encryptor().Base64Encrypt(
                EncryptionService.encryptor()
                        .encrypt(getClient_id(), getSecret_key())
        )
        );
        
        pRequest.put("jurnal", pJournal);
        pRequest.put("refnum", pRefnum);
        pRequest.put("remarks", pRemark);
        pRequest.put("amount", pAmount.doubleValue());
        
        return pRequest;
    }
}
