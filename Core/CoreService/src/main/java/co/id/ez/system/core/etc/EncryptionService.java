/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.system.core.etc;

import co.id.ez.system.core.ex.ServiceException;
import co.id.ez.system.core.rc.RC;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author Lutfi
 */
public class EncryptionService {
    private static SecretKeySpec secretKey;
    private static byte[] key;

    private void setKey(String myKey) throws UnsupportedEncodingException, NoSuchAlgorithmException{
        MessageDigest sha = MessageDigest.getInstance("SHA-1");
        key = myKey.getBytes("UTF-8");
        key = sha.digest(key);
        key = Arrays.copyOf(key, 16); 
        secretKey = new SecretKeySpec(key, "AES");
    }
 
    public String encrypt(String strToEncrypt, String secret){
        try {
            setKey(secret);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException 
                | NoSuchPaddingException | InvalidKeyException 
                | IllegalBlockSizeException | BadPaddingException ex) {
            throw new ServiceException(RC.ERROR_INVALID_HASHCODE, "Failed encrypt data", ex);
        }
    }
    
    public String encrypt(String secret, String... strToEncrypt){
        try {
            setKey(secret);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            
            String tTartget = "";
            
            for (String key : strToEncrypt) {
                tTartget = tTartget.concat(key).concat(":");
            }
            
            return Base64.getEncoder().encodeToString(cipher.doFinal(tTartget.getBytes("UTF-8")));
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException 
                | NoSuchPaddingException | InvalidKeyException 
                | IllegalBlockSizeException | BadPaddingException ex) {
            throw new ServiceException(RC.ERROR_INVALID_HASHCODE, "Failed encrypt data", ex);
        }
    }
    
    public boolean validateKey(String validateValue, String key, String... pData){
        return validateValue.equals(encrypt(key, pData));
    }
 
    public String decrypt(String strToDecrypt, String secret){
        try {
            setKey(secret);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException 
                | NoSuchPaddingException | InvalidKeyException 
                | IllegalBlockSizeException | BadPaddingException ex) {
            throw new ServiceException(RC.ERROR_INVALID_HASHCODE, "Failed decrypt data", ex);
        }
    }
    
    private byte[] getByte(String stringToHashing) throws NoSuchAlgorithmException{
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        return md.digest(stringToHashing.getBytes(StandardCharsets.UTF_8));
    }

    public String hashSHA256(String stringToHas) {
        try {
            BigInteger number = new BigInteger(1, getByte(stringToHas));
            StringBuilder hexString = new StringBuilder(number.toString(16));
            
            while (hexString.length() < 64) {
                hexString.insert(0, '0');
            }
            
            return hexString.toString();
        } catch (NoSuchAlgorithmException ex) {
            throw new ServiceException(RC.ERROR_INVALID_HASHCODE, "Failed hash data", ex);
        }
    }
    
    private static class instance{
        private static final EncryptionService inst = new EncryptionService();
    }
    
    public static EncryptionService encryptor(){
        return instance.inst;
    }
    
    public String Base64Decrypt(String pParam) {
        byte[] tByte = Base64.getDecoder().decode(pParam.getBytes());
        String value = new String(tByte, StandardCharsets.UTF_8);
        return value;
    }
    
    public String Base64Encrypt(String pParam){
        String value = Base64.getEncoder().encodeToString(pParam.getBytes());
        return value;
    }
}
