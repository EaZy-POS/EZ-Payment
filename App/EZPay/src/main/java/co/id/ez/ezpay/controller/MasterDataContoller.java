/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.controller;

import co.id.ez.database.DB;
import co.id.ez.database.entity.WhereField;
import co.id.ez.database.enums.Operator;
import co.id.ez.database.query.QueryBuilder;
import co.id.ez.database.query.QueryType;
import static co.id.ez.ezpay.abstracts.AbstractModule.dbName;
import co.id.ez.ezpay.app.App;
import co.id.ez.ezpay.interfaces.DataTable;
import co.id.ez.ezpay.model.data.master.JabatanModel;
import co.id.ez.ezpay.model.data.master.KaryawanModel;
import co.id.ez.ezpay.model.data.master.MPBillerModel;
import co.id.ez.ezpay.model.data.master.PDAMBillerModel;
import co.id.ez.ezpay.model.data.master.PrepaidDenomListModel;
import co.id.ez.ezpay.model.data.master.UserModel;
import co.id.ez.ezpay.model.data.master.VoucherProductModel;
import co.id.ez.ezpay.msg.data.CentralDataRequest;
import co.id.ez.system.core.etc.EncryptionService;
import com.json.JSONArray;
import com.json.JSONObject;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Lutfi
 */
public class MasterDataContoller {

    public String getQueryMultiPaymentBiller(String search) {
        String tSQL = "SELECT biller, biller_name, status "
                + "FROM `dp_mp_biller` ";

        if (search.length() > 0) {
            tSQL = tSQL.concat("WHERE biller LIKE '%" + search + "%' "
                    + "OR biller_name LIKE '%" + search + "%' ");
        }

        tSQL = tSQL.concat("ORDER BY biller_name ASC");

        return tSQL;
    }

    public LinkedList<DataTable> loadMultiPaymentBiller(String search) throws SQLException {
        String tSQL = getQueryMultiPaymentBiller(search);
        LinkedList<JSONObject> data = DB.executeQuery(dbName, tSQL);
        LinkedList<DataTable> result = new LinkedList<>();

        if (data != null && data.size() > 0) {
            int index = 0;
            for (JSONObject master : data) {
                int number = index + 1;
                String biller = master.get("biller").toString();
                String billerName = master.get("biller_name").toString();
                String status = master.getInt("status") == 1 ? "Aktif" : "Tidak Aktif";
                MPBillerModel model = new MPBillerModel(number, biller, billerName, status);
                result.add(model);
                index++;
            }
        }

        return result;
    }

    public String getQueryVoucherProductList(String search) {
        String tSQL = "SELECT "
                + "prd.voucher_id, prd.voucher_name, prd.price, prd.sale_price, "
                + "prov.provider_name, prd.product_type, "
                + "CASE prd.status WHEN 0 THEN 'Tidak Aktif' ELSE 'Aktif' END as status "
                + "FROM dp_vcr_product prd "
                + "INNER JOIN dp_vcr_provider prov ON prd.provider_id = prov.id "
                + "LEFT JOIN dp_vcr_provider_prefix pref on pref.provider_id = prov.id ";

        if (search.length() > 0) {
            tSQL = tSQL.concat("WHERE prd.voucher_id LIKE '%" + search + "%' OR ")
                    .concat("prd.voucher_name LIKE '%" + search + "%' OR ")
                    .concat("prov.provider_name LIKE '%" + search + "%' ");
        }

        tSQL = tSQL.concat("ORDER BY prd.voucher_id, prd.voucher_name ASC");
        return tSQL;
    }

    public LinkedList<DataTable> loadVoucherProductList(String search) throws SQLException {
        String tSQL = getQueryVoucherProductList(search);
        LinkedList<JSONObject> data = DB.executeQuery(dbName, tSQL);
        LinkedList<DataTable> result = new LinkedList<>();

        if (data != null && data.size() > 0) {
            int index = 0;
            for (JSONObject master : data) {
                int number = index + 1;
                String voucherid = master.get("voucher_id").toString();
                String voucher = master.get("voucher_name").toString();
                String provider = master.get("provider_name").toString();
                String type = master.has("product_type") ? master.get("product_type").toString() : "-";
                BigDecimal harga = new BigDecimal(master.get("price").toString());
                BigDecimal hargajual = new BigDecimal(master.get("sale_price").toString());
                String status = master.getString("status");
                VoucherProductModel model = new VoucherProductModel(
                        number,
                        voucherid,
                        voucher,
                        provider,
                        harga,
                        hargajual,
                        type,
                        status
                );
                result.add(model);
                index++;
            }
        }

        return result;
    }

