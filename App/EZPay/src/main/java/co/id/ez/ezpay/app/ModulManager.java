/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.app;

import co.id.ez.ezpay.model.data.master.AppModulModel;
import co.id.ez.ezpay.model.data.master.AppSubModulModel;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author RCS
 */
public class ModulManager {

    private final List<AppModulModel> listModul = new ArrayList<>();

    public ModulManager() {
        setModulList();
    }

    private void setModulList() {
        createModule("master_data", "master_jabatan:true", "master_employes:true");
        createModule("produk_biller", "tarik_data", "pdam_biller", "mp_biller", "vcr_produk", "pre_denom", "ubah_harga");
        createModule("mitra", "profil_mitra", "api_access");
        createModule("deposit", "saldo", "mutasi");
        createModule("pengguna", "profil_pengguna", "rubah_password_saya");
        createModule("alat", "aktifasi", "integrasi", "cek_update");
        createModule("manajemen_user", "data_user:true", "rubah_password_user");
        createModule("pengaturan", "setting_aplikasi");
        createModule("transaksi", "transaksi", "cek_status", "cetak_ulang", "rekap_harian", "rekap_bulanan");
        createModule("modul", "voucher", "pln", "prepaid", "pdam", "mp");
    }

    private void createModule(String module, String... subModule) {
        AppModulModel modul = new AppModulModel(module);

        for (String submodul : subModule) {
            String subModuleName;
            boolean isMasterData = false;

            if (submodul.contains(":")) {
                String[] subModuls = submodul.split(":");
                subModuleName = subModuls[0].trim();
                isMasterData = subModuls[1].trim().equalsIgnoreCase("true");
            } else {
                subModuleName = submodul;
            }

            if (isMasterData) {
                modul.addSubModul(new AppSubModulModel(subModuleName, true));
            } else {
                modul.addSubModul(new AppSubModulModel(subModuleName));
            }
        }

        listModul.add(modul);
    }

    public List<AppModulModel> getListModul() {
        return listModul;
    }

    public void reset() {
        listModul.forEach(appModulModel -> {
            appModulModel.reset();
        });
    }

    public void resetSubmModule() {
        listModul.forEach(appModulModel -> {
            appModulModel.resetSubModule();
        });
    }

    public void grantAllAccess() {
        listModul.forEach(appModulModel -> {
            appModulModel.setSelected(true);
            appModulModel.grantAllSubModule();
        });
    }

    public String getAccessString() {
        String tResult = "";

        int index = 0;
        for (AppModulModel appModulModel : listModul) {
            if (appModulModel.isCheck()) {
                tResult = tResult.concat(index == 0 ? "" : ";").concat(appModulModel.getModule().trim()).concat("=");

                String tSubModul = "";

                int subindex = 0;
                for (AppSubModulModel appSubModulModel : appModulModel.getSubModulList()) {
                    if (appSubModulModel.isCheck()) {
                        tSubModul = tSubModul.concat(subindex == 0 ? "" : ",").concat(appSubModulModel.getModule()).concat(":");

                        if (appSubModulModel.isMasterData()) {
                            tSubModul = tSubModul.concat(appSubModulModel.getCrudAccess().getCrudAccessString());

                        } else {
                            tSubModul = tSubModul.concat("{*}");
                        }

                        subindex++;
                    }
                }

                tResult = tResult.concat("[").concat(tSubModul).concat("]");
                index++;
            }
        }

        return tResult;
    }

    public void parseAccess(String access) {
        reset();
        resetSubmModule();
        if (access != null) {
            if (access.equalsIgnoreCase("*/*")) {
                grantAllAccess();
            } else {
                String[] dataAccess = access.split(";");
                for (String modulAccess : dataAccess) {
                    String[] detailAccess = modulAccess.split("=");
                    for (int i = 0; i < listModul.size(); i++) {
                        if (listModul.get(i).getModule()
                                .equalsIgnoreCase(detailAccess[0].trim())) {
                            listModul.get(i).setSelected(true);

                            String[] submodelaccess = detailAccess[1].trim()
                                    .replace("[", "").replace("]", "").split(",");

                            for (String submodelacces : submodelaccess) {
                                for (int j = 0; j < listModul.get(i).getSubModulList().size(); j++) {

                                    String[] detailsubmodelaccess = submodelacces.trim().split(":");

                                    if (listModul.get(i).getSubModulList().get(j).getModule()
                                            .equalsIgnoreCase(detailsubmodelaccess[0].trim())) {

                                        listModul.get(i).getSubModulList().get(j).setSelected(true);

                                        if (listModul.get(i).getSubModulList().get(j).isMasterData()) {

                                            String[] crudaccess = detailsubmodelaccess[1].trim()
                                                    .replace("{", "").replace("}", "").split("\\|");

                                            for (String crudacces : crudaccess) {
                                                if (crudacces.equalsIgnoreCase("create")) {
                                                    listModul.get(i).getSubModulList().get(j)
                                                            .setCanWrite(true);
                                                }

                                                if (crudacces.equalsIgnoreCase("update")) {
                                                    listModul.get(i).getSubModulList().get(j)
                                                            .setCanUpdate(true);
                                                }

                                                if (crudacces.equalsIgnoreCase("delete")) {
                                                    listModul.get(i).getSubModulList().get(j)
                                                            .setCanDelete(true);
                                                }
                                            }
                                        }
                                        break;
                                    }
                                }
                            }
                            break;
                        }
                    }
                }
            }
        }
    }

    public boolean isHasAccessModule(String... modul){
        if (modul.length > 0) {
            String mod = modul[0];
            AppModulModel access = null;
            for (AppModulModel appModulModel : listModul) {
                if (appModulModel.getModule().equals(mod)) {
                    access = appModulModel;
                    break;
                }
            }
            
            
            if(modul.length > 1 && access != null){
                mod = modul[1];
                AppSubModulModel subaccess = null;
                for (AppSubModulModel appSubModulModel : access.getSubModulList()) {
                    if(appSubModulModel.getModule().equals(mod)){
                        subaccess = appSubModulModel;
                        break;
                    }
                }
                
                if(modul.length > 2 && subaccess != null){
                    mod = modul[2];
                    switch(mod){
                        case "create":
                            return subaccess.getCrudAccess().isCanWrite();
                        case "update":
                            return subaccess.getCrudAccess().isCanUpdate();
                        case "delete":
                            return subaccess.getCrudAccess().isCanDelete();
                    }
                }
                
                return subaccess != null;
            }
        }
        return false;
    }
    
    private static class Holder {
        private static final ModulManager instance = new ModulManager();
    }

    public static ModulManager instance() {
        return Holder.instance;
    }
}
