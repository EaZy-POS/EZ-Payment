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
import co.id.ez.gateway.message.mp.MPAdviceRequest;
import co.id.ez.gateway.message.mp.MPInquiryRequest;
import co.id.ez.gateway.message.mp.MPPaymentRequest;
import co.id.ez.gateway.resource.MessageHandler;
import co.id.ez.gateway.util.enums.RequiredFields;
import co.id.ez.gateway.util.enums.TableName;
import co.id.ez.gateway.util.enums.tables.MultiPaymentTable;
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
public class MPPlugin extends MessageHandler{

    @Produces(MediaType.APPLICATION_JSON)
    @POST
    @Path("/api/mp/inq")
    public Response inquiry(String pBodyMessage, @Context HttpHeaders pHeaders) {
        return handleInquiryRequest(pBodyMessage, pHeaders, RequiredFields.MP_INQUIRY, TableName.MUTLI_PAYMENT);
    }
    
    
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    @Path("/api/mp/pay")
    public Response payment(String pBodyMessage, @Context HttpHeaders pHeaders) {
        return handlePaymentRequest(pBodyMessage, pHeaders, RequiredFields.MP_PAYMENT, TableName.MUTLI_PAYMENT);
    }

    @Produces(MediaType.APPLICATION_JSON)
    @POST
    @Path("/api/mp/adv")
    public Response advice(String pBodyMessage, @Context HttpHeaders pHeaders) {
        return handleAdviceRequest(pBodyMessage, pHeaders, RequiredFields.MP_ADVICE, TableName.MUTLI_PAYMENT);
    }

    @Override
    public BillerRequest constructBillerRequest(JSONObject request) {
        if(request.getString("command").equalsIgnoreCase("INQ")){
            MPInquiryRequest inqRequest = new MPInquiryRequest(request.getString("command"), request.getString("modul"));
            inqRequest.setInput1(request.getString("input1"));
            inqRequest.setInput2(request.has("input2") ? request.getString("input2") : null);
            inqRequest.setInput3(request.has("input3") ? request.getString("input3") : null);
            inqRequest.setBiller(request.getString("biller"));
            return inqRequest;
        }
        
        if(request.getString("command").equalsIgnoreCase("PAY")){
            MPPaymentRequest inqRequest = new MPPaymentRequest(request.getString("command"), request.getString("modul"));
            inqRequest.setInput1(request.getString("input1"));
            inqRequest.setBiller(request.getString("biller"));
            inqRequest.setInput2(request.has("input2") ? request.getString("input2") : null);
            inqRequest.setInput3(request.has("input3") ? request.getString("input3") : null);
            inqRequest.setTrxid(request.getString("trxid"));
            inqRequest.setAmount(request.get("amount").toString());
            return inqRequest;
        }
        
        if(request.getString("command").equalsIgnoreCase("ADV")){
            MPAdviceRequest inqRequest = new MPAdviceRequest(request.getString("command"), request.getString("modul"));
            inqRequest.setInput1(request.getString("input1"));
            inqRequest.setInput2(request.has("input2") ? request.getString("input2") : null);
            inqRequest.setInput3(request.has("input3") ? request.getString("input3") : null);
            inqRequest.setBiller(request.getString("biller"));
            inqRequest.setTrxid(request.getString("trxid"));
            inqRequest.setAmount(request.get("amount").toString());
            return inqRequest;
        }
        return null;
    }
    
    @Override
    public void validateTransactAmount(JSONObject request, LinkedList<JSONObject> pTranmainDB) {
        BigDecimal amountMitra = getTransactAmount(request);
        BigDecimal amountTranmain = new BigDecimal(pTranmainDB.getFirst().getDouble("total_bill_amount"));
        
        if (amountMitra.compareTo(amountTranmain) != 0) {
            throw new ServiceException(RC.ERROR_INVALID_TRANSACTION_AMOUNT, "Transaction value is different from inquiry");
        }
    }