    public LinkedList<DataTable> loadPDAMBillerList(String search) throws SQLException {
        String tSQL = "SELECT biller, biller_name, distric, "
                + "CASE status WHEN 0 THEN 'Tidak Aktif' ELSE 'Aktif' END as status "
                + "FROM `dp_pam_biller` ";

        if (search.length() > 0) {
            tSQL = tSQL.concat("WHERE biller LIKE '%" + search + "%' OR ")
                    .concat("biller_name LIKE '%" + search + "%' OR ")
                    .concat("distric LIKE '%" + search + "%' ");
        }

        tSQL = tSQL.concat("ORDER BY biller ASC");

        LinkedList<JSONObject> data = DB.executeQuery(dbName, tSQL);
        LinkedList<DataTable> result = new LinkedList<>();

        if (data != null && data.size() > 0) {
            int index = 0;
            for (JSONObject master : data) {
                int number = index + 1;
                String biller = master.get("biller").toString();
                String biller_name = master.has("biller_name") ? master.get("biller_name").toString() : "-";
                String distric = master.has("distric") ? master.get("distric").toString() : "-";
                String status = master.getString("status");
                PDAMBillerModel model = new PDAMBillerModel(number, biller, biller_name, distric, status);
                result.add(model);
                index++;
            }
        }

        return result;
    }

    public String getQueryPrepaidDenomList(String search){
        String tSQL = "SELECT denom, description, price_sale, "
                + "CASE status WHEN 0 THEN 'Tidak Aktif' ELSE 'Aktif' END as status "
                + " FROM `dp_pre_demon` ";

        if (search.length() > 0) {
            tSQL = tSQL.concat("WHERE description LIKE '%" + search + "%' ");
        }

        tSQL = tSQL.concat("ORDER BY denom ASC");
        return tSQL;
    }
    
    public LinkedList<DataTable> loadPrepaidDenomList(String search) throws SQLException {
        String tSQL = getQueryPrepaidDenomList(search);
        LinkedList<JSONObject> data = DB.executeQuery(dbName, tSQL);
        LinkedList<DataTable> result = new LinkedList<>();

        if (data != null && data.size() > 0) {
            int index = 0;
            for (JSONObject master : data) {
                int number = index + 1;
                BigDecimal denom = new BigDecimal(master.get("denom").toString());
                BigDecimal hargajual = new BigDecimal(master.get("price_sale").toString());
                String desc = master.get("description").toString();
                String status = master.getString("status");
                PrepaidDenomListModel model = new PrepaidDenomListModel(number, denom, desc, hargajual, status);
                result.add(model);
                index++;
            }
        }

        return result;
    }

    public void deletePDAMBiller() throws SQLException {
        String tSQL = "DELETE FROM dp_pam_biller";
        DB.executeUpdate(dbName, tSQL);
    }

    public List<String> getMappingExsistingData(CentralDataRequest.Type type) throws SQLException {
        List<String> resultMapping = new ArrayList<>();
        LinkedList<DataTable> data = null;
        switch (type) {
            case BILLER_PDAM:
                data = loadPDAMBillerList("");
                break;
            case BILLER_MP:
                data = loadMultiPaymentBiller("");
                break;
            case PRODUK_VOUCHER:
                data = loadVoucherProductList("");
                break;
            case DENOM_PREPAID:
                data = loadPrepaidDenomList("");
                break;
            default:
                break;
        }

        mappingData(resultMapping, data, type);
        return resultMapping;
    }

