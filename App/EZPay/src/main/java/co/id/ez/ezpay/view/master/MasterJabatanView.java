/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.view.master;

import co.id.ez.ezpay.abstracts.AbstractViewLaporan;
import co.id.ez.ezpay.app.Common;
import co.id.ez.ezpay.app.ModulManager;
import co.id.ez.ezpay.controller.MasterDataContoller;
import co.id.ez.ezpay.enums.MessageType;
import co.id.ez.ezpay.enums.util.Icons;
import co.id.ez.ezpay.interfaces.DataTable;
import co.id.ez.ezpay.model.data.master.AppModulModel;
import co.id.ez.ezpay.model.data.master.JabatanModel;
import co.id.ez.ezpay.model.table.DataTableModel;
import co.id.ez.ezpay.util.swings.table.TableActionEvent;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 *
 * @author RCS
 */
public class MasterJabatanView extends AbstractViewLaporan {

    private final DataTableModel<DataTable> modulModel;
    private final DataTableModel<DataTable> subModuleModel;
    private final MasterDataContoller controller = new MasterDataContoller();
    private boolean isEditStage;

    /**
     * Creates new form SyncronizeView
     */
    public MasterJabatanView() {
        super(Master.POSITION.getTableHeader(), true, false);
        modulModel = new DataTableModel(Master.DATA_MODULE.getTableHeader());
        subModuleModel = new DataTableModel(Master.DATA_SUB_MODULE.getTableHeader());
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
        panelData = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        lbl_x = new javax.swing.JLabel();
        lbl_x1 = new javax.swing.JLabel();
        btn_simpan = new javax.swing.JButton();
        txt_id = new javax.swing.JTextField();
        txt_jabatan = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        table_submodul = new javax.swing.JTable();
        cek_all_submodul = new javax.swing.JCheckBox();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        table_modul = new javax.swing.JTable();
        cek_all_modul = new javax.swing.JCheckBox();
        btn_batal = new javax.swing.JButton();

        setLayout(new java.awt.CardLayout());

        jPanel1.setLayout(new java.awt.BorderLayout());

        panelData.setLayout(new java.awt.CardLayout());
        jPanel1.add(panelData, java.awt.BorderLayout.CENTER);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 13), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanel2.setPreferredSize(new java.awt.Dimension(680, 90));

        lbl_x.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lbl_x.setForeground(new java.awt.Color(255, 255, 255));
        lbl_x.setText("ID");

        lbl_x1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lbl_x1.setForeground(new java.awt.Color(255, 255, 255));
        lbl_x1.setText("Jabatan");

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

