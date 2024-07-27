/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.app;

import static co.id.ez.ezpay.abstracts.AbstractModule.cTimeoutKey;
import co.id.ez.ezpay.view.base.SplashScreen;
import co.id.ez.system.core.config.ConfigService;
import co.id.ez.system.core.log.LogService;
import com.json.JSONArray;
import com.json.JSONObject;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.concurrent.TimeUnit;
import org.testcontainers.shaded.okhttp3.OkHttpClient;
import org.testcontainers.shaded.okhttp3.Request;
import org.testcontainers.shaded.okhttp3.Response;

/**
 *
 * @author RCS
 */
public class UpdateManager {

    private static UpdateManager INSTANCE;
    private final SplashScreen consumer;

    public UpdateManager(SplashScreen consumer) {
        this.consumer = consumer;
    }
    
    public static UpdateManager instance(SplashScreen consumer){
        if(INSTANCE == null){
            INSTANCE = new UpdateManager(consumer);
        }
        
        return INSTANCE;
    }
    
    public void cekUpdate() {
        consumer.setMaxProgressValue(2);
        consumer.changeProgress("Prepare checking update sistem", 0);
        String tBaseUrl = App.environment().getCentralUrl();
        int timeout = ConfigService.getInstance().getInt(cTimeoutKey);

        try {
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .connectTimeout(20000, TimeUnit.MILLISECONDS)
                    .readTimeout(timeout, TimeUnit.MILLISECONDS)
                    .build();
            
            Request.Builder builder = new Request.Builder()
                    .url(tBaseUrl.concat("/api/cekupdate"))
                    .get()
                    .addHeader("Accept", "application/json")
                    .addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0;Windows98;DigExt)")
                    .addHeader("charset", "utf-8")
                    .addHeader("Content-type", "application/json");
            
            Request httpRequest = builder.build();
            consumer.changeProgress("Checking update sistem", 1);
            Response response = client.newCall(httpRequest).execute();
            String tResponse = response.body().string();
            
            JSONObject tUpdateResult = new JSONObject(tResponse);
            if (tUpdateResult.getString("rc").equals("2000000")) {
                performUpdate(tUpdateResult);
            } else {
                consumer.changeProgress("No update for current application", 2);
            }
        } catch (IOException e) {
            LogService.getInstance(this).error()
                    .withCause(e).log("[IOException] Get not succes when trying to cek update", true);
            Common.showErrorMessage("Proses update gagal!\n"+ e.getMessage(), null);
            consumer.changeProgress("Failed update application", 2);
        } catch (Exception e) {
            LogService.getInstance(this).error()
                    .withCause(e).log("[Exception] Get not succes when trying to cek update", true);
            Common.showErrorMessage("Proses update gagal!\n"+ e.getMessage(), null);
            consumer.changeProgress("Failed update application", 2);
        }
    }
    
    private void performUpdate(JSONObject pUpdate) throws MalformedURLException, IOException{
        String tVersion = pUpdate.getString("version");
        String tType = pUpdate.getString("type");
        JSONArray update = pUpdate.getJSONArray("update");
        consumer.changeProgress("Update application found, "+ update.length() +" update", 2);
        consumer.setMaxProgressValue(update.length() + (3 * update.length()));
        consumer.changeProgress("Prepare to updating application", 0);
        
        for (int i = 0; i < update.length(); i++) {
            String tBaseUrl = App.environment().getCentralUrl().concat("/api/download");
            JSONObject data = new JSONObject(update.get(i).toString());
            String info = data.getString("description");
            int seq = data.getInt("sequence");
            String file = data.getString("file");
            String action = data.getString("action");
            String type = data.getString("type");
            String target = data.getString("target");
            tBaseUrl = tBaseUrl.concat("?version=").concat(tVersion).concat("&file=").concat(file);
            
            consumer.changeProgress("Update: "+ info, i + 1);
            
            File dest = new File("update", tVersion);
            
            consumer.changeProgress("Update: preparing ", i + 2);
            if(!dest.exists()){
                dest.mkdirs();
            }
            
            consumer.changeProgress("Update: downloading "+ tBaseUrl, i + 3);
            
            InputStream in = new URL(tBaseUrl).openStream();
            
            consumer.changeProgress("Update: saving update file", i + 4);
            Files.copy(in, Paths.get(dest.getPath().concat("/").concat(file)), StandardCopyOption.REPLACE_EXISTING);
        }
    }
}