    private void mappingData(final List<String> maps, LinkedList<DataTable> data, CentralDataRequest.Type type) {
        if (data != null) {
            switch (type) {
                case BILLER_PDAM:
                    data.stream().map(dataTable -> (PDAMBillerModel) dataTable).forEachOrdered(model -> {
                        maps.add(model.getBiller());
                    });
                    break;

                case BILLER_MP:
                    data.stream().map(dataTable -> (MPBillerModel) dataTable).forEachOrdered(model -> {
                        maps.add(model.getBiller());
                    });
                    break;

                case PRODUK_VOUCHER:
                    data.stream().map(dataTable -> (VoucherProductModel) dataTable).forEachOrdered(model -> {
                        maps.add(model.getVoucherid());
                    });
                    break;

                case DENOM_PREPAID:
                    data.stream().map(dataTable -> (PrepaidDenomListModel) dataTable).forEachOrdered(model -> {
                        maps.add(model.getDenom().toPlainString());
                    });
                    break;

                default:
                    break;
            }
        }
    }

    public void insertPDAMBiller(JSONObject data) throws SQLException {
        String biller = data.getString("biller");
        String biller_name = data.getString("biller_name");
        String distric = data.getString("distric");
        String active = data.getBoolean("active") ? "1" : "0";

        String tSQL = "INSERT INTO dp_pam_biller "
                + "(created_at, biller, biller_name, distric, status) "
                + "VALUES (NOW(), '" + biller + "', '" + biller_name + "', '" + distric + "', " + active + ")";
        DB.executeUpdate(dbName, tSQL);
    }

    public void updatePDAMBiller(JSONObject data) throws SQLException {
        String biller = data.getString("biller");
        String biller_name = data.getString("biller_name");
        String distric = data.getString("distric");
        String active = data.getBoolean("active") ? "1" : "0";

        String tSQL = "UPDATE dp_pam_biller SET "
                + "updated_at = NOW(), "
                + "biller = '" + biller + "', "
                + "biller_name = '" + biller_name + "', "
                + "distric = '" + distric + "', "
                + "status = " + active + " "
                + "WHERE biller = '" + biller + "'";
        DB.executeUpdate(dbName, tSQL);
    }

    public void insertPrepaidDenom(JSONObject data) throws SQLException {
        String denom = data.get("denom").toString();
        String price = data.get("price").toString();
        String active = data.getBoolean("active") ? "1" : "0";
        String tDesc = data.getString("desc");

        String tSQL = "INSERT INTO dp_pre_demon "
                + "(created_at, denom, description, price_sale,status) "
                + "VALUES (NOW(), '" + denom + "', '" + tDesc + "', '" + price + "', " + active + ")";
        DB.executeUpdate(dbName, tSQL);
    }

    public void updatePrepaidDenom(JSONObject data) throws SQLException {
        String denom = data.get("denom").toString();
        String price = data.get("price").toString();
        String active = data.getBoolean("active") ? "1" : "0";
        String tDesc = data.getString("desc");

        String tSQL = "UPDATE dp_pre_demon SET "
                + "updated_at = NOW(), "
                + "denom = '" + denom + "', "
                + "price_sale = '" + price + "', "
                + "description = '" + tDesc + "', "
                + "status = " + active + " "
                + "WHERE denom = '" + denom + "'";
        DB.executeUpdate(dbName, tSQL);
    }

    public void insertMPBiller(JSONObject data) throws SQLException {
        String biller = data.get("biller").toString();
        String active = data.getBoolean("active") ? "1" : "0";
        String billername = data.getString("biller_name");
        String detail = "";
        JSONArray details = data.getJSONArray("details");

        for (int j = 0; j < details.length(); j++) {
            detail = detail
                    .concat((j == 0 ? "[" : ";["))
                    .concat(details.getString(j))
                    .concat("]");
        }

        QueryBuilder builder = new QueryBuilder("dp_mp_biller", QueryType.INSERT);
        builder.addEntryValue("created_at", "NOW()", true);
        builder.addEntryValue("biller", biller);
        builder.addEntryValue("biller_name", billername);
        builder.addEntryValue("status", active);

        JSONArray input = data.getJSONArray("input");

        for (int i = 0; i < input.length(); i++) {
            JSONObject dataInput = input.getJSONObject(i);
            String label = dataInput.getString("label");
            JSONObject type = new JSONObject(dataInput.get("type").toString());
            builder.addEntryValue("input_" + (i + 1) + "_label", label);
            builder.addEntryValue("input_" + (i + 1) + "_type", type.toString());
        }

        DB.executeUpdate(dbName, builder);
    }