        txt_jabatan.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txt_jabatan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_jabatanActionPerformed(evt);
            }
        });

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Hak Akses", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 13), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanel4.setLayout(new java.awt.BorderLayout(2, 0));

        jPanel5.setLayout(new java.awt.BorderLayout());

        table_submodul.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        table_submodul.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        table_submodul.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_NEXT_COLUMN);
        table_submodul.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        table_submodul.setRowHeight(40);
        table_submodul.setShowGrid(false);
        table_submodul.setShowHorizontalLines(true);
        jScrollPane3.setViewportView(table_submodul);

        jPanel5.add(jScrollPane3, java.awt.BorderLayout.CENTER);

        cek_all_submodul.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        cek_all_submodul.setForeground(new java.awt.Color(255, 255, 255));
        cek_all_submodul.setText("Pilih Semua");
        cek_all_submodul.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cek_all_submodulItemStateChanged(evt);
            }
        });
        jPanel5.add(cek_all_submodul, java.awt.BorderLayout.PAGE_START);

        jPanel4.add(jPanel5, java.awt.BorderLayout.CENTER);

        jPanel6.setLayout(new java.awt.BorderLayout());

        jScrollPane4.setPreferredSize(new java.awt.Dimension(250, 402));

        table_modul.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        table_modul.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_NEXT_COLUMN);
        table_modul.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        table_modul.setRowHeight(40);
        table_modul.setShowGrid(false);
        table_modul.setShowHorizontalLines(true);
        table_modul.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_modulMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(table_modul);

        jPanel6.add(jScrollPane4, java.awt.BorderLayout.LINE_START);

        cek_all_modul.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        cek_all_modul.setForeground(new java.awt.Color(255, 255, 255));
        cek_all_modul.setText("Pilih Semua");
        cek_all_modul.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cek_all_modulItemStateChanged(evt);
            }
        });
        jPanel6.add(cek_all_modul, java.awt.BorderLayout.PAGE_START);

        jPanel4.add(jPanel6, java.awt.BorderLayout.LINE_START);

        btn_batal.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        btn_batal.setText("Batal");
        btn_batal.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_batal.setPreferredSize(new java.awt.Dimension(100, 25));
        btn_batal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_batalActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(lbl_x1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_jabatan, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(lbl_x, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_id)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_simpan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_batal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 678, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_x, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_id, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_x1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_jabatan, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_simpan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_batal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel2, java.awt.BorderLayout.LINE_START);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 850, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 570, Short.MAX_VALUE)
                .addContainerGap())
        );

        add(jPanel3, "card2");
    }// </editor-fold>//GEN-END:initComponents

    private void btn_simpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_simpanActionPerformed
        // TODO add your handling code here:
        int opt = Common.showConfirmMessage("Simpan data?", this);
        if (opt == 0) {
            if (!txt_jabatan.getText().equals("")) {
                simpan();
            }
        }
    }//GEN-LAST:event_btn_simpanActionPerformed

    private void btn_batalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_batalActionPerformed
        // TODO add your handling code here:
        resetForm();
        loadData();
    }//GEN-LAST:event_btn_batalActionPerformed

    private void table_modulMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_modulMouseClicked
        // TODO add your handling code here:
        cek_all_submodul.setSelected(false);
        int index = table_modul.getSelectedRow();
        AppModulModel model = (AppModulModel) modulModel.getData().get(index);
        if (model.isCheck()) {
            loadDataSubModule(model);
        } else {
            subModuleModel.clearAllItem(table_submodul);
        }
    }//GEN-LAST:event_table_modulMouseClicked

    private void cek_all_submodulItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cek_all_submodulItemStateChanged
        // TODO add your handling code here:
        int index = table_modul.getSelectedRow();
        if (cek_all_submodul.isSelected()) {
            ModulManager.instance().getListModul().get(index).grantAllSubModule();
        } else {
            ModulManager.instance().getListModul().get(index).resetSubModule();
        }
        loadDataSubModule(ModulManager.instance().getListModul().get(index));
    }//GEN-LAST:event_cek_all_submodulItemStateChanged

    private void txt_jabatanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_jabatanActionPerformed
        // TODO add your handling code here:
        btn_simpanActionPerformed(evt);
    }//GEN-LAST:event_txt_jabatanActionPerformed

    private void cek_all_modulItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cek_all_modulItemStateChanged
        // TODO add your handling code here:
        if (cek_all_modul.isSelected()) {
            ModulManager.instance().grantAllAccess();
        } else {
            ModulManager.instance().reset();
            ModulManager.instance().resetSubmModule();
        }
        loadDataModule();
    }//GEN-LAST:event_cek_all_modulItemStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_batal;
    private javax.swing.JButton btn_simpan;
    private javax.swing.JCheckBox cek_all_modul;
    private javax.swing.JCheckBox cek_all_submodul;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel lbl_x;
    private javax.swing.JLabel lbl_x1;
    private javax.swing.JPanel panelData;
    private javax.swing.JTable table_modul;
    private javax.swing.JTable table_submodul;
    private javax.swing.JTextField txt_id;
    private javax.swing.JTextField txt_jabatan;
    // End of variables declaration//GEN-END:variables

    @Override
    public final void initForm() {
        Common.setOpaqueComponent(false, this, jPanel2, panelData, jPanel1,
                panelData, jPanel1, jPanel3, jPanel4, jScrollPane4,
                jScrollPane3, jPanel5, jPanel6, cek_all_modul, cek_all_submodul);
        jScrollPane4.getViewport().setOpaque(false);
        jScrollPane3.getViewport().setOpaque(false);
        btn_simpan.setIcon(Icons.SMALL_SAVE.get());
        btn_batal.setIcon(Icons.SMALL_CANCEL.get());
        loadViewTable(panelData);
        viewTableData.hideHeaderSeparator();
        loadData();
        resetForm();
    }

    @Override
    public void resetForm() {
        txt_id.setText("Generated by system");
        txt_jabatan.setText("");
        cek_all_modul.setSelected(false);
        cek_all_submodul.setSelected(false);
        ModulManager.instance().reset();
        ModulManager.instance().resetSubmModule();
        loadDataModule();
        isEditStage = false;
        txt_jabatan.requestFocus();

    }

    @Override
    public void loadData(String search) {
        try {
            viewTableData.clearTable();
            LinkedList<DataTable> data = controller.loadDataJabatan(search);

            if (data != null && data.size() > 0) {
                loadDataToTables(data, getTableActionEvent());
            }
        } catch (SQLException e) {
            Common.showErrorMessage(MessageType.SYSTEM_ERROR, this);
            Common.errorLog("[SQLException] Failed load data jabatan", e);
        }
    }

    @Override
    public void loadData() {
        loadData("");
    }

    private void loadDataModule() {

        modulModel.clearAllItem(table_modul);
        subModuleModel.clearAllItem(table_submodul);

        LinkedList<DataTable> dataModul = new LinkedList<>();

        ModulManager.instance().getListModul().forEach(modul -> {
            dataModul.add(modul);
        });

        modulModel.initDataToTable(table_modul, dataModul);
    }

    private void loadDataSubModule(AppModulModel selectedModul) {
        subModuleModel.clearAllItem(table_submodul);

        LinkedList<DataTable> dataSubModul = new LinkedList<>();

        selectedModul.getSubModulList().forEach(modul -> {
            dataSubModul.add(modul);
        });

        subModuleModel.initDataToTable(table_submodul, dataSubModul);
    }

    private TableActionEvent getTableActionEvent() {
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
                JabatanModel model = (JabatanModel) viewTableData.getTableModel().getData().get(row);
                if (!model.getAccess().equals("*/*")) {
                    int opt = Common.showConfirmMessage("Rubah data jabatan " + model.getJabatan() + "?", viewTableData);

                    if (opt == 0) {
                        editData(model);
                    }
                } else {
                    Common.showWarningMessage("Data tidak bisa dirubah!", viewTableData);
                }
            }

            @Override
            public void onDelete(int row) {
                JabatanModel model = (JabatanModel) viewTableData.getTableModel().getData().get(row);
                if (!model.getAccess().equals("*/*")) {
                    int opt = Common.showConfirmMessage("Hapus data jabatan " + model.getJabatan() + "?", viewTableData);

                    if (opt == 0) {
                        delete(model);
                    }
                } else {
                    Common.showWarningMessage("Data tidak bisa dihapus!", viewTableData);
                }
            }

            @Override
            public boolean canDelete() {
                return true;
            }
        };

        return event;
    }

    private void delete(JabatanModel model) {
        try {
            controller.deleteDataJabatan(model.getId());
            loadData();
            Common.showInfoMessage("Data berhasil dihapus", this);
            resetForm();
        } catch (SQLException e) {
            Common.showErrorMessage(MessageType.SYSTEM_ERROR, this);
            Common.errorLog("[SQLException] Failed delete data jabatan", e);
        }
    }

    private void simpan() {
        try {
            String jabatan = txt_jabatan.getText();
            String access = ModulManager.instance().getAccessString();
            if (isEditStage) {
                String id = txt_id.getText();
                controller.updateDataJabatan(id, jabatan, access);
            } else {
                controller.insertDataJabatan(jabatan, access);
            }
            loadData();
            Common.showInfoMessage("Data berhasil disimpan", this);
            resetForm();
        } catch (SQLException e) {
            Common.showErrorMessage(MessageType.SYSTEM_ERROR, this);
            Common.errorLog("[SQLException] Failed insert data jabatan", e);
        }
    }

    private void editData(JabatanModel model) {
        txt_id.setText(model.getId());
        txt_jabatan.setText(model.getJabatan());
        isEditStage = true;
        txt_jabatan.requestFocus();
        ModulManager.instance().parseAccess(model.getAccess());
        loadDataModule();
    }
}