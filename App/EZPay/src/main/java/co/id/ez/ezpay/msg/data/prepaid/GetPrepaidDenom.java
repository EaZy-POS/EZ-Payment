/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.msg.data.prepaid;

import co.id.ez.ezpay.msg.data.CentralDataRequest;

/**
 *
 * @author Lutfi
 */
public class GetPrepaidDenom extends CentralDataRequest{
    
    @Override
    public String getPath() {
        return "/api/prepaid/denom/list";
    }

    @Override
    public Type getType() {
        return Type.DENOM_PREPAID;
    }
}
