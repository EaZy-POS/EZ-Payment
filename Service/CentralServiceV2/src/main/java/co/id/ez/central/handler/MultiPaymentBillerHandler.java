/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.central.handler;

import co.id.ez.system.core.ex.ServiceException;
import co.id.ez.system.core.log.LogService;
import co.id.ez.system.core.rc.RC;
import com.json.JSONArray;
import com.json.JSONObject;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 *
 * @author Lutfi
 */
public class MultiPaymentBillerHandler extends MessageHandler{

    @Override
    public String performHandler(JSONObject request) {
        try {
            LogService.getInstance(this).trace()
                    .log("(" + corelation_id + ") Before get Multi Payment biller list");
            LinkedList<JSONObject> data = controller.getMultiPaymentBillerList();
            LogService.getInstance(this).trace()
                    .log("(" + corelation_id + ") After get Multi Payment biller list (Success)");
            return constructResponse(data);
        } catch (SQLException e) {
            throw new ServiceException(RC.ERROR_DATABASE, "(" + corelation_id + ") Failed get multi payment biller list");
        }
    }
    
    private String constructResponse(LinkedList<JSONObject> data){
        JSONObject resp = new JSONObject();
        
        if(data.size() > 0){
            resp.put("rc", "0000");
            resp.put("rcm", "Success");
        }else{
            resp.put("rc", "0014");
            resp.put("rcm", "Data Not Found");
        }
        
        JSONArray detail = new JSONArray();
        
        data.stream().map(jSONObject -> {
            String tBiller = jSONObject.getString("biller");
            String tBillerName = jSONObject.getString("biller_name");
            boolean active = jSONObject.getInt("status") == 1;
            String[] details = jSONObject.has("details")
                    ? jSONObject.get("details").toString()
                            .replace("[", "").replace("]", "").split(";") : new String[0];
            JSONObject billerData = new JSONObject();
            billerData.put("biller", tBiller);
            billerData.put("biller_name", tBillerName);
            billerData.put("active", active);
            billerData.put("details", new JSONArray(details));
            JSONArray input = new JSONArray();
            for (int i = 0; i < 3; i++) {
                if(jSONObject.has("input_"+ (i+1) +"_label") &&
                        jSONObject.has("input_"+ (i+1) +"_type")){
                    String label = jSONObject.getString("input_"+ (i+1) +"_label");
                    JSONObject type = new JSONObject(jSONObject.get("input_"+ (i+1) +"_type").toString());
                    
                    JSONObject inputData = new JSONObject();
                    inputData.put("label", label);
                    inputData.put("type", type);
                    input.put(inputData);
                }
            }
            billerData.put("input", input);
            return billerData;
        }).forEachOrdered(billerData -> {
            detail.put(billerData);
        });

        resp.put("data", detail);
        return resp.toString();
    }
}
