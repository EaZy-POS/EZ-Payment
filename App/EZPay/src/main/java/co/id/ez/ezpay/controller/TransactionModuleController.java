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
import static co.id.ez.ezpay.abstracts.AbstractModule.dbName;
import co.id.ez.ezpay.app.Common;
import co.id.ez.ezpay.msg.BillerMessage;
import co.id.ez.ezpay.msg.BillerResponse;
import co.id.ez.system.core.ex.ServiceException;
import co.id.ez.system.core.rc.RC;
import com.json.JSONObject;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 *
 * @author Lutfi
 */
public class TransactionModuleController {

    public void connectionCek() throws SQLException {
        String tSQL = "select @@version";
        DB.executeQuery(dbName, tSQL);
    }

    public LinkedList<JSONObject> getVoucherProductList(String pType) throws SQLException {
        String tSQL = "SELECT "
                + "prd.voucher_id, "
                + "prd.voucher_name, "
                + "prd.sale_price, "
                + "prov.provider_name, "
                + "IFNULL(pref.prefix,'') as prefix, " 
                + "IFNULL(prd.product_type, '') as product_type "
                + "FROM dp_vcr_product prd "
                + "INNER JOIN dp_vcr_provider prov ON prd.provider_id = prov.id "
                + "LEFT JOIN dp_vcr_provider_prefix pref on pref.provider_id = prov.id "
                + "WHERE prd.`status` = 1 AND prov.`status` = 1 AND prd.product_type = '"+pType+"' "
                + "ORDER BY prd.voucher_id, prd.voucher_name ASC";
        return DB.executeQuery(dbName, tSQL);
    }

    public void updateSuccessTransactionVoucher(JSONObject billerResponse) throws SQLException {
        JSONObject resp = billerResponse;
        QueryBuilder builder = new QueryBuilder("trx_vcr_sales", QueryType.UPDATE);
        builder.addEntryValue("paid_date", "NOW()", true);
        builder.addEntryValue("flag", "1", true);
        builder.addEntryValue("nominal", resp.get("nominal").toString());
        builder.addEntryValue("harga", resp.get("harga").toString());
        builder.addEntryValue("harga_jual", resp.get("harga_jual").toString());
        builder.addEntryValue("serial_number", resp.get("serialnumber").toString());
        builder.addEntryValue("info", resp.get("text").toString());
        builder.addEntryValue("no_faktur", "");

        builder.addWhereValue(
                new WhereField("refnumber", resp.get("refnum").toString(), Operator.EQUALS)
        );
        builder.addWhereValue(
                new WhereField("transaction_id", resp.get("trxid").toString(),
                        Operator.EQUALS, QueryConditional.AND)
        );

        DB.executeUpdate(dbName, builder);
    }

    public LinkedList<JSONObject> getPrepaidDenomList() throws SQLException {
        String tSQL = "SELECT denom, description, price_sale FROM `dp_pre_demon` WHERE `status` = 1 ORDER BY denom ASC";
        return DB.executeQuery(dbName, tSQL);
    }

    public void updateSuccessTransactionPrepaid(JSONObject billerResponse, BigDecimal denom) throws SQLException {
        JSONObject resp = billerResponse;
        QueryBuilder builder = new QueryBuilder("trx_pre_sales", QueryType.UPDATE);
        builder.addEntryValue("subscriber_id", resp.getString("idpel"));
        builder.addEntryValue("msn", resp.getString("MSN"));
        builder.addEntryValue("nominal", denom.toPlainString());
        builder.addEntryValue("admin", resp.get("admin").toString());
        builder.addEntryValue("total", resp.get("nominal").toString());
        builder.addEntryValue("harga_jual", resp.get("harga_jual").toString());
        builder.addEntryValue("token", resp.get("token").toString());
        builder.addEntryValue("info", resp.get("text").toString());
        builder.addEntryValue("no_faktur", "");
        builder.addEntryValue("subscriber_name", Common.escape(resp.getString("nama")));
        builder.addEntryValue("segmentation", resp.getString("tarifdaya"));
        builder.addEntryValue("kwh", resp.getString("jmlkwh"));
        builder.addEntryValue("paid_date", "NOW()", true);
        builder.addEntryValue("flag", "1", true);

        builder.addWhereValue(
                new WhereField("transaction_id", resp.get("trxid").toString(),
                        Operator.EQUALS, QueryConditional.AND)
        );
        DB.executeUpdate(dbName, builder);
    }

    public LinkedList<JSONObject> getPdamBillerList() throws SQLException {
        String tSQL = "SELECT biller, biller_name, distric FROM `dp_pam_biller` WHERE `status` = 1 "
                + "ORDER BY distric, biller_name ASC";
        return DB.executeQuery(dbName, tSQL);
    }

    public void updateSuccessTransactionPdam(JSONObject billerResponse) throws SQLException {
        JSONObject resp = billerResponse;
        QueryBuilder builder = new QueryBuilder("trx_pam_sales", QueryType.UPDATE);
        builder.addEntryValue("paid_date", "NOW()", true);
        builder.addEntryValue("flag", "1", true);
        builder.addEntryValue("subscriber_name", Common.escape(resp.getString("name")));
        builder.addEntryValue("total_bill_amount", resp.get("transamount").toString());
        builder.addEntryValue("total_admin_charge", resp.get("admincharges").toString());
        builder.addEntryValue("subscriber_segmentation", resp.getString("subsegmen"));
        builder.addEntryValue("info", Common.escape(resp.get("text").toString()));
        builder.addEntryValue("no_faktur", "");

        builder.addWhereValue(
                new WhereField("transaction_id", resp.get("trxid").toString(),
                        Operator.EQUALS, QueryConditional.AND)
        );

        DB.executeUpdate(dbName, builder);
    }
    
