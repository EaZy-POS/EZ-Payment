/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.central.controller;

import co.id.ez.central.enums.Fields;
import co.id.ez.central.resource.CommonHanlder;
import co.id.ez.database.DB;
import co.id.ez.system.core.ex.ServiceException;
import co.id.ez.system.core.rc.RC;
import com.json.JSONArray;
import com.json.JSONObject;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 *
 * @author LUTFI
 */
public class CentralController {

    public LinkedList<JSONObject> getVoucherProductList() throws SQLException {
        String tSQL = "SELECT "
                + "prov.id, "
                + "prov.provider_name, "
                + "prov.`status` as prov_active, "
                + "pref.prefix, "
                + "prd.voucher_id, "
                + "prd.voucher_name, "
                + "prd.price , "
                + "prd.product_type, "
                + "prd.`status` "
                + "FROM dp_vcr_product prd "
                + "INNER JOIN dp_vcr_provider prov ON prd.provider_id = prov.id "
                + "LEFT JOIN dp_vcr_provider_prefix pref on pref.provider_id = prov.id "
                + "ORDER BY prd.voucher_id, prd.voucher_name, prefix ASC";

        LinkedList<JSONObject> data = DB.executeQuery(CommonHanlder.dbName, tSQL);

        if (data != null && data.size() > 0) {
            return data;
        }

        return new LinkedList<>();
    }

    public LinkedList<JSONObject> getPDAMBillerList() throws SQLException {
        String tSQL = "SELECT biller, biller_name, distric, status FROM `dp_pam_biller` ORDER BY biller ASC";

        LinkedList<JSONObject> data = DB.executeQuery(CommonHanlder.dbName, tSQL);

        if (data != null && data.size() > 0) {
            return data;
        }

        return new LinkedList<>();
    }

    public LinkedList<JSONObject> getPrepaidDenomList() throws SQLException {
        String tSQL = "SELECT denom, description, status FROM `dp_pre_demon` ORDER BY denom ASC";

        LinkedList<JSONObject> data = DB.executeQuery(CommonHanlder.dbName, tSQL);

        if (data != null && data.size() > 0) {
            return data;
        }

        return new LinkedList<>();
    }

    public LinkedList<JSONObject> getMultiPaymentBillerList() throws SQLException {
        return getMultiPaymentBillerList(null);
    }

    public LinkedList<JSONObject> getMultiPaymentBillerList(String moduleCode) throws SQLException {
        String tSQL = "SELECT biller, biller_name, "
                + "input_1_label, input_1_type, "
                + "input_2_label, input_2_type, "
                + "input_3_label, input_3_type, "
                + "details, status, module_code "
                + "FROM dp_mp_biller "
                + (moduleCode == null ? "" : "WHERE module_code = '" + moduleCode + "' ")
                        + "ORDER BY biller ASC";

        LinkedList<JSONObject> data = DB.executeQuery(CommonHanlder.dbName, tSQL);

        if (data != null && data.size() > 0) {
            return data;
        }

        return new LinkedList<>();
    }

    public int validateProviderData(String provider, boolean isfirsttime) throws SQLException {
        String tSQL = "SELECT id FROM dp_vcr_provider WHERE provider_name = '" + provider + "'";
        LinkedList<JSONObject> data = DB.executeQuery(CommonHanlder.dbName, tSQL);

        if (data != null && data.size() > 0) {
            return data.getFirst().getInt("id");
        } else {
            if (isfirsttime) {
                tSQL = "INSERT INTO dp_vcr_provider (created_at, created_by, provider_name, status) "
                        + "VALUES (NOW(), 'Sch', '" + provider + "', 1)";
                DB.executeUpdate(CommonHanlder.dbName, tSQL);
                return validateProviderData(provider, false);
            } else {
                return -1;
            }

        }
    }

    public void validateVoucherData(String voucherid, String vouchername, double price, int providerid) throws SQLException {

        String tSQL = "SELECT id FROM dp_vcr_product WHERE voucher_id = '" + voucherid + "' AND provider_id = " + providerid;
        LinkedList<JSONObject> data = DB.executeQuery(CommonHanlder.dbName, tSQL);

        if (data != null && data.size() > 0) {
            int id = data.getFirst().getInt("id");

            tSQL = "UPDATE dp_vcr_product SET voucher_id = '" + voucherid + "', "
                    + "voucher_name = '" + vouchername + "', price = '" + price + "', status =1 "
                    + "WHERE id = '" + id + "' AND provider_id = " + providerid;
            DB.executeUpdate(CommonHanlder.dbName, tSQL);

        } else {
            tSQL = "INSERT "
                    + "INTO dp_vcr_product (created_at, created_by, voucher_id, voucher_name, price, provider_id, status) "
                    + "VALUES (NOW(), 'Sch', '" + voucherid + "', '" + vouchername + "', '"
                    + price + "', '" + providerid + "', 1)";
            DB.executeUpdate(CommonHanlder.dbName, tSQL);
        }
    }

