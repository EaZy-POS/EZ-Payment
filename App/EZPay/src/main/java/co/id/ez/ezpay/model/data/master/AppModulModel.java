/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.model.data.master;

import co.id.ez.ezpay.interfaces.DataTable;
import java.util.LinkedList;

/**
 *
 * @author Lutfi
 */
public class AppModulModel implements DataTable {

    private boolean isCheck;
    private final String module;
    private final LinkedList<AppSubModulModel> listSubModul = new LinkedList<>();

    public AppModulModel(String module) {
        this(false, module);
    }

    public AppModulModel(boolean isChecked, String module) {
        this.module = module;
        this.isCheck = isChecked;
    }

    public boolean isCheck() {
        return isCheck;
    }
    
    public void setCheck(boolean isCekd) {
        this.isCheck = isCekd;
    }

    public String getModule() {
        return module;
    }

    public void addSubModul(AppSubModulModel subModul) {
        listSubModul.add(subModul);
    }

    public LinkedList<AppSubModulModel> getSubModulList() {
        return listSubModul;
    }

    public LinkedList<DataTable> getSubModulListAsDataTable() {
        LinkedList<DataTable> data = new LinkedList<>();

        listSubModul.forEach(appSubModulModel -> {
            data.add(appSubModulModel);
        });

        return data;
    }

    @Override
    public Object[] getArrayData() {
        return new Object[]{isCheck, module};
    }

    public void setSelected(Object select) {
        setSelected(select, 0);
    }

    @Override
    public void setSelected(Object select, int columnIndex) {
        this.isCheck = (boolean) select;
        if(!isCheck){
            resetSubModule();
        }
    }

    public void reset() {
        setSelected(false, 0);
    }

    public void resetSubModule() {
        getSubModulList().forEach(dataTable -> {
            dataTable.resetCrudAccess();
        });
    }

    public void grantAll() {
        setSelected(true, 0);
    }
    
    public void grantAllSubModule() {
        getSubModulList().forEach(dataTable -> {
            dataTable.grantAll();
        });
    }
}
