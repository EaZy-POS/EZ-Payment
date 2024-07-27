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
public class UserModel implements DataTable{

    private final int number;
    private final String id, userid, nip, nama, password, jabatan, status, aksi;

    public UserModel(int number, String id, String userid, String nip, String nama, String password, String jabatan, String status) {
        this.number = number;
        this.id = id;
        this.userid = userid;
        this.nama = nama;
        this.password = password;
        this.jabatan = jabatan;
        this.aksi = null;
        this.status = status;
        this.nip = nip;
    }

    public String getUserid() {
        return userid;
    }

    public String getPassword() {
        return password;
    }

    public int getNumber() {
        return number;
    }

    public String getId() {
        return id;
    }
    
    public String getNama() {
        return nama;
    }

    public String getJabatan() {
        return jabatan;
    }

    public String getAksi() {
        return aksi;
    }

    public String getStatus() {
        return status;
    }

    public String getNip() {
        return nip;
    }

    @Override
    public Object[] getArrayData() {
        return new Object[]{number, nip, nama, jabatan, userid, status, aksi};
    }
}
