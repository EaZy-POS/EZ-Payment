/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.gateway.data;

import co.id.ez.database.DB;
import co.id.ez.database.entity.WhereField;
import co.id.ez.database.enums.Operator;
import co.id.ez.database.query.QueryBuilder;
import co.id.ez.database.query.QueryConditional;
import co.id.ez.database.query.QueryType;
import co.id.ez.gateway.resource.CommonHanlder;
import co.id.ez.gateway.util.RequestMapping;
import co.id.ez.gateway.util.enums.TableName;
import co.id.ez.gateway.util.enums.tables.LogDownTable;
import co.id.ez.system.core.ex.ServiceException;
import co.id.ez.system.core.log.LogService;
import co.id.ez.system.core.rc.RC;
import com.json.JSONObject;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 *
 * @author LUTFI
 */
public class Repository {

    private static final Repository loggingHook = new Repository();

    public static LinkedList<JSONObject> getTranmainData(String pTranmainTable, String pTranID, String pMitraID, String pModuleID) {
        try {
            QueryBuilder builder = new QueryBuilder(pTranmainTable, QueryType.SELECT);
            builder.addEntry("*");
            builder.addWhereValue(new WhereField("transaction_id", pTranID, Operator.EQUALS));
            builder.addWhereValue(new WhereField("mitra_id", pMitraID, Operator.EQUALS, QueryConditional.AND));
            builder.addWhereValue(new WhereField("module_id", pModuleID, Operator.EQUALS, QueryConditional.AND));

            LinkedList<JSONObject> tResult = DB.executeQuery(CommonHanlder.dbName, builder);
            if (tResult != null) {
                if (tResult.size() > 0) {
                    return tResult;
                }
            }
        } catch (SQLException ex) {
            throw new ServiceException(RC.ERROR_DATABASE, "[SQLException] Failed to get tranmain data", ex);
        }

        throw new ServiceException(RC.ERROR_INVALID_SWITCHING_REFNUM, "Transaction Not found with trxid [" + pTranID + "] module id ["+pModuleID+"]");
    }

    public static void logMessage(String pComand, String pMsgId, String pCorrelationID, String pTrxID, RequestMapping reqMap, String pMessage, String pWid) {
        try {
            QueryBuilder builder = new QueryBuilder(TableName.LOG_BILLER.get(), QueryType.INSERT);
            builder.addEntryValue(LogDownTable.id.name(), "UUID()", true);
            builder.addEntryValue(LogDownTable.created_at.name(), "NOW()", true);
            builder.addEntryValue(LogDownTable.created_by.name(), reqMap == null ? "" :reqMap.getUser_id());
            builder.addEntryValue(LogDownTable.msg_id.name(), pMsgId);
            builder.addEntryValue(LogDownTable.comand.name(), pComand);
            builder.addEntryValue(LogDownTable.correlation_id.name(), pCorrelationID);
            builder.addEntryValue(LogDownTable.trx_id.name(), pTrxID);
            builder.addEntryValue(LogDownTable.stream.name(), escape(pMessage));
            builder.addEntryValue(LogDownTable.module_id.name(), reqMap == null ? "" :reqMap.getModule_id());
            builder.addEntryValue(LogDownTable.mitra_id.name(), reqMap == null ? "" :reqMap.getClient_id());
            builder.addEntryValue(LogDownTable.wid.name(), pWid);
            builder.addEntryValue(LogDownTable.rc.name(), "");
            
            DB.executeAsyncUpdate(CommonHanlder.dbName, builder);
            LogService.getInstance(loggingHook).db().log("[Success Execute logMessage]: {}", pMessage);
        } catch (Exception e) {
            LogService.getInstance(loggingHook).dbError().withCause(e).log("[Failed Execute logMessage]: " + pMessage, true);
        }
    }

    public static void logDownMessage(String pComand, String pMsgId, String pCorrelationID, RequestMapping reqMap, JSONObject pMessage, String pWid) {
        try {
            QueryBuilder builder = new QueryBuilder(TableName.LOG_DOWN.get(), QueryType.INSERT);
            builder.addEntryValue(LogDownTable.id.name(), "UUID()", true);
            builder.addEntryValue(LogDownTable.created_at.name(), "NOW()", true);
            builder.addEntryValue(LogDownTable.created_by.name(), reqMap == null ? "" :reqMap.getUser_id());
            builder.addEntryValue(LogDownTable.msg_id.name(), pMsgId);
            builder.addEntryValue(LogDownTable.comand.name(), pComand);
            builder.addEntryValue(LogDownTable.correlation_id.name(), pCorrelationID);
            builder.addEntryValue(LogDownTable.trx_id.name(), pMessage.has("trxid") ? pMessage.getString("trxid") : "");
            builder.addEntryValue(LogDownTable.stream.name(), escape(pMessage.toString()));
            builder.addEntryValue(LogDownTable.module_id.name(), reqMap == null ? "" :reqMap.getModule_id());
            builder.addEntryValue(LogDownTable.mitra_id.name(), reqMap == null ? "" :reqMap.getClient_id());
            builder.addEntryValue(LogDownTable.wid.name(), pWid);
            builder.addEntryValue(LogDownTable.rc.name(), pMessage.has("rc") ? pMessage.getString("rc") : "");
            
            DB.executeAsyncUpdate(CommonHanlder.dbName, builder);
            LogService.getInstance(loggingHook).db().log("[Success Execute logDownMessage]: {}", pMessage.toString());
        } catch (Exception e) {
            LogService.getInstance(loggingHook).error().withCause(e).log("[Failed Execute logDownMessage]: "+ pMessage.toString(), true);
        }
    }

    public static void insertDeviceMitra(String comp_name, String os, String proc_rev, String proc_idnt, String device_id, String snkey, RequestMapping reqMap) {

        try {
            QueryBuilder builder = new QueryBuilder("ez_device_mitra", QueryType.INSERT);
            builder.addEntryValue("reg_id", "NULL", true);
            builder.addEntryValue("reg_date", "NOW()", true);
            builder.addEntryValue("mitra_id", reqMap.getClient_id());
            builder.addEntryValue("device_id", device_id);
            builder.addEntryValue("computer_name", comp_name);
            builder.addEntryValue("operating_system", os);
            builder.addEntryValue("processor_revision", proc_rev);
            builder.addEntryValue("processor_identifier", proc_idnt);
            builder.addEntryValue("snkey", snkey);

            DB.executeUpdate(CommonHanlder.dbName, builder);

            LogService.getInstance(loggingHook).db().log("[Success Execute ez_device_mitra]: {}", builder);
        } catch (SQLException e) {
            throw new ServiceException(RC.ERROR_DATABASE, "Error Insert Device Mitra, " + e.getMessage(), e);
        }
    }

    public static String escape(String s) {
        return s.replace("\\", "\\\\")
                .replace("\t", "\\t")
                .replace("\b", "\\b")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\f", "\\f")
                .replace("\'", "\\'")
                .replace("\"", "\\\"");
    }
}
