/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.view.central;

import co.id.ez.ezpay.view.master.*;
import co.id.ez.ezpay.app.Common;
import co.id.ez.ezpay.enums.MessageType;
import co.id.ez.ezpay.enums.util.Icons;
import co.id.ez.ezpay.interfaces.DataTable;
import co.id.ez.ezpay.model.data.central.CentralVoucherProductModel;
import co.id.ez.ezpay.msg.data.voucher.AddVoucherProduct;
import co.id.ez.ezpay.msg.data.voucher.DeleteVoucherProduct;
import co.id.ez.ezpay.msg.data.voucher.GetVoucherProduct;
import co.id.ez.ezpay.msg.data.voucher.MarkupVoucherProduct;
import co.id.ez.ezpay.msg.data.voucher.UpdateVoucherProduct;
import co.id.ez.ezpay.msg.data.voucher.provider.GetVoucherProvider;
import co.id.ez.ezpay.util.swings.input.InputTextType;
import co.id.ez.ezpay.util.swings.table.TableActionEvent;
import com.json.JSONArray;
import com.json.JSONObject;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedList;

/**
 *
 * @author RCS
 */
public class MasterVoucherProductView extends CentralDataForm<CentralVoucherProductModel> {

    private final HashMap<String, String> provider = new HashMap<>();
    private final LinkedList<DataTable> result = new LinkedList<>();
    private boolean isEditStage;

