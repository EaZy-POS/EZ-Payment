/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.model.data.search;

import java.math.BigDecimal;

/**
 *
 * @author Lutfi
 */
public class PDAMModel {

    private final String bulan, nama;
    private final BigDecimal tagihan, denda, adm, installment, vat, danameter, waste, materai, billadm;
    private final int meter_awal, meter_akhir, total_meter;

    public PDAMModel(String bulan, String nama,BigDecimal tagihan, BigDecimal denda, 
            BigDecimal adm, BigDecimal installment, BigDecimal vat, 
            BigDecimal danameter, BigDecimal waste, BigDecimal materai, BigDecimal billadm,
            int meter_awal, int meter_akhir, int total_meter) {
        this.bulan = bulan;
        this.nama = nama;
        this.tagihan = tagihan;
        this.denda = denda;
        this.adm = adm;
        this.installment = installment;
        this.vat = vat;
        this.danameter = danameter;
        this.waste = waste;
        this.meter_awal = meter_awal;
        this.meter_akhir = meter_akhir;
        this.total_meter = total_meter;
        this.materai = materai;
        this.billadm = billadm;
    }
    
    public BigDecimal getOtherFee(){
        return getInstallment().add(getVat()).add(getDanameter()).add(getWaste()).add(getMaterai());
    }

    public BigDecimal getTotal(){
        return getTagihan().add(getDenda()).add(getAdm()).add(getOtherFee());
    }
    
    public String getNama() {
        return nama;
    }

    public String getBulan() {
        return bulan;
    }

    public BigDecimal getTagihan() {
        return tagihan;
    }

    public BigDecimal getDenda() {
        return denda;
    }

    public BigDecimal getAdm() {
        return adm;
    }

    public BigDecimal getInstallment() {
        return installment;
    }

    public BigDecimal getVat() {
        return vat;
    }

    public BigDecimal getDanameter() {
        return danameter;
    }

    public int getMeter_awal() {
        return meter_awal;
    }

    public int getMeter_akhir() {
        return meter_akhir;
    }

    public int getTotal_meter() {
        return total_meter;
    }

    public BigDecimal getWaste() {
        return waste;
    }

    public BigDecimal getMaterai() {
        return materai;
    }

    public BigDecimal getBilladm() {
        return billadm;
    }
    
}
