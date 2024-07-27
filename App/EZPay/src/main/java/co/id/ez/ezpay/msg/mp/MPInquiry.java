/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.msg.mp;

import co.id.ez.ezpay.enums.RequestType;
import co.id.ez.ezpay.model.data.search.MultiPaymentBiller;
import co.id.ez.ezpay.msg.BillerMessage;
import com.json.JSONObject;

/**
 *
 * @author Lutfi
 */
public class MPInquiry extends BillerMessage{

    private final MultiPaymentBiller biller;
    
    public MPInquiry(MultiPaymentBiller biller) {
        super(RequestType.parse(biller.getBiller()), "GP");
        this.biller = biller;
    }

    @Override
    public JSONObject getBodyRequest(){
        JSONObject req = new JSONObject();
        req.put("modul", getModuleid())
                .put("biller", biller.getBiller())
                .put("command", getCommand());
        
        int index = 0;
        for (MultiPaymentBiller.Input input : biller.getInputList()) {
            req.put("input" + (index + 1), input.getValue());
            index++;
        }
        
        return req;
    }

    @Override
    public String getPath() {
        return "/api/mp/inq";
    }

    @Override
    public String getCommand() {
        return "INQ";
    }

}
