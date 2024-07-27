/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.abstracts;

import co.id.ez.ezpay.interfaces.DataTable;
import co.id.ez.ezpay.util.swings.table.TableActionEvent;
import co.id.ez.ezpay.util.swings.table.TableHeader;
import co.id.ez.ezpay.view.table.ViewTableData;
import java.util.LinkedList;
import javax.swing.JPanel;

/**
 *
 * @author Lutfi
 */
public abstract class AbstractViewLaporan extends AbstractModule {

    protected final ViewTableData viewTableData;
    protected String mChaceStringString;

    public abstract void loadData(String search);
    public void loadReport(){
        
    }

    public AbstractViewLaporan(LinkedList<TableHeader> pTableHeader) {
        this(pTableHeader, false);
    }

    public AbstractViewLaporan(LinkedList<TableHeader> pTableHeader, boolean isSearchable) {
        this(pTableHeader, isSearchable, true);
    }

    public AbstractViewLaporan(LinkedList<TableHeader> pTableHeader, boolean isSearchable, boolean isPrintable) {
        super();
        this.viewTableData = new ViewTableData(pTableHeader, isSearchable, isPrintable);
        initTableView();
    }

    private void initTableView() {
        viewTableData.initConsumer(this);
    }

    public <T extends DataTable> void loadDataToTables(LinkedList<T> rows) {
        loadDataToTables(rows, null);
    }

    public <T extends DataTable> void loadDataToTables(
            LinkedList<T> rows,
            TableActionEvent event
    ) {
        new Thread(()->{
            viewTableData.loadDataToTables(rows, event);
        }).start();
        
    }

    public void loadViewTable(JPanel panelForm) {
        panelForm.removeAll();
        panelForm.repaint();
        panelForm.revalidate();
        panelForm.add(viewTableData);
        panelForm.repaint();
        panelForm.revalidate();
    }

}
