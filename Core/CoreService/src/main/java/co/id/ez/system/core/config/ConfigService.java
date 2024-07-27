/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.system.core.config;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import java.io.File;
import java.io.FileNotFoundException;

/**
 *
 * @author RCS
 */
public class ConfigService {
    
    protected static Configuration configHolder;

    public static void createInstance(String... dir) {
        configHolder = ConfigurationFactory.loadFromDirectory(dir);
    }

    public static Configuration getInstance() {
        return configHolder;
    }

    public static void reload(File file){
        try {
            Config config = ConfigurationFactory.createConfig(file);
            configHolder.reload(config);
        } catch (FileNotFoundException e) {

        }
    }
    
    public static void customeConfig(String configString){
        try {
            Config config = ConfigFactory.parseString(configString);
            configHolder.reload(config);
        } catch (Exception e) {
            
        }
    }
}
