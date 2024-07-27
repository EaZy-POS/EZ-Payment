/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.view.base;

import co.id.ez.database.DBService;
import co.id.ez.ezpay.MainApps;
import co.id.ez.ezpay.abstracts.AbstractModule;
import co.id.ez.ezpay.app.App;
import co.id.ez.ezpay.app.AppSetting;
import co.id.ez.ezpay.app.Common;
import co.id.ez.ezpay.app.ConnectionManager;
import co.id.ez.ezpay.app.MigrationManager;
import co.id.ez.ezpay.app.ProfileManager;
import co.id.ez.ezpay.app.ProfileSetting;
import co.id.ez.ezpay.enums.util.Icons;
import co.id.ez.ezpay.view.Home;
import co.id.ez.system.core.config.ConfigService;
import co.id.ez.system.core.ex.ServiceException;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author RCS
 */
public class SplashScreen extends javax.swing.JFrame {

    /**
     * Creates new form SplashScrean
     */
    public SplashScreen() {
        initComponents();
        intForm();
    }

    private void intForm() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) Math.abs(screenSize.getWidth() * 0.3);
        int height = (int) Math.abs(screenSize.getHeight() * 0.1);
        this.setPreferredSize(new Dimension(300, 100));
        this.setTitle(System.getProperty("app-name") +" "+ System.getProperty("app-version"));
        ImageIcon img = Icons.ASSETS_ICONS.get();
        this.setIconImage(img.getImage());
        Common.clearTexts(tagline1, tagline2, tagline3, txt_title);
        lbl_logo.setIcon(Icons.ASSETS_UNIVERS.get());
        lbl_logo.setText(System.getProperty("app-name"));
        tagline1.setText(System.getProperty("tagline1", ""));
        tagline2.setText(System.getProperty("tagline2", ""));
        tagline3.setText(System.getProperty("tagline3", ""));
        setLocationRelativeTo(null);
    }

    private void prepareApplicationEnvironmet() {
        try {
            new Thread(() -> {
                try {
                    progressbar.setMaximum(8);
                    int i = 0;
                    changeProgress("load configuration file", i);
                    ConfigService.createInstance("conf/", "env/");
                    i++;

                    changeProgress("Load application settings", i);
                    AppSetting.instance().read();
                    i++;
                    
                    changeProgress("Load profile settings", i);
                    ProfileSetting.instance().read();
                    i++;

                    changeProgress("load database connection", i);
                    DBService.loadDBConfig();
                    i++;

                    changeProgress("Check database connection", i);
                    boolean isDBReady = AbstractModule.dbConnectionCheck();
                    i++;

                    if (isDBReady) {
                        changeProgress("Migrating database", i);
                        MigrationManager.instance().migrate();
                        i++;

                        changeProgress("Load application environment", i);
                        App.Builder.buildApp();
                        i++;

                        if (App.environment().isKnownAplication()) {
                            changeProgress("Connecting to server", i);
                            if (!ConnectionManager.instance().isAlive()) {
                                ConnectionManager.instance().start();
                            }
                            i++;

                            int loopCekConnection = 0;
                            while (!ConnectionManager.instance().isAllConnectionSuccess() && loopCekConnection < 10) {
                                loopCekConnection++;
                                Thread.sleep(500);
                            }

                            boolean isOpenApps;
                            if (!ConnectionManager.instance().isAllConnectionSuccess()) {
                                changeProgress("Cannot connected to server", i);
                                i++;
                                Common.showWarningMessage("Koneksi ke server gagal, "
                                        + "\nmohon periksa jaringan anda!", this);

                                int opt = Common.showConfirmMessage("Beberapa fitur mungkin tidak akan berjalan dengan baik"
                                        + "\nLanjutkan ke aplikasi?", this);

                                isOpenApps = (opt == 0);
                            } else {
                                isOpenApps = true;
                            }

                            if (isOpenApps) {
                                changeProgress("Load profile", i);
                                i++;

                                if (ConnectionManager.instance().isAllConnectionSuccess()) {
                                    ProfileManager.instance().loadProfile();

                                    if (!ProfileManager.instance().isReady()) {
                                        System.exit(0);
                                    }
                                }

//                                changeProgress("Checking update application", 7);
//                                UpdateManager.instance(this).cekUpdate();
                                changeProgress("Application is ready", i);
                                this.dispose();
                                openHome();
                            } else {
                                System.exit(0);
                            }
                        } else {
                            changeProgress("application not ready", 8);
                            Common.showWarningMessage("Aplikasi anda belum terdaftar!"
                                    + "\nSilahkan masukan akses API anda atau lakukan registrasi", this);
                            this.dispose();
                            openApiAccess();
                        }
                    } else {
                        this.dispose();
                        openDBConfiguration();
                    }
                } catch (ServiceException ex) {
                    Common.errorLog("[ServiceException] Failed start application", ex);
                    Common.showMessage("System Error!\n" + ex.getMessage(), JOptionPane.ERROR_MESSAGE);
                    System.exit(0);
                } catch (Exception ex) {
                    Common.errorLog("[Exception] Failed start application", ex);
                    Common.showMessage("Fatal Error!\n" + ex.getMessage(), JOptionPane.ERROR_MESSAGE);
                    System.exit(0);
                }

            }).start();
        } catch (Exception ex) {
            Common.errorLog("[Exception] Failed start application", ex);
            Common.showMessage("Fatal Error!\n" + ex.getMessage(), JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }

    public void setMaxProgressValue(int maximum) {
        progressbar.setMaximum(maximum);
    }

    public void changeProgress(String progressdesc, int value) {
        if (progressdesc.length() > 120) {
            progressdesc = progressdesc.substring(0, 120);
        }
        txt_title.setText(progressdesc.concat(" ..."));
        progressbar.setValue(value);
    }

    private void openHome() {
        MainApps.instance.setLookAndFeel(true);
        new Home().setVisible(true);
    }

    private void openApiAccess() {
        MainApps.instance.setLookAndFeel(true);
        new APIAccess().setVisible(true);
    }

    private void openDBConfiguration() {
        MainApps.instance.setLookAndFeel(true);
        new DatabaseConnection().setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        customPanel1 = new co.id.ez.ezpay.util.swings.CustomPanel();
        defaultPanel1 = new co.id.ez.ezpay.util.swings.DefaultPanel();
        progressbar = new javax.swing.JProgressBar();
        txt_title = new javax.swing.JLabel();
        lbl_logo = new javax.swing.JLabel();
        tagline1 = new javax.swing.JLabel();
        tagline2 = new javax.swing.JLabel();
        tagline3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });
        getContentPane().setLayout(new java.awt.CardLayout());

        txt_title.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txt_title.setForeground(new java.awt.Color(255, 255, 255));
        txt_title.setText("Memuat aplikasi ........ Memuat aplikasi ........ Memuat aplikasi ........ Memuat aplikasi ........  asdas");

        lbl_logo.setFont(new java.awt.Font("Matura MT Script Capitals", 0, 20)); // NOI18N
        lbl_logo.setForeground(new java.awt.Color(255, 255, 255));
        lbl_logo.setText("EZ Payment");
        lbl_logo.setPreferredSize(new java.awt.Dimension(100, 28));

        tagline1.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        tagline1.setForeground(new java.awt.Color(255, 255, 255));
        tagline1.setText("Memberi kemudahan layanan pembayaran online\\nasdasd");

        tagline2.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        tagline2.setForeground(new java.awt.Color(255, 255, 255));
        tagline2.setText("Memberi kemudahan layanan pembayaran online\\nasdasd");

        tagline3.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        tagline3.setForeground(new java.awt.Color(255, 255, 255));
        tagline3.setText("Memberi kemudahan layanan pembayaran online\\nasdasd");

        javax.swing.GroupLayout defaultPanel1Layout = new javax.swing.GroupLayout(defaultPanel1);
        defaultPanel1.setLayout(defaultPanel1Layout);
        defaultPanel1Layout.setHorizontalGroup(
            defaultPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(defaultPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(defaultPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(progressbar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txt_title, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbl_logo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tagline1, javax.swing.GroupLayout.DEFAULT_SIZE, 524, Short.MAX_VALUE)
                    .addComponent(tagline2, javax.swing.GroupLayout.DEFAULT_SIZE, 524, Short.MAX_VALUE)
                    .addComponent(tagline3, javax.swing.GroupLayout.DEFAULT_SIZE, 524, Short.MAX_VALUE))
                .addContainerGap())
        );
        defaultPanel1Layout.setVerticalGroup(
            defaultPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, defaultPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(lbl_logo, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tagline1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tagline2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                .addComponent(tagline3)
                .addGap(46, 46, 46)
                .addComponent(txt_title)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(progressbar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout customPanel1Layout = new javax.swing.GroupLayout(customPanel1);
        customPanel1.setLayout(customPanel1Layout);
        customPanel1Layout.setHorizontalGroup(
            customPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(customPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(defaultPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        customPanel1Layout.setVerticalGroup(
            customPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(customPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(defaultPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        getContentPane().add(customPanel1, "card2");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        // TODO add your handling code here:
        prepareApplicationEnvironmet();
    }//GEN-LAST:event_formComponentShown


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private co.id.ez.ezpay.util.swings.CustomPanel customPanel1;
    private co.id.ez.ezpay.util.swings.DefaultPanel defaultPanel1;
    private javax.swing.JLabel lbl_logo;
    private javax.swing.JProgressBar progressbar;
    private javax.swing.JLabel tagline1;
    private javax.swing.JLabel tagline2;
    private javax.swing.JLabel tagline3;
    private javax.swing.JLabel txt_title;
    // End of variables declaration//GEN-END:variables
}
