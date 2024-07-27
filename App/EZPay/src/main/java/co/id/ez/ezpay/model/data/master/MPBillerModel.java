/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.model.data.master;

import co.id.ez.ezpay.interfaces.DataTable;

/**
 *
 * @author Lutfi
 */
public class MPBillerModel implements DataTable{

    private final int number;
    private final String biller, billerName, status;

    public MPBillerModel(int number, String biller, String billerName, String status) {
        this.number = number;
        this.biller = biller;
        this.billerName = billerName;
        this.status = status;
    }

    public int getNumber() {
        return number;
    }

    public String getBiller() {
        return biller;
    }

    public String getBillerName() {
        return billerName;
    }

    public String getStatus() {
        return status;
    }
    
    @Override
    public Object[] getArrayData() {
        return new Object[]{number, biller, billerName, status};
    }
    
}
