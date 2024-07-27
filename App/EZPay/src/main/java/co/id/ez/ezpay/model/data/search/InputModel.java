/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.model.data.search;

import co.id.ez.ezpay.interfaces.Model;

/**
 *
 * @author Lutfi
 */
public class InputModel implements Model{

    private final String key, value;

    public InputModel(String key, String value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public String getInfo() {
        return value;
    }

}
