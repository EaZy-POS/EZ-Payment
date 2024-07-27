/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.central.handler.module;

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
public class ModuleUpdateHandler extends MessageHandler {

    @Override
    public String performHandler(JSONObject request) {
        try {
            LogService.getInstance(this).trace()
                    .log("(" + corelation_id + ") Before update module");

            String tID = request.getString("id");
            String tModuleID = request.getString("module_id");
            String tModuleName = request.getString("module_name");
            boolean tIsNeddSign = request.getBoolean("is_need_sign");
            String tStatus = request.getString("status");

            controller.updateModule(tID, tModuleID, tModuleName, tIsNeddSign, tStatus);
            LogService.getInstance(this).trace()
                    .log("(" + corelation_id + ") After updating module (Success)");
            return constructResponse(request);
        } catch (SQLException e) {
            throw new ServiceException(RC.ERROR_DATABASE, "(" + corelation_id + ") Failed updating module data");
        }
    }

    private String constructResponse(JSONObject request) {
        JSONObject resp = new JSONObject(request.toString());
        resp.put("rc", "0000");
        resp.put("rcm", "Success");
        return resp.toString();
    }
}
