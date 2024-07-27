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
public class KaryawanModel implements DataTable{

    private final int number;
    private final String id, nip, nama, alamat, phone, email, jabatan, status, aksi;

    public KaryawanModel(int number, String id, String nip, String nama, String alamat, String phone, String email, String jabatan, String status) {
        this.number = number;
        this.id = id;
        this.nip = nip;
        this.nama = nama;
        this.alamat = alamat;
        this.phone = phone;
        this.email = email;
        this.jabatan = jabatan;
        this.aksi = null;
        this.status = status;
    }

    public int getNumber() {
        return number;
    }

    public String getId() {
        return id;
    }

    public String getNip() {
        return nip;
    }

    public String getNama() {
        return nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
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

    @Override
    public Object[] getArrayData() {
        return new Object[]{number, nip, nama, alamat, phone, email, jabatan, status, aksi};
    }
}
