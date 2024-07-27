/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.msg.data;

import com.json.JSONObject;

/**
 *
 * @author Lutfi
 */
public class GetMutasiSaldo extends CentralDataRequest{

    private final String start_date, end_date;

    public GetMutasiSaldo(String start_date, String end_date) {
        this.start_date = start_date;
        this.end_date = end_date;
    }
    
    @Override
    public String getPath() {
        return "/api/mitra/getstatement";
    }

    @Override
    public Type getType() {
        return Type.GET_MUTASI;
    }
    
    @Override
    public JSONObject getBodyRequest(){
        JSONObject req = super.getBodyRequest();
        req.put("from_date", start_date)
                .put("until_date", end_date);
        
        return req;
    }

}
