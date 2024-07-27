/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.msg.data.pdam;

import co.id.ez.ezpay.msg.data.CentralDataRequest;

/**
 *
 * @author Lutfi
 */
public class GetPDAMBiller extends CentralDataRequest{
    
    @Override
    public String getPath() {
        return "/api/pdam/biller/list";
    }

    @Override
    public Type getType() {
        return Type.BILLER_PDAM;
    }
}
