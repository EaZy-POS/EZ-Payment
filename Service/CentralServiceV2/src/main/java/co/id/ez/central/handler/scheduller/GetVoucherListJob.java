/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.central.handler.scheduller;

import co.id.ez.central.controller.CentralController;
import co.id.ez.system.core.config.ConfigService;
import co.id.ez.system.core.log.LogService;
import com.json.JSONArray;
import com.json.JSONObject;
import java.io.IOException;
import java.math.BigDecimal;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 *
 * @author RCS
 */
public class GetVoucherListJob implements Job{

    private final CentralController controller = new CentralController();
    
    @Override
    public void execute(JobExecutionContext jec) throws JobExecutionException {
        LogService.getInstance(this).temp("scheduller").log("Start execute scheduller");
        try {
            LogService.getInstance(this).temp("scheduller-trace").log("Start execute job get voucher product list");
            Document doc = Jsoup.connect(ConfigService.getInstance()
                    .getString("central-service.config.voucherlist", 
                            "http://182.23.93.77:88/pl/price-list-h2h.html"))
                    .get();
            
            Element tablebody = doc.select("tbody").get(0);
            Elements tableRow = tablebody.select("tr");
            
            JSONObject voucherdata = new JSONObject();
            int index = 0;
            for (Element element : tableRow) {
                if (index > 0) {
                    JSONObject data = new JSONObject();
                    String tProvider = null;
                    for (int i = 0; i < 3; i++) {
                        switch (i){
                            case 0: 
                                data.put("voucherid", element.select("td").get(i).text().trim());
                                break;
                            case 1: 
                                String tname = element.select("td").get(i).text().trim();
                                tProvider = tname.split(" ")[0].trim();
                                data.put("vouchername", tname);
                                break;
                            case 2: 
                                data.put("price", 
                                        new BigDecimal(element.select("td")
                                                .get(i).text().trim().replace(".", "")).doubleValue()
                                        );
                                break;
                            default:
                                break;
                        }
                    }
                    
                    if(tProvider != null){
                        if(voucherdata.has(tProvider)){
                            voucherdata.getJSONArray(tProvider).put(data);
                        }else{
                            voucherdata.put(tProvider, new JSONArray().put(data));
                        }
                    }
                }
                index++;
            }
            
            saveAndUpdateData(voucherdata);
            
            LogService.getInstance(this).temp("scheduller-trace")
                    .log("After execute job get voucher product list");
        } catch (IOException ex) {
            LogService.getInstance(this).temp("scheduller-error").withCause(ex)
                    .log("[IOException] Failed execute job get voucher list", true);
        } catch (Exception ex) {
            LogService.getInstance(this).temp("scheduller-error").withCause(ex)
                    .log("[Exception] Failed execute job get voucher list", true);
        }
        
        LogService.getInstance(this).temp("scheduller").log("Finish execute scheduller. "+ jec.toString());
    }
    
    private void saveAndUpdateData(JSONObject datavoucher) throws Exception{
        LogService.getInstance(this).temp("scheduller-trace").log("Before save and update voucher product list");
        BigDecimal tMarkupFee = controller.getVoucherProductMarkup();
        for (Object key : datavoucher.keySet()) {
            String tProvider = key.toString();
            
            LogService.getInstance(this).temp("scheduller-trace").log("Before validate provider["+tProvider+"]");
            int providerid = controller.validateProviderData(tProvider, true);
            LogService.getInstance(this).temp("scheduller-trace")
                    .log("After validate provider["+tProvider+"] Success ["+providerid+"]");
            
            if(providerid > 0){
                JSONArray data = datavoucher.getJSONArray(tProvider);
                for (int i = 0; i < data.length(); i++) {
                    JSONObject voucher = data.getJSONObject(i);
                    String tVOucherid = voucher.getString("voucherid");
                    String tVOuchername = voucher.getString("vouchername");
                    double price = voucher.getDouble("price");
                    
                    LogService.getInstance(this).temp("scheduller-trace").log("Before validate voucer["+voucher+"]");
                    controller.validateVoucherData(tVOucherid, tVOuchername, price, tMarkupFee.doubleValue(), providerid);
                    LogService.getInstance(this).temp("scheduller-trace").log("After validate voucer["+voucher+"] Success");
                }
            }else{
                LogService.getInstance(this).temp("scheduller-trace").log("Skip insert data for ["+tProvider+"]");
            }
        }
        
        LogService.getInstance(this).temp("scheduller-trace").log("After save and update voucher product list");
    }
}
