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
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.LinkedList;

/**
 *
 * @author Lutfi
 */
public class VoucherProductHandler extends MessageHandler{

    @Override
    public String performHandler(JSONObject request) {
        try {
            LogService.getInstance(this).trace().log("Before get voucher product list");
            LinkedList<JSONObject> data = controller.getVoucherProductList();
            return constructResponse(data);
        } catch (SQLException e) {
            throw new ServiceException(RC.ERROR_DATABASE, "Failed get voucher product list");
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
        LinkedHashMap<Integer, JSONObject> listDetail = new LinkedHashMap<>();
        
        for (JSONObject jSONObject : data) {
            int id = jSONObject.getInt("id");
            String prov = jSONObject.get("provider_name").toString();
            boolean prov_active = jSONObject.getInt("prov_active") == 1;
            String prefix = jSONObject.has("prefix") ? jSONObject.get("prefix").toString() : "";
            String vcrid = jSONObject.get("voucher_id").toString();
            String vcrname = jSONObject.get("voucher_name").toString();
            String product_type = jSONObject.has("product_type") 
                    ? jSONObject.get("product_type").toString() 
                    : "";
            BigDecimal price = new BigDecimal(jSONObject.getDouble("price"));
            boolean prod_active = jSONObject.getInt("status") == 1;
            
            if(listDetail.containsKey(id)){
                JSONArray prefixlis = listDetail.get(id).getJSONArray("prefix");
                boolean isExsit = false;
                if (prefix.length() > 0) {
                    for (int i = 0; i < prefixlis.length(); i++) {
                        if (prefixlis.get(i).toString().trim().equals(prefix.trim())) {
                            isExsit = true;
                            break;
                        }
                    }

                    if (!isExsit) {
                        listDetail.get(id).getJSONArray("prefix").put(prefix);
                    }
                }
                
                prefixlis = listDetail.get(id).getJSONArray("voucher");
                isExsit = false;
                for (int i = 0; i < prefixlis.length(); i++) {
                    JSONObject detailData = prefixlis.getJSONObject(i);
                    if(detailData.getString("voucher_id").trim().equals(vcrid.trim())){
                        isExsit = true;
                        break;
                    }
                }
                
                if (!isExsit) {
                    JSONObject prod = new JSONObject();
                    prod.put("voucher_id", vcrid);
                    prod.put("voucher_name", vcrname);
                    prod.put("product_type", product_type);
                    prod.put("price", price.doubleValue());
                    prod.put("active", prod_active);
                    
                    listDetail.get(id).getJSONArray("voucher").put(prod);
                }
            }else{
                JSONObject detailData = new JSONObject();
                
                detailData.put("id", id);
                detailData.put("provider", prov);
                detailData.put("active", prov_active);
                detailData.put("prefix", 
                        prefix.length() > 0 ? 
                                new JSONArray().put(prefix)  
                                : new JSONArray()
                );
                
                JSONObject prod = new JSONObject();
                prod.put("voucher_id", vcrid);
                prod.put("voucher_name", vcrname);
                prod.put("product_type", product_type);
                prod.put("price", price.doubleValue());
                prod.put("active", prod_active);
                
                detailData.put("voucher", new JSONArray().put(prod));
                listDetail.put(id, detailData);
            }
        }
        
        listDetail.values().forEach(object -> {
            detail.put(object);
        });
        
        resp.put("data", detail);
        return resp.toString();
    }
}
