/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.controller;

import co.id.ez.database.DB;
import co.id.ez.database.entity.WhereField;
import co.id.ez.database.enums.Operator;
import co.id.ez.database.query.QueryBuilder;
import co.id.ez.database.query.QueryConditional;
import co.id.ez.database.query.QueryType;
import co.id.ez.ezpay.abstracts.AbstractModule;
import co.id.ez.ezpay.app.App;
import co.id.ez.ezpay.app.Common;
import co.id.ez.system.core.config.ConfigService;
import co.id.ez.system.core.etc.EncryptionService;
import com.json.JSONObject;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author RCS
 */
public class AppController {

    public JSONObject getAppEnvironment() throws SQLException {
        if (ConfigService.getInstance().getString("service.environment", "dev").equalsIgnoreCase("dev")) {
            JSONObject data = new JSONObject()
                    .put("down_mac", ConfigService.getInstance().getString("app-config.dev.down_mac"))
                    .put("acc_pocky", ConfigService.getInstance().getString("app-config.dev.acc_pocky"))
                    .put("enu_key", ConfigService.getInstance().getString("app-config.dev.enu_key"))
                    .put("enp_val", ConfigService.getInstance().getString("app-config.dev.enp_val"))
                    .put("guri_base", ConfigService.getInstance().getString("app-config.dev.g_uri"))
                    .put("curi_base", ConfigService.getInstance().getString("app-config.dev.c_uri"));
            return data;
        } else {
            QueryBuilder builder = new QueryBuilder("ctl_down_acc", QueryType.SELECT);
            builder.addEntry("down_mac", "acc_pocky", "enu_key", "enp_val", "guri_base", "curi_base");
            builder.addWhereValue(new WhereField("status", "1", Operator.EQUALS, QueryConditional.AND));

            LinkedList<JSONObject> data = DB.executeQuery(AbstractModule.dbName, builder);

            if (data != null && data.size() > 0) {
                return data.getFirst();
            }
        }
        return null;
    }

    public void saveAppEnvironment(
            String pGwServer,
            String pCtlServer,
            String pClienID,
            String pSecretKey,
            String pUserName,
            String pPassword
    ) throws SQLException {

        String tQuery = "TRUNCATE ctl_down_acc";
        DB.executeUpdate(AbstractModule.dbName, tQuery);

        QueryBuilder builder = new QueryBuilder("ctl_down_acc", QueryType.INSERT);
        builder.addEntryValue(
                "guri_base",
                getEncryptedString(pGwServer, App.environment().getEnv_key())
        );

        builder.addEntryValue(
                "curi_base",
                getEncryptedString(pCtlServer, App.environment().getEnv_key())
        );

        builder.addEntryValue(
                "down_mac",
                getEncryptedString(pClienID, App.environment().getEnv_key())
        );

        builder.addEntryValue(
                "acc_pocky",
                getEncryptedString(pSecretKey, App.environment().getEnv_key() + pClienID)
        );

        builder.addEntryValue(
                "enu_key",
                getEncryptedString(pUserName, App.environment().getEnv_key())
        );

        builder.addEntryValue(
                "enp_val",
                getEncryptedString(pPassword, App.environment().getEnv_key() + pUserName)
        );

        builder.addEntryValue(
                "status",
                "1"
        );

        DB.executeUpdate(AbstractModule.dbName, builder);
    }

    private String getEncryptedString(String pTarget, String pKey) {
        return EncryptionService
                .encryptor()
                .Base64Encrypt(
                        EncryptionService
                                .encryptor().encrypt(
                                        pTarget,
                                        pKey
                                )
                );
    }

    public boolean isDBExist(String pName, String pDBName) throws SQLException {
        String tSQL = "SELECT COUNT(*) as val FROM information_schema.tables WHERE table_schema = '" + pDBName + "'";
        LinkedList<JSONObject> data = DB.executeQuery(pName, tSQL);
        if (data != null && data.size() > 0) {
            return data.getFirst().getInt("val") > 0;
        }

        return false;
    }

    public void createNewDB(String pName, String pDBName) throws SQLException {
        String tSQL = "CREATE DATABASE " + pDBName;
        DB.executeUpdate(pName, tSQL);
    }

    public void resetDB(String pName, String pDBName) throws SQLException {
        String tSQL = "DROP DATABASE " + pDBName;
        DB.executeUpdate(pName, tSQL);
        createNewDB(pName, pDBName);
    }

    public boolean isDBNeedReset(String pName, String pDBName) throws SQLException {
        String tSQL = "SELECT "
                + "IFNULL(( "
                + "SELECT count(table_name) FROM information_schema.tables "
                + "WHERE table_schema = '" + pDBName + "' "
                + "), 0) as table_count, "
                + "IFNULL(("
                + "SELECT count(table_name) FROM information_schema.tables "
                + "WHERE table_schema = '" + pDBName + "' AND table_name = 'flyway_schema_history' "
                + "), 0) as flyway";
        LinkedList<JSONObject> data = DB.executeQuery(pName, tSQL);
        if (data != null && data.size() > 0) {
            JSONObject result = data.getFirst();
            int tableCount = result.getInt("table_count");
            int flyway = result.getInt("flyway");

            return (tableCount > 0 && flyway == 0);
        }

        return false;
    }

    public void integrationConnectionCek() throws SQLException {
        String tSQL = "select @@version";
        DB.executeQuery("integration", tSQL);
    }

    public List<String> getTableName() throws SQLException {
        List<String> result = new ArrayList<>();
        String tSQL = "SHOW FULL TABLES where Table_Type = 'BASE TABLE'";
        LinkedList<JSONObject> data = DB.executeQuery("integration", tSQL);

        if (data != null && data.size() > 0) {
            data.forEach(dataTable -> {
                dataTable.keySet().stream().filter(
                        key -> (key.toString().toLowerCase().contains("tables"))).forEachOrdered(
                                key -> {
                                    result.add(dataTable.getString(key.toString()));
                                });
            });
        }

        return result;
    }

    public List<String> getFieldTable(String pTableName) throws SQLException {
        List<String> result = new ArrayList<>();
        String tSQL = "SHOW COLUMNS FROM " + pTableName + ";";

        LinkedList<JSONObject> data = DB.executeQuery("integration", tSQL);

        if (data != null && data.size() > 0) {
            data.forEach(dataTable -> {
                result.add(dataTable.getString("Field"));
            });
        }

        return result;
    }

    public JSONObject getIntegrationSetting(String pType) throws SQLException {
        String tSQL = "SELECT * FROM `ap_integration_config` WHERE type = '" + pType + "'";
        LinkedList<JSONObject> data = DB.executeQuery(AbstractModule.dbName, tSQL);
        if (data != null && data.size() > 0) {
            return data.getFirst();
        }
        return null;
    }

    public void insertIntegrationConfig(JSONObject configData, String pType) throws SQLException {
        String tSQL = "INSERT ap_integration_config (id, config, type)"
                + "VALUE ("
                + "UUID(), "
                
                + "'" + Common.escape(configData.toString()) + "', "
                + "'" + pType + "' "
                + ")";
        DB.executeUpdate(AbstractModule.dbName, tSQL);
    }

    public void updateIntegrationConfig(JSONObject configData, String id) throws SQLException {
        String tSQL = "UPDATE ap_integration_config "
                + "SET "
                + "config = '" + Common.escape(configData.toString()) + "' "
                + "WHERE id = '" + id + "'";
        DB.executeUpdate(AbstractModule.dbName, tSQL);
    }
}
