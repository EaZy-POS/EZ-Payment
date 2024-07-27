/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.central.handler;

import co.id.ez.central.resource.CommonHanlder;
import co.id.ez.system.core.config.ConfigService;
import co.id.ez.system.core.etc.EncryptionService;
import co.id.ez.system.core.ex.ServiceException;
import co.id.ez.system.core.log.LogService;
import co.id.ez.system.core.rc.RC;
import com.json.JSONArray;
import com.json.JSONException;
import com.json.JSONObject;
import java.math.BigDecimal;
import java.sql.SQLException;

/**
 *
 * @author Lutfi
 */
public class TopupHandler extends MessageHandler{

    @Override
    public String performHandler(JSONObject request) {
        LogService.getInstance(this).trace().log("(" + corelation_id + ") Before handle topup saldo mitra");

        try {
            executeTopupSaldo(contructTopupRequest(request));
            request.put("rc", "0000");
            request.put("rcm", "Sukses");
        } catch (JSONException | SQLException e) {
            LogService.getInstance(this).trace().log("(" + corelation_id + ") After handle topup salod mitra (failed)");
            throw new ServiceException(RC.ERROR_DATABASE, "Failed topup saldo. "+ e.getMessage(), e);
        }
        LogService.getInstance(this).trace().log("(" + corelation_id + ") After handle topup salod mitra (success)");
        return request.toString();
    }
    
    private JSONObject contructTopupRequest(JSONObject pRequest) throws SQLException{
        JSONObject tRequest = new JSONObject();
        
        String tCLientID = pRequest.getString("client_id");
        BigDecimal tAMount = new BigDecimal(pRequest.get("amount").toString());
        String tReffnum = pRequest.getString("noreff");
        String tCLientKey = controller.getMitraAccessKey(tCLientID);
        
        tRequest.put("das_id", getDasAccount(tCLientID, tCLientKey));
        
        tRequest.put("jurnal", "C");
        tRequest.put("refnum", corelation_id);
        tRequest.put("remarks", "TOPUP "+ tCLientID +"-"+ tReffnum + " "+ corelation_id);
        tRequest.put("amount", tAMount.doubleValue());
        
        return tRequest;
    }
    
    private void executeTopupSaldo(JSONObject pRequest) {
        LogService.getInstance(this).trace().log("(" + corelation_id + ") Before send request ministatement to deposit");
        String tUrl = ConfigService.getInstance()
                .getString(CommonHanlder.cDepJournalKey, "http://127.0.0.1:6567/api/deposit/jurnal");
        String tAuthResponse = sendPostRequest(pRequest, tUrl, "DEP-SERVICE");

        if (!tAuthResponse.equals("") && tAuthResponse.startsWith("{")) {
            JSONObject tResponse = new JSONObject(tAuthResponse);
            String tRc = tResponse.getString("rc").substring(3);
            if (!tRc.equals("0000")) {
                LogService.getInstance(this).trace()
                        .log("(" + corelation_id + ") After send request journal to deposit (failed)");
                throw new ServiceException(RC.parseResponseCodeString(tRc), tResponse.getString("rcm"));
            }else{
                LogService.getInstance(this).trace()
                        .log("(" + corelation_id + ") After send request journal to deposit (Success)");
            }
        }else{
            LogService.getInstance(this).trace()
                    .log("(" + corelation_id + ") After send request journal to deposit (Failed)");
            throw new ServiceException(RC.ERROR_OTHER, 
                    "(" + corelation_id + ") Get null/empty response from deposit. [" + tAuthResponse + "]");
        }
        
    }
}
