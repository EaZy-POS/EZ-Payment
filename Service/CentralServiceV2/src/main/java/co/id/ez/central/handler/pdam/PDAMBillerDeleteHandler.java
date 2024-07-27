/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.central.handler.pdam;

import co.id.ez.central.handler.MessageHandler;
import co.id.ez.system.core.ex.ServiceException;
import co.id.ez.system.core.log.LogService;
import co.id.ez.system.core.rc.RC;
import com.json.JSONObject;
import java.sql.SQLException;

/**
 *
 * @author Lutfi
 */
public class PDAMBillerDeleteHandler extends MessageHandler{

    @Override
    public String performHandler(JSONObject request) {
        try {
            LogService.getInstance(this).trace().log("(" + corelation_id + ") Before deleting PDAM biller list");
            
            String tID = request.getString("id");
            controller.deletePDAMBillerList(tID);
            
            LogService.getInstance(this).trace().log("(" + corelation_id + ") After deleting PDAM biller list");
            
            return constructResponse(request);
        } catch (SQLException e) {
            throw new ServiceException(RC.ERROR_DATABASE, "(" + corelation_id + ") Failed deleting pdam biller list");
        }
    }

    private String constructResponse(JSONObject pRequest) {
        JSONObject resp = new JSONObject(pRequest.toString());

        resp.put("rc", "0000");
        resp.put("rcm", "Success");

        return resp.toString();
    }
}
