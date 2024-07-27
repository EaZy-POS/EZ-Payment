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
    @POST
    @Path("/api/pdam/biller/add")
    public Response addPDAMBiller(@Context HttpHeaders pHeaders, String  pBodyMessage) {
        return handleRequest(pHeaders, pBodyMessage, RequestHandler.ADD_PDAM_BILLER);
    }
    
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    @Path("/api/pdam/biller/update")
    public Response updatePDAMBiller(@Context HttpHeaders pHeaders, String  pBodyMessage) {
        return handleRequest(pHeaders, pBodyMessage, RequestHandler.UPDATE_PDAM_BILLER);
    }
    
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    @Path("/api/pdam/biller/delete")
    public Response deletePDAMBiller(@Context HttpHeaders pHeaders, String  pBodyMessage) {
        return handleRequest(pHeaders, pBodyMessage, RequestHandler.DELETE_PDAM_BILLER);
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
    @POST
    @Path("/api/prepaid/denom/add")
    public Response addPrepaidDenom(@Context HttpHeaders pHeaders, String  pBodyMessage) {
        return handleRequest(pHeaders, pBodyMessage, RequestHandler.ADD_PREPAID_DENOM);
    }
    
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    @Path("/api/prepaid/denom/update")
    public Response updatePrepaidDenom(@Context HttpHeaders pHeaders, String  pBodyMessage) {
        return handleRequest(pHeaders, pBodyMessage, RequestHandler.UPDATE_PREPAID_DENOM);
    }
    
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    @Path("/api/prepaid/denom/delete")
    public Response deletePrepaidDenom(@Context HttpHeaders pHeaders, String  pBodyMessage) {
        return handleRequest(pHeaders, pBodyMessage, RequestHandler.DELETE_PREPAID_DENOM);
    }
    
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    @Path("/api/voucher/product/list")
    public Response getVoucherProduct(@Context HttpHeaders pHeaders) {
        return handleRequest(pHeaders, "{}", RequestHandler.VOUCHER_PRODUCT);
    }
    
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    @Path("/api/voucher/product/add")
    public Response addVoucherProduct(@Context HttpHeaders pHeaders, String  pBodyMessage) {
        return handleRequest(pHeaders, pBodyMessage, RequestHandler.ADD_VOUCHER_PRODUCT);
    }
    
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    @Path("/api/voucher/product/update")
    public Response updateVoucherProduct(@Context HttpHeaders pHeaders, String  pBodyMessage) {
        return handleRequest(pHeaders, pBodyMessage, RequestHandler.UPDATE_VOUCHER_PRODUCT);
    }
    
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    @Path("/api/voucher/product/delete")
    public Response deleteVoucherProduct(@Context HttpHeaders pHeaders, String  pBodyMessage) {
        return handleRequest(pHeaders, pBodyMessage, RequestHandler.DELETE_VOUCHER_PRODUCT);
    }
    
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    @Path("/api/voucher/product/markup")
    public Response markupVoucherProduct(@Context HttpHeaders pHeaders, String  pBodyMessage) {
        return handleRequest(pHeaders, pBodyMessage, RequestHandler.MARKUP_VOUCHER_PRODUCT);
    }
    
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    @Path("/api/module/list")
    public Response getModuleList(@Context HttpHeaders pHeaders) {
        return handleRequest(pHeaders, "{}", RequestHandler.MODULE_LIST);
    }
    
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    @Path("/api/module/add")
    public Response addModuleList(@Context HttpHeaders pHeaders, String pBodyMessage) {
        return handleRequest(pHeaders, pBodyMessage, RequestHandler.ADD_MODULE_LIST);
    }
    
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    @Path("/api/module/update")
    public Response updateModuleList(@Context HttpHeaders pHeaders, String pBodyMessage) {
        return handleRequest(pHeaders, pBodyMessage, RequestHandler.UPDATE_MODULE_LIST);
    }
    
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    @Path("/api/module/delete")
    public Response deleteModuleList(@Context HttpHeaders pHeaders, String pBodyMessage) {
        return handleRequest(pHeaders, pBodyMessage, RequestHandler.DELETE_MODULE_LIST);
    }
    
    @GET
    @Path("/api/module/syslist/list")
    public Response getModuleSystemList(@Context HttpHeaders pHeaders) {
        return handleRequest(pHeaders, "{}", RequestHandler.MODULE_SYSTEM_LIST);
    }
    
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    @Path("/api/module/syslist/add")
    public Response addModuleSystemList(@Context HttpHeaders pHeaders, String pBodyMessage) {
        return handleRequest(pHeaders, pBodyMessage, RequestHandler.ADD_MODULE_SYSTEM_LIST);
    }
    
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    @Path("/api/module/syslist/update")
    public Response updateModuleSystemList(@Context HttpHeaders pHeaders, String pBodyMessage) {
        return handleRequest(pHeaders, pBodyMessage, RequestHandler.UPDATE_MODULE_SYSTEM_LIST);
    }
    
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    @Path("/api/module/syslist/delete")
    public Response deleteModuleSystemList(@Context HttpHeaders pHeaders, String pBodyMessage) {
        return handleRequest(pHeaders, pBodyMessage, RequestHandler.DELETE_MODULE_SYSTEM_LIST);
    }
    
    @GET
    @Path("/api/voucher/provider/list")
    public Response getVoucherproviderList(@Context HttpHeaders pHeaders) {
        return handleRequest(pHeaders, "{}", RequestHandler.VOUCHER_PROVIDER_LIST);
    }
    
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    @Path("/api/voucher/provider/add")
    public Response addVoucherproviderList(@Context HttpHeaders pHeaders, String pBodyMessage) {
        return handleRequest(pHeaders, pBodyMessage, RequestHandler.ADD_VOUCHER_PROVIDER_LIST);
    }
    
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    @Path("/api/voucher/provider/update")
    public Response updateVoucherproviderList(@Context HttpHeaders pHeaders, String pBodyMessage) {
        return handleRequest(pHeaders, pBodyMessage, RequestHandler.UPDATE_VOUCHER_PROVIDER_LIST);
    }
    
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    @Path("/api/voucher/provider/delete")
    public Response deleteVoucherproviderList(@Context HttpHeaders pHeaders, String pBodyMessage) {
        return handleRequest(pHeaders, pBodyMessage, RequestHandler.DELETE_VOUCHER_PROVIDER_LIST);
    }
    
    @GET
    @Path("/api/voucher/provider/prefix/list")
    public Response getVoucherproviderPrefixList(@Context HttpHeaders pHeaders) {
        return handleRequest(pHeaders, "{}", RequestHandler.VOUCHER_PROVIDER_PREFIX_LIST);
    }
    
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    @Path("/api/voucher/provider/prefix/add")
    public Response addVoucherproviderPrefixList(@Context HttpHeaders pHeaders, String pBodyMessage) {
        return handleRequest(pHeaders, pBodyMessage, RequestHandler.ADD_VOUCHER_PROVIDER_PREFIX_LIST);
    }
    
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    @Path("/api/voucher/provider/prefix/update")
    public Response updateVoucherproviderPrefixList(@Context HttpHeaders pHeaders, String pBodyMessage) {
        return handleRequest(pHeaders, pBodyMessage, RequestHandler.UPDATE_VOUCHER_PROVIDER_PREFIX_LIST);
    }
    
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    @Path("/api/voucher/provider/prefix/delete")
    public Response deleteVoucherproviderPrefixList(@Context HttpHeaders pHeaders, String pBodyMessage) {
        return handleRequest(pHeaders, pBodyMessage, RequestHandler.DELETE_VOUCHER_PROVIDER_PREFIX_LIST);
    }
    
    @GET
    @Path("/api/mitra/list")
    public Response getMitraList(@Context HttpHeaders pHeaders) {
        return handleRequest(pHeaders, "{}", RequestHandler.MITRA_LIST);
    }
    
    @POST
    @Path("/api/mitra/update")
    public Response updateMitraList(@Context HttpHeaders pHeaders, String pBodyMessage) {
        return handleRequest(pHeaders, pBodyMessage, RequestHandler.UPDATE_MITRA_LIST);
    }
    
    @POST
    @Path("/api/mitra/delete")
    public Response deleteMitraList(@Context HttpHeaders pHeaders, String pBodyMessage) {
        return handleRequest(pHeaders, pBodyMessage, RequestHandler.DELETE_MITRA_LIST);
    }
}
