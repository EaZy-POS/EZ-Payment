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
import java.math.BigDecimal;
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
                + "prd.id as vcr_id, "
                + "prd.voucher_id, "
                + "prd.voucher_name, "
                + "prd.price , "
                + "prd.markup , "
                + "prd.base_price , "
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

    public void insertVoucherProductlist(String pProviderID, String pVoucherID, String pVoucherName, String pProductType, String pPrice) throws SQLException {
        BigDecimal tMarkup = getVoucherProductMarkup();
        String tSQL = "INSERT INTO "
                + "dp_vcr_product (created_at, created_by, provider_id, "
                + "voucher_id, voucher_name, product_type, base_price, markup, price, status) "
                + "VALUES ("
                + "NOW(), "
                + "'Admin', "
                + "'" + pProviderID + "', "
                + "'" + pVoucherID + "', "
                + "'" + pVoucherName + "', "
                + "'" + pProductType + "', "
                + "'" + pPrice + "' , "
                + "'" + tMarkup.toPlainString() + "' , "
                + tMarkup.toPlainString().concat(" + ").concat(pPrice) + " , "
                + "1)";
        DB.executeUpdate(CommonHanlder.dbName, tSQL);
    }

    public void updateVoucherProductlist(String pID, String pProviderID, String pVoucherID, String pVoucherName, String pProductType, String pPrice, String pStatus) throws SQLException {
        BigDecimal tMarkup = getVoucherProductMarkup();
        String tSQL = "UPDATE "
                + "dp_vcr_product "
                + "SET updated_at = NOW(), "
                + "updated_by = 'Admin', "
                + "provider_id = '" + pProviderID + "', "
                + "voucher_id = '" + pVoucherID + "', "
                + "voucher_name = '" + pVoucherName + "', "
                + "product_type = '" + pProductType + "', "
                + "base_price = '" + pPrice + "', "
                + "markup = '" + tMarkup.toPlainString() + "', "
                + "price = " + tMarkup.toPlainString().concat(" + ").concat(pPrice) + ", "
                + "status = '" + pStatus + "' "
                + "WHERE id = '" + pID + "'";
        DB.executeUpdate(CommonHanlder.dbName, tSQL);
    }

    public void markupVoucherProductlist(String pMarkup) throws SQLException {
        String tSQL = "TRUNCATE "
                + "dp_vcr_product_markup ";
        DB.executeUpdate(CommonHanlder.dbName, tSQL);

        tSQL = "INSERT INTO "
                + "dp_vcr_product_markup (created_at, created_by, markup) "
                + "VALUES ("
                + "NOW(), "
                + "'Admin', "
                + "'" + pMarkup + "' )";
        DB.executeUpdate(CommonHanlder.dbName, tSQL);

        tSQL = "UPDATE "
                + "dp_vcr_product "
                + "SET updated_at = NOW(), "
                + "updated_by = 'Admin', "
                + "markup = " + pMarkup + ", "
                + "price = base_price + " + pMarkup + " "
                + "WHERE status < 2";
        DB.executeUpdate(CommonHanlder.dbName, tSQL);
    }

    public BigDecimal getVoucherProductMarkup() throws SQLException {
        BigDecimal tMarkup = BigDecimal.ZERO;
        String tSQL = "SELECT IFNULL((SELECT markup FROM dp_vcr_product_markup) , 0) as markup;";

        LinkedList<JSONObject> data = DB.executeQuery(CommonHanlder.dbName, tSQL);

        if (data != null && data.size() > 0) {
            tMarkup = new BigDecimal(data.getFirst().getDouble("markup"));
        }

        return tMarkup;
    }

    public void deleteVoucherProductlist(String pID) throws SQLException {
        String tSQL = "UPDATE "
                + "dp_vcr_product "
                + "SET "
                + "deleted_at = NOW(), "
                + "deleted_by = 'Admin', "
                + "status = '2' "
                + "WHERE id = '" + pID + "'";
        DB.executeUpdate(CommonHanlder.dbName, tSQL);
    }

    public LinkedList<JSONObject> getPDAMBillerList() throws SQLException {
        String tSQL = "SELECT id, biller, biller_name, distric, status "
                + "FROM `dp_pam_biller` "
                + "ORDER BY biller ASC";

        LinkedList<JSONObject> data = DB.executeQuery(CommonHanlder.dbName, tSQL);

        if (data != null && data.size() > 0) {
            return data;
        }

        return new LinkedList<>();
    }

    public void insertPDAMBillerList(String pBiller, String pBillerName, String pDistrict) throws SQLException {
        String tSQL = "INSERT INTO "
                + "dp_pam_biller (created_at, created_by, biller, biller_name, distric, status) "
                + "VALUES (NOW(), 'Admin', '" + pBiller + "', '" + pBillerName + "', '" + pDistrict + "', 1)";
        DB.executeUpdate(CommonHanlder.dbName, tSQL);
    }

    public void updatePDAMBillerList(String pID, String pBiller, String pBillerName, String pDistrict, String pStatus) throws SQLException {
        String tSQL = "UPDATE dp_pam_biller SET "
                + "updated_at = NOW(), "
                + "updated_by = 'Admin', "
                + "biller = '" + pBiller + "', "
                + "biller_name = '" + pBillerName + "', "
                + "distric = '" + pDistrict + "', "
                + "status = '" + pStatus + "' "
                + "WHERE id = '" + pID + "'";
        DB.executeUpdate(CommonHanlder.dbName, tSQL);
    }

    public void deletePDAMBillerList(String pID) throws SQLException {
        String tSQL = "DELETE FROM dp_pam_biller WHERE id = '" + pID + "'";
        DB.executeUpdate(CommonHanlder.dbName, tSQL);
    }

    public LinkedList<JSONObject> getPrepaidDenomList() throws SQLException {
        String tSQL = "SELECT id, denom, price_sale, description, status FROM `dp_pre_demon` WHERE price_sale > 0 ORDER BY denom ASC";

        LinkedList<JSONObject> data = DB.executeQuery(CommonHanlder.dbName, tSQL);

        if (data != null && data.size() > 0) {
            return data;
        }

        return new LinkedList<>();
    }

    public void insertPrepaidDenomList(String pDenom, String pDescription, BigDecimal pPriceSales) throws SQLException {
        String tSQL = "INSERT INTO "
                + "dp_pre_demon (created_at, created_by, denom, description, price_sale, status) "
                + "VALUES (NOW(), 'Admin', '" + pDenom + "', '" + pDescription + "', '" + pPriceSales.toPlainString() + "', 1)";
        DB.executeUpdate(CommonHanlder.dbName, tSQL);
    }

    public void updatePrepaidDenomList(String pID, String pDenom, String pDescription, BigDecimal pSaleProce, String pStatus) throws SQLException {
        String tSQL = "UPDATE dp_pre_demon SET "
                + "updated_at = NOW(), "
                + "updated_by = 'Admin', "
                + "denom = '" + pDenom + "', "
                + "description = '" + pDescription + "', "
                + "price_sale = '" + pSaleProce.toPlainString() + "', "
                + "status = '" + pStatus + "' "
                + "WHERE id = '" + pID + "'";
        DB.executeUpdate(CommonHanlder.dbName, tSQL);
    }

    public void deletePrepaidDenomList(String pID) throws SQLException {
        String tSQL = "DELETE FROM dp_pre_demon WHERE id = '" + pID + "'";
        DB.executeUpdate(CommonHanlder.dbName, tSQL);
    }

    public LinkedList<JSONObject> getMultiPaymentBillerList() throws SQLException {
        String tSQL = "SELECT biller, biller_name, "
                + "input_1_label, input_1_type, "
                + "input_2_label, input_2_type, "
                + "input_3_label, input_3_type, "
                + "details, status "
                + "FROM dp_mp_biller "
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

    public void validateVoucherData(String voucherid, String vouchername, double price, double markup, int providerid) throws SQLException {

        String tSQL = "SELECT id FROM dp_vcr_product WHERE voucher_id = '" + voucherid + "' AND provider_id = " + providerid;
        LinkedList<JSONObject> data = DB.executeQuery(CommonHanlder.dbName, tSQL);

        if (data != null && data.size() > 0) {
            int id = data.getFirst().getInt("id");

            tSQL = "UPDATE dp_vcr_product SET voucher_id = '" + voucherid + "', "
                    + "voucher_name = '" + vouchername + "', base_price = '" + price + "', "
                    + " markup = '" + markup + "', price = '" + (price + markup) + "', "
                    + "status =1 "
                    + "WHERE id = '" + id + "' AND provider_id = " + providerid;
            DB.executeUpdate(CommonHanlder.dbName, tSQL);

        } else {
            tSQL = "INSERT "
                    + "INTO dp_vcr_product (created_at, created_by, voucher_id, voucher_name, "
                    + "base_price, markup, price, provider_id, status) "
                    + "VALUES (NOW(), 'Sch', '" + voucherid + "', '" + vouchername
                    + "', '" + price + "', '" + markup + "', '" + (price + markup) + "', '" + providerid + "', 1)";
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
        String tSQL = "SELECT id, status FROM ap_mitra WHERE mitra_id = '" + clientid + "'";
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
    
    public void updateMitraDetail(int id, JSONObject request) throws SQLException {

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

        String tSQL = "UPDATE ap_mitra_detail SET "
                + "`updated_at`= NOW() ,"
                + "`updated_by` = 'Admin' ,"
                + "`mitra_name` = '" + mitraName + "' ,"
                + "`phone` = '" + phone + "', "
                + "`wa_number` = '" + wanumber + "', "
                + "`email` = '" + email + "', "
                + "`address` = '" + address + "', "
                + "`city` = '" + kota + "', "
                + "`provincy` = '" + provincy + "', "
                + "`country` = '" + country + "', "
                + "`contact_person` = '" + contact + "', "
                + "`tagline` = '" + tagline + "' "
                + "WHERE id = '" + id + "'";
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

    public void insertModuleMitra(int mitraid) throws SQLException {
        String tSQL = "INSERT INTO ctl_module_mitra (created_at, created_by, module_id, mitra_id, status) "
                + "SELECT NOW(), 'Admin', id, '" + mitraid + "', 1 from dp_module WHERE module_id != 'DEP'";
        DB.executeUpdate(CommonHanlder.dbName, tSQL);
    }

    public void deleteModuleMitra(int mitraid) throws SQLException {
        String tSQL = "DELETE FROM ctl_module_mitra WHERE mitra_id = '" + mitraid + "'";
        DB.executeUpdate(CommonHanlder.dbName, tSQL);
    }

    public void activateMitra(int clientid) throws SQLException {
        String tSQL = "UPDATE ap_mitra SET status =1, updated_at = NOW(), updated_by = 'Admin' "
                + "WHERE id = '" + clientid + "'";
        DB.executeUpdate(CommonHanlder.dbName, tSQL);
    }
    
    public void blockMitra(int clientid) throws SQLException {
        String tSQL = "UPDATE ap_mitra SET status =2, deleted_at = NOW(), deleted_by = 'Admin' "
                + "WHERE id = '" + clientid + "'";
        DB.executeUpdate(CommonHanlder.dbName, tSQL);
    }

    public LinkedList<JSONObject> getModuleList() throws SQLException {
        String tSQL = "SELECT id, module_id, module_name, `status`, is_need_sign "
                + "FROM `dp_module` "
                + "WHERE status < 2 "
                + "ORDER BY id ASC";

        LinkedList<JSONObject> data = DB.executeQuery(CommonHanlder.dbName, tSQL);

        if (data != null && data.size() > 0) {
            return data;
        }

        return new LinkedList<>();
    }

    public void insertModule(String pModuleID, String pModuleName, boolean pIsNeedSign) throws SQLException {
        String tSQL = "INSERT INTO "
                + "dp_module (created_at, created_by, module_id, module_name, is_need_sign, status) "
                + "VALUES (NOW(), 'Admin', '" + pModuleID + "', '" + pModuleName + "', '" + (pIsNeedSign ? "1" : "0") + "', 1)";
        DB.executeUpdate(CommonHanlder.dbName, tSQL);
    }

    public void updateModule(String pID, String pModuleID, String pModuleName, boolean pIsNeedSign, String pStatus) throws SQLException {
        String tSQL = "UPDATE dp_module SET "
                + "updated_at = NOW(), "
                + "updated_by = 'Admin', "
                + "module_id = '" + pModuleID + "', "
                + "module_name = '" + pModuleName + "', "
                + "is_need_sign = '" + (pIsNeedSign ? "1" : "0") + "', "
                + "status = '" + pStatus + "' "
                + "WHERE id = '" + pID + "'";
        DB.executeUpdate(CommonHanlder.dbName, tSQL);
    }

    public void deleteModule(String pID) throws SQLException {
        String tSQL = "UPDATE dp_module SET "
                + "deleted_at = NOW(), "
                + "deleted_by = 'Admin', "
                + "status = '2' "
                + "WHERE id = '" + pID + "'";
        DB.executeUpdate(CommonHanlder.dbName, tSQL);
    }

    public LinkedList<JSONObject> getModuleSyslistList() throws SQLException {
        String tSQL = "SELECT dp_module_syslist.id, dp_module_syslist.module_id, module_name, path, dp_module_syslist.`status` "
                + "FROM `dp_module_syslist` "
                + "INNER JOIN dp_module ON dp_module.id = dp_module_syslist.module_id "
                + "WHERE dp_module_syslist.`status` < 2 AND dp_module.`status` < 2 "
                + "ORDER BY module_name, path ASC";

        LinkedList<JSONObject> data = DB.executeQuery(CommonHanlder.dbName, tSQL);

        if (data != null && data.size() > 0) {
            return data;
        }

        return new LinkedList<>();
    }

    public void insertModuleSyslist(String pModuleID, String pPath) throws SQLException {
        String tSQL = "INSERT INTO "
                + "dp_module_syslist (created_at, created_by, module_id, path, status) "
                + "VALUES (NOW(), 'Admin', '" + pModuleID + "', '" + pPath + "', 1)";
        DB.executeUpdate(CommonHanlder.dbName, tSQL);
    }

    public void updateModuleSyslist(String pID, String pModuleID, String pPath, String pStatus) throws SQLException {
        String tSQL = "UPDATE dp_module_syslist SET "
                + "updated_at = NOW(), "
                + "updated_by = 'Admin', "
                + "module_id = '" + pModuleID + "', "
                + "path = '" + pPath + "', "
                + "status = '" + pStatus + "' "
                + "WHERE id = '" + pID + "'";
        DB.executeUpdate(CommonHanlder.dbName, tSQL);
    }

    public void deleteModuleSyslist(String pID) throws SQLException {
        String tSQL = "UPDATE dp_module_syslist SET "
                + "deleted_at = NOW(), "
                + "deleted_by = 'Admin', "
                + "status = '2' "
                + "WHERE id = '" + pID + "'";
        DB.executeUpdate(CommonHanlder.dbName, tSQL);
    }

    public LinkedList<JSONObject> getVoucherProviderList() throws SQLException {
        String tSQL = "SELECT "
                + "id, "
                + "provider_name, "
                + "`status` "
                + "FROM `dp_vcr_provider` "
                + "WHERE `status` < 2 "
                + "ORDER BY provider_name ASC";

        LinkedList<JSONObject> data = DB.executeQuery(CommonHanlder.dbName, tSQL);

        if (data != null && data.size() > 0) {
            return data;
        }

        return new LinkedList<>();
    }

    public void insertVoucherProviderlist(String pProviderName) throws SQLException {
        String tSQL = "INSERT INTO "
                + "dp_vcr_provider (created_at, created_by, provider_name, status) "
                + "VALUES (NOW(), 'Admin', '" + pProviderName + "', 1)";
        DB.executeUpdate(CommonHanlder.dbName, tSQL);
    }

    public void updateVoucherProviderlist(String pID, String pProviderName, String pStatus) throws SQLException {
        String tSQL = "UPDATE "
                + "dp_vcr_provider "
                + "SET updated_at = NOW(), "
                + "updated_by = 'Admin', "
                + "provider_name = '" + pProviderName + "', "
                + "status = '" + pStatus + "' "
                + "WHERE id = '" + pID + "'";
        DB.executeUpdate(CommonHanlder.dbName, tSQL);
    }

    public void deleteVoucherProviderlist(String pID) throws SQLException {
        String tSQL = "UPDATE "
                + "dp_vcr_provider "
                + "SET deleted_at = NOW(), "
                + "deleted_by = 'Admin', "
                + "status = '2' "
                + "WHERE id = '" + pID + "'";
        DB.executeUpdate(CommonHanlder.dbName, tSQL);
    }

    public LinkedList<JSONObject> getVoucherProviderPrefixList() throws SQLException {
        String tSQL = "SELECT "
                + "dp_vcr_provider_prefix.id, dp_vcr_provider.id  as provider_id, provider_name, prefix "
                + "FROM dp_vcr_provider_prefix "
                + "RIGHT JOIN `dp_vcr_provider` ON dp_vcr_provider_prefix.provider_id = dp_vcr_provider.id "
                + "WHERE dp_vcr_provider.`status` < 2 "
                + "ORDER BY provider_name ASC";

        LinkedList<JSONObject> data = DB.executeQuery(CommonHanlder.dbName, tSQL);

        if (data != null && data.size() > 0) {
            return data;
        }

        return new LinkedList<>();
    }

    public void insertVoucherProviderPrefixlist(String pProviderID, String pPrefix) throws SQLException {
        String tSQL = "INSERT INTO "
                + "dp_vcr_provider_prefix (created_at, created_by, provider_id, prefix, status) "
                + "VALUES (NOW(), 'Admin', '" + pProviderID + "', '" + pPrefix + "' , 1)";
        DB.executeUpdate(CommonHanlder.dbName, tSQL);
    }

    public void updateVoucherProviderPrefixlist(String pID, String pProviderID, String pPrefix) throws SQLException {
        String tSQL = "UPDATE "
                + "dp_vcr_provider_prefix "
                + "SET updated_at = NOW(), "
                + "updated_by = 'Admin', "
                + "provider_id = '" + pProviderID + "', "
                + "prefix = '" + pPrefix + "' "
                + "WHERE id = '" + pID + "'";
        DB.executeUpdate(CommonHanlder.dbName, tSQL);
    }

    public void deleteVoucherProviderPrefixlist(String pID) throws SQLException {
        String tSQL = "DELETE FROM "
                + "dp_vcr_provider_prefix "
                + "WHERE id = '" + pID + "'";
        DB.executeUpdate(CommonHanlder.dbName, tSQL);
    }

    public LinkedList<JSONObject> getMitraList() throws SQLException {
        String tSQL = "SELECT "
                + "amd.id, "
                + "CASE am.`status` WHEN 0 THEN 'Tidak Aktif' WHEN 1 THEN 'Aktif' ELSE 'Blok' END as status, "
                + "am.mitra_id as client_id, "
                + "amd.mitra_name, "
                + "amd.address, "
                + "amd.city, "
                + "amd.provincy, "
                + "amd.country, "
                + "amd.contact_person, "
                + "amd.phone, "
                + "amd.wa_number as handphone, "
                + "amd.email, "
                + "amd.tagline, "
                + "ama.mitra_access as client_key, "
                + "amc.user_id, "
                + "amca.cred_access as user_password "
                + "FROM ap_mitra am "
                + "INNER JOIN ap_mitra_detail amd ON amd.mitra_id = am.id "
                + "INNER JOIN ap_mitra_access ama ON ama.mitra_id = am.id "
                + "INNER JOIN ap_mitra_cred amc ON amc.mitra_id = am.id "
                + "INNER JOIN ap_mitra_cred_access amca ON amca.cred_id = amc.id "
                + "ORDER BY amd.mitra_name ASC";

        LinkedList<JSONObject> data = DB.executeQuery(CommonHanlder.dbName, tSQL);

        if (data != null && data.size() > 0) {
            return data;
        }

        return new LinkedList<>();
    }
}
