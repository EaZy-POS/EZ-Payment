/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.view.module;

import co.id.ez.ezpay.abstracts.AbstractViewLaporan;
import co.id.ez.ezpay.app.Common;
import co.id.ez.ezpay.controller.HistoryTransaksiController;
import co.id.ez.ezpay.enums.MessageType;
import co.id.ez.ezpay.enums.RequestType;
import co.id.ez.ezpay.util.swings.table.TableActionEvent;
import co.id.ez.ezpay.interfaces.DataTable;
import co.id.ez.ezpay.model.data.detail.Field;
import co.id.ez.ezpay.model.data.history.mp.HistoryMultiBillerModel;
import co.id.ez.ezpay.model.data.history.HistoryPDAMModel;
import co.id.ez.ezpay.model.data.history.HistoryPOSPAIDModel;
import co.id.ez.ezpay.model.data.history.HistoryPrepaidModel;
import co.id.ez.ezpay.model.data.history.HistoryVoucherModel;
import co.id.ez.ezpay.msg.BillerMessage;
import co.id.ez.ezpay.msg.mp.MPAdvice;
import co.id.ez.ezpay.msg.pam.PDAMAdvice;
import co.id.ez.ezpay.msg.pos.PospaidAdvice;
import co.id.ez.ezpay.msg.pre.PrepaidAdvice;
import co.id.ez.ezpay.msg.vcr.VoucherAdvice;
import co.id.ez.system.core.ex.ServiceException;
import co.id.ez.system.core.rc.RC;
import com.json.JSONException;
import com.json.JSONObject;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 *
 * @author Lutfi
 */
public class HistoryTransaksi extends AbstractViewLaporan {

    private HistoryTransaksiController controller = new HistoryTransaksiController();
    private RequestType module;
    private String chachedSearchString;
    private boolean isAllModule;
    private final int flag;

    /**
     * Creates new form HistoryTransaksi
     */
    public HistoryTransaksi() {
        this(null, true, false);
    }

    public HistoryTransaksi(RequestType module, boolean isSearchable, boolean isPrintable) {
        this(module, isSearchable, isPrintable, 1);
    }

    public HistoryTransaksi(RequestType module, boolean isSearchable, boolean isPrintable, int flag) {
        super(module.getTableHeader(), isSearchable, isPrintable);
        initComponents();
        this.module = module;
        isAllModule = this.module == null;
        this.flag = flag;
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

        jPanel1 = new javax.swing.JPanel();
        date_picker = new com.toedter.calendar.JDateChooser();
        jLabel3 = new javax.swing.JLabel();
        btn_cari = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        cmb_module = new javax.swing.JComboBox<>();
        checkAll = new javax.swing.JCheckBox();
        panelData = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        setLayout(new java.awt.BorderLayout(1, 0));

        jPanel1.setPreferredSize(new java.awt.Dimension(350, 45));

        date_picker.setDateFormatString("y-MM-dd");
        date_picker.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Tanggal");
        jLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);

        btn_cari.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        btn_cari.setForeground(new java.awt.Color(27, 38, 44));
        btn_cari.setText("Tampilkan");
        btn_cari.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_cari.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btn_cari.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btn_cari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cariActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Modul");

        cmb_module.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        cmb_module.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        checkAll.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        checkAll.setForeground(new java.awt.Color(255, 255, 255));
        checkAll.setText("Semua");
        checkAll.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                checkAllItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmb_module, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(date_picker, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_cari)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(checkAll)
                .addContainerGap(167, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(date_picker, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cmb_module)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btn_cari, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(checkAll)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        add(jPanel1, java.awt.BorderLayout.PAGE_START);

        panelData.setOpaque(false);
        panelData.setLayout(new java.awt.CardLayout());

        jLabel1.setText("yyyyyyyyy");
        panelData.add(jLabel1, "card2");

        add(panelData, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void btn_cariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cariActionPerformed
        // TODO add your handling code here:
        loadData();
    }//GEN-LAST:event_btn_cariActionPerformed

    private void checkAllItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_checkAllItemStateChanged
        // TODO add your handling code here:
        controller = new HistoryTransaksiController(checkAll.isSelected());
    }//GEN-LAST:event_checkAllItemStateChanged

