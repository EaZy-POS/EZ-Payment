package co.id.ez.system.core.config;

import co.id.ez.system.core.log.LogService;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigBeanFactory;
import com.typesafe.config.ConfigValue;

import java.time.Duration;
import java.time.Period;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Configuration {

    private Config baseConfig;
    private final Map<String, Configuration> cachedConfig;
    private final Map<String, List<Configuration>> cachedConfigList;

    public Configuration(Config baseConfig) {
        this.baseConfig = baseConfig;
        this.cachedConfig = new HashMap<>();
        this.cachedConfigList = new HashMap<>();
    }

    public void reload(Config targetConfig) {
        if (targetConfig != null) {
            baseConfig = targetConfig.withFallback(baseConfig);
        }
    }
    
    public String render() {
        return baseConfig.root().render();
    }

    public boolean hasPath(String path) {
        return baseConfig.hasPath(path);
    }

    public boolean getBoolean(String path) {
        return baseConfig.getBoolean(path);
    }
    
    public Object getObject(String path) {
        return baseConfig.getObject(path);
    }
    
    public Object getObject(String path, Object defaultValue) {
        try{
            if(hasPath(path)){
                return getObject(path);
            }else{
                LogService.getInstance(this).config().log("No configuration setting found for key '"+path+"', retuning default value "+ defaultValue);
            }
        }catch(Exception e){
            LogService.getInstance(this).config().log(e.getMessage() +", retuning default value "+ defaultValue);
        }
        
        return defaultValue;
    }
    
    public boolean getBoolean(String path, boolean defaultValue) {
        try{
            if(hasPath(path)){
                return getBoolean(path);
            }else{
                LogService.getInstance(this).config().log("No configuration setting found for key '"+path+"', retuning default value "+ defaultValue);
            }
        }catch(Exception e){
            LogService.getInstance(this).config().log(e.getMessage() +", retuning default value "+ defaultValue);
        }
        
        return defaultValue;
    }

    public Number getNumber(String path) {
        return baseConfig.getNumber(path);
    }
    
    public Number getNumber(String path, Number defaultValue) {
        try{
            if(hasPath(path)){
                return getNumber(path);
            }else{
                LogService.getInstance(this).config().log("No configuration setting found for key '"+path+"', retuning default value "+ defaultValue);
            }
        }catch(Exception e){
            LogService.getInstance(this).config().log(e.getMessage() +", retuning default value "+ defaultValue);
        }
        
        return defaultValue;
    }

    public int getInt(String path) {
        return baseConfig.getInt(path);
    }

    public long getLong(String path) {
        return baseConfig.getLong(path);
    }

    public double getDouble(String path) {
        return baseConfig.getDouble(path);
    }

    public String getString(String path) {
        return baseConfig.getString(path);
    }
    
    public int getInt(String path, int defaultValue) {
        try{
            if(hasPath(path)){
                return getInt(path);
            }else{
                LogService.getInstance(this).config().log("No configuration setting found for key '"+path+"', retuning default value "+ defaultValue);
            }
        }catch(Exception e){
            LogService.getInstance(this).config().log(e.getMessage() +", retuning default value "+ defaultValue);
        }
        
        return defaultValue;
    }

    public long getLong(String path, long defaultValue) {
        try{
            if(hasPath(path)){
                return getLong(path);
            }else{
                LogService.getInstance(this).config().log("No configuration setting found for key '"+path+"', retuning default value "+ defaultValue);
            }
        }catch(Exception e){
            LogService.getInstance(this).config().log(e.getMessage() +", retuning default value "+ defaultValue);
        }
        
        return defaultValue;
    }

    public double getDouble(String path, double  defaultValue) {
        try{
            if(hasPath(path)){
                return getDouble(path);
            }else{
                LogService.getInstance(this).config().log("No configuration setting found for key '"+path+"', retuning default value "+ defaultValue);
            }
        }catch(Exception e){
            LogService.getInstance(this).config().log(e.getMessage() +", retuning default value "+ defaultValue);
        }
        
        return defaultValue;
    }

    public String getString(String path, String defaultValue) {
        try{
            if(hasPath(path)){
                return getString(path);
            }else{
                LogService.getInstance(this).config().log("No configuration setting found for key '"+path+"', retuning default value "+ defaultValue);
            }
        }catch(Exception e){
            LogService.getInstance(this).config().log(e.getMessage() +", retuning default value "+ defaultValue);
        }
        
        return defaultValue;
    }

    public Configuration getConfig(String path) {
        if (!cachedConfig.containsKey(path)) {
            cachedConfig.put(path, new Configuration(baseConfig.getConfig(path)));
        }
        return cachedConfig.get(path);
    }

    public long getSizeInBytes(String path) {
        return baseConfig.getBytes(path);
    }

    public long getDuration(String path, TimeUnit timeUnit) {
        return baseConfig.getDuration(path, timeUnit);
    }

    public Duration getDuration(String path) {
        return baseConfig.getDuration(path);
    }

    public Period getPeriod(String path) {
        return baseConfig.getPeriod(path);
    }

    public <T> T getBean(String path, Class<T> beanClass) {
        return ConfigBeanFactory.create(baseConfig.getConfig(path), beanClass);
    }

    public List<Boolean> getBooleanList(String path) {
        return baseConfig.getBooleanList(path);
    }

    public List<Number> getNumberList(String path) {
        return baseConfig.getNumberList(path);
    }

    public List<Integer> getIntList(String path) {
        return baseConfig.getIntList(path);
    }

    public List<Long> getLongList(String path) {
        return baseConfig.getLongList(path);
    }

    public List<Double> getDoubleList(String path) {
        return baseConfig.getDoubleList(path);
    }

    public List<String> getStringList(String path) {
        return baseConfig.getStringList(path);
    }
    
    public List<Boolean> getBooleanList(String path, List<Boolean> defaultValue) {
        try{
            if(hasPath(path)){
                return getBooleanList(path);
            }else{
                LogService.getInstance(this).config().log("No configuration setting found for key '"+path+"', retuning default value "+ defaultValue);
            }
        }catch(Exception e){
            LogService.getInstance(this).config().log(e.getMessage() +", retuning default value "+ defaultValue);
        }
        
        return defaultValue;
    }

    public List<Number> getNumberList(String path, List<Number> defaultValue) {
        try{
            if(hasPath(path)){
                return getNumberList(path);
            }else{
                LogService.getInstance(this).config().log("No configuration setting found for key '"+path+"', retuning default value "+ defaultValue);
            }
        }catch(Exception e){
            LogService.getInstance(this).config().log(e.getMessage() +", retuning default value "+ defaultValue);
        }
        
        return defaultValue;
    }

    public List<Integer> getIntList(String path, List<Integer> defaultValue) {
        try{
            if(hasPath(path)){
                return getIntList(path);
            }else{
                LogService.getInstance(this).config().log("No configuration setting found for key '"+path+"', retuning default value "+ defaultValue);
            }
        }catch(Exception e){
            LogService.getInstance(this).config().log(e.getMessage() +", retuning default value "+ defaultValue);
        }
        
        return defaultValue;
    }

    public List<Long> getLongList(String path, List<Long> defaultValue) {
        try{
            if(hasPath(path)){
                return getLongList(path);
            }else{
                LogService.getInstance(this).config().log("No configuration setting found for key '"+path+"', retuning default value "+ defaultValue);
            }
        }catch(Exception e){
            LogService.getInstance(this).config().log(e.getMessage() +", retuning default value "+ defaultValue);
        }
        
        return defaultValue;
    }

    public List<Double> getDoubleList(String path, List<Double> defaultValue) {
        try{
            if(hasPath(path)){
                return getDoubleList(path);
            }else{
                LogService.getInstance(this).config().log("No configuration setting found for key '"+path+"', retuning default value "+ defaultValue);
            }
        }catch(Exception e){
            LogService.getInstance(this).config().log(e.getMessage() +", retuning default value "+ defaultValue);
        }
        
        return defaultValue;
    }

    public List<String> getStringList(String path, List<String> defaultValue) {
        try{
            if(hasPath(path)){
                return getStringList(path);
            }else{
                LogService.getInstance(this).config().log("No configuration setting found for key '"+path+"', retuning default value "+ defaultValue);
            }
        }catch(Exception e){
            LogService.getInstance(this).config().log(e.getMessage() +", retuning default value "+ defaultValue);
        }
        
        return defaultValue;
    }

    public <T extends Enum<T>> List<T> getEnumList(Class<T> aClass, String path) {
        return baseConfig.getEnumList(aClass, path);
    }

    public List<Long> getBytesList(String path) {
        return baseConfig.getBytesList(path);
    }

    public List<Long> getDurationList(String path, TimeUnit timeUnit) {
        return baseConfig.getDurationList(path, timeUnit);
    }

    public List<Duration> getDurationList(String path) {
        return baseConfig.getDurationList(path);
    }

    public List<Configuration> getConfigList(String path, ArrayList defaultValue) {
        try{
            if(hasPath(path)){
                return getConfigList(path);
            }else{
                LogService.getInstance(this).config().log("No configuration setting found for key '"+path+"', retuning default value "+ defaultValue);
            }
        }catch(Exception e){
            LogService.getInstance(this).config().log(e.getMessage() +", retuning default value "+ defaultValue);
        }
        
        return defaultValue;
    }
    
    public List<Configuration> getConfigList(String path) {
        if (!cachedConfigList.containsKey(path)) {
            List<? extends Config> baseConfigList = baseConfig.getConfigList(path);
            List<Configuration> configList = new ArrayList<>();
            baseConfigList.forEach(baseConfigs -> {
                configList.add(new Configuration(baseConfigs));
            });
            cachedConfigList.put(path, configList);
        }

        return cachedConfigList.get(path);
    }

    public Set<Map.Entry<String, Object>> entrySet() {
        Set<Map.Entry<String, ConfigValue>> entries = baseConfig.entrySet();
        Set<Map.Entry<String, Object>> result = new HashSet<>();
        entries.forEach(entry -> {
            result.add(new AbstractMap.SimpleEntry<>(entry.getKey(), entry.getValue().unwrapped()));
        });

        return result;
    }

    public <T> List<T> getBeanList(String path, Class<T> beanClass) {
        List<T> result = new ArrayList<>();
        List<? extends Config> configList = baseConfig.getConfigList(path);
        configList.stream().map(config -> ConfigBeanFactory.create(config, beanClass)).forEachOrdered(bean -> {
            result.add(bean);
        });

        return result;
    }
    
    public void clear(){
        cachedConfig.clear();
        cachedConfigList.clear();
    }
}
