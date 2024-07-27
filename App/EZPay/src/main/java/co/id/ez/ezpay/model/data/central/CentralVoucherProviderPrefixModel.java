/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.model.data.central;

import co.id.ez.ezpay.interfaces.DataTable;

/**
 *
 * @author Lutfi
 */
public class CentralVoucherProviderPrefixModel implements DataTable{

    private int number;
    private final String id, provider_id, providerName, prefix, aksi;

    public CentralVoucherProviderPrefixModel(int number, String id, String provider_id, String providerName, String prefix, String aksi) {
        this.number = number;
        this.id = id;
        this.providerName = providerName;
        this.provider_id = provider_id;
        this.prefix = prefix;
        this.aksi = aksi;
    }

    public int getNumber() {
        return number;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getId() {
        return id;
    }

    public String getProvider_id() {
        return provider_id;
    }

    public String getPrefix() {
        return prefix;
    }

    @Override
    public Object[] getArrayData() {
        return new Object[]{
            number, 
            providerName,
            prefix, 
            aksi
        };
    }
}
