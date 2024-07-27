/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.deposit.resource;

import co.id.ez.deposit.data.Repository;
import co.id.ez.deposit.enums.Fields;
import co.id.ez.deposit.enums.RequiredFields;
import com.json.JSONArray;
import com.json.JSONObject;
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
public class StatementPlugin extends DepositHandler {
    
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    @Path("/api/deposit/statement")
    public Response jurnal(String pBodyMessage, @Context HttpHeaders pHeaders) {
        return handleRequest(pBodyMessage, RequiredFields.STATEMENT);
    }

    @Override
    public JSONObject executeRequest(JSONObject pRequest, String pAccount) {
        String tFrom = pRequest.getString(Fields.from_date.name());
        String tUntl = pRequest.getString(Fields.until_date.name());
        
        JSONArray tData = Repository.getStatement(pAccount, tFrom, tUntl);
        
        if (tData.length() > 0) {
            pRequest.put("rc", "0000");
            pRequest.put("rcm", "Sukses");
        }else{
            pRequest.put("rc", "0014");
            pRequest.put("rcm", "Not Found");
        }
        
        pRequest.put("data", tData);
        return pRequest;
    }
    
    
}
