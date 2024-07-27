/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.msg.data.voucher.provider;

import co.id.ez.ezpay.msg.data.CentralDataRequest;

/**
 *
 * @author Lutfi
 */
public class GetVoucherProvider extends CentralDataRequest{
    
    @Override
    public String getPath() {
        return "/api/voucher/provider/list";
    }

    @Override
    public Type getType() {
        return Type.PROVIDER_VOUCHER;
    }
}
