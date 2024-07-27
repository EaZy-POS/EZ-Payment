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
public class CentralPDAMBillerModel implements DataTable{

    private int number;
    private final String id, biller, billerName, district, status, aksi;

    public CentralPDAMBillerModel(int number, String id, String biller, String billerName, String district, String status, String aksi) {
        this.number = number;
        this.id = id;
        this.biller = biller;
        this.billerName = billerName;
        this.district = district;
        this.status = status;
        this.aksi = aksi;
    }

    public String getId() {
        return id;
    }

    public String getBiller() {
        return biller;
    }

    public String getBillerName() {
        return billerName;
    }

    public String getDistrict() {
        return district;
    }

    public String getStatus() {
        return status;
    }

    public String getAksi() {
        return aksi;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    
    @Override
    public Object[] getArrayData() {
        return new Object[]{number, biller, billerName, district, status, aksi};
    }
}
