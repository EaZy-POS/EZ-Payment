/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.controller;

import co.id.ez.database.DB;
import co.id.ez.ezpay.abstracts.AbstractModule;
import com.json.JSONObject;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

/**
 *
 * @author RCS
 */
public class DashboardController {
    
    public JSONObject getSummaryCurrentMonth() throws SQLException{
        String tCurentMonth = new SimpleDateFormat("yyyy-MM").format(new Date());
        String tSQL = "SELECT * FROM v_dashboard_summary_monthly WHERE transaction_date = '"+tCurentMonth+"'";
        LinkedList<JSONObject> data = DB.executeQuery(AbstractModule.dbName, tSQL);
        if(data != null && data.size() > 0){
            return data.getFirst();
        }
        
        return null;
    }
    
    public JSONObject getSummaryCurrentDay() throws SQLException{
        String tCurentMonth = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String tSQL = "SELECT * FROM v_dashboard_summary_daily WHERE transaction_date = '"+tCurentMonth+"'";
        LinkedList<JSONObject> data = DB.executeQuery(AbstractModule.dbName, tSQL);
        if(data != null && data.size() > 0){
            return data.getFirst();
        }
        
        return null;
    }
    
    public LinkedList<JSONObject> getHistoriTransaksi() throws SQLException{
        String tSQL = "SELECT * FROM v_summary_transaction_today";
        return DB.executeQuery(AbstractModule.dbName, tSQL);
    }
    
    public LinkedList<JSONObject> getSummarySalesCurentMonth() throws SQLException{
        String tSQL = "SELECT * FROM v_summary_sales_curent_month";
        return DB.executeQuery(AbstractModule.dbName, tSQL);
    }
    
    public LinkedList<JSONObject> getSummarySales(String pStartDate, String pEndDate) throws SQLException{
        String tSQL = "SELECT *, DATE_FORMAT(transaction_date,'%d %M %y') as tran_date "
                + "FROM v_summary_sales "
                + "WHERE transaction_date BETWEEN '"+pStartDate+"' AND '"+pEndDate+"' "
                + "ORDER BY total_amount, module, transaction_date";
        return DB.executeQuery(AbstractModule.dbName, tSQL);
    }
    
}
