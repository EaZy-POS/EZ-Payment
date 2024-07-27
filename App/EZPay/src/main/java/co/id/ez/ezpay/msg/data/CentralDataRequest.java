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
public abstract class CentralDataRequest extends BillerMessage{
    
    
    public CentralDataRequest() {
        super(RequestType.CTLDATA, "");
    }

    public abstract Type getType();

    @Override
    public JSONObject getBodyRequest(){
        JSONObject req = new JSONObject();
        return req;
    }

    @Override
    public String getCommand() {
        return "";
    }

    public enum Type{
        BILLER_PDAM,
        ADD_BILLER_PDAM,
        UPDATE_BILLER_PDAM,
        DELETE_BILLER_PDAM,
        BILLER_MP,
        PRODUK_VOUCHER,
        ADD_PRODUK_VOUCHER,
        UPDATE_PRODUK_VOUCHER,
        DELETE_PRODUK_VOUCHER,
        MARKUP_PRODUK_VOUCHER,
        DENOM_PREPAID,
        ADD_DENOM_PREPAID,
        UPDATE_DENOM_PREPAID,
        DELETE_DENOM_PREPAID,
        GET_SALDO,
        GET_MUTASI,
        CEK_KONEKSI,
        MODULE_LIST,
        ADD_MODULE,
        UPDATE_MODULE,
        DELETE_MODULE,
        MODULE_SYSLIST_LIST,
        ADD_MODULE_SYSLIST,
        UPDATE_MODULE_SYSLIST,
        DELETE_MODULE_SYSLIST,
        PROVIDER_VOUCHER,
        ADD_PROVIDER_VOUCHER,
        UPDATE_PROVIDER_VOUCHER,
        DELETE_PROVIDER_VOUCHER,
        PREFIX_PROVIDER_VOUCHER,
        ADD_PREFIX_PROVIDER_VOUCHER,
        UPDATE_PREFIX_PROVIDER_VOUCHER,
        DELETE_PREFIX_PROVIDER_VOUCHER,
        MITRA_LIST,
        AKTIFASI_MITRA,
        DELETE_MITRA
    }
}
