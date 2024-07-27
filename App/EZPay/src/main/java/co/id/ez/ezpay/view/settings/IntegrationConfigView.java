/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.view.settings;

import co.id.ez.ezpay.abstracts.AbstractView;
import co.id.ez.ezpay.app.Common;

/**
 *
 * @author RCS
 */
public class IntegrationConfigView extends AbstractView {

    /**
     * Creates new form IntegrationConfigView
     */
    public IntegrationConfigView() {
        initComponents();
        initForm();
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
        cmb_tipe = new javax.swing.JComboBox<>();
        configPane = new javax.swing.JPanel();

        setLayout(new java.awt.BorderLayout(0, 3));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tipe Integrasi", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 12), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanel1.setPreferredSize(new java.awt.Dimension(400, 60));

        cmb_tipe.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        cmb_tipe.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Pilih Tipe Integrasi --", "File", "Database" }));
        cmb_tipe.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmb_tipeItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cmb_tipe, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(232, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cmb_tipe, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(8, Short.MAX_VALUE))
        );

        add(jPanel1, java.awt.BorderLayout.PAGE_START);

        configPane.setLayout(new java.awt.CardLayout());
        add(configPane, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void cmb_tipeItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmb_tipeItemStateChanged
        // TODO add your handling code here:
        switch (cmb_tipe.getSelectedIndex()) {
            case 0:
                loadForm(null);
                break;
            case 1:
                loadForm(new FileIntegration());
                break;
            case 2:
                loadForm(null);
                break;
        }
    }//GEN-LAST:event_cmb_tipeItemStateChanged

    @Override
    public void initForm() {
        Common.setOpaqueComponent(false, this, jPanel1, configPane);
    }

    @Override
    public void resetForm() {

    }

    private void loadForm(AbstractView view) {
        configPane.removeAll();
        configPane.repaint();
        configPane.revalidate();

        if (view != null) {
            configPane.add(view);
            configPane.repaint();
            configPane.revalidate();
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cmb_tipe;
    private javax.swing.JPanel configPane;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
