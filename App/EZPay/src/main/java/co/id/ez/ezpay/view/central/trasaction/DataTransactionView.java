/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.view.central.trasaction;

import co.id.ez.ezpay.view.central.*;
import co.id.ez.ezpay.view.master.*;
import co.id.ez.ezpay.app.Common;
import co.id.ez.ezpay.enums.MessageType;
import co.id.ez.ezpay.enums.util.Icons;
import co.id.ez.ezpay.interfaces.DataTable;
import co.id.ez.ezpay.model.data.central.CentralModuleModel;
import co.id.ez.ezpay.msg.data.module.AddModule;
import co.id.ez.ezpay.msg.data.module.UpdateModule;
import co.id.ez.ezpay.msg.data.module.DeleteModule;
import co.id.ez.ezpay.msg.data.module.GetModuleList;
import co.id.ez.ezpay.util.swings.table.TableActionEvent;
import com.json.JSONArray;
import com.json.JSONObject;
import java.util.LinkedList;

/**
 *
 * @author RCS
 */
public class DataTransactionView extends CentralDataForm<CentralModuleModel> {

    private final LinkedList<DataTable> result = new LinkedList<>();
    private boolean isEditStage;

    /**
     * Creates new form SyncronizeView
     */
    public DataTransactionView() {
        super(Master.MODULE.getTableHeader(), true, false);
        initComponents();
        initForm();
        btn_simpan.setIcon(Icons.SMALL_SAVE.get());
        btn_batal.setIcon(Icons.SMALL_CANCEL.get());
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
        panelData = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        lbl_x = new javax.swing.JLabel();
        lbl_x1 = new javax.swing.JLabel();
        btn_simpan = new javax.swing.JButton();
        txt_id = new javax.swing.JTextField();
        txt_modul_id = new javax.swing.JTextField();
        btn_batal = new javax.swing.JButton();
        cek_aktif = new javax.swing.JCheckBox();
        lbl_x2 = new javax.swing.JLabel();
        txt_nama_modul = new javax.swing.JTextField();
        cek_isneed_sign = new javax.swing.JCheckBox();

        setLayout(new java.awt.CardLayout());

        jPanel1.setLayout(new java.awt.BorderLayout());

        panelData.setLayout(new java.awt.CardLayout());
        jPanel1.add(panelData, java.awt.BorderLayout.CENTER);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Form Input", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 13), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanel2.setPreferredSize(new java.awt.Dimension(450, 90));

        lbl_x.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lbl_x.setForeground(new java.awt.Color(255, 255, 255));
        lbl_x.setText("ID");

        lbl_x1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lbl_x1.setForeground(new java.awt.Color(255, 255, 255));
        lbl_x1.setText("ID Modul");

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

        txt_modul_id.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

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
        lbl_x2.setText("Nama Modul");

        txt_nama_modul.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        cek_isneed_sign.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        cek_isneed_sign.setForeground(new java.awt.Color(255, 255, 255));
        cek_isneed_sign.setText("butuh session login");
        cek_isneed_sign.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cek_isneed_signItemStateChanged(evt);
            }
        });

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
                        .addComponent(txt_modul_id, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(lbl_x2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(btn_simpan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_batal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(cek_isneed_sign, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txt_nama_modul, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)))))
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
                    .addComponent(txt_modul_id, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_x2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_nama_modul, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cek_isneed_sign, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btn_batal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_simpan, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(389, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel2, java.awt.BorderLayout.LINE_START);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 877, Short.MAX_VALUE)
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

    private void cek_isneed_signItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cek_isneed_signItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_cek_isneed_signItemStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_batal;
    private javax.swing.JButton btn_simpan;
    private javax.swing.JCheckBox cek_aktif;
    private javax.swing.JCheckBox cek_isneed_sign;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel lbl_x;
    private javax.swing.JLabel lbl_x1;
    private javax.swing.JLabel lbl_x2;
    private javax.swing.JPanel panelData;
    private javax.swing.JTextField txt_id;
    private javax.swing.JTextField txt_modul_id;
    private javax.swing.JTextField txt_nama_modul;
    // End of variables declaration//GEN-END:variables

    @Override
    public boolean validateForm() {
        if (txt_modul_id.getText().equals("")) {
            txt_modul_id.requestFocus();
            Common.showWarningMessage("Mohon isi module ID dengan benar!", this);
            return false;
        }

        if (txt_nama_modul.getText().equals("")) {
            txt_nama_modul.requestFocus();
            Common.showWarningMessage("Mohon isi nama module dengan benar!", this);
            return false;
        }

        if (!isEditStage) {
            for (DataTable dataTable : result) {
                CentralModuleModel model = (CentralModuleModel) dataTable;
                if (txt_modul_id.getText().equals(model.getModuleid())) {
                    txt_modul_id.requestFocus();
                    Common.showWarningMessage("Module ID Sudah digunakan!", this);
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public final void initForm() {
        Common.setOpaqueComponent(false, this, jPanel2, panelData, jPanel1,
                panelData, jPanel1, jPanel3, cek_aktif, cek_isneed_sign);
        loadViewTable(panelData);
        btn_simpan.setIcon(Icons.SMALL_SAVE.get());
        btn_batal.setIcon(Icons.SMALL_CANCEL.get());
        viewTableData.hideHeaderSeparator();
        viewTableData.clearTable();
        resetForm();
        loadData();
    }

    @Override
    public void resetForm() {
        txt_id.setText("Generated by system");
        Common.clearText(txt_nama_modul, txt_modul_id);
        isEditStage = false;
        txt_modul_id.requestFocus();
        cek_aktif.setVisible(isEditStage);
        txt_modul_id.setEditable(true);
        cek_isneed_sign.setSelected(false);
    }

    @Override
    public void loadData(String search) {
        try {
            viewTableData.clearTable();
            if (search.length() == 0) {
                JSONObject data = sendRequetToCentral(new GetModuleList(), "GET");
                result.clear();

                if (data != null) {
                    JSONArray dataModule = data.getJSONArray("data");
                    for (int i = 0; i < dataModule.length(); i++) {
                        JSONObject module = dataModule.getJSONObject(i);
                        String tID = module.get("id").toString();
                        String tModuleID = module.get("module_id").toString();
                        String tModuleName = module.get("module_name").toString();
                        String tStatus = module.getInt("status") == 1 ? "Aktif" : "Tidak Aktif";
                        boolean isNeedSign = module.getInt("is_need_sign") == 1;

                        CentralModuleModel model = new CentralModuleModel(
                                i + 1,
                                tID,
                                tModuleID,
                                tModuleName,
                                tStatus,
                                null,
                                isNeedSign
                        );
                        result.add(model);
                    }
                    loadDataToTables(result, getTableActionEvent());
                }
            } else {
                LinkedList<DataTable> tmpData = new LinkedList<>();
                int index = 0;
                for (DataTable dataTable : result) {
                    CentralModuleModel model = (CentralModuleModel) dataTable;
                    if (isContainsData(search, model.getModuleid())
                            || isContainsData(search, model.getModuleName())
                            || isContainsData(search, model.getId())) {
                        index++;
                        model.setNumber(index);
                        tmpData.add(dataTable);
                    }
                }
                loadDataToTables(tmpData, getTableActionEvent());
            }
        } catch (Exception e) {
            Common.showErrorMessage(MessageType.SYSTEM_ERROR, this);
            Common.errorLog("[Exception] Failed load data jabatan", e);
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
                CentralModuleModel model = (CentralModuleModel) viewTableData.getTableModel().getData().get(row);
                int opt = Common.showConfirmMessage("Rubah data user " + model.getModuleName() + "?", viewTableData);

                if (opt == 0) {
                    editData(model);
                }
            }

            @Override
            public void onDelete(int row) {
                CentralModuleModel model = (CentralModuleModel) viewTableData.getTableModel().getData().get(row);
                int opt = Common.showConfirmMessage("Hapus data modul " + model.getModuleName() + "?", viewTableData);

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
    public void delete(CentralModuleModel model) {
        try {
            DeleteModule req = new DeleteModule();
            req.setID(model.getId());
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

    @Override
    public void simpan() {
        try {
            String moduleid = txt_modul_id.getText();
            String modulename = txt_nama_modul.getText();
            String id = isEditStage ? txt_id.getText() : "";

            if (isEditStage) {
                UpdateModule req = new UpdateModule();
                req.setModulID(moduleid);
                req.setModulName(modulename);
                req.setNeedSign(cek_isneed_sign.isSelected());
                req.setId(id);
                req.setStatus(cek_aktif.isSelected() ? "1" : "0");

                JSONObject data = sendRequetToCentral(req, "POST");
                if (data != null) {
                    loadData();
                    Common.showInfoMessage("Data berhasil dirubah", this);
                    resetForm();
                }
            } else {
                AddModule req = new AddModule();
                req.setModulID(moduleid);
                req.setModulName(modulename);
                req.setNeedSign(cek_isneed_sign.isSelected());

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

    @Override
    public void editData(CentralModuleModel model) {
        txt_id.setText(model.getId());
        txt_modul_id.setText(model.getModuleid());
        txt_nama_modul.setText(model.getModuleName());
        isEditStage = true;
        cek_aktif.setVisible(true);
        cek_aktif.setSelected(model.getStatus().equalsIgnoreCase("aktif"));
        cek_isneed_sign.setSelected(model.isIsNeedSignin());
        txt_modul_id.requestFocus();
    }

    @Override
    public String getDataName() {
        return "Data Module";
    }

}
