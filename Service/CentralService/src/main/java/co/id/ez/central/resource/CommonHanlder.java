/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.central.resource;

import co.id.ez.central.controller.CentralController;
import co.id.ez.central.enums.RequestHandler;
import co.id.ez.central.util.RequestMapping;
import co.id.ez.system.core.config.ConfigService;
import co.id.ez.system.core.etc.EncryptionService;
import co.id.ez.system.core.ex.ServiceException;
import co.id.ez.system.core.log.LogService;
import co.id.ez.system.core.rc.RC;
import com.json.JSONObject;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.UriInfo;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.testcontainers.shaded.okhttp3.MediaType;
import org.testcontainers.shaded.okhttp3.OkHttpClient;
import org.testcontainers.shaded.okhttp3.Request;
import org.testcontainers.shaded.okhttp3.RequestBody;
import org.testcontainers.shaded.okhttp3.Response;

/**
 *
 * @author RCS
 */
public abstract class CommonHanlder extends Thread {

    public static String dbName = "central-service";
    public static final String MODULE_NAME_SPACE = "central-service.config.";
    public static String cTimeOutKey = MODULE_NAME_SPACE + "timeout";
    public static String cAuthKey = MODULE_NAME_SPACE + "auth";
    public static String cDepAccountKey = MODULE_NAME_SPACE + "dep-account";
    public static String cDepBalanceKey = MODULE_NAME_SPACE + "dep-balance";
    public static String cDepMinistatementKey = MODULE_NAME_SPACE + "dep-ministatement";
    public static String cDepJournalKey = MODULE_NAME_SPACE + "dep-journal";
    
    protected String corelation_id;
    public static RequestMapping reqMap;
    protected final CentralController controller = new CentralController();
    
    @Context
    public UriInfo uriInfo;
    
    public JSONObject constructErrorResponse(String rc, String rcm) {
        JSONObject resp = constructErrorResponse(null, rc, rcm);
        return resp;
    }
    
    public JSONObject constructErrorResponse(RC rc, String rcm) {
        JSONObject resp = constructErrorResponse(null, rc, rcm);
        return resp;
    }
    
    public JSONObject constructErrorResponse(JSONObject request, String rc, String rcm) {
        JSONObject resp = request == null ? new JSONObject() : request ;
        resp.put("rc", rc);
        resp.put("rcm", rcm);

        return resp;
    }
    
    public JSONObject constructErrorResponse(JSONObject request, RC rc, String rcm) {
        JSONObject resp = request == null ? new JSONObject() : request ;
        resp.put("rc", rc.getResponseCodeString());
        resp.put("rcm", rcm);

        return resp;
    }
    
    public void validateMessage(@Context HttpHeaders pHeaders, JSONObject pRequest, RequestHandler pHandler) {
        LogService.getInstance(this).trace().log("(" + corelation_id + ") Before validate message");

        reqMap = validateHeader(pHeaders);
        validateUser(reqMap);

        LogService.getInstance(this).trace().log("(" + corelation_id + ") Before validate mandatory field");
        if (pHandler.getRequiredField() != null) {
            List<String> tList = new ArrayList<>();
            pHandler.getRequiredField().getFields().stream().filter(field -> (!pRequest.has(field))).forEachOrdered(field -> {
                tList.add(field);
            });

            if (tList.size() > 0) {
                throw new ServiceException(RC.ERROR_INVALID_MESSAGE, "Invalid mandatory field. " + tList.toString());
            }
        }
        LogService.getInstance(this).trace().log("(" + corelation_id + ") After validate mandatory field (success)");
    }

    public String[] validateAuth(List<String> pAuthHeader, List<String> pKeyHeader) {
        LogService.getInstance(this).trace().log("(" + corelation_id + ") Before validate auth");
        String tKey;
        String[] tDataAuth;

        if (pKeyHeader != null) {
            if (pKeyHeader.size() > 0) {
                tKey = pKeyHeader.get(0);
                if (tKey == null || tKey.equals("")) {
                    throw new ServiceException(RC.ERROR_INVALID_KEY, "Invalid mandatory field in header. [secret_key]");
                }
            } else {
                throw new ServiceException(RC.ERROR_INVALID_KEY, "Invalid mandatory field in header. [secret_key]");
            }
        } else {
            throw new ServiceException(RC.ERROR_INVALID_KEY, "Invalid mandatory field in header. [secret_key]");
        }

        if (pAuthHeader != null) {
            if (pAuthHeader.size() > 0) {
                String tAuth = pAuthHeader.get(0).replace("Basic ", "");
                tDataAuth = EncryptionService.encryptor().Base64Decrypt(tAuth).split(":");
            } else {
                throw new ServiceException(RC.ERROR_INVALID_KEY, "Invalid mandatory field in header. [Auth]");
            }
        } else {
            throw new ServiceException(RC.ERROR_INVALID_KEY, "Invalid mandatory field in header. [Auth]");
        }

        LogService.getInstance(this).trace().log("(" + corelation_id + ") After validate auth (success)");
        return tDataAuth;
    }

