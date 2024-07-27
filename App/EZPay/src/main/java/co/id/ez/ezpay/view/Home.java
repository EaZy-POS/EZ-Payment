/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.view;

import co.id.ez.ezpay.view.form.ViewProdukBiller;
import co.id.ez.ezpay.view.module.PDAMModule;
import co.id.ez.ezpay.view.module.PLNPrepaidModule;
import co.id.ez.ezpay.view.form.ViewModule;
import co.id.ez.ezpay.view.module.VoucherModule;
import co.id.ez.ezpay.view.module.PLNPospaidModule;
import co.id.ez.ezpay.view.module.MPModule;
import co.id.ez.database.DBService;
import co.id.ez.ezpay.app.AppSetting;
import co.id.ez.ezpay.app.Common;
import co.id.ez.ezpay.app.SessionManager;
import co.id.ez.ezpay.enums.util.Icons;
import co.id.ez.ezpay.enums.Modul;
import co.id.ez.ezpay.util.ConnectionRunner;
import co.id.ez.ezpay.util.DateTimeRunner;
import co.id.ez.ezpay.view.form.ViewDeposit;
import co.id.ez.ezpay.view.form.ViewMasterData;
import co.id.ez.ezpay.view.form.ViewMitra;
import co.id.ez.ezpay.view.form.ViewSettings;
import co.id.ez.ezpay.view.form.ViewUser;
import co.id.ez.ezpay.view.form.ViewUserManagement;
import co.id.ez.ezpay.view.form.central.ViewCentralData;
import co.id.ez.ezpay.view.menu.central.Central;
import co.id.ez.ezpay.view.module.Dashboard;
import co.id.ez.ezpay.view.module.EmptyForm;
import java.awt.Component;
import java.awt.Toolkit;
import java.net.URL;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

/**
 *
 * @author Lutfi
 */
public class Home extends javax.swing.JFrame {

    private Modul modul;
    private ViewModule viewModule;
    private DateTimeRunner dateTime;
    private ConnectionRunner connection;

    /**
     * Creates new form Home
     */
    public Home() {
        initComponents();
        setButtonIcon();
    }