    @Override
    public final void initForm() {
        Common.setOpaqueComponent(false, panelData, jPanel1, checkAll);
        loadViewTable(panelData);
        viewTableData.loadDataToTables(null, getTableActionEvent());
        btn_cari.setToolTipText("tampilkan");
        setModulList();
        setCurentDate(date_picker);
        resetForm();
    }

    private void setModulList() {
        cmb_module.removeAllItems();

        if (isAllModule) {
            cmb_module.addItem("-- Pilih Module --");
            for (RequestType modul : RequestType.values()) {
                if (modul != RequestType.MULTIPAYMENT) {
                    cmb_module.addItem(modul.name());
                }
            }
        } else {
            if (module == RequestType.MULTIPAYMENT) {
                cmb_module.addItem("-- Pilih Module --");
                for (RequestType modul : RequestType.values()) {
                    if (modul.getGroup() == RequestType.MULTIPAYMENT) {
                        cmb_module.addItem(modul.name());
                    }
                }
            } else {
                cmb_module.addItem(module.name());
            }
        }
        cmb_module.setSelectedIndex(0);
    }

    @Override
    public void resetForm() {
        if (isAllModule) {
            module = null;
        }
    }

    public void updateDataTransaction() {
        try {
            JSONObject resp = billerResponse.getPayload();
            switch (module) {
                case VOUCHER:
                    transactionController.updateSuccessTransactionVoucher(resp);
                    break;
                case PDAM:
                    transactionController.updateSuccessTransactionPdam(resp);
                    break;
                case PREPAID:
                    transactionController.updateSuccessTransactionPrepaid(resp, BigDecimal.ZERO);
                    break;
                case POSTPAID:
                    transactionController.updateSuccessTransactionPospaid(resp);
                    break;
                default:
                    transactionController.updateSuccessTransactionMultiPayment(resp);
                    break;
            }

            Common.showInfoMessage(resp.getString("rcm"), this);
            resetForm();
        } catch (SQLException e) {
            Common.showErrorMessage(MessageType.FATAL_ERROR, this);
            Common.errorLog("[SQLException] Error on get voucher product", e);
            resetForm();
        }
    }

    public void updateFailedTransaction(BillerMessage pRequest) {
        try {
            String pTableName;
            switch (module) {
                case VOUCHER:
                    pTableName = "trx_vcr_sales";
                    transactionController.updateFailedTransaction(pTableName, pRequest);
                    break;
                case PDAM:
                    pTableName = "trx_pam_sales";
                    transactionController.updateFailedTransaction(pTableName, pRequest);
                    break;
                case PREPAID:
                    pTableName = "trx_pre_sales";
                    transactionController.updateFailedTransaction(pTableName, pRequest);
                    break;
                case POSTPAID:
                    pTableName = "trx_pos_sales";
                    transactionController.updateFailedTransaction(pTableName, pRequest);
                    break;
                default:
                    pTableName = "trx_mp_sales";
                    transactionController.updateFailedTransaction(pTableName, pRequest);
                    break;
            }
        } catch (SQLException e) {
            Common.showErrorMessage(MessageType.FATAL_ERROR, this);
            Common.errorLog("[SQLException] Error on get voucher product", e);
            resetForm();
        }
    }

    @Override
    public void loadData(String search) {
        new Thread(() -> {
            chachedSearchString = search;
            boolean isValid = true;

            if (isAllModule) {
                if (cmb_module.getSelectedIndex() <= 0) {
                    isValid = false;
                    Common.showWarningMessage("Mohon pilih modul terlebih dahulu!", this);
                }
            }

            if (isValid) {
                try {
                    viewTableData.clearTable();
                    RequestType moduleUsed = module;

                    if (isAllModule) {
                        module = RequestType.parse(cmb_module.getSelectedItem().toString().trim());
                    } else {
                        if (module == RequestType.MULTIPAYMENT && cmb_module.getSelectedIndex() > 0) {
                            moduleUsed = RequestType.parse(cmb_module.getSelectedItem().toString().trim());
                        }
                    }

                    LinkedList<JSONObject> tranmainData
                            = controller.getHistoryData(moduleUsed, date_picker.getDate(), search, flag);
                    loadDataFromDB(tranmainData);
                } catch (SQLException e) {
                    Common.showErrorMessage(MessageType.SYSTEM_ERROR, this);
                    Common.errorLog("[SQLException] Failed load data history from tranmain", e);
                } catch (Exception e) {
                    Common.showErrorMessage(MessageType.FATAL_ERROR, this);
                    Common.errorLog("[Exception] Failed load data history from tranmain", e);
                }
            }
        }).start();

    }