    /**
     * Creates new form SyncronizeView
     */
    public MasterVoucherProductView() {
        super(Master.VOUCHER_PRODUCT.getTableHeader(), true, false);
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel3 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        lbl_x = new javax.swing.JLabel();
        lbl_x1 = new javax.swing.JLabel();
        btn_simpan = new javax.swing.JButton();
        txt_id = new javax.swing.JTextField();
        txt_vcrid = new javax.swing.JTextField();
        btn_batal = new javax.swing.JButton();
        cek_aktif = new javax.swing.JCheckBox();
        lbl_x2 = new javax.swing.JLabel();
        txt_vcrname = new javax.swing.JTextField();
        txt_sale_price = new javax.swing.JTextField();
        lbl_x3 = new javax.swing.JLabel();
        rd_vcr = new javax.swing.JRadioButton();
        lbl_x4 = new javax.swing.JLabel();
        rd_pdt = new javax.swing.JRadioButton();
        rd_ewl = new javax.swing.JRadioButton();
        rd_emy = new javax.swing.JRadioButton();
        lbl_x5 = new javax.swing.JLabel();
        cmb_provider = new javax.swing.JComboBox<>();
        jPanel4 = new javax.swing.JPanel();
        panelData = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        lbl_x6 = new javax.swing.JLabel();
        txt_margin = new javax.swing.JTextField();
        btn_terapkan = new javax.swing.JButton();

        setLayout(new java.awt.CardLayout());

        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Form Input", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 13), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanel2.setPreferredSize(new java.awt.Dimension(450, 90));

        lbl_x.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lbl_x.setForeground(new java.awt.Color(255, 255, 255));
        lbl_x.setText("ID");

        lbl_x1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lbl_x1.setForeground(new java.awt.Color(255, 255, 255));
        lbl_x1.setText("ID Voucher");

        btn_simpan.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        btn_simpan.setText("Simpan");
        btn_simpan.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_simpan.setPreferredSize(new java.awt.Dimension(100, 25));
        btn_simpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_simpanActionPerformed(evt);
            }
        });

        txt_id.setEditable(false);
        txt_id.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        txt_vcrid.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        btn_batal.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        btn_batal.setText("Batal");
        btn_batal.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_batal.setPreferredSize(new java.awt.Dimension(100, 25));
        btn_batal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_batalActionPerformed(evt);
            }
        });

        cek_aktif.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        cek_aktif.setForeground(new java.awt.Color(255, 255, 255));
        cek_aktif.setText("Aktif");
        cek_aktif.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cek_aktifItemStateChanged(evt);
            }
        });

        lbl_x2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lbl_x2.setForeground(new java.awt.Color(255, 255, 255));
        lbl_x2.setText("Nama Voucher");

        txt_vcrname.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        txt_sale_price.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        lbl_x3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lbl_x3.setForeground(new java.awt.Color(255, 255, 255));
        lbl_x3.setText("Harga");

        buttonGroup1.add(rd_vcr);
        rd_vcr.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        rd_vcr.setForeground(new java.awt.Color(255, 255, 255));
        rd_vcr.setText("Voucher");

        lbl_x4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lbl_x4.setForeground(new java.awt.Color(255, 255, 255));
        lbl_x4.setText("Jenis");

        buttonGroup1.add(rd_pdt);
        rd_pdt.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        rd_pdt.setForeground(new java.awt.Color(255, 255, 255));
        rd_pdt.setText("Data");

        buttonGroup1.add(rd_ewl);
        rd_ewl.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        rd_ewl.setForeground(new java.awt.Color(255, 255, 255));
        rd_ewl.setText("EWallet");

        buttonGroup1.add(rd_emy);
        rd_emy.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        rd_emy.setForeground(new java.awt.Color(255, 255, 255));
        rd_emy.setText("Emoney");

        lbl_x5.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lbl_x5.setForeground(new java.awt.Color(255, 255, 255));
        lbl_x5.setText("Provider");

        cmb_provider.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(lbl_x, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_id, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cek_aktif, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(lbl_x1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_vcrid, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(lbl_x2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_vcrname, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(lbl_x4, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rd_vcr)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rd_pdt)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rd_ewl)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rd_emy))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(btn_simpan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btn_batal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addComponent(lbl_x5, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmb_provider, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(lbl_x3, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_sale_price, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(11, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_x, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_id, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cek_aktif, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_x1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_vcrid, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_x2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_vcrname, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_x4, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rd_vcr, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rd_pdt, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rd_ewl, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rd_emy, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_x5, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(cmb_provider, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_x3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_sale_price, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btn_batal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_simpan, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(322, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel2, java.awt.BorderLayout.LINE_START);

        jPanel4.setLayout(new java.awt.BorderLayout(0, 2));

        panelData.setLayout(new java.awt.CardLayout());
        jPanel4.add(panelData, java.awt.BorderLayout.CENTER);

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel5.setPreferredSize(new java.awt.Dimension(484, 45));

        lbl_x6.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lbl_x6.setForeground(new java.awt.Color(255, 255, 255));
        lbl_x6.setText("Margin");

        txt_margin.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        btn_terapkan.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        btn_terapkan.setText("Terapkan");
        btn_terapkan.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_terapkan.setPreferredSize(new java.awt.Dimension(100, 25));
        btn_terapkan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_terapkanActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_x6, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_margin, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_terapkan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(87, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lbl_x6, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txt_margin, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btn_terapkan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        jPanel4.add(jPanel5, java.awt.BorderLayout.PAGE_START);

        jPanel1.add(jPanel4, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1007, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 577, Short.MAX_VALUE)
                .addContainerGap())
        );

        add(jPanel3, "card2");
    }// </editor-fold>//GEN-END:initComponents

    private void btn_simpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_simpanActionPerformed
        // TODO add your handling code here:
        int opt = Common.showConfirmMessage("Simpan data?", this);
        if (opt == 0) {
            if (validateForm()) {
                simpan();
            }
        }
    }//GEN-LAST:event_btn_simpanActionPerformed

    private void btn_batalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_batalActionPerformed
        // TODO add your handling code here:
        int opt = Common.showConfirmMessage("Batal menyimpan data?", this);
        if (opt == 0) {
            resetForm();
            loadData();
        }
    }//GEN-LAST:event_btn_batalActionPerformed

    private void cek_aktifItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cek_aktifItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_cek_aktifItemStateChanged

    private void btn_terapkanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_terapkanActionPerformed
        // TODO add your handling code here:
        if (Common.showConfirmMessage("Terapkan Markup Harga?", this) == 0) {
            markupHarga();
        }
    }//GEN-LAST:event_btn_terapkanActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_batal;
    private javax.swing.JButton btn_simpan;
    private javax.swing.JButton btn_terapkan;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JCheckBox cek_aktif;
    private javax.swing.JComboBox<String> cmb_provider;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JLabel lbl_x;
    private javax.swing.JLabel lbl_x1;
    private javax.swing.JLabel lbl_x2;
    private javax.swing.JLabel lbl_x3;
    private javax.swing.JLabel lbl_x4;
    private javax.swing.JLabel lbl_x5;
    private javax.swing.JLabel lbl_x6;
    private javax.swing.JPanel panelData;
    private javax.swing.JRadioButton rd_emy;
    private javax.swing.JRadioButton rd_ewl;
    private javax.swing.JRadioButton rd_pdt;
    private javax.swing.JRadioButton rd_vcr;
    private javax.swing.JTextField txt_id;
    private javax.swing.JTextField txt_margin;
    private javax.swing.JTextField txt_sale_price;
    private javax.swing.JTextField txt_vcrid;
    private javax.swing.JTextField txt_vcrname;
    // End of variables declaration//GEN-END:variables

    @Override
    public boolean validateForm() {
        if (txt_vcrid.getText().equals("") || txt_vcrid.getText().equals("0")) {
            txt_vcrid.requestFocus();
            Common.showWarningMessage("Mohon isi denom dengan benar!", this);
            return false;
        }

        if (txt_vcrname.getText().equals("")) {
            txt_vcrname.requestFocus();
            Common.showWarningMessage("Mohon isi deskripsi dengan benar!", this);
            return false;
        }

        if (txt_sale_price.getText().equals("") || txt_sale_price.getText().equals("0")) {
            txt_sale_price.requestFocus();
            Common.showWarningMessage("Mohon isi harga jual dengan benar!", this);
            return false;
        }
        
        if(cmb_provider.getSelectedIndex() == 0){
            cmb_provider.requestFocus();
            Common.showWarningMessage("Mohon pilih provider dengan benar!", this);
            return false;
        }

        if (!isEditStage) {
            for (DataTable dataTable : result) {
                CentralVoucherProductModel model = (CentralVoucherProductModel) dataTable;
                if (txt_vcrid.getText().equals(model.getVoucherid())) {
                    txt_vcrid.requestFocus();
                    Common.showWarningMessage("Voucher ID Sudah digunakan!", this);
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public final void initForm() {
        btn_simpan.setIcon(Icons.SMALL_SAVE.get());
        btn_batal.setIcon(Icons.SMALL_CANCEL.get());
        Common.setOpaqueComponent(false, this, jPanel2, panelData, jPanel1,
                panelData, jPanel1, jPanel3, cek_aktif, rd_emy, rd_ewl, rd_pdt, rd_vcr, jPanel4, jPanel5);
        loadViewTable(panelData);
        btn_simpan.setIcon(Icons.SMALL_SAVE.get());
        btn_batal.setIcon(Icons.SMALL_CANCEL.get());
        viewTableData.hideHeaderSeparator();
        viewTableData.clearTable();
        InputTextType.makeCurrencyInput(txt_sale_price, txt_margin);
        InputTextType.makeLimitedInput(5, txt_vcrid);
        rd_vcr.setSelected(true);
        resetForm();
        loadDataProvider();
    }

    @Override
    public void resetForm() {
        txt_id.setText("Generated by system");
        Common.clearText(txt_vcrname, txt_vcrid, txt_sale_price);
        isEditStage = false;
        txt_vcrid.requestFocus();
        cek_aktif.setVisible(isEditStage);
        txt_vcrid.setEditable(true);
    }

    @Override
    public void loadData(String search) {
        try {
            viewTableData.clearTable();
            if (search.length() == 0) {
                JSONObject data = sendRequetToCentral(new GetVoucherProduct(), "GET");
                result.clear();

                if (data != null) {
                    JSONArray dataModule = data.getJSONArray("data");
                    int index = 0;
                    for (int i = 0; i < dataModule.length(); i++) {
                        JSONObject module = dataModule.getJSONObject(i);
                        String tProviderID = module.get("id").toString();
                        String tProvider = module.get("provider").toString();
                        JSONArray tVoucher = module.getJSONArray("voucher");

                        for (int j = 0; j < tVoucher.length(); j++) {
                            index++;
                            JSONObject datVoucher = tVoucher.getJSONObject(j);
                            String tType = datVoucher.get("product_type").toString();
                            String tVoucherName = datVoucher.get("voucher_name").toString();
                            String tVoucherID = datVoucher.get("voucher_id").toString();
                            String tID = datVoucher.get("id").toString();
                            BigDecimal tBasePrice = new BigDecimal(datVoucher.get("base_price").toString());
                            BigDecimal tMarkup = new BigDecimal(datVoucher.get("markup").toString());
                            BigDecimal tPrice = new BigDecimal(datVoucher.get("price").toString());
                            String tStatus = datVoucher.getBoolean("active") ? "Aktif" : "Tidak Aktif";

                            CentralVoucherProductModel model = new CentralVoucherProductModel(
                                    index,
                                    tID,
                                    tVoucherID,
                                    tProviderID,
                                    tVoucherName,
                                    tProvider,
                                    tType,
                                    tBasePrice,
                                    tMarkup,
                                    tPrice,
                                    tStatus,
                                    null
                            );
                            result.add(model);
                        }
                    }
                    loadDataToTables(result, getTableActionEvent());
                }
            } else {
                LinkedList<DataTable> tmpData = new LinkedList<>();
                int index = 0;
                for (DataTable dataTable : result) {
                    CentralVoucherProductModel model = (CentralVoucherProductModel) dataTable;
                    if (isContainsData(search, model.getVoucherid())
                            || isContainsData(search, model.getVoucherName())
                            || isContainsData(search, model.getProvider())) {
                        index++;
                        model.setNumber(index);
                        tmpData.add(dataTable);
                    }
                }
                loadDataToTables(tmpData, getTableActionEvent());
            }
        } catch (Exception e) {
            Common.showErrorMessage(MessageType.SYSTEM_ERROR, this);
            Common.errorLog("[Exception] Failed load data prepaid denom", e);
        }
    }

    private void loadDataProvider() {
        try {
            cmb_provider.removeAllItems();
            cmb_provider.addItem("-- Pilih Provider --");
            cmb_provider.setSelectedIndex(0);

            JSONObject data = sendRequetToCentral(new GetVoucherProvider(), "GET");
            if (data != null) {
                JSONArray dataModule = data.getJSONArray("data");
                for (int i = 0; i < dataModule.length(); i++) {
                    JSONObject module = dataModule.getJSONObject(i);
                    String tProviderID = module.get("id").toString();
                    String tProvider = module.get("provider_name").toString();
                    provider.put(tProvider, tProviderID);
                }

                provider.keySet().forEach(key -> {
                    cmb_provider.addItem(key);
                });
            }
        } catch (Exception e) {
            Common.showErrorMessage(MessageType.SYSTEM_ERROR, this);
            Common.errorLog("[Exception] Failed load data provider", e);
        }
    }

    @Override
    public void loadData() {
        loadData("");
    }

    @Override
    public TableActionEvent getTableActionEvent() {
        TableActionEvent event = new TableActionEvent() {
            @Override
            public void onView(int row) {
            }

            @Override
            public void onCheckStatus(int row) {
            }

            @Override
            public boolean canView() {
                return false;
            }

            @Override
            public boolean canCheck() {
                return false;
            }

            @Override
            public boolean canReprint() {
                return false;
            }

            @Override
            public void onReprint(int row) {
            }

            @Override
            public boolean canEdit() {
                return true;
            }

            @Override
            public void onEdit(int row) {
                CentralVoucherProductModel model = (CentralVoucherProductModel) viewTableData.getTableModel().getData().get(row);
                int opt = Common.showConfirmMessage("Rubah data voucher " + model.getVoucherid() + "?", viewTableData);

                if (opt == 0) {
                    editData(model);
                }
            }

            @Override
            public void onDelete(int row) {
                CentralVoucherProductModel model = (CentralVoucherProductModel) viewTableData.getTableModel().getData().get(row);
                int opt = Common.showConfirmMessage("Hapus data voucher " + model.getVoucherid() + "?", viewTableData);

                if (opt == 0) {
                    delete(model);
                }
            }

            @Override
            public boolean canDelete() {
                return true;
            }
        };

        return event;
    }

    @Override
    public void delete(CentralVoucherProductModel model) {
        try {
            DeleteVoucherProduct req = new DeleteVoucherProduct();
            req.setId(model.getId());
            JSONObject data = sendRequetToCentral(req, "POST");
            if (data != null) {
                loadData();
                Common.showInfoMessage("Data berhasil dihapus", this);
                resetForm();
            }
        } catch (Exception e) {
            Common.showErrorMessage(MessageType.SYSTEM_ERROR, this);
            Common.errorLog("[Exception] Failed delete data jabatan", e);
        }
    }

    private String getProductType() {
        if (rd_emy.isSelected()) {
            return "EMY";
        }

        if (rd_ewl.isSelected()) {
            return "EWL";
        }

        if (rd_pdt.isSelected()) {
            return "PDT";
        }

        if (rd_vcr.isSelected()) {
            return "VCR";
        }

        return "";
    }

    @Override
    public void simpan() {
        try {
            String voucgerid = txt_vcrid.getText();
            String vouchername = txt_vcrname.getText();
            String providerid = provider.get(cmb_provider.getSelectedItem().toString());
            BigDecimal price = new BigDecimal(txt_sale_price.getText().replace(",", ""));
            String producttype = getProductType();
            String id = isEditStage ? txt_id.getText() : "";

            if (isEditStage) {
                UpdateVoucherProduct req = new UpdateVoucherProduct();
                req.setPrice(price.toPlainString());
                req.setProduct_type(producttype);
                req.setProvider_id(providerid);
                req.setVoucher_id(voucgerid);
                req.setVoucher_name(vouchername);
                req.setId(id);
                req.setStatus(cek_aktif.isSelected() ? "1" : "0");

                JSONObject data = sendRequetToCentral(req, "POST");
                if (data != null) {
                    loadData();
                    Common.showInfoMessage("Data berhasil dirubah", this);
                    resetForm();
                }
            } else {
                AddVoucherProduct req = new AddVoucherProduct();
                req.setPrice(price.toPlainString());
                req.setProduct_type(producttype);
                req.setProvider_id(providerid);
                req.setVoucher_id(voucgerid);
                req.setVoucher_name(vouchername);

                JSONObject data = sendRequetToCentral(req, "POST");
                if (data != null) {
                    loadData();
                    Common.showInfoMessage("Data berhasil disimpan", this);
                    resetForm();
                }
            }

        } catch (Exception e) {
            Common.showErrorMessage(MessageType.SYSTEM_ERROR, this);
            Common.errorLog("[SQLException] Failed insert data karyawan", e);
            resetForm();
        }
    }

    public void markupHarga() {
        try {
            BigDecimal price = new BigDecimal(txt_margin.getText().replace(",", ""));

            MarkupVoucherProduct req = new MarkupVoucherProduct();
            req.setMarkup(price.toPlainString());
            
            JSONObject data = sendRequetToCentral(req, "POST");
            if (data != null) {
                loadData();
                Common.showInfoMessage("Data berhasil dirubah", this);
                resetForm();
            }
        } catch (Exception e) {
            Common.showErrorMessage(MessageType.SYSTEM_ERROR, this);
            Common.errorLog("[Exception] Failed sending reqest markup", e);
            resetForm();
        }
    }

    @Override
    public void editData(CentralVoucherProductModel model) {
        txt_id.setText(model.getId());
        txt_vcrid.setText(model.getVoucherid());
        txt_vcrname.setText(model.getVoucherName());
        txt_sale_price.setText(Common.formatRupiah(model.getBase_price().doubleValue(), false));
        cmb_provider.setSelectedItem(model.getProvider());
        isEditStage = true;
        cek_aktif.setVisible(true);
        cek_aktif.setSelected(model.getStatus().equalsIgnoreCase("aktif"));
        txt_vcrid.requestFocus();
    }

    @Override
    public String getDataName() {
        return "Data Produk Voucher";
    }

}
