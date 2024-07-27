/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.view.form;

import co.id.ez.ezpay.abstracts.AbstractModule;
import co.id.ez.ezpay.app.Common;
import co.id.ez.ezpay.enums.Modul;
import co.id.ez.ezpay.enums.RequestType;
import co.id.ez.ezpay.enums.report.TransactionModule;
import co.id.ez.ezpay.interfaces.ListMenuConsumer;
import co.id.ez.ezpay.util.CekTagihanNotification;
import co.id.ez.ezpay.view.module.HistoryTransaksi;
import co.id.ez.ezpay.view.module.RekapTransaksi;
import java.awt.Component;
import javax.swing.JPanel;

/**
 *
 * @author RCS
 */
public class ViewModule extends JPanel implements ListMenuConsumer{

    private final AbstractModule transactionForm;
    private final Modul module;
    private int stage = -1;
    private CekTagihanNotification cekTagihan;
    
    /**
     * Creates new form ModulForm
     * @param module
     * @param transactionmodule
     */
    public ViewModule(Modul module, AbstractModule transactionmodule) {
        initComponents();
        this.transactionForm = transactionmodule;
        this.module = module;
        initForm();
    }

    private void initForm(){
        Common.setOpaqueComponent(false, this, jPanel1, jPanel2);
        menu.getListMenu().setConsumer(this);
        menu.getListMenu().select(0);
        cekTagihan = new CekTagihanNotification(getRequestType(), menu.getListMenu());
        cekTagihan.start();
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
                    loadModule(transactionForm);
                    break;
                case 1:
                    loadModule(new HistoryTransaksi(getRequestType(), true, false, 2));
                    break;
                case 2:
                    loadModule(new HistoryTransaksi(getRequestType(), true, false));
                    break;
                case 3:
                    loadModule(new RekapTransaksi(getTransactionModule(), 0));
                    break;
                case 4:
                    loadModule(new RekapTransaksi(getTransactionModule(), 1));
                    break;
                default:
                    loadModule(null);
                    break;
            }
        }
    }
    
    private RequestType getRequestType(){
        switch(module){
            case VOUCHER:
                return RequestType.VOUCHER;
            case PDAM:
                return RequestType.PDAM;
            case POSTPAID:
                return RequestType.POSTPAID;
            case PREPAID:
                return RequestType.PREPAID;
            default:
                return RequestType.MULTIPAYMENT;
        }
    }
    
    private TransactionModule getTransactionModule(){
        switch(module){
            case VOUCHER:
                return TransactionModule.VOUCHER;
            case PDAM:
                return TransactionModule.PDAM;
            case POSTPAID:
                return TransactionModule.POSTPAID;
            case PREPAID:
                return TransactionModule.PREPAID;
            default:
                return TransactionModule.MULTIPAYMENT;
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
        menu = new co.id.ez.ezpay.view.menu.MenuModule();
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
        if(cekTagihan != null){
            cekTagihan.stop();
        }
    }//GEN-LAST:event_formComponentHidden

    public void closeOperation(){
        if(cekTagihan != null){
            cekTagihan.stop();
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private co.id.ez.ezpay.view.menu.MenuModule menu;
    private co.id.ez.ezpay.util.swings.DefaultPanel modulpane;
    // End of variables declaration//GEN-END:variables

    @Override
    public void notify(int index) {
        selectActivedMenu(index);
    }
    
}
