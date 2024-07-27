/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.central.resource.plugin;

import co.id.ez.central.enums.RequestHandler;
import co.id.ez.central.resource.CentralHandler;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 *
 * @author Lutfi
 */
@Path("")
public class DepositPlugin extends CentralHandler {
    
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    @Path("/api/mitra/getsaldo")
    public Response getSaldo(@Context HttpHeaders pHeaders) {
        return handleRequest(pHeaders, "{}", RequestHandler.BALANCE);
    }
    
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    @Path("/api/mitra/getstatement")
    public Response getMutasi(String pBodyMessage, @Context HttpHeaders pHeaders) {
        return handleRequest(pHeaders, pBodyMessage, RequestHandler.MINISTATEMENT);
    }
    
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    @Path("/api/mitra/topup")
    public Response topupSaldo(String pBodyMessage, @Context HttpHeaders pHeaders) {
        return handleRequest(pHeaders, pBodyMessage, RequestHandler.TOPUP_SALDO);
    }

}
