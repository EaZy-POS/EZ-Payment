/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.central.resource.plugin;

import co.id.ez.central.enums.RequestHandler;
import co.id.ez.central.resource.CentralHandler;
import co.id.ez.system.core.ex.ServiceException;
import co.id.ez.system.core.log.LogService;
import co.id.ez.system.core.rc.RC;
import com.json.JSONObject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

/**
 *
 * @author Lutfi
 */
@Path("")
public class GetProfilePlugin extends CentralHandler {
    
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    @Path("/api/mitra/profile")
    public Response getProfile(@Context HttpHeaders pHeaders) {
        return handleRequest(pHeaders, "{}", RequestHandler.GET_PROFILE);
    }
    
    @Override
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
            
            JSONObject dataUser = validateUser(reqMap, tRequest);
            tJsonRequest.put("detail", dataUser.get("detail"));
            tJsonRequest.put("rc", "0000");
            tJsonRequest.put("rcm", "Sukses");
            
            String tRespose = tJsonRequest.toString();
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
}
