/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.central.handler.mitra;

import co.id.ez.central.handler.MessageHandler;
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
public class MitraListHandler extends MessageHandler {

    @Override
    public String performHandler(JSONObject request) {
        try {
            LogService.getInstance(this).trace().log("Before geting mitra list");
            LinkedList<JSONObject> data = controller.getMitraList();
            LogService.getInstance(this).trace().log("After geting mitra list");
            return constructResponse(data);
        } catch (SQLException e) {
            throw new ServiceException(RC.ERROR_DATABASE, "Failed get mitra list");
        }
    }

    private String constructResponse(LinkedList<JSONObject> data) {
        JSONObject resp = new JSONObject();

        if (data.size() > 0) {
            resp.put("rc", "0000");
            resp.put("rcm", "Success");
        } else {
            resp.put("rc", "0014");
            resp.put("rcm", "Data Not Found");
        }

        JSONArray detail = new JSONArray();

        data.forEach(object -> {
            detail.put(object);
        });

        resp.put("data", detail);
        return resp.toString();
    }
}
