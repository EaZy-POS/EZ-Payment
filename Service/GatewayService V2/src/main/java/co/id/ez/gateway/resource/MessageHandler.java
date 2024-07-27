/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.gateway.resource;

import co.id.ez.database.DB;
import co.id.ez.database.entity.WhereField;
import co.id.ez.database.enums.Operator;
import co.id.ez.database.query.QueryBuilder;
import co.id.ez.database.query.QueryConditional;
import co.id.ez.database.query.QueryType;
import co.id.ez.gateway.message.BillerRequest;
import static co.id.ez.gateway.resource.CommonHanlder.dbName;
import co.id.ez.gateway.util.enums.RequiredFields;
import co.id.ez.gateway.util.enums.TableName;
import co.id.ez.gateway.util.enums.tables.VoucherTable;
import co.id.ez.system.core.ex.ServiceException;
import co.id.ez.system.core.log.LogService;
import co.id.ez.system.core.rc.RC;
import com.json.JSONObject;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.UUID;

/**
 *
 * @author Lutfi
 */
public abstract class MessageHandler extends CommonHanlder {

    public Response handleInquiryRequest(
            String pBodyMessage, 
            @Context HttpHeaders pHeaders, 
            RequiredFields pRequiredField, 
            TableName pTranmain) {
        JSONObject tJsonRequest = new JSONObject();
        createCorelationID();
        try {
            String tRequest = "";
            String tUrlInfo = pBodyMessage;
            try {
                tRequest = URLDecoder
                        .decode(
                                tUrlInfo, 
                                StandardCharsets.UTF_8.toString()
                        ).replace("\n", "")
                        .replace("\t", " ")
                        .replace("\r", "");
                LogService.getInstance(this).stream()
                        .log("[" + pRequiredField.name() + " REQUEST ]:(" + corelation_id + "): " + tRequest);
            } catch (UnsupportedEncodingException ex) {
                throw new Exception("Error Encoding Request");
            }

            tJsonRequest = new JSONObject(tRequest);

            validateMessage(pHeaders, tRequest, pRequiredField, pTranmain);
            controller.logDownMessage(
                    tJsonRequest.getString("command"),
                    "REQ",
                    corelation_id,
                    reqMap,
                    tJsonRequest,
                    String.valueOf(getId()
                    )
            );
            
            BillerRequest inqReq = constructBillerRequest(tJsonRequest);

            String tResponseString = sendGetHttpRequest(inqReq, reqMap);
            RC tRC;
            JSONObject res;
            if (tRequest != null && !tResponseString.equals("") && tResponseString.startsWith("{")) {
                res = mergeResponseWithResponse(tJsonRequest, tResponseString);
                tRC = RC.parseResponseCodeString(res.getString("rc"));
                if (tRC == RC.SUCCESS) {
                    insertSuccessfullResponseToTranmain(res);
                }else{
                    LogService
                            .getInstance(this)
                            .error()
                            .log("(" + corelation_id + ") Get not success response from biller " + tResponseString);
                    res.put("rc", tRC.getResponseCodeString());
                }
            } else {
                throw new ServiceException(RC.ERROR_TRANSACTION_FAILED_FROM_VENDING,
                        "Get not success response from biller. response body: {" + tResponseString + "}");
            }

            Response tResp = constructHttpResponse(tRC, res);
            
            controller.logDownMessage(
                    tJsonRequest.getString("command"), 
                    "RES", 
                    corelation_id, 
                    reqMap, 
                    res, 
                    String.valueOf(getId())
            );
            LogService
                    .getInstance(this)
                    .stream()
                    .log("[" + pRequiredField.name() + " RESPONSE ]:(" + corelation_id + "): " + tResp.getEntity());
            return tResp;

        } catch (ServiceException ex) {
            LogService
                    .getInstance(this)
                    .error()
                    .withCause(ex)
                    .log("[ServiceException](" + corelation_id + "):  Error on Handle " + pRequiredField + " request. ", 
                            true
                    );
            RC tRC = ex.getRC();
            String tMsg = ex.getMessage();

            if (tRC.equals(RC.ERROR_DATABASE)) {
                tMsg = "Internal Error";
            }

            if (tRC.equals(RC.ERROR_TIMEOUT) || tRC.equals(RC.ERROR_OTHER)) {
                tMsg = "Internal Server Error";
            }

            if (tRC.equals(RC.ERROR_TRANSACTION_FAILED_FROM_VENDING)) {
                tMsg = "External Server Error";
            }

            JSONObject tResponse = constructErrorResponse(tJsonRequest, tRC.getResponseCodeString(), tMsg);
            Response tResp = constructHttpResponse(tRC, tResponse);
            controller.logDownMessage(
                    tJsonRequest.getString("command"), 
                    "RES", 
                    corelation_id, 
                    reqMap, 
                    tResponse, 
                    String.valueOf(getId())
            );
            LogService.getInstance(this)
                    .stream()
                    .log("[" + pRequiredField.name() + " RESPONSE ]: (" + corelation_id + "): " + tResp.getEntity());
            return tResp;
        } catch (Exception ex) {
            LogService.getInstance(this)
                    .error()
                    .withCause(ex)
                    .log("[Exception](" + corelation_id + "): Error on Handle " + pRequiredField + " request. ", true);
            RC tRC = RC.ERROR_OTHER;
            String tMsg = "Internal Server Error";

            JSONObject tResponse = constructErrorResponse(tJsonRequest, tRC.getResponseCodeString(), tMsg);
            Response tResp = constructHttpResponse(tRC, tResponse);
            controller.logDownMessage(
                    tJsonRequest.getString("command"), 
                    "RES", 
                    corelation_id, 
                    reqMap, 
                    tResponse, 
                    String.valueOf(getId())
            );
            LogService
                    .getInstance(this)
                    .stream()
                    .log("[" + pRequiredField.name() + " RESPONSE ]: (" + corelation_id + "): " + tResp.getEntity());
            return tResp;
        }
    }

