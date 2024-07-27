/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.util.swings;

import co.id.ez.database.DB;
import co.id.ez.ezpay.abstracts.AbstractModule;
import co.id.ez.ezpay.app.Common;
import co.id.ez.ezpay.enums.util.Icons;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.SwingWorker;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.swing.JRViewer;
import net.sf.jasperreports.swing.JRViewerToolbar;
import net.sf.jasperreports.view.JRSaveContributor;
import net.sf.jasperreports.view.save.JRDocxSaveContributor;
import net.sf.jasperreports.view.save.JRPdfSaveContributor;
import net.sf.jasperreports.view.save.JRSingleSheetXlsSaveContributor;

/**
 *
 * @author RCS
 */
public class ReportViewer extends JFrame {

    private Connection mConnection;
    private LoadingViewer loading;

    /**
     * Creates new form ReportViewer
     */
    public ReportViewer() {
        initComponents();
        initForm();
    }

    private void initForm() {
        this.setTitle("Laporan");
        ImageIcon img = Icons.ASSETS_ICONS.get();
        this.setIconImage(img.getImage());
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) Math.abs(screenSize.getWidth() * 0.7);
        int height = (int) Math.abs(screenSize.getHeight() * 0.9);
        this.setMinimumSize(new Dimension(width, height));
        this.setLocationRelativeTo(this);
        this.setExtendedState(JFrame.MAXIMIZED_VERT);
        setResizable(false);
        loading = new LoadingViewer(this);
    }

    public void viewReport(String pFileName) {
        viewReport(pFileName, null);
    }

    public void viewReport(String pFileName, HashMap<String, Object> pParam) {
        viewReport(pFileName, null, pParam);
    }

    public void viewReport(String pFileName, String pQuery, HashMap<String, Object> pParam) {
        SwingWorker<Void, Void> sw = new SwingWorker<Void, Void>() {

            @Override
            protected Void doInBackground() throws Exception {
                viewReportWorker(pFileName, pQuery, pParam);
                return null;
            }

            @Override
            protected void done() {
                loading.setVisible(false);
            }
        };

        this.setVisible(true);
        loading.runningTask(sw);
    }

    public void viewReportWorker(String pFileName, String pQuery, HashMap<String, Object> pParam) {
        try {
            mConnection = DB.getConnection(AbstractModule.dbName);
            if (mConnection != null) {
                try {
                    JasperReport jasperReport;
                    if (pQuery != null && !pQuery.equals("")) {
                        JasperDesign jasperDesign = JRXmlLoader.load(pFileName);
                        JRDesignQuery jasperQuery = new JRDesignQuery();
                        jasperQuery.setText(pQuery);
                        jasperDesign.setQuery(jasperQuery);
                        jasperReport = JasperCompileManager.compileReport(jasperDesign);
                    } else {
                        jasperReport = JasperCompileManager.compileReport(pFileName);
                    }

                    JasperPrint print = JasperFillManager.fillReport(jasperReport, pParam, mConnection);
                    JRViewer viewer = new Report(print);
                    viewer.setOpaque(true);
                    viewer.setVisible(true);
                    viewer.setFitPageZoomRatio();
                    panelReport.add(viewer);
                    panelReport.repaint();
                    panelReport.revalidate();
                } catch (JRException ex) {
                    Common.showErrorMessage("Failed show report!\n" + ex.getMessage(), this);
                    Common.errorLog("Failed show report", ex);
                } finally {
                    mConnection.close();
                }
            } else {
                Common.showErrorMessage("Connection DB is not initialize yet", this);
            }
        } catch (SQLException ex) {
            Common.showErrorMessage("Failed initialize database", this);
            Common.errorLog("Failed show report", ex);
        }
        try {
            mConnection.close();
        } catch (SQLException e) {
        }
    }

    private class Report extends JRViewer {

        public Report(JasperPrint jrPrint) {
            super(jrPrint);
        }

        @Override
        protected JRViewerToolbar createToolbar() {
            JRViewerToolbar toolbar = super.createToolbar();

            Locale locale = viewerContext.getLocale();
            ResourceBundle resBundle = viewerContext.getResourceBundle();
            JRPdfSaveContributor pdf = new JRPdfSaveContributor(locale, resBundle);
            JRSingleSheetXlsSaveContributor xls = new JRSingleSheetXlsSaveContributor(locale, resBundle);
            JRDocxSaveContributor docx = new JRDocxSaveContributor(locale, resBundle);
            toolbar.setSaveContributors(new JRSaveContributor[]{pdf, xls, docx});
            return toolbar;
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

        panelReport = new co.id.ez.ezpay.util.swings.CustomPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(new java.awt.CardLayout());

        panelReport.setLayout(new java.awt.CardLayout());
        getContentPane().add(panelReport, "card2");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        if (Common.showConfirmMessage("Tutup laporan?", this) == 0) {
            if (mConnection != null) {
                try {
                    mConnection.close();
                } catch (SQLException e) {
                }
            }
            this.dispose();
        }
    }//GEN-LAST:event_formWindowClosing


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private co.id.ez.ezpay.util.swings.CustomPanel panelReport;
    // End of variables declaration//GEN-END:variables
}
