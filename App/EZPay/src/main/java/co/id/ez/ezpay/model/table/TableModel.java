/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.model.table;

import co.id.ez.ezpay.util.swings.table.ActionCellEditor;
import co.id.ez.ezpay.util.swings.table.ActionCellRender;
import co.id.ez.ezpay.util.swings.table.TableActionEvent;
import co.id.ez.ezpay.util.swings.table.TableHeader;
import co.id.ez.ezpay.util.swings.table.WrapTextCellRender;
import co.id.ez.system.core.ex.ServiceException;
import co.id.ez.system.core.rc.RC;
import java.awt.Component;
import java.util.LinkedList;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Lutfi
 * @param <T>
 */
public abstract class TableModel<T> extends AbstractTableModel {

    private final LinkedList<TableHeader> header;
    private final Object[] headerArray;
    private LinkedList<T> data;

    public TableModel(LinkedList<TableHeader> pHeaders) {
        header = pHeaders;
        data = new LinkedList<>();
        headerArray = new Object[header.size()];

        int index = 0;
        for (TableHeader tableHeader : header) {
            headerArray[index] = tableHeader.getColumnName();
            index++;
        }
    }

    public LinkedList<TableHeader> getHeader() {
        return header;
    }

    public LinkedList<T> getData() {
        return data;
    }

    public void setData(LinkedList<T> data) {
        this.data = data;
    }

    public Object[] getHeaderArray() {
        return headerArray;
    }

    public void clearAllItem(JTable table_data) {
        getData().clear();
//        table_data.setModel(this);
        table_data.setModel(new DefaultTableModel(headerArray, 0));
    }

    public void initDataToTable(JTable tabel_data, LinkedList<T> data) {
        initDataToTable(tabel_data, data, null);
    }

    public void initDataToTable(final JTable tabel_data, LinkedList<T> datas, TableActionEvent event) {
        if (datas == null) {
            throw new ServiceException(RC.ERROR_INVALID_PARAMETER, "Header or Data table cannot be null");
        }

        setData(datas);
        tabel_data.setModel(this);

        for (int i = 0; i < getHeader().size(); i++) {
            final TableHeader header = getHeader().get(i);

            switch (header.getCellType()) {
                case ACTIONPANE:
                    if (event != null) {
                        tabel_data.getColumnModel().getColumn(i).setPreferredWidth(header.getColumnLength());
                        tabel_data.getColumnModel().getColumn(i).setCellRenderer(new ActionCellRender(event));
                        tabel_data.getColumnModel().getColumn(i).setCellEditor(new ActionCellEditor(event));
                        break;
                    }
                case WRAP_TEXT:
                    tabel_data.getColumnModel().getColumn(i).setPreferredWidth(header.getColumnLength());
                    tabel_data.getColumnModel().getColumn(i).setResizable(false);
                    tabel_data.getColumnModel().getColumn(i).setCellRenderer(new WrapTextCellRender());
                    break;
                case CHECK:
                    tabel_data.getColumnModel().getColumn(i).setPreferredWidth(header.getColumnLength());
                    tabel_data.getColumnModel().getColumn(i).setResizable(false);
                    break;
                default:
                    tabel_data.getColumnModel().getColumn(i)
                            .setCellRenderer(
                                    new DefaultTableCellRenderer() {
                                @Override
                                public Component getTableCellRendererComponent(
                                        JTable jtable, Object o, boolean bln, boolean bln1, int i, int i1) {
                                    setHorizontalAlignment(header.getAligment().getValue());
                                    setBorder(new EmptyBorder(10, 5, 10, 5));
                                    super.getTableCellRendererComponent(jtable, o, bln, bln1, i, i1);
                                    return this;
                                }
                            });
                    tabel_data.getColumnModel().getColumn(i).setPreferredWidth(header.getColumnLength());
                    break;
            }
        }
    }

}
