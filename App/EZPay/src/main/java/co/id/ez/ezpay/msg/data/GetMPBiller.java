/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.msg.data;

/**
 *
 * @author Lutfi
 */
public class GetMPBiller extends CentralDataRequest{

    @Override
    public String getPath() {
        return "/api/mp/biller/list";
    }

    @Override
    public Type getType() {
        return Type.BILLER_MP;
    }

}
