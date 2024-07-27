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
import com.json.JSONObject;
import java.sql.SQLException;

/**
 *
 * @author Lutfi
 */
public class VoucherProviderPrefixUpdateHandler extends MessageHandler {

    @Override
    public String performHandler(JSONObject request) {
        try {
            LogService.getInstance(this).trace().log("Before updating voucher provider prefix list");
            String pID = request.getString("id");
            String pProviderID = request.getString("provider_id");
            String pPrefix = request.getString("prefix");
            controller.updateVoucherProviderPrefixlist(pID, pProviderID, pPrefix);
            LogService.getInstance(this).trace().log("After updating voucher provider prefix list");
            return constructResponse(request);
        } catch (SQLException e) {
            throw new ServiceException(RC.ERROR_DATABASE, "Failed updating voucher provider prefix list");
        }
    }

    private String constructResponse(JSONObject request) {
        JSONObject resp = new JSONObject(request.toString());

        resp.put("rc", "0000");
        resp.put("rcm", "Success");

        return resp.toString();
    }
}