    private void loadDataFromDB(LinkedList<JSONObject> pTranmainData) {
        if (pTranmainData != null && pTranmainData.size() > 0) {
            switch (module) {
                case VOUCHER:
                    loadDataVoucher(pTranmainData);
                    break;
                case PDAM:
                    loadDataPDAM(pTranmainData);
                    break;
                case PREPAID:
                    loadDataPrepaid(pTranmainData);
                    break;
                case POSTPAID:
                    loadDataPOSPAID(pTranmainData);
                    break;
                default:
                    loadDataMultiBiller(pTranmainData);
                    break;
            }
        } else {
            Common.showWarningMessage("Data tidak ditemukan", this);
        }
    }

    private void loadDataVoucher(LinkedList<JSONObject> pTranmainData) {
        LinkedList<HistoryVoucherModel> rows = new LinkedList<>();
        int index = 0;
        for (JSONObject data : pTranmainData) {
            int number = index + 1;
            String tanggal = data.get("transaction_date").toString();
            String trxid = data.get("transaction_id").toString();
            String tujuan = data.get("dest_number").toString();
            String voucher = data.get("voucher_id").toString();
            String nominal = Common.formatRupiah(data.getDouble("nominal")).replace("Rp. ", "");
            String sn = data.has("serial_number") ? data.get("serial_number").toString() : "-";
            String status = data.getInt("flag") == 1 ? "Sukses"
                    : data.getInt("flag") == 2 ? "Suspect"
                    : "Gagal";

            HistoryVoucherModel row = new HistoryVoucherModel(
                    number,
                    tanggal,
                    trxid,
                    tujuan,
                    voucher,
                    nominal,
                    sn,
                    status
            );
            rows.add(row);
            index++;
        }

        loadDataToTables(rows);
    }

    private TableActionEvent getTableActionEvent() {
        TableActionEvent event = new TableActionEvent() {
            @Override
            public void onView(int row) {
                viewDetail(row);
            }

            @Override
            public void onCheckStatus(int row) {
                cekStatus(row);
            }

            @Override
            public boolean canView() {
                return flag >= 2;
            }

            @Override
            public boolean canCheck() {
                return flag >= 2;
            }

            @Override
            public boolean canReprint() {
                return flag == 1;
            }

            @Override
            public void onReprint(int row) {
                int opt = Common.showConfirmMessage("Cetak ulang transaksi ?", panelData);
                if (opt == 0) {
                    reprintTransaction(row);
                }
            }

            @Override
            public boolean canEdit() {
                return false;
            }

            @Override
            public void onEdit(int row) {
            }
        };

        return event;
    }

    private void loadDataPrepaid(LinkedList<JSONObject> pTranmainData) {
        LinkedList<HistoryPrepaidModel> rows = new LinkedList<>();
        int index = 0;
        for (JSONObject data : pTranmainData) {
            int number = index + 1;
            String tanggal = data.get("transaction_date").toString();
            String trxid = data.get("transaction_id").toString();
            String idpel = data.get("subscriber_id").toString();
            String msn = data.get("msn").toString();
            String nama = data.get("subscriber_name").toString();
            String nominal = Common.formatRupiah(data.getDouble("nominal")).replace("Rp. ", "");
            String token = data.has("token") ? data.get("token").toString() : "-";
            String status = data.getInt("flag") == 1 ? "Sukses"
                    : data.getInt("flag") == 2 ? "Suspect"
                    : "Gagal";

            HistoryPrepaidModel row
                    = new HistoryPrepaidModel(
                            number,
                            tanggal,
                            trxid,
                            idpel,
                            msn,
                            nama,
                            nominal,
                            token,
                            status
                    );
            rows.add(row);
            index++;
        }

        loadDataToTables(rows);
    }

