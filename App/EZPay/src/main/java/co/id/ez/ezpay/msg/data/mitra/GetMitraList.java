/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.msg.data.mitra;

import co.id.ez.ezpay.msg.data.CentralDataRequest;

/**
 *
 * @author Lutfi
 */
public class GetMitraList extends CentralDataRequest{
    
    @Override
    public String getPath() {
        return "/api/mitra/list";
    }

    @Override
    public Type getType() {
        return Type.MITRA_LIST;
    }
}
