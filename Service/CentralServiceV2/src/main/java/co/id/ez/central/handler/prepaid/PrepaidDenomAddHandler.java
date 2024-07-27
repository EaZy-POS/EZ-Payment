/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.central.handler.prepaid;

import co.id.ez.central.handler.MessageHandler;
import co.id.ez.system.core.ex.ServiceException;
import co.id.ez.system.core.log.LogService;
import co.id.ez.system.core.rc.RC;
import com.json.JSONObject;
import java.math.BigDecimal;
import java.sql.SQLException;

/**
 *
 * @author Lutfi
 */
public class PrepaidDenomAddHandler extends MessageHandler {

    @Override
    public String performHandler(JSONObject request) {
        try {
            LogService.getInstance(this).trace().log("(" + corelation_id + ") Before adding new Prepaid Denom");
            String pDenom = request.getString("denom");
            String pDescription = request.getString("description");
            BigDecimal pSalePrice = new BigDecimal(request.get("sale_price").toString());
            controller.insertPrepaidDenomList(pDenom, pDescription, pSalePrice);
            LogService.getInstance(this).trace().log("(" + corelation_id + ") After adding new Prepaid Denom");
            return constructResponse(request);
        } catch (SQLException e) {
            throw new ServiceException(RC.ERROR_DATABASE, "(" + corelation_id + ") Failed adding new Prepaid Denom");
        }
    }

    private String constructResponse(JSONObject request) {
        JSONObject resp = new JSONObject(request.toString());

        resp.put("rc", "0000");
        resp.put("rcm", "Success");

        return resp.toString();
    }
}
