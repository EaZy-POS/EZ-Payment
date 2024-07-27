/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.model.table;

import co.id.ez.ezpay.enums.util.CellType;
import co.id.ez.ezpay.interfaces.DataTable;
import co.id.ez.ezpay.util.swings.table.TableHeader;
import java.util.LinkedList;

/**
 *
 * @author Lutfi
 * @param <T>
 */
public class DataTableModel<T extends DataTable> extends TableModel<T> {

    public DataTableModel(LinkedList<TableHeader> pHeaders) {
        super(pHeaders);
    }

    @Override
    public String getColumnName(int column) {
        return getHeader().get(column).getColumnName();
    }

    @Override
    public int getRowCount() {
        return getData().size();
    }

    @Override
    public int getColumnCount() {
        return getHeader().size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        T data = getData().get(rowIndex);
        Object[] result = data.getArrayData();
        if (getHeader().get(columnIndex).getCellType() != CellType.CHECK) {
            return " "+ result[columnIndex];
        }else{
            return result[columnIndex];
        }
    }

    public T getValueAt(int row) {
        if (row < getData().size()) {
            return getData().get(row);
        }

        return null;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if (getHeader().get(columnIndex).getCellType() != CellType.CHECK) {
            super.setValueAt(aValue, rowIndex, columnIndex);
        } else {
            T model = getData().get(rowIndex);
            model.setSelected(aValue, columnIndex);
        }
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return getHeader().get(column).isEditable();
    }

    @Override
    public Class getColumnClass(int column) {
        return getValueAt(0, column) == null ? Object.class : getValueAt(0, column).getClass();
    }

    @Override
    public String toString() {
        return "TableModel{"
                + "rows=" + getData().toString() + ", "
                + "columns=" + getHeader().toString()
                + '}';
    }

}
