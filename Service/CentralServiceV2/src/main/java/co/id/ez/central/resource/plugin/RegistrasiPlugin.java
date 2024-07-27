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
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Lutfi
 */
@Path("")
public class RegistrasiPlugin extends CentralHandler {
    
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    @Path("/api/mitra/registration")
    public Response registrasi(String pBodyMessage, @Context HttpHeaders pHeaders) {
        return handleRequest(pHeaders, pBodyMessage, RequestHandler.REGISTRASI);
    }
    
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    @Path("/api/mitra/aktivasi")
    public Response aktivasi(String pBodyMessage, @Context HttpHeaders pHeaders) {
        return handleRequest(pHeaders, pBodyMessage, RequestHandler.AKTIFASI_MITRA);
    }
    
    @Override
    public void validateMessage(@Context HttpHeaders pHeaders, String pBodyRequest, RequestHandler pHandler) {
        LogService.getInstance(this).trace().log("(" + corelation_id + ") Before validate mandatory field");
        
        JSONObject pRequest = new JSONObject(pBodyRequest);
        
        if (pHandler.getRequiredField() != null) {
            List<String> tList = new ArrayList<>();
            pHandler.getRequiredField().getFields().stream().filter(field 
                    -> (!pRequest.has(field))).forEachOrdered(field -> {
                tList.add(field);
            });

            if (tList.size() > 0) {
                throw new ServiceException(RC.ERROR_INVALID_MESSAGE, "Invalid mandatory field. " + tList.toString());
            }
        }
        LogService.getInstance(this).trace().log("(" + corelation_id + ") After validate mandatory field (success)");
    }
}
