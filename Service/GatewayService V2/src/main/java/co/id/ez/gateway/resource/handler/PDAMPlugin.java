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
import co.id.ez.database.query.QueryType;
import co.id.ez.gateway.message.BillerRequest;
import co.id.ez.gateway.message.pdam.PdamAdviceRequest;
import co.id.ez.gateway.message.pdam.PdamInquiryRequest;
import co.id.ez.gateway.message.pdam.PdamPaymentRequest;
import co.id.ez.gateway.resource.MessageHandler;
import co.id.ez.gateway.resource.MessageType;
import co.id.ez.gateway.util.enums.RequiredFields;
import co.id.ez.gateway.util.enums.TableName;
import co.id.ez.gateway.util.enums.tables.PdamTable;
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
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 *
 * @author RCS
 */
@Path("")
public class PDAMPlugin extends MessageHandler{
    
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    @Path("/api/pdam/inq")
    public Response inquiry(String pBodyMessage, @Context HttpHeaders pHeaders) {
        return handleInquiryRequest(pBodyMessage, pHeaders, RequiredFields.PDAM_INQUIRY, TableName.PDAM);
    }

    @Produces(MediaType.APPLICATION_JSON)
    @POST
    @Path("/api/pdam/pay")
    public Response payment(String pBodyMessage, @Context HttpHeaders pHeaders) {
        return handlePaymentRequest(pBodyMessage, pHeaders, RequiredFields.PDAM_PAYMENT, TableName.PDAM);
    }

    @Produces(MediaType.APPLICATION_JSON)
    @POST
    @Path("/api/pdam/adv")
    public Response advice(String pBodyMessage, @Context HttpHeaders pHeaders) {
        return handleAdviceRequest(pBodyMessage, pHeaders, RequiredFields.PDAM_ADVICE, TableName.PDAM);
    }
    
