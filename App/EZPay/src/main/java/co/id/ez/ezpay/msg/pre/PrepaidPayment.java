/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.msg.pre;

import co.id.ez.ezpay.enums.RequestType;
import co.id.ez.ezpay.model.data.search.PrepaidModel;
import co.id.ez.ezpay.msg.BillerMessage;
import co.id.ez.ezpay.msg.BillerResponse;
import com.json.JSONObject;
import java.math.BigDecimal;

/**
 *
 * @author Lutfi
 */
public class PrepaidPayment extends BillerMessage{
    
    private final BillerResponse billerResponse;
    private final BigDecimal price_sale;

    public PrepaidPayment(BillerResponse billerresponse, PrepaidModel product) {
        super(RequestType.PREPAID, "PRE");
        this.billerResponse = billerresponse;
        this.price_sale = product.getPrice_sale();
    }

    @Override
    public JSONObject getBodyRequest() {
        JSONObject tRequest = billerResponse.getPayload();
        JSONObject tPayReq = new JSONObject();
        tPayReq.put("harga_jual", price_sale.toPlainString())
                .put("trxid", tRequest.get("trxid").toString())
                .put("nominal", tRequest.get("nominal").toString())
                .put("MSN", tRequest.get("MSN").toString())
                .put("modul", getModuleid())
                .put("command", getCommand());
        
        return tPayReq;
    }

    @Override
    public String getPath() {
        return "/api/prepaid/pay";
    }

    @Override
    public String getCommand() {
        return "PAY";
    }
    
    
    
}