    public RequestMapping validateHeader(@Context HttpHeaders pHeaders) {
        LogService.getInstance(this).trace().log("(" + corelation_id + ") Before validate header");

        List<String> tHeaderClientID = pHeaders.getRequestHeader("client_id");
        List<String> tHeaderMitraKey = pHeaders.getRequestHeader("secret_key");
        List<String> tHeaderAuth = pHeaders.getRequestHeader("Authorization");
        List<String> tHeaderproduct = pHeaders.getRequestHeader("module_id");

        validateMitra(tHeaderClientID, tHeaderproduct);
        String[] tUserData = validateAuth(tHeaderAuth, tHeaderMitraKey);

        RequestMapping reqMaps = new RequestMapping(tHeaderClientID.get(0),
                tHeaderproduct.get(0),
                tHeaderMitraKey.get(0),
                tHeaderAuth.get(0),
                tUserData[0],
                tUserData[1],
                uriInfo.getPath()
        );

        LogService.getInstance(this).trace().log("(" + corelation_id + ") After validate header (success)");
        return reqMaps;
    }

    public void validateMitra(List<String> pClientHeader, List<String> pModuleHeader) {
        LogService.getInstance(this).trace().log("(" + corelation_id + ") Before validate mitra");
        if (pModuleHeader != null) {
            if (pModuleHeader.size() <= 0) {
                throw new ServiceException(RC.ERROR_INVALID_KEY, "Invalid mandatory field in header. [module_id]");
            }
        } else {
            throw new ServiceException(RC.ERROR_INVALID_KEY, "Invalid mandatory field in header. [module_id]");
        }

        if (pClientHeader != null) {
            if (pClientHeader.size() <= 0) {
                throw new ServiceException(RC.ERROR_INVALID_KEY, "Invalid mandatory field in header. [client_id]");
            }
        } else {
            throw new ServiceException(RC.ERROR_INVALID_KEY, "Invalid mandatory field in header. [client_id]");
        }

        LogService.getInstance(this).trace().log("(" + corelation_id + ") After validate mitra (success)");
    }

    public String sendPostRequest(JSONObject pRequest, String pUrl, String pServiceName) {
        String tResponse = "";

        String tBaseUrl = pUrl;
        int timeout = ConfigService.getInstance().getInt(cTimeOutKey);

        LogService.getInstance(this).stream().log((pServiceName.toUpperCase()) 
                +" REQUEST to HTTP ("+corelation_id+"): {} body: [{}]", tBaseUrl, pRequest.toString());

        try {
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .connectTimeout(5, TimeUnit.SECONDS)
                    .writeTimeout(5, TimeUnit.SECONDS)
                    .connectTimeout(timeout, TimeUnit.MILLISECONDS)
                    .build();
            
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, pRequest.toString());
            Request httpRequest = new Request.Builder()
                    .url(tBaseUrl)
                    .method("POST", body)
                    .addHeader("Accept", "application/json")
                    .addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0;Windows98;DigExt)")
                    .addHeader("charset", "utf-8")
                    .addHeader("Content-type", "application/json")
                    .build();

            Response response = client.newCall(httpRequest).execute();
            LogService.getInstance(this).trace().log("Response ("+corelation_id+"): {} ", response);
            tResponse = response.body().string();
        } catch (IOException e) {
            throw new ServiceException(RC.ERROR_TRANSACTION_FAILED_FROM_VENDING, "Error Response from biller. ", e);
        }

        LogService.getInstance(this).stream().log((pServiceName.toUpperCase()) +" RESPONSE from HTTP ("+corelation_id+"): {} [{}] ", tBaseUrl, tResponse);

        return tResponse;
    }
    
    public static String escape(String s) {
        return s.replace("\\", "\\\\")
                .replace("\t", "\\t")
                .replace("\b", "\\b")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\f", "\\f")
                .replace("\'", "\\'")
                .replace("\"", "\\\"");
    }
    
    public JSONObject validateUser(RequestMapping reqMap){
        LogService.getInstance(this).trace().log("Before validate user");
        String pAuthUrl = ConfigService.getInstance().getString(cAuthKey, "http://127.0.0.1:6566/api/auth/validate");
        
        String tAuthResponse = sendPostRequest(reqMap.getAuthRequest(), pAuthUrl, "AUTH-SERVICE");
        
        if(!tAuthResponse.equals("") && tAuthResponse.startsWith("{")){
            JSONObject tResponse = new JSONObject(tAuthResponse);
            String tRc = tResponse.getString("rc").substring(3);
            if (tRc.equals("0000")) {
                LogService.getInstance(this).trace().log("Afetr validate user (success)");
                return tResponse;
            }else{
                LogService.getInstance(this).trace().log("After validate user (failed)");
                throw new ServiceException(RC.parseResponseCodeString(tRc), tResponse.getString("rcm"));
            }
        }
        LogService.getInstance(this).trace().log("After validate user (failed)");
        throw new ServiceException(RC.ERROR_OTHER, "Get null/empty response from auth. ["+ tAuthResponse+"]");
    }
}
