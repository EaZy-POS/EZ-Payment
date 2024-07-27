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
import com.json.JSONObject;
import java.sql.SQLException;

/**
 *
 * @author Lutfi
 */
public class VoucherProviderAddHandler extends MessageHandler {

    @Override
    public String performHandler(JSONObject request) {
        try {
            LogService.getInstance(this).trace().log("Before adding voucher provider list");
            String pProviderName = request.getString("provider_name");
            controller.insertVoucherProviderlist(pProviderName);
            LogService.getInstance(this).trace().log("After adding voucher provider list");
            return constructResponse(request);
        } catch (SQLException e) {
            throw new ServiceException(RC.ERROR_DATABASE, "Failed adding voucher provider list");
        }
    }

    private String constructResponse(JSONObject request) {
        JSONObject resp = new JSONObject(request.toString());

        resp.put("rc", "0000");
        resp.put("rcm", "Success");

        return resp.toString();
    }
}
