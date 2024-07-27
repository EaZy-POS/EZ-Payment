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
public class CentralModuleModel implements DataTable{

    private int number;
    private final String id, moduleid, moduleName, status, aksi;
    private final boolean isNeedSignin;

    public CentralModuleModel(int number, String id, String moduleid, String moduleName, String status, String aksi, boolean isNeedSignin) {
        this.number = number;
        this.id = id;
        this.moduleid = moduleid;
        this.moduleName = moduleName;
        this.status = status;
        this.aksi = aksi;
        this.isNeedSignin = isNeedSignin;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getId() {
        return id;
    }

    public String getModuleid() {
        return moduleid;
    }

    public String getModuleName() {
        return moduleName;
    }

    public String getStatus() {
        return status;
    }

    public String getAksi() {
        return aksi;
    }

    public boolean isIsNeedSignin() {
        return isNeedSignin;
    }

    

    @Override
    public Object[] getArrayData() {
        return new Object[]{number, moduleid, moduleName, isNeedSignin, status, aksi};
    }
}
