/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.central.handler.mitra;

import co.id.ez.central.enums.Fields;
import co.id.ez.central.handler.MessageHandler;
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
public class UpdateMitraHandler extends MessageHandler{

    @Override
    public String performHandler(JSONObject request) {
        JSONObject tResponse = new JSONObject(request.toString());
        try {
            LogService.getInstance(this).trace().log("(" + corelation_id + ") Before handle update mitra");
            String tClientID = request.getString(Fields.client_id.name());
            
            String tEncryptedClientID = EncryptionService.encryptor().Base64Encrypt(
                EncryptionService.encryptor().encrypt(tClientID, cServiceKey)
            );
            
            LogService.getInstance(this).trace().log("(" + corelation_id + ") Before update data mitra");
            JSONObject dataMitra = controller.getMitra(tEncryptedClientID);
            LogService.getInstance(this).trace().log("(" + corelation_id + ") After update data mitra (success)");
            
            if(dataMitra == null){
                throw new ServiceException(RC.ERROR_UNKNOWN_SUBSCRIBER, "Mitra id tidak ditemukan");
            }
            
            if(dataMitra.getInt("status") != 1){
                throw new ServiceException(RC.ERROR_ACCOUNT_ALREADY_DELETED, "Data mitra tidak aktif");
            }
            
            int id_client = dataMitra.getInt("id");
            
            
            LogService.getInstance(this).trace().log("(" + corelation_id + ") Before update mitra");
            controller.updateMitraDetail(id_client, request);
            LogService.getInstance(this).trace().log("(" + corelation_id + ") After update (success)");
            
            tResponse
                    .put("rc", "0000")
                    .put("rcm", "success");
            
        } catch (SQLException e) {
            LogService.getInstance(this)
                    .error().withCause(e)
                    .log("(" + corelation_id + ") Failed update motra", true);
            tResponse.put("rc", "0091")
                    .put("rcm", "Internal error");
        } catch (ServiceException e) {
            LogService.getInstance(this)
                    .error().withCause(e)
                    .log("(" + corelation_id + ") Failed update motra", true);
            tResponse.put("rc", e.getRC().getResponseCodeString())
                    .put("rcm", e.getMessage());
        }
        
        LogService.getInstance(this)
                .trace()
                .log("(" + corelation_id + ") After handle update mitra, with response: "+ tResponse);
        return tResponse.toString();
    }
    
}
