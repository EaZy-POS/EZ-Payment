/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.central.resource;

import co.id.ez.central.enums.RequestHandler;
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
import java.util.UUID;

/**
 *
 * @author Lutfi
 */
public abstract class CentralHandler extends CommonHanlder{

    public Response handleRequest(@Context HttpHeaders pHeaders, String pBodyMessage, RequestHandler pRequestHandler) {
        JSONObject tJsonRequest = new JSONObject();
        createCorelationID();
        pRequestHandler.getHandler().setTraceID(corelation_id);
        try {
            String tRequest = "";
            String tUrlInfo = pBodyMessage;
            try {
                tRequest = URLDecoder.decode(tUrlInfo, StandardCharsets.UTF_8.toString())
                        .replace("\n", "").replace("\t", " ").replace("\r", "");
                LogService.getInstance(this).stream()
                        .log("[" + pRequestHandler.name() + " REQUEST ]:(" + corelation_id + "): " + tRequest);
            } catch (UnsupportedEncodingException ex) {
                throw new Exception("Error Encoding Request");
            }

            tJsonRequest = new JSONObject(tRequest);
            validateMessage(pHeaders, tRequest, pRequestHandler);
            
            String tRespose = pRequestHandler.getHandler().performHandler(tJsonRequest);
            
            LogService.getInstance(this).stream()
                    .log("[" + pRequestHandler.name() + " RESPONSE ]:(" + corelation_id + "): " + tRespose);
            return constructHttpResponse(RC.SUCCESS, new JSONObject(tRespose));

        } catch (ServiceException ex) {
            LogService.getInstance(this).error().withCause(ex)
                    .log("[ServiceException](" + corelation_id + "):  Error on Handle " 
                            + pRequestHandler.name() + " request. ", true);
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
                    .log("[" + pRequestHandler.name() + " RESPONSE ]:(" + corelation_id + "): " + tJsonRequest.toString());
            return constructHttpResponse(tRC, tJsonRequest);
        } catch (Exception ex) {
            LogService.getInstance(this).error().withCause(ex)
                    .log("[Exception](" + corelation_id + "): Error on Handle " 
                            + pRequestHandler.name() + " request. ", true);
            RC tRC = RC.ERROR_OTHER;
            String tMsg = "Internal Server Error";

            tJsonRequest.put("rc", tRC.getResponseCodeString());
            tJsonRequest.put("rcm", tMsg);
            
            LogService.getInstance(this).stream()
                    .log("[" + pRequestHandler.name() + " RESPONSE ]:(" + corelation_id + "): " + tJsonRequest.toString());
            return constructHttpResponse(tRC, tJsonRequest);
        }
    }

    public Response constructHttpResponse(RC responseCode, JSONObject pResponse) {
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

    public void createCorelationID() {
        corelation_id = "id_" + UUID.randomUUID().toString().replace("-", "").substring(0, 15);
    }
    
}