    private void loadDataPDAM(LinkedList<JSONObject> pTranmainData) {
        LinkedList<HistoryPDAMModel> rows = new LinkedList<>();
        int index = 0;
        for (JSONObject data : pTranmainData) {
            int number = index + 1;
            String tanggal = data.get("transaction_date").toString();
            String trxid = data.get("transaction_id").toString();
            String idpel = data.get("subscriber_id").toString();
            String nama = data.get("subscriber_name").toString();
            String tarif = data.has("subscriber_segmentation") ? data.get("subscriber_segmentation").toString() : "-";
            String tagihan = Common.formatRupiah(data.getDouble("total_bill")).replace("Rp. ", "");
            String status = data.getInt("flag") == 1 ? "Sukses"
                    : data.getInt("flag") == 2 ? "Suspect"
                    : "Gagal";

            HistoryPDAMModel row
                    = new HistoryPDAMModel(
                            number,
                            tanggal,
                            trxid,
                            idpel,
                            nama,
                            tarif,
                            tagihan,
                            status
                    );
            rows.add(row);
            index++;
        }

        loadDataToTables(rows);
    }

    private void loadDataPOSPAID(LinkedList<JSONObject> pTranmainData) {
        LinkedList<HistoryPOSPAIDModel> rows = new LinkedList<>();
        int index = 0;
        for (JSONObject data : pTranmainData) {
            int number = index + 1;
            String tanggal = data.get("transaction_date").toString();
            String trxid = data.get("transaction_id").toString();
            String idpel = data.get("subscriber_id").toString();
            String nama = data.get("subscriber_name").toString();
            String tarif = data.has("segmentation") ? data.get("segmentation").toString() : "-";
            String tagihan = Common.formatRupiah(data.getDouble("total_bill_amount")).replace("Rp. ", "");
            String status = data.getInt("flag") == 1 ? "Sukses"
                    : data.getInt("flag") == 2 ? "Suspect"
                    : "Gagal";

            HistoryPOSPAIDModel row
                    = new HistoryPOSPAIDModel(
                            number,
                            tanggal,
                            trxid,
                            idpel,
                            nama,
                            tarif,
                            tagihan,
                            status
                    );
            rows.add(row);
            index++;
        }

        loadDataToTables(rows);
    }

    private void loadDataMultiBiller(LinkedList<JSONObject> pTranmainData) {
        try {
            LinkedList<HistoryMultiBillerModel> rows = new LinkedList<>();
            int index = 0;
            for (JSONObject data : pTranmainData) {
                int number = (index + 1);
                String tanggal = data.get("transaction_date").toString();
                String trxid = data.get("transaction_id").toString();
                String idpel1 = data.has("input_id_1") ? data.get("input_id_1").toString() : "";
                String idpel2 = data.has("input_id_2") ? data.get("input_id_2").toString() : "";
                String idpel3 = data.has("input_id_3") ? data.get("input_id_3").toString() : "";
                String detail = data.getString("transaction_data");
                String biller = data.get("biller").toString();
                String tagihan = Common.formatRupiah(data.getDouble("bill_amount")).replace("Rp. ", "");
                String status = data.getInt("flag") == 1 ? "Sukses"
                        : data.getInt("flag") == 2 ? "Suspect"
                        : "Gagal";

                Class dataTable = module.getDataTableClass();

                HistoryMultiBillerModel row = (HistoryMultiBillerModel) dataTable.newInstance();

                row.create(
                        number,
                        tanggal,
                        trxid,
                        idpel1,
                        idpel2,
                        idpel3,
                        detail,
                        biller,
                        tagihan,
                        status
                );
                rows.add(row);
                index++;
            }

            loadDataToTables(rows);
        } catch (JSONException e) {
            Common.showErrorMessage("Failed load data " + module.getModule() + ". \n" + e.getMessage(), this);
            Common.errorLog("[JSONException] Failed load data " + module.getModule(), e);
        } catch (IllegalAccessException e) {
            Common.showErrorMessage("Failed load data " + module.getModule() + ". \n" + e.getMessage(), this);
            Common.errorLog("[IllegalAccessException] Failed load data " + module.getModule(), e);
        } catch (InstantiationException e) {
            Common.showErrorMessage("Failed load data " + module.getModule() + ". \n" + e.getMessage(), this);
            Common.errorLog("[InstantiationException] Failed load data " + module.getModule(), e);
        }
    }

