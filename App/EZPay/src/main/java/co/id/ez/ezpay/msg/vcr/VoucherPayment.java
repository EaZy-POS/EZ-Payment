/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.msg.vcr;

import co.id.ez.ezpay.enums.RequestType;
import co.id.ez.ezpay.model.data.search.VoucherModel;
import co.id.ez.ezpay.msg.BillerMessage;
import co.id.ez.ezpay.msg.BillerResponse;
import com.json.JSONObject;
import java.math.BigDecimal;

/**
 *
 * @author Lutfi
 */
public class VoucherPayment extends BillerMessage{
    
    private final BillerResponse billerResponse;
    private final BigDecimal sale_price;

    public VoucherPayment(BillerResponse billerresponse, VoucherModel productModel) {
        super(RequestType.VOUCHER, "ISI");
        this.billerResponse = billerresponse;
        sale_price = productModel.getPrice_sale();
    }

    @Override
    public JSONObject getBodyRequest() {
        JSONObject tRequest = billerResponse.getPayload();
        tRequest.remove("rc");
        tRequest.remove("rcm");
        tRequest.remove("text");
        tRequest.remove("status");
        
        tRequest.put("harga_jual", sale_price.doubleValue())
                .put("modul", getModuleid())
                .put("command", getCommand());
        
        return tRequest;
    }

    @Override
    public String getPath() {
        return "/api/voucher/pay";
    }

    @Override
    public String getCommand() {
        return "PAY";
    }
    
    
    
}
