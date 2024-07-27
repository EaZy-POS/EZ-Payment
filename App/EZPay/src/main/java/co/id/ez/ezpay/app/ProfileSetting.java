/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.app;

import co.id.ez.system.core.config.ConfigService;
import co.id.ez.system.core.log.LogService;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

/**
 *
 * @author Lutfi
 */
public class ProfileSetting {
    
    private final HashMap<String, Object> setting = new HashMap<>();
    private File configFile;

    public ProfileSetting() {
        try {
            
            File envFolder = new File("env");
            
            if(!envFolder.exists()){
                envFolder.mkdir();
            }
            configFile = new File("env/profile.cfg");
            if(!configFile.exists()){
                configFile.createNewFile();
            }
        } catch (IOException e) {
            LogService.getInstance(configFile)
                    .config().withCause(e)
                    .log("[IOException | NumberFormatException] Error load setting struk", true);
        }
    }

    public void read() {
        readProfileSettings();
    }
    
    public void put(String setup, Object value){
        this.setting.put(setup, value);
    }
    
    public Object get(String setup){
        return ConfigService.getInstance().getObject("app."+ setup, null);
    }
    
    public String getString(String setup){
        return ConfigService.getInstance().getString("app."+ setup, null);
    }
    
    public boolean getBoolean(String setup){
        return ConfigService.getInstance().getBoolean("app."+ setup, false);
    }
    
    public int getInteger(String setup){
        return ConfigService.getInstance().getInt("app."+ setup, -1);
    }
    
    public void save(){
        try {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(configFile))) {
                for (String settingkey : setting.keySet()) {
                    writer.write(
                            settingkey.concat("= ")
                                    .concat(setting.get(settingkey).toString()
                                    )
                    );
                    writer.newLine();
                }
                
                writer.flush();
                writer.close();
            }
            readProfileSettings();
        } catch (IOException e) {
            LogService.getInstance(this)
                    .config().withCause(e)
                    .log("[IOException] Error save setting struk", true);
        }
    }
    
    private void readProfileSettings(){
        ConfigService.reload(configFile);
    }
    
    private static class Holder{
        public static final ProfileSetting instance = new ProfileSetting();
    }
    
    public static ProfileSetting instance(){
        return Holder.instance;
    }
}
