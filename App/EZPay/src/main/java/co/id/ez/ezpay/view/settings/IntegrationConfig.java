/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.view.settings;

import com.json.JSONArray;
import com.json.JSONObject;
import java.util.LinkedHashMap;

/**
 *
 * @author RCS
 */
public class IntegrationConfig {

    private final LinkedHashMap<String, Integration> maps = new LinkedHashMap<>();
    private String tableName;

    public void setTableName(String tableName){
        this.tableName = tableName;
    }

    public String getTableName() {
        return tableName;
    }
    
    public void add(String dataField, String targetField) {
        add(dataField, targetField, false, null);
    }

    public void add(String dataField, String targetField, boolean isDefault, String pDefaultValue) {
        add(dataField, targetField, isDefault, pDefaultValue, false);
    }

    public void add(String dataField, String targetField, boolean isDefault, String pDefaultValue, boolean isSQLSyntax) {
        maps.put(dataField, new Integration(targetField, pDefaultValue, isDefault, isSQLSyntax));
    }

    public LinkedHashMap<String, Integration> values() {
        return maps;
    }

    public void remove(String dataField) {
        if (maps.containsKey(dataField)) {
            maps.remove(dataField);
        }
    }

    public String getKeyResult(String key) {
        return key + "= " + maps.get(key).toString();
    }

    @Override
    public String toString() {
        JSONObject obj = new JSONObject().put("table_name", getTableName());
        JSONArray fields = new JSONArray();
        
        maps.keySet().stream().map(key -> {
            Integration integr = maps.get(key);
            JSONObject field = new JSONObject(integr.toString());
            field.put("data", key);
            return field;
        }).forEachOrdered(field -> {
            fields.put(field);
        });
        
        obj.put("fields", fields);
        return obj.toString(0);
    }

    public class Integration {

        private final String field, defaultValue;
        private final boolean isDefault, isSQLSyntax;

        public Integration(String field, String defaultValue, boolean isDefault, boolean isSQLSyntax) {
            this.field = field;
            this.defaultValue = defaultValue;
            this.isDefault = isDefault;
            this.isSQLSyntax = isSQLSyntax;
        }

        public String getField() {
            return field;
        }

        public String getDefaultValue() {
            return defaultValue;
        }

        public boolean isDefault() {
            return isDefault;
        }

        public boolean isSQLSyntax() {
            return isSQLSyntax;
        }

        @Override
        public String toString() {
            return "{" + "\"field\": \"" + field + "\", \"defaultValue\": \"" + defaultValue + "\", "
                    + "\"isDefault\": " + isDefault + ", \"isSQLSyntax\": " + isSQLSyntax + '}';
        }

        
    }
}
