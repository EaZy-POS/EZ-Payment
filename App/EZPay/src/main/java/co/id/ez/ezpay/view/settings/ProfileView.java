/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.view.settings;

import co.id.ez.ezpay.abstracts.AbstractView;
import co.id.ez.ezpay.app.App;
import co.id.ez.ezpay.app.Common;
import co.id.ez.ezpay.app.ProfileManager;
import co.id.ez.ezpay.app.ProfileSetting;
import co.id.ez.ezpay.enums.Headers;
import co.id.ez.ezpay.enums.util.Icons;
import co.id.ez.system.core.log.LogService;
import java.awt.Cursor;
import java.awt.Image;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author RCS
 */
public class ProfileView extends AbstractView {

    private JFileChooser fileChooser;
    private boolean isShow = false;
    
    /**
     * Creates new form SyncronizeView
     * @param isInit
     */
    public ProfileView(boolean isInit) {
        initComponents();
        initForm();
        
        if(!isInit){
            loadProfile();
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

        panelAdmin = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        btn_save = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        lbl_logo = new javax.swing.JLabel();
        lbl_toko = new javax.swing.JLabel();
        lbl_alamat = new javax.swing.JLabel();
        lbl_kota = new javax.swing.JLabel();
        lbl_kontak = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel3 = new javax.swing.JPanel();
        lbl_x = new javax.swing.JLabel();
        txt_clientid = new javax.swing.JTextField();
        lbl_x1 = new javax.swing.JLabel();
        txt_secreatkey = new javax.swing.JTextField();
        lbl_x2 = new javax.swing.JLabel();
        txt_username = new javax.swing.JTextField();
        lbl_x3 = new javax.swing.JLabel();
        txt_password = new javax.swing.JTextField();
        btn_show = new co.id.ez.ezpay.util.swings.table.ActionButton();

        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });
        setLayout(new java.awt.CardLayout());

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Profile", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 13), new java.awt.Color(255, 255, 255))); // NOI18N

