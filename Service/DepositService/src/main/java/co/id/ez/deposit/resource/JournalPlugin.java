/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.deposit.resource;

import co.id.ez.deposit.data.Repository;
import co.id.ez.deposit.enums.Fields;
import co.id.ez.deposit.enums.RequiredFields;
import co.id.ez.system.core.ex.ServiceException;
import co.id.ez.system.core.rc.RC;
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
public class JournalPlugin extends DepositHandler {
    
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    @Path("/api/deposit/jurnal")
    public Response jurnal(String pBodyMessage, @Context HttpHeaders pHeaders) {
        return handleRequest(pBodyMessage, RequiredFields.JOURNAL_DEPOSIT);
    }

    @Override
    public JSONObject executeRequest(JSONObject pRequest, String pAccount) {
        String tJurnal = pRequest.getString(Fields.jurnal.name());
        String tRefnum = pRequest.getString(Fields.refnum.name());
        String tRemarks = pRequest.getString(Fields.remarks.name());
        BigDecimal tAmount = new BigDecimal(pRequest.getDouble(Fields.amount.name()));
        
        BigDecimal tBalance = Repository.getBalanceAccount(pAccount);
        
        if (tJurnal.equals("D")) {
            if (tBalance.compareTo(tAmount) <= 0) {
                throw new ServiceException(RC.ERROR_INSUFFICIENT_DEPOSIT,
                        "The balance on account " + pAccount + " is not sufficient to carry out transactions");
            }
        }
        
        if(tJurnal.contains("C") || tJurnal.equals("D")){
            Repository.insertJournal(pAccount, tJurnal, tRefnum, tRemarks, tAmount, tBalance);
        }else{
            throw new ServiceException(RC.ERROR_INVALID_KEY, 
                    "The journal type is not appropriate");
        }
        
        pRequest.put("rc", "0000");
        pRequest.put("rcm", "Sukses");
        
        return pRequest;
    }
    
    
}
