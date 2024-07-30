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
 * @author lutfi
 */
public class RequestMapping {

    private final String client_id, module_id, auth, user_id, password, path, date, signature;
    private String product;
    private boolean isDetail;
    private LinkedList<JSONObject> tranmaindata;
    private BigDecimal amount;

    private final String cServiceKey = "0xdef09x70";

    public RequestMapping(
            String client_id,
            String module_id,
            String auth,
            String userid,
            String password,
            String path,
            String date,
            String signature
    ) {
        this.client_id = client_id;
        this.module_id = module_id;
        this.auth = auth;
        this.user_id = userid;
        this.password = password;
        this.path = path;
        this.date = date;
        this.signature = signature;
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

    public String getDate() {
        return date;
    }

    public String getSignature() {
        return signature;
    }

    @Override
    public String toString() {
        return "RequestMapping{" + "mitra_id=" + client_id + ", product_id=" + module_id
                + ", auth=" + auth + ", product=" + getProduct()
                + ", user_id=" + user_id + ", tranmaindata=" + tranmaindata.toString()
                + ", path=" + path + ", amount=" + amount
                + ", date=" + date + ", signature=" + signature + '}';
    }

    public JSONObject getAuthRequest(String pBody) {
        JSONObject pRequest = new JSONObject();
        pRequest.put("path", getPath());
        pRequest.put("client_id", getClient_id());
        pRequest.put("module_id", getModule_id());
        pRequest.put("date", getDate());
        pRequest.put("signature", EncryptionService
                .encryptor().Base64Encrypt(
                        getSignature()
                )
        );
        pRequest.put("body", pBody);
        pRequest.put("user_id", EncryptionService
                .encryptor().Base64Encrypt(
                        EncryptionService.encryptor().Base64Encrypt(
                                EncryptionService.encryptor()
                                        .encrypt(
                                                getUser_id(),
                                                getClient_id())
                        )
                )
        );
        pRequest.put("password",
                EncryptionService.encryptor().Base64Encrypt(
                        EncryptionService.encryptor()
                                .encrypt(
                                        getPassword(),
                                        cServiceKey
                                )
                )
        );
        pRequest.put("product", getProduct());

        return pRequest;
    }

    public JSONObject getJournalRequest(String pJournal, String pRefnum, String pRemark, BigDecimal pAmount) {
        JSONObject pRequest = new JSONObject();
        pRequest.put("das_id", EncryptionService.encryptor().Base64Encrypt(
                EncryptionService.encryptor()
                        .encrypt(
                                getClient_id(),
                                cServiceKey
                        )
        )
        );

        pRequest.put("jurnal", pJournal);
        pRequest.put("refnum", pRefnum);
        pRequest.put("remarks", pRemark);
        pRequest.put("amount", pAmount.doubleValue());

        return pRequest;
    }
}