    public void updateMPBiller(JSONObject data) throws SQLException {
        String biller = data.get("biller").toString();
        String active = data.getBoolean("active") ? "1" : "0";
        String billername = data.getString("biller_name");
        String detail = "";
        JSONArray details = data.getJSONArray("details");

        for (int j = 0; j < details.length(); j++) {
            detail = detail
                    .concat((j == 0 ? "[" : ";["))
                    .concat(details.getString(j))
                    .concat("]");
        }

        QueryBuilder builder = new QueryBuilder("dp_mp_biller", QueryType.UPDATE);
        builder.addEntryValue("updated_at", "NOW()", true);
        builder.addEntryValue("biller", biller);
        builder.addEntryValue("biller_name", billername);
        builder.addEntryValue("status", active);

        JSONArray input = data.getJSONArray("input");

        for (int i = 0; i < input.length(); i++) {
            JSONObject dataInput = input.getJSONObject(i);
            String label = dataInput.getString("label");
            JSONObject type = new JSONObject(dataInput.get("type").toString());
            builder.addEntryValue("input_" + (i + 1) + "_label", label);
            builder.addEntryValue("input_" + (i + 1) + "_type", type.toString());
        }

        builder.addWhereValue(new WhereField("biller", biller, Operator.EQUALS));

        DB.executeUpdate(dbName, builder);
    }

    public void insertVoucjerProduk(List<String> exsData, JSONObject data) throws SQLException {
        String provider = data.getString("provider");
        String status = data.getBoolean("active") ? "1" : "0";
        String tProvID = getProviderID(provider, status);

        JSONArray tPrevix = data.getJSONArray("prefix");
        insertVoucherPrefix(tPrevix, tProvID);

        JSONArray voucher = data.getJSONArray("voucher");
        for (int i = 0; i < voucher.length(); i++) {
            JSONObject dataVoucher = voucher.getJSONObject(i);
            insertUpdateVoucherProduct(tProvID, dataVoucher);
        }
    }

    public String getProviderID(String provider, String active) throws SQLException {
        String tSQL = "SELECT IFNULL((SELECT id FROM dp_vcr_provider "
                + "WHERE provider_name = '" + provider + "'),0) as provideid";

        LinkedList<JSONObject> data = DB.executeQuery(dbName, tSQL);
        String id = "0";
        if (data != null && data.size() > 0) {
            id = data.getFirst().get("provideid").toString();
        }

        if (id.equals("0")) {
            tSQL = "INSERT INTO dp_vcr_provider (created_at, provider_name, status)"
                    + "VALUES (NOW(), '" + provider + "', " + active + ")";
            DB.executeUpdate(dbName, tSQL);
            return getProviderID(provider, active);
        } else {
            tSQL = "UPDATE dp_vcr_provider SET "
                    + "updated_at = NOW(), "
                    + "provider_name = '" + provider + "', "
                    + "status = '" + active + "' "
                    + "WHERE id = '" + id + "'";
            DB.executeUpdate(dbName, tSQL);
        }

        return id;
    }

    public void insertVoucherPrefix(JSONArray data, String id) throws SQLException {
        String tSQL = "DELETE FROM dp_vcr_provider_prefix WHERE provider_id = '" + id + "'";
        DB.executeUpdate(dbName, tSQL);

        for (int i = 0; i < data.length(); i++) {
            String prefix = data.getString(i);
            tSQL = "INSERT INTO dp_vcr_provider_prefix (created_at, provider_id, prefix, status) "
                    + "VALUES (NOW(), '" + id + "', '" + prefix + "', '1')";
            DB.executeUpdate(dbName, tSQL);
        }
    }

