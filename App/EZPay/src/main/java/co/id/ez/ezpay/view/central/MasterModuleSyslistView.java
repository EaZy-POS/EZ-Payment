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
import co.id.ez.ezpay.model.data.central.CentralModuleSyslistModel;
import co.id.ez.ezpay.msg.data.modulesyslist.AddModuleSyslist;
import co.id.ez.ezpay.msg.data.modulesyslist.DeleteModuleSysList;
import co.id.ez.ezpay.msg.data.modulesyslist.GetModuleSysList;
import co.id.ez.ezpay.msg.data.modulesyslist.UpdateModuleSyslist;
import co.id.ez.ezpay.util.swings.table.TableActionEvent;
import com.json.JSONArray;
import com.json.JSONObject;
import java.util.HashMap;
import java.util.LinkedList;

/**
 *
 * @author RCS
 */
public class MasterModuleSyslistView extends CentralDataForm<CentralModuleSyslistModel> {

    private final HashMap<String, String> modul = new HashMap<>();
    private final HashMap<String, LinkedList<DataTable>> modulSyslist = new HashMap<>();
    private boolean isEditStage, isRefresh;
    private String mSelectedModule;

    /**
     * Creates new form SyncronizeView
     */
    public MasterModuleSyslistView() {
        super(Master.MODULE_SYSLIST.getTableHeader(), false, false);
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

        jPanel3 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        lbl_x = new javax.swing.JLabel();
        lbl_x1 = new javax.swing.JLabel();
        btn_simpan = new javax.swing.JButton();
        txt_id = new javax.swing.JTextField();
        txt_path = new javax.swing.JTextField();
        btn_batal = new javax.swing.JButton();
        cek_aktif = new javax.swing.JCheckBox();
        jPanel4 = new javax.swing.JPanel();
        panelData = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cmb_module = new javax.swing.JComboBox<>();
        btn_refresh = new javax.swing.JButton();

        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });
        setLayout(new java.awt.CardLayout());

        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Form Input", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 13), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanel2.setPreferredSize(new java.awt.Dimension(450, 90));

        lbl_x.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lbl_x.setForeground(new java.awt.Color(255, 255, 255));
        lbl_x.setText("ID");

        lbl_x1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lbl_x1.setForeground(new java.awt.Color(255, 255, 255));
        lbl_x1.setText("Path");

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

        txt_path.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

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

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(lbl_x1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(btn_simpan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_batal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txt_path)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(lbl_x, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_id, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addComponent(cek_aktif, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
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
                    .addComponent(txt_path, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_batal, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_simpan, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(285, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel2, java.awt.BorderLayout.LINE_START);

        jPanel4.setLayout(new java.awt.BorderLayout(0, 3));

        panelData.setLayout(new java.awt.CardLayout());
        jPanel4.add(panelData, java.awt.BorderLayout.CENTER);

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel5.setPreferredSize(new java.awt.Dimension(427, 50));

        jLabel1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel1.setText("Module");
        jLabel1.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        cmb_module.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        cmb_module.setPreferredSize(new java.awt.Dimension(72, 25));
        cmb_module.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmb_moduleItemStateChanged(evt);
            }
        });

        btn_refresh.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        btn_refresh.setText("Refresh");
        btn_refresh.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                btn_refreshComponentShown(evt);
            }
        });
        btn_refresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_refreshActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmb_module, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_refresh)
                .addContainerGap(52, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(0, 11, Short.MAX_VALUE)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmb_module, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_refresh, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel4.add(jPanel5, java.awt.BorderLayout.PAGE_START);

        jPanel1.add(jPanel4, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 406, Short.MAX_VALUE)
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

    private void cmb_moduleItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmb_moduleItemStateChanged
        // TODO add your handling code here:
        if (cmb_module.getSelectedIndex() > 0) {
            resetForm();
            LinkedList<CentralModuleSyslistModel> list = new LinkedList<>();
            int index = 0;
            mSelectedModule = cmb_module.getSelectedItem().toString();
            for (DataTable dataTable : modulSyslist.get(modul.get(mSelectedModule))) {
                index++;
                CentralModuleSyslistModel model = (CentralModuleSyslistModel) dataTable;
                model.setNumber(index);
                list.add(model);
            }

            loadDataToTables(list, getTableActionEvent());
        }
    }//GEN-LAST:event_cmb_moduleItemStateChanged

    private void btn_refreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_refreshActionPerformed
        // TODO add your handling code here:
        if(Common.showConfirmMessage("Refreh data?", this) == 0){
            isRefresh = true;
            loadData();
            isRefresh = false;
            Common.showInfoMessage("Data berhasil di refresh", this);
        }
    }//GEN-LAST:event_btn_refreshActionPerformed

    private void btn_refreshComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_btn_refreshComponentShown
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_refreshComponentShown

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        // TODO add your handling code here:
    }//GEN-LAST:event_formComponentShown


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_batal;
    private javax.swing.JButton btn_refresh;
    private javax.swing.JButton btn_simpan;
    private javax.swing.JCheckBox cek_aktif;
    private javax.swing.JComboBox<String> cmb_module;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JLabel lbl_x;
    private javax.swing.JLabel lbl_x1;
    private javax.swing.JPanel panelData;
    private javax.swing.JTextField txt_id;
    private javax.swing.JTextField txt_path;
    // End of variables declaration//GEN-END:variables

    @Override
    public boolean validateForm() {
        if (cmb_module.getSelectedIndex() <= 0) {
            Common.showWarningMessage("Mohon pilih module terlebih dahulu!", this);
            return false;
        }

        if (txt_path.getText().equals("")) {
            txt_path.requestFocus();
            Common.showWarningMessage("Mohon isi path dengan benar!", this);
            return false;
        }

        if (!isEditStage) {
            for (DataTable dataTable : modulSyslist.get(modul.get(cmb_module.getSelectedItem().toString()))) {
                CentralModuleSyslistModel model = (CentralModuleSyslistModel) dataTable;
                if (txt_path.getText().equals(model.getPath())) {
                    txt_path.requestFocus();
                    Common.showWarningMessage("Path Sudah digunakan!", this);
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public final void initForm() {
        Common.setOpaqueComponent(false, this, jPanel2, panelData, jPanel1,
                panelData, jPanel1, jPanel3, cek_aktif, jPanel4, jPanel5);
        loadViewTable(panelData);
        btn_simpan.setIcon(Icons.SMALL_SAVE.get());
        btn_batal.setIcon(Icons.SMALL_CANCEL.get());
        viewTableData.hideHeaderSeparator();
        viewTableData.clearTable();
        resetForm();
    }

    @Override
    public void resetForm() {
        txt_id.setText("Generated by system");
        Common.clearText(txt_path);
        isEditStage = false;
        txt_path.requestFocus();
        cek_aktif.setVisible(isEditStage);
        txt_path.setEditable(true);
        cmb_module.setEnabled(true);
    }

    @Override
    public void loadData(String search) {
        try {
            viewTableData.clearTable();
            if (search.length() == 0) {
                if (cmb_module.getSelectedIndex() > 0 && !isRefresh) {
                    LinkedList<CentralModuleSyslistModel> list = new LinkedList<>();
                    int index = 0;
                    for (DataTable dataTable : modulSyslist.get(modul.get(cmb_module.getSelectedItem().toString()))) {
                        CentralModuleSyslistModel model = (CentralModuleSyslistModel) dataTable;
                        if (model.getModuleid().toLowerCase().contains(search.toLowerCase())
                                || model.getModuleName().toLowerCase().contains(search.toLowerCase())
                                || model.getPath().toLowerCase().contains(search.toLowerCase())) {
                            index++;
                            model.setNumber(index);
                            list.add(model);
                        }
                    }

                    loadDataToTables(list, getTableActionEvent());
                } else {
                    JSONObject data = sendRequetToCentral(new GetModuleSysList(), "GET");
                    modul.clear();
                    modulSyslist.clear();

                    if (data != null) {
                        JSONArray dataModule = data.getJSONArray("data");
                        for (int i = 0; i < dataModule.length(); i++) {
                            JSONObject module = dataModule.getJSONObject(i);
                            String tID = module.get("id").toString();
                            String tModuleID = module.get("module_id").toString();
                            String tModuleName = module.get("module_name").toString();
                            String tPath = module.get("path").toString();
                            String tStatus = module.getInt("status") == 1 ? "Aktif" : "Tidak Aktif";

                            CentralModuleSyslistModel model = new CentralModuleSyslistModel(
                                    i + 1,
                                    tID,
                                    tModuleID,
                                    tModuleName,
                                    tPath,
                                    tStatus,
                                    null
                            );

                            modul.put(tModuleName, tModuleID);
                            if (modulSyslist.containsKey(tModuleID)) {
                                modulSyslist.get(tModuleID).add(model);
                            } else {
                                LinkedList<DataTable> list = new LinkedList<>();
                                list.add(model);
                                modulSyslist.put(tModuleID, list);
                            }
                        }
                        loadDataModule();
                    }
                }
            } else {
                if (cmb_module.getSelectedIndex() > 0) {
                    LinkedList<CentralModuleSyslistModel> list = new LinkedList<>();
                    int index = 0;
                    for (DataTable dataTable : modulSyslist.get(modul.get(cmb_module.getSelectedItem().toString()))) {
                        CentralModuleSyslistModel model = (CentralModuleSyslistModel) dataTable;
                        if (model.getModuleid().toLowerCase().contains(search.toLowerCase())
                                || model.getModuleName().toLowerCase().contains(search.toLowerCase())
                                || model.getPath().toLowerCase().contains(search.toLowerCase())) {
                            index++;
                            model.setNumber(index);
                            list.add(model);
                        }
                    }

                    loadDataToTables(list, getTableActionEvent());
                }
            }
        } catch (Exception e) {
            Common.showErrorMessage(MessageType.SYSTEM_ERROR, this);
            Common.errorLog("[Exception] Failed load data module system list. " + e.getMessage(), e);
        }
    }

    private void loadDataModule() {
        cmb_module.removeAllItems();
        cmb_module.addItem("-- Pilih Module --");
        modul.keySet().forEach(key -> {
            cmb_module.addItem(key);
        });

        cmb_module.setSelectedIndex(0);
        if(mSelectedModule != null && modul.containsKey(mSelectedModule)){
            cmb_module.setSelectedItem(mSelectedModule);
        }
    }

    @Override
    public void loadData() {
        loadData("");
    }

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
                CentralModuleSyslistModel model = (CentralModuleSyslistModel) viewTableData.getTableModel().getData().get(row);
                int opt = Common.showConfirmMessage("Rubah data module syslist " + model.getPath() + "?", viewTableData);

                if (opt == 0) {
                    editData(model);
                }
            }

            @Override
            public void onDelete(int row) {
                CentralModuleSyslistModel model = (CentralModuleSyslistModel) viewTableData.getTableModel().getData().get(row);
                int opt = Common.showConfirmMessage("Hapus data module syslist " + model.getPath() + "?", viewTableData);

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
    public void delete(CentralModuleSyslistModel model) {
        try {
            String module = cmb_module.getSelectedItem().toString();
            DeleteModuleSysList req = new DeleteModuleSysList();
            req.setID(model.getId());
            JSONObject data = sendRequetToCentral(req, "POST");
            if (data != null) {
                isRefresh = true;
                loadData();
                isRefresh = false;
                Common.showInfoMessage("Data berhasil dihapus", this);
                resetForm();
                cmb_module.setSelectedItem(module);
            }
        } catch (Exception e) {
            Common.showErrorMessage(MessageType.SYSTEM_ERROR, this);
            Common.errorLog("[Exception] Failed delete data jabatan", e);
        }
    }

    @Override
    public void simpan() {
        try {
            String path = txt_path.getText();
            String moduleID = modul.get(cmb_module.getSelectedItem().toString());
            String module = cmb_module.getSelectedItem().toString();
            String id = isEditStage ? txt_id.getText() : "";

            if (isEditStage) {
                UpdateModuleSyslist req = new UpdateModuleSyslist();
                req.setModulID(moduleID);
                req.setPathSyslist(path);
                req.setStatus(cek_aktif.isSelected() ? "1" : "0");
                req.setID(id);

                JSONObject data = sendRequetToCentral(req, "POST");
                if (data != null) {
                    isRefresh = true;
                    loadData();
                    isRefresh = false;
                    Common.showInfoMessage("Data berhasil dirubah", this);
                    resetForm();
                    cmb_module.setSelectedItem(module);
                }
            } else {
                AddModuleSyslist req = new AddModuleSyslist();
                req.setModulID(moduleID);
                req.setPathSyslist(path);
                req.setStatus(cek_aktif.isSelected() ? "1" : "0");

                JSONObject data = sendRequetToCentral(req, "POST");
                if (data != null) {
                    isRefresh = true;
                    loadData();
                    isRefresh = false;
                    Common.showInfoMessage("Data berhasil disimpan", this);
                    resetForm();
                    cmb_module.setSelectedItem(module);
                }
            }
        } catch (Exception e) {
            Common.showErrorMessage(MessageType.SYSTEM_ERROR, this);
            Common.errorLog("[Exception] Failed insert data karyawan", e);
            resetForm();
        }
    }

    @Override
    public void editData(CentralModuleSyslistModel model) {
        txt_id.setText(model.getId());
        txt_path.setText(model.getPath());
        isEditStage = true;
        cek_aktif.setVisible(true);
        cek_aktif.setSelected(model.getStatus().equalsIgnoreCase("aktif"));
        txt_path.requestFocus();
        cmb_module.setEnabled(false);
    }

    @Override
    public String getDataName() {
        return "Data Module System List";
    }

}
