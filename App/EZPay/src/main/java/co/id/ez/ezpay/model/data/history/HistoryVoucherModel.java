/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.model.data.history;

import co.id.ez.ezpay.interfaces.DataTable;

/**
 *
 * @author Lutfi
 */
public class HistoryVoucherModel implements DataTable{
    
    private int number;
    private String Tanggal,IDTransaksi,Tujuan, Voucher, Nominal, SN, Status, Aksi;

    public HistoryVoucherModel(int number, String Tanggal, String IDTransaksi, 
            String Tujuan, String Voucher, String Nominal, String SN, String Status) {
        this.number = number;
        this.Tanggal = Tanggal;
        this.IDTransaksi = IDTransaksi;
        this.Tujuan = Tujuan;
        this.Voucher = Voucher;
        this.Nominal = Nominal;
        this.SN = SN;
        this.Status = Status;
        this.Aksi = null;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getTanggal() {
        return Tanggal;
    }

    public void setTanggal(String Tanggal) {
        this.Tanggal = Tanggal;
    }

    public String getIDTransaksi() {
        return IDTransaksi;
    }

    public void setIDTransaksi(String IDTransaksi) {
        this.IDTransaksi = IDTransaksi;
    }

    public String getTujuan() {
        return Tujuan;
    }

    public void setTujuan(String Tujuan) {
        this.Tujuan = Tujuan;
    }

    public String getVoucher() {
        return Voucher;
    }

    public void setVoucher(String Voucher) {
        this.Voucher = Voucher;
    }

    public String getNominal() {
        return Nominal;
    }

    public void setNominal(String Nominal) {
        this.Nominal = Nominal;
    }

    public String getSN() {
        return SN;
    }

    public void setSN(String SN) {
        this.SN = SN;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public String getAksi() {
        return Aksi;
    }

    public void setAksi(String Aksi) {
        this.Aksi = Aksi;
    }

    @Override
    public Object[] getArrayData() {
        Object[] data = new Object[]{
            number,
            Tanggal,
            IDTransaksi,
            Tujuan,
            Voucher,
            Nominal,
            SN, 
            Status, 
            Aksi
        };
        
        return data;
    }

    @Override
    public boolean isSuccess() {
        return getStatus().equalsIgnoreCase("sukses");
    }    
}
