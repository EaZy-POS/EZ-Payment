/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.util;

import co.id.ez.ezpay.controller.HistoryTransaksiController;
import co.id.ez.ezpay.enums.RequestType;
import co.id.ez.ezpay.util.swings.menu.ListMenu;
import co.id.ez.system.core.log.LogService;
import com.json.JSONObject;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 *
 * @author Lutfi
 */
public class CekTagihanNotification extends Thread{

    private final HistoryTransaksiController controler = new HistoryTransaksiController(true);
    private final RequestType module;
    private final ListMenu target;

    public CekTagihanNotification(RequestType module, ListMenu target) {
        this.module = module;
        this.target = target;
    }
    
    @Override
    public void run() {
        while (true) {
            try {
                LinkedList<JSONObject> data = controler.getHistoryData(module, null, "", 2);
                if (data != null) {
                    String notif = data.size() > 0 ? String.valueOf(data.size()) : "";
                    target.changeNotification(1, notif);
                }
            } catch (SQLException e) {
                LogService.getInstance(this).error().withCause(e).log("[SQLException] Failed change notification on Cek Tagihan", true);
            } catch (Exception e) {
                LogService.getInstance(this).error().withCause(e).log("[Exception] Failed change notification on Cek Tagihan", true);
            }
            
            try {
                Thread.sleep(20000);
            } catch (InterruptedException e) {
            }
        }
    }
    
}
