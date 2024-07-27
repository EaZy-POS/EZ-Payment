/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.gateway.message;

import co.id.ez.gateway.resource.CommonHanlder;
import co.id.ez.system.core.config.ConfigService;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author RCS
 */
public abstract class BillerRequest {
    private String comand, modul;
    private final String cid, date, resp;
    private String hcode, trxid, refnum;
    
    public String contructSimulatorResponse(){
        return null;
    }
    
    public BillerRequest(String comand, String modul) {
        this.comand = comand;
        this.modul = modul;

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

    public void setRefnum(String refnum) {
        this.refnum = refnum;
    }

    public String getRefnum() {
        return refnum;
    }
    
    private static String generateTrxID(){
        return UUID.randomUUID().toString().replace("-", "").substring(0, 16).toUpperCase();
    }
    
    private static String getHC(String tCid, String date, String skey) throws NoSuchAlgorithmException{
        String value = toHexString(getSHA(tCid+date+skey));
        return value;
    }
    
    private static byte[] getSHA(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        return md.digest(input.getBytes(StandardCharsets.UTF_8));
    }

    private static String toHexString(byte[] hash) {
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

    public String getComand() {
        return comand;
    }

    public void setComand(String comand) {
        this.comand = comand;
    }

    public String getModul() {
        return modul;
    }

    public void setModul(String modul) {
        this.modul = modul;
    }

    public String getMessageStream() {
        return "command=" + comand + "&modul=" + modul +"&trxid="+trxid
                + "&cid=" + cid + "&dt=" + date 
                + "&resp=" + resp + "&hc=" + hcode;
    }

    public String getRemarks(){
        return getRemarks("Pembayaran");
    }
    
    public String getRemarks(String pPrefix){
        return pPrefix+" "+ getModul()+" "+ getTrxid();
    }
    
    public static void main(String[] args) {
        try {
            System.out.println(getHC("dev6ffacfed-6293-11e6-8325-3nGsysT3m", "20240215", "@4k5e53nGsysT3m"));
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(BillerRequest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
