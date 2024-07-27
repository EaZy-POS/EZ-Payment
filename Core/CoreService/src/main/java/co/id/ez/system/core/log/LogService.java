/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.system.core.log;

import co.id.ez.system.core.log.elm.Logger;
import java.util.HashMap;

/**
 *
 * @author RCS
 */
public class LogService {
    private static final HashMap<String, Logger> logMaps = new HashMap<>();
    
    public static Logger getInstance(Object pObjectLogging){
        Class clazz = pObjectLogging.getClass();
        if(!logMaps.containsKey(clazz.getName())){
            Logger log = Logger.forClass(clazz);
            logMaps.put(clazz.getName(), log);
        }
        
        return logMaps.get(clazz.getName());
    }
}
