/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.deposit.resource;

import co.id.ez.deposit.data.Repository;
import co.id.ez.deposit.enums.RequiredFields;
import co.id.ez.system.core.ex.ServiceException;
import co.id.ez.system.core.log.LogService;
import co.id.ez.system.core.rc.RC;
import com.json.JSONObject;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author Lutfi
 */
public abstract class DepositHandler {
    
    public static String dbName = "dep-service";
    protected String corelation_id;
    private static BigInteger SEQ_NUMBER = BigInteger.ZERO;

    public abstract JSONObject executeRequest(JSONObject pRequest, String pAccount);
    
    public Response handleRequest(String pBodyMessage, RequiredFields pReqField) {
        JSONObject tJsonRequest = new JSONObject();
        createCorelationID();
        try {
            String tRequest = "";
            String tUrlInfo = pBodyMessage;
            try {
                tRequest = URLDecoder.decode(tUrlInfo, StandardCharsets.UTF_8.toString()).replace("\n", "").replace("\t", " ").replace("\r", "");
                LogService.getInstance(this).stream().log("[" + pReqField.name() + " REQUEST ]:(" + corelation_id + "): " + tRequest);
            } catch (UnsupportedEncodingException ex) {
                throw new Exception("Error Encoding Request");
            }

            tJsonRequest = new JSONObject(tRequest);
            validateMessage(tJsonRequest, pReqField);
            
            
            String tAccount = null;
            if (pReqField != RequiredFields.CREATE_ACCOUNT) {
                tAccount = Repository.getDepAccount(tJsonRequest);
            }
            
            JSONObject tResponse = executeRequest(tJsonRequest, tAccount);
            
            Response resp = constructHttpResponse(tResponse);
            LogService.getInstance(this).stream()
                    .log("[" + pReqField.name() + " RESPONSE ]:(" + corelation_id + "): " + resp.getEntity());
            return resp;
        } catch (ServiceException ex) {
            LogService.getInstance(this).error().withCause(ex)
                    .log("[ServiceException](" + corelation_id + "):  Error on Handle " + RequiredFields.JOURNAL_DEPOSIT.name() + " request. ", true);
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
            
            tJsonRequest.put("rc", tRC.getResponseCodeString());
            tJsonRequest.put("rcm", tMsg);
            
            Response resp = constructHttpResponse(tJsonRequest);
            LogService.getInstance(this).stream()
                    .log("[" + pReqField.name() + " RESPONSE ]:(" + corelation_id + "): " + resp.getEntity());
            return resp;
        } catch (Exception ex) {
            LogService.getInstance(this).error().withCause(ex)
                    .log("[Exception](" + corelation_id + "): Error on Handle " + RequiredFields.JOURNAL_DEPOSIT.name() + " request. ", true);
            RC tRC = RC.ERROR_OTHER;
            String tMsg = "Internal Server Error";

            tJsonRequest.put("rc", tRC.getResponseCodeString());
            tJsonRequest.put("rcm", tMsg);
            
            Response resp = constructHttpResponse(tJsonRequest);
            LogService.getInstance(this).stream()
                    .log("[" + pReqField.name() + " RESPONSE ]:(" + corelation_id + "): " + resp.getEntity());
            return resp;
        }
    }

    private void validateMessage(JSONObject pRequest, RequiredFields pRequiredField) {
        LogService.getInstance(this).trace().log("(" + corelation_id + ") Before validate mandatory field");
        List<String> tList = new ArrayList<>();
        
        pRequiredField.getFields().stream().filter(field -> (!pRequest.has(field))).forEachOrdered(field -> {
            tList.add(field);
        });
        
        if(tList.size()>0){
            throw new ServiceException(RC.ERROR_INVALID_MESSAGE, "Invalid mandatory field. " + tList.toString() );
        }
        
        LogService.getInstance(this).trace().log("(" + corelation_id + ") After validate mandatory field (success)");
    }
    
    private Response constructHttpResponse(JSONObject pResponse) {
        Response.Status tStatus;
        JSONObject tResponse;
        RC responseCode = RC.parseResponseCodeString(pResponse.getString("rc"));

        switch (responseCode) {
            case SUCCESS:
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
                tStatus = Response.Status.BAD_REQUEST;
                tResponse = new JSONObject().put("rcm", pResponse.getString("rcm"));
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
    
    public synchronized BigInteger getSequnceNumber(){
        SEQ_NUMBER = SEQ_NUMBER.add(BigInteger.ONE);
        if(SEQ_NUMBER.compareTo(new BigInteger("999")) > 0){
            SEQ_NUMBER = BigInteger.ONE;
        }
        
        return SEQ_NUMBER;
    }
    
    public String generateAccount(){
        SimpleDateFormat format = new SimpleDateFormat("yyMMddHHmmss");
        String account = shuffleString(format.format(new Date()));
        String tSeqNumber = getSequnceNumber().toString();
        
        while (tSeqNumber.length() < 3) {            
            tSeqNumber = "0" + tSeqNumber;
        }
        
        return account.concat(tSeqNumber);
        
    }
    
    public String shuffleString(String input) { 
        List<Character> characters = new ArrayList<>(); 
        for (char c : input.toCharArray()) { 
            characters.add(c); 
        }
        
        Collections.shuffle(characters); 
  
        StringBuilder shuffledString = new StringBuilder(); 
        characters.forEach(c -> {
            shuffledString.append(c);
        }); 
  
        return shuffledString.toString(); 
    }

}
