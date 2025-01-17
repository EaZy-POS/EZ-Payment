/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.view.module;

import co.id.ez.database.query.QueryBuilder;
import co.id.ez.database.query.QueryType;
import co.id.ez.ezpay.util.swings.PopupMenu;
import java.awt.event.KeyEvent;
import co.id.ez.ezpay.abstracts.AbstractModule;
import co.id.ez.ezpay.app.App;
import co.id.ez.ezpay.app.Common;
import co.id.ez.ezpay.enums.util.Icons;
import co.id.ez.ezpay.enums.MessageType;
import co.id.ez.ezpay.model.data.DataSearch;
import co.id.ez.ezpay.model.data.detail.Field;
import co.id.ez.ezpay.model.data.search.PrepaidModel;
import co.id.ez.ezpay.msg.pre.PrepaidInquiry;
import co.id.ez.ezpay.msg.pre.PrepaidPayment;
import co.id.ez.system.core.rc.RC;
import com.json.JSONException;
import com.json.JSONObject;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 *
 * @author Lutfi
 */
public class PLNPrepaidModule extends AbstractModule {

    private DataSearch<PrepaidModel> dataHolderList;
    private PrepaidModel product;

    /**
     * Creates new form PLNPrepaidModule
     */
    public PLNPrepaidModule() {
        initComponents();
        initForm();
        initListener();
    }

