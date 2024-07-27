package co.id.ez.ezpay.view.menu.central;

import co.id.ez.ezpay.util.swings.menu.ListMenu;
import co.id.ez.ezpay.util.swings.menu.Model_Menu;
import java.awt.Cursor;

public class MenuCentralData extends javax.swing.JPanel {

    public MenuCentralData() {
        initComponents();
    }

    public void init(Central central) {
        setOpaque(false);
        listMenu1.setOpaque(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        for (Model_Menu model_Menu : central.getListMenu()) {
            listMenu1.addItem(model_Menu);
        }
    }

    public ListMenu getListMenu() {
        return listMenu1;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        listMenu1 = new co.id.ez.ezpay.util.swings.menu.ListMenu<>();

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 1, Short.MAX_VALUE)
                .addComponent(listMenu1, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 1, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 7, Short.MAX_VALUE)
                .addComponent(listMenu1, javax.swing.GroupLayout.PREFERRED_SIZE, 460, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 7, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private co.id.ez.ezpay.util.swings.menu.ListMenu<String> listMenu1;
    // End of variables declaration//GEN-END:variables
}
