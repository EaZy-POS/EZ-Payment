/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.central.handler.voucher;

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
public class VoucherProductAddHandler extends MessageHandler {

    @Override
    public String performHandler(JSONObject request) {
        try {
            LogService.getInstance(this).trace().log("Before adding voucher product list");
            
            String pProviderID = request.getString("provider_id");
            String pVoucherID = request.getString("voucher_id");
            String pVoucherName = request.getString("voucher_name");
            String pType = request.getString("product_type");
            String pPrice = request.get("price").toString();
            controller.insertVoucherProductlist(pProviderID, pVoucherID, pVoucherName, pType, pPrice);
            
            LogService.getInstance(this).trace().log("After adding voucher product list");
            return constructResponse(request);
        } catch (SQLException e) {
            throw new ServiceException(RC.ERROR_DATABASE, "Failed adding voucher product list");
        }
    }

    private String constructResponse(JSONObject request) {
        JSONObject resp = new JSONObject(request.toString());

        resp.put("rc", "0000");
        resp.put("rcm", "Success");

        return resp.toString();
    }
}
