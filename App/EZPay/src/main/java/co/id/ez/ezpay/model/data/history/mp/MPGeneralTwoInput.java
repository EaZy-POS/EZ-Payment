/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.model.data.history.mp;

/**
 *
 * @author Lutfi
 */
public class MPGeneralTwoInput extends HistoryMultiBillerModel {

    @Override
    public Object[] getArrayData() {
        Object[] row = new Object[]{
            getNumber(),
            getTanggal(),
            getTrxid(),
            getBiller(),
            getIdpel1(),
            getIdpel2(),
            getTagihan(),
            getDetail(),
            getStatus(),
            getAksi()
        };

        return row;
    }

}