    private void setButtonIcon() {
        this.setTitle(System.getProperty("app-name") +" "+ System.getProperty("app-version"));
        URL img = Icons.ASSETS_ICONS.getURLImage();
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(img));
        dateTime = new DateTimeRunner(txt_jam);
        dateTime.start();
        connection = new ConnectionRunner(txt_status_koneksi);
        connection.start();
        icons_app.setIcon(Icons.ASSETS_UNIVERS.get());
        icons_app.setText(System.getProperty("app-name") + " ");
        lbl_user.setText("");
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        btn_voucher.setIcon(Icons.WIDE_CARD.get());
        btn_prepaid.setIcon(Icons.WIDE_ELECTS.get());
        btn_pdam.setIcon(Icons.WIDE_PUMP.get());
        btn_multi_payment.setIcon(Icons.WIDE_RECEIPT.get());
        btn_pln.setIcon(Icons.WIDE_ELECTBILL.get());
        menu_produk_biller.setIcon(Icons.SMALL_CONTACS.get());
        menu_deposit.setIcon(Icons.SMALL_DEPOSIT.get());
        menu_setting_aplikasi.setIcon(Icons.SMALL_SETTING.get());
        menu_keluar.setIcon(Icons.SMALL_CANCEL.get());
        menu_data_user.setIcon(Icons.SMALL_USER.get());
        menu_logout.setIcon(Icons.SMALL_EXIT.get());
        menu_master_data.setIcon(Icons.SMALL_MASTER_DATA.get());
        menu_profile.setIcon(Icons.SMALL_PROFILE.get());
        menu_user_management.setIcon(Icons.SMALL_USER_MANAGEMENT.get());
        menu_dashboard.setIcon(Icons.SMALL_DASHBOARD.get());
        menu_kelola_mitra.setIcon(Icons.SMALL_MITRA.get());
        menu_kelola_transaksi.setIcon(Icons.SMALL_TRANSACTION.get());
        menu_data_central.setIcon(Icons.SMALL_DATA_CENTRAL.get());
        menu_monitoring.setIcon(Icons.SMALL_MONITORING.get());
        Common.setVisibility(false, widgetpane, btn_multi_payment);
        Common.setHome(this);
    }

    private void loadModule(Component form, Modul modul) {
        SwingWorker<Void, Void> work = new SwingWorker<Void, Void>() {

            @Override
            protected Void doInBackground() throws Exception {
                loadModuleWorker(form, modul);
                return null;
            }

            @Override
            protected void done() {
                Common.hideLoadingTask();
            }
        };

        Common.showLoadingTask(work);
    }

    private void loadModuleWorker(Component form, Modul modul) {
        if (this.modul != modul) {
            if (SessionManager.instance().isAllowedAccess(modul.getAccess())) {
                panelForm.removeAll();
                panelForm.repaint();
                panelForm.revalidate();
                panelForm.add(form);
                panelForm.repaint();
                panelForm.revalidate();
                lbl_icon.setIcon(modul.getIcons().get());
                title_page.setText(modul.getTitle());
                this.modul = modul;
            } else {
                if (modul == Modul.DASHBOARD) {
                    loadEmptyForm();
                } else {
                    Toolkit.getDefaultToolkit().beep();
                    Common.showWarningMessage("Anda tidak memiliki hak akses!", this);
                }
            }
        }
    }

    private void loadEmptyForm() {
        panelForm.removeAll();
        panelForm.repaint();
        panelForm.revalidate();
        panelForm.add(new EmptyForm());
        panelForm.repaint();
        panelForm.revalidate();
        lbl_icon.setIcon(null);
        title_page.setText("");
    }

    private void prepareLoadModule(ViewModule view) {
        if (viewModule != null) {
            viewModule.closeOperation();
        }

        viewModule = view;
    }

    public void cekSession() {
        if (!SessionManager.instance().isActive()) {
            lbl_user.setText(
                    "User : -   "
            );
            new FormLogin(this, true).setVisible(true);
        } else {
            lbl_user.setText(
                    "User : "
                    + SessionManager.instance().getName()
                    + " "
            );
            loadModule(new Dashboard(), Modul.DASHBOARD);
            cekAppSetting();
        }
    }

    public void cekAppSetting() {
        try {
            AppSetting.instance().getReceiptFormat();
        } catch (Exception e) {
            Common.showWarningMessage("Pengaturan pada aplikasi belum lengkap!"
                    + "\nSilahkan lengkapi pengaturan aplikasi", this);
            menu_setting_aplikasiActionPerformed(null);
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

        jPanel6 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        widgetpane = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        txt_jam = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        txt_status_koneksi = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        lbl_user = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        lbl_icon = new javax.swing.JLabel();
        title_page = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        panelForm = new co.id.ez.ezpay.util.swings.CustomPanel();
        headerPanel1 = new co.id.ez.ezpay.util.swings.HeaderPanel();
        jToolBar1 = new javax.swing.JToolBar();
        btn_voucher = new javax.swing.JButton();
        btn_pln = new javax.swing.JButton();
        btn_prepaid = new javax.swing.JButton();
        btn_pdam = new javax.swing.JButton();
        btn_multi_payment = new javax.swing.JButton();
        icons_app = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        menu_file = new javax.swing.JMenu();
        menu_dashboard = new javax.swing.JMenuItem();
        menu_master_data = new javax.swing.JMenuItem();
        menu_produk_biller = new javax.swing.JMenuItem();
        jSeparator8 = new javax.swing.JPopupMenu.Separator();
        menu_keluar = new javax.swing.JMenuItem();
        menu_mitra = new javax.swing.JMenu();
        menu_profile = new javax.swing.JMenuItem();
        jSeparator7 = new javax.swing.JPopupMenu.Separator();
        menu_deposit = new javax.swing.JMenuItem();
        menu_user = new javax.swing.JMenu();
        menu_data_user = new javax.swing.JMenuItem();
        menu_logout = new javax.swing.JMenuItem();
        menu_pengaturan = new javax.swing.JMenu();
        menu_user_management = new javax.swing.JMenuItem();
        menu_setting_aplikasi = new javax.swing.JMenuItem();
        menu_bo = new javax.swing.JMenu();
        menu_kelola_mitra = new javax.swing.JMenuItem();
        menu_kelola_transaksi = new javax.swing.JMenuItem();
        menu_monitoring = new javax.swing.JMenuItem();
        menu_data_central = new javax.swing.JMenuItem();

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 604, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 28, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("EZ Payment");
        setLocationByPlatform(true);
        setMinimumSize(new java.awt.Dimension(1000, 820));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanel1.setLayout(new java.awt.CardLayout());

        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel4.setLayout(new java.awt.BorderLayout(1, 1));

        widgetpane.setPreferredSize(new java.awt.Dimension(0, 0));

        javax.swing.GroupLayout widgetpaneLayout = new javax.swing.GroupLayout(widgetpane);
        widgetpane.setLayout(widgetpaneLayout);
        widgetpaneLayout.setHorizontalGroup(
            widgetpaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        widgetpaneLayout.setVerticalGroup(
            widgetpaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 376, Short.MAX_VALUE)
        );

        jPanel4.add(widgetpane, java.awt.BorderLayout.LINE_END);

        jPanel8.setLayout(new java.awt.BorderLayout());

        jPanel9.setBackground(new java.awt.Color(27, 38, 44));
        jPanel9.setPreferredSize(new java.awt.Dimension(497, 45));
        jPanel9.setLayout(new java.awt.BorderLayout());

        jPanel3.setOpaque(false);
        jPanel3.setPreferredSize(new java.awt.Dimension(380, 45));
        jPanel3.setLayout(new java.awt.BorderLayout());

        txt_jam.setBackground(new java.awt.Color(255, 255, 51));
        txt_jam.setFont(new java.awt.Font("Arial Black", 0, 13)); // NOI18N
        txt_jam.setForeground(new java.awt.Color(255, 255, 51));
        txt_jam.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        txt_jam.setText("2023-01-99 10:00:00");
        txt_jam.setToolTipText("");
        txt_jam.setPreferredSize(new java.awt.Dimension(300, 16));
        jPanel3.add(txt_jam, java.awt.BorderLayout.LINE_END);

        jPanel7.setOpaque(false);
        jPanel7.setPreferredSize(new java.awt.Dimension(380, 20));
        jPanel7.setLayout(new java.awt.GridBagLayout());

        txt_status_koneksi.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txt_status_koneksi.setForeground(new java.awt.Color(255, 255, 255));
        txt_status_koneksi.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        txt_status_koneksi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ico/small/online.png"))); // NOI18N
        txt_status_koneksi.setText("Cek koneksi central...");
        txt_status_koneksi.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        txt_status_koneksi.setMaximumSize(new java.awt.Dimension(300, 20));
        txt_status_koneksi.setMinimumSize(new java.awt.Dimension(200, 20));
        txt_status_koneksi.setPreferredSize(new java.awt.Dimension(200, 17));
        jPanel7.add(txt_status_koneksi, new java.awt.GridBagConstraints());

        jLabel1.setText("     ");
        jPanel7.add(jLabel1, new java.awt.GridBagConstraints());

        lbl_user.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lbl_user.setForeground(new java.awt.Color(255, 255, 255));
        lbl_user.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbl_user.setText("User login: M Lutfi Riyanto");
        lbl_user.setPreferredSize(new java.awt.Dimension(300, 18));
        jPanel7.add(lbl_user, new java.awt.GridBagConstraints());

        jLabel3.setText("  ");
        jPanel7.add(jLabel3, new java.awt.GridBagConstraints());

        jPanel3.add(jPanel7, java.awt.BorderLayout.PAGE_END);

        jPanel9.add(jPanel3, java.awt.BorderLayout.LINE_END);

        jPanel5.setOpaque(false);
        jPanel5.setLayout(new java.awt.BorderLayout());

        lbl_icon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_icon.setPreferredSize(new java.awt.Dimension(35, 30));
        jPanel5.add(lbl_icon, java.awt.BorderLayout.LINE_START);

        title_page.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        title_page.setForeground(new java.awt.Color(255, 255, 255));
        title_page.setText("Dashboard");
        jPanel5.add(title_page, java.awt.BorderLayout.CENTER);

        jPanel9.add(jPanel5, java.awt.BorderLayout.CENTER);

        jPanel8.add(jPanel9, java.awt.BorderLayout.PAGE_START);

        jScrollPane1.setBorder(null);

        panelForm.setLayout(new java.awt.CardLayout());
        jScrollPane1.setViewportView(panelForm);

        jPanel8.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel4.add(jPanel8, java.awt.BorderLayout.CENTER);

        jPanel2.add(jPanel4, java.awt.BorderLayout.CENTER);

        headerPanel1.setPreferredSize(new java.awt.Dimension(730, 85));
        headerPanel1.setLayout(new java.awt.BorderLayout());

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);
        jToolBar1.setOpaque(false);

        btn_voucher.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        btn_voucher.setForeground(new java.awt.Color(255, 255, 255));
        btn_voucher.setText("Voucher");
        btn_voucher.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_voucher.setFocusable(false);
        btn_voucher.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_voucher.setMargin(new java.awt.Insets(3, 14, 2, 14));
        btn_voucher.setPreferredSize(new java.awt.Dimension(120, 24));
        btn_voucher.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_voucher.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_voucherActionPerformed(evt);
            }
        });
        jToolBar1.add(btn_voucher);

        btn_pln.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        btn_pln.setForeground(new java.awt.Color(255, 255, 255));
        btn_pln.setText("Tagihan Listrik");
        btn_pln.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_pln.setFocusable(false);
        btn_pln.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_pln.setMargin(new java.awt.Insets(3, 14, 2, 14));
        btn_pln.setPreferredSize(new java.awt.Dimension(120, 24));
        btn_pln.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_pln.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_plnActionPerformed(evt);
            }
        });
        jToolBar1.add(btn_pln);

        btn_prepaid.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        btn_prepaid.setForeground(new java.awt.Color(255, 255, 255));
        btn_prepaid.setText("Token Listrik");
        btn_prepaid.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_prepaid.setFocusable(false);
        btn_prepaid.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_prepaid.setMargin(new java.awt.Insets(3, 14, 2, 14));
        btn_prepaid.setPreferredSize(new java.awt.Dimension(120, 24));
        btn_prepaid.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_prepaid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_prepaidActionPerformed(evt);
            }
        });
        jToolBar1.add(btn_prepaid);

        btn_pdam.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        btn_pdam.setForeground(new java.awt.Color(255, 255, 255));
        btn_pdam.setText("PDAM");
        btn_pdam.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_pdam.setFocusable(false);
        btn_pdam.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_pdam.setMargin(new java.awt.Insets(3, 14, 2, 14));
        btn_pdam.setPreferredSize(new java.awt.Dimension(120, 24));
        btn_pdam.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_pdam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_pdamActionPerformed(evt);
            }
        });
        jToolBar1.add(btn_pdam);

        btn_multi_payment.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        btn_multi_payment.setForeground(new java.awt.Color(255, 255, 255));
        btn_multi_payment.setText("Multi Payment");
        btn_multi_payment.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_multi_payment.setFocusable(false);
        btn_multi_payment.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_multi_payment.setMargin(new java.awt.Insets(3, 14, 2, 14));
        btn_multi_payment.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_multi_payment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_multi_paymentActionPerformed(evt);
            }
        });
        jToolBar1.add(btn_multi_payment);

        headerPanel1.add(jToolBar1, java.awt.BorderLayout.CENTER);

        icons_app.setFont(new java.awt.Font("Matura MT Script Capitals", 0, 18)); // NOI18N
        icons_app.setForeground(new java.awt.Color(255, 255, 255));
        icons_app.setText("EZ Payment  ");
        headerPanel1.add(icons_app, java.awt.BorderLayout.LINE_END);

        jPanel2.add(headerPanel1, java.awt.BorderLayout.PAGE_START);

        jPanel1.add(jPanel2, "card2");

        jMenuBar1.setBackground(new java.awt.Color(27, 38, 44));
        jMenuBar1.setMinimumSize(new java.awt.Dimension(310, 28));
        jMenuBar1.setOpaque(true);
        jMenuBar1.setPreferredSize(new java.awt.Dimension(310, 35));

        menu_file.setForeground(new java.awt.Color(255, 255, 255));
        menu_file.setText("File");
        menu_file.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        menu_file.setPreferredSize(new java.awt.Dimension(46, 40));

        menu_dashboard.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        menu_dashboard.setText("Dashboard");
        menu_dashboard.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menu_dashboard.setPreferredSize(new java.awt.Dimension(200, 28));
        menu_dashboard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_dashboardActionPerformed(evt);
            }
        });
        menu_file.add(menu_dashboard);

        menu_master_data.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        menu_master_data.setText("Master data");
        menu_master_data.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menu_master_data.setPreferredSize(new java.awt.Dimension(200, 28));
        menu_master_data.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_master_dataActionPerformed(evt);
            }
        });
        menu_file.add(menu_master_data);

        menu_produk_biller.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        menu_produk_biller.setText("Produk & Biller");
        menu_produk_biller.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menu_produk_biller.setPreferredSize(new java.awt.Dimension(200, 28));
        menu_produk_biller.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_produk_billerActionPerformed(evt);
            }
        });
        menu_file.add(menu_produk_biller);
        menu_file.add(jSeparator8);

        menu_keluar.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ESCAPE, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        menu_keluar.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        menu_keluar.setText("Keluar Aplikasi");
        menu_keluar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menu_keluar.setPreferredSize(new java.awt.Dimension(200, 28));
        menu_keluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_keluarActionPerformed(evt);
            }
        });
        menu_file.add(menu_keluar);

        jMenuBar1.add(menu_file);

        menu_mitra.setForeground(new java.awt.Color(255, 255, 255));
        menu_mitra.setText("Mitra");
        menu_mitra.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        menu_profile.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        menu_profile.setText("Profile");
        menu_profile.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menu_profile.setPreferredSize(new java.awt.Dimension(200, 28));
        menu_profile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_profileActionPerformed(evt);
            }
        });
        menu_mitra.add(menu_profile);
        menu_mitra.add(jSeparator7);

        menu_deposit.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        menu_deposit.setText("Deposit");
        menu_deposit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menu_deposit.setPreferredSize(new java.awt.Dimension(200, 28));
        menu_deposit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_depositActionPerformed(evt);
            }
        });
        menu_mitra.add(menu_deposit);

        jMenuBar1.add(menu_mitra);

        menu_user.setForeground(new java.awt.Color(255, 255, 255));
        menu_user.setText("Pengguna");
        menu_user.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        menu_user.setPreferredSize(new java.awt.Dimension(80, 40));

        menu_data_user.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        menu_data_user.setText("Profile Pengguna");
        menu_data_user.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menu_data_user.setPreferredSize(new java.awt.Dimension(200, 28));
        menu_data_user.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_data_userActionPerformed(evt);
            }
        });
        menu_user.add(menu_data_user);

        menu_logout.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        menu_logout.setText("Keluar");
        menu_logout.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menu_logout.setPreferredSize(new java.awt.Dimension(200, 28));
        menu_logout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_logoutActionPerformed(evt);
            }
        });
        menu_user.add(menu_logout);

        jMenuBar1.add(menu_user);

        menu_pengaturan.setForeground(new java.awt.Color(255, 255, 255));
        menu_pengaturan.setText("Pengaturan");
        menu_pengaturan.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        menu_user_management.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        menu_user_management.setText("Manajemen Pengguna");
        menu_user_management.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menu_user_management.setPreferredSize(new java.awt.Dimension(160, 28));
        menu_user_management.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_user_managementActionPerformed(evt);
            }
        });
        menu_pengaturan.add(menu_user_management);

        menu_setting_aplikasi.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        menu_setting_aplikasi.setText("aplikasi");
        menu_setting_aplikasi.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menu_setting_aplikasi.setPreferredSize(new java.awt.Dimension(140, 28));
        menu_setting_aplikasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_setting_aplikasiActionPerformed(evt);
            }
        });
        menu_pengaturan.add(menu_setting_aplikasi);

        jMenuBar1.add(menu_pengaturan);

        menu_bo.setText("Back Office");
        menu_bo.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        menu_kelola_mitra.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        menu_kelola_mitra.setText("Kelola Mitra");
        menu_kelola_mitra.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menu_kelola_mitra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_kelola_mitraActionPerformed(evt);
            }
        });
        menu_bo.add(menu_kelola_mitra);

        menu_kelola_transaksi.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        menu_kelola_transaksi.setText("Kelola Transaksi");
        menu_kelola_transaksi.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menu_kelola_transaksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_kelola_transaksiActionPerformed(evt);
            }
        });
        menu_bo.add(menu_kelola_transaksi);

        menu_monitoring.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        menu_monitoring.setText("Monitoring");
        menu_monitoring.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menu_monitoring.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_monitoringActionPerformed(evt);
            }
        });
        menu_bo.add(menu_monitoring);

        menu_data_central.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        menu_data_central.setText("Data Central");
        menu_data_central.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menu_data_central.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_data_centralActionPerformed(evt);
            }
        });
        menu_bo.add(menu_data_central);

        jMenuBar1.add(menu_bo);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 922, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 461, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_voucherActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_voucherActionPerformed
        // TODO add your handling code here:
        ViewModule mod = new ViewModule(Modul.VOUCHER, new VoucherModule());
        prepareLoadModule(mod);
        loadModule(mod, Modul.VOUCHER);
    }//GEN-LAST:event_btn_voucherActionPerformed

    private void btn_plnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_plnActionPerformed
        ViewModule mod = new ViewModule(Modul.POSTPAID, new PLNPospaidModule());
        prepareLoadModule(mod);
        loadModule(mod, Modul.POSTPAID);
    }//GEN-LAST:event_btn_plnActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        // TODO add your handling code here:
        dateTime.stop();
        connection.stopRunner();
        DBService.stop();
    }//GEN-LAST:event_formWindowClosed

    private void btn_prepaidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_prepaidActionPerformed
        // TODO add your handling code here:
        ViewModule mod = new ViewModule(Modul.PREPAID, new PLNPrepaidModule());
        prepareLoadModule(mod);
        loadModule(mod, Modul.PREPAID);
    }//GEN-LAST:event_btn_prepaidActionPerformed

    private void btn_pdamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_pdamActionPerformed
        // TODO add your handling code here:
        ViewModule mod = new ViewModule(Modul.PDAM, new PDAMModule());
        prepareLoadModule(mod);
        loadModule(mod, Modul.PDAM);
    }//GEN-LAST:event_btn_pdamActionPerformed

    private void btn_multi_paymentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_multi_paymentActionPerformed
        // TODO add your handling code here:
        ViewModule mod = new ViewModule(Modul.MULTIPAYMENT, new MPModule());
        prepareLoadModule(mod);
        loadModule(mod, Modul.MULTIPAYMENT);
    }//GEN-LAST:event_btn_multi_paymentActionPerformed

    private void menu_depositActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_depositActionPerformed
        // TODO add your handling code here:
        loadModule(new ViewDeposit(), Modul.DEPOSIT);
    }//GEN-LAST:event_menu_depositActionPerformed

    private void menu_produk_billerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_produk_billerActionPerformed
        // TODO add your handling code here:
        loadModule(new ViewProdukBiller(), Modul.PRODUCT_AND_BILLER);
    }//GEN-LAST:event_menu_produk_billerActionPerformed

    private void menu_setting_aplikasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_setting_aplikasiActionPerformed
        // TODO add your handling code here:
        loadModule(new ViewSettings(), Modul.SETTINGS);
    }//GEN-LAST:event_menu_setting_aplikasiActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        cekSession();
    }//GEN-LAST:event_formWindowOpened

    private void menu_keluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_keluarActionPerformed
        // TODO add your handling code here:
        int opt = Common.showConfirmMessage("Anda ingin keluar dari aplikasi?", this);
        if (opt == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }//GEN-LAST:event_menu_keluarActionPerformed

    private void menu_data_userActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_data_userActionPerformed
        // TODO add your handling code here:
        loadModule(new ViewUser(), Modul.USERS);
    }//GEN-LAST:event_menu_data_userActionPerformed

    private void menu_logoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_logoutActionPerformed
        // TODO add your handling code here:
        int opt = Common.showConfirmMessage("Anda ingin keluar?", this);
        if (opt == JOptionPane.YES_OPTION) {
            panelForm.removeAll();
            SessionManager.instance().destroy();
            cekSession();
        }
    }//GEN-LAST:event_menu_logoutActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        int opt = Common.showConfirmMessage("Anda ingin keluar dari aplikasi?", this);
        if (opt == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }//GEN-LAST:event_formWindowClosing

    private void menu_master_dataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_master_dataActionPerformed
        // TODO add your handling code here:
        loadModule(new ViewMasterData(), Modul.MASTER_DATA);
    }//GEN-LAST:event_menu_master_dataActionPerformed

    private void menu_profileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_profileActionPerformed
        // TODO add your handling code here:
        loadModule(new ViewMitra(), Modul.MITRA);
    }//GEN-LAST:event_menu_profileActionPerformed

    private void menu_user_managementActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_user_managementActionPerformed
        // TODO add your handling code here:
        loadModule(new ViewUserManagement(), Modul.USER_MANAGEMENT);
    }//GEN-LAST:event_menu_user_managementActionPerformed

    private void menu_dashboardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_dashboardActionPerformed
        // TODO add your handling code here:
        loadModule(new Dashboard(), Modul.DASHBOARD);
    }//GEN-LAST:event_menu_dashboardActionPerformed

    private void menu_data_centralActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_data_centralActionPerformed
        // TODO add your handling code here:
        loadModule(new ViewCentralData(Central.CENTRAL_DATA), Modul.CENTRAL_DATA);
    }//GEN-LAST:event_menu_data_centralActionPerformed

    private void menu_kelola_mitraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_kelola_mitraActionPerformed
        // TODO add your handling code here:
        loadModule(new ViewCentralData(Central.MITRA_DATA), Modul.MITRA_DATA);
    }//GEN-LAST:event_menu_kelola_mitraActionPerformed

    private void menu_kelola_transaksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_kelola_transaksiActionPerformed
        // TODO add your handling code here:
        loadModule(new ViewCentralData(Central.TRANSACTION_DATA), Modul.TRANSACTION_DATA);
    }//GEN-LAST:event_menu_kelola_transaksiActionPerformed

    private void menu_monitoringActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_monitoringActionPerformed
        // TODO add your handling code here:
        loadModule(new ViewCentralData(Central.TRANSACTION_MONITORING), Modul.TRANSACTION_MONITORING);
    }//GEN-LAST:event_menu_monitoringActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_multi_payment;
    private javax.swing.JButton btn_pdam;
    private javax.swing.JButton btn_pln;
    private javax.swing.JButton btn_prepaid;
    private javax.swing.JButton btn_voucher;
    private co.id.ez.ezpay.util.swings.HeaderPanel headerPanel1;
    private javax.swing.JLabel icons_app;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPopupMenu.Separator jSeparator7;
    private javax.swing.JPopupMenu.Separator jSeparator8;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JLabel lbl_icon;
    private javax.swing.JLabel lbl_user;
    private javax.swing.JMenu menu_bo;
    private javax.swing.JMenuItem menu_dashboard;
    private javax.swing.JMenuItem menu_data_central;
    private javax.swing.JMenuItem menu_data_user;
    private javax.swing.JMenuItem menu_deposit;
    private javax.swing.JMenu menu_file;
    private javax.swing.JMenuItem menu_kelola_mitra;
    private javax.swing.JMenuItem menu_kelola_transaksi;
    private javax.swing.JMenuItem menu_keluar;
    private javax.swing.JMenuItem menu_logout;
    private javax.swing.JMenuItem menu_master_data;
    private javax.swing.JMenu menu_mitra;
    private javax.swing.JMenuItem menu_monitoring;
    private javax.swing.JMenu menu_pengaturan;
    private javax.swing.JMenuItem menu_produk_biller;
    private javax.swing.JMenuItem menu_profile;
    private javax.swing.JMenuItem menu_setting_aplikasi;
    private javax.swing.JMenu menu_user;
    private javax.swing.JMenuItem menu_user_management;
    private co.id.ez.ezpay.util.swings.CustomPanel panelForm;
    private javax.swing.JLabel title_page;
    private javax.swing.JLabel txt_jam;
    private javax.swing.JLabel txt_status_koneksi;
    private javax.swing.JPanel widgetpane;
    // End of variables declaration//GEN-END:variables
}
