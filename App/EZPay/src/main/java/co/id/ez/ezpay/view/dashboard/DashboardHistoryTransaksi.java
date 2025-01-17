/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.view.dashboard;

import co.id.ez.ezpay.abstracts.AbstractModule;
import co.id.ez.ezpay.app.Common;
import co.id.ez.ezpay.controller.DashboardController;
import co.id.ez.ezpay.interfaces.DataTable;
import co.id.ez.ezpay.model.data.dashboard.HistoriTransaksiModel;
import co.id.ez.ezpay.model.table.DataTableModel;
import co.id.ez.ezpay.util.swings.table.TableHeader;
import com.json.JSONException;
import com.json.JSONObject;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 *
 * @author RCS
 */
public class DashboardHistoryTransaksi extends AbstractModule {

    private final DashboardController controller = new DashboardController();
    private final DataTableModel<DataTable> model;
    /**
     * Creates new form DashboardHistoryTransaksi
     */
    public DashboardHistoryTransaksi() {
        LinkedList<TableHeader> header = new LinkedList<>();
        header.add(
                TableHeader.create("#", 40)
        );
        
        header.add(
                TableHeader.create("Modul", 120)
        );
        
        header.add(
                TableHeader.create("Jumlah", 40)
        );
        
        header.add(
                TableHeader.create("Total", 100)
        );
        
        model = new DataTableModel(
                header
        );
        initComponents();
        initForm();
    }
    
    public final void initForm(){
        table.fixTable(jScrollPane1);
        loadData();
    }
    
    public void loadData(){
        new Thread(()->{
            try {
                model.clearAllItem(table);
                LinkedList<JSONObject> data = controller.getHistoriTransaksi();
                LinkedList<DataTable> rows = new LinkedList<>();
                if(data != null && data.size() > 0){
                    int number = 0;
                    for (JSONObject dataHistory : data) {
                        number ++;
                        String modul = dataHistory.getString("module");
                        String jml = dataHistory.get("total_transaction").toString();
                        String ttl = Common.formatRupiah(dataHistory.getDouble("total_amount"));
                        HistoriTransaksiModel model = new HistoriTransaksiModel(""+ number, modul, jml, ttl);
                        rows.add(model);
                    }
                }
                model.initDataToTable(table, rows);
            } catch (JSONException | SQLException e) {
                Common.errorLog("[JSONException | SQLException] Error load data histori", e);
            } catch (Exception e) {
                e.printStackTrace();
                Common.errorLog("[Exception] Error load data histori", e);
            }
        }).start();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        content_pane = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new co.id.ez.ezpay.util.swings.table.ui.Table();

        setOpaque(false);
        setLayout(new java.awt.BorderLayout(0, 2));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 15)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 51, 51));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Histori Transaksi Hari Ini");
        jLabel1.setPreferredSize(new java.awt.Dimension(122, 25));
        add(jLabel1, java.awt.BorderLayout.PAGE_START);

        jPanel1.setOpaque(false);
        jPanel1.setLayout(new java.awt.BorderLayout());

        jSeparator1.setPreferredSize(new java.awt.Dimension(50, 5));
        jPanel1.add(jSeparator1, java.awt.BorderLayout.PAGE_START);

        content_pane.setOpaque(false);
        content_pane.setLayout(new java.awt.CardLayout());

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(table);

        content_pane.add(jScrollPane1, "card2");

        jPanel1.add(content_pane, java.awt.BorderLayout.CENTER);

        add(jPanel1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel content_pane;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private co.id.ez.ezpay.util.swings.table.ui.Table table;
    // End of variables declaration//GEN-END:variables

    @Override
    public void resetForm() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
