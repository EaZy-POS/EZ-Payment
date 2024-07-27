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
public class PospaidModel {

    private final String bulan, nama, meter;
    private final BigDecimal tagihan, denda, adm;

    public PospaidModel(
            String bulan, 
            String nama, 
            String meter,
            BigDecimal tagihan, 
            BigDecimal denda, 
            BigDecimal adm) {
        this.bulan = bulan;
        this.nama = nama;
        this.tagihan = tagihan;
        this.denda = denda;
        this.adm = adm;
        this.meter = meter;
    }
    
    public BigDecimal getTotal(){
        return getTagihan();
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

    public String getMeter() {
        return meter;
    }
    
}
