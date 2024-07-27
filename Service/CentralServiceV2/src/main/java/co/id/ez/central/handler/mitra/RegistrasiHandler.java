/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.central.handler.mitra;

import co.id.ez.central.enums.Fields;
import co.id.ez.central.handler.MessageHandler;
import co.id.ez.central.resource.CommonHanlder;
import co.id.ez.central.util.MitraAccess;
import co.id.ez.system.core.config.ConfigService;
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
public class RegistrasiHandler extends MessageHandler{

    @Override
    public String performHandler(JSONObject request) {
        JSONObject tResponse = new JSONObject(request.toString());
        JSONObject access = null;
        int stage = 0;
        try {
            LogService.getInstance(this).trace().log("(" + corelation_id + ") Before handle registrasi");
            String userid = request.getString(Fields.user_id.name());
            String password = request.getString(Fields.password.name());
            
            LogService.getInstance(this).trace().log("(" + corelation_id + ") Before generate new mitra");
            access = new JSONObject(new MitraAccess().generateNewMitra(userid, password));
            LogService.getInstance(this).trace().log("(" + corelation_id + ") After generate new mitra (success)");
            
            String tClientID = access.getJSONObject("user").getString("client_id");
            String tClientKey = access.getJSONObject("user").getString("secret_key");
            String tEncClientID = access.getJSONObject("user").getString("encrypted_client");
            String tEncClientKey = access.getJSONObject("user").getString("encrypted_key");
            String tEncryptedUser = access.getJSONObject("access").getString("encrypted_user");
            String tEncyptedPassword = access.getJSONObject("access").getString("encrypted_pass");
            
            LogService.getInstance(this).trace().log("(" + corelation_id + ") Before insert new mitra");
            int id_client = controller.insertNewMitra(tEncClientID);
            LogService.getInstance(this).trace().log("(" + corelation_id + ") After insert new mitra (success)");
            stage++;
            
            LogService.getInstance(this).trace().log("(" + corelation_id + ") Before insert mitra access");
            controller.insertMitraAccess(id_client, tEncClientKey);
            LogService.getInstance(this).trace().log("(" + corelation_id + ") After insert mitra access (success)");
            stage++;
            
            LogService.getInstance(this).trace().log("(" + corelation_id + ") Before insert mitra Cred");
            int id_cred = controller.insertMitraCred(id_client, tEncryptedUser);
            LogService.getInstance(this).trace().log("(" + corelation_id + ") After insert new mitra (success)");
            stage++;
            
            LogService.getInstance(this).trace().log("(" + corelation_id + ") Before insert mitra access cred");
            controller.insertMitraCredAccess(id_cred, tEncyptedPassword);
            LogService.getInstance(this).trace().log("(" + corelation_id + ") After insert mitra access cred (success)");
            stage++;
            
            LogService.getInstance(this).trace().log("(" + corelation_id + ") Before insert mitra detail");
            controller.insertMitraDetail(id_client, request);
            LogService.getInstance(this).trace().log("(" + corelation_id + ") After insert new mitra (success)");
            stage++;
            
            LogService.getInstance(this).trace().log("(" + corelation_id + ") Before insert module mitra");
            controller.insertModuleMitra(id_client);
            LogService.getInstance(this).trace().log("(" + corelation_id + ") After insert module mitra (success)");
            stage++;
            
            String dash = EncryptionService.encryptor().Base64Encrypt(
                EncryptionService.encryptor()
                        .encrypt(tClientID, cServiceKey));
            
            LogService.getInstance(this).trace().log("(" + corelation_id + ") Before create new account deposit");
            createDepositAccount(contructCreateAccountDepRequest(dash));
            stage++;
            LogService.getInstance(this).trace().log("(" + corelation_id + ") After create new account deposit (success)");
            
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
            deleteFailedRegistration(stage, access);
            throw new ServiceException(RC.ERROR_DATABASE, "Error inserting data registration", e);
        } catch (ServiceException e) {
            LogService.getInstance(this)
                    .error().withCause(e)
                    .log("(" + corelation_id + ") Failed insert new users", true);
            tResponse.put("rc", e.getRC().getResponseCodeString())
                    .put("rcm", e.getMessage());
            deleteFailedRegistration(stage, access);
            throw e;
        }
        
        LogService.getInstance(this)
                .trace().log("(" + corelation_id + ") After handle registrasi, with response: "+ tResponse);
        return tResponse.toString();
    }
    
    private JSONObject contructCreateAccountDepRequest(String dash){
        JSONObject tObjec = new JSONObject();
        tObjec.put("das_id", dash);
        return tObjec;
    }
    
