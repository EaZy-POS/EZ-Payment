/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.view;

import co.id.ez.ezpay.abstracts.AbstractModule;
import co.id.ez.ezpay.enums.util.Icons;
import co.id.ez.ezpay.util.DateTimeRunner;

/**
 *
 * @author RCS
 */
public class Celender extends AbstractModule {

    /**
     * Creates new form Summary
     */
    public Celender() {
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

        jPanel2 = new javax.swing.JPanel();
        jCalendar1 = new com.toedter.calendar.JCalendar();
        jPanel1 = new javax.swing.JPanel();
        txt_jam = new javax.swing.JLabel();

        setForeground(new java.awt.Color(60, 63, 65));
        setLayout(new java.awt.BorderLayout());

        jPanel2.setBackground(new java.awt.Color(27, 38, 44));
        jPanel2.setPreferredSize(new java.awt.Dimension(0, 150));
        jPanel2.setLayout(new java.awt.CardLayout());

        jCalendar1.setPreferredSize(new java.awt.Dimension(576, 130));
        jPanel2.add(jCalendar1, "card2");

        add(jPanel2, java.awt.BorderLayout.CENTER);

        jPanel1.setBackground(new java.awt.Color(27, 38, 44));
        jPanel1.setPreferredSize(new java.awt.Dimension(371, 30));
        jPanel1.setLayout(new java.awt.CardLayout());

        txt_jam.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        txt_jam.setForeground(new java.awt.Color(255, 255, 255));
        txt_jam.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txt_jam.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ico/dashboard.png"))); // NOI18N
        txt_jam.setText("  00:00:00   ");
        txt_jam.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jPanel1.add(txt_jam, "card2");

        add(jPanel1, java.awt.BorderLayout.PAGE_START);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JCalendar jCalendar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel txt_jam;
    // End of variables declaration//GEN-END:variables

    @Override
    public final void initForm() {
        txt_jam.setIcon(Icons.LARGE_CLOCK.get());
//   DateTimeRunnernew Clock(txt_jam).start();
    }

    @Override
    public void resetForm() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public final void loadData() {
    }
}
