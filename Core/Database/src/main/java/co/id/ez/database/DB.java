package co.id.ez.database;

import co.id.ez.database.core.Handle;
import co.id.ez.database.core.Jdbc;
import co.id.ez.database.query.QueryBuilder;
import co.id.ez.system.core.log.LogService;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import com.json.JSONObject;
import java.sql.Connection;

public class DB {
    private static final Map<String, Jdbc> jdbcMap = new HashMap<>();
    public static final DB loggingHook = new DB();

    private static Jdbc get(String name) {
        if(jdbcMap.containsKey(name)){
            return jdbcMap.get(name);
        }
        
        throw new IllegalArgumentException("Invalid connection name "+ name);
    }

    public static void set(String name, Jdbc jdbc) {
        jdbcMap.put(name, jdbc);
    }
    
    public static Connection getConnection(String pDBName) throws SQLException{
        if(jdbcMap.containsKey(pDBName)){
            Jdbc jdbc = jdbcMap.get(pDBName);
            return jdbc.getConnection();
        }
        
        return null;
    }
    
    public static void executeUpdate(String pDBName, String pQuery) throws SQLException{
        DB.get(pDBName).useHandle(tHandle -> {
            tHandle.createUpdate(pQuery).execute();
        });
    }
    
    public static void executeAsyncUpdate(String pDBName, String pQuery){
        new Thread(() -> {
            try {
                executeUpdate(pDBName, pQuery);
            } catch (SQLException ex) {
                LogService.getInstance(loggingHook).dbError().withCause(ex).log("[SQLException] Failed execute query {}", pQuery);
            } catch (Exception ex) {
                LogService.getInstance(loggingHook).dbError().withCause(ex).log("[Exception] Failed execute query {}", pQuery);
            }
        }).start();
    }
    
    public static void executeUpdate(String pDBName, QueryBuilder pQueryBuilder) throws SQLException{
        DB.get(pDBName).useHandle(tHandle -> {
            tHandle.createUpdate(pQueryBuilder.getQueryValue()).execute();
        });
    }
    
    public static void executeAsyncUpdate(String pDBName, QueryBuilder pQueryBuilder){
        new Thread(() -> {
            try {
                executeUpdate(pDBName, pQueryBuilder);
            } catch (SQLException ex) {
                LogService.getInstance(loggingHook).dbError().withCause(ex).log("[SQLException] Failed execute query with builder: {}", pQueryBuilder);
            } catch (Exception ex) {
                LogService.getInstance(loggingHook).dbError().withCause(ex).log("[Exception] Failed execute query with builder: {}", pQueryBuilder);
            }
        }).start();
    }
    
    public static LinkedList<JSONObject> executeQuery(String pDBName, String pQuery) throws SQLException{
        return DB.get(pDBName).withHandle((Handle tHandle) -> {
                return tHandle.createSelect(pQuery).map(rs -> {
                    return getListResult(rs);
                }).one().orElse(null);
            });
    }
    
    public static LinkedList<JSONObject> executeQuery(String pDBName, QueryBuilder pQueryBuilder) throws SQLException{
        return DB.get(pDBName).withHandle((Handle tHandle) -> {
                return tHandle.createSelect(pQueryBuilder.getQueryValue()).map(rs -> {
                    return getListResult(rs);
                }).one().orElse(null);
            });
    }
    
    /**
     * Constructs a list of data based on query result.
     *
     * @param pResult the result of the query
     * @return the list of data
     * @throws SQLException when retrieving query result data is failed
     */
    private static LinkedList<JSONObject> getListResult(ResultSet pResult) throws SQLException {
        return getList(pResult);
    }
    
    /**
     * Constructs the specified field name and result into a list of data.
     *
     * @param pListColumn the list of field name
     * @param pResultSet the query result data
     * @return the list of data
     * @throws SQLException when retrieving query result data is failed
     */
    private static LinkedList<JSONObject> getList(ResultSet pResultSet) throws SQLException {
        LinkedList<JSONObject> tList = new LinkedList<>();
        do {
            JSONObject tObject = new JSONObject();
            for (int i = 0; i < pResultSet.getMetaData().getColumnCount(); i++) {
                String tName = pResultSet.getMetaData().getColumnLabel(i + 1);
                Object tValue = pResultSet.getObject(i + 1);
                tObject.put(tName, tValue);
            }
            tList.add(tObject);
        }while (pResultSet.next());
        return tList;
    }
}
