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
public class PDAMBillerHandler extends MessageHandler{

    @Override
    public String performHandler(JSONObject request) {
        try {
            LogService.getInstance(this).trace().log("(" + corelation_id + ") Before get PDAM biller list");
            LinkedList<JSONObject> data = controller.getPDAMBillerList();
            return constructResponse(data);
        } catch (SQLException e) {
            throw new ServiceException(RC.ERROR_DATABASE, "(" + corelation_id + ") Failed get pdam biller list");
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
            String biller = jSONObject.get("biller").toString();
            String billername = jSONObject.get("biller_name").toString();
            String distric = jSONObject.get("distric").toString();
            boolean active = jSONObject.getInt("status") == 1;
            JSONObject detailData = new JSONObject();
            detailData.put("biller", biller);
            detailData.put("biller_name", billername);
            detailData.put("distric", distric);
            detailData.put("active", active);
            return detailData;
        }).forEachOrdered(detailData -> {
            detail.put(detailData);
        });

        resp.put("data", detail);
        return resp.toString();
    }
}
