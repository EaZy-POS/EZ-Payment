/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.view.module;

import co.id.ez.database.query.QueryBuilder;
import co.id.ez.database.query.QueryType;
import co.id.ez.ezpay.abstracts.AbstractModule;
import co.id.ez.ezpay.app.App;
import co.id.ez.ezpay.app.Common;
import co.id.ez.ezpay.enums.util.Icons;
import co.id.ez.ezpay.enums.MessageType;
import co.id.ez.ezpay.enums.util.Aligment;
import co.id.ez.ezpay.model.data.detail.DetailDataFactory;
import co.id.ez.ezpay.model.data.detail.Field;
import co.id.ez.ezpay.model.data.search.PospaidModel;
import co.id.ez.ezpay.msg.pos.PospaidInquiry;
import co.id.ez.ezpay.msg.pos.PospaidPayment;
import com.json.JSONException;
import com.json.JSONObject;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 *
 * @author Lutfi
 */
public class PLNPospaidModule extends AbstractModule {

    LinkedList<PospaidModel> tagihan = new LinkedList<>();
    
    /**
     * Creates new form PLNPrepaidModule
     */
    public PLNPospaidModule() {
        initComponents();
        initForm();
        initListener();
    }

    @Override
    public final void initForm() {
        btn_cek.setIcon(Icons.LARGE_DONE.get());
        btn_batal.setIcon(Icons.LARGE_CANCEL.get());
        btn_bayar.setIcon(Icons.LARGE_PAY.get());
        Common.setOpaqueComponent(false, jPanel1);
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
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        btn_cek = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txt_idpel = new javax.swing.JTextField();
        txt_nama = new javax.swing.JTextField();
        txt_tarif = new javax.swing.JTextField();
        txt_admin = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txt_total = new javax.swing.JTextField();
        btn_bayar = new javax.swing.JButton();
        btn_batal = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        txt_total_periode = new javax.swing.JTextField();
        txt_periode = new javax.swing.JTextField();
        txt_tagihan = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txt_denda = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        btn_detail = new javax.swing.JButton();

        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Input Pembayaran", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 14), new java.awt.Color(255, 255, 255))); // NOI18N

        jLabel3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("1. ID Pel");

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

        jLabel5.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("2. Nama");

        jLabel6.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("4. Tarif");

        jLabel7.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("7. Admin");

        txt_idpel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_idpelActionPerformed(evt);
            }
        });
        txt_idpel.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_idpelKeyPressed(evt);
            }
        });

        txt_nama.setEditable(false);

        txt_tarif.setEditable(false);

        txt_admin.setEditable(false);

        jLabel9.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("8. Total Bayar");

        txt_total.setEditable(false);

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

        jLabel10.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("3. Periode");

        txt_total_periode.setEditable(false);

        txt_periode.setEditable(false);

        txt_tagihan.setEditable(false);

        jLabel8.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("5. Tagihan");

        txt_denda.setEditable(false);

        jLabel11.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("6. Denda");

        btn_detail.setText("Detail");
        btn_detail.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_detail.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_detailMouseClicked(evt);
            }
        });
        btn_detail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_detailActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_bayar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_batal, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_denda))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_tagihan))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_idpel))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txt_tarif, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txt_total))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txt_admin))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txt_total_periode, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txt_periode, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txt_nama)))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btn_cek, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_detail, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(52, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txt_idpel, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn_cek, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txt_nama, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txt_total_periode, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txt_periode, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txt_tarif, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txt_tagihan, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txt_denda, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txt_admin, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txt_total, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn_detail, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btn_bayar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_batal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(64, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        // TODO add your handling code here:
    }//GEN-LAST:event_formComponentShown

    private void btn_cekActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cekActionPerformed
        // TODO add your handling code here:
        performCek();
    }//GEN-LAST:event_btn_cekActionPerformed

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
                    + "Tagihan Listrik "+ txt_idpel.getText() + " ?", this
            );
            if (opt == 0) {
                performPayment();
            }
        } else {
            Common.showWarningMessage("Data transaksi belum valid, silahkan ulangi transaksi!", this);
            resetForm();
        }
    }//GEN-LAST:event_btn_bayarActionPerformed

    private void btn_detailMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_detailMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_detailMouseClicked

    private void btn_detailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_detailActionPerformed
        // TODO add your handling code here:
        showDetail();
    }//GEN-LAST:event_btn_detailActionPerformed

    private void txt_idpelKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_idpelKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            performCek();
        }
    }//GEN-LAST:event_txt_idpelKeyPressed

    private void txt_idpelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_idpelActionPerformed
        // TODO add your handling code here:
        btn_cek.requestFocus();
    }//GEN-LAST:event_txt_idpelActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_batal;
    private javax.swing.JButton btn_bayar;
    private javax.swing.JButton btn_cek;
    private javax.swing.JButton btn_detail;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField txt_admin;
    private javax.swing.JTextField txt_denda;
    private javax.swing.JTextField txt_idpel;
    private javax.swing.JTextField txt_nama;
    private javax.swing.JTextField txt_periode;
    private javax.swing.JTextField txt_tagihan;
    private javax.swing.JTextField txt_tarif;
    private javax.swing.JTextField txt_total;
    private javax.swing.JTextField txt_total_periode;
    // End of variables declaration//GEN-END:variables

    private void performCek(){
        int opt = Common.showConfirmMessage("Cek pembelian PLN Prepaid ?", this);
        if (opt == 0) {
            if (validateForm()) {
                performInquiry();
            } else {
                Common.showWarningMessage("Mohon pilih denom terlebih dahulu!", this);
            }
        }
    }
    
    @Override
    public final void resetForm() {
        Common.clearText(txt_admin, txt_idpel, txt_idpel, 
                txt_nama, txt_tagihan, txt_tarif, 
                txt_total_periode, txt_total, txt_admin, 
                txt_total, txt_periode, txt_denda);
        Common.activateComponent(false, btn_bayar, btn_batal, btn_detail);
        Common.activateComponent(true, btn_cek, txt_idpel);
        billerResponse = null;
        txt_idpel.requestFocus();
    }

    @Override
    public final void initListener() {
    }

    @Override
    public void search() {
    }

    @Override
    public void loadData() {
    }

    @Override
    public void performInquiry() {
        try {
            PospaidInquiry request = new PospaidInquiry();
            request.setIDPelanggan(txt_idpel.getText());
            billerResponse = sendRequest(request);

            if (billerResponse != null) {
                setDataInquiry();
                saveTransaction();
                Common.activateComponent(true, btn_bayar, btn_batal, btn_detail);
                Common.activateComponent(false, btn_cek, txt_idpel);
                btn_bayar.requestFocus();
            } else {
                txt_idpel.requestFocus();
            }
        } catch (Exception e) {
            Common.errorLog("Failed to execute Perform Prepaid inquiry", e);
            Common.showErrorMessage("Error System!\nSilahkan hubungi helpdesk kami", this);
            resetForm();
        }
    }
    
    private void setDataInquiry(){
        JSONObject tPayload = billerResponse.getPayload();
        String tTotalPeriode = tPayload.getString("bulan");
        String tNama = tPayload.getString("nama");
        String tTarif = tPayload.getString("tarif");
        String tBLTH = tPayload.getString("blth");
        int tPeriode = Integer.parseInt(tPayload.getString("bulan").replace("BLN", "").trim());
        
        BigDecimal tTagihan = BigDecimal.ZERO, 
                tDenda = BigDecimal.ZERO, 
                tAdminCharge, 
                tTotal
                ;
        
        tagihan.clear();
        for (int i = 0; i < tPeriode; i++) {
            BigDecimal tag = new BigDecimal(tPayload.get("tagbln_" + (i + 1)).toString());
            BigDecimal denda = new BigDecimal(tPayload.get("penalty_" + (i + 1)).toString());
            BigDecimal adm = new BigDecimal(tPayload.get("admin_" + (i + 1)).toString());
            String tBulan = tPayload.getString("bln_" + (i+1));
            String meter = tPayload.getString("meter_" + (i+1));
            
            PospaidModel model = new PospaidModel(tBulan, tNama, meter, tag, denda, adm);
            
            tTagihan = tTagihan.add(model.getTagihan());
            tDenda = tDenda.add(model.getDenda());
            tagihan.add(model);
        }
        
        tAdminCharge = new BigDecimal(tPayload.get("admin").toString());
        tTotal = new BigDecimal(tPayload.get("total").toString());
        
        txt_nama.setText(tNama);
        txt_tarif.setText(tTarif);
        txt_periode.setText(tBLTH);
        txt_total_periode.setText(tTotalPeriode);
        txt_tagihan.setText(Common.formatRupiah(tTagihan.doubleValue()));
        txt_denda.setText(Common.formatRupiah(tDenda.doubleValue()));
        txt_admin.setText(Common.formatRupiah(tAdminCharge.doubleValue()));
        txt_total.setText(Common.formatRupiah(tTotal.doubleValue()));
    }

    @Override
    public void performPayment() {
        try {
            prePostPayment("trx_pos_sales", billerResponse);
            
            PospaidPayment request = new PospaidPayment(billerResponse);
            billerResponse = sendRequest(request);

            if (billerResponse != null) {
                updateDataTransaction();
            } else {
                resetForm();
            }
        } catch (Exception e) {
            Common.errorLog("Failed to execute Perform prepaid payment", e);
            Common.showErrorMessage(MessageType.FATAL_ERROR, btn_bayar);
        }
    }

    @Override
    public boolean validateForm() {
        return txt_idpel.getText().length() > 0;
    }

    @Override
    public void updateDataTransaction() {
        try {
            JSONObject resp = billerResponse.getPayload();
            transactionController.updateSuccessTransactionPospaid(resp);
            Common.showInfoMessage(resp.getString("rcm"), this);
            previewStruk(resp.getString("trxid"), "trx_pos_sales");
            resetForm();
        } catch (JSONException | SQLException e) {
            Common.showErrorMessage(MessageType.SYSTEM_ERROR, this);
            Common.errorLog("[SQLException] Error on insert pospaid transaction", e);
            resetForm();
        }
    }

    @Override
    public QueryBuilder contructQueryInsertTransaction() {
        JSONObject resp = billerResponse.getPayload();
        QueryBuilder builder = new QueryBuilder("trx_pos_sales", QueryType.INSERT);
        builder.addEntryValue("id", "UUID()", true);
        builder.addEntryValue("transaction_date", "NOW()", true);
        builder.addEntryValue("user_id", App.environment().getDEU());
        builder.addEntryValue("subscriber_id", resp.getString("idpel"));
        builder.addEntryValue("segmentation", resp.getString("tarif"));
        builder.addEntryValue("subscriber_name", Common.escape(resp.getString("nama")));
        builder.addEntryValue("total_bill_amount", resp.get("total").toString());
        builder.addEntryValue("total_admin_charge", resp.get("admin").toString());
        builder.addEntryValue("bill_status", ""+tagihan.size());

        BigDecimal tBillTotal = BigDecimal.ZERO,
                tDendaTotal = BigDecimal.ZERO;
        int index = 0;
        for (PospaidModel pDAMModel : tagihan) {
            builder.addEntryValue("periode_" + (index + 1), pDAMModel.getBulan());
            builder.addEntryValue("bill_amount_" + (index + 1), pDAMModel.getTagihan().toPlainString());
            builder.addEntryValue("bill_denda_" + (index + 1), pDAMModel.getDenda().toPlainString());
            builder.addEntryValue("bill_adm_" + (index + 1), pDAMModel.getAdm().toPlainString());
            builder.addEntryValue("meter_" + (index + 1), String.valueOf(pDAMModel.getMeter()));
            tBillTotal = tBillTotal.add(pDAMModel.getTagihan());
            tDendaTotal = tDendaTotal.add(pDAMModel.getDenda());
            index++;
        }

        builder.addEntryValue("total_bill", tBillTotal.toPlainString());
        builder.addEntryValue("total_denda", tDendaTotal.toPlainString());
        builder.addEntryValue("refnumber", resp.get("refnum").toString());
        builder.addEntryValue("transaction_id", resp.get("trxid").toString());
        builder.addEntryValue("info", Common.escape(resp.get("text").toString()));
        builder.addEntryValue("no_faktur", "");
        
        return builder;
    }
    
    private void showDetail() {
        try {
            JSONObject tPayload = billerResponse.getPayload();
            String tTotalPeriode = tPayload.getString("bulan");
            String tNama = tPayload.getString("nama");
            String tIdpel = txt_idpel.getText();

            DetailDataFactory dataList = new DetailDataFactory();

            dataList.create(
                    Field.bind("ID Pelanggan ", 15, Aligment.LEFT, " "),
                    Field.bind(": ", 2),
                    Field.bind(tIdpel, -1, Aligment.LEFT, " ")
            );
            dataList.create(
                    Field.bind("Nama Pelanggan ", 15, Aligment.LEFT, " "),
                    Field.bind(": ", 2),
                    Field.bind(tNama, -1, Aligment.LEFT, " ")
            );
            dataList.create(
                    Field.bind("Total Periode ", 15, Aligment.LEFT, " "),
                    Field.bind(": ", 2),
                    Field.bind(tTotalPeriode, -1, Aligment.LEFT, " ")
            );
            
            int index = 0;
            for (PospaidModel pDAMModel : tagihan) {
                dataList.create(
                        Field.bind("-", 40, "-")
                );
                dataList.create(
                        Field.bind("Tagihan Ke-" + (index + 1))
                );
                dataList.create(
                        Field.bind("-", 40, "-")
                );

                BigDecimal tag = pDAMModel.getTagihan();
                BigDecimal denda = pDAMModel.getDenda();
                BigDecimal adm = pDAMModel.getAdm();
                String tBulan = pDAMModel.getBulan();
                String meterawal = pDAMModel.getMeter();
                
                dataList.create(
                        Field.bind("Periode ", 15, Aligment.LEFT, " "),
                        Field.bind(": ", 2),
                        Field.bind(tBulan, -1, Aligment.LEFT, " ")
                );
                
                dataList.create(
                        Field.bind("Meter ", 15, Aligment.LEFT, " "),
                        Field.bind(": ", 2),
                        Field.bind(String.valueOf(meterawal), -1, Aligment.LEFT, " ")
                );
                
                dataList.create(
                        Field.bind("Tagihan ", 15, Aligment.LEFT, " "),
                        Field.bind(": ", 2),
                        Field.bind(Common.formatRupiah(tag.doubleValue()), -1, Aligment.LEFT, " ")
                );
                
                dataList.create(
                        Field.bind("Denda ", 15, Aligment.LEFT, " "),
                        Field.bind(": ", 2),
                        Field.bind(Common.formatRupiah(denda.doubleValue()), -1, Aligment.LEFT, " ")
                );
                
                
                dataList.create(
                        Field.bind("Admin ", 15, Aligment.LEFT, " "),
                        Field.bind(": ", 2),
                        Field.bind(Common.formatRupiah(adm.doubleValue()), -1, Aligment.LEFT, " ")
                );
                
                dataList.create(
                        Field.bind("Total ", 15, Aligment.LEFT, " "),
                        Field.bind(": ", 2),
                        Field.bind(Common.formatRupiah(tag.add(denda).add(adm).doubleValue()), -1, Aligment.LEFT, " ")
                );

                index++;
            }
            
            Common.showDetailForm(dataList, this, "Detail Tagihan PDAM");
        } catch (JSONException e) {
            Common.showErrorMessage(MessageType.SYSTEM_ERROR, this);
            Common.errorLog("[JSONException] failed load detail data", e);
        }
    }
    
    @Override
    public Field[][] createBodyStruk(JSONObject pTranmainData) {
        return createBodyStrukPospaid(pTranmainData);
    }
}
