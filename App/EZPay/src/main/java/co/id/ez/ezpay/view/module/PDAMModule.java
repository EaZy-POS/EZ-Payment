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
import co.id.ez.ezpay.enums.util.Aligment;
import co.id.ez.ezpay.enums.util.Icons;
import co.id.ez.ezpay.enums.MessageType;
import co.id.ez.ezpay.model.data.detail.DetailDataFactory;
import co.id.ez.ezpay.model.data.detail.Field;
import co.id.ez.ezpay.model.data.search.PDAMModel;
import co.id.ez.ezpay.msg.pam.PDAMInquiry;
import co.id.ez.ezpay.msg.pam.PDAMPayment;
import co.id.ez.system.core.rc.RC;
import com.json.JSONArray;
import com.json.JSONException;
import com.json.JSONObject;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 *
 * @author Lutfi
 */
public class PDAMModule extends AbstractModule {

    private final LinkedList<PDAMModel> tagihan = new LinkedList<>();
    private JSONObject billerData = new JSONObject();
    
    /**
     * Creates new form PLNPrepaidModule
     */
    public PDAMModule() {
        initComponents();
        initForm();
        initListener();
    }

    @Override
    public final void initForm() {
        Common.setOpaqueComponent(false,jPanel1, jPanel2, jPanel3, jPanel4, panel_bayar, panel_tagihan_1);
        btn_cek.setIcon(Icons.LARGE_DONE.get());
        btn_batal.setIcon(Icons.LARGE_CANCEL.get());
        btn_bayar.setIcon(Icons.LARGE_PAY.get());
        btn_refresh.setIcon(Icons.SMALL_RELOAD.get());
        resetForm();
        loadData();
    }
    
    private void showDetail() {
        try {
            JSONObject tPayload = billerResponse.getPayload();
            String tTotalPeriode = tPayload.getString("totalperiod");
            String tNama = tPayload.getString("name");
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
            for (PDAMModel pDAMModel : tagihan) {
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
                BigDecimal billadm = pDAMModel.getBilladm();
                BigDecimal instalment = pDAMModel.getInstallment();
                BigDecimal danameter = pDAMModel.getDanameter();
                BigDecimal vat = pDAMModel.getVat();
                BigDecimal waste = pDAMModel.getWaste();
                BigDecimal materai = pDAMModel.getMaterai();
                String tBulan = pDAMModel.getBulan();
                int meterawal = pDAMModel.getMeter_awal();
                int meterakhir = pDAMModel.getMeter_akhir();
                int totalMeter = pDAMModel.getTotal_meter();
                
                dataList.create(
                        Field.bind("Periode ", 15, Aligment.LEFT, " "),
                        Field.bind(": ", 2),
                        Field.bind(tBulan, -1, Aligment.LEFT, " ")
                );
                
                dataList.create(
                        Field.bind("Meter Awal ", 15, Aligment.LEFT, " "),
                        Field.bind(": ", 2),
                        Field.bind(String.valueOf(meterawal), -1, Aligment.LEFT, " ")
                );
                
                dataList.create(
                        Field.bind("Meter Akhir ", 15, Aligment.LEFT, " "),
                        Field.bind(": ", 2),
                        Field.bind(String.valueOf(meterakhir), -1, Aligment.LEFT, " ")
                );
                
                dataList.create(
                        Field.bind("Total Meter ", 15, Aligment.LEFT, " "),
                        Field.bind(": ", 2),
                        Field.bind(String.valueOf(totalMeter), -1, Aligment.LEFT, " ")
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
                        Field.bind("Angsuran ", 15, Aligment.LEFT, " "),
                        Field.bind(": ", 2),
                        Field.bind(Common.formatRupiah(instalment.doubleValue()), -1, Aligment.LEFT, " ")
                );
                
                dataList.create(
                        Field.bind("Dana Meter ", 15, Aligment.LEFT, " "),
                        Field.bind(": ", 2),
                        Field.bind(Common.formatRupiah(danameter.doubleValue()), -1, Aligment.LEFT, " ")
                );
                
                dataList.create(
                        Field.bind("Retribusi ", 15, Aligment.LEFT, " "),
                        Field.bind(": ", 2),
                        Field.bind(Common.formatRupiah(vat.doubleValue()), -1, Aligment.LEFT, " ")
                );
                
                dataList.create(
                        Field.bind("Materai ", 15, Aligment.LEFT, " "),
                        Field.bind(": ", 2),
                        Field.bind(Common.formatRupiah(materai.doubleValue()), -1, Aligment.LEFT, " ")
                );
                
                dataList.create(
                        Field.bind("Lain-lain ", 15, Aligment.LEFT, " "),
                        Field.bind(": ", 2),
                        Field.bind(Common.formatRupiah(waste.doubleValue()), -1, Aligment.LEFT, " ")
                );
                
                dataList.create(
                        Field.bind("Admin ", 15, Aligment.LEFT, " "),
                        Field.bind(": ", 2),
                        Field.bind(Common.formatRupiah(adm.doubleValue()), -1, Aligment.LEFT, " ")
                );
                
                dataList.create(
                        Field.bind("Admin Charge ", 15, Aligment.LEFT, " "),
                        Field.bind(": ", 2),
                        Field.bind(Common.formatRupiah(billadm.doubleValue()), -1, Aligment.LEFT, " ")
                );

                index++;
            }
            
            Common.showDetailForm(dataList, this, "Detail Tagihan PDAM");
        } catch (JSONException e) {
            Common.showErrorMessage(MessageType.SYSTEM_ERROR, this);
            Common.errorLog("[JSONException] failed load detail data", e);
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txt_idpel = new javax.swing.JTextField();
        btn_refresh = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        btn_cek = new javax.swing.JButton();
        cmb_biller = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        cmb_distric = new javax.swing.JComboBox<>();
        panel_bayar = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        txt_nama = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        panel_tagihan_1 = new javax.swing.JPanel();
        txt_total_periode = new javax.swing.JTextField();
        txt_total_meter = new javax.swing.JTextField();
        txt_tagihan = new javax.swing.JTextField();
        txt_denda = new javax.swing.JTextField();
        txt_adm_pdam = new javax.swing.JTextField();
        txt_biaya_lain = new javax.swing.JTextField();
        txt_admin_charge = new javax.swing.JTextField();
        txt_total_tagihan = new javax.swing.JTextField();
        txt_periode = new javax.swing.JTextField();
        btn_detail = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel27 = new javax.swing.JLabel();
        btn_bayar = new javax.swing.JButton();
        btn_batal = new javax.swing.JButton();

        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });
        setLayout(new java.awt.CardLayout());

