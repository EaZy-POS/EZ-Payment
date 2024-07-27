/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.controller;

import co.id.ez.database.DB;
import static co.id.ez.ezpay.abstracts.AbstractModule.dbName;
import com.json.JSONObject;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 *
 * @author Lutfi
 */
public class TransactionRecapController {
    
    public String getQueryVoucherDailyReport(String startdate, String enddate) {
        String tSQL = "SELECT * from v_vcr_daily_report "
                + "WHERE transaction_date BETWEEN '" + startdate + "' AND '" + enddate + "'";
        return tSQL;
    }
    
    public LinkedList<JSONObject> getVoucherDailyReport(String startdate, String enddate) throws SQLException {
        String tSQL = getQueryVoucherDailyReport(startdate, enddate);
        LinkedList<JSONObject> data = DB.executeQuery(dbName, tSQL);
        
        if (data != null && data.size() > 0) {
            return data;
        }
        
        return new LinkedList<>();
    }
    
    public String getQueryVoucherMonthlyReport(String startdate, String enddate) {
        String tSQL = "SELECT * FROM v_vcr_monthly_report "
                + "WHERE bulan BETWEEN '" + startdate + "' AND '" + enddate + "'";
        return tSQL;
    }
    
    public LinkedList<JSONObject> getVoucherMonthlyReport(String startdate, String enddate) throws SQLException {
        String tSQL = getQueryVoucherMonthlyReport(startdate, enddate);
        LinkedList<JSONObject> data = DB.executeQuery(dbName, tSQL);
        
        if (data != null && data.size() > 0) {
            return data;
        }
        
        return new LinkedList<>();
    }
    
    public String getQueryPrepaidDailyReport(String startdate, String enddate) {
        String tSQL = "SELECT * from v_pre_daily_report "
                + "WHERE transaction_date BETWEEN '" + startdate + "' AND '" + enddate + "'";
        return tSQL;
    }
    
    public LinkedList<JSONObject> getPrepaidDailyReport(String startdate, String enddate) throws SQLException {
        String tSQL = getQueryPrepaidDailyReport(startdate, enddate);
        LinkedList<JSONObject> data = DB.executeQuery(dbName, tSQL);
        
        if (data != null && data.size() > 0) {
            return data;
        }
        
        return new LinkedList<>();
    }
    
    public String getQueryPrepaidMonthlyReport(String startdate, String enddate) {
        String tSQL = "SELECT * FROM v_pre_monthly_report "
                + "WHERE bulan BETWEEN '" + startdate + "' AND '" + enddate + "'";
        return tSQL;
    }
    
    public LinkedList<JSONObject> getPrepaidMonthlyReport(String startdate, String enddate) throws SQLException {
        String tSQL = getQueryPrepaidMonthlyReport(startdate, enddate);
        LinkedList<JSONObject> data = DB.executeQuery(dbName, tSQL);
        
        if (data != null && data.size() > 0) {
            return data;
        }
        
        return new LinkedList<>();
    }
    
    public String getQueryPospaidDailyReport(String startdate, String enddate) {
        String tSQL = "SELECT * from v_pos_daily_report "
                + "WHERE transaction_date BETWEEN '" + startdate + "' AND '" + enddate + "'";
        return tSQL;
    }
    
    public LinkedList<JSONObject> getPospaidDailyReport(String startdate, String enddate) throws SQLException {
        String tSQL = getQueryPospaidDailyReport(startdate, enddate);
        LinkedList<JSONObject> data = DB.executeQuery(dbName, tSQL);
        
        if (data != null && data.size() > 0) {
            return data;
        }
        
        return new LinkedList<>();
    }
    
    public String getQueryPospaidMonthlyReport(String startdate, String enddate) {
        String tSQL = "SELECT * FROM v_pos_monthly_report "
                + "WHERE bulan BETWEEN '" + startdate + "' AND '" + enddate + "'";
        return tSQL;
    }
    
    public LinkedList<JSONObject> getPospaidMonthlyReport(String startdate, String enddate) throws SQLException {
        String tSQL = getQueryPospaidMonthlyReport(startdate, enddate);
        LinkedList<JSONObject> data = DB.executeQuery(dbName, tSQL);
        
        if (data != null && data.size() > 0) {
            return data;
        }
        
        return new LinkedList<>();
    }
    
    public String getQueryMultiPaymentDailyReport(String startdate, String enddate, String biller) {
        String tSQL = "SELECT * from v_mp_daily_report "
                + "WHERE transaction_date BETWEEN '" + startdate + "' AND '" + enddate + "'"
                + (biller == null ? "" : "AND biller = '" + biller + "'");
        return tSQL;
    }
    
    public LinkedList<JSONObject> getMultiPaymentDailyReport(String startdate, String enddate, String biller) throws SQLException {
        String tSQL = getQueryMultiPaymentDailyReport(startdate, enddate, biller);
        LinkedList<JSONObject> data = DB.executeQuery(dbName, tSQL);
        
        if (data != null && data.size() > 0) {
            return data;
        }
        
        return new LinkedList<>();
    }
    
    public String getQueryMultiPaymentMonthlyReport(String startdate, String enddate, String biller) {
        String tSQL = "SELECT * FROM v_mp_monthly_report "
                + "WHERE bulan BETWEEN '" + startdate + "' AND '" + enddate + "' "
                + (biller == null ? "" : "AND biller = '" + biller + "'");
        return tSQL;
    }
    
    public LinkedList<JSONObject> getMultiPaymentMonthlyReport(String startdate, String enddate, String biller) throws SQLException {
        String tSQL = getQueryMultiPaymentMonthlyReport(startdate, enddate, biller);
        LinkedList<JSONObject> data = DB.executeQuery(dbName, tSQL);
        
        if (data != null && data.size() > 0) {
            return data;
        }
        
        return new LinkedList<>();
    }
    
    public String getQueryPDAMDailyReport(String startdate, String enddate) {
        String tSQL = "SELECT * from v_pam_daily_report "
                + "WHERE transaction_date BETWEEN '" + startdate + "' AND '" + enddate + "'";
        return tSQL;
    }
    
    public LinkedList<JSONObject> getPDAMDailyReport(String startdate, String enddate) throws SQLException {
        String tSQL = getQueryPDAMDailyReport(startdate, enddate);
        LinkedList<JSONObject> data = DB.executeQuery(dbName, tSQL);
        
        if (data != null && data.size() > 0) {
            return data;
        }
        
        return new LinkedList<>();
    }
    
    public String getQueryPDAMMonthlyReport(String startdate, String enddate) {
        String tSQL = "SELECT * FROM v_pam_monthly_report "
                + "WHERE bulan BETWEEN '" + startdate + "' AND '" + enddate + "'";
        return tSQL;
    }
    
    public LinkedList<JSONObject> getPDAMMonthlyReport(String startdate, String enddate) throws SQLException {
        String tSQL = getQueryPDAMMonthlyReport(startdate, enddate);
        LinkedList<JSONObject> data = DB.executeQuery(dbName, tSQL);
        
        if (data != null && data.size() > 0) {
            return data;
        }
        
        return new LinkedList<>();
    }
}
