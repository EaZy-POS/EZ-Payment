/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.msg.data.modulesyslist;

import co.id.ez.ezpay.msg.data.CentralDataRequest;

/**
 *
 * @author Lutfi
 */
public class GetModuleSysList extends CentralDataRequest{

    @Override
    public String getPath() {
        return "/api/module/syslist/list";
    }

    @Override
    public Type getType() {
        return Type.MODULE_SYSLIST_LIST;
    }

}