    public int insertNewMitra(String clientid) throws SQLException {
        return insertNewMitra(clientid, 0);
    }

    public int insertNewMitra(String clientid, int count) throws SQLException {
        if (count < 3) {
            String tSQL = "INSERT INTO ap_mitra "
                    + "(created_at, mitra_id, status) "
                    + "VALUES (NOW(), '" + clientid + "', 0)";
            DB.executeUpdate(CommonHanlder.dbName, tSQL);

            JSONObject data = getMitra(clientid);

            if (data == null) {
                return insertNewMitra(clientid, count++);
            }

            return data.getInt("id");
        }

        return 0;
    }

    public JSONObject getMitra(String clientid) throws SQLException {
        String tSQL = "SELECT id FROM ap_mitra WHERE mitra_id = '" + clientid + "'";
        LinkedList<JSONObject> data = DB.executeQuery(CommonHanlder.dbName, tSQL);

        if (data != null) {
            if (data.size() > 0) {
                return data.getFirst();
            }
        }

        return null;
    }

    public void deleteMitra(String clientid) throws SQLException {
        String tSQL = "DELETE FROM ap_mitra WHERE mitra_id = '" + clientid + "'";
        DB.executeUpdate(CommonHanlder.dbName, tSQL);
    }

    public void activateMitra(int clientid) throws SQLException {
        String tSQL = "UPDATE ap_mitra SET status =1, updated_at = NOW(), updated_by = 'Admin' "
                + "WHERE mitra_id = '" + clientid + "'";
        DB.executeUpdate(CommonHanlder.dbName, tSQL);
    }

    public void insertMitraAccess(int id, String access) throws SQLException {
        String tSQL = "INSERT INTO ap_mitra_access "
                + "(created_at, mitra_id, mitra_access, status) "
                + "VALUES (NOW(), '" + id + "', '" + access + "', 1)";
        DB.executeUpdate(CommonHanlder.dbName, tSQL);
    }

    public void deleteMitraAccess(int id) throws SQLException {
        String tSQL = "DELETE FROM ap_mitra_access "
                + "WHERE mitra_id = '" + id + "'";
        DB.executeUpdate(CommonHanlder.dbName, tSQL);
    }

    public int insertMitraCred(int id, String cred) throws SQLException {
        return insertMitraCred(id, cred, 0);
    }

    public int insertMitraCred(int id, String cred, int count) throws SQLException {
        if (count < 3) {
            String tSQL = "INSERT INTO ap_mitra_cred "
                    + "(created_at, mitra_id, user_id, status) "
                    + "VALUES (NOW(), '" + id + "', '" + cred + "', 1)";
            DB.executeUpdate(CommonHanlder.dbName, tSQL);

            JSONObject datacred = getMitraCred(id, cred);
            if (datacred == null) {
                return insertMitraCred(id, cred, count++);
            }
            return datacred.getInt("id");
        }

        return 0;
    }

    public JSONObject getMitraCred(int id, String cred) throws SQLException {
        String tSQL = "SELECT id FROM ap_mitra_cred WHERE mitra_id = '" + id + "' "
                + "AND user_id = '" + cred + "'";
        LinkedList<JSONObject> data = DB.executeQuery(CommonHanlder.dbName, tSQL);

        if (data != null) {
            if (data.size() > 0) {
                return data.getFirst();
            }
        }

        return null;
    }

    public String getMitraAccessKey(String pClientID) throws SQLException {
        String tSQL = "SELECT mitra_access "
                + "FROM `ap_mitra_access` "
                + "INNER JOIN ap_mitra ON ap_mitra.id = ap_mitra_access.mitra_id "
                + "WHERE ap_mitra.mitra_id = '" + pClientID + "'";
        LinkedList<JSONObject> data = DB.executeQuery(CommonHanlder.dbName, tSQL);

        if (data != null) {
            if (data.size() > 0) {
                return data.getFirst().getString("mitra_access");
            }
        }

        throw new ServiceException(RC.ERROR_UNREGISTERED_ACCOUNT, "Invalid client id " + pClientID);
    }

    public void deleteMitraCred(int id) throws SQLException {
        String tSQL = "DELETE FROM ap_mitra_cred "
                + "WHERE mitra_id = '" + id + "' ";
        DB.executeUpdate(CommonHanlder.dbName, tSQL);
    }

    public void insertMitraCredAccess(int credid, String access) throws SQLException {
        String tSQL = "INSERT INTO ap_mitra_cred_access "
                + "(created_at, cred_id, cred_access)"
                + "VALUES (NOW(), '" + credid + "', '" + access + "')";
        DB.executeUpdate(CommonHanlder.dbName, tSQL);
    }

