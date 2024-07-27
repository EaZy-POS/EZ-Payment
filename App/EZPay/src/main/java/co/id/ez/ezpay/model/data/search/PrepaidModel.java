/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.model.data.search;

import co.id.ez.ezpay.interfaces.Model;
import java.math.BigDecimal;

/**
 *
 * @author Lutfi
 */
public class PrepaidModel implements Model{

    private final String description;
    private final BigDecimal denom, price_sale;

    public PrepaidModel(String description, BigDecimal denom, BigDecimal price_sale) {
        this.description = description;
        this.denom = denom;
        this.price_sale = price_sale;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getDenom() {
        return denom;
    }
    
    @Override
    public String getKey() {
        return getDescription();
    }

    @Override
    public String getValue() {
        return getDescription();
    }

    @Override
    public String getInfo() {
        return getDescription();
    }

    public BigDecimal getPrice_sale() {
        return price_sale;
    }
    
}