        btn_save.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        btn_save.setText("....");
        btn_save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_saveActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12), new java.awt.Color(255, 255, 255))); // NOI18N

        lbl_logo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_logo, javax.swing.GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_logo, javax.swing.GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
                .addContainerGap())
        );

        lbl_toko.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        lbl_toko.setForeground(new java.awt.Color(255, 255, 255));
        lbl_toko.setText("Toko ABCD");

        lbl_alamat.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lbl_alamat.setForeground(new java.awt.Color(255, 255, 255));
        lbl_alamat.setText("Toko ABCD");

        lbl_kota.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lbl_kota.setForeground(new java.awt.Color(255, 255, 255));
        lbl_kota.setText("Toko ABCD");

        lbl_kontak.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lbl_kontak.setForeground(new java.awt.Color(255, 255, 255));
        lbl_kontak.setText("Toko ABCD");

        jSeparator1.setPreferredSize(new java.awt.Dimension(50, 2));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_save, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_toko, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbl_alamat, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
                    .addComponent(lbl_kota, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
                    .addComponent(lbl_kontak, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(lbl_toko, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl_alamat, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl_kota, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl_kontak, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(25, 25, 25))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(btn_save)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Akses API", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 13), new java.awt.Color(255, 255, 255))); // NOI18N

        lbl_x.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lbl_x.setForeground(new java.awt.Color(255, 255, 255));
        lbl_x.setText("1. CLient ID");

        txt_clientid.setEditable(false);
        txt_clientid.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        lbl_x1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lbl_x1.setForeground(new java.awt.Color(255, 255, 255));
        lbl_x1.setText("2. Secret Key");

        txt_secreatkey.setEditable(false);
        txt_secreatkey.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        lbl_x2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lbl_x2.setForeground(new java.awt.Color(255, 255, 255));
        lbl_x2.setText("3. User Name");

        txt_username.setEditable(false);
        txt_username.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        lbl_x3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lbl_x3.setForeground(new java.awt.Color(255, 255, 255));
        lbl_x3.setText("4. Password");

        txt_password.setEditable(false);
        txt_password.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        btn_show.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_showActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(lbl_x, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_clientid, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(lbl_x1, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_secreatkey, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(lbl_x2, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_username, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(lbl_x3, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_password, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_show, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lbl_x, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txt_clientid, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lbl_x1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txt_secreatkey, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lbl_x2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txt_username, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lbl_x3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txt_password, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(btn_show, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout panelAdminLayout = new javax.swing.GroupLayout(panelAdmin);
        panelAdmin.setLayout(panelAdminLayout);
        panelAdminLayout.setHorizontalGroup(
            panelAdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAdminLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelAdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelAdminLayout.setVerticalGroup(
            panelAdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAdminLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(58, Short.MAX_VALUE))
        );

        add(panelAdmin, "card3");
    }// </editor-fold>//GEN-END:initComponents

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        // TODO add your handling code here:
    }//GEN-LAST:event_formComponentShown

    private void btn_showActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_showActionPerformed
        // TODO add your handling code here:
        isShow = !isShow;
        if(!isShow){
            btn_show.setIcon(Icons.SMALL_VIEW.get());
        }else{
            btn_show.setIcon(Icons.SMALL_HIDE.get());
        }
        loadApiAccess();
    }//GEN-LAST:event_btn_showActionPerformed

    private void btn_saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_saveActionPerformed
        // TODO add your handling code here:
        fileChooser.showOpenDialog(this);
        if(fileChooser.getSelectedFile() != null){
            loadLogo(fileChooser.getSelectedFile());
        }
    }//GEN-LAST:event_btn_saveActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_save;
    private co.id.ez.ezpay.util.swings.table.ActionButton btn_show;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lbl_alamat;
    private javax.swing.JLabel lbl_kontak;
    private javax.swing.JLabel lbl_kota;
    private javax.swing.JLabel lbl_logo;
    private javax.swing.JLabel lbl_toko;
    private javax.swing.JLabel lbl_x;
    private javax.swing.JLabel lbl_x1;
    private javax.swing.JLabel lbl_x2;
    private javax.swing.JLabel lbl_x3;
    private javax.swing.JPanel panelAdmin;
    private javax.swing.JTextField txt_clientid;
    private javax.swing.JTextField txt_password;
    private javax.swing.JTextField txt_secreatkey;
    private javax.swing.JTextField txt_username;
    // End of variables declaration//GEN-END:variables

    @Override
    public final void initForm() {
        Common.setOpaqueComponent(false, this, jPanel2, panelAdmin, jPanel1, jPanel3);
        Common.clearTexts(lbl_alamat, lbl_kontak, lbl_kota, lbl_toko);
        btn_show.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn_show.setIcon(Icons.SMALL_VIEW.get());
        fileChooser = new JFileChooser();
        fileChooser.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter filter = 
                new FileNameExtensionFilter("Image File (jpg, jpeg, png)", "jpg", "jpeg", "png", "PNG");
        fileChooser.addChoosableFileFilter(filter);
        fileChooser.setDialogTitle("Pilih file logo");
    }

    @Override
    public void resetForm() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void loadProfile() {
        new Thread(() -> {
            if (ProfileManager.instance().isReady()) {
                lbl_toko.setText(ProfileManager.instance().getMitra_name());
                lbl_alamat.setText(
                        ProfileManager.instance().getAddress()
                );

                lbl_kota.setText(
                        ProfileManager.instance().getCity()
                                .concat(", ")
                                .concat(ProfileManager.instance().getProvincy())
                                .concat(", ")
                                .concat(ProfileManager.instance().getCountry())
                );

                lbl_kontak.setText(
                        "Phone: ".concat(
                                ProfileManager.instance().getWa_number()
                        ).concat(", Email: ")
                                .concat(
                                        ProfileManager.instance().getEmail()
                                )
                );
            }
            String tLogo = ProfileSetting.instance().getString("logo");
            loadLogo(new File(tLogo == null ? "env/logo.png" : tLogo));
            loadApiAccess();
        }).start();
    }
    
    private String getHiddenString(String pTarget, int index){
        String tResult = "*";
        for (int i = 0; i < pTarget.length(); i++) {
            for (int j = 0; j < index; j++) {
                tResult = tResult.concat("*");
            }
        }
        
        return tResult;
    }
    
    private void loadApiAccess() {
        HashMap<Headers, String> appEnv = App.environment().get();
        txt_clientid
                .setText(
                        isShow
                                ? appEnv.get(Headers.DOWN_MAC)
                                : getHiddenString(appEnv.get(Headers.DOWN_MAC), 1)
                );
        txt_secreatkey
                .setText(
                        isShow
                                ? App.environment().getAcc_pocky()
                                : getHiddenString(App.environment().getAcc_pocky(), 2)
                );
        
        txt_username.setText(
                isShow
                        ? App.environment().getEnu_key()
                        : getHiddenString(App.environment().getEnu_key(), 3)
        );
        
        txt_password.setText(
                isShow
                        ? App.environment().getEnp_val()
                        : getHiddenString(App.environment().getEnp_val(), 4));
    }
    
    private void loadLogo(File fileImage){
        try {
            
            ImageIcon icon ;
            boolean isCopy = false;
            
            if (fileImage.exists()) {
                icon = new ImageIcon(fileImage.getPath());
                isCopy = true;
            } else{
                icon = Icons.WIDE_NO_IMAGE.get();
            }
            
            
            if (icon.getIconHeight() > 138 || icon.getIconWidth() > 138) {
                int width = 138;
                int height = 138;
                Image scaleImage = icon.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT);
                icon = new ImageIcon(scaleImage);
            }

            String fileExt = fileImage
                    .getAbsolutePath()
                    .substring(fileImage.getAbsolutePath().lastIndexOf("."));
            String tDest = new File("env/logo".concat(fileExt)).getAbsolutePath();
            
            if (isCopy) {
                Files.copy(
                        Path.of(fileImage.getAbsolutePath()),
                        Path.of(tDest),
                        StandardCopyOption.REPLACE_EXISTING
                );
                ProfileSetting.instance().put("logo", "env/logo".concat(fileExt));
                ProfileSetting.instance().save();
            }

            lbl_logo.setIcon(icon);
        } catch (Exception e) {
            LogService.getInstance(this).error().withCause(e).log("[Exception] Failed load logo", true);
            Common.showErrorMessage("Gagal menampilkan logo!", this);
        }
    }
}