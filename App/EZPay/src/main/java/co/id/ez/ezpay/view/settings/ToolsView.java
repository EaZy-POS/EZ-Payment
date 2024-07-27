/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.view.settings;

import co.id.ez.ezpay.abstracts.AbstractView;
import co.id.ez.ezpay.app.App;
import co.id.ez.ezpay.app.AppSetting;
import co.id.ez.ezpay.app.Common;
import co.id.ez.ezpay.app.Printer;
import co.id.ez.ezpay.enums.util.Icons;
import co.id.ez.system.core.etc.EncryptionService;
import java.awt.event.KeyEvent;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author RCS
 */
public class ToolsView extends AbstractView {
    private DefaultTableModel model = new DefaultTableModel();
    
    /**
     * Creates new form SyncronizeView
     */
    public ToolsView() {
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
        jPanel1 = new javax.swing.JPanel();
        panelAdmin = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        lbl_x = new javax.swing.JLabel();
        cmb_printer = new javax.swing.JComboBox<>();
        lbl_x1 = new javax.swing.JLabel();
        cek_default = new javax.swing.JCheckBox();
        rd_58 = new javax.swing.JRadioButton();
        rd_75 = new javax.swing.JRadioButton();
        rd_80 = new javax.swing.JRadioButton();
        lbl_x2 = new javax.swing.JLabel();
        txt_breakline = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        table_footer = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        btn_simpan = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        cek_autoupdate = new javax.swing.JCheckBox();

        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });
        setLayout(new java.awt.CardLayout());

        jPanel1.setLayout(new java.awt.CardLayout());

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Struk", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 13), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanel2.setPreferredSize(new java.awt.Dimension(580, 128));

        lbl_x.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lbl_x.setForeground(new java.awt.Color(255, 255, 255));
        lbl_x.setText("Printer");
        lbl_x.setPreferredSize(new java.awt.Dimension(74, 25));

        cmb_printer.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        lbl_x1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lbl_x1.setForeground(new java.awt.Color(255, 255, 255));
        lbl_x1.setText("Ukuran Kertas");
        lbl_x1.setPreferredSize(new java.awt.Dimension(74, 25));

        cek_default.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        cek_default.setForeground(new java.awt.Color(255, 255, 255));
        cek_default.setText("default printer");
        cek_default.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cek_defaultItemStateChanged(evt);
            }
        });

        buttonGroup1.add(rd_58);
        rd_58.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        rd_58.setForeground(new java.awt.Color(255, 255, 255));
        rd_58.setText("58");

        buttonGroup1.add(rd_75);
        rd_75.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        rd_75.setForeground(new java.awt.Color(255, 255, 255));
        rd_75.setText("75");

        buttonGroup1.add(rd_80);
        rd_80.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        rd_80.setForeground(new java.awt.Color(255, 255, 255));
        rd_80.setText("80");

        lbl_x2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lbl_x2.setForeground(new java.awt.Color(255, 255, 255));
        lbl_x2.setText("Break Line");
        lbl_x2.setPreferredSize(new java.awt.Dimension(74, 25));

        txt_breakline.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        jSeparator1.setPreferredSize(new java.awt.Dimension(0, 1));

        table_footer.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        table_footer.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                table_footerKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(table_footer);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addComponent(lbl_x, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmb_printer, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cek_default))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addComponent(lbl_x1, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rd_58, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rd_75, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rd_80, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addComponent(lbl_x2, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_breakline, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 70, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_x, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmb_printer, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cek_default))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lbl_x1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(rd_58, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(rd_75, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(rd_80, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_x2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txt_breakline, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(8, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12), new java.awt.Color(255, 255, 255))); // NOI18N

        btn_simpan.setText("Simpan");
        btn_simpan.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_simpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_simpanActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btn_simpan, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btn_simpan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Advance", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12), new java.awt.Color(255, 255, 255))); // NOI18N

        cek_autoupdate.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        cek_autoupdate.setForeground(new java.awt.Color(255, 255, 255));
        cek_autoupdate.setText("Auto update");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cek_autoupdate)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(cek_autoupdate)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelAdminLayout = new javax.swing.GroupLayout(panelAdmin);
        panelAdmin.setLayout(panelAdminLayout);
        panelAdminLayout.setHorizontalGroup(
            panelAdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAdminLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelAdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 650, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelAdminLayout.setVerticalGroup(
            panelAdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAdminLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 363, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(74, Short.MAX_VALUE))
        );

        jPanel1.add(panelAdmin, "card3");

        add(jPanel1, "card3");
    }// </editor-fold>//GEN-END:initComponents

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        // TODO add your handling code here:
    }//GEN-LAST:event_formComponentShown

    private void btn_simpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_simpanActionPerformed
        // TODO add your handling code here:
        int opt = Common.showConfirmMessage("Simpan pengaturan?", this);
        if(opt == 0){
            saveSetting();
        }
    }//GEN-LAST:event_btn_simpanActionPerformed

    private void cek_defaultItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cek_defaultItemStateChanged
        // TODO add your handling code here:
        if(cek_default.isSelected()){
            cmb_printer.setSelectedItem(Printer.instance().getDefaultPrinter());
            cmb_printer.setEnabled(false);
        }else{
            cmb_printer.setEnabled(true);
        }
    }//GEN-LAST:event_cek_defaultItemStateChanged

    private void table_footerKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_table_footerKeyPressed
        // TODO add your handling code here:
        String curentResult = table_footer.getValueAt(table_footer.getSelectedRow(), 0).toString();
        if (evt.getKeyCode() == KeyEvent.VK_DOWN) {
            if (!curentResult.trim().equals("")) {
                if (table_footer.getSelectedRow() == table_footer.getRowCount() - 1) {
                    model.addRow(new String[]{""});
                }
            }
        }
        
    }//GEN-LAST:event_table_footerKeyPressed

    private void loadPrinterName(){
        cmb_printer.removeAllItems();
        cmb_printer.addItem("-- Pilih printer --");
        Printer.instance().getPrinterNameList().forEach(printer -> {
            cmb_printer.addItem(printer);
        });
        cmb_printer.setSelectedIndex(0);
    }
    
    private void loadAppSetting(){
        cek_default.setSelected(AppSetting.instance().getBoolean("usedefaultprinter"));
        cek_autoupdate.setSelected(AppSetting.instance().getBoolean("autoupdate"));
        
        String printer = AppSetting.instance().getString("printername");
        if(printer != null){
            if(Printer.instance().hasPrinterName(printer)){
                cmb_printer.setSelectedItem(printer);
            }
        }
        
        int printerSize = AppSetting.instance().getInteger("printersize");
        if (printerSize > 0) {
            rd_58.setSelected(printerSize == 58);
            rd_75.setSelected(printerSize == 75);
            rd_80.setSelected(printerSize == 80);
        }else{
            rd_58.setSelected(true);
        }
        
        int breakline = AppSetting.instance().getInteger("breakline");
        if(printerSize > 0){
            txt_breakline.setText(""+ breakline);
        }else{
            txt_breakline.setText("0");
        }
        
        String footer = AppSetting.instance().getString("footer");
        loadDataFooter(footer);
    }
    
    private void saveSetting(){
        if(cmb_printer.getSelectedIndex() > 0){
            AppSetting.instance().put("printername", cmb_printer.getSelectedItem());
            AppSetting.instance().put("usedefaultprinter", cek_default.isSelected() ? "true" : "false");
            AppSetting.instance().put(
                    "printersize",
                    rd_58.isSelected()
                    ? "58"
                    : rd_75.isSelected()
                    ? "75"
                    : rd_80.isSelected()
                    ? "80" : "-1"
            );

            AppSetting.instance().put("breakline", txt_breakline.getText());
            AppSetting.instance().put("autoupdate", cek_autoupdate.isSelected() ? "true" : "false");

            String tFooter = getSettingFooterStruk();
            if(tFooter.length() > 0){
                AppSetting.instance().put("footer", tFooter);
            }
            
            AppSetting.instance().save();

            Common.showInfoMessage("Pengaturan berhasil disimpan", this);
            loadAppSetting();
        }else{
            Common.showWarningMessage("Mohon pilih printer!", this);
        }
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_simpan;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JCheckBox cek_autoupdate;
    private javax.swing.JCheckBox cek_default;
    private javax.swing.JComboBox<String> cmb_printer;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lbl_x;
    private javax.swing.JLabel lbl_x1;
    private javax.swing.JLabel lbl_x2;
    private javax.swing.JPanel panelAdmin;
    private javax.swing.JRadioButton rd_58;
    private javax.swing.JRadioButton rd_75;
    private javax.swing.JRadioButton rd_80;
    private javax.swing.JTable table_footer;
    private javax.swing.JTextField txt_breakline;
    // End of variables declaration//GEN-END:variables

    @Override
    public final void initForm() {
        Common.setOpaqueComponent(
                        false, 
                        this, jPanel2, panelAdmin, jPanel1,
                        cek_autoupdate, cek_default, 
                        rd_58, rd_75, rd_80,
                        jPanel3, jPanel4, jScrollPane1, jSeparator1);
        jScrollPane1.getViewport().setOpaque(false);
        btn_simpan.setIcon(Icons.LARGE_SAVE.get());
        loadPrinterName();
        loadAppSetting();
    }

    @Override
    public void resetForm() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    private void loadDataFooter(String dataFooter) {
        model = new DefaultTableModel(new String[]{"Footer struk"}, 0);
        if (dataFooter != null) {

            String[] footer = dataFooter.split(";");
            
            for (String footer1 : footer) {
                String[] result = new String[]{EncryptionService
                        .encryptor()
                        .decrypt(footer1.replace("[", "").replaceAll("]", "").replaceAll("<>", "="), 
                                App.environment().getEnv_key()
                        )
                };
                model.addRow(result);
            }
        }else{
            model.addRow(new String[]{""});
        }
        
        table_footer.setModel(model);
        table_footer.setEditingColumn(0);
    }
    
    private String getSettingFooterStruk() {
        String result = "";
        for (int i = 0; i < table_footer.getRowCount(); i++) {
            Object footer = table_footer.getValueAt(i, 0);
            if (footer != null && !footer.toString().equals("")) {
                result = result.concat(i == 0 ? "" : ";")
                        .concat(
                                EncryptionService.encryptor().encrypt(footer.toString(),
                                        App.environment().getEnv_key())
                        ).replace("=", "<>");
            }
        }

        return result;
    }
}