    public void insertUpdateVoucherProduct(String providerid, JSONObject dataVoucher) throws SQLException {
        String tVOucherID = dataVoucher.getString("voucher_id");
        String tVOucherName = dataVoucher.getString("voucher_name");
        String tProductType = dataVoucher.getString("product_type");
        String tStatus = dataVoucher.getBoolean("active") ? "1" : "0";
        BigDecimal tPrice = new BigDecimal(dataVoucher.getDouble("price"));

        String tSQL = "SELECT IFNULL((SELECT id FROM dp_vcr_product "
                + "WHERE voucher_id = '" + tVOucherID + "' AND provider_id= '" + providerid + "'),0) as prodid";

        LinkedList<JSONObject> data = DB.executeQuery(dbName, tSQL);
        String id = "0";
        if (data != null && data.size() > 0) {
            id = data.getFirst().get("prodid").toString();
        }

        if (id.equals("0")) {
            tSQL = "INSERT INTO dp_vcr_product (created_at, voucher_id, voucher_name, price, sale_price, provider_id, product_type, status)"
                    + "VALUES (NOW(), "
                    + "'" + tVOucherID + "', "
                    + "'" + tVOucherName + "', "
                    + "'" + tPrice.toPlainString() + "', "
                    + "'" + tPrice.toPlainString() + "', "
                    + "'" + providerid + "', "
                    + "'" + tProductType + "', "
                    + tStatus + ")";
            DB.executeUpdate(dbName, tSQL);
        } else {
            tSQL = "UPDATE dp_vcr_product SET "
                    + "updated_at = NOW(), "
                    + "voucher_id = '" + tVOucherID + "', "
                    + "voucher_name = '" + tVOucherName + "', "
                    + "price = '" + tPrice.toPlainString() + "', "
                    + "product_type = '" + tProductType + "', "
                    + "status = '" + tStatus + "' "
                    + "WHERE id = '" + id + "'";
            DB.executeUpdate(dbName, tSQL);
        }
    }

    public LinkedList<DataTable> loadDataJabatan(String search) throws SQLException {
        String tSQL = "SELECT id, position, access "
                + "FROM mst_employes_position ";

        if (search.length() > 0) {
            tSQL = tSQL.concat("WHERE position LIKE '%" + search + "%' ");
        }

        tSQL = tSQL.concat("ORDER BY position ASC");

        LinkedList<JSONObject> data = DB.executeQuery(dbName, tSQL);
        LinkedList<DataTable> result = new LinkedList<>();

        if (data != null && data.size() > 0) {
            int index = 0;
            for (JSONObject master : data) {
                int number = index + 1;
                String id = master.get("id").toString();
                String jabatan = master.get("position").toString();
                String access = master.get("access").toString();

                JabatanModel model = new JabatanModel(number, id, jabatan, access);
                result.add(model);
                index++;
            }
        }

        return result;
    }

    public void deleteDataJabatan(String id) throws SQLException {
        String tSQL = "DELETE FROM mst_employes_position WHERE id = '" + id + "'";
        DB.executeUpdate(dbName, tSQL);
    }

    public void insertDataJabatan(String jabatan, String access) throws SQLException {
        String tSQL = "INSERT INTO "
                + "mst_employes_position (created_at, position, access) "
                + "VALUES (NOW(), '" + jabatan + "', '" + access + "')";
        DB.executeUpdate(dbName, tSQL);
    }

    public void updateDataJabatan(String id, String jabatan, String access) throws SQLException {
        String tSQL = "UPDATE "
                + "mst_employes_position "
                + "SET updated_at = NOW(), "
                + "position = '" + jabatan + "', access = '" + access + "' "
                + "WHERE id = '" + id + "'";
        DB.executeUpdate(dbName, tSQL);
    }

    public LinkedList<DataTable> loadDataKaryawan(String search) throws SQLException {
        return loadDataKaryawan(search, false);
    }

