/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.model.data.deposit;

import co.id.ez.ezpay.interfaces.DataTable;

/**
 *
 * @author Lutfi
 */
public class MutasiModel implements DataTable{

    private final int number;
    private final String tanggal, jurnal, saldo, amount, saldo_akhir, trxid, remarks;

    public MutasiModel(int number, String tanggal, String jurnal, String saldo, String amount, String saldoAkhir, String trxid, String remarks) {
        this.number = number;
        this.tanggal = tanggal;
        this.jurnal = jurnal;
        this.amount = amount;
        this.trxid = trxid;
        this.remarks = remarks;
        this.saldo = saldo;
        this.saldo_akhir = saldoAkhir;
    }

    public int getNumber() {
        return number;
    }

    public String getTanggal() {
        return tanggal;
    }

    public String getJurnal() {
        return jurnal;
    }

    public String getAmount() {
        return amount;
    }

    public String getTrxid() {
        return trxid;
    }

    public String getRemarks() {
        return remarks;
    }

    public String getSaldo() {
        return saldo;
    }

    public String getSaldo_akhir() {
        return saldo_akhir;
    }

    @Override
    public Object[] getArrayData() {
        return new Object[]{
            number, 
            tanggal, 
            jurnal, 
            saldo, 
            (jurnal.equalsIgnoreCase("C") ? "(+) " : "(-) ").concat(amount), 
            saldo_akhir, 
            trxid, 
            remarks
        };
    }
    
}
