/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.view.dashboard;

import co.id.ez.ezpay.app.Common;
import javax.swing.JPanel;

/**
 *
 * @author RCS
 */
public class OngoingTransaction extends javax.swing.JPanel {

    /**
     * Creates new form OngoingTransaction
     */
    public OngoingTransaction() {
        initComponents();
        initForm();
    }
    
    private void initForm(){
        Common.setOpaqueComponent(false, this, jPanel1);
        addPanel(panel_histori, new TransactionSummary());
        addPanel(panel_chart_sales, new LineChartSales());
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

        jPanel1 = new javax.swing.JPanel();
        panel_histori = new javax.swing.JPanel();
        panel_chart_sales = new javax.swing.JPanel();

        setPreferredSize(new java.awt.Dimension(400, 400));

        jPanel1.setPreferredSize(new java.awt.Dimension(100, 400));
        jPanel1.setLayout(new java.awt.BorderLayout(5, 0));

        panel_histori.setOpaque(false);
        panel_histori.setPreferredSize(new java.awt.Dimension(500, 300));
        panel_histori.setLayout(new java.awt.CardLayout());
        jPanel1.add(panel_histori, java.awt.BorderLayout.LINE_START);

        panel_chart_sales.setBackground(new java.awt.Color(204, 255, 255));
        panel_chart_sales.setOpaque(false);
        panel_chart_sales.setLayout(new java.awt.CardLayout());
        jPanel1.add(panel_chart_sales, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel panel_chart_sales;
    private javax.swing.JPanel panel_histori;
    // End of variables declaration//GEN-END:variables
}