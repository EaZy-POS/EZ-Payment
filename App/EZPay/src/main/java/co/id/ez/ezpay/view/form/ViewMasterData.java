/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.view.form;

import co.id.ez.ezpay.app.Common;
import co.id.ez.ezpay.interfaces.ListMenuConsumer;
import co.id.ez.ezpay.view.master.MasterJabatanView;
import co.id.ez.ezpay.view.master.MasterKaryawanView;
import java.awt.Component;
import javax.swing.JPanel;

/**
 *
 * @author RCS
 */
public class ViewMasterData extends JPanel implements ListMenuConsumer{

    private int stage = -1;
    
    /**
     * Creates new form ModulForm
     */
    public ViewMasterData() {
        initComponents();
        initForm();
    }

    private void initForm(){
        Common.setOpaqueComponent(false, this, jPanel1, jPanel2);
        menu.getListMenu().setConsumer(this);
        menu.getListMenu().select(0);
    }
    
    private void loadModule(Component form) {
        modulpane.removeAll();
        modulpane.repaint();
        modulpane.revalidate();
        
        if (form != null) {
            modulpane.add(form);
            modulpane.repaint();
            modulpane.revalidate();
        }
    }
    
    private void selectActivedMenu(int index){
        if (index != stage) {
            stage = index;
            switch (index) {
                case 0:
                    loadModule(new MasterJabatanView());
                    break;
                case 1:
                    loadModule(new MasterKaryawanView());
                    break;
                default:
                    loadModule(null);
                    break;
            }
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        menu = new co.id.ez.ezpay.view.menu.MenuMasterData();
        jPanel1 = new javax.swing.JPanel();
        modulpane = new co.id.ez.ezpay.util.swings.DefaultPanel();

        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentHidden(java.awt.event.ComponentEvent evt) {
                formComponentHidden(evt);
            }
        });
        setLayout(new java.awt.BorderLayout());

        jPanel2.setPreferredSize(new java.awt.Dimension(200, 339));
        jPanel2.add(menu);

        add(jPanel2, java.awt.BorderLayout.LINE_START);

        modulpane.setLayout(new java.awt.CardLayout());

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(modulpane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(modulpane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        add(jPanel1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void formComponentHidden(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentHidden
        // TODO add your handling code here:
    }//GEN-LAST:event_formComponentHidden

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private co.id.ez.ezpay.view.menu.MenuMasterData menu;
    private co.id.ez.ezpay.util.swings.DefaultPanel modulpane;
    // End of variables declaration//GEN-END:variables

    @Override
    public void notify(int index) {
        selectActivedMenu(index);
    }
    
}
