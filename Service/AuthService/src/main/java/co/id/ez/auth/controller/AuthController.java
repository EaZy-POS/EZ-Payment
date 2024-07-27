/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.auth.controller;

import co.id.ez.auth.enums.Fields;
import co.id.ez.auth.resource.AuthHandler;
import co.id.ez.database.DB;
import co.id.ez.database.entity.WhereField;
import co.id.ez.database.enums.Operator;
import co.id.ez.database.query.QueryBuilder;
import co.id.ez.database.query.QueryConditional;
import co.id.ez.database.query.QueryType;
import co.id.ez.system.core.ex.ServiceException;
import co.id.ez.system.core.rc.RC;
import com.json.JSONObject;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 *
 * @author LUTFI
 */
public class AuthController {

    public JSONObject getCredential(final JSONObject pRequest) {
        String tMitraID = "";
        try {
            tMitraID = pRequest.getString(Fields.client_id.name());
            String tUserID = pRequest.getString(Fields.user_id.name());
            String tQuery = "SELECT am.mitra_id , ama.mitra_access, cred_access, "
                    + "mitra_name, wa_number, email, user_id, address, city, provincy, country "
                    + "from ap_mitra am " 
                    + "INNER JOIN ap_mitra_access ama on ama.mitra_id = am.id " 
                    + "INNER JOIN ap_mitra_detail amd on amd.mitra_id = am.id " 
                    + "INNER JOIN ap_mitra_cred amc on amc.mitra_id = am.id " 
                    + "INNER JOIN ap_mitra_cred_access amca on amc.id = amca.cred_id " 
                    + "WHERE am.mitra_id = '"+tMitraID+"' AND user_id = '"+tUserID+"'";
            
            LinkedList<JSONObject> tResult = DB.executeQuery(AuthHandler.dbName, tQuery);
            
            if (tResult != null) {
                if (tResult.size() > 0) {
                    return tResult.getFirst();
                }
            }

        } catch (SQLException ex) {
            throw new ServiceException(RC.ERROR_DATABASE, "[SQLException] Failed to validate mitra", ex);
        }

        throw new ServiceException(RC.ERROR_UNREGISTERED_ACCOUNT, "Invalid Client ID["+tMitraID+"]");
    }
    
    public boolean isValidModule(String pProduct) {
        try {
            QueryBuilder builder = new QueryBuilder("dp_module", QueryType.SELECT);
            builder.addEntry("module_id", "module_name");
            builder.addWhereValue(new WhereField("module_id", pProduct, Operator.EQUALS));
            builder.addWhereValue(new WhereField("status", "1", Operator.EQUALS, QueryConditional.AND));

            LinkedList<JSONObject> tResult = DB.executeQuery(AuthHandler.dbName, builder);
            if (tResult != null) {
                return tResult.size() > 0;
            }
        } catch (SQLException ex) {
            throw new ServiceException(RC.ERROR_DATABASE, "[SQLException] Failed validate module", ex);
        }

        return false;
    }

    public boolean isValidClientModule(String pMitra, String pProduct) {
        return isValidClientModule(pMitra, pProduct, null);
    }
    
    public boolean isValidClientModule(String pMitra, String pProduct, String path) {

        try {
            String tQuery = "SELECT am.mitra_id, cmm.adm_fee, dm.module_id, dm.module_name "
                    + "FROM ctl_module_mitra cmm "
                    + "INNER JOIN ap_mitra am ON am.id = cmm.mitra_id "
                    + "INNER JOIN dp_module dm ON dm.id = cmm.module_id "
                    + (path != null ? "INNER JOIN dp_module_syslist dms ON dm.id = dms.module_id " : "")
                    + "WHERE am.mitra_id = '" + pMitra + "' "
                    + "AND dm.module_id = '" + pProduct + "' "
                    + (path != null ? "AND dms.path = '" + path + "' " : "")
                    + "AND cmm.`status` = 1 "
                    + "AND dm.`status` = 1 "
                    + (path != null ? "AND dms.`status` = 1" : "");

            LinkedList<JSONObject> tResult = DB.executeQuery(AuthHandler.dbName, tQuery);
            if (tResult != null) {
                return tResult.size() > 0;
            }
        } catch (SQLException ex) {
            throw new ServiceException(RC.ERROR_DATABASE, "[SQLException] Failed to get valid client module", ex);
        }

        return false;
    }
}
