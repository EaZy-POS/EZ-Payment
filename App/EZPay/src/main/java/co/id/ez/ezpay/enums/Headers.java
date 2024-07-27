/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.enums;

/**
 *
 * @author RCS
 */
public enum Headers {
    
    DOWN_MAC("client_id"),
    ACC_POCKY("secret_key"),
    AUTH("Authorization"),
    MODULID("module_id");
    
    private final String key;

    private Headers(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
    
    
}
