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
import co.id.ez.gateway.message.prepaid.PlnPrepaidAdviceRequest;
import co.id.ez.gateway.message.prepaid.PlnPrepaidInquiryRequest;
import co.id.ez.gateway.message.prepaid.PlnPrepaidPaymentRequest;
import co.id.ez.gateway.resource.MessageHandler;
import co.id.ez.gateway.resource.MessageType;
import co.id.ez.gateway.util.enums.RequiredFields;
import co.id.ez.gateway.util.enums.TableName;
import co.id.ez.gateway.util.enums.tables.PrepaidTable;
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
public class PLNPrepaidPlugin extends MessageHandler {

    @Produces(MediaType.APPLICATION_JSON)
    @POST
    @Path("/api/prepaid/inq")
    public Response inquiry(String pBodyMessage, @Context HttpHeaders pHeaders) {
        return handleInquiryRequest(pBodyMessage, pHeaders, RequiredFields.PREPAID_INQUIRY, TableName.PLN_PREPAID);
    }

    @Produces(MediaType.APPLICATION_JSON)
    @POST
    @Path("/api/prepaid/pay")
    public Response payment(String pBodyMessage, @Context HttpHeaders pHeaders) {
        return handlePaymentRequest(pBodyMessage, pHeaders, RequiredFields.PREPAID_PAYMENT, TableName.PLN_PREPAID);
    }

    @Produces(MediaType.APPLICATION_JSON)
    @POST
    @Path("/api/prepaid/adv")
    public Response advice(String pBodyMessage, @Context HttpHeaders pHeaders) {
        return handleAdviceRequest(pBodyMessage, pHeaders, RequiredFields.PREPAID_ADVICE, TableName.PLN_PREPAID);
    }

    @Override
    public BillerRequest constructBillerRequest(JSONObject request, MessageType pMsgType) {
        if (pMsgType == MessageType.INQUIRY) {
            PlnPrepaidInquiryRequest inqRequest = new PlnPrepaidInquiryRequest();
            inqRequest.setMSN(request.getString("MSN"));
            inqRequest.setNominal(request.getString("nominal"));
            return inqRequest;
        }

        if (pMsgType == MessageType.PAYMENT) {
            PlnPrepaidPaymentRequest inqRequest = new PlnPrepaidPaymentRequest();
            inqRequest.setMSN(request.getString("MSN"));
            inqRequest.setNominal(request.getString("nominal"));
            inqRequest.setTrxid(request.getString("trxid"));
            return inqRequest;
        }

        if (pMsgType == MessageType.ADVICE) {
            PlnPrepaidAdviceRequest inqRequest = new PlnPrepaidAdviceRequest();
            inqRequest.setMSN(request.getString("MSN"));
            inqRequest.setNominal(request.getString("nominal"));
            inqRequest.setTrxid(request.getString("trxid"));
            return inqRequest;
        }
        return null;
    }

    @Override
    public void validateTransactAmount(JSONObject request, LinkedList<JSONObject> pTranmainDB) {
        BigDecimal amountMitra = new BigDecimal(request.getDouble("nominal"));
        BigDecimal amountTranmain = new BigDecimal(pTranmainDB.getFirst().getDouble("nominal"));

        if (amountMitra.compareTo(amountTranmain) != 0) {
            throw new ServiceException(RC.ERROR_INVALID_TRANSACTION_AMOUNT, "Transaction value is different from inquiry");
        }
    }

