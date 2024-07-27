/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.central.handler.voucher.provider;

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
public class VoucherProviderHandler extends MessageHandler {

    @Override
    public String performHandler(JSONObject request) {
        try {
            LogService.getInstance(this).trace().log("Before geting voucher provider list");
            LinkedList<JSONObject> data = controller.getVoucherProviderList();
            LogService.getInstance(this).trace().log("After geting voucher provider list");
            return constructResponse(data);
        } catch (SQLException e) {
            throw new ServiceException(RC.ERROR_DATABASE, "Failed get voucher provider list");
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

        data.stream().map(jSONObject -> {
            int id = jSONObject.getInt("id");
            String prov = jSONObject.get("provider_name").toString();
            boolean prov_active = jSONObject.getInt("status") == 1;
            JSONObject prod = new JSONObject();
            prod.put("id", id);
            prod.put("provider_name", prov);
            prod.put("active", prov_active);
            return prod;
        }).forEachOrdered(prod -> {
            detail.put(prod);
        });

        resp.put("data", detail);
        return resp.toString();
    }
}
