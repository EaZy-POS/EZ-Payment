package co.id.ez.system.core.config;

import co.id.ez.system.core.etc.EncryptionService;
import com.google.common.reflect.ClassPath;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigException;
import com.typesafe.config.ConfigFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

class ConfigurationFactory {

    private static final String cKey = "enmcH556poKyPwxx";
    
    public static Configuration loadFromDirectory(String... files) {
        Config config = ConfigFactory.empty();
        String curentPath = "";
        try {
            config = readConfigFromResources();

            for (String path : files) {
                curentPath = path;
                File dir = new File(path);
                if (dir.isDirectory()) {
                    config = appendConfigFromDir(config, dir);
                } else {
                    throw new ConfigException.BadPath(path, "Path " + path + " is not a valid directory");
                }
            }
        } catch (IOException e) {
            throw new ConfigException.BadPath(curentPath, e.getMessage(), e);
        }

        return new Configuration(config);
    }

    public static Configuration load() throws IOException {
        Config config = readConfigFromResources();
        return new Configuration(config);
    }

    private static Config readConfigFromResources() throws IOException {
        Config config = ConfigFactory.empty();
        URL conf = Configuration.class.getResource("/conf");

        File confFolder = new File(conf.getFile());
        if (confFolder.isDirectory()) {
            config = appendConfigFromDir(config, confFolder);
        }

        ClassPath classPath = ClassPath.from(ConfigurationFactory.class.getClassLoader());

        List<ClassPath.ResourceInfo> resources = classPath.getResources()
                .stream()
                .filter(res -> res.getResourceName().endsWith(".conf"))
                .collect(Collectors.toList());

        for (ClassPath.ResourceInfo res : resources) {
            Config newConfig = ConfigFactory.parseResources(res.getResourceName());
            config = config.withFallback(newConfig);
        }

        return config;
    }

    private static Config appendConfigFromDir(Config baseConfig, File dir) throws FileNotFoundException {
        Config result = baseConfig;

        for (File item : dir.listFiles((File file) -> !file.getName().startsWith("."))) {
            Config config = null;
            if (item.getName().endsWith(".cfg")) {
                config = createConfig(item);
            } else if (item.getName().endsWith(".conf")) {
                if (item.isFile()) {
                    config = ConfigFactory.parseFile(item);
                } else if (item.isDirectory()) {
                    config = ConfigFactory.empty();
                    config = appendConfigFromDir(config, item);
                }
            }

            if (config != null) {
                result = config.withFallback(result);
            }
        }

        return result;
    }

    public static Config createConfig(File file) throws FileNotFoundException {
        if (file.isFile() && file.getName().endsWith(".cfg")) {
            String configName = file.getName().substring(0, file.getName().lastIndexOf("."));
            String type = null, name = null;
            boolean isEcrypt = false;
            HashMap<String, List<String>> holder = new HashMap<>();

            InputStream is = new FileInputStream(file);
            try (Scanner myReader = new Scanner(is)) {
                while (myReader.hasNextLine()) {
                    String tmpValue = myReader.nextLine();
                    if (!tmpValue.startsWith("#")
                            && !tmpValue.startsWith("--")
                            && tmpValue.contains("=")
                            && !tmpValue.trim().equals("")) {
                        String[] splitValue = tmpValue.split("=");
                        if (splitValue.length > 1) {
                            if (splitValue[0].trim().equalsIgnoreCase("$target")) {
                                configName = splitValue[1].trim();
                            } else if (splitValue[0].trim().equalsIgnoreCase("@type")) {
                                type = splitValue[1].trim();
                            } else if (splitValue[0].trim().equalsIgnoreCase("@name")) {
                                name = splitValue[1].trim();
                            } else if (splitValue[0].trim().equalsIgnoreCase("@enc")) {
                                isEcrypt = splitValue[1].trim().equalsIgnoreCase("true");
                            } else {
                                String key = splitValue[0].trim();
                                String value = splitValue[1].trim().replace("<>", "=");

                                if (holder.containsKey(key)) {
                                    holder.get(key).add(value);
                                } else {
                                    List<String> lits = new ArrayList<>();
                                    lits.add(value);
                                    holder.put(key, lits);
                                }
                            }
                        }
                    }
                }
            }

            String result = contructConfigString(configName, type, name, holder, isEcrypt);
            return ConfigFactory.parseString(result);
        }

        return null;
    }