    @Override
    public final void initForm() {
        Common.setOpaqueComponent(false, jPanel1, jPanel2);
        btn_cek.setIcon(Icons.LARGE_DONE.get());
        btn_batal.setIcon(Icons.LARGE_CANCEL.get());
        btn_bayar.setIcon(Icons.LARGE_PAY.get());
        btn_reload.setIcon(Icons.SMALL_RELOAD.get());
        popupMenu = new PopupMenu(txt_denom);
        dataHolderList = new DataSearch();
        resetForm();
        loadData();
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
        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        btn_cek = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        btn_reload = new javax.swing.JButton();
        txt_denom = new javax.swing.JTextField();
        txt_idpel = new javax.swing.JTextField();
        txt_nama = new javax.swing.JTextField();
        txt_tarif = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txt_total = new javax.swing.JTextField();
        btn_bayar = new javax.swing.JButton();
        btn_batal = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        txt_nometer = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txt_token = new javax.swing.JTextField();

        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });
        setLayout(new java.awt.CardLayout());

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Input pembelian", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 14), new java.awt.Color(255, 255, 255))); // NOI18N

        jLabel3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("1. ID Pel");

        btn_cek.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        btn_cek.setText("Cek");
        btn_cek.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_cek.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_cekMouseClicked(evt);
            }
        });
        btn_cek.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cekActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("2. Nominal");

        jLabel5.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("4. Nama");

        jLabel6.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("5. Tarif");

        btn_reload.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        btn_reload.setText("Refresh");
        btn_reload.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_reload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_reloadActionPerformed(evt);
            }
        });

        txt_denom.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txt_denom.setPreferredSize(new java.awt.Dimension(64, 25));
        txt_denom.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_denomFocusGained(evt);
            }
        });
        txt_denom.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_denomMouseClicked(evt);
            }
        });
        txt_denom.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_denomKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_denomKeyReleased(evt);
            }
        });

        txt_idpel.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txt_idpel.setPreferredSize(new java.awt.Dimension(64, 25));
        txt_idpel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_idpelActionPerformed(evt);
            }
        });

        txt_nama.setEditable(false);
        txt_nama.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txt_nama.setPreferredSize(new java.awt.Dimension(64, 25));

        txt_tarif.setEditable(false);
        txt_tarif.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txt_tarif.setPreferredSize(new java.awt.Dimension(64, 25));

        jLabel9.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("6. Harga");

        txt_total.setEditable(false);
        txt_total.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txt_total.setPreferredSize(new java.awt.Dimension(64, 25));

        btn_bayar.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        btn_bayar.setText("Bayar");
        btn_bayar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_bayar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_bayar.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        btn_bayar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_bayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_bayarActionPerformed(evt);
            }
        });

        btn_batal.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        btn_batal.setText("Batal");
        btn_batal.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_batal.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_batal.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        btn_batal.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_batal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_batalActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("3. No Meter");

        txt_nometer.setEditable(false);
        txt_nometer.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txt_nometer.setMinimumSize(new java.awt.Dimension(64, 25));
        txt_nometer.setPreferredSize(new java.awt.Dimension(64, 25));

        jLabel10.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("7. Token");

        txt_token.setEditable(false);
        txt_token.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txt_token.setPreferredSize(new java.awt.Dimension(64, 25));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_idpel, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_bayar, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_batal, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btn_cek, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txt_denom, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_reload, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_nometer, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_nama, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_tarif, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txt_token, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txt_total, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(147, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txt_idpel, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txt_denom, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_reload, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_cek, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_nometer, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_nama, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_tarif, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_total, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txt_token, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_batal)
                    .addComponent(btn_bayar)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(78, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        add(jPanel2, "card2");
    }// </editor-fold>//GEN-END:initComponents

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        // TODO add your handling code here:
    }//GEN-LAST:event_formComponentShown

    private void txt_denomKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_denomKeyReleased
        // TODO add your handling code here:
        if (evt.getKeyCode() != KeyEvent.VK_UP
                && evt.getKeyCode() != KeyEvent.VK_DOWN
                && evt.getKeyCode() != KeyEvent.VK_ENTER
                && evt.getKeyCode() != KeyEvent.VK_BACK_SPACE) {
            search();
        }
    }//GEN-LAST:event_txt_denomKeyReleased

    private void txt_denomMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_denomMouseClicked
        // TODO add your handling code here:
        getDenom();
    }//GEN-LAST:event_txt_denomMouseClicked

    private void txt_denomKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_denomKeyPressed
        // TODO add your handling code here:
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_UP:
                popupMenu.beforeSelection();
                break;
            case KeyEvent.VK_DOWN:
                popupMenu.afterSelection();
                break;
            case KeyEvent.VK_BACK_SPACE:
                search();
                break;
            case KeyEvent.VK_ENTER:
                product = (PrepaidModel) popupMenu.setSelectedValue();
                btn_cek.requestFocus();
                break;
            default:
                break;
        }
    }//GEN-LAST:event_txt_denomKeyPressed

    private void btn_cekActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cekActionPerformed
        // TODO add your handling code here:
        int opt = Common.showConfirmMessage("Cek pembelian PLN Prepaid ?", this);
        if (opt == 0) {
            if (validateForm()) {
                performInquiry();
            } else {
                Common.showWarningMessage("Mohon pilih denom terlebih dahulu!", this);
            }
        }
    }//GEN-LAST:event_btn_cekActionPerformed

    private void btn_reloadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_reloadActionPerformed

        int opt = Common.showConfirmMessage("Perbarui halaman module pln prepaid ?", this);
        if(opt == 0){
            resetForm();
            loadData();
            Common.showInfoMessage("Reload data berhasil", this);
        }
        
    }//GEN-LAST:event_btn_reloadActionPerformed

    private void btn_cekMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_cekMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_btn_cekMouseClicked

    private void btn_batalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_batalActionPerformed
        // TODO add your handling code here:
        int opt = Common.showConfirmMessage("Batalkan Transaksi ?", this);
        if(opt == 0){
            resetForm();
        }
    }//GEN-LAST:event_btn_batalActionPerformed

    private void btn_bayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_bayarActionPerformed
        // TODO add your handling code here:
        if (billerResponse != null) {
            int opt = Common.showConfirmMessage("Anda ingin melakukan pembayaran \n" 
                    + product.getInfo() + " ke "+ txt_idpel.getText() + " ?", this);
            if (opt == 0) {
                performPayment();
            }
        } else {
            Common.showWarningMessage("Data transaksi belum valid, silahkan ulangi transaksi!", this);
            resetForm();
        }
    }//GEN-LAST:event_btn_bayarActionPerformed

    private void txt_denomFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_denomFocusGained
        // TODO add your handling code here:
        getDenom();
    }//GEN-LAST:event_txt_denomFocusGained

    private void txt_idpelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_idpelActionPerformed
        // TODO add your handling code here:
        txt_denom.requestFocus();
    }//GEN-LAST:event_txt_idpelActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_batal;
    private javax.swing.JButton btn_bayar;
    private javax.swing.JButton btn_cek;
    private javax.swing.JButton btn_reload;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField txt_denom;
    private javax.swing.JTextField txt_idpel;
    private javax.swing.JTextField txt_nama;
    private javax.swing.JTextField txt_nometer;
    private javax.swing.JTextField txt_tarif;
    private javax.swing.JTextField txt_token;
    private javax.swing.JTextField txt_total;
    // End of variables declaration//GEN-END:variables

    @Override
    public final void resetForm() {
        Common.clearText(txt_denom, txt_idpel, txt_nama, txt_nometer, txt_tarif, txt_token, txt_total);
        Common.activateComponent(false, btn_bayar, btn_batal);
        Common.activateComponent(true, btn_cek, btn_reload, txt_idpel, txt_denom);
        product = null; 
        billerResponse = null;
        txt_idpel.requestFocus();
    }

    @Override
    public final void initListener() {
    }

    @Override
    public void search() {
        product = null;
        if (txt_idpel.getText().length() == 0) {
            Common.showWarningMessage("Mohon isi id pelanggan dengan benar!", this);
            txt_idpel.requestFocus();
        } else {
            if (txt_denom.getText().length() >= 3) {
                getDenom();
            } else {
                popupMenu.hide();
            }
        }
    }

    @Override
    public void loadData() {
        try {
            LinkedList<JSONObject> data = transactionController.getPrepaidDenomList();
            if (data != null && data.size() > 0) {
                data.forEach(dataVoucher -> {
                    String tDesc = dataVoucher.get("description").toString();
                    BigDecimal tDenom = new BigDecimal(dataVoucher.getDouble("denom"));
                    BigDecimal tPrice = new BigDecimal(dataVoucher.getDouble("price_sale"));
                    PrepaidModel model = new PrepaidModel(tDesc, tDenom, tPrice);
                    dataHolderList.putData(model);
                });

            }
        } catch (SQLException e) {
            Common.showErrorMessage(MessageType.SYSTEM_ERROR, this);
            Common.errorLog("[SQLException] Error on get denom prepaid", e);
        }
    }

    private void getDenom() {
        String tDenom = txt_denom.getText().trim();
        if(tDenom.length() > 0){
            popupMenu.show(dataHolderList, tDenom);
        }else{
            popupMenu.showAll(dataHolderList);
        }
    }

    @Override
    public void performInquiry() {
        try {
            PrepaidInquiry request = new PrepaidInquiry();
            request.setMsn(txt_idpel.getText());
            request.setNominal(product.getDenom());
            billerResponse = sendRequest(request);

            if (billerResponse != null) {
                saveTransaction();
                JSONObject tPayload = billerResponse.getPayload();
                txt_nama.setText(tPayload.getString("nama"));
                txt_nometer.setText(tPayload.getString("MSN"));
                txt_idpel.setText(tPayload.getString("subid"));
                txt_tarif.setText(tPayload.getString("tarif"));
                txt_total.setText(Common.formatRupiah(product.getPrice_sale().doubleValue()));
                
                Common.activateComponent(true, btn_bayar, btn_batal);
                Common.activateComponent(false, btn_cek, btn_reload, txt_idpel, txt_denom);
                btn_bayar.requestFocus();
            } else {
                txt_denom.requestFocus();
            }
        } catch (JSONException | SQLException e) {
            Common.errorLog("[JSONException | SQLException] Failed to execute Perform Prepaid inquiry", e);
            Common.showErrorMessage(MessageType.SYSTEM_ERROR, this);
        }
    }

    @Override
    public void performPayment() {
        try {
            prePostPayment("trx_pre_sales", billerResponse);
            
            PrepaidPayment request = new PrepaidPayment(billerResponse, product);
            billerResponse = sendRequest(request);

            if (billerResponse != null) {
                if (billerResponse.getResponseCode() == RC.SUCCESS) {
                    JSONObject tResp = billerResponse.getPayload();
                    String token = tResp.get("token").toString();
                    txt_token.setText(token);
                    updateDataTransaction();
                }
            } else {
                transactionController.updateFailedTransaction("trx_pre_sales", request);
                resetForm();
            }
        } catch (JSONException | SQLException e) {
            Common.errorLog("[JSONException | SQLException] Failed to execute Perform prepaid payment", e);
            Common.showErrorMessage(MessageType.SYSTEM_ERROR, this);
            resetForm();
        }
    }

    @Override
    public boolean validateForm() {
        if(product == null){
            product = (PrepaidModel) popupMenu.getSelectedModel();
        }
        
        return txt_idpel.getText().length() > 0
                && txt_denom.getText().length() > 0
                && product != null;
    }

    @Override
    public void updateDataTransaction() {
        try {
            JSONObject resp = billerResponse.getPayload();
            transactionController.updateSuccessTransactionPrepaid(resp, product.getDenom());
            Common.showInfoMessage(resp.getString("rcm"), this);
            previewStruk(resp.getString("trxid"), "trx_pre_sales");
            resetForm();
        } catch (JSONException | SQLException e) {
            Common.showErrorMessage(MessageType.SYSTEM_ERROR, this);
            Common.errorLog("[SQLException] Error on insert prepaid transaction", e);
            resetForm();
        }
    }

    @Override
    public QueryBuilder contructQueryInsertTransaction() {
        JSONObject resp = billerResponse.getPayload();
        QueryBuilder builder = new QueryBuilder("trx_pre_sales", QueryType.INSERT);
        builder.addEntryValue("id", "UUID()", true);
        builder.addEntryValue("transaction_date", "NOW()", true);
        builder.addEntryValue("user_id", App.environment().getDEU());
        builder.addEntryValue("subscriber_id", resp.getString("subid"));
        builder.addEntryValue("msn", resp.getString("MSN"));
        builder.addEntryValue("nominal", product.getDenom().toPlainString());
        builder.addEntryValue("harga_jual", product.getPrice_sale().toPlainString());
        builder.addEntryValue("refnumber", resp.get("refnum").toString());
        builder.addEntryValue("transaction_id", resp.get("trxid").toString());
        builder.addEntryValue("info", resp.get("text").toString());
        builder.addEntryValue("no_faktur", "");
        builder.addEntryValue("subscriber_name", Common.escape(resp.getString("nama")));
        
        return builder;
    }
    
    @Override
    public Field[][] createBodyStruk(JSONObject pTranmainData) {
        return createBodyStrukPrepaid(pTranmainData);
    }
}