    @Override
    public void insertSuccessfullResponseToTranmain(JSONObject pRespBiller){
        try {
            QueryBuilder tBuilder = new QueryBuilder(TableName.MUTLI_PAYMENT.get(), QueryType.INSERT);
            tBuilder.addEntryValue(MultiPaymentTable.id.name(), "UUID()", true);
            tBuilder.addEntryValue(MultiPaymentTable.transaction_date.name(), "NOW()", true);
            tBuilder.addEntryValue(MultiPaymentTable.admin_charge.name(), pRespBiller.get("admin").toString());
            tBuilder.addEntryValue(MultiPaymentTable.bill_amount.name(), pRespBiller.get("jmltagihan").toString());
            tBuilder.addEntryValue(MultiPaymentTable.total_bill_amount.name(), pRespBiller.get("totaltag").toString());
            tBuilder.addEntryValue(MultiPaymentTable.transaction_id.name(), pRespBiller.getString("trxid"));
            tBuilder.addEntryValue(MultiPaymentTable.input_id_1.name(), pRespBiller.getString("input1"));
            tBuilder.addEntryValue(MultiPaymentTable.input_id_2.name(), pRespBiller.has("input2") ? pRespBiller.getString("input2") : "");
            tBuilder.addEntryValue(MultiPaymentTable.input_id_3.name(), pRespBiller.has("input3") ? pRespBiller.getString("input3") : "");
            tBuilder.addEntryValue(MultiPaymentTable.ref_number.name(), pRespBiller.getString("refnum"));
            tBuilder.addEntryValue(MultiPaymentTable.flag.name(), "0");
            tBuilder.addEntryValue(MultiPaymentTable.transaction_data.name(), escape(pRespBiller.getJSONObject("data").toString()));
            tBuilder.addEntryValue(MultiPaymentTable.biller.name(), reqMap.getBiller());
            tBuilder.addEntryValue(MultiPaymentTable.mitra_id.name(), reqMap.getClient_id());
            tBuilder.addEntryValue(MultiPaymentTable.module_id.name(), reqMap.getModule_id());
            tBuilder.addEntryValue(MultiPaymentTable.user_id.name(), reqMap.getUser_id());
            tBuilder.addEntryValue(MultiPaymentTable.info_text.name(), escape(pRespBiller.getString("text")));
            
            DB.executeUpdate(dbName, tBuilder);
        } catch (SQLException ex) {
            throw new ServiceException(RC.ERROR_DATABASE, "Failed to insert tranmain data", ex);
        }
    }

    @Override
    public void updateSuccessfullResponseToTranmain(JSONObject pRespBiller) {
        try {
            QueryBuilder tBuilder = new QueryBuilder(TableName.MUTLI_PAYMENT.get(), QueryType.UPDATE);
            tBuilder.addEntryValue(MultiPaymentTable.paid_date.name(), "NOW()", true);
            tBuilder.addEntryValue(MultiPaymentTable.flag.name(), "1");
            tBuilder.addEntryValue(MultiPaymentTable.info_text.name(), escape(pRespBiller.getString("text")));
            
            tBuilder.addWhereValue(new WhereField(MultiPaymentTable.transaction_id.name(), pRespBiller.getString("trxid"), Operator.EQUALS));
            tBuilder.addWhereValue(new WhereField(MultiPaymentTable.mitra_id.name(), reqMap.getClient_id(), Operator.EQUALS, QueryConditional.AND));
            
            DB.executeUpdate(dbName, tBuilder);
        } catch (SQLException ex) {
            LogService.getInstance(this).dbError().withCause(ex).log("Failed updating data to tranmain."+ ex.getMessage(), true);
        }
    }

    
    @Override
    public LinkedList<JSONObject> validateMessage(
            @Context HttpHeaders pHeaders, 
            String pBodyRequest, 
            RequiredFields pRequiredField, 
            TableName pTranmainTable) {
        
        LinkedList<JSONObject> tTranmain =super.validateMessage(pHeaders, pBodyRequest, pRequiredField, pTranmainTable);
        
        JSONObject request = new JSONObject(pBodyRequest);
        
        if (request.getString("command").equals("INQ")) {
            String biller = request.getString("biller");
            reqMap.setBiller(biller);
        }
        
        return tTranmain;
    }

    @Override
    public BigDecimal getTransactAmount(JSONObject pRequest) {
        return new BigDecimal(pRequest.getDouble("amount"));
    }

}
