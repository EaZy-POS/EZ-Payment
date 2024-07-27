/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.view.dashboard;

import co.id.ez.ezpay.app.Common;
import co.id.ez.ezpay.controller.DashboardController;
import com.json.JSONObject;
import java.beans.PropertyChangeEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import javax.swing.JPanel;

/**
 *
 * @author RCS
 */
public class PassTransaction extends JPanel {

    private final DashboardController controller = new DashboardController();

    /**
     * Creates new form DashboardTransaction
     */
    public PassTransaction() {
        initComponents();
        initForm();
    }

    private void initForm() {
        Common.setOpaqueComponent(false, this, jScrollPane1, jScrollPane1.getViewport());
        roundPanel1.unGradient();
        roundPanel1.setRound(15);
        jScrollPane1.setBorder(null);
        panel_histori.unGradient();
        panel_chart.unGradient();
        start_date.setDate(new Date());
        start_date.setMaxSelectableDate(new Date());
        end_date.setDate(new Date());
        end_date.setMaxSelectableDate(new Date());
        start_date.getDateEditor()
                .addPropertyChangeListener((PropertyChangeEvent e) -> {
                    if ("date".equals(e.getPropertyName())) {
                        loadData();
                    }
        });
        
        end_date.getDateEditor()
                .addPropertyChangeListener((PropertyChangeEvent e) -> {
                    if ("date".equals(e.getPropertyName())) {
                        loadData();
                    }
        });
        loadData();
    }

    private void loadData() {
        new Thread(() -> {
            try {
                String tStartDate = new SimpleDateFormat("yyyy-MM-dd").format(start_date.getDate());
                String tEndDate = new SimpleDateFormat("yyyy-MM-dd").format(end_date.getDate());
                LinkedList<JSONObject> data = controller.getSummarySales(tStartDate, tEndDate);
                addPanel(panel_histori, new PieChartTransactionSummary(data));
                addPanel(panel_chart, new BarChartTransactionSummary(data));
                addPanel(panel_sales_chart, new LineChartTransactionSummary(data, start_date.getDate(), end_date.getDate()));
                addPanel(panel_summary, new DashboardSummaryTransaksi(data));
            } catch (Exception e) {
                Common.errorLog("[Exception] Failed load summary sales", e);
            }
        }).start();

    }

    private void addPanel(JPanel target, JPanel dest) {
        target.removeAll();
        target.repaint();
        target.revalidate();
        target.add(dest);
        target.repaint();
        target.revalidate();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        roundPanel1 = new co.id.ez.ezpay.util.swings.RoundPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        end_date = new com.toedter.calendar.JDateChooser();
        jLabel3 = new javax.swing.JLabel();
        start_date = new com.toedter.calendar.JDateChooser();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        panel_histori = new co.id.ez.ezpay.util.swings.RoundPanel();
        panel_chart = new co.id.ez.ezpay.util.swings.RoundPanel();
        jPanel5 = new javax.swing.JPanel();
        panel_sales_chart = new javax.swing.JPanel();
        panel_summary = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();

        setOpaque(false);

        roundPanel1.setBackground(new java.awt.Color(255, 255, 255));
        roundPanel1.setLayout(new java.awt.BorderLayout(0, 5));

        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(804, 65));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("Dashboard Transaksi");
        jLabel1.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        jLabel2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Periode :");
        jLabel2.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        end_date.setDateFormatString("yyyy-MM-dd");
        end_date.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("s/d");
        jLabel3.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        start_date.setDateFormatString("yyyy-MM-dd");
        start_date.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 496, Short.MAX_VALUE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(start_date, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(end_date, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator1))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(end_date, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(start_date, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        roundPanel1.add(jPanel1, java.awt.BorderLayout.PAGE_START);

        jScrollPane1.setBorder(null);

        jPanel2.setBackground(new java.awt.Color(27, 38, 44));
        jPanel2.setOpaque(false);
        jPanel2.setPreferredSize(new java.awt.Dimension(937, 1300));
        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel4.setOpaque(false);
        jPanel4.setPreferredSize(new java.awt.Dimension(1015, 350));
        jPanel4.setLayout(new java.awt.BorderLayout(3, 0));

        panel_histori.setBackground(new java.awt.Color(204, 204, 204));
        panel_histori.setPreferredSize(new java.awt.Dimension(500, 350));
        panel_histori.setRound(15);
        panel_histori.setLayout(new java.awt.CardLayout());
        jPanel4.add(panel_histori, java.awt.BorderLayout.LINE_START);

        panel_chart.setBackground(new java.awt.Color(204, 204, 204));
        panel_chart.setRound(15);
        panel_chart.setLayout(new java.awt.CardLayout());
        jPanel4.add(panel_chart, java.awt.BorderLayout.CENTER);

        jPanel2.add(jPanel4, java.awt.BorderLayout.PAGE_START);

        jPanel5.setOpaque(false);
        jPanel5.setPreferredSize(new java.awt.Dimension(1015, 700));

        panel_sales_chart.setBackground(new java.awt.Color(255, 255, 255));
        panel_sales_chart.setLayout(new java.awt.CardLayout());

        panel_summary.setBackground(new java.awt.Color(255, 255, 255));
        panel_summary.setLayout(new java.awt.CardLayout());

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel_sales_chart, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1015, Short.MAX_VALUE)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panel_summary, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panel_sales_chart, javax.swing.GroupLayout.PREFERRED_SIZE, 395, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel_summary, javax.swing.GroupLayout.DEFAULT_SIZE, 537, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel2.add(jPanel5, java.awt.BorderLayout.CENTER);

        jScrollPane1.setViewportView(jPanel2);

        roundPanel1.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel3.setOpaque(false);
        jPanel3.setPreferredSize(new java.awt.Dimension(1025, 2));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1025, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2, Short.MAX_VALUE)
        );

        roundPanel1.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(roundPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(roundPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 601, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser end_date;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private co.id.ez.ezpay.util.swings.RoundPanel panel_chart;
    private co.id.ez.ezpay.util.swings.RoundPanel panel_histori;
    private javax.swing.JPanel panel_sales_chart;
    private javax.swing.JPanel panel_summary;
    private co.id.ez.ezpay.util.swings.RoundPanel roundPanel1;
    private com.toedter.calendar.JDateChooser start_date;
    // End of variables declaration//GEN-END:variables
}