    public <T extends DataTable> void loadDataToTables(LinkedList<T> rows) {
        viewTableData.loadDataToTables(rows, getTableActionEvent());
    }

    private void reprintTransaction(int row) {
        String trxid, tablename;
        switch (module) {
            case VOUCHER:
                HistoryVoucherModel data = (HistoryVoucherModel) viewTableData.getTableModel().getValueAt(row);
                trxid = data.getIDTransaksi();
                tablename = "trx_vcr_sales";
                break;
            case PDAM:
                HistoryPDAMModel dataPdam = (HistoryPDAMModel) viewTableData.getTableModel().getValueAt(row);
                trxid = dataPdam.getTrxid();
                tablename = "trx_pam_sales";
                break;
            case PREPAID:
                HistoryPrepaidModel dataPrepaid = (HistoryPrepaidModel) viewTableData.getTableModel().getValueAt(row);
                trxid = dataPrepaid.getTrxid();
                tablename = "trx_pre_sales";
                break;
            case POSTPAID:
                HistoryPOSPAIDModel dataPospaid = (HistoryPOSPAIDModel) viewTableData.getTableModel().getValueAt(row);
                trxid = dataPospaid.getTrxid();
                tablename = "trx_pos_sales";
                break;
            default:
                HistoryMultiBillerModel dataMP = (HistoryMultiBillerModel) viewTableData.getTableModel().getValueAt(row);
                trxid = dataMP.getTrxid();
                tablename = "trx_mp_sales";
                break;
        }

        previewStruk(trxid, tablename);
    }

    @Override
    public Field[][] createBodyStruk(JSONObject pTranmainData) {
        switch (module) {
            case VOUCHER:
                return createBodyStrukVoucher(pTranmainData);
            case PREPAID:
                return createBodyStrukPrepaid(pTranmainData);
            case PDAM:
                return createBodyStrukPDAM(pTranmainData);
            case POSTPAID:
                return createBodyStrukPospaid(pTranmainData);
            default:
                return createBodyStrukMP(pTranmainData);
        }
    }

    private void viewDetail(int row) {
        int opt = Common.showConfirmMessage("Lihat detail transaksi ?", this);
        if (opt == 0) {
            String trxid, tablename;
            switch (module) {
                case VOUCHER:
                    HistoryVoucherModel data = (HistoryVoucherModel) viewTableData.getTableModel().getValueAt(row);
                    trxid = data.getIDTransaksi();
                    tablename = "trx_vcr_sales";
                    break;
                case PDAM:
                    HistoryPDAMModel dataPdam = (HistoryPDAMModel) viewTableData.getTableModel().getValueAt(row);
                    trxid = dataPdam.getTrxid();
                    tablename = "trx_pam_sales";
                    break;
                case PREPAID:
                    HistoryPrepaidModel dataPrepaid = (HistoryPrepaidModel) viewTableData.getTableModel().getValueAt(row);
                    trxid = dataPrepaid.getTrxid();
                    tablename = "trx_pre_sales";
                    break;
                case POSTPAID:
                    HistoryPOSPAIDModel dataPospaid = (HistoryPOSPAIDModel) viewTableData.getTableModel().getValueAt(row);
                    trxid = dataPospaid.getTrxid();
                    tablename = "trx_pos_sales";
                    break;
                default:
                    HistoryMultiBillerModel dataMP = (HistoryMultiBillerModel) viewTableData.getTableModel().getValueAt(row);
                    trxid = dataMP.getTrxid();
                    tablename = "trx_mp_sales";
                    break;
            }

            previewDetail(trxid, tablename);
        }
    }

    public Field[][] createDetailData(JSONObject pTranmainData) {
        switch (module) {
            case VOUCHER:
                return createDetailDataVoucher(pTranmainData);
            case PREPAID:
                return createDetailDataPrepaid(pTranmainData);
            case PDAM:
                return createDetailDataPDAM(pTranmainData);
            case POSTPAID:
                return createDetailDataPOSPAID(pTranmainData);
            default:
                return createDetailDataMP(pTranmainData);
        }
    }

