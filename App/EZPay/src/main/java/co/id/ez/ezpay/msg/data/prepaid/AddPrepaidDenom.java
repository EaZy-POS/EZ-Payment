/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.msg.data.prepaid;

import co.id.ez.ezpay.msg.data.CentralDataRequest;
import com.json.JSONObject;
import java.math.BigDecimal;

/**
 *
 * @author Lutfi
 */
public class AddPrepaidDenom extends CentralDataRequest{
    
    private String denom, description;
    private BigDecimal salePrice;
    
    @Override
    public String getPath() {
        return "/api/prepaid/denom/add";
    }

    @Override
    public Type getType() {
        return Type.ADD_DENOM_PREPAID;
    }

    public String getDenom() {
        return denom;
    }

    public void setDenom(String denom) {
        this.denom = denom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    @Override
    public JSONObject getBodyRequest() {
        JSONObject req = super.getBodyRequest();
        req.put("denom", denom);
        req.put("description", description);
        req.put("sale_price", salePrice.doubleValue());
        
        return req;
    }
    
    
}
