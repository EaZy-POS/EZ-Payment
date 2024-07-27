/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.view.deposit;

import co.id.ez.ezpay.abstracts.AbstractViewLaporan;
import co.id.ez.ezpay.app.Common;
import co.id.ez.ezpay.enums.MessageType;
import co.id.ez.ezpay.interfaces.DataTable;
import co.id.ez.ezpay.model.data.deposit.MutasiModel;
import co.id.ez.ezpay.msg.BillerResponse;
import co.id.ez.ezpay.msg.data.GetMutasiSaldo;
import com.json.JSONArray;
import com.json.JSONException;
import com.json.JSONObject;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

/**
 *
 * @author RCS
 */
public class MutasiView extends AbstractViewLaporan {

    /**
     * Creates new form SyncronizeView
     */
    public MutasiView() {
        super(Deposit.MUTASI.getTableHeader(), false, false);
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
        start_date = new com.toedter.calendar.JDateChooser();
        end_date = new com.toedter.calendar.JDateChooser();
        lbl_x1 = new javax.swing.JLabel();
        btnCekMutasi = new javax.swing.JButton();

        setLayout(new java.awt.CardLayout());

        jPanel1.setLayout(new java.awt.BorderLayout());

        panelData.setLayout(new java.awt.CardLayout());
        jPanel1.add(panelData, java.awt.BorderLayout.CENTER);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Periode", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 13), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanel2.setPreferredSize(new java.awt.Dimension(550, 90));

        lbl_x.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lbl_x.setForeground(new java.awt.Color(255, 255, 255));
        lbl_x.setText("Tanggal Awal");

        start_date.setDateFormatString("yyyy-MM-dd");
        start_date.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        start_date.setPreferredSize(new java.awt.Dimension(120, 25));

        end_date.setDateFormatString("yyyy-MM-dd");
        end_date.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        end_date.setPreferredSize(new java.awt.Dimension(120, 25));

        lbl_x1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lbl_x1.setForeground(new java.awt.Color(255, 255, 255));
        lbl_x1.setText("Tanggal Akhir");

        btnCekMutasi.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        btnCekMutasi.setText("Cari");
        btnCekMutasi.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCekMutasi.setPreferredSize(new java.awt.Dimension(100, 25));
        btnCekMutasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCekMutasiActionPerformed(evt);
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
                        .addComponent(start_date, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(lbl_x1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(end_date, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCekMutasi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(397, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(start_date, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_x, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnCekMutasi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(end_date, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_x1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(11, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel2, java.awt.BorderLayout.PAGE_START);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 754, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 349, Short.MAX_VALUE)
                .addContainerGap())
        );

        add(jPanel3, "card2");
    }// </editor-fold>//GEN-END:initComponents

    private void btnCekMutasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCekMutasiActionPerformed
        // TODO add your handling code here:
        int opt = Common.showConfirmMessage("Cek mutasi saldo?", this);
        if (opt == 0) {
            loadData();
        }
    }//GEN-LAST:event_btnCekMutasiActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCekMutasi;
    private com.toedter.calendar.JDateChooser end_date;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel lbl_x;
    private javax.swing.JLabel lbl_x1;
    private javax.swing.JPanel panelData;
    private com.toedter.calendar.JDateChooser start_date;
    // End of variables declaration//GEN-END:variables

    @Override
    public final void initForm() {
        Common.setOpaqueComponent(false, this, jPanel2, panelData, jPanel1, panelData, jPanel1, jPanel3);
        loadViewTable(panelData);
        setCurentDate(start_date, end_date);
        end_date.setMaxSelectableDate(new Date());
        start_date.setMaxSelectableDate(new Date());
        start_date.setMinSelectableDate(new Date(start_date.getDate().getYear(), 1, 1));
        viewTableData.clearTable();
    }

    @Override
    public void resetForm() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void loadData(String search) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void loadData() {
        new Thread(() -> {
            try {
                viewTableData.clearTable();
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                BillerResponse resp = sendRequest(
                        new GetMutasiSaldo(
                                format.format(start_date.getDate()),
                                format.format(end_date.getDate())
                        ),
                        "POST",
                        true
                );

                if (resp != null) {
                    JSONObject respObj = resp.getPayload();
                    JSONArray data = respObj.getJSONArray("data");
                    LinkedList<DataTable> dataTable = new LinkedList<>();

                    for (int i = 0; i < data.length(); i++) {
                        JSONObject mutasi = new JSONObject(data.get(i).toString());
                        String tTanggal = mutasi.getString("date");
                        String tAmount = Common
                                .formatRupiah(
                                        new BigDecimal(mutasi.get("amount").toString()).doubleValue()
                                );
                        String tSaldoAwal = Common
                                .formatRupiah(
                                        new BigDecimal(mutasi.get("balance").toString()).doubleValue()
                                );
                        
                        String tEndingBalance = Common
                                .formatRupiah(
                                        new BigDecimal(mutasi.get("ending_balance").toString()).doubleValue()
                                );
                        String tJurnal = mutasi.getString("journal");
                        String tTrxID = mutasi.getString("trxid");
                        String tRemarks = mutasi.getString("remarks");

                        MutasiModel model = new MutasiModel((i + 1), tTanggal, tJurnal, tSaldoAwal, tAmount, tEndingBalance, tTrxID, tRemarks);
                        dataTable.add(model);
                    }

                    if (dataTable.size() > 0) {
                        loadDataToTables(dataTable);
                    }
                }
            } catch (JSONException e) {
                Common.showErrorMessage(MessageType.SYSTEM_ERROR, this);
                Common.errorLog("[SQLException] Failed load data mutasi saldo", e);
            }
        }).start();
    }
}