    private JSONObject getTransactionData(int row) throws SQLException {
        switch (module) {
            case VOUCHER:
                HistoryVoucherModel data = (HistoryVoucherModel) viewTableData.getTableModel().getValueAt(row);
                String trxid = data.getIDTransaksi();
                return transactionController.getTransactionData(trxid, "trx_vcr_sales");
            case PDAM:
                HistoryPDAMModel dataPdam = (HistoryPDAMModel) viewTableData.getTableModel().getValueAt(row);
                trxid = dataPdam.getTrxid();
                return transactionController.getTransactionData(trxid, "trx_pam_sales");
            case PREPAID:
                HistoryPrepaidModel dataPrepaid = (HistoryPrepaidModel) viewTableData.getTableModel().getValueAt(row);
                trxid = dataPrepaid.getTrxid();
                return transactionController.getTransactionData(trxid, "trx_pre_sales");
            case POSTPAID:
                HistoryPOSPAIDModel dataPospaid = (HistoryPOSPAIDModel) viewTableData.getTableModel().getValueAt(row);
                trxid = dataPospaid.getTrxid();
                return transactionController.getTransactionData(trxid, "trx_pos_sales");
            default:
                HistoryMultiBillerModel dataMP = (HistoryMultiBillerModel) viewTableData.getTableModel().getValueAt(row);
                trxid = dataMP.getTrxid();
                return transactionController.getTransactionData(trxid, "trx_mp_sales");
        }
    }

    private void cekStatus(int row) {
        int opt = Common.showConfirmMessage("Cek Tagihan " + module.name() + "?", this);
        if (opt == 0) {
            performAdvice(row);
        }
    }

    private void performAdvice(int row) {
        try {
            BillerMessage request = constructAdviceRequest(row);
            if (request != null) {
                billerResponse = sendRequest(request);
                if (billerResponse != null) {
                    if (billerResponse.getResponseCode() == RC.SUCCESS) {
                        updateDataTransaction();
                    } else {
                        if (billerResponse.getResponseCode() == RC.ERROR_NO_PAYMENT
                                || billerResponse.getResponseCode() == RC.ERROR_TRANSACTION_NOT_FOUND
                                || billerResponse.getResponseCode() == RC.ERROR_REVERSAL_HAD_BEEN_DONE) {
                            updateFailedTransaction(request);
                        }
                    }
                    loadData(chachedSearchString == null ? "" : chachedSearchString);
                }
            }
        } catch (SQLException e) {
            Common.showErrorMessage(MessageType.SYSTEM_ERROR, this);
            Common.errorLog("[SQLException] Failed execute perform advice", e);
        } catch (ServiceException e) {
            Common.showErrorMessage(e.getMessage(), this);
            Common.errorLog("[ServiceException] Failed execute perform advice", e);
        } catch (Exception e) {
            Common.showErrorMessage(MessageType.FATAL_ERROR, this);
            Common.errorLog("[Exception] Failed execute perform advice", e);
        }
    }

