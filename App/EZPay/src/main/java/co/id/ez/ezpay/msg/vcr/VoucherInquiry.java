/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.msg.vcr;

import co.id.ez.ezpay.enums.RequestType;
import co.id.ez.ezpay.msg.BillerMessage;
import com.json.JSONObject;

/**
 *
 * @author Lutfi
 */
public class VoucherInquiry extends BillerMessage{

    private String voucherid, tujuan;
    
    public VoucherInquiry() {
        super(RequestType.VOUCHER, "ISI");
    }

    @Override
    public JSONObject getBodyRequest(){
        JSONObject req = new JSONObject();
        req.put("voucherid", voucherid)
                .put("tujuan", tujuan)
                .put("modul", getModuleid())
                .put("command", getCommand());
        return req;
    }

    @Override
    public String getPath() {
        return "/api/voucher/inq";
    }

    @Override
    public String getCommand() {
        return "INQ";
    }

    public String getVoucherid() {
        return voucherid;
    }

    public void setVoucherid(String voucherid) {
        this.voucherid = voucherid;
    }

    public String getTujuan() {
        return tujuan;
    }

    public void setTujuan(String tujuan) {
        this.tujuan = tujuan;
    }

}
