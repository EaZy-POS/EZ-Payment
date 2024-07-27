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
import com.json.JSONObject;
import java.math.BigDecimal;

/**
 *
 * @author Lutfi
 */
public class BalanceHandler extends MessageHandler{

    @Override
    public String performHandler(JSONObject request) {
        LogService.getInstance(this).trace().log("(" + corelation_id + ") Before handle get balance");

        String dash = EncryptionService.encryptor().Base64Encrypt(
                EncryptionService.encryptor()
                        .encrypt(reqMap.getClient_id(), reqMap.getSecret_key()));

        LogService.getInstance(this).trace().log("dash: "+ dash);
        BigDecimal bal = getSaldoDeposit(contructGetSaldoRequest(dash));

        request.put("balance", bal.toPlainString());
        request.put("rc", "0000");
        request.put("rcm", "Sukses");
        LogService.getInstance(this).trace().log("(" + corelation_id + ") After handle get balance");
        return request.toString();
    }
    
    private JSONObject contructGetSaldoRequest(String dash){
        JSONObject tObjec = new JSONObject();
        tObjec.put("das_id", dash);
        return tObjec;
    }
    
    private BigDecimal getSaldoDeposit(JSONObject pRequest) {
        BigDecimal result = BigDecimal.ZERO;
        LogService.getInstance(this).trace().log("(" + corelation_id + ") Before send request to deposit");
        String tUrl = ConfigService.getInstance()
                .getString(CommonHanlder.cDepBalanceKey, "http://127.0.0.1:6567/api/deposit/balance");
        String tAuthResponse = sendPostRequest(pRequest, tUrl, "DEP-SERVICE");

        if (!tAuthResponse.equals("") && tAuthResponse.startsWith("{")) {
            JSONObject tResponse = new JSONObject(tAuthResponse);
            String tRc = tResponse.getString("rc").substring(3);
            if (!tRc.equals("0000")) {
                LogService.getInstance(this).trace().log("(" + corelation_id + ") After send request to deposit (failed)");
                throw new ServiceException(RC.parseResponseCodeString(tRc), tResponse.getString("rcm"));
            }else{
                result = new BigDecimal(tResponse.getDouble("balance"));
            }
        }else{
            throw new ServiceException(RC.ERROR_OTHER, "(" + corelation_id + ") Get null/empty response from auth. [" + tAuthResponse + "]");
        }
        LogService.getInstance(this).trace().log("(" + corelation_id + ") After send request to deposit (Success)");
        return result;
    }
}
