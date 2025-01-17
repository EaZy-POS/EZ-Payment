/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.view.module;
import co.id.ez.ezpay.app.Common;
import co.id.ez.ezpay.enums.util.Icons;
import co.id.ez.ezpay.model.data.search.MultiPaymentBiller;
import co.id.ez.system.core.ex.ServiceException;
import co.id.ez.system.core.rc.RC;
import javax.swing.JPanel;

/**
 *
 * @author Lutfi
 */
public class ForminputMPModule extends JPanel {

    private final MultiPaymentBiller biller;
    private final MPModule module;

    /**
     * Creates new form ForminputMPModule
     * @param biller
     * @param module
     */
    public ForminputMPModule(MultiPaymentBiller biller, MPModule module) {
        this.biller = biller;
        this.module = module;
        initComponents();
        initForm();
    }

    public final void initForm() {
        Common.setOpaqueComponent(false, panelinput, this);
        btn_cek.setIcon(Icons.LARGE_DONE.get());
        resetForm();
        loadBillerForm();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        panelinput = new javax.swing.JPanel();
        label_1 = new javax.swing.JLabel();
        txt_input1 = new javax.swing.JTextField();
        label_3 = new javax.swing.JLabel();
        txt_input3 = new javax.swing.JTextField();
        txt_input2 = new javax.swing.JTextField();
        label_2 = new javax.swing.JLabel();
        btn_cek = new javax.swing.JButton();
        label_4 = new javax.swing.JLabel();

        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });
        setLayout(new java.awt.CardLayout());

        label_1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        label_1.setForeground(new java.awt.Color(255, 255, 255));
        label_1.setText("Label1");

        txt_input1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        label_3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        label_3.setForeground(new java.awt.Color(255, 255, 255));
        label_3.setText("Label3");

        txt_input3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        txt_input2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        label_2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        label_2.setForeground(new java.awt.Color(255, 255, 255));
        label_2.setText("Label2");

        btn_cek.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        btn_cek.setText("Cek");
        btn_cek.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_cek.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cekActionPerformed(evt);
            }
        });

        label_4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        javax.swing.GroupLayout panelinputLayout = new javax.swing.GroupLayout(panelinput);
        panelinput.setLayout(panelinputLayout);
        panelinputLayout.setHorizontalGroup(
            panelinputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelinputLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelinputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panelinputLayout.createSequentialGroup()
                        .addComponent(label_1, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_input1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelinputLayout.createSequentialGroup()
                        .addComponent(label_2, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_input2, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelinputLayout.createSequentialGroup()
                        .addComponent(label_3, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_input3))
                    .addGroup(panelinputLayout.createSequentialGroup()
                        .addComponent(label_4, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_cek, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(134, Short.MAX_VALUE))
        );
        panelinputLayout.setVerticalGroup(
            panelinputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelinputLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelinputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(label_1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_input1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelinputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(label_2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_input2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelinputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(label_3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_input3, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addGroup(panelinputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(label_4, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_cek))
                .addContainerGap(9, Short.MAX_VALUE))
        );

        add(panelinput, "card3");
    }// </editor-fold>//GEN-END:initComponents

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        // TODO add your handling code here:
    }//GEN-LAST:event_formComponentShown

    private void btn_cekActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cekActionPerformed
        // TODO add your handling code here:
        int opt = Common.showConfirmMessage("Cek pembayaran "+biller.getBillername()+" ?", this);
        if (opt == 0) {
            if (validateForm()) {
                module.performInquiry();
            } else {
                Common.showWarningMessage("Mohon pilih produk terlebih dahulu!", this);
            }
        }
    }//GEN-LAST:event_btn_cekActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_cek;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel label_1;
    private javax.swing.JLabel label_2;
    private javax.swing.JLabel label_3;
    private javax.swing.JLabel label_4;
    private javax.swing.JPanel panelinput;
    private javax.swing.JTextField txt_input1;
    private javax.swing.JTextField txt_input2;
    private javax.swing.JTextField txt_input3;
    // End of variables declaration//GEN-END:variables

    public final void resetForm() {
        Common.clearText(txt_input1, txt_input2, txt_input3);
        Common.activateComponent(true, btn_cek);
        Common.setVisibility(false, panelinput);
    }

    private void loadBillerForm(){
        Common.setVisibility(false,txt_input1, label_1, txt_input2, txt_input3, label_2, label_3);
        if(biller != null){
            int index = 0;
            for (MultiPaymentBiller.Input input : biller.getInputList()) {
                if(index == 0){
                    input.setInputHandler(label_1, txt_input1);
                }
                
                if(index == 1){
                    input.setInputHandler(label_2, txt_input2);
                }
                
                if(index == 2){
                    input.setInputHandler(label_3, txt_input3);
                }
                index++;
                
                if(index > 2){
                    break;
                }
            }
            
            if(index > 0){
                Common.activateComponent(true, btn_cek, txt_input1, txt_input2, txt_input3);
                Common.setVisibility(true, panelinput);
            }
        }
    }    

    public boolean validateForm() {
        if(biller == null){
            throw new ServiceException(RC.ERROR_INVALID_POWER_VALUE, "Biller undifined!");
        }
        
        return txt_input1.getText().length() > 0 && module.validateForm();
    }
    
    public void activateForm(boolean isActive){
        Common.activateComponent(isActive, txt_input1, txt_input2, txt_input3, btn_cek);
    }
}