    private BillerMessage constructAdviceRequest(int row) throws SQLException {
        switch (module) {
            case VOUCHER:
                HistoryVoucherModel data = (HistoryVoucherModel) viewTableData.getTableModel().getValueAt(row);
                if (data.isSuccess()) {
                    Common.showInfoMessage("Status transaksi sukses", this);
                    break;
                }if (data.getStatus().equalsIgnoreCase("gagal")) {
                    Common.showInfoMessage("Status transaksi gagal", this);
                    break;
                } else {
                    JSONObject tranmainData = getTransactionData(row);

                    VoucherAdvice req = new VoucherAdvice();
                    req.setVoucherid(tranmainData.get("voucher_id").toString());
                    req.setTrxid(tranmainData.get("transaction_id").toString());
                    req.setTujuan(tranmainData.get("dest_number").toString());
                    req.setNominal(new BigDecimal(tranmainData.get("nominal").toString()));
                    req.setHarga(new BigDecimal(tranmainData.get("harga").toString()));
                    req.setHarga_jual(
                            new BigDecimal(
                                    tranmainData.has("harga_jual")
                                    ? tranmainData.get("harga_jual").toString()
                                    : "0"
                            )
                    );
                    return req;
                }
            case PDAM:
                HistoryPDAMModel dataPdam = (HistoryPDAMModel) viewTableData.getTableModel().getValueAt(row);
                if (dataPdam.isSuccess()) {
                    Common.showInfoMessage("Status transaksi sukses", this);
                    break;
                }if (dataPdam.getStatus().equalsIgnoreCase("gagal")) {
                    Common.showInfoMessage("Status transaksi gagal", this);
                    break;
                } else {
                    JSONObject tranmainData = getTransactionData(row);

                    PDAMAdvice req = new PDAMAdvice();
                    req.setIdpel(tranmainData.get("subscriber_id").toString());
                    req.setTrxid(tranmainData.get("transaction_id").toString());
                    req.setBiller(tranmainData.get("biller").toString());
                    req.setAmount(new BigDecimal(tranmainData.get("total_bill_amount").toString()));
                    return req;
                }
            case PREPAID:
                HistoryPrepaidModel dataPrepaid = (HistoryPrepaidModel) viewTableData.getTableModel().getValueAt(row);
                if (dataPrepaid.isSuccess()) {
                    Common.showInfoMessage("Status transaksi sukses", this);
                }if (dataPrepaid.getStatus().equalsIgnoreCase("gagal")) {
                    Common.showInfoMessage("Status transaksi gagal", this);
                    break;
                } else {
                    JSONObject tranmainData = getTransactionData(row);

                    PrepaidAdvice req = new PrepaidAdvice();
                    req.setMsn(tranmainData.get("subscriber_id").toString());
                    req.setTrxid(tranmainData.get("transaction_id").toString());
                    req.setNominal(new BigDecimal(tranmainData.get("nominal").toString()));
                    req.setHargaJual(new BigDecimal(tranmainData.get("harga_jual").toString()));
                    return req;
                }
            case POSTPAID:
                HistoryPOSPAIDModel dataPospaid = (HistoryPOSPAIDModel) viewTableData.getTableModel().getValueAt(row);
                if (dataPospaid.isSuccess()) {
                    Common.showInfoMessage("Status transaksi sukses", this);
                    break;
                }if (dataPospaid.getStatus().equalsIgnoreCase("gagal")) {
                    Common.showInfoMessage("Status transaksi gagal", this);
                    break;
                } else {
                    JSONObject tranmainData = getTransactionData(row);
                    PospaidAdvice req = new PospaidAdvice();
                    req.setIDPelanggan(tranmainData.get("subscriber_id").toString());
                    req.setTrxid(tranmainData.get("transaction_id").toString());
                    req.setAmount(new BigDecimal(tranmainData.get("total_bill_amount").toString()));
                    return req;
                }
            default:
                HistoryMultiBillerModel dataMP = (HistoryMultiBillerModel) viewTableData.getTableModel().getValueAt(row);
                if (dataMP.isSuccess()) {
                    Common.showInfoMessage("Status transaksi sukses", this);
                    break;
                }if (dataMP.getStatus().equalsIgnoreCase("gagal")) {
                    Common.showInfoMessage("Status transaksi gagal", this);
                    break;
                } else {
                    JSONObject tranmainData = getTransactionData(row);

                    MPAdvice req = new MPAdvice(tranmainData.get("biller").toString());
                    if (tranmainData.has("input_id_1")) {
                        req.setInput1(tranmainData.get("input_id_1").toString());
                    }

                    if (tranmainData.has("input_id_2")) {
                        req.setInput2(tranmainData.get("input_id_2").toString());
                    }

                    if (tranmainData.has("input_id_3")) {
                        req.setInput3(tranmainData.get("input_id_3").toString());
                    }

                    req.setTrxid(tranmainData.get("transaction_id").toString());
                    req.setAmount(new BigDecimal(tranmainData.get("total_bill_amount").toString()));
                    return req;
                }
        }

        return null;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_cari;
    private javax.swing.JCheckBox checkAll;
    private javax.swing.JComboBox<String> cmb_module;
    private com.toedter.calendar.JDateChooser date_picker;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel panelData;
    // End of variables declaration//GEN-END:variables

    @Override
    public void loadData() {
        loadData("");
    }

}
