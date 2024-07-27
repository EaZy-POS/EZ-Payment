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
import co.id.ez.gateway.message.postpaid.PLNPostPaidAdviceRequest;
import co.id.ez.gateway.message.postpaid.PLNPostPaidPaymentRequest;
import co.id.ez.gateway.message.postpaid.PLNPostpaidInquiryRequest;
import co.id.ez.gateway.resource.MessageHandler;
import static co.id.ez.gateway.resource.CommonHanlder.dbName;
import co.id.ez.gateway.util.enums.RequiredFields;
import co.id.ez.gateway.util.enums.TableName;
import co.id.ez.gateway.util.enums.tables.PospaidTable;
import co.id.ez.system.core.ex.ServiceException;
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
 * @author Lutfi
 */
@Path("")
public class PLNPostpaidPlugin extends MessageHandler {

    @Produces(MediaType.APPLICATION_JSON)
    @POST
    @Path("/api/postpaid/inq")
    public Response inquiry(String pBodyMessage, @Context HttpHeaders pHeaders) {
        return handleInquiryRequest(pBodyMessage, pHeaders, RequiredFields.POSPAID_INQUIRY, TableName.PLN_POSTPAID);
    }

    @Produces(MediaType.APPLICATION_JSON)
    @POST
    @Path("/api/postpaid/pay")
    public Response payment(String pBodyMessage, @Context HttpHeaders pHeaders) {
        return handlePaymentRequest(pBodyMessage, pHeaders, RequiredFields.POSPAID_PAYMENT, TableName.PLN_POSTPAID);
    }

    @Produces(MediaType.APPLICATION_JSON)
    @POST
    @Path("/api/postpaid/adv")
    public Response advice(String pBodyMessage, @Context HttpHeaders pHeaders) {
        return handleAdviceRequest(pBodyMessage, pHeaders, RequiredFields.POSPAID_ADVICE, TableName.PLN_POSTPAID);
    }

    @Override
    public BillerRequest constructBillerRequest(JSONObject request) {
        if (request.getString("command").equalsIgnoreCase("INQ")) {
            PLNPostpaidInquiryRequest inqRequest = 
                    new PLNPostpaidInquiryRequest(request.getString("command"), request.getString("modul"));
            inqRequest.setIdpel(request.getString("idpel"));
            inqRequest.setDetail(request.getBoolean("detail"));
            return inqRequest;
        }

        if (request.getString("command").equalsIgnoreCase("PAY")) {
            PLNPostPaidPaymentRequest inqRequest = 
                    new PLNPostPaidPaymentRequest(request.getString("command"), request.getString("modul"));
            inqRequest.setIdpel(request.getString("idpel"));
            inqRequest.setDetail(request.getBoolean("detail"));
            inqRequest.setTrxid(request.getString("trxid"));
            inqRequest.setAmount(request.get("amount").toString());
            return inqRequest;
        }

        if (request.getString("command").equalsIgnoreCase("ADV")) {
            PLNPostPaidAdviceRequest inqRequest = 
                    new PLNPostPaidAdviceRequest(request.getString("command"), request.getString("modul"));
            inqRequest.setIdpel(request.getString("idpel"));
            inqRequest.setAmount(request.get("amount").toString());
            inqRequest.setDetail(request.getBoolean("detail"));
            inqRequest.setTrxid(request.getString("trxid"));
            return inqRequest;
        }
        return null;
    }

    @Override
    public void validateTransactAmount(JSONObject request, LinkedList<JSONObject> pTranmainDB) {
        BigDecimal amountMitra = new BigDecimal(request.getDouble("amount"));
        BigDecimal amountTranmain = new BigDecimal(pTranmainDB.getFirst().getDouble("total_bill_amount"));

        if (amountMitra.compareTo(amountTranmain) != 0) {
            throw new ServiceException(RC.ERROR_INVALID_TRANSACTION_AMOUNT, "Transaction value is different from inquiry");
        }
    }

