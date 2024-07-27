/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.central.handler;

import co.id.ez.central.enums.Fields;
import co.id.ez.system.core.etc.EncryptionService;
import co.id.ez.system.core.ex.ServiceException;
import co.id.ez.system.core.log.LogService;
import co.id.ez.system.core.rc.RC;
import com.json.JSONObject;
import java.sql.SQLException;

/**
 *
 * @author Lutfi
 */
public class AktifasiHandler extends MessageHandler{

    @Override
    public String performHandler(JSONObject request) {
        JSONObject tResponse = new JSONObject(request.toString());
        try {
            LogService.getInstance(this).trace().log("(" + corelation_id + ") Before handle registrasi");
            String tClientID = request.getString(Fields.client_id.name());
            String tClientKey = request.getString(Fields.secret_key.name());
            
            String tEncryptedClientID = EncryptionService.encryptor().Base64Encrypt(
                EncryptionService.encryptor().encrypt(tClientID, tClientKey)
            );
            
            LogService.getInstance(this).trace().log("(" + corelation_id + ") Before getting data mitra");
            JSONObject dataMitra = controller.getMitra(tEncryptedClientID);
            LogService.getInstance(this).trace().log("(" + corelation_id + ") After getting data mitra (success)");
            
            if(dataMitra == null){
                throw new ServiceException(RC.ERROR_UNKNOWN_SUBSCRIBER, "Mitra id tidak ditemukan");
            }
            
            int id_client = dataMitra.getInt("id");
            
            LogService.getInstance(this).trace().log("(" + corelation_id + ") Before activate mitra");
            controller.activateMitra(id_client);
            LogService.getInstance(this).trace().log("(" + corelation_id + ") After activate (success)");
            
            tResponse.put("rc", "0000")
                    .put("rcm", "success")
                    .put("access",
                            new JSONObject()
                                    .put("client_id", tClientID)
                                    .put("client_key", tClientKey)
                    );
            
        } catch (SQLException e) {
            LogService.getInstance(this)
                    .error().withCause(e)
                    .log("(" + corelation_id + ") Failed insert new users", true);
            tResponse.put("rc", "0091")
                    .put("rcm", "Internal error");
        } catch (ServiceException e) {
            LogService.getInstance(this)
                    .error().withCause(e)
                    .log("(" + corelation_id + ") Failed insert new users", true);
            tResponse.put("rc", e.getRC().getResponseCodeString())
                    .put("rcm", e.getMessage());
        }
        
        LogService.getInstance(this)
                .trace().log("(" + corelation_id + ") After handle registrasi, with response: "+ tResponse);
        return tResponse.toString();
    }
    
}