        jPanel4.setLayout(new java.awt.BorderLayout());

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Input", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 14), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanel1.setPreferredSize(new java.awt.Dimension(549, 115));

        jLabel4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Biller");

        txt_idpel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_idpelActionPerformed(evt);
            }
        });

        btn_refresh.setText("Refresh");
        btn_refresh.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_refresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_refreshActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("ID Pelanggan");

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

        cmb_biller.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        jLabel5.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Wilayah");

        cmb_distric.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        cmb_distric.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmb_districItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_idpel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_cek, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmb_distric, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cmb_biller, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_refresh, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmb_distric, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_refresh, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmb_biller, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_cek, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txt_idpel, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29))
        );

        jPanel4.add(jPanel1, java.awt.BorderLayout.PAGE_START);

        panel_bayar.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Detail Tagihan", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 14), new java.awt.Color(255, 255, 255))); // NOI18N

        jLabel9.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Nama");

        txt_nama.setEditable(false);

        jPanel3.setForeground(new java.awt.Color(255, 255, 255));

        jLabel10.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Periode          ");

        jLabel11.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Total Penggunaan");

        jLabel16.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Tagihan");

        jLabel17.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("Denda");

        jLabel18.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("Adm PDAM");

        jLabel19.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("Biaya Lain-lain");

        jLabel20.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("Admin Charge");

        jLabel21.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("Total Tagihan");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel20, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel21, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        txt_total_periode.setEditable(false);

        txt_total_meter.setEditable(false);

        txt_tagihan.setEditable(false);
        txt_tagihan.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txt_tagihan.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        txt_denda.setEditable(false);
        txt_denda.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txt_denda.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        txt_adm_pdam.setEditable(false);
        txt_adm_pdam.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txt_adm_pdam.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        txt_biaya_lain.setEditable(false);
        txt_biaya_lain.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txt_biaya_lain.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        txt_admin_charge.setEditable(false);
        txt_admin_charge.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txt_admin_charge.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        txt_total_tagihan.setEditable(false);
        txt_total_tagihan.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        txt_total_tagihan.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        txt_periode.setEditable(false);

        btn_detail.setText("Detail");
        btn_detail.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_detail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_detailActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel_tagihan_1Layout = new javax.swing.GroupLayout(panel_tagihan_1);
        panel_tagihan_1.setLayout(panel_tagihan_1Layout);
        panel_tagihan_1Layout.setHorizontalGroup(
            panel_tagihan_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txt_total_meter)
            .addGroup(panel_tagihan_1Layout.createSequentialGroup()
                .addComponent(txt_total_periode, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_periode, javax.swing.GroupLayout.DEFAULT_SIZE, 314, Short.MAX_VALUE))
            .addGroup(panel_tagihan_1Layout.createSequentialGroup()
                .addGroup(panel_tagihan_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(txt_total_tagihan, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_admin_charge, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_biaya_lain, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_adm_pdam, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_denda, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_tagihan, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_detail)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        panel_tagihan_1Layout.setVerticalGroup(
            panel_tagihan_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_tagihan_1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_tagihan_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_total_periode, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_periode, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_total_meter, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_tagihan, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_denda, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_adm_pdam, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_biaya_lain, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_admin_charge, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_tagihan_1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_total_tagihan, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_detail, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jSeparator2.setForeground(new java.awt.Color(255, 255, 255));
        jSeparator2.setPreferredSize(new java.awt.Dimension(0, 1));

        jLabel27.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

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

        javax.swing.GroupLayout panel_bayarLayout = new javax.swing.GroupLayout(panel_bayar);
        panel_bayar.setLayout(panel_bayarLayout);
        panel_bayarLayout.setHorizontalGroup(
            panel_bayarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(panel_bayarLayout.createSequentialGroup()
                .addGroup(panel_bayarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel_bayarLayout.createSequentialGroup()
                        .addGroup(panel_bayarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel_bayarLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panel_bayarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(panel_tagihan_1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txt_nama)))
                    .addGroup(panel_bayarLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_bayar, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_batal, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel_bayarLayout.setVerticalGroup(
            panel_bayarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_bayarLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel_bayarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_nama))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_bayarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panel_tagihan_1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel_bayarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_bayar, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
                    .addComponent(btn_batal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel4.add(panel_bayar, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 598, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        add(jPanel2, "card2");
    }// </editor-fold>//GEN-END:initComponents

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        // TODO add your handling code here:
    }//GEN-LAST:event_formComponentShown

    private void btn_cekActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cekActionPerformed
        // TODO add your handling code here:
        int opt = Common.showConfirmMessage("Cek pembayaran PDAM ?", this);
        if (opt == 0) {
            if (validateForm()) {
                performInquiry();
            } else {
                Common.showWarningMessage("Mohon pilih denom terlebih dahulu!", this);
            }
        }
    }//GEN-LAST:event_btn_cekActionPerformed

    private void btn_cekMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_cekMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_cekMouseClicked

    private void btn_bayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_bayarActionPerformed
        // TODO add your handling code here:
        int opt = Common.showConfirmMessage(
                "Bayar tagihan PDAM "+txt_idpel.getText()+" - "+txt_total_tagihan.getText()+" ?", this);
        if (opt == 0) {
            performPayment();
        }
    }//GEN-LAST:event_btn_bayarActionPerformed

    private void btn_detailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_detailActionPerformed
        // TODO add your handling code here:
        showDetail();
    }//GEN-LAST:event_btn_detailActionPerformed

    private void btn_batalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_batalActionPerformed
        // TODO add your handling code here:
        int opt = Common.showConfirmMessage("Batalkan transaksi pembayaran PDAM ?", this);
        if (opt == 0) {
            resetForm();
        }
    }//GEN-LAST:event_btn_batalActionPerformed

    private void btn_refreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_refreshActionPerformed
        // TODO add your handling code here:
        int opt = Common.showConfirmMessage("Reload data biller ?", this);
        if (opt == 0) {
            resetForm();
            loadData();
            Common.showInfoMessage("Reload data berhasil", this);
        }
    }//GEN-LAST:event_btn_refreshActionPerformed

    private void cmb_districItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmb_districItemStateChanged
        // TODO add your handling code here:
        getBiller(cmb_distric.getSelectedItem().toString().trim());
    }//GEN-LAST:event_cmb_districItemStateChanged

    private void txt_idpelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_idpelActionPerformed
        // TODO add your handling code here:
        btn_cek.requestFocus();
    }//GEN-LAST:event_txt_idpelActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_batal;
    private javax.swing.JButton btn_bayar;
    private javax.swing.JButton btn_cek;
    private javax.swing.JButton btn_detail;
    private javax.swing.JButton btn_refresh;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cmb_biller;
    private javax.swing.JComboBox<String> cmb_distric;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JPanel panel_bayar;
    private javax.swing.JPanel panel_tagihan_1;
    private javax.swing.JTextField txt_adm_pdam;
    private javax.swing.JTextField txt_admin_charge;
    private javax.swing.JTextField txt_biaya_lain;
    private javax.swing.JTextField txt_denda;
    private javax.swing.JTextField txt_idpel;
    private javax.swing.JTextField txt_nama;
    private javax.swing.JTextField txt_periode;
    private javax.swing.JTextField txt_tagihan;
    private javax.swing.JTextField txt_total_meter;
    private javax.swing.JTextField txt_total_periode;
    private javax.swing.JTextField txt_total_tagihan;
    // End of variables declaration//GEN-END:variables

    @Override
    public final void resetForm() {
        Common.clearText(txt_adm_pdam, txt_admin_charge, txt_biaya_lain, txt_denda,
        txt_idpel, txt_nama, txt_periode, txt_tagihan, txt_total_meter, txt_total_periode, txt_total_tagihan);
        Common.activateComponent(false, btn_bayar, btn_batal, btn_detail);
        Common.activateComponent(true, btn_cek, btn_refresh, txt_idpel, cmb_biller);
        Common.setVisibility(false, panel_bayar);
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
        cmb_biller.removeAllItems();
        cmb_biller.addItem("-- Pilih Biller --");
        cmb_distric.removeAll();
        cmb_distric.addItem("-- Pilih Wilayah --");
        billerData = new JSONObject();
        try {
            LinkedList<JSONObject> data = transactionController.getPdamBillerList();
            if (data != null && data.size() > 0) {
                data.forEach(dataBiller -> {
                    String tDistric = dataBiller.get("distric").toString().trim();
                    if (billerData.has(tDistric)) {
                        billerData.getJSONArray(tDistric).put(dataBiller);
                    }else{
                        billerData.put(tDistric, new JSONArray().put(dataBiller));
                    }
                });
                
                billerData.keySet().forEach(key -> {
                    cmb_distric.addItem(key.toString());
                });
            }else{
                Common.showInfoMessage("Tidak ada data aktif, Silahkan hubungi administrator", this);
            }
        } catch (SQLException e) {
            Common.showErrorMessage("Gagal mencari data biller PDAM", this);
            Common.errorLog("[SQLException] Error on get biller PDAM", e);
        }
        cmb_biller.setSelectedIndex(0);
        cmb_distric.setSelectedIndex(0);
    }
    
    private void getBiller(String pDistric) {
        cmb_biller.removeAllItems();
        cmb_biller.addItem("-- Pilih Biller --");
        if (billerData.has(pDistric)) {
            JSONArray data = billerData.getJSONArray(pDistric);
            for (int i = 0; i < data.length(); i++) {
                JSONObject dataBiller = data.getJSONObject(i);
                String tBiller = dataBiller.get("biller").toString();
                String tBillerName = dataBiller.get("biller_name").toString();
                cmb_biller.addItem(tBiller.trim().concat(" - ").concat(tBillerName));
            }
        }
        cmb_biller.setSelectedIndex(0);
    }
    
    private void setDataInquiry(){
        JSONObject tPayload = billerResponse.getPayload();
        String tTotalPeriode = tPayload.getString("totalperiod");
        String tNama = tPayload.getString("name");
        String[] tPeriode = tPayload.getString("billperiod").split("\\|");
        
        BigDecimal tTagihan = BigDecimal.ZERO, 
                tDenda = BigDecimal.ZERO, 
                tAdmPam = BigDecimal.ZERO,
                tLainnya = BigDecimal.ZERO,
                tAdminCharge, 
                tTotal
                ; 
        String tMeterUse = "";
        
        tagihan.clear();
        for (int i = 0; i < tPeriode.length; i++) {
            BigDecimal tag = new BigDecimal(tPayload.get("tagihan_" + (i + 1)).toString());
            BigDecimal denda = new BigDecimal(tPayload.get("penalty_" + (i + 1)).toString());
            BigDecimal adm = new BigDecimal(tPayload.get("admpdam_" + (i + 1)).toString());
            BigDecimal billadm = new BigDecimal(tPayload.get("adm_" + (i + 1)).toString());
            BigDecimal instalment = new BigDecimal(tPayload.get("installment_" + (i + 1)).toString());
            BigDecimal danameter = new BigDecimal(tPayload.get("danameter_" + (i + 1)).toString());
            BigDecimal vat = new BigDecimal(tPayload.get("vat_" + (i + 1)).toString());
            BigDecimal waste = new BigDecimal(tPayload.get("waste_" + (i + 1)).toString());
            BigDecimal materai = new BigDecimal(tPayload.get("stamp_" + (i + 1)).toString());
            String tBulan = tPayload.getString("bln_" + (i+1));
            int meterawal = tPayload.getInt("mtrstart_" + (i+1));
            int meterakhir = tPayload.getInt("mtrend_" + (i+1));
            int totalMeter = tPayload.getInt("mtruse_" + (i+1));
            
            tMeterUse = tMeterUse.concat((i == 0 ? "" : ", ") + totalMeter +" m3");
            
            PDAMModel model = new PDAMModel(tBulan, tNama, tag, denda, adm, instalment, 
                    vat, danameter, waste, materai, billadm, meterawal, meterakhir, totalMeter);
            tTagihan = tTagihan.add(model.getTagihan());
            tDenda = tDenda.add(model.getDenda());
            tAdmPam = tAdmPam.add(model.getAdm());
            tLainnya = tLainnya.add(model.getOtherFee());
            tagihan.add(model);
        }
        
        tAdminCharge = new BigDecimal(tPayload.get("admincharge").toString());
        tTotal = new BigDecimal(tPayload.get("transamount").toString());
        
        txt_nama.setText(tNama);
        txt_total_periode.setText(tTotalPeriode);
        txt_periode.setText(tPayload.getString("billperiod").replace("|", ", "));
        txt_total_meter.setText(tMeterUse);
        txt_tagihan.setText(Common.formatRupiah(tTagihan.doubleValue()));
        txt_denda.setText(Common.formatRupiah(tDenda.doubleValue()));
        txt_adm_pdam.setText(Common.formatRupiah(tAdmPam.doubleValue()));
        txt_biaya_lain.setText(Common.formatRupiah(tLainnya.doubleValue()));
        txt_admin_charge.setText(Common.formatRupiah(tAdminCharge.doubleValue()));
        txt_total_tagihan.setText(Common.formatRupiah(tTotal.doubleValue()));
    }

    @Override
    public void performInquiry() {
        try {
            PDAMInquiry request = new PDAMInquiry();
            String biller = cmb_biller.getSelectedItem().toString().split(" - ")[0].trim();
            request.setBiller(biller);
            request.setIdpel(txt_idpel.getText());
            billerResponse = sendRequest(request);

            if (billerResponse != null) {
                setDataInquiry();
                saveTransaction();
                Common.setVisibility(true, panel_bayar);
                Common.activateComponent(true, btn_bayar, btn_batal, btn_detail);
                Common.activateComponent(false, btn_cek, btn_refresh, txt_idpel, cmb_biller);
                btn_bayar.requestFocus();
            } else {
                txt_idpel.requestFocus();
            }
        } catch (SQLException e) {
            Common.errorLog("[SQLException] Failed to execute Perform PDAM inquiry", e);
            Common.showErrorMessage(MessageType.SYSTEM_ERROR, this);
            resetForm();
        } catch (Exception e) {
            Common.errorLog("[Exception] Failed to execute Perform PDAM inquiry", e);
            Common.showErrorMessage(MessageType.FATAL_ERROR, this);
            resetForm();
        }
    }

    @Override
    public void performPayment() {
        try {
            prePostPayment("trx_pam_sales", billerResponse);
            PDAMPayment request = new PDAMPayment(billerResponse);
            billerResponse = sendRequest(request);

            if (billerResponse != null) {
                if (billerResponse.getResponseCode() == RC.SUCCESS) {
                    billerResponse.getPayload();
                    updateDataTransaction();
                }
            } else {
                transactionController.updateFailedTransaction("trx_pam_sales", request);
                resetForm();
            }
        } catch (SQLException e) {
            Common.errorLog("[SQLException] Failed to execute Perform PDAM payment", e);
            Common.showErrorMessage(MessageType.SYSTEM_ERROR, this);
            resetForm();
        } catch (Exception e) {
            Common.errorLog("[Exception] Failed to execute Perform PDAM payment", e);
            Common.showErrorMessage(MessageType.FATAL_ERROR, this);
            resetForm();
        }
    }

    @Override
    public boolean validateForm() {
        return txt_idpel.getText().length() > 0
                && cmb_biller.getSelectedIndex() > 0;
    }

    @Override
    public void updateDataTransaction() {
        try {
            JSONObject resp = billerResponse.getPayload();
            transactionController.updateSuccessTransactionPdam(resp);
            Common.showInfoMessage(resp.getString("rcm"), this);
            previewStruk(resp.getString("trxid"), "trx_pam_sales");
            resetForm();
        } catch (SQLException e) {
            Common.showErrorMessage(MessageType.SYSTEM_ERROR, this);
            Common.errorLog("[SQLException] Error on insert pdam transaction", e);
            resetForm();
        } catch (Exception e) {
            Common.showErrorMessage(MessageType.SYSTEM_ERROR, this);
            Common.errorLog("[Exception] Error on insert pdam transaction", e);
            resetForm();
        }
    }

    @Override
    public QueryBuilder contructQueryInsertTransaction() {
        JSONObject resp = billerResponse.getPayload();
        QueryBuilder builder = new QueryBuilder("trx_pam_sales", QueryType.INSERT);
        builder.addEntryValue("id", "UUID()", true);
        builder.addEntryValue("transaction_date", "NOW()", true);
        builder.addEntryValue("user_id", App.environment().getDEU());
        builder.addEntryValue("subscriber_id", resp.getString("idpel"));
        builder.addEntryValue("subscriber_name", Common.escape(resp.getString("name")));
        builder.addEntryValue("total_bill_amount", resp.get("transamount").toString());
        builder.addEntryValue("total_admin_charge", resp.get("admincharge").toString());
        builder.addEntryValue("bill_status", ""+tagihan.size());

        BigDecimal tBillTotal = BigDecimal.ZERO;
        int index = 0;
        for (PDAMModel pDAMModel : tagihan) {
            builder.addEntryValue("periode_" + (index + 1), pDAMModel.getBulan());
            builder.addEntryValue("bill_amount_" + (index + 1), pDAMModel.getTagihan().toPlainString());
            builder.addEntryValue("bill_stamp_" + (index + 1), pDAMModel.getMaterai().toPlainString());
            builder.addEntryValue("bill_penalty_" + (index + 1), pDAMModel.getDenda().toPlainString());
            builder.addEntryValue("bill_danameter_" + (index + 1), pDAMModel.getDanameter().toPlainString());
            builder.addEntryValue("bill_vat_" + (index + 1), pDAMModel.getVat().toPlainString());
            builder.addEntryValue("bill_waste_" + (index + 1), pDAMModel.getWaste().toPlainString());
            builder.addEntryValue("bill_installment_" + (index + 1), pDAMModel.getInstallment().toPlainString());
            builder.addEntryValue("meter_usage_" + (index + 1), String.valueOf(pDAMModel.getMeter_awal()));
            builder.addEntryValue("meter_start_" + (index + 1), String.valueOf(pDAMModel.getMeter_akhir()));
            builder.addEntryValue("meter_end_" + (index + 1), String.valueOf(pDAMModel.getTotal_meter()));
            builder.addEntryValue("bill_adm_pdam_" + (index + 1), pDAMModel.getTagihan().toPlainString());
            builder.addEntryValue("bill_adm_" + (index + 1), pDAMModel.getBilladm().toPlainString());
            tBillTotal = tBillTotal.add(pDAMModel.getTotal());
            index++;
        }

        builder.addEntryValue("total_bill", tBillTotal.toPlainString());
        builder.addEntryValue("refnumber", resp.get("refnum").toString());
        builder.addEntryValue("transaction_id", resp.get("trxid").toString());
        builder.addEntryValue("biller", resp.getString("biller"));
        builder.addEntryValue("info", Common.escape(resp.get("text").toString()));
        builder.addEntryValue("no_faktur", "");
        
        return builder;
    }
    
    @Override
    public Field[][] createBodyStruk(JSONObject pTranmainData) {
        return createBodyStrukPDAM(pTranmainData);
    }
}
