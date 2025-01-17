/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.view.master;

import co.id.ez.ezpay.abstracts.AbstractViewLaporan;
import co.id.ez.ezpay.app.App;
import co.id.ez.ezpay.app.Common;
import co.id.ez.ezpay.controller.MasterDataContoller;
import co.id.ez.ezpay.enums.MessageType;
import co.id.ez.ezpay.enums.util.Aligment;
import co.id.ez.ezpay.enums.util.CellType;
import co.id.ez.ezpay.enums.util.Icons;
import co.id.ez.ezpay.enums.util.InputType;
import co.id.ez.ezpay.interfaces.DataTable;
import co.id.ez.ezpay.model.data.master.KaryawanModel;
import co.id.ez.ezpay.model.data.master.UserModel;
import co.id.ez.ezpay.util.swings.table.TableActionEvent;
import co.id.ez.ezpay.util.swings.table.TableHeader;
import co.id.ez.system.core.etc.EncryptionService;
import java.awt.EventQueue;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 *
 * @author RCS
 */
public class MasterUsersView extends AbstractViewLaporan {

    private final MasterDataContoller controller = new MasterDataContoller();
    private boolean isEditStage;
    private LinkedList<KaryawanModel> mappingKaryawan = new LinkedList<>();

    /**
     * Creates new form SyncronizeView
     */
    public MasterUsersView() {
        super(Master.USERS.getTableHeader(),  true, false);
        initComponents();
        initForm();
        btn_changepassword.setIcon(Icons.SMALL_CHANGE_PASSWORD.get());
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
        txt_userid = new javax.swing.JTextField();
        btn_batal = new javax.swing.JButton();
        cek_aktif = new javax.swing.JCheckBox();
        lbl_x2 = new javax.swing.JLabel();
        lbl_x6 = new javax.swing.JLabel();
        cmb_karyawan = new javax.swing.JComboBox<>();
        txt_password = new javax.swing.JPasswordField();
        btn_changepassword = new co.id.ez.ezpay.util.swings.table.ActionButton();
        lbl_x3 = new javax.swing.JLabel();
        txt_repassword = new javax.swing.JPasswordField();

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
        lbl_x1.setText("User ID");

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

        txt_userid.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

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
        lbl_x2.setText("Password");

        lbl_x6.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lbl_x6.setForeground(new java.awt.Color(255, 255, 255));
        lbl_x6.setText("Karyawan");

        cmb_karyawan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        txt_password.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_passwordKeyTyped(evt);
            }
        });

        btn_changepassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_changepasswordActionPerformed(evt);
            }
        });

        lbl_x3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lbl_x3.setForeground(new java.awt.Color(255, 255, 255));
        lbl_x3.setText("Ulangi");

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
                        .addComponent(lbl_x6, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmb_karyawan, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(btn_simpan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_batal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                    .addComponent(lbl_x3, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txt_repassword))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                    .addComponent(lbl_x2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txt_password))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                    .addComponent(lbl_x1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txt_userid, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_changepassword, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbl_x6, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmb_karyawan, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_x1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_userid, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbl_x2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_password, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_changepassword, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbl_x3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_repassword, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_batal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_simpan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(362, Short.MAX_VALUE))
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

    private void txt_passwordKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_passwordKeyTyped
        // TODO add your handling code here:
        if(txt_password.getText().length() > 2){
            hideRepassword(true);
        }else{
            hideRepassword(false);
        }
    }//GEN-LAST:event_txt_passwordKeyTyped

    private void btn_changepasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_changepasswordActionPerformed
        // TODO add your handling code here:
        int opt = Common.showConfirmMessage("Rubah password?", this);
        if (opt == 0) {
            txt_userid.setEditable(false);
            txt_password.setEditable(true);
            txt_repassword.setEditable(true);
            txt_password.requestFocus();
            hideRepassword(true);
            txt_repassword.setText(txt_password.getText());
        }
    }//GEN-LAST:event_btn_changepasswordActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_batal;
    private co.id.ez.ezpay.util.swings.table.ActionButton btn_changepassword;
    private javax.swing.JButton btn_simpan;
    private javax.swing.JCheckBox cek_aktif;
    private javax.swing.JComboBox<String> cmb_karyawan;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel lbl_x;
    private javax.swing.JLabel lbl_x1;
    private javax.swing.JLabel lbl_x2;
    private javax.swing.JLabel lbl_x3;
    private javax.swing.JLabel lbl_x6;
    private javax.swing.JPanel panelData;
    private javax.swing.JTextField txt_id;
    private javax.swing.JPasswordField txt_password;
    private javax.swing.JPasswordField txt_repassword;
    private javax.swing.JTextField txt_userid;
    // End of variables declaration//GEN-END:variables

    @Override
    public boolean validateForm() {
        if (txt_userid.getText().equals("")) {
            txt_userid.requestFocus();
            Common.showWarningMessage("Mohon isi NIP dengan benar!", this);
            return false;
        }

        if (txt_userid.getText().equals("")) {
            txt_userid.requestFocus();
            Common.showWarningMessage("Mohon isi user id dengan benar!", this);
            return false;
        }

        if (txt_password.getText().equals("")) {
            txt_password.requestFocus();
            Common.showWarningMessage("Mohon isi password dengan benar!", this);
            return false;
        }

        if (txt_repassword.getText().equals("")) {
            txt_repassword.requestFocus();
            Common.showWarningMessage("Mohon isi ulangi password anda!", this);
            return false;
        }

        if (!isEditStage) {
            if (cmb_karyawan.getSelectedIndex() <= 0) {
                Common.showWarningMessage("Mohon pilih karywan!", this);
                return false;
            }
        }
        
        if(!txt_password.getText().equals(txt_repassword.getText())){
            txt_repassword.requestFocus();
            Common.showWarningMessage("Password yang anda masukan tidak sama!", this);
            return false;
        }
        
        if(!Common.validateFormatText(txt_userid, InputType.USERNAME)){
            Common.showWarningMessage("Format username tidak sesuai!\n"
                    + "Username minimum 8 digit dengan format huruf atau angka\n"
                    + "dan tidak menggunakan karakter special", this);
            txt_userid.requestFocus();
            return false;
        }
        
        if(!Common.validateFormatText(txt_password, InputType.PASSWORD)){
            Common.showWarningMessage("Format password tidak sesuai!\n"
                    + "Password minimum 8 digit dengan kombinasi \n"
                    + "huruf besar, hurud kecil, angka dan special karakter", this);
            txt_password.requestFocus();
            return false;
        }
        
        try {
            if (isEditStage) {
                if (controller
                        .isExistUsers(txt_userid.getText(), "AND id != '" + txt_id.getText() + "' ")) {
                    Common.showWarningMessage("User ID sudah digunakan!", this);
                    return false;
                }
            }else{
                if (controller
                        .isExistUsers(txt_userid.getText(), "")) {
                    Common.showWarningMessage("User ID sudah digunakan!", this);
                    return false;
                }
            }
        } catch (SQLException e) {
            Common.showErrorMessage(MessageType.SYSTEM_ERROR, this);
            return false;
        }

        return true;
    }

    @Override
    public final void initForm() {
        Common.setOpaqueComponent(false, this, jPanel2, panelData, jPanel1,
                panelData, jPanel1, jPanel3, cek_aktif);
        loadViewTable(panelData);
        btn_simpan.setIcon(Icons.SMALL_SAVE.get());
        btn_batal.setIcon(Icons.SMALL_CANCEL.get());
        viewTableData.hideHeaderSeparator();
        resetForm();
        loadData();
    }

    @Override
    public void resetForm() {
        txt_id.setText("Generated by system");
        Common.clearText(txt_password, txt_repassword, txt_userid);
        isEditStage = false;
        txt_userid.requestFocus();
        cek_aktif.setVisible(isEditStage);
        btn_changepassword.setVisible(isEditStage);
        hideRepassword(false);
        cmb_karyawan.setSelectedIndex(0);
        loadDataKaryawan();
        txt_userid.setEditable(true);
        txt_password.setEditable(true);
        txt_repassword.setEditable(true);
    }
    
    private void hideRepassword(boolean visible){
        txt_repassword.setText("");
        txt_repassword.setVisible(visible);
        lbl_x3.setVisible(visible);
    }

    public void loadDataKaryawan() {
        EventQueue.invokeLater(() -> {
            cmb_karyawan.removeAllItems();
            cmb_karyawan.addItem("-- Pilih karyawan --");
            try {
                mappingKaryawan = controller.loadDataKaryawanNotHaveUser();
                mappingKaryawan.forEach(dataTable -> {
                    cmb_karyawan.addItem(dataTable.getNama());
                });
            } catch (SQLException e) {
                Common.showErrorMessage(MessageType.SYSTEM_ERROR, this);
                Common.errorLog("[SQLException] Failed load data jabatan", e);
            }
            cmb_karyawan.setSelectedIndex(0);
            cmb_karyawan.setEnabled(true);
        });
    }

    @Override
    public void loadData(String search) {
        EventQueue.invokeLater(() -> {
            try {
                viewTableData.clearTable();
                LinkedList<DataTable> data = controller.loadDataUser(search);

                if (data != null && data.size() > 0) {
                    loadDataToTables(data, getTableActionEvent());
                }
            } catch (SQLException e) {
                Common.showErrorMessage(MessageType.SYSTEM_ERROR, this);
                Common.errorLog("[SQLException] Failed load data jabatan", e);
            }
        });
    }

    @Override
    public void loadData() {
        loadData("");
    }

    private LinkedList<TableHeader> getDataTableHeader() {
        LinkedList<TableHeader> tableHeader = new LinkedList<>();
        tableHeader.add(TableHeader.create("No", 20, Aligment.CENTER));
        tableHeader.add(TableHeader.create("NIP", 30, Aligment.LEFT));
        tableHeader.add(TableHeader.create("Nama Karyawan", 120, Aligment.LEFT));
        tableHeader.add(TableHeader.create("Jabatan", 120, Aligment.LEFT));
        tableHeader.add(TableHeader.create("User ID", 120, Aligment.LEFT));
        tableHeader.add(TableHeader.create("Status", 30, Aligment.CENTER));
        tableHeader.add(TableHeader.create("Aksi", 10, Aligment.LEFT, CellType.ACTIONPANE));

        return tableHeader;
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
                UserModel model = (UserModel) viewTableData.getTableModel().getData().get(row);
                int opt = Common.showConfirmMessage("Rubah data user " + model.getNama()+ "?", viewTableData);

                if (opt == 0) {
                    editData(model);
                }
            }

            @Override
            public void onDelete(int row) {
                UserModel model = (UserModel) viewTableData.getTableModel().getData().get(row);
                int opt = Common.showConfirmMessage("Hapus data user " + model.getNama() + "?", viewTableData);

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

    private void delete(UserModel model) {
        try {
            controller.deleteDataUser(model.getId());
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
            String userid = txt_userid.getText();
            String password = txt_password.getText();
            String empid = isEditStage ? "" : mappingKaryawan.get(cmb_karyawan.getSelectedIndex() - 1).getId();

            if (isEditStage) {
                String id = txt_id.getText();
                controller.updateDataUser(id, userid, password, (cek_aktif.isSelected() ? "1" : "0"));
            } else {
                controller.insertDataUser(userid, empid, password);
            }
            
            loadData();
            Common.showInfoMessage("Data berhasil disimpan", this);
            resetForm();
        } catch (SQLException e) {
            Common.showErrorMessage(MessageType.SYSTEM_ERROR, this);
            Common.errorLog("[SQLException] Failed insert data karyawan", e);
            resetForm();
        }
    }

    private void editData(UserModel model) {
        
        String userid = EncryptionService
                .encryptor().decrypt(
                        EncryptionService.encryptor()
                                .Base64Decrypt(model.getUserid()), 
                        App.environment().getEnv_key()
                );
        
        String password = EncryptionService
                .encryptor().decrypt(
                        EncryptionService.encryptor()
                                .Base64Decrypt(model.getPassword()), 
                        "#".concat(App.environment().getEnv_key().concat("key"))
                );
        
        txt_id.setText(model.getId());
        txt_userid.setText(userid);
        
        cmb_karyawan.removeAllItems();
        cmb_karyawan.addItem(model.getNama());
        cmb_karyawan.setSelectedItem(model.getNama());
        cmb_karyawan.setEnabled(false);
        
        txt_password.setText(password);
        txt_repassword.setText(password);
        txt_password.setEditable(false);
        txt_repassword.setEditable(false);
        
        isEditStage = true;
        cek_aktif.setVisible(true);
        btn_changepassword.setVisible(true);
        
        cek_aktif.setSelected(model.getStatus().equalsIgnoreCase("aktif"));
        txt_userid.requestFocus();
    }
}
