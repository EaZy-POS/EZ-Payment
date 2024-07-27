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
public class JabatanModel implements DataTable{

    private final int number;
    private final String id, jabatan, aksi, access;
    private final LinkedList<AppModulModel> modul = new LinkedList<>();

    public JabatanModel(int number, String id, String jabatan, String access) {
        this.number = number;
        this.id = id;
        this.jabatan = jabatan;
        this.aksi = null;
        this.access = access;
    }

    public String getAccess() {
        return access;
    }

    public int getNumber() {
        return number;
    }

    public String getId() {
        return id;
    }

    public String getJabatan() {
        return jabatan;
    }

    public void addModul(AppModulModel subModul){
        modul.add(subModul);
    }
    
    public LinkedList<AppModulModel> getModulList(){
        return modul;
    }

    @Override
    public Object[] getArrayData() {
        return new Object[]{number, jabatan, aksi};
    }
}
