/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.gateway.resource.handler;

import co.id.ez.database.DB;
import co.id.ez.database.entity.WhereField;
import co.id.ez.database.enums.Operator;
import co.id.ez.database.query.QueryBuilder;
import co.id.ez.database.query.QueryConditional;
import co.id.ez.database.query.QueryType;
import co.id.ez.gateway.message.BillerRequest;
import co.id.ez.gateway.message.voucher.VoucherAdviceRequest;
import co.id.ez.gateway.message.voucher.VoucherInquiryRequest;
import co.id.ez.gateway.message.voucher.VoucherPaymentRequest;
import co.id.ez.gateway.resource.MessageHandler;
import co.id.ez.gateway.resource.MessageType;
import co.id.ez.gateway.util.enums.RequiredFields;
import co.id.ez.gateway.util.enums.TableName;
import co.id.ez.gateway.util.enums.tables.VoucherTable;
import co.id.ez.system.core.ex.ServiceException;
import co.id.ez.system.core.log.LogService;
import co.id.ez.system.core.rc.RC;
import com.json.JSONObject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 *
 * @author Lutfi
 */
@Path("")
public class VoucherPlugin extends MessageHandler {
    
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    @Path("/api/voucher/inq")
    public Response inquiry(String pBodyMessage, @Context HttpHeaders pHeaders) {
        return handleInquiryRequest(pBodyMessage, pHeaders, RequiredFields.VOUCHER_INQUIRY, TableName.VOUCHER);
    }
    
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    @Path("/api/voucher/pay")
    public Response payment(String pBodyMessage, @Context HttpHeaders pHeaders) {
        return handlePaymentRequest(pBodyMessage, pHeaders, RequiredFields.VOUCHER_PAYMENT, TableName.VOUCHER);
    }

    @Produces(MediaType.APPLICATION_JSON)
    @POST
    @Path("/api/voucher/adv")
    public Response advice(String pBodyMessage, @Context HttpHeaders pHeaders) {
        return handleAdviceRequest(pBodyMessage, pHeaders, RequiredFields.VOUCHER_ADVICE, TableName.VOUCHER);
    }

    @Override
    public BillerRequest constructBillerRequest(JSONObject request, MessageType pMsgType) {
        if(pMsgType == MessageType.INQUIRY){
            VoucherInquiryRequest inqRequest = new VoucherInquiryRequest();
            inqRequest.setTujuan(request.getString("tujuan"));
            inqRequest.setVoucherId(request.getString("voucherid"));
            return inqRequest;
        }
        
        if(pMsgType == MessageType.PAYMENT){
            VoucherPaymentRequest inqRequest = new VoucherPaymentRequest();
            inqRequest.setTujuan(request.getString("tujuan"));
            inqRequest.setVoucherId(request.getString("voucherid"));
            inqRequest.setTrxid(request.getString("trxid"));
            return inqRequest;
        }
        
        if(pMsgType == MessageType.ADVICE){
            VoucherAdviceRequest inqRequest = new VoucherAdviceRequest();
            inqRequest.setTujuan(request.getString("tujuan"));
            inqRequest.setVoucherId(request.getString("voucherid"));
            inqRequest.setTrxid(request.getString("trxid"));
            return inqRequest;
        }
        return null;
    }
    
    @Override
    public void validateTransactAmount(JSONObject request, LinkedList<JSONObject> pTranmainDB) {
        BigDecimal amountMitra = getTransactAmount(request);
        BigDecimal amountTranmain = new BigDecimal(pTranmainDB.getFirst().getDouble(VoucherTable.price.name()));
        
        if (amountMitra.compareTo(amountTranmain) != 0) {
            throw new ServiceException(RC.ERROR_INVALID_TRANSACTION_AMOUNT, "Transaction value is different from inquiry");
        }
    }

    @Override
    public void insertSuccessfullResponseToTranmain(JSONObject pRespBiller){
        try {
            QueryBuilder builder = new QueryBuilder(TableName.VOUCHER.get(), QueryType.INSERT);
            
            builder.addEntryValue(VoucherTable.id.name(), "UUID()", true);
            builder.addEntryValue(VoucherTable.transaction_date.name(), "NOW()", true);
            builder.addEntryValue(VoucherTable.dest_number.name(), pRespBiller.getString("nomor"));
            builder.addEntryValue(VoucherTable.voucher_amount.name(), pRespBiller.get("nominal").toString());
            builder.addEntryValue(VoucherTable.voucher_id.name(), pRespBiller.getString("voucherid"));
            builder.addEntryValue(VoucherTable.price.name(), pRespBiller.get("harga").toString());
            builder.addEntryValue(VoucherTable.sale_price.name(), pRespBiller.get("harga").toString());
            builder.addEntryValue(VoucherTable.transaction_id.name(), pRespBiller.getString("trxid"));
            builder.addEntryValue(VoucherTable.ref_number.name(), pRespBiller.getString("refnum"));
            builder.addEntryValue(VoucherTable.flag.name(), "0");
            builder.addEntryValue(VoucherTable.mitra_id.name(), reqMap.getClient_id());
            builder.addEntryValue(VoucherTable.module_id.name(), reqMap.getModule_id());
            builder.addEntryValue(VoucherTable.user_id.name(), reqMap.getUser_id());
            DB.executeUpdate(dbName, builder);
        } catch (SQLException ex) {
            throw new ServiceException(RC.ERROR_DATABASE, "Failed to insert tranmain data", ex);
        }
    }

    @Override
    public void updateSuccessfullResponseToTranmain(JSONObject pResponse) {
        try {
            QueryBuilder builder = new QueryBuilder(TableName.VOUCHER.get(), QueryType.UPDATE);
            builder.addEntryValue(VoucherTable.paid_date.name(), "NOW()", true);
            builder.addEntryValue(VoucherTable.flag.name(), "1");
            builder.addEntryValue(VoucherTable.serial_number.name(), pResponse.getString("serialnumber"));
            builder.addEntryValue(VoucherTable.info_text.name(), pResponse.getString("text"));
            builder.addEntryValue(VoucherTable.sale_price.name(), pResponse.get("harga_jual").toString());
            
            builder.addWhereValue(
                    new WhereField(
                            VoucherTable.transaction_id.name(), 
                            pResponse.getString("trxid"), 
                            Operator.EQUALS
                    )
            );
            builder.addWhereValue(
                    new WhereField(
                            VoucherTable.mitra_id.name(), 
                            reqMap.getClient_id(), 
                            Operator.EQUALS, 
                            QueryConditional.AND
                    )
            );
            
            DB.executeUpdate(dbName, builder);
        } catch (SQLException ex) {
            LogService.getInstance(this)
                    .dbError()
                    .withCause(ex)
                    .log("Failed updating data to tranmain."+ ex.getMessage(), true);
        }
        
    }

    @Override
    public BigDecimal getTransactAmount(JSONObject pRequest) {
        return new BigDecimal(pRequest.getDouble("harga"));
    }

    @Override
    public Object getMitraAmount(JSONObject request){
        return request.get("harga_jual").toString();
    }
    
    @Override
    public String getProduct(JSONObject pRequest) {
        return pRequest.get("voucherid").toString();
    }

}