    @Override
    public void insertSuccessfullResponseToTranmain(JSONObject pRespBiller) {
        try {
            QueryBuilder tBuilder = new QueryBuilder(TableName.PLN_PREPAID.get(), QueryType.INSERT);

            tBuilder.addEntryValue(PrepaidTable.id.name(), "UUID()", true);
            tBuilder.addEntryValue(PrepaidTable.transaction_date.name(), "NOW()", true);
            tBuilder.addEntryValue(PrepaidTable.mitra_id.name(), reqMap.getClient_id());
            tBuilder.addEntryValue(PrepaidTable.module_id.name(), reqMap.getModule_id());
            tBuilder.addEntryValue(PrepaidTable.user_id.name(), reqMap.getUser_id());
            tBuilder.addEntryValue(PrepaidTable.transaction_id.name(), pRespBiller.getString("trxid"));
            tBuilder.addEntryValue(PrepaidTable.subscriber_id.name(), pRespBiller.getString("msn"));
            tBuilder.addEntryValue(PrepaidTable.msn.name(), pRespBiller.getString("subid"));
            tBuilder.addEntryValue(PrepaidTable.subscriber_name.name(), escape(pRespBiller.getString("nama")));
            tBuilder.addEntryValue(PrepaidTable.segmentation.name(), pRespBiller.getString("tarif"));
            tBuilder.addEntryValue(PrepaidTable.distribution_code.name(), pRespBiller.has("distcode") 
                    ? pRespBiller.getString("distcode") : "");
            tBuilder.addEntryValue(PrepaidTable.unsold.name(), pRespBiller.get("unsold").toString());
            tBuilder.addEntryValue(PrepaidTable.unsold_denom.name(), pRespBiller.get("denomunsold").toString());
            tBuilder.addEntryValue(PrepaidTable.sub_unit.name(), pRespBiller.has("sunit") 
                    ? pRespBiller.getString("sunit") : "");
            tBuilder.addEntryValue(PrepaidTable.sub_phone.name(), pRespBiller.has("suphone") 
                    ? pRespBiller.getString("suphone") : "");
            tBuilder.addEntryValue(PrepaidTable.maximum_kwh.name(), pRespBiller.has("maxkwh") 
                    ? pRespBiller.getString("maxkwh") : "0");
            tBuilder.addEntryValue(PrepaidTable.nominal.name(), pRespBiller.has("nominal") 
                    ? pRespBiller.getString("nominal") : "0");
            tBuilder.addEntryValue(PrepaidTable.ref_number.name(), pRespBiller.getString("refnum"));
            tBuilder.addEntryValue(PrepaidTable.flag.name(), "0");
            tBuilder.addEntryValue(PrepaidTable.info_text.name(), escape(pRespBiller.getString("text")));

            DB.executeUpdate(dbName, tBuilder);
        } catch (SQLException ex) {
            throw new ServiceException(RC.ERROR_DATABASE, "Failed to insert tranmain data", ex);
        }
    }

    @Override
    public void updateSuccessfullResponseToTranmain(JSONObject pRespBiller) {
        try {
            QueryBuilder tBuilder = new QueryBuilder(TableName.PLN_PREPAID.get(), QueryType.UPDATE);
            tBuilder.addEntryValue(PrepaidTable.paid_date.name(), "NOW()", true);
            tBuilder.addEntryValue(PrepaidTable.flag.name(), "1");
            tBuilder.addEntryValue(PrepaidTable.token.name(), pRespBiller.getString("token"));
            tBuilder.addEntryValue(PrepaidTable.kwh_amount.name(), pRespBiller.getString("jmlkwh"));
            tBuilder.addEntryValue(PrepaidTable.info_text.name(), escape(pRespBiller.getString("text")));
            tBuilder.addEntryValue(PrepaidTable.ppn_amount.name(), pRespBiller.get("ppn").toString());
            tBuilder.addEntryValue(PrepaidTable.ppj_amount.name(), pRespBiller.get("ppj").toString());
            tBuilder.addEntryValue(PrepaidTable.sale_price.name(), pRespBiller.get("harga_jual").toString());
            tBuilder.addEntryValue(PrepaidTable.installments_amount.name(), pRespBiller.get("angsuran").toString());
            tBuilder.addEntryValue(PrepaidTable.stamp_amount.name(), pRespBiller.get("materai").toString());
            tBuilder.addEntryValue(PrepaidTable.token_amount.name(), pRespBiller.get("rptoken").toString());
            tBuilder.addEntryValue(PrepaidTable.total_admin_charge.name(), pRespBiller.get("admin").toString());
            tBuilder.addEntryValue(PrepaidTable.total_bill_amount.name(), pRespBiller.get("nominal").toString());

            tBuilder.addWhereValue(new WhereField(PrepaidTable.transaction_id.name(), pRespBiller.getString("trxid"), Operator.EQUALS));
            tBuilder.addWhereValue(new WhereField(PrepaidTable.mitra_id.name(), reqMap.getClient_id(), Operator.EQUALS));
            DB.executeUpdate(dbName, tBuilder);
        } catch (SQLException ex) {
            LogService.getInstance(this).dbError().withCause(ex).log("Failed updating data to tranmain."+ ex.getMessage(), true);
        }
    }

    @Override
    public BigDecimal getTransactAmount(JSONObject pRequest) {
        return new BigDecimal(pRequest.getDouble("harga_jual"));
    }

    @Override
    public Object getMitraAmount(JSONObject request){
        return request.get("harga_jual").toString();
    }
    
    @Override
    public String getProduct(JSONObject pRequest) {
        return pRequest.get("nominal").toString();
    }
}
