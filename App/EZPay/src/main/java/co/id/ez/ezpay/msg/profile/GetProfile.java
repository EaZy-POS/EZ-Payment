/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.msg.profile;

/**
 *
 * @author Lutfi
 */
public class GetProfile extends ProfileDataRequest{

    @Override
    public String getPath() {
        return "/api/mitra/profile";
    }

    @Override
    public Type getType() {
        return Type.GET_PROFILE;
    }

}
