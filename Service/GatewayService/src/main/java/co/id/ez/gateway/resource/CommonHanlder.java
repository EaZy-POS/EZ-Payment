/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.gateway.resource;

import co.id.ez.gateway.data.Repository;
import co.id.ez.gateway.message.BillerRequest;
import co.id.ez.gateway.util.RequestMapping;
import co.id.ez.gateway.util.SSLUtilities;
import co.id.ez.gateway.util.enums.Fields;
import co.id.ez.gateway.util.enums.RequiredFields;
import co.id.ez.gateway.util.enums.TableName;
import co.id.ez.system.core.config.ConfigService;
import co.id.ez.system.core.etc.EncryptionService;
import co.id.ez.system.core.ex.ServiceException;
import co.id.ez.system.core.log.LogService;
import co.id.ez.system.core.rc.RC;
import com.json.JSONObject;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.UriInfo;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
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

    public static String dbName = "gateway-service";
    public static final String cModuleNamespace = "gateway-service.config.";

    public static String cUrlKey = "gateway-service.url.";
    public static String cMessageKey = "gateway-service.message.";
    public static String cPathKey = "path";
    public static String cCIDKey = "cid";
    public static String cSkeyKey = "skey";
    public static String cResponseKey = "response";
    public static String cTimeOutKey = "timeout";
    public static String cIsSimulator = cModuleNamespace + "is-simulator";
    public static String cAuthKey = "auth";
    public static String cDepJournalKey = "dep-journal";
    
    public static String cCidKey = "cid";
    public static String cdtKey = "dt";
    public static String cHcKey = "hc";
    public static String cModulKey = "modul";
    public static String cComandKey = "comand";
    public static String cTrxIDKey = "trxid";
    public static String cRespKey = "resp";
    public static String cBillerKey = "biller";
    public static String cInput1Key = "input1";
    public static String cInput2Key = "input2";
    public static String cInput3Key = "input3";
    
    protected String corelation_id;
    protected RequestMapping reqMap;
    
    @Context
    public UriInfo uriInfo;
    
    public abstract BillerRequest constructBillerRequest(JSONObject request);
    public abstract void validateTransactAmount(JSONObject request, LinkedList<JSONObject> pTranmainDB);
    public abstract void insertSuccessfullResponseToTranmain(JSONObject response);
    public abstract void updateSuccessfullResponseToTranmain(JSONObject response);
    public abstract BigDecimal getTransactAmount(JSONObject pRequest);
    
    public Object getMitraAmount(JSONObject request){
        return null;
    }

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
    
    public LinkedList<JSONObject> validateMessage(@Context HttpHeaders pHeaders, JSONObject request, RequiredFields requiredField, TableName pTranmainTable) {
        LogService.getInstance(this).trace().log("(" + corelation_id + ") Before validate message");

        reqMap = validateHeader(pHeaders);
        validateUser(reqMap);
        LinkedList<JSONObject> tTranmain = null;

        LogService.getInstance(this).trace().log("(" + corelation_id + ") Before validate mandatory field");
        requiredField.getFields().stream().filter(field -> (!request.has(field))).forEachOrdered(field -> {
            throw new ServiceException(RC.ERROR_INVALID_MESSAGE, "Invalid mandatory field. [" + field + "]");
        });
        LogService.getInstance(this).trace().log("(" + corelation_id + ") After validate mandatory field (success)");

        if (!request.getString("command").equals("INQ")) {
            LogService.getInstance(this).trace().log("(" + corelation_id + ") Before validate numeric amount");
            Object downAmount = getMitraAmount(request);
            
            if(downAmount != null){
                try {
                    Double.parseDouble(downAmount.toString());
                } catch (NumberFormatException e) {
                    throw new ServiceException(RC.ERROR_INVALID_TRANSACTION_AMOUNT, 
                            "Invalid format for transact amount: ["+downAmount+"]");
                }
            }
            
            LogService.getInstance(this).trace().log("(" + corelation_id + ") After validate numeric amount (success)");
            
            LogService.getInstance(this).trace().log("(" + corelation_id + ") Before validate with tranmain");
            tTranmain = validateWithTranmain(request, pTranmainTable.get());
            LogService.getInstance(this).trace().log("(" + corelation_id + ") After validate with tranmain (success)");

            if (request.getString("command").equals("PAY")) {
                
                if(tTranmain.getFirst().getInt("flag") == 1){
                    throw new ServiceException(RC.ERROR_BILL_ALREADY_PAID, "Transaction has been paid");
                }
                
                if(tTranmain.getFirst().getInt("flag") > 1){
                    throw new ServiceException(RC.ERROR_REQUEST_IS_BEING_PROCESSED, "Transaction has been processing");
                }
                
                LogService.getInstance(this).trace().log("(" + corelation_id + ") Before validate request time");
                validateRequestTime(tTranmain);
                LogService.getInstance(this).trace().log("(" + corelation_id + ") After validate request time (success)");
            }

            LogService.getInstance(this).trace().log("(" + corelation_id + ") Before validate transact amount");
            validateTransactAmount(request, tTranmain);
            LogService.getInstance(this).trace().log("(" + corelation_id + ") After validate transact amount (success)");

            LogService.getInstance(this).trace().log("(" + corelation_id + ") Before validate deposit");
            BigDecimal ammount = BigDecimal.ZERO;
            LogService.getInstance(this).trace().log("(" + corelation_id + ") After validate deposit");

            reqMap.setTranmaindata(tTranmain, ammount);
        }
        LogService.getInstance(this).trace().log("(" + corelation_id + ") After validate message (success)");
        return tTranmain;
    }

    private void validateRequestTime(LinkedList<JSONObject> tTranmain) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String reqTime = format.format(new Date());
            String inqTime = tTranmain.getFirst().get("transaction_date").toString();

            Date curTime = format.parse(reqTime);
            Date tarTime = format.parse(inqTime);

            if ((((curTime.getTime() - tarTime.getTime()) / 1000) / 60) > 5) {
                throw new ServiceException(RC.ERROR_INVALID_ACCESS_TIME, "Request has been Expired, please try again!");
            }
        } catch (ParseException ex) {
            throw new ServiceException(RC.ERROR_OTHER, "Failed parsing date ", ex);
        }
    }

    private LinkedList<JSONObject> validateWithTranmain(JSONObject request, String pTranMainTable) {
        return Repository.getTranmainData(pTranMainTable, request.getString("trxid"), reqMap.getClient_id(), reqMap.getModule_id());
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
        int timeout = ConfigService.getInstance().getInt(cUrlKey + cTimeOutKey);

        LogService.getInstance(this).stream().log((pServiceName.toUpperCase()) +" REQUEST to HTTP ("+corelation_id+"): {} body: [{}]", tBaseUrl, pRequest.toString());

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
    
    public String sendGetHttpRequest(BillerRequest request, RequestMapping reqMap) {
        HttpURLConnection tCon;
        String tResponse = "";
        
        String tBaseUrl = ConfigService.getInstance().getString(cUrlKey + cPathKey);
        tBaseUrl = tBaseUrl.concat(request.getMessageStream());
        int timeout = ConfigService.getInstance().getInt(cUrlKey + cTimeOutKey);
        boolean isSimulator = ConfigService.getInstance().getBoolean(cIsSimulator, false);
        
        try {
            LogService.getInstance(this).stream().log("Biller REQUEST to HTTP (" + corelation_id + "):[{}] {}", (isSimulator ? "simulator" : ""), tBaseUrl);
            Repository.logMessage(request.getComand(), "REQ", corelation_id, request.getTrxid(), reqMap, tBaseUrl, String.valueOf(getId()));
            
            if (isSimulator) {
                tResponse = request.contructSimulatorResponse();
            } else {

                SSLUtilities.trustAllHostnames();
                SSLUtilities.trustAllHttpsCertificates();

                URL tRequestHttp = new URL(tBaseUrl);
                tCon = (HttpURLConnection) tRequestHttp.openConnection();
                tCon.setDoInput(true);
                tCon.setDoOutput(true);
                tCon.setUseCaches(false);
                tCon.setReadTimeout(timeout);
                tCon.setConnectTimeout(20000);
                tCon.setRequestMethod("GET");
                tCon.setRequestProperty("Accept", "application/json");
                tCon.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0;Windows98;DigExt)");
                tCon.setRequestProperty("charset", "utf-8");
                tCon.setRequestProperty("Content-type", "application/json");
                byte[] postData = request.getMessageStream().getBytes();
                int postDataLength = postData.length;
                tCon.setRequestProperty("Content-Length", Integer.toString(postDataLength));

                int tRC = tCon.getResponseCode();
                if (tRC == 200) {
                    try (BufferedReader in = new BufferedReader(new InputStreamReader(tCon.getInputStream()))) {
                        StringBuilder response = new StringBuilder();
                        String line;
                        while ((line = in.readLine()) != null) {
                            response.append(line);
                        }
                        tResponse = response.toString();
                    }
                }
            }
        } catch (IOException e) {
            LogService
                    .getInstance(this)
                    .error()
                    .withCause(e)
                    .log("("+corelation_id+") [IOException] Error No Response from biller. ", true);
        }
        
        LogService.getInstance(this)
                .stream()
                .log("Biller RESPONSE from HTTP (" + corelation_id + "): {}[{}] [{}] ", 
                        tBaseUrl, 
                        (isSimulator ? "simulator" : ""), 
                        tResponse
                );
        Repository.logMessage(
                request.getComand(), 
                "RES", 
                corelation_id, 
                request.getTrxid(), 
                reqMap, 
                tResponse, 
                String.valueOf(getId()
                )
        );
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

    public JSONObject constructSuccessfullResponse(JSONObject pRequest, String pResponse){
        JSONObject tResp = new JSONObject(pResponse);
        pRequest.keySet().stream().map(keys -> {
            return keys;
        }).filter(keys -> (!tResp.has(keys.toString()))).forEachOrdered(keys -> {
            tResp.put(keys.toString(), pRequest.get(keys.toString()));
        });
        
        return tResp;
    }
    
    public JSONObject validateUser(RequestMapping reqMap){
        LogService.getInstance(this).trace().log("Before validate user");
        String pAuthUrl = ConfigService.getInstance().getString(cUrlKey + cAuthKey, "http://127.0.0.1:6566/api/auth/validate");
        
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

    public boolean sendDeposit(JSONObject pRequest, BillerRequest message, String pJournal){
        return sendDeposit(pRequest, message, pJournal, "Pembayaran");
    }
    
    public boolean sendDeposit(JSONObject pRequest, BillerRequest message, String pJournal, String pPrefix){
        if (reqMap != null) {
            if (reqMap.getTranmaindata().getFirst().getInt("flag") == 0) {
                JSONObject tDepReq = reqMap.getJournalRequest(
                        pJournal, 
                        pRequest.getString(Fields.trxid.name()),
                        message.getRemarks(pPrefix),
                        getTransactAmount(pRequest)
                );

                LogService.getInstance(this).trace().log("Before send request to deposit");
                String tUrl = ConfigService.getInstance()
                        .getString(cUrlKey + cDepJournalKey, "http://127.0.0.1:6567/api/deposit/jurnal");
                String tAuthResponse = sendPostRequest(tDepReq, tUrl, "DEP-SERVICE");

                if (!tAuthResponse.equals("") && tAuthResponse.startsWith("{")) {
                    JSONObject tResponse = new JSONObject(tAuthResponse);
                    String tRc = tResponse.getString("rc").substring(3);
                    if (!tRc.equals("0000")) {
                        LogService.getInstance(this).trace().log("After send request to deposit (failed)");
                        throw new ServiceException(RC.parseResponseCodeString(tRc), tResponse.getString("rcm"));
                    }
                    
                    return true;
                }
                
                LogService.getInstance(this).trace().log("Before send request to deposit (failed)");
                throw new ServiceException(RC.ERROR_OTHER, "Get null/empty response from auth. [" + tAuthResponse + "]");
            }
        }
        
        return false;
    }
}