    private void createDepositAccount(JSONObject pRequest) {
        LogService.getInstance(this).trace()
                .log("(" + corelation_id + ") Before send request create new account to deposit");
        String tUrl = ConfigService.getInstance()
                .getString(CommonHanlder.cDepAccountKey, "http://127.0.0.1:6567/api/deposit/account/create");
        String tAuthResponse = sendPostRequest(pRequest, tUrl, "DEP-SERVICE");

        if (!tAuthResponse.equals("") && tAuthResponse.startsWith("{")) {
            JSONObject tResponse = new JSONObject(tAuthResponse);
            String tRc = tResponse.getString("rc").substring(3);
            if (!tRc.equals("0000")) {
                LogService.getInstance(this).trace().log("After send request to deposit (failed)");
                throw new ServiceException(RC.parseResponseCodeString(tRc), tResponse.getString("rcm"));
            }
        }else{
            throw new ServiceException(RC.ERROR_OTHER, 
                    "(" + corelation_id + ") Get null/empty response from auth. [" + tAuthResponse + "]");
        }
        LogService.getInstance(this).trace()
                .log("(" + corelation_id + ") After send request create new account to deposit (Success)");
    }
    
    private void deleteFailedRegistration(int pStage, JSONObject pAccess){
        new Thread(()->{
            try {
                LogService.getInstance(this)
                        .trace()
                        .log("(" + corelation_id + ") "
                                + "Before deleting failed registration mitra with stage: " + pStage
                                + ", Access: " + pAccess);
                if (pAccess != null && pStage > 0) {
                    String tClientID = pAccess.getJSONObject("user").getString("client_id");
                    String tEncryptedUser = pAccess.getJSONObject("access").getString("encrypted_user");
                    JSONObject dataMitra = controller.getMitra(tClientID);
                    int id_client = dataMitra != null ? dataMitra.getInt("id") : 0;
                    JSONObject dataMitraCred = controller.getMitraCred(id_client, tEncryptedUser);
                    int id_cred = dataMitraCred != null ? dataMitraCred.getInt("id") : 0;
                    
                    if (pStage >= 1) {
                        LogService.getInstance(this)
                                .trace()
                                .log("(" + corelation_id + ") "
                                        + "Before deleting mitra: " + tClientID);
                        controller.deleteMitra(tClientID);
                        LogService.getInstance(this)
                                .trace()
                                .log("(" + corelation_id + ") "
                                        + "After deleting mitra: " + tClientID);
                    }

                    if (pStage >= 2) {
                        LogService.getInstance(this)
                                .trace()
                                .log("(" + corelation_id + ") "
                                        + "Before deleting mitra access: " + tClientID);
                        controller.deleteMitraAccess(id_client);
                        LogService.getInstance(this)
                                .trace()
                                .log("(" + corelation_id + ") "
                                        + "After deleting mitra access: " + tClientID);
                    }
                    
                    if (pStage >= 3) {
                        LogService.getInstance(this)
                                .trace()
                                .log("(" + corelation_id + ") "
                                        + "Before deleting mitra cred: " + tClientID);
                        controller.deleteMitraCred(id_client);
                        LogService.getInstance(this)
                                .trace()
                                .log("(" + corelation_id + ") "
                                        + "After deleting mitra cred: " + tClientID);
                    }
                    
                    if (pStage >= 4) {
                        LogService.getInstance(this)
                                .trace()
                                .log("(" + corelation_id + ") "
                                        + "Before deleting mitra access cred: " + tClientID);
                        controller.deleteMitraCredAccess(id_cred);
                        LogService.getInstance(this)
                                .trace()
                                .log("(" + corelation_id + ") "
                                        + "After deleting mitra access cred: " + tClientID);
                    }
                    
                    if (pStage >= 5) {
                        LogService.getInstance(this)
                                .trace()
                                .log("(" + corelation_id + ") "
                                        + "Before deleting mitra detail: " + tClientID);
                        controller.deleteMitraDetail(id_client);
                        LogService.getInstance(this)
                                .trace()
                                .log("(" + corelation_id + ") "
                                        + "After deleting mitra detail: " + tClientID);
                    }
                }
                LogService.getInstance(this)
                        .trace()
                        .log("(" + corelation_id + ") After deleting failed registration mitra");
            } catch (Exception e) {
                LogService.getInstance(this)
                    .error().withCause(e)
                    .log("(" + corelation_id + ") Failed delete new users", true);
            }
        }).start();
    }
}
