/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.util;

import co.id.ez.ezpay.app.ConnectionManager;
import co.id.ez.ezpay.enums.util.Icons;
import javax.swing.JLabel;

/**
 *
 * @author Lutfi
 */
public class ConnectionRunner extends Thread {

    private final JLabel txtTarget;

    public ConnectionRunner(JLabel txtTarget) {
        this.txtTarget = txtTarget;
        ConnectionManager.instance().initMonitoring(this);
    }

    @Override
    public void run() {
        try {
            cekKoneksi(true);
            Thread.sleep(700);
            cekKoneksi(false);
            Thread.sleep(700);
        } catch (InterruptedException e) {
        }
    }

    private void cekKoneksi(boolean isToCTL) throws InterruptedException {
        String name = isToCTL ? "central" : "gateway";

        txtTarget.setIcon(Icons.SMALL_CEK_ONLINE.get());
        txtTarget.setText("Conecting to " + name + " ...");

        Thread.sleep(500);
        if (isToCTL ? ConnectionManager.instance().isConnectToCtl() : ConnectionManager.instance().isConnectToGW()) {
            txtTarget.setIcon(Icons.SMALL_ONLINE.get());
            txtTarget.setText("Conected to " + name);
        }
    }

    public void signal(boolean signal) {
        if(signal){
            txtTarget.setIcon(Icons.SMALL_ONLINE.get());
            txtTarget.setText("Online");
        }else{
            txtTarget.setIcon(Icons.SMALL_OFFLINE.get());
            txtTarget.setText("Offline");
        }
    }

    public void stopRunner() {
        super.stop();
        ConnectionManager.instance().stop();
    }
}
