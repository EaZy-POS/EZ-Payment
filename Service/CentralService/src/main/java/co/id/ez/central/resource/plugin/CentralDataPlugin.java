/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.central.resource.plugin;

import co.id.ez.central.enums.RequestHandler;
import co.id.ez.central.resource.CentralHandler;
import com.json.JSONObject;
import jakarta.ws.rs.GET;
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
public class CentralDataPlugin extends CentralHandler {
    
    @Path("/cek_koneksi")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String cekKoneksi() {
        JSONObject resp = new JSONObject().put("status", "connected");
        return resp.toString();
    }
    
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    @Path("/api/pdam/biller/list")
    public Response getPDAMBiller(@Context HttpHeaders pHeaders) {
        return handleRequest(pHeaders, "{}", RequestHandler.PDAM_BILLER);
    }
    
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    @Path("/api/mp/biller/list")
    public Response getMPBiller(@Context HttpHeaders pHeaders) {
        return handleRequest(pHeaders, "{}", RequestHandler.MP_BILLER);
    }
    
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    @Path("/api/prepaid/denom/list")
    public Response getPrepaidDenom(@Context HttpHeaders pHeaders) {
        return handleRequest(pHeaders, "{}", RequestHandler.PREPAID_DENOM);
    }
    
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    @Path("/api/voucher/product/list")
    public Response getVoucherProduct(@Context HttpHeaders pHeaders) {
        return handleRequest(pHeaders, "{}", RequestHandler.VOUCHER_PRODUCT);
    }
}