    public String getQueryGetDataKaryawan(String search, boolean isSpecific){
        String tSQL = "SELECT mst_employes.id, employe_id, name, address, "
                + "phone, email, mst_employes_position.position, "
                + "CASE `status` WHEN 0 THEN 'Tidak Aktif' ELSE 'Aktif' END as status "
                + "FROM mst_employes "
                + "JOIN mst_employes_position ON mst_employes_position.id = mst_employes.position ";

        if (search.length() > 0) {
            if (isSpecific) {
                tSQL = tSQL.concat("WHERE (" + search + ") AND status < 2 ");
            } else {
                tSQL = tSQL.concat("WHERE (employe_id LIKE '%" + search + "%' "
                        + "OR name LIKE '%" + search + "%' "
                        + "OR address LIKE '%" + search + "%' "
                        + "OR phone LIKE '%" + search + "%' "
                        + "OR email LIKE '%" + search + "%' "
                        + "OR mst_employes_position.position LIKE '%" + search + "%') "
                        + "AND status < 2 ");
            }
        } else {
            tSQL = tSQL.concat("WHERE status < 2 ");
        }

        tSQL = tSQL.concat("ORDER BY employe_id, name, mst_employes.id ASC");
        
        return tSQL;
    }
    
    public LinkedList<DataTable> loadDataKaryawan(String search, boolean isSpecific) throws SQLException {
        String tSQL = getQueryGetDataKaryawan(search, isSpecific);
        LinkedList<JSONObject> data = DB.executeQuery(dbName, tSQL);
        LinkedList<DataTable> result = new LinkedList<>();

        if (data != null && data.size() > 0) {
            int index = 0;
            for (JSONObject master : data) {
                int number = index + 1;
                String id = master.get("id").toString();
                String nip = master.get("employe_id").toString();
                String nama = master.get("name").toString();
                String alamat = master.get("address").toString();
                String phone = master.get("phone").toString();
                String jabatan = master.get("position").toString();
                String email = master.get("email").toString();
                String status = master.getString("status");

                KaryawanModel model = new KaryawanModel(number, id, nip, nama, alamat, phone, email, jabatan, status);
                result.add(model);
                index++;
            }
        }

        return result;
    }

    public LinkedList<KaryawanModel> loadDataKaryawanNotHaveUser() throws SQLException {
        String tSQL = "SELECT mst_employes.id, employe_id, `name` FROM mst_employes "
                + "INNER JOIN mst_employes_position on mst_employes.position = mst_employes_position.id "
                + "LEFT JOIN ap_user on ap_user.emp_id = mst_employes.id ";
        tSQL = tSQL.concat("WHERE ISNULL(user_name) AND mst_employes.status < 2 ");

        tSQL = tSQL.concat("ORDER BY mst_employes.id, name ASC");

        LinkedList<JSONObject> data = DB.executeQuery(dbName, tSQL);
        LinkedList<KaryawanModel> result = new LinkedList<>();

        if (data != null && data.size() > 0) {
            int index = 0;
            for (JSONObject master : data) {
                int number = index + 1;
                String id = master.get("id").toString();
                String nip = master.get("employe_id").toString();
                String nama = master.get("name").toString();

                KaryawanModel model = new KaryawanModel(number, id, nip, nama, "", "", "", "", "");
                result.add(model);
                index++;
            }
        }

        return result;
    }

    public void deleteDataKaryawan(String id) throws SQLException {
        String tSQL = "UPDATE mst_employes "
                + "SET status = 2, deleted_at = NOW() "
                + "WHERE id = '" + id + "'";
        DB.executeUpdate(dbName, tSQL);
    }

    public void insertDataKaryawan(
            String nip,
            String nama,
            String alamat,
            String phone,
            String email,
            String jabatan) throws SQLException {
        String tSQL = "INSERT INTO "
                + "mst_employes (created_at, employe_id, name, address, phone, email, position) "
                + "VALUES (NOW(), '" + nip + "', '" + nama + "', '" + alamat
                + "', '" + phone + "', '" + email + "', '" + jabatan + "' )";
        DB.executeUpdate(dbName, tSQL);
    }

