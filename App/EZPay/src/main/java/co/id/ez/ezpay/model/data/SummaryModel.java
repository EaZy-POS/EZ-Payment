package co.id.ez.ezpay.model.data;

import co.id.ez.ezpay.interfaces.DataTable;

public class SummaryModel implements DataTable {

    private final String label, value;

    public SummaryModel(String label, String value) {
        this.label = label;
        this.value = value;
    }
    
    @Override
    public Object[] getArrayData() {
        return new Object[]{label, value};
    }
    
}
