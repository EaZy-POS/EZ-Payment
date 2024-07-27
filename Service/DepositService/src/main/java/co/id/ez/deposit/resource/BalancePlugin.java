/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.deposit.resource;

import co.id.ez.deposit.data.Repository;
import co.id.ez.deposit.enums.RequiredFields;
import com.json.JSONObject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.math.BigDecimal;

/**
 *
 * @author Lutfi
 */
@Path("")
public class BalancePlugin extends DepositHandler {
    
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    @Path("/api/deposit/balance")
    public Response jurnal(String pBodyMessage, @Context HttpHeaders pHeaders) {
        return handleRequest(pBodyMessage, RequiredFields.BALANCE);
    }

    @Override
    public JSONObject executeRequest(JSONObject pRequest, String pAccount) {
        
        BigDecimal tBalance = Repository.getBalanceAccount(pAccount);
        
        pRequest.put("rc", "0000");
        pRequest.put("rcm", "Sukses");
        pRequest.put("balance", tBalance.doubleValue());
        
        return pRequest;
    }
    
    
}
