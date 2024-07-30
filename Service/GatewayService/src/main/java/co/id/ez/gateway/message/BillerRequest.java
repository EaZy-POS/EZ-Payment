/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.gateway.message;

import co.id.ez.gateway.resource.CommonHanlder;
import co.id.ez.gateway.util.IDGenerator;
import co.id.ez.system.core.config.ConfigService;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Lutfi
 */
public abstract class BillerRequest {

    private final String cid, date, resp;
    private String hcode, trxid;

    public String contructSimulatorResponse() {
        return null;
    }
    
    public abstract String getModule();
    public abstract String getModuleCode();
    public abstract String getComand();
    
    public BillerRequest() {
        cid = ConfigService.getInstance().getString(CommonHanlder.cMessageKey + CommonHanlder.cCIDKey);
        String tSkey = ConfigService.getInstance().getString(CommonHanlder.cMessageKey + CommonHanlder.cSkeyKey);
        resp = ConfigService.getInstance().getString(CommonHanlder.cMessageKey + CommonHanlder.cResponseKey);
        date = new SimpleDateFormat("yyyyMMdd").format(new Date());
        trxid = generateTrxID();
        
        try {
            hcode = getHC(cid, date, tSkey);
        } catch (NoSuchAlgorithmException ex) {
            throw new IllegalArgumentException("Error Hashing message. ", ex);
        }
    }

    private String generateTrxID() {
        return IDGenerator.get(getModuleCode());
    }

    private String getHC(String tCid, String date, String skey) throws NoSuchAlgorithmException {
        String value = toHexString(getSHA(tCid + date + skey));
        return value;
    }

    private byte[] getSHA(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        return md.digest(input.getBytes(StandardCharsets.UTF_8));
    }

    private String toHexString(byte[] hash) {
        BigInteger number = new BigInteger(1, hash);
        StringBuilder hexString = new StringBuilder(number.toString(16));
        while (hexString.length() < 64) {
            hexString.insert(0, '0');
        }

        return hexString.toString();
    }

    public void setTrxid(String trxid) {
        this.trxid = trxid;
    }

    public String getTrxid() {
        return trxid;
    }

    public String getCid() {
        return cid;
    }

    public String getDate() {
        return date;
    }

    public String getResp() {
        return resp;
    }

    public String getHcode() {
        return hcode;
    }

    public void setHcode(String hcode) {
        this.hcode = hcode;
    }

    public String getBasicMessageStream() {
        return "command=" + getComand() + "&modul=" + getModule() + "&trxid=" + trxid
                + "&cid=" + cid + "&dt=" + date
                + "&resp=" + resp + "&hc=" + hcode;
    }

    public String getMessageStream() {
        return getBasicMessageStream();
    }

    public String getRemarks() {
        return getRemarks("Pembayaran");
    }

    public String getRemarks(String pPrefix) {
        return pPrefix + " " + getModule()+ " " + getTrxid();
    }
    
}
