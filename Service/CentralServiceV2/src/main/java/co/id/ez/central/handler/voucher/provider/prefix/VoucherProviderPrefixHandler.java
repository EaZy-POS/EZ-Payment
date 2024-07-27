/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.central.handler.voucher.provider.prefix;

import co.id.ez.central.handler.MessageHandler;
import co.id.ez.system.core.ex.ServiceException;
import co.id.ez.system.core.log.LogService;
import co.id.ez.system.core.rc.RC;
import com.json.JSONArray;
import com.json.JSONObject;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Lutfi
 */
public class VoucherProviderPrefixHandler extends MessageHandler {

    @Override
    public String performHandler(JSONObject request) {
        try {
            LogService.getInstance(this).trace().log("Before geting voucher provider prefix list");
            LinkedList<JSONObject> data = controller.getVoucherProviderPrefixList();
            LogService.getInstance(this).trace().log("After geting voucher provider prefix list");
            return constructResponse(data);
        } catch (SQLException e) {
            throw new ServiceException(RC.ERROR_DATABASE, "Failed get voucher provider prefix list");
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
        LinkedHashMap<Integer, List<JSONObject>> listDetail = new LinkedHashMap<>();

        data.forEach(jSONObject -> {
            int id = jSONObject.has("id") ? jSONObject.getInt("id") : 0;
            int provider_id = jSONObject.getInt("provider_id");
            String prov = jSONObject.get("provider_name").toString().trim();
            String prefix = jSONObject.has("prefix") ? jSONObject.get("prefix").toString() : "";
            JSONObject prod = new JSONObject();
            prod.put("id", id);
            prod.put("prefix", prefix);
            prod.put("provider_name", prov);

            if (listDetail.containsKey(provider_id)) {
                listDetail.get(provider_id).add(prod);
            } else {
                List<JSONObject> list = new ArrayList<>();
                list.add(prod);
                listDetail.put(provider_id, list);
            }
        });

        listDetail.keySet().stream().map(id -> {
            JSONObject valueData = new JSONObject();
            valueData.put("provider_id", id);
            JSONArray listPrefix = new JSONArray();
            listDetail.get(id).stream().map(dataPrefix -> {
                valueData.put("provider_name", dataPrefix.getString("provider_name"));
                return dataPrefix;
            }).map(dataPrefix -> {
                dataPrefix.remove("provider_name");
                return dataPrefix;
            }).forEachOrdered(dataPrefix -> {
                if(dataPrefix.getInt("id") > 0){
                    listPrefix.put(dataPrefix);
                }
            });
            valueData.put("prefix_list", listPrefix);
            return valueData;
        }).forEachOrdered(valueData -> {
            detail.put(valueData);
        });

        resp.put("data", detail);
        return resp.toString();
    }
}
