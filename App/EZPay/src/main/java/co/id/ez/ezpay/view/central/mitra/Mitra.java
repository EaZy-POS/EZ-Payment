/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.view.central.mitra;

import co.id.ez.ezpay.model.data.central.CentralMitraModel;
import co.id.ez.ezpay.msg.data.mitra.UpdateMitra;
import javax.swing.JPanel;

/**
 *
 * @author RCS
 */
public abstract class Mitra extends JPanel{
    
    public abstract void initData(CentralMitraModel model);
    public abstract void editData(CentralMitraModel model);
    public abstract void initForm();
    public abstract void resetForm();
    public abstract boolean validateForm();
    public abstract void getData(final UpdateMitra request);
    
    public String getHiddenString(String pTarget){
        String tResult = "*";
        for (int i = 0; i < pTarget.length(); i++) {
            for (int j = 0; j < 4; j++) {
                tResult = tResult.concat("*");
            }
        }
        
        return tResult;
    }
}