    public void updateDataKaryawan(
            String id,
            String nip,
            String nama,
            String alamat,
            String phone,
            String email,
            String jabatan,
            String status) throws SQLException {

        String tSQL = "UPDATE "
                + "mst_employes "
                + "SET updated_at = NOW(), "
                + "employe_id = '" + nip + "', "
                + "name = '" + nama + "', "
                + "address = '" + alamat + "', "
                + "phone= '" + phone + "', "
                + "email= '" + email + "', "
                + "position = '" + jabatan + "', "
                + "status = '" + status + "' "
                + "WHERE id = '" + id + "'";

        DB.executeUpdate(dbName, tSQL);
    }

    public LinkedList<DataTable> loadDataUser(String search) throws SQLException {
        String tSQL = "SELECT "
                + "ap_user.id, "
                + "user_name, "
                + "`password`, "
                + "`name`, "
                + "ap_user.`status`, "
                + "employe_id, "
                + "mst_employes_position.position "
                + "FROM ap_user "
                + "JOIN mst_employes on ap_user.emp_id = mst_employes.id "
                + "JOIN mst_employes_position on mst_employes.position = mst_employes_position.id ";

        if (search.length() > 0) {
            tSQL = tSQL.concat("WHERE (user_name LIKE '%" + search + "%' "
                    + "OR name LIKE '%" + search + "%' "
                    + "OR mst_employes_position.position LIKE '%" + search + "%') ");
        }

        tSQL = tSQL.concat("ORDER BY user_name ASC ");

        LinkedList<JSONObject> data = DB.executeQuery(dbName, tSQL);
        LinkedList<DataTable> result = new LinkedList<>();

        if (data != null && data.size() > 0) {
            int index = 0;
            for (JSONObject master : data) {
                int number = index + 1;
                String id = master.get("id").toString();
                String nip = master.get("employe_id").toString();
                String nama = master.get("name").toString();
                String username = master.get("user_name").toString();
                String password = master.get("password").toString();
                String jabatan = master.get("position").toString();
                String status = master.getInt("status") == 1 ? "Aktif" : "Tidak Aktif";

                UserModel model = new UserModel(number, id, username, nip, nama, password, jabatan, status);
                result.add(model);
                index++;
            }
        }

        return result;
    }

    public void deleteDataUser(String id) throws SQLException {
        String tSQL = "DELETE FROM ap_user WHERE id = '" + id + "'";
        DB.executeUpdate(dbName, tSQL);
    }

    public void insertDataUser(
            String userid,
            String empid,
            String password) throws SQLException {

        String tEncUser = EncryptionService
                .encryptor().Base64Encrypt(
                        EncryptionService.encryptor()
                                .encrypt(userid, App.environment().getEnv_key())
                );

        String tEncPassword = EncryptionService
                .encryptor().Base64Encrypt(
                        EncryptionService.encryptor()
                                .encrypt(
                                        password,
                                        "#".concat(App.environment().getEnv_key().concat("key"))
                                )
                );

        String tSQL = "INSERT INTO "
                + "ap_user (created_at, user_name, password, emp_id) "
                + "VALUES (NOW(), '" + tEncUser + "', '" + tEncPassword + "', '" + empid + "' )";
        DB.executeUpdate(dbName, tSQL);
    }

    public void updateDataUser(
            String id,
            String userid,
            String password,
            String status) throws SQLException {

        String tEncUser = EncryptionService
                .encryptor().Base64Encrypt(
                        EncryptionService.encryptor()
                                .encrypt(userid, App.environment().getEnv_key())
                );

        String tEncPassword = EncryptionService
                .encryptor().Base64Encrypt(
                        EncryptionService.encryptor()
                                .encrypt(
                                        password,
                                        "#".concat(App.environment().getEnv_key().concat("key"))
                                )
                );

        String tSQL = "UPDATE "
                + "ap_user "
                + "SET updated_at = NOW(), "
                + "user_name = '" + tEncUser + "', "
                + "password = '" + tEncPassword + "', "
                + "status = '" + status + "' "
                + "WHERE id = '" + id + "'";

        DB.executeUpdate(dbName, tSQL);
    }