    private static String contructConfigString(
            String configName,
            String type,
            String name,
            HashMap<String, List<String>> holder,
            boolean isEnc
    ) {
        if (type == null) {
            type = "config";
        }

        if (name == null) {
            name = "";
        }

        switch (type) {
            case "config":
                return constructConfig(configName, name, holder, isEnc);
            case "configlist":
                return constructConfigList(configName, name, holder, isEnc);
            case "stringlist":
                return constructStringList(configName, name, holder, isEnc);
            default:
                return constructConfig(configName, name, holder, isEnc);
        }
    }

    private static String constructConfig(
            String configName,
            String name,
            HashMap<String, List<String>> holder,
            boolean isEnc
    ) {
        String result = "";

        int preindex = 0;
        for (String key : holder.keySet()) {
            result = result.concat(preindex == 0 ? "" : ", ");
            List<String> value = holder.get(key);
            int index = 0;
            for (String val : value) {
                if (isEnc) {
                    val = EncryptionService
                            .encryptor().decrypt(
                                    EncryptionService
                                            .encryptor()
                                            .Base64Decrypt(val.replace("<>", "=")),
                                    cKey);
                }
                result = result
                        .concat(index == 0 ? "" : ", ")
                        .concat(key)
                        .concat(": ")
                        .concat("\"" + val + "\"");
                index++;
            }
            preindex++;
        }

        result = configName.concat(name.equals("") ? "" : ".").concat(name).concat("{").concat(result).concat("}");
        return result;
    }

    private static String constructConfigList(
            String configName,
            String name,
            HashMap<String, List<String>> holder,
            boolean isEnc
    ) {
        String result = "";
        List<HashMap<String, String>> listConfig = new ArrayList<>();
        holder.keySet().forEach(key -> {
            List<String> value = holder.get(key);
            int index = 0;
            for (String val : value) {
                if (index < listConfig.size()) {
                    listConfig.get(index).put(key, val);
                } else {
                    HashMap<String, String> data = new HashMap<>();
                    data.put(key, val);
                    listConfig.add(data);
                }
                index++;
            }
        });

        int preindex = 0;
        for (HashMap<String, String> hashMap : listConfig) {
            result = result.concat(preindex == 0 ? "" : ", ").concat("{");
            int index = 0;
            for (String key : hashMap.keySet()) {
                String val = hashMap.get(key);
                if (isEnc) {
                    val = EncryptionService
                            .encryptor().decrypt(
                                    EncryptionService
                                            .encryptor()
                                            .Base64Decrypt(val.replace("<>", "=")),
                                    cKey);
                }
                result = result
                        .concat(index == 0 ? "" : ", ")
                        .concat(key)
                        .concat(": ")
                        .concat("\"" + val +"\"" );
                index++;
            }
            result = result.concat("}");
            preindex++;
        }

        result = configName.concat(name.equals("") ? "" : ".").concat(name).concat(":[").concat(result).concat("]");
        return result;
    }
    
    private static String constructStringList(
            String configName,
            String name,
            HashMap<String, List<String>> holder,
            boolean isEnc
    ) {
        String result = "";
        
        int preindex = 0;
        for (String key : holder.keySet()) {
            result = result.concat(preindex == 0 ? "" : ", ").concat("[");
            List<String> value = holder.get(key);
            int index = 0;
            for (String val : value) {
                if (isEnc) {
                    val = EncryptionService
                            .encryptor().decrypt(
                                    EncryptionService
                                            .encryptor()
                                            .Base64Decrypt(val.replace("<>", "=")),
                                    cKey);
                }
                result = result
                        .concat(index == 0 ? "" : ", ")
                        .concat("\"" + val + "\"");
                index++;
            }
            result = result.concat("]");
            preindex++;
        }

        result = configName.concat(name.equals("") ? "" :".").concat(name).concat(":[").concat(result).concat("]");
        return result;
    }
}
