package co.id.ez.database;

import co.id.ez.database.core.Jdbc;
import co.id.ez.system.core.config.ConfigService;
import co.id.ez.system.core.config.Configuration;
import co.id.ez.system.core.etc.Utility;
import com.zaxxer.hikari.HikariDataSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DBService {

    private static final Map<String, HikariDataSource> dataSources = new HashMap<>();

    public static void loadDBConfig() {

        Utility.printMessage("Adding database connection ...");
        List<Configuration> connections = ConfigService.getInstance().getConfigList("ezsystem.database.connections", new ArrayList());

        connections.forEach(conn -> {
            String name = conn.getString("name");
            String url = conn.getString("url");
            String username = conn.getString("username");
            String password = conn.getString("password");

            Utility.printMessage(" - Connecting to " + name + "(" + url + ")");

            HikariDataSource ds = new HikariDataSource();
            ds.setJdbcUrl(url);
            ds.setUsername(username);
            ds.setPassword(password);

            if (conn.hasPath("auto-commit")) {
                ds.setAutoCommit(conn.getBoolean("auto-commit"));
            }
            if (conn.hasPath("connection-timeout")) {
                ds.setConnectionTimeout(conn.getLong("connection-timeout"));
            }
            if (conn.hasPath("idle-timeout")) {
                ds.setIdleTimeout(conn.getLong("idle-timeout"));
            }
            if (conn.hasPath("max-lifetime")) {
                ds.setMaxLifetime(conn.getLong("max-lifetime"));
            }
            if (conn.hasPath("minimum-idle")) {
                ds.setMaximumPoolSize(conn.getInt("minimum-idle"));
            }
            if (conn.hasPath("maximum-pool-size")) {
                ds.setMaximumPoolSize(conn.getInt("maximum-pool-size"));
            }
            if (conn.hasPath("schema")) {
                ds.setSchema(conn.getString("schema"));
            }

            if (conn.hasPath("datasource")) {
                Set<Map.Entry<String, Object>> dsConfig = conn.getConfig("datasource").entrySet();
                dsConfig.forEach(entry -> {
                    ds.addDataSourceProperty(entry.getKey(), entry.getValue());
                });
            }

            dataSources.put(name, ds);
            DB.set(name, Jdbc.create(ds));
        });

        Utility.printMessage("Finish adding database connection ...");
        Utility.createSeparator();
    }

    public static HikariDataSource getDataSource(String name) {
        if (dataSources.containsKey(name)) {
            return dataSources.get(name);
        }

        return null;
    }

    public static void createSingleDBConfig(String name, String url, String username, String password) {
        HikariDataSource ds = new HikariDataSource();
        ds.setJdbcUrl(url);
        ds.setUsername(username);
        ds.setPassword(password);
        dataSources.put(name, ds);
        DB.set(name, Jdbc.create(ds));
    }

    public static void stop() {
        new Thread(() -> {
            Utility.printMessage("Closing database connection ...");
            dataSources.values().forEach(ds -> {
                ds.close();
            });
            Utility.printMessage("Done.");
        }).start();
    }

}
