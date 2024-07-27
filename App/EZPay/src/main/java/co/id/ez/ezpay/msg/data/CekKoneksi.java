/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.msg.data;

import co.id.ez.ezpay.enums.RequestType;
import co.id.ez.ezpay.msg.BillerMessage;
import com.json.JSONObject;

/**
 *
 * @author Lutfi
 */
public class CekKoneksi extends BillerMessage{

    public CekKoneksi() {
        super(RequestType.CEKKONEKSI, "");
    }

    @Override
    public String getPath() {
        return "/cek_koneksi";
    }

    @Override
    public JSONObject getBodyRequest(){
        JSONObject req = new JSONObject();
        return req;
    }

    @Override
    public String getCommand() {
        return "";
    }

    @Override
    public boolean isNeedHeader() {
        return false;
    }

}
