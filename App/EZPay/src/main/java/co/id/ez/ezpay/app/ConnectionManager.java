/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.app;

import co.id.ez.ezpay.msg.data.CekKoneksi;
import co.id.ez.ezpay.util.ConnectionRunner;
import co.id.ez.ezpay.view.settings.ProfileView;

/**
 *
 * @author RCS
 */
public class ConnectionManager extends Thread{

    private boolean isConnectToGW, isConnetToCtl, isLogged;
    private ConnectionRunner monitoring;
   
    public void initMonitoring(ConnectionRunner monitor){
        this.monitoring = monitor;
    }
    
    @Override
    public void run() {
        while (true) {
            try {
                cekKoneksi(true);
                Thread.sleep(500);
                cekKoneksi(false);
                Thread.sleep(500);
            } catch (Exception e) {
            }
        }
    }
    
    private void cekKoneksi(boolean pIsToCTL) {
        try {
            String resp = new ProfileView(true).sendGetRequest(new CekKoneksi(), pIsToCTL, "connection");
            if (pIsToCTL) {
                isConnetToCtl = resp != null && !resp.equals("");
            } else {
                isConnectToGW = resp != null && !resp.equals("");
            }
            if(isAllConnectionSuccess()){
                signal(true);
            }
        } catch (Exception e) {
            signal(false);
            if(!isLogged){
                Common.errorLog("[Exception] Failed cek koneksi", e);
                isLogged = true;
            }
        }
    }

    public boolean isConnectToGW() {
        return isConnectToGW;
    }

    public boolean isConnectToCtl() {
        return isConnetToCtl;
    }

    public boolean isAllConnectionSuccess() {
        return isConnetToCtl && isConnectToGW;
    }

    public void signal(boolean signal) {
        isConnectToGW = signal;
        isConnetToCtl = signal;
        
        if(monitoring != null){
            monitoring.signal(signal);
        }
    }

    private static class Holder {

        public static ConnectionManager session = new ConnectionManager();
    }

    public static ConnectionManager instance() {
        return Holder.session;
    }
}
