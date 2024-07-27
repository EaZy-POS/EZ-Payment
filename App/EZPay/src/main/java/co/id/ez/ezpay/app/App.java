/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.app;

import co.id.ez.ezpay.controller.AppController;
import co.id.ez.ezpay.enums.Headers;
import co.id.ez.system.core.etc.EncryptionService;
import co.id.ez.system.core.ex.ServiceException;
import co.id.ez.system.core.rc.RC;
import com.json.JSONObject;
import java.sql.SQLException;
import java.util.HashMap;

/**
 *
 * @author RCS
 */
public class App {
    
    private final String down_mac;
    private final String acc_pocky;
    private final String enu_key;
    private final String enp_val;
    private String guri_base;
    private String curi_base;
    private final String env_key = "enmcH556poKyPwxx";
    private final String svc_key = "0xdef09x70";
    private final HashMap<Headers, String> environment;
    private final AppController controller = new AppController();
    private JSONObject envi;

    public App() {
        readEnvironment();
        this.down_mac = envi == null ? null : envi.getString("down_mac");
        this.acc_pocky = envi == null ? null :  envi.getString("acc_pocky");
        this.enu_key = envi == null ? null : envi.getString("enu_key");
        this.enp_val = envi == null ? null : envi.getString("enp_val");
        this.guri_base = envi == null ? null : envi.getString("guri_base");
        this.curi_base = envi == null ? null : envi.getString("curi_base");
        environment = new HashMap<>();
    }

    public void setGuri_base(String guri_base) {
        this.guri_base = guri_base;
    }

    public void setCuri_base(String curi_base) {
        this.curi_base = curi_base;
    }

    public boolean isKnownAplication(){
        return envi != null;
    }
    
    private void readEnvironment(){
        try {
            envi = controller.getAppEnvironment();
        } catch (SQLException ex) {
            throw new ServiceException(RC.ERROR_OTHER, "Failed read Aplication environment", ex);
        }
    }

    public String getDown_mac() {
        String result = EncryptionService
                .encryptor().decrypt(
                        EncryptionService
                                .encryptor()
                                .Base64Decrypt(down_mac),
                         env_key);
        return result;
    }

    public String getAcc_pocky() {
        String result = EncryptionService
                .encryptor().decrypt(
                        EncryptionService
                                .encryptor()
                                .Base64Decrypt(acc_pocky),
                         env_key + getDown_mac());
        return result;
    }

    public String getEnu_key() {
        return EncryptionService
                .encryptor().decrypt(
                        EncryptionService
                                .encryptor()
                                .Base64Decrypt(enu_key),
                         env_key);
    }

    public String getEnp_val() {
        return EncryptionService
                .encryptor().decrypt(
                        EncryptionService
                                .encryptor()
                                .Base64Decrypt(enp_val),
                         env_key + getEnu_key());
    }

    public String getSvc_key() {
        return svc_key;
    }
    
    private String getAcc(){
        String acc = EncryptionService.encryptor()
                .Base64Encrypt(getEnu_key().concat(":").concat(getEnp_val()));
        return "Basic ".concat(acc);
    }
    
    public HashMap<Headers, String> get(){
        environment.put(Headers.DOWN_MAC, envi == null ? null : getDown_mac());
        environment.put(Headers.AUTH, envi == null ? null : getAcc());
        return environment;
    }
    
    public String getDEU(){
        return getEnu_key();
    }

    public String getEnv_key() {
        return env_key;
    }
    
    private String getGuri(){
        return EncryptionService
                .encryptor().decrypt(
                        EncryptionService
                                .encryptor()
                                .Base64Decrypt(guri_base),
                         env_key);
    }

    private String getCuri() {
        return EncryptionService
                .encryptor().decrypt(
                        EncryptionService
                                .encryptor()
                                .Base64Decrypt(curi_base),
                         env_key);
    }

    public String getGatewayUrl(){
        return getGuri();
    }
    
    public String getCentralUrl(){
        return getCuri();
    }
    
    public boolean isSuperAdmin(String pUser, String pPassword){
        return pUser.equals(getEnu_key()) && pPassword.equals(getEnp_val());
    }
    
    @Override 
    public String toString() {
        return "App{" + "down_mac=" + down_mac 
                + ", acc_pocky=" + acc_pocky 
                + ", enu_key=" + enu_key 
                + ", enp_val=" + enp_val 
                + ", env_key=" + env_key + '}';
    }
    
    public static class Builder{
        private static App instance;
        
        public static void buildApp(){
            instance = new App();
        }
    }
    
    public static App environment(){
        return Builder.instance;
    }
    
}
