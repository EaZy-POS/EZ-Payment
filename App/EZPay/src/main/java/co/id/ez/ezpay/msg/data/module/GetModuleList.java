/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.msg.data.module;

import co.id.ez.ezpay.msg.data.CentralDataRequest;

/**
 *
 * @author Lutfi
 */
public class GetModuleList extends CentralDataRequest{

    @Override
    public String getPath() {
        return "/api/module/list";
    }

    @Override
    public Type getType() {
        return Type.MODULE_LIST;
    }

}