    public void deleteMitraCredAccess(int credid) throws SQLException {
        String tSQL = "DELETE ap_mitra_cred_access "
                + "WHERE cred_id = '" + credid + "'";
        DB.executeUpdate(CommonHanlder.dbName, tSQL);
    }

    public void insertMitraDetail(int id, JSONObject request) throws SQLException {

        String mitraName = request.getString(Fields.mitra_name.name());
        String address = request.getString(Fields.address.name());
        String phone = request.getString(Fields.phone.name());
        String wanumber = request.getString(Fields.wa_number.name());
        String contact = request.getString(Fields.contact_person.name());
        String email = request.getString(Fields.email.name());
        String kota = request.getString(Fields.city.name());
        String provincy = request.getString(Fields.province.name());
        String country = request.getString(Fields.country.name());
        String tagline = request.getString(Fields.tagline.name());

        String tSQL = "INSERT INTO ap_mitra_detail "
                + "(`created_at` ,"
                + "`mitra_id` ,"
                + "`mitra_name` ,"
                + "`phone` ,"
                + "`wa_number` ,"
                + "`email` ,"
                + "`address` ,"
                + "`city` ,"
                + "`provincy` ,"
                + "`country` ,"
                + "`contact_person` ,"
                + "`tagline`) "
                + "VALUES "
                + "(NOW(), "
                + "'" + id + "', "
                + "'" + mitraName + "', "
                + "'" + phone + "', "
                + "'" + wanumber + "', "
                + "'" + email + "', "
                + "'" + address + "', "
                + "'" + kota + "', "
                + "'" + provincy + "', "
                + "'" + country + "', "
                + "'" + contact + "', "
                + "'" + tagline + "' "
                + ")";
        DB.executeUpdate(CommonHanlder.dbName, tSQL);
    }

    public void insertModuleMitra(int mitraid) throws SQLException {
        String tSQL = "INSERT INTO ctl_module_mitra (created_at, created_by, module_id, mitra_id, status) "
                + "SELECT NOW(), 'Admin', id, '" + mitraid + "', 1 from dp_module WHERE module_id != 'DEP'";
        DB.executeUpdate(CommonHanlder.dbName, tSQL);
    }

    public void deleteModuleMitra(int mitraid) throws SQLException {
        String tSQL = "DELETE FROM ctl_module_mitra WHERE mitra_id = '" + mitraid + "'";
        DB.executeUpdate(CommonHanlder.dbName, tSQL);
    }

    public void deleteMitraDetail(int id) throws SQLException {
        String tSQL = "DELETE FROM ap_mitra_detail "
                + "WHERE mitra_id = '" + id + "'";
        DB.executeUpdate(CommonHanlder.dbName, tSQL);
    }

    public JSONObject getAppUpdate() throws SQLException {

        JSONObject tResult = new JSONObject();

        String tSQL = "SELECT "
                + "ap_version_update.app_version as version, "
                + "update_detail as info, "
                + "type, "
                + "update_seq as sequence, "
                + "update_file, "
                + "update_file_target, "
                + "update_type,"
                + "update_file_type "
                + "FROM `ap_version_update_detail` "
                + "INNER JOIN ap_version_update "
                + "ON ap_version_update_detail.app_version = ap_version_update.app_version "
                + "WHERE ap_version_update.`status` = 1 "
                + "ORDER BY ap_version_update.created_at DESC";

        LinkedList<JSONObject> data = DB.executeQuery(CommonHanlder.dbName, tSQL);

        if (data != null && data.size() > 0) {
            JSONArray tUpdate = new JSONArray();
            String tVersion = "", tType = "";

            for (int i = 0; i < data.size(); i++) {
                JSONObject dataUpdate = data.get(i);
                if (i == 0) {
                    tVersion = dataUpdate.get("version").toString().trim();
                    tType = dataUpdate.get("type").toString().trim();
                } else {
                    if (!dataUpdate.get("version").toString().trim().equalsIgnoreCase(tVersion)) {
                        break;
                    }
                }

                JSONObject detail = new JSONObject();
                detail.put("sequence", dataUpdate.get("sequence"));
                detail.put("description", dataUpdate.getString("info"));
                detail.put("file", dataUpdate.getString("update_file"));
                detail.put("target", dataUpdate.getString("update_file_target"));
                detail.put("type", dataUpdate.getString("update_type"));
                detail.put("action", dataUpdate.getString("update_file_type"));

                tUpdate.put(detail);
            }

            tResult.put("version", tVersion);
            tResult.put("type", tType);
            tResult.put("update", tUpdate);
            tResult.put("rc", "0000");
            tResult.put("rcm", "Sukses");

            return tResult;
        }

        tResult.put("rc", "0014");
        tResult.put("rcm", "No update");

        return null;
    }

}
