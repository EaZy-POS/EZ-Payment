/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.model.data.transaksi;

/**
 *
 * @author Lutfi
 */
public class MPMonthlyRecapModel extends MonthlyRecapModel{
    
    private final String biller;

    public MPMonthlyRecapModel(String number, String bulan, String biller, String tanggal, String jmltransaki, String total) {
        super(number, bulan, tanggal, jmltransaki, total);
        this.biller = biller;
    }

    public String getBiller() {
        return biller;
    }
    
    @Override
    public Object[] getArrayData() {
        return new Object[]{
            getNumber(),
            biller,
            getBulan(),
            getTanggal(),
            getJmltransaki(),
            getTotal()
        };
    }

        
}
