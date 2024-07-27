/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.controller;

import co.id.ez.database.DB;
import static co.id.ez.ezpay.abstracts.AbstractModule.dbName;
import co.id.ez.ezpay.enums.RequestType;
import com.json.JSONObject;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

/**
 *
 * @author Lutfi
 */
public class HistoryTransaksiController {
    
    private final boolean isUnLimited;

    public HistoryTransaksiController() {
        this(false);
    }

    public HistoryTransaksiController(boolean isUnLimited) {
        this.isUnLimited = isUnLimited;
    }
    
    public LinkedList<JSONObject> getHistoryData(RequestType module, Date date, String search, int flag) throws SQLException {
        String query = constructQueryGetData(module, date, search, flag);
        LinkedList<JSONObject> tranmainData = DB.executeQuery(dbName, query);
        return tranmainData;
    }
    
    private String constructQueryGetData(RequestType module, Date date, String search, int flag){
        String trxDate = date == null ? "" : new SimpleDateFormat("yyyy-MM-dd").format(date);
        String tQuery ;
        switch(module){
            case VOUCHER:
                tQuery = "SELECT "
                        + "DATE_FORMAT(transaction_date, '%Y-%m-%d %H:%i:%s') as transaction_date, "
                        + "transaction_id, "
                        + "dest_number, "
                        + "voucher_id, "
                        + "nominal, "
                        + "serial_number, "
                        + "flag "
                        + "FROM trx_vcr_sales "
                        + "WHERE "
                        + "flag" + (flag == 1 ? " = " : " >= ") +flag+" "
                        + ( isUnLimited ? "" : "AND transaction_date BETWEEN '"+trxDate+" 00:00:00' AND '"+trxDate+" 23:59:59' ");
                
                if(search.length() > 0){
                    tQuery = tQuery.concat(" AND (")
                            .concat("transaction_id LIKE '%"+search+"%' ")
                            .concat("OR dest_number LIKE '%"+search+"%' )");
                }
                
                tQuery = tQuery.concat("ORDER BY transaction_date DESC ");
                break;
            case PDAM:
                tQuery = "SELECT "
                        + "DATE_FORMAT(transaction_date, '%Y-%m-%d %H:%i:%s') as transaction_date, "
                        + "transaction_id, "
                        + "subscriber_id, "
                        + "subscriber_name, " 
                        + "subscriber_segmentation, "
                        + "total_bill, "
                        + "flag "
                        + "FROM trx_pam_sales "
                        + "WHERE "
                        + "flag" + (flag == 1 ? " = " : " >= ") +flag+" "
                        + ( isUnLimited ? "" : "AND transaction_date BETWEEN '"+trxDate+" 00:00:00' AND '"+trxDate+" 23:59:59' ");
                
                if(search.length() > 0){
                    tQuery = tQuery.concat(" AND (")
                            .concat("transaction_id LIKE '%"+search+"%' ")
                            .concat("OR subscriber_name LIKE '%"+search+"%' ")
                            .concat("OR subscriber_id LIKE '%"+search+"%' )");
                }
                
                tQuery = tQuery.concat("ORDER BY transaction_date DESC");
                break;
            case PREPAID:
                tQuery = "SELECT "
                        + "DATE_FORMAT(transaction_date, '%Y-%m-%d %H:%i:%s') as transaction_date, "
                        + "transaction_id, "
                        + "subscriber_id, "
                        + "msn, "
                        + "subscriber_name, "
                        + "segmentation, " 
                        + "nominal, "
                        + "token, "
                        + "flag "
                        + "FROM trx_pre_sales "
                        + "WHERE "
                        + "flag" + (flag == 1 ? " = " : " >= ") +flag+" "
                        + ( isUnLimited ? "" : "AND transaction_date BETWEEN '"+trxDate+" 00:00:00' AND '"+trxDate+" 23:59:59' ");
                
                if(search.length() > 0){
                    tQuery = tQuery.concat(" AND (")
                            .concat("transaction_id LIKE '%"+search+"%' ")
                            .concat("OR subscriber_name LIKE '%"+search+"%' ")
                            .concat("OR subscriber_id LIKE '%"+search+"%' )");
                }
                
                tQuery = tQuery.concat("ORDER BY transaction_date DESC");
                break;
            case POSTPAID:
                tQuery = "SELECT "
                        + "DATE_FORMAT(transaction_date, '%Y-%m-%d %H:%i:%s') as transaction_date, "
                        + "transaction_id, "
                        + "subscriber_id, "
                        + "subscriber_name, " 
                        + "segmentation, "
                        + "total_bill_amount, "
                        + "flag "
                        + "FROM trx_pos_sales "
                        + "WHERE "
                        + "flag" + (flag == 1 ? " = " : " >= ") +flag+" "
                        + ( isUnLimited ? "" : "AND transaction_date BETWEEN '"+trxDate+" 00:00:00' AND '"+trxDate+" 23:59:59' ");
                
                if(search.length() > 0){
                    tQuery = tQuery.concat(" AND (")
                            .concat("transaction_id LIKE '%"+search+"%' ")
                            .concat("OR subscriber_name LIKE '%"+search+"%' ")
                            .concat("OR subscriber_id LIKE '%"+search+"%' )");
                }
                
                tQuery = tQuery.concat("ORDER BY transaction_date DESC");
                break;
            default:
                tQuery = "SELECT "
                        + "DATE_FORMAT(transaction_date, '%Y-%m-%d %H:%i:%s') as transaction_date, "
                        + "transaction_id, "
                        + "input_id_1, "
                        + "input_id_2, "
                        + "input_id_3, "
                        + "transaction_data, " 
                        + "bill_amount, "
                        + "biller, "
                        + "flag "
                        + "FROM trx_mp_sales "
                        + "WHERE "
                        + "flag" + (flag == 1 ? " = " : " >= ") +flag+" "
                        + ( isUnLimited ? "" : "AND transaction_date BETWEEN '"+trxDate+" 00:00:00' AND '"+trxDate+" 23:59:59' ");
                        
                if(module != RequestType.MULTIPAYMENT){
                    tQuery = tQuery.concat("AND biller = '"+module.getModule()+"' ");
                }
                        
                
                if(search.length() > 0){
                    tQuery = tQuery.concat(" AND (")
                            .concat("transaction_id LIKE '%"+search+"%' ")
                            .concat("OR input_id_1 LIKE '%"+search+"%' ")
                            .concat("OR input_id_2 LIKE '%"+search+"%' ")
                            .concat("OR input_id_3 LIKE '%"+search+"%' ")
                            .concat("OR biller LIKE '%"+search+"%' )");
                }
                
                tQuery = tQuery.concat("ORDER BY transaction_date DESC");
                break;
        }
        
        if (isUnLimited) {
            tQuery = tQuery.concat(" LIMIT 1000");
        }
        return tQuery;
    }
}
