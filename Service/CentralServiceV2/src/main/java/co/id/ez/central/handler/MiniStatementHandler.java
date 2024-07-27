/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.central.handler;

import co.id.ez.central.resource.CommonHanlder;
import co.id.ez.system.core.config.ConfigService;
import co.id.ez.system.core.ex.ServiceException;
import co.id.ez.system.core.log.LogService;
import co.id.ez.system.core.rc.RC;
import com.json.JSONArray;
import com.json.JSONObject;

/**
 *
 * @author Lutfi
 */
public class MiniStatementHandler extends MessageHandler{

    @Override
    public String performHandler(JSONObject request) {
        LogService.getInstance(this).trace().log("(" + corelation_id + ") Before handle get ministatement");

        String dash = getDasAccount();

        LogService.getInstance(this).trace().log("dash: "+ dash);
        JSONArray data = getMiniStatement(contructMiniStatementRequest(request, dash));

        request.put("data", data);
        request.put("rc", data.length() > 0 ? "0000" : "0014");
        request.put("rcm", "Sukses");
        LogService.getInstance(this).trace().log("(" + corelation_id + ") After handle ministatement");
        return request.toString();
    }
    
    private JSONObject contructMiniStatementRequest(JSONObject pRequest, String dash){
        JSONObject tObjec = new JSONObject();
        tObjec.put("das_id", dash);
        tObjec.put("from_date", pRequest.get("from_date").toString());
        tObjec.put("until_date", pRequest.get("until_date").toString());
        return tObjec;
    }
    
    private JSONArray getMiniStatement(JSONObject pRequest) {
        LogService.getInstance(this).trace().log("(" + corelation_id + ") Before send request ministatement to deposit");
        String tUrl = ConfigService.getInstance()
                .getString(CommonHanlder.cDepMinistatementKey, "http://127.0.0.1:6567/api/deposit/statement");
        String tAuthResponse = sendPostRequest(pRequest, tUrl, "DEP-SERVICE");

        if (!tAuthResponse.equals("") && tAuthResponse.startsWith("{")) {
            JSONObject tResponse = new JSONObject(tAuthResponse);
            String tRc = tResponse.getString("rc").substring(3);
            if (!tRc.equals("0000") && !tRc.equals("0014")) {
                LogService.getInstance(this).trace()
                        .log("(" + corelation_id + ") After send request ministatement to deposit (failed)");
                throw new ServiceException(RC.parseResponseCodeString(tRc), tResponse.getString("rcm"));
            }else{
                LogService.getInstance(this).trace()
                        .log("(" + corelation_id + ") After send request ministatement to deposit (Success)");
                return tResponse.getJSONArray("data");
            }
        }else{
            LogService.getInstance(this).trace()
                    .log("(" + corelation_id + ") After send request ministatement to deposit (Failed)");
            throw new ServiceException(RC.ERROR_OTHER, 
                    "(" + corelation_id + ") Get null/empty response from auth. [" + tAuthResponse + "]");
        }
        
    }
}