    @Override
    public void insertSuccessfullResponseToTranmain(JSONObject pRespBiller) {
        try {
            QueryBuilder tBuilder = new QueryBuilder(TableName.PLN_POSTPAID.get(), QueryType.INSERT);
            
            String tSubID = pRespBiller.getString("subid");
            String tSubName = escape(pRespBiller.getString("nama"));
            String tTotalPeriode = pRespBiller.getString("bulan");
            String tTrxID = pRespBiller.getString("trxid");
            String tRefnum = pRespBiller.getString("refnum");
            String tBillPeriode = pRespBiller.getString("blth");
            String tSegmen = pRespBiller.getString("tarif");
            BigInteger tBill = new BigInteger(pRespBiller.get("tagihan").toString());
            BigInteger tTotalAdminCharge = new BigInteger(pRespBiller.get("admin").toString());
            BigInteger tTransactAmount = new BigInteger(pRespBiller.get("total").toString());
            
            tBuilder.addEntryValue(PospaidTable.id.name(), "UUID()", true);
            tBuilder.addEntryValue(PospaidTable.transaction_date.name(), "NOW()", true);
            tBuilder.addEntryValue(PospaidTable.detail.name(), reqMap.isDetail() ? "1": "0");
            tBuilder.addEntryValue(PospaidTable.mitra_id.name(), reqMap.getClient_id());
            tBuilder.addEntryValue(PospaidTable.module_id.name(), reqMap.getModule_id());
            tBuilder.addEntryValue(PospaidTable.user_id.name(), reqMap.getUser_id());
            tBuilder.addEntryValue(PospaidTable.transaction_id.name(), tTrxID);
            tBuilder.addEntryValue(PospaidTable.flag.name(), "0");
            tBuilder.addEntryValue(PospaidTable.ref_number.name(), tRefnum);
            tBuilder.addEntryValue(PospaidTable.subscriber_id.name(), tSubID);
            tBuilder.addEntryValue(PospaidTable.subscriber_name.name(), tSubName);
            tBuilder.addEntryValue(PospaidTable.segmentation.name(), tSegmen);
            tBuilder.addEntryValue(PospaidTable.bill_status.name(), tTotalPeriode);
            tBuilder.addEntryValue(PospaidTable.total_admin_charge.name(), tTotalAdminCharge.toString());
            tBuilder.addEntryValue(PospaidTable.total_bill_amount.name(), tTransactAmount.toString());
            
            if(reqMap.isDetail()){
                for (int i = 0; i < 4; i++) {
                    
                    BigInteger tBillAmount = new BigInteger(pRespBiller.get("tagbln_"+ (i+1)).toString());
                    BigInteger tDendaAmount = new BigInteger(pRespBiller.get("penalty_"+ (i+1)).toString());
                    BigInteger tBillAdm = new BigInteger(pRespBiller.get("admin_"+ (i+1)).toString());
                    String tPeriode = pRespBiller.getString("bln_"+ (i+1));
                    String tMeter = pRespBiller.get("meter_"+ (i+1)).toString();
                    
                    tBuilder.addEntryValue(PospaidTable.valueOf("periode_"+ (i+1)).name(), tPeriode);
                    tBuilder.addEntryValue(PospaidTable.valueOf("bill_amount_"+ (i+1)).name(), tBillAmount.toString());
                    tBuilder.addEntryValue(PospaidTable.valueOf("bill_denda_"+ (i+1)).name(), tDendaAmount.toString());
                    tBuilder.addEntryValue(PospaidTable.valueOf("meter_"+ (i+1)).name(), tMeter);
                    tBuilder.addEntryValue(PospaidTable.valueOf("bill_adm_"+ (i+1)).name(), tBillAdm.toString());
                }
            }else{
                tBuilder.addEntryValue(PospaidTable.periode_1.name(), tBillPeriode);
                tBuilder.addEntryValue(PospaidTable.bill_amount_1.name(), tBill.toString());
                tBuilder.addEntryValue(PospaidTable.bill_denda_1.name(), "0");
                tBuilder.addEntryValue(PospaidTable.bill_adm_1.name(), "0");
            }
            
            DB.executeUpdate(dbName, tBuilder);
        } catch (SQLException ex) {
            throw new ServiceException(RC.ERROR_DATABASE, "Failed to insert tranmain data", ex);
        }
    }

    @Override
    public void updateSuccessfullResponseToTranmain(JSONObject pResponseBiller) {
        try {
            QueryBuilder tBuilder = new QueryBuilder(TableName.PLN_POSTPAID.get(), QueryType.UPDATE);
            tBuilder.addEntryValue(PospaidTable.paid_date.name(), "NOW()", true);
            tBuilder.addEntryValue(PospaidTable.info_text.name(), escape(pResponseBiller.getString("text")));
            tBuilder.addEntryValue(PospaidTable.flag.name(), "1");

            tBuilder.addWhereValue(new WhereField(PospaidTable.transaction_id.name(), pResponseBiller.getString("trxid"), Operator.EQUALS));
            tBuilder.addWhereValue(new WhereField(PospaidTable.mitra_id.name(), reqMap.getClient_id(), Operator.EQUALS));

            DB.executeUpdate(dbName, tBuilder);
        } catch (SQLException ex) {
            throw new ServiceException(RC.ERROR_DATABASE, "Failed to update tranmain data", ex);
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
            reqMap.setDetail(request.getBoolean("detail"));
        }
        
        return tTranmain;
    }

    @Override
    public BigDecimal getTransactAmount(JSONObject pRequest) {
        return new BigDecimal(pRequest.getDouble("amount"));
    }

}
