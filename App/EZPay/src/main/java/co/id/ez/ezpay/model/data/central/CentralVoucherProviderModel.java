/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.model.data.central;

import co.id.ez.ezpay.interfaces.DataTable;

/**
 *
 * @author Lutfi
 */
public class CentralVoucherProviderModel implements DataTable{

    private int number;
    private final String id, providerName, status, aksi;

    public CentralVoucherProviderModel(int number, String id, String providerName, String status, String aksi) {
        this.number = number;
        this.id = id;
        this.providerName = providerName;
        this.status = status;
        this.aksi = aksi;
    }

    public int getNumber() {
        return number;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public Object[] getArrayData() {
        return new Object[]{
            number, 
            providerName,
            status, 
            aksi
        };
    }
}