    @Override
    public BillerRequest constructBillerRequest(JSONObject request, MessageType pMessageType) {
        if(pMessageType == MessageType.INQUIRY){
            PdamInquiryRequest inqRequest = new PdamInquiryRequest();
            inqRequest.setIdpel(request.getString("idpel"));
            inqRequest.setBiller(request.getString("biller"));
            inqRequest.setDetail(request.getBoolean("detail"));
            return inqRequest;
        }
        
        if(pMessageType == MessageType.PAYMENT){
            PdamPaymentRequest payRequest = new PdamPaymentRequest();
            payRequest.setIdpel(request.getString("idpel"));
            payRequest.setBiller(request.getString("biller"));
            payRequest.setDetail(request.getBoolean("detail"));
            payRequest.setTrxid(request.getString("trxid"));
            return payRequest;
        }
        
        if(pMessageType == MessageType.ADVICE){
            PdamAdviceRequest advRequest = new PdamAdviceRequest();
            advRequest.setIdpel(request.getString("idpel"));
            advRequest.setBiller(request.getString("biller"));
            advRequest.setDetail(request.getBoolean("detail"));
            advRequest.setTrxid(request.getString("trxid"));
            return advRequest;
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
    public void insertSuccessfullResponseToTranmain(JSONObject pRespBiller) {
        try {
            
            QueryBuilder tBuilder = new QueryBuilder(TableName.PDAM.get(), QueryType.INSERT);
            
            String tSubID = pRespBiller.getString("subid");
            String tSubName = escape(pRespBiller.getString("name"));
            String tTotalPeriode = pRespBiller.getString("totalperiod");
            String tTrxID = pRespBiller.getString("trxid");
            String tRefnum = pRespBiller.getString("refnum");
            String tBillPeriode = pRespBiller.getString("billperiod");
            BigInteger tTotalAdminCharge = new BigInteger(pRespBiller.get("admincharge").toString());
            BigInteger tTransactAmount = new BigInteger(pRespBiller.get("transamount").toString());
            
            tBuilder.addEntryValue(PdamTable.id.name(), "UUID()", true);
            tBuilder.addEntryValue(PdamTable.transaction_date.name(), "NOW()", true);
            tBuilder.addEntryValue(PdamTable.biller.name(), reqMap.getProduct());
            tBuilder.addEntryValue(PdamTable.detail.name(), reqMap.isDetail() ? "1": "0");
            tBuilder.addEntryValue(PdamTable.mitra_id.name(), reqMap.getClient_id());
            tBuilder.addEntryValue(PdamTable.module_id.name(), reqMap.getModule_id());
            tBuilder.addEntryValue(PdamTable.user_id.name(), reqMap.getUser_id());
            tBuilder.addEntryValue(PdamTable.transaction_id.name(), tTrxID);
            tBuilder.addEntryValue(PdamTable.flag.name(), "0");
            tBuilder.addEntryValue(PdamTable.ref_number.name(), tRefnum);
            tBuilder.addEntryValue(PdamTable.subscriber_id.name(), tSubID);
            tBuilder.addEntryValue(PdamTable.subscriber_name.name(), tSubName);
            tBuilder.addEntryValue(PdamTable.total_bill.name(), tTotalPeriode);
            tBuilder.addEntryValue(PdamTable.total_admin_charge.name(), tTotalAdminCharge.toString());
            tBuilder.addEntryValue(PdamTable.total_bill_amount.name(), tTransactAmount.toString());
            
            if(reqMap.isDetail()){
                for (int i = 0; i < 4; i++) {
                    
                    BigInteger tBillAmount = new BigInteger(pRespBiller.get("tagihan_"+ (i+1)).toString());
                    BigInteger tStamp = new BigInteger(pRespBiller.get("stamp_"+ (i+1)).toString());
                    BigInteger tPenalty = new BigInteger(pRespBiller.get("penalty_"+ (i+1)).toString());
                    BigInteger tDanameter = new BigInteger(pRespBiller.get("danameter_"+ (i+1)).toString());
                    BigInteger tVat = new BigInteger(pRespBiller.get("vat_"+ (i+1)).toString());
                    BigInteger tWaste = new BigInteger(pRespBiller.get("waste_"+ (i+1)).toString());
                    BigInteger tBillAdmPdam = new BigInteger(pRespBiller.get("admpdam_"+ (i+1)).toString());
                    BigInteger tInstalment = new BigInteger(pRespBiller.get("installment_"+ (i+1)).toString());
                    BigInteger tBillAdm = new BigInteger(pRespBiller.get("adm_"+ (i+1)).toString());
                    
                    String tPeriode = pRespBiller.getString("bln_"+ (i+1));
                    String tUsage = pRespBiller.get("mtruse_"+ (i+1)).toString();
                    String tStart = pRespBiller.get("mtrstart_"+ (i+1)).toString();
                    String tEnd = pRespBiller.get("mtrend_"+ (i+1)).toString();
                    
                    tBuilder.addEntryValue(PdamTable.valueOf("periode_"+ (i+1)).name(), tPeriode);
                    tBuilder.addEntryValue(PdamTable.valueOf("bill_amount_"+ (i+1)).name(), tBillAmount.toString());
                    tBuilder.addEntryValue(PdamTable.valueOf("bill_stamp_"+ (i+1)).name(), tStamp.toString());
                    tBuilder.addEntryValue(PdamTable.valueOf("bill_penalty_"+ (i+1)).name(), tPenalty.toString());
                    tBuilder.addEntryValue(PdamTable.valueOf("bill_danameter_"+ (i+1)).name(), tDanameter.toString());
                    tBuilder.addEntryValue(PdamTable.valueOf("bill_vat_"+ (i+1)).name(), tVat.toString());
                    tBuilder.addEntryValue(PdamTable.valueOf("bill_waste_"+ (i+1)).name(), tWaste.toString());
                    tBuilder.addEntryValue(PdamTable.valueOf("bill_installment_"+ (i+1)).name(), tInstalment.toString());
                    tBuilder.addEntryValue(PdamTable.valueOf("bill_adm_pdam_"+ (i+1)).name(), tBillAdmPdam.toString());
                    tBuilder.addEntryValue(PdamTable.valueOf("meter_usage_"+ (i+1)).name(), tUsage);
                    tBuilder.addEntryValue(PdamTable.valueOf("meter_start_"+ (i+1)).name(), tStart);
                    tBuilder.addEntryValue(PdamTable.valueOf("meter_end_"+ (i+1)).name(), tEnd);
                    tBuilder.addEntryValue(PdamTable.valueOf("bill_adm_"+ (i+1)).name(), tBillAdm.toString());
                }
            }else{
                tBuilder.addEntryValue(PdamTable.periode_1.name(), tBillPeriode);
                tBuilder.addEntryValue(PdamTable.bill_amount_1.name(), tTransactAmount.toString());
                tBuilder.addEntryValue(PdamTable.bill_stamp_1.name(), "0");
                tBuilder.addEntryValue(PdamTable.bill_penalty_1.name(), "0");
                tBuilder.addEntryValue(PdamTable.bill_danameter_1.name(), "0");
                tBuilder.addEntryValue(PdamTable.bill_vat_1.name(), "0");
                tBuilder.addEntryValue(PdamTable.bill_installment_1.name(), "0");
                tBuilder.addEntryValue(PdamTable.bill_waste_1.name(), "0");
                tBuilder.addEntryValue(PdamTable.bill_adm_pdam_1.name(), "0");
                tBuilder.addEntryValue(PdamTable.meter_usage_1.name(), "0");
                tBuilder.addEntryValue(PdamTable.meter_start_1.name(), "0");
                tBuilder.addEntryValue(PdamTable.meter_end_1.name(), "0");
                tBuilder.addEntryValue(PdamTable.bill_adm_1.name(), "0");
            }
            
            DB.executeUpdate(dbName, tBuilder);
        } catch (SQLException ex) {
            throw new ServiceException(RC.ERROR_DATABASE, "Failed to insert tranmain data", ex);
        }
    }

    @Override
    public void updateSuccessfullResponseToTranmain(JSONObject pResponseBiller) {
        try {
            QueryBuilder tBuilder = new QueryBuilder(TableName.PDAM.get(), QueryType.UPDATE);
            tBuilder.addEntryValue(PdamTable.paid_date.name(), "NOW()", true);
            tBuilder.addEntryValue(PdamTable.flag.name(), "1");
            tBuilder.addEntryValue(PdamTable.info_text.name(), escape(pResponseBiller.getString("text")));
            tBuilder.addEntryValue(PdamTable.subscriber_segmentation.name(), pResponseBiller.getString("subsegmen"));
            
            tBuilder.addWhereValue(new WhereField(PdamTable.transaction_id.name(), pResponseBiller.getString("trxid"), Operator.EQUALS));
            tBuilder.addWhereValue(new WhereField(PdamTable.mitra_id.name(), reqMap.getClient_id(), Operator.EQUALS));
            
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
            TableName pTranmainTable,
            MessageType pMsgType) {
        
        LinkedList<JSONObject> tTranmain =super.validateMessage(pHeaders, pBodyRequest, pRequiredField, pTranmainTable, pMsgType);
        
        JSONObject request = new JSONObject(pBodyRequest);
        
        if (pMsgType == MessageType.INQUIRY) {
            String biller = request.getString("biller");
            reqMap.setProduct(biller);
            reqMap.setDetail(request.getBoolean("detail"));
        }
        
        return tTranmain;
    }

    @Override
    public BigDecimal getTransactAmount(JSONObject pRequest) {
        return new BigDecimal(pRequest.getDouble("amount"));
    }

    @Override
    public String getProduct(JSONObject pRequest) {
        return pRequest.getString("biller");
    }
}
