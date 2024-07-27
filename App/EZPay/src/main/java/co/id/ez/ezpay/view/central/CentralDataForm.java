/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.view.central;

import co.id.ez.ezpay.abstracts.AbstractViewLaporan;
import co.id.ez.ezpay.app.Common;
import co.id.ez.ezpay.interfaces.DataTable;
import co.id.ez.ezpay.msg.BillerResponse;
import co.id.ez.ezpay.msg.data.CentralDataRequest;
import co.id.ez.ezpay.util.swings.table.TableActionEvent;
import co.id.ez.ezpay.util.swings.table.TableHeader;
import com.json.JSONObject;
import java.util.LinkedList;

/**
 *
 * @author Lutfi
 * @param <T>
 */
public abstract class CentralDataForm<T extends DataTable> extends AbstractViewLaporan{
    
    public CentralDataForm(LinkedList<TableHeader> pTableHeader, boolean isSearchable, boolean isPrintable) {
        super(pTableHeader, isSearchable, isPrintable);
    }
    
    public abstract String getDataName();
    public abstract TableActionEvent getTableActionEvent();
    public abstract void delete(T model);
    public abstract void editData(T model);
    public abstract void simpan();
    
    public JSONObject sendRequetToCentral(CentralDataRequest req, String pMethod){
        try {
            BillerResponse resp = sendRequest(req, pMethod, true);

            if (resp != null) {
                JSONObject respObj = resp.getPayload();
                return respObj;
            }
        } catch (Exception e) {
            Common.errorLog("[SQLException] Failed execute send data to centrals ", e);
        }
        
        return null;
    }
    
    public boolean isContainsData(String pSearchData, String pData){
        return pData.toLowerCase().replace(" ", "")
                .contains(pSearchData.toLowerCase().replace(" ", ""));
    }
}
