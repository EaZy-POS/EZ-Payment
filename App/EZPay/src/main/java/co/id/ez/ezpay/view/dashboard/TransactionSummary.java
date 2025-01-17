/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.view.dashboard;

import co.id.ez.ezpay.app.Common;
import co.id.ez.ezpay.controller.DashboardController;
import co.id.ez.ezpay.interfaces.DataTable;
import co.id.ez.ezpay.model.data.dashboard.HistoriTransaksiModel;
import co.id.ez.ezpay.model.table.DataTableModel;
import co.id.ez.ezpay.util.swings.RoundPanel;
import co.id.ez.ezpay.util.swings.table.TableHeader;
import com.json.JSONException;
import com.json.JSONObject;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 *
 * @author RCS
 */
public class TransactionSummary extends RoundPanel {

    private final DashboardController controller = new DashboardController();
    private final DataTableModel<DataTable> model;

    /**
     * Creates new form TransactionSummary
     */
    public TransactionSummary() {
        LinkedList<TableHeader> header = new LinkedList<>();
        header.add(
                TableHeader.create("#", 40)
        );

        header.add(
                TableHeader.create("Modul", 120)
        );

        header.add(
                TableHeader.create("Jml", 40)
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

    private void initForm() {
        unGradient();
        setRound(15);
        table_data.fixTable(jScrollPane1);
        start();
    }

    private void start() {
        new Thread(() -> {
            while (true) {
                try {
                    model.clearAllItem(table_data);
                    LinkedList<JSONObject> data = controller.getHistoriTransaksi();
                    LinkedList<DataTable> rows = new LinkedList<>();
                    if (data != null && data.size() > 0) {
                        int number = 0;
                        for (JSONObject dataHistory : data) {
                            number++;
                            String modul = dataHistory.getString("module");
                            String jml = dataHistory.get("total_transaction").toString();
                            String ttl = Common.formatRupiah(dataHistory.getDouble("total_amount"));
                            HistoriTransaksiModel model = new HistoriTransaksiModel("" + number, modul, jml, ttl);
                            rows.add(model);
                        }
                    }
                    model.initDataToTable(table_data, rows);
                } catch (JSONException | SQLException e) {
                    Common.errorLog("[JSONException | SQLException] Error load data histori", e);
                } catch (Exception e) {
                    Common.errorLog("[Exception] Error load data histori", e);
                }
                
                try {
                    Thread.sleep(30000);
                } catch (Exception e) {
                }
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

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table_data = new co.id.ez.ezpay.util.swings.table.ui.Table();

        setBackground(new java.awt.Color(255, 255, 255));
        setLayout(new java.awt.BorderLayout());

        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(400, 50));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 51, 51));
        jLabel1.setText("Histori Transaksi Hari Ini");

        jSeparator1.setPreferredSize(new java.awt.Dimension(50, 3));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 208, Short.MAX_VALUE))
                    .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        add(jPanel1, java.awt.BorderLayout.PAGE_START);

        jPanel2.setOpaque(false);

        table_data.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(table_data);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 447, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 318, Short.MAX_VALUE)
                .addContainerGap())
        );

        add(jPanel2, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private co.id.ez.ezpay.util.swings.table.ui.Table table_data;
    // End of variables declaration//GEN-END:variables
}