    public Response handlePaymentRequest(
            String pBodyMessage, 
            @Context HttpHeaders pHeaders, 
            RequiredFields pRequiredField, 
            TableName pTranmain) {
        JSONObject tJsonRequest = new JSONObject();
        createCorelationID();
        boolean isDeductedDeposit = false;
        BillerRequest payReq = null;
        try {
            String tRequest = "";
            String tUrlInfo = pBodyMessage;
            try {
                tRequest = URLDecoder
                        .decode(
                                tUrlInfo, 
                                StandardCharsets.UTF_8.toString()
                        ).replace("\n", "").replace("\t", " ").replace("\r", "");
                LogService
                        .getInstance(this)
                        .stream()
                        .log("[" + pRequiredField.name() + " REQUEST ]: (" + corelation_id + "): " + tRequest);
            } catch (UnsupportedEncodingException ex) {
                throw new Exception("Error encoding Request");
            }

            tJsonRequest = new JSONObject(tRequest);

            LinkedList<JSONObject> tranmain = validateMessage(pHeaders, tRequest, pRequiredField, pTranmain);

            controller.logDownMessage(
                    tJsonRequest.getString("command"), 
                    "REQ", 
                    corelation_id, 
                    reqMap, 
                    tJsonRequest, 
                    String.valueOf(getId())
            );
            
            String tRefnum = tJsonRequest.has("refnum") 
                    ? tJsonRequest.getString("refnum")
                    : tranmain.getFirst().has("ref_number")
                    ? tranmain.getFirst().getString("ref_number")
                    : UUID.randomUUID().toString().replace("-", "").toUpperCase();
            
            payReq = constructBillerRequest(tJsonRequest);
            payReq.setRefnum(tRefnum);
            
            isDeductedDeposit = sendDeposit(tJsonRequest, payReq, "D");
            String tResponseString = sendGetHttpRequest(payReq, reqMap);
            RC tRC;
            JSONObject res;

            if (tRequest != null && !tResponseString.equals("") && tResponseString.startsWith("{")) {
                res = mergeResponseWithResponse(tJsonRequest, tResponseString);
                tRC = RC.parseResponseCodeString(res.getString("rc"));
                if (tRC == RC.SUCCESS || tRC == RC.SUCCESS_NOTCONFIRMED) {
                    updateSuccessfullResponseToTranmain(res);
                }else{
                    LogService
                            .getInstance(this)
                            .error()
                            .log("(" + corelation_id + ") Get not success response from biller " + tResponseString);
                    
                    if(tRC == RC.ERROR_OTHER || tRC == RC.ERROR_TIMEOUT || tRC == RC.ERROR_NO_PAYMENT){
                        tRC = RC.ERROR_TRANSACTION_PENDING_FROM_VENDING;
                        updatePendingFailedResponseToTranmain(payReq, "3", pTranmain);
                    } else {
                        updatePendingFailedResponseToTranmain(payReq, "2", pTranmain);
                        if (isDeductedDeposit) {
                            sendDeposit(tJsonRequest, payReq, "C", "Pembatalan");
                        }
                    }
                    
                    res.put("rc", tRC.getResponseCodeString());
                }
            } else {
                LogService
                    .getInstance(this)
                    .error()
                    .log("Get null response from biller. response body: {" + tResponseString + "}");
                tRC = RC.ERROR_TRANSACTION_PENDING_FROM_VENDING;
                updatePendingFailedResponseToTranmain(payReq, "3", pTranmain);
                res = new JSONObject(tJsonRequest.toString());
                res.put("rc", tRC.getResponseCodeString());
            }

            Response tResp = constructHttpResponse(tRC, res);
            
            controller.logDownMessage(
                    tJsonRequest.getString("command"), 
                    "RES", 
                    corelation_id, 
                    reqMap, 
                    res, 
                    String.valueOf(getId())
            );
            LogService
                    .getInstance(this)
                    .stream()
                    .log("[" + pRequiredField.name() + " RESPONSE ]:(" + corelation_id + "): " + tResp.getEntity());
            return tResp;

        } catch (ServiceException ex) {
            LogService
                    .getInstance(this)
                    .error()
                    .withCause(ex)
                    .log("[ServiceException](" + corelation_id + "): Error on Handle " + pRequiredField + " request. ", 
                            true
                    );
            RC tRC = ex.getRC();
            String tMsg = ex.getMessage();

            if (tRC.equals(RC.ERROR_DATABASE)) {
                tMsg = "Internal Error";
            }

            if (tRC.equals(RC.ERROR_TIMEOUT) || tRC.equals(RC.ERROR_OTHER)) {
                tMsg = "Internal Server Error";
            }

            if (tRC.equals(RC.ERROR_TRANSACTION_FAILED_FROM_VENDING)) {
                tMsg = "External Server Error";
            }
            
            if(isDeductedDeposit){
                sendDeposit(tJsonRequest, payReq, "C", "Pembatalan");
            }

            JSONObject tResponse = constructErrorResponse(tJsonRequest, tRC.getResponseCodeString(), tMsg);
            Response tResp = constructHttpResponse(tRC, tResponse);
            controller.logDownMessage(
                    tJsonRequest.getString("command"), 
                    "RES", 
                    corelation_id, 
                    reqMap, 
                    tResponse, 
                    String.valueOf(getId())
            );
            LogService
                    .getInstance(this)
                    .stream()
                    .log("[" + pRequiredField.name() + " RESPONSE ]: (" + corelation_id + "): " + tResp.getEntity());
            return tResp;
        } catch (Exception ex) {
            LogService
                    .getInstance(this)
                    .error()
                    .withCause(ex)
                    .log("[Exception](" + corelation_id + "): Error on Handle " + pRequiredField + " request. ", true);

            RC tRC = RC.ERROR_OTHER;
            String tMsg = "Internal Server Error";
            
            if(isDeductedDeposit){
                sendDeposit(tJsonRequest, payReq, "C", "Pembatalan");
            }

            JSONObject tResponse = constructErrorResponse(tJsonRequest, tRC.getResponseCodeString(), tMsg);
            Response tResp = constructHttpResponse(tRC, tResponse);
            controller.logDownMessage(
                    tJsonRequest.getString("command"), 
                    "RES", 
                    corelation_id, 
                    reqMap, 
                    tResponse, 
                    String.valueOf(getId())
            );
            LogService
                    .getInstance(this)
                    .stream()
                    .log("[" + pRequiredField.name() + " RESPONSE ]: (" + corelation_id + "): " + tResp.getEntity());
            return tResp;
        }
    }