    public void updateSuccessTransactionPospaid(JSONObject billerResponse) throws SQLException {
        JSONObject resp = billerResponse;
        QueryBuilder builder = new QueryBuilder("trx_pos_sales", QueryType.UPDATE);
        builder.addEntryValue("paid_date", "NOW()", true);
        builder.addEntryValue("flag", "1", true);
        builder.addEntryValue("info1", Common.escape(resp.getString("info1")));
        builder.addEntryValue("info2", Common.escape(resp.getString("info2")));
        builder.addEntryValue("info", Common.escape(resp.get("text").toString()));
        builder.addEntryValue("no_faktur", "");

        builder.addWhereValue(
                new WhereField("transaction_id", resp.get("trxid").toString(),
                        Operator.EQUALS, QueryConditional.AND)
        );

        DB.executeUpdate(dbName, builder);
    }
    
    public LinkedList<JSONObject> getMultiPaymentProductList() throws SQLException {
        String tSQL = "SELECT biller, biller_name, "
                + "input_1_label, input_1_type, "
                + "input_2_label, input_2_type, "
                + "input_3_label, input_3_type, "
                + "details "
                + "FROM `dp_mp_biller` "
                + "WHERE `status` = 1 ORDER BY biller_name ASC";
        return DB.executeQuery(dbName, tSQL);
    }
    
    public JSONObject getMultiPaymentBiller(String biller) throws SQLException {
        String tSQL = "SELECT biller, biller_name, "
                + "input_1_label, input_1_type, "
                + "input_2_label, input_2_type, "
                + "input_3_label, input_3_type, "
                + "details "
                + "FROM `dp_mp_biller` "
                + "WHERE `biller` = '"+biller+"'";
        LinkedList<JSONObject> data = DB.executeQuery(dbName, tSQL);
        
        if(data != null && data.size() > 0){
            return data.getFirst();
        }
        
        return null;
    }

    public void updateSuccessTransactionMultiPayment(JSONObject billerResponse) throws SQLException {
        JSONObject resp = billerResponse;
        QueryBuilder builder = new QueryBuilder("trx_mp_sales", QueryType.UPDATE);

        builder.addEntryValue("paid_date", "NOW()", true);
        builder.addEntryValue("flag", "1", true);
        builder.addEntryValue("admin_charge", resp.get("admin").toString());
        builder.addEntryValue("total_bill_amount", resp.get("amount").toString());
        builder.addEntryValue("bill_amount", resp.get("jmltagihan").toString());
        builder.addEntryValue("transaction_data", resp.getJSONObject("data").toString());
        builder.addEntryValue("info", resp.get("text").toString());
        builder.addEntryValue("no_faktur", "");

        builder.addWhereValue(
                new WhereField("refnumber", resp.get("refnum").toString(), Operator.EQUALS)
        );
        builder.addWhereValue(
                new WhereField("transaction_id", resp.get("trxid").toString(),
                        Operator.EQUALS, QueryConditional.AND)
        );

        DB.executeUpdate(dbName, builder);
    }

    public void saveTransactionToTranmain(QueryBuilder builder) throws SQLException {
        DB.executeUpdate(dbName, builder);
    }

    public void updatePrePostRequest(String pTableName, BillerResponse billerResponse) throws SQLException {
        JSONObject resp = billerResponse.getPayload();
        QueryBuilder builder = new QueryBuilder(pTableName, QueryType.UPDATE);

        builder.addEntryValue("flag", "2");
        builder.addWhereValue(
                new WhereField("refnumber", resp.get("refnum").toString(), Operator.EQUALS)
        );
        builder.addWhereValue(
                new WhereField("transaction_id", resp.get("trxid").toString(),
                        Operator.EQUALS, QueryConditional.AND)
        );

        DB.executeUpdate(dbName, builder);
    }

    public JSONObject getTransactionData(String pTrxID, String pTableName) throws SQLException{
        QueryBuilder builder = new QueryBuilder(pTableName, QueryType.SELECT);
        builder.addEntry("*");
        builder.addWhereValue(new WhereField("transaction_id", pTrxID, Operator.EQUALS));
        
        LinkedList<JSONObject> data = DB.executeQuery(dbName, builder);
        
        if(data != null && data.size() > 0){
            return data.getFirst();
        }
        
        throw new ServiceException(RC.ERROR_INVALID_SWITCHING_REFNUM, "Invalid trx ID "+ pTrxID);
    }
    
    public void updateFailedTransaction(String pTableName, BillerMessage billerResponse) throws SQLException {
        updateFailedTransaction(pTableName, billerResponse, "3");
    }
    
    public void updateFailedTransaction(String pTableName, BillerMessage billerResponse, String pFlag) throws SQLException {
        JSONObject resp = billerResponse.getBodyRequest();
        QueryBuilder builder = new QueryBuilder(pTableName, QueryType.UPDATE);

        builder.addEntryValue("flag", pFlag);
        builder.addWhereValue(
                new WhereField("transaction_id", resp.get("trxid").toString(),
                        Operator.EQUALS, QueryConditional.AND)
        );

        DB.executeUpdate(dbName, builder);
    }
}
