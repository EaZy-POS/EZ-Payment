/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.model.data.detail;

import co.id.ez.ezpay.interfaces.Model;

/**
 *
 * @author Lutfi
 */
public class DetailModel implements Model{

    private final String data;

    public DetailModel(String data) {
        this.data = data;
    }

    @Override
    public String getKey() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getValue() {
        return data;
    }

    @Override
    public String getInfo() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    
}