    public JSONObject getDataUsers(String pUserID, String pPassword) throws SQLException {
        String tEncUser = EncryptionService
                .encryptor().Base64Encrypt(
                        EncryptionService.encryptor()
                                .encrypt(pUserID, App.environment().getEnv_key())
                );

        String tEncPassword = EncryptionService
                .encryptor().Base64Encrypt(
                        EncryptionService.encryptor()
                                .encrypt(
                                        pPassword,
                                        "#".concat(App.environment().getEnv_key().concat("key"))
                                )
                );

        String tSQL = "SELECT "
                + "mst_employes.id, "
                + "mst_employes.employe_id, "
                + "name, address, "
                + "email, "
                + "phone, "
                + "mst_employes_position.position, "
                + "mst_employes_position.access, "
                + "mst_employes.`status` as emp_status, "
                + "ap_user.`status` as user_status "
                + "FROM `ap_user` "
                + "JOIN mst_employes ON ap_user.emp_id = mst_employes.id "
                + "JOIN mst_employes_position ON mst_employes_position.id = mst_employes.position "
                + "WHERE user_name = '" + tEncUser + "' AND `password` = '" + tEncPassword + "' "
                + "ORDER BY ap_user.id ASC LIMIT 1";

        LinkedList<JSONObject> data = DB.executeQuery(dbName, tSQL);
        if (data != null && data.size() > 0) {
            return data.getFirst();
        }

        return null;
    }

    public boolean isExistUsers(String pUserID, String pOthesCOnd) throws SQLException {
        String tEncUser = EncryptionService
                .encryptor().Base64Encrypt(
                        EncryptionService.encryptor()
                                .encrypt(pUserID, App.environment().getEnv_key())
                );

        String tSQL = "SELECT "
                + "mst_employes.id, "
                + "mst_employes.employe_id, "
                + "name, address, "
                + "email, "
                + "phone, "
                + "mst_employes_position.position, "
                + "mst_employes_position.access, "
                + "mst_employes.`status` as emp_status, "
                + "ap_user.`status` as user_status "
                + "FROM `ap_user` "
                + "JOIN mst_employes ON ap_user.emp_id = mst_employes.id "
                + "JOIN mst_employes_position ON mst_employes_position.id = mst_employes.position "
                + "WHERE user_name = '" + tEncUser + "' " + pOthesCOnd
                + "ORDER BY ap_user.id ASC LIMIT 1";

        LinkedList<JSONObject> data = DB.executeQuery(dbName, tSQL);

        return data != null && data.size() > 0;
    }

    public JSONObject getMarginSales() throws SQLException {
        String tSQL = "SELECT * FROM ap_margin_sales ORDER BY created_at DESC LIMIT 1";

        LinkedList<JSONObject> data = DB.executeQuery(dbName, tSQL);
        if (data != null && data.size() > 0) {
            return data.getFirst();
        }

        return null;
    }

    public void insertMarginSales(String pType, String pValue, String pRoundType, String pMin, String pMax) throws SQLException {
        String tQuery = "TRUNCATE ap_margin_sales";
        DB.executeUpdate(dbName, tQuery);

        String tSQL = "INSERT INTO ap_margin_sales (created_at, type, value, round_type, margin_min, margin_max)"
                + " VALUES (NOW(), '" + pType + "', '" + pValue + "', '" + pRoundType + "', '" + pMin + "', '" + pMax + "')";

        DB.executeUpdate(dbName, tSQL);
    }

    public void updateVoucherPriceSales(String pVcrID, BigDecimal pPriceSale) throws SQLException {
        QueryBuilder builder = new QueryBuilder("dp_vcr_product", QueryType.UPDATE);
        builder.addEntryValue("sale_price", pPriceSale.toPlainString());
        builder.addWhereValue(new WhereField("voucher_id", pVcrID, Operator.EQUALS));

        DB.executeUpdate(dbName, builder);
    }

    public void updatePrepaidPriceSales(String pDenom, BigDecimal pPriceSale) throws SQLException {
        QueryBuilder builder = new QueryBuilder("dp_pre_demon", QueryType.UPDATE);
        builder.addEntryValue("price_sale", pPriceSale.toPlainString());
        builder.addWhereValue(new WhereField("denom", pDenom, Operator.EQUALS));

        DB.executeUpdate(dbName, builder);
    }
}
