/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.view.dashboard;

import co.id.ez.ezpay.app.Common;
import co.id.ez.ezpay.controller.DashboardController;
import co.id.ez.ezpay.enums.util.Icons;
import co.id.ez.ezpay.util.swings.card.Card;
import co.id.ez.ezpay.util.swings.card.ModelCard;
import co.id.ez.ezpay.view.module.Dashboard;
import com.json.JSONObject;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.SwingWorker;

/**
 *
 * @author RCS
 */
public class DashboardSummaryLoader extends Thread {

    private final Dashboard dash;
    private final DashboardController controller = new DashboardController();
    List<Thread> threadList = new ArrayList<>();

    public DashboardSummaryLoader(Dashboard dash) {
        this.dash = dash;
    }

    @Override
    public void run() {
        loadSummaryMonthly();
        loadSummaryDaily();
    }
    
    private SwingWorker getSummaryMonthlyWorker(Card cardTransaction, Card cardAmount){
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            BigDecimal amount;
            double total;
            
            @Override
            protected Void doInBackground() throws Exception {
                try {
                    amount = BigDecimal.ZERO;
                    total = 0;
                    JSONObject data = controller.getSummaryCurrentMonth();
                    if (data != null) {
                        amount = new BigDecimal(data.getDouble("total_amount"));
                        total = data.getDouble("total_transaction");
                    }
                } catch (SQLException e) {
                    Common.errorLog("[SQLException] Failed load dashboard summary monthly", e);
                }
                
                return null;
            }

            @Override
            protected void done() {
                ModelCard card2 = new ModelCard(
                        Icons.WIDE_ESTIMATE.get(),
                        Common.formatRupiah(total, false),
                        "Jumlah Transaksi bulan ini"
                );
                cardTransaction.setData(card2);

                ModelCard card = new ModelCard(
                        Icons.WIDE_TOTAL_SALES.get(),
                        Common.formatRupiah(amount.doubleValue()),
                        "Total Transaksi bulan ini"
                );
                cardAmount.setData(card);
            }
        };
        
        return worker;
    }
    
    private void loadSummaryMonthly(){
        final Card cardTransaction = new Card();
        final Card cardAmount = new Card();
        runWorkerMonthly(cardTransaction, cardAmount);
    }
    
    private void runWorkerMonthly(Card cardTransaction, Card cardAmount){
        dash.addDashboardSummary(cardTransaction, cardAmount);
        Thread thread = new Thread(()->{
            while (true) {
                try {
                    getSummaryMonthlyWorker(cardTransaction, cardAmount).execute();
                    Thread.sleep(20000);
                } catch (Exception e) {
                    Common.errorLog("[Exception] Failed load dashboard summary", e);
                }
            }
        });
        
        thread.start();
        threadList.add(thread);
    }
    
    private void runWorkerDaily(Card cardTransaction, Card cardAmount){
        dash.addDashboardSummary(cardTransaction, cardAmount);
        Thread thread = new Thread(()->{
            while (true) {
                try {
                    getSummaryDailyWorker(cardTransaction, cardAmount).execute();
                    Thread.sleep(20000);
                } catch (Exception e) {
                    Common.errorLog("[Exception] Failed load dashboard summary", e);
                }
            }
        });
        
        thread.start();
        threadList.add(thread);
    }
    
    public void stopWorker(){
        threadList.forEach(thread -> {
            System.out.println("co.id.ez.ezpay.view.dashboard.DashboardSummaryLoader.stopWorker(): stoping-> "+ thread);
            thread.stop();
        });
    }
    
    private SwingWorker getSummaryDailyWorker(Card cardTransaction, Card cardAmount){
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            BigDecimal amount;
            double total;
            
            @Override
            protected Void doInBackground() throws Exception {
                try {
                    amount = BigDecimal.ZERO;
                    total = 0;
                    JSONObject data = controller.getSummaryCurrentDay();
                    if (data != null) {
                        amount = new BigDecimal(data.getDouble("total_amount"));
                        total = data.getDouble("total_transaction");
                    }
                } catch (SQLException e) {
                    Common.errorLog("[SQLException] Failed load dashboard summary monthly", e);
                }
                
                return null;
            }

            @Override
            protected void done() {
                ModelCard card2 = new ModelCard(
                        Icons.WIDE_SALES.get(),
                        Common.formatRupiah(total, false),
                        "Jumlah Transaksi hari ini"
                );
                cardTransaction.setData(card2);

                ModelCard card = new ModelCard(
                        Icons.WIDE_PROFITS.get(),
                        Common.formatRupiah(amount.doubleValue()),
                        "Total Transaksi hari ini"
                );
                cardAmount.setData(card);
            }
        };
        
        return worker;
    }
    
    private void loadSummaryDaily(){
        final Card cardTransaction = new Card();
        final Card cardAmount = new Card();
        runWorkerDaily(cardTransaction, cardAmount);
    }

    
}
