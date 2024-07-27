/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.auth.resource;

import co.id.ez.auth.controller.AuthController;
import co.id.ez.auth.enums.Fields;
import co.id.ez.auth.enums.RequiredFields;
import co.id.ez.system.core.etc.EncryptionService;
import co.id.ez.system.core.ex.ServiceException;
import co.id.ez.system.core.log.LogService;
import co.id.ez.system.core.rc.RC;
import com.json.JSONObject;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author Lutfi
 */
public abstract class AuthHandler {
    
    public static String dbName = "auth-service";
    protected String corelation_id;
    protected final AuthController authController = new AuthController();

    public Response handleRequest(String pBodyMessage) {
        JSONObject tJsonRequest = new JSONObject();
        createCorelationID();
        try {
            String tRequest = "";
            String tUrlInfo = pBodyMessage;
            try {
                tRequest = URLDecoder.decode(tUrlInfo, 
                        StandardCharsets.UTF_8.toString()).replace("\n", "").replace("\t", " ").replace("\r", "");
                LogService.getInstance(this).stream()
                        .log("[" + RequiredFields.AUTH.name() + " REQUEST ]:(" + corelation_id + "): " + tRequest);
            } catch (UnsupportedEncodingException ex) {
                throw new Exception("Error Encoding Request");
            }

            tJsonRequest = new JSONObject(tRequest);
            validateMessage(tJsonRequest);
            
            JSONObject tMitraCred = authController.getCredential(tJsonRequest);
            validateCredential(tJsonRequest, tMitraCred);
            
            tJsonRequest.put("rc", "0000");
            tJsonRequest.put("rcm", "SUCCESS");
            
            LogService.getInstance(this).stream()
                    .log("[" + RequiredFields.AUTH.name() + " RESPONSE ]:(" + corelation_id + "): " + tJsonRequest);
            return constructHttpResponse(RC.SUCCESS, tJsonRequest);

        } catch (ServiceException ex) {
            LogService.getInstance(this).error().withCause(ex)
                    .log("[ServiceException](" + corelation_id + "):  Error on Handle " + RequiredFields.AUTH.name() + " request. ", true);
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
            
            LogService.getInstance(this).stream()
                    .log("[" + RequiredFields.AUTH.name() + " RESPONSE ]:(" + corelation_id + "): " + tJsonRequest.toString());
            return constructHttpResponse(tRC, tJsonRequest);
        } catch (Exception ex) {
            LogService.getInstance(this).error().withCause(ex)
                    .log("[Exception](" + corelation_id + "): Error on Handle " + RequiredFields.AUTH.name() + " request. ", true);
            RC tRC = RC.ERROR_OTHER;
            String tMsg = "Internal Server Error";

            tJsonRequest.put("rc", tRC.getResponseCodeString());
            tJsonRequest.put("rcm", tMsg);
            
            LogService.getInstance(this).stream()
                    .log("[" + RequiredFields.AUTH.name() + " RESPONSE ]:(" + corelation_id + "): " + tJsonRequest.toString());
            return constructHttpResponse(tRC, tJsonRequest);
        }
    }

    private void validateMessage(JSONObject pRequest) {
        LogService.getInstance(this).trace().log("(" + corelation_id + ") Before validate mandatory field");
        List<String> tList = new ArrayList<>();
        
        RequiredFields.AUTH.getFields().stream().filter(field -> (!pRequest.has(field))).forEachOrdered(field -> {
            tList.add(field);
        });
        
        if(tList.size()>0){
            throw new ServiceException(RC.ERROR_INVALID_MESSAGE, "Invalid mandatory field. " + tList.toString() );
        }
        
        LogService.getInstance(this).trace().log("(" + corelation_id + ") After validate mandatory field (success)");
    }
    
    private void validateCredential(final JSONObject pRequest, JSONObject pCredData){
        String tValidate = pCredData.getString("cred_access");
        String tKey = pRequest.getString(Fields.secret_key.name());
        String tUId = pRequest.getString(Fields.user_id.name());
        String tPass = EncryptionService.encryptor().Base64Decrypt(pRequest.getString(Fields.password.name()));
        String tClientID = pRequest.getString(Fields.client_id.name());
        String tModuleID = pRequest.getString(Fields.module_id.name());
        String tPath = pRequest.has(Fields.path.name()) ? pRequest.getString(Fields.path.name()) : null;
        
        LogService.getInstance(this).trace().log("(" + corelation_id + ") Before validate module");
        if (!authController.isValidModule(tModuleID)) {
            throw new ServiceException(RC.ERROR_PRODUCT_NOT_AVAILABLE, "Unregister Module [" + tModuleID + "]");
        }
        LogService.getInstance(this).trace().log("(" + corelation_id + ") After validate module (success)");
        
        LogService.getInstance(this).trace().log("(" + corelation_id + ") Before validate client access");
        if (!authController.isValidClientModule(tClientID, tModuleID, tPath)) {
            throw new ServiceException(RC.ERROR_BLOCKED_TERMINAL, "Invalid access product client");
        }
        LogService.getInstance(this).trace().log("(" + corelation_id + ") After validate client access (success)");
        
        LogService.getInstance(this).trace().log("(" + corelation_id + ") Before validate credential");
        if(!EncryptionService.encryptor().validateKey(tValidate, tKey, tClientID, tUId, tPass)){
            throw new ServiceException(RC.ERROR_UNREGISTERED_PARTNER_CENTRAL, "Invalid access client [" + tClientID + "]");
        }
        LogService.getInstance(this).trace().log("(" + corelation_id + ") After validate credential (Success)");
        
        pCredData.remove("cred_access");
        pCredData.remove("mitra_access");
        pCredData.remove("mitra_id");
        
        pRequest.put("detail", pCredData);
        
    }
    
    private Response constructHttpResponse(RC responseCode, JSONObject pResponse) {
        Response.Status tStatus;
        JSONObject tResponse;

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
    
}
