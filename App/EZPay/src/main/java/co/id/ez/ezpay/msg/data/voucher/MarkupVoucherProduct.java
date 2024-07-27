/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.msg.data.voucher;

import co.id.ez.ezpay.msg.data.CentralDataRequest;
import com.json.JSONObject;

/**
 *
 * @author Lutfi
 */
public class MarkupVoucherProduct extends CentralDataRequest{
    
    private String markup;

    public String getMarkup() {
        return markup;
    }

    public void setMarkup(String markup) {
        this.markup = markup;
    }
    
    @Override
    public String getPath() {
        return "/api/voucher/product/markup";
    }

    @Override
    public Type getType() {
        return Type.MARKUP_PRODUK_VOUCHER;
    }

    @Override
    public JSONObject getBodyRequest() {
        JSONObject req =  super.getBodyRequest();
        req.put("markup", markup);
        
        return req;
    }
}