    public Response handleAdviceRequest(
            String pBodyMessage, 
            @Context HttpHeaders pHeaders, 
            RequiredFields pRequiredField, 
            TableName pTranmain) {
        JSONObject tJsonRequest = new JSONObject();
        createCorelationID();
        BillerRequest advReq;
        
        try {
            String tRequest = "";
            String tUrlInfo = pBodyMessage;
            try {
                tRequest = URLDecoder.decode(
                        tUrlInfo, 
                        StandardCharsets.UTF_8.toString()
                ).replace("\n", "").replace("\t", " ").replace("\r", "");
                LogService
                        .getInstance(this)
                        .stream()
                        .log("[" + pRequiredField.name() + " REQUEST ]: (" + corelation_id + "): " + tRequest);
            } catch (UnsupportedEncodingException ex) {
                throw new Exception("Error encoding Request");
            }

            tJsonRequest = new JSONObject(tRequest);

            LinkedList<JSONObject> tTranmain = validateMessage(pHeaders, tRequest, pRequiredField, pTranmain);
            controller.logDownMessage(
                    tJsonRequest.getString("command"), 
                    "REQ", 
                    corelation_id, 
                    reqMap, 
                    tJsonRequest, 
                    String.valueOf(getId())
            );

            String tRefnum = tJsonRequest.has("refnum") 
                    ? tJsonRequest.getString("refnum")
                    : tTranmain.getFirst().has("ref_number")
                    ? tTranmain.getFirst().getString("ref_number")
                    : UUID.randomUUID().toString().replace("-", "").toUpperCase();
            
            advReq = constructBillerRequest(tJsonRequest);
            advReq.setRefnum(tRefnum);
            
            JSONObject res;
            
            if (tTranmain.getFirst().getInt("flag") == 0) {
                throw new ServiceException(RC.ERROR_NO_PAYMENT, "Payment transaction not found");
            }
            
            if (tTranmain.getFirst().getInt("flag") == 2) {
                throw new ServiceException(RC.ERROR_TRANSACTION_NOT_FOUND, "Transaction status failed");
            }
            
            if (tTranmain.getFirst().getInt("flag") == 4) {
                throw new ServiceException(RC.ERROR_REVERSAL_HAD_BEEN_DONE, "Transaction has been canceled");
            }
            
            String tResponseString = sendGetHttpRequest(advReq, reqMap);
            RC tRC;
            

            if (tRequest != null && !tResponseString.equals("") && tResponseString.startsWith("{")) {
                res = mergeResponseWithResponse(tJsonRequest, tResponseString);
                tRC = RC.parseResponseCodeString(res.getString("rc"));
                if (tRC == RC.SUCCESS || tRC == RC.SUCCESS_NOTCONFIRMED) {
                    if (tTranmain.getFirst().getInt("flag") == 3) {
                        updateSuccessfullResponseToTranmain(res);
                    }
                }else{
                    LogService
                            .getInstance(this)
                            .error()
                            .log("(" + corelation_id + ") Get not success response from biller " + tResponseString);
                    
                    if(tRC == RC.ERROR_OTHER || tRC == RC.ERROR_TIMEOUT){
                        tRC = RC.ERROR_TRANSACTION_PENDING_FROM_VENDING;
                    }
                    
                    if(tRC == RC.ERROR_NO_PAYMENT || tRC == RC.ERROR_INVALID_SWITCHER_REF_NUMBER){
                        updatePendingFailedResponseToTranmain(advReq, "4", pTranmain);
                        sendDeposit(tJsonRequest, advReq, "C", "Pembatalan");
                    }
                    
                    res.put("rc", tRC.getResponseCodeString());
                }
            } else {
                throw new ServiceException(RC.ERROR_TRANSACTION_FAILED_FROM_VENDING,
                        "Get not success response from biller. response body: {" + tResponseString + "}");
            }

            Response tResp = constructHttpResponse(tRC, res);
            
            controller.logDownMessage(
                    tJsonRequest.getString("command"), 
                    "RES", 
                    corelation_id, 
                    reqMap, 
                    res, 
                    String.valueOf(getId())
            );
            LogService
                    .getInstance(this)
                    .stream()
                    .log("[" + pRequiredField.name() + " RESPONSE ]:(" + corelation_id + "): " + tResp.getEntity());
            return tResp;

        } catch (ServiceException ex) {
            LogService
                    .getInstance(this)
                    .error()
                    .withCause(ex)
                    .log(
                            "[ServiceException](" + corelation_id + "): Error on Handle " + pRequiredField + " request. ", 
                            true
                    );
            RC tRC = ex.getRC();
            String tMsg = ex.getMessage();
            
            if (tRC.equals(RC.ERROR_DATABASE)) {
                tMsg = "Internal Error";
            }

            if (tRC.equals(RC.ERROR_TIMEOUT) || tRC.equals(RC.ERROR_OTHER)) {
                tMsg = "Internal Server Error";
            }

            if (tRC.equals(RC.ERROR_TRANSACTION_FAILED_FROM_VENDING)) {
                tMsg = "External Server Error";
            }

            JSONObject tResponse = constructErrorResponse(tJsonRequest, tRC.getResponseCodeString(), tMsg);
            Response tResp = constructHttpResponse(tRC, tResponse);
            controller.logDownMessage(
                    tJsonRequest.getString("command"), 
                    "RES", 
                    corelation_id, 
                    reqMap, 
                    tResponse, 
                    String.valueOf(getId())
            );
            LogService
                    .getInstance(this)
                    .stream()
                    .log("[" + pRequiredField.name() + " RESPONSE ]: (" + corelation_id + "): " + tResp.getEntity());
            return tResp;
        } catch (Exception ex) {
            LogService
                    .getInstance(this)
                    .error()
                    .withCause(ex)
                    .log(
                            "[Exception](" + corelation_id + "): Error on Handle " + pRequiredField + " request. ", 
                            true
                    );

            RC tRC = RC.ERROR_OTHER;
            String tMsg = "Internal Server Error";
            
            JSONObject tResponse = constructErrorResponse(tJsonRequest, tRC.getResponseCodeString(), tMsg);
            Response tResp = constructHttpResponse(tRC, tResponse);
            controller.logDownMessage(
                    tJsonRequest.getString("command"), 
                    "RES", 
                    corelation_id, 
                    reqMap, 
                    tResponse, 
                    String.valueOf(getId())
            );
            LogService
                    .getInstance(this)
                    .stream()
                    .log("[" + pRequiredField.name() + " RESPONSE ]: (" + corelation_id + "): " + tResp.getEntity());
            return tResp;
        }
    }

    private Response constructHttpResponse(RC responseCode, JSONObject pResponse) {
        Response.Status tStatus;
        JSONObject tResponse;

        switch (responseCode) {
            case SUCCESS:
            case SUCCESS_NOTCONFIRMED:
                tStatus = Response.Status.OK;
                tResponse = pResponse;
                break;

            case ERROR_TIMEOUT:
                tStatus = Response.Status.REQUEST_TIMEOUT;
                tResponse = new JSONObject().put("rcm", "Time Out");
                break;

            case ERROR_OTHER:
                tStatus = Response.Status.INTERNAL_SERVER_ERROR;
                tResponse = new JSONObject().put("rcm", "General Error");
                break;

            case ERROR_DATABASE:
                tStatus = Response.Status.INTERNAL_SERVER_ERROR;
                tResponse = new JSONObject().put("rcm", "Internal Server Error");
                break;

            case ERROR_TRANSACTION_FAILED_FROM_VENDING:
            case ERROR_INVALID_PARAMETER:
                tStatus = Response.Status.GATEWAY_TIMEOUT;
                tResponse = new JSONObject().put("rcm", "External Server Error");
                break;

            case ERROR_UNKNOWN_CERTIFICATE:
                tStatus = Response.Status.UNAUTHORIZED;
                tResponse = new JSONObject().put("rcm", pResponse.getString("rcm"));
                break;

            case ERROR_INVALID_MESSAGE:
            case ERROR_INVALID_KEY:
            case ERROR_INVALID_ACCESS_TIME:
            case ERROR_PRODUCT_NOT_AVAILABLE:
            case ERROR_UNREGISTERED_PARTNER_CENTRAL:
            case ERROR_BLOCKED_TERMINAL:
            case ERROR_NEED_TO_SIGN_ON:
                tStatus = Response.Status.BAD_REQUEST;
                tResponse = new JSONObject().put("rcm", pResponse.getString("rcm"));
                break;
            case ERROR_TRANSACTION_PENDING_FROM_VENDING:
                tStatus = Response.Status.PAYMENT_REQUIRED;
                tResponse = new JSONObject(pResponse.toString())
                        .put("rcm", "Pending transaction status, "
                        + "please check the status periodically "
                        + "or contact the helpdesk!");
                break;
            default:
                tStatus = Response.Status.NOT_FOUND;
                tResponse = pResponse;
                break;
        }

        tResponse.put("rc", tStatus.getStatusCode() + responseCode.getResponseCodeString());
        return Response
                .status(tStatus)
                .entity(tResponse.toString())
                .type(MediaType.APPLICATION_JSON)
                .header("correlation_id", corelation_id)
                .build();

    }

    private void createCorelationID() {
        corelation_id = "id_" + UUID.randomUUID().toString().replace("-", "").substring(0, 15);
    }
    
    public void updatePendingFailedResponseToTranmain(BillerRequest pBillerRequest, String pFlag, TableName pTranmain) {
        try {
            QueryBuilder builder = new QueryBuilder(pTranmain.get(), QueryType.UPDATE);
            builder.addEntryValue(VoucherTable.paid_date.name(), "NOW()", true);
            builder.addEntryValue(VoucherTable.flag.name(), pFlag);
            
            builder.addWhereValue(
                    new WhereField(
                            VoucherTable.transaction_id.name(), 
                            pBillerRequest.getTrxid(), 
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
                    .dbError().withCause(ex)
                    .log("Failed updating data to tranmain."+ ex.getMessage(), true);
        }
    }
}
