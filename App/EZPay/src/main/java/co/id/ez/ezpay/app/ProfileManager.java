/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.app;

import co.id.ez.ezpay.msg.BillerResponse;
import co.id.ez.ezpay.msg.profile.GetProfile;
import co.id.ez.ezpay.view.settings.ProfileView;
import com.json.JSONObject;
import java.io.File;
import java.util.HashMap;

/**
 *
 * @author RCS
 */
public class ProfileManager {

    private String provincy,
            country,
            address,
            wa_number,
            city,
            mitra_name,
            email;

    private boolean isReady;

    public String getProvincy() {
        return provincy;
    }

    public String getAddress() {
        return address;
    }

    public String getWa_number() {
        return wa_number;
    }

    public String getCity() {
        return city;
    }

    public String getMitra_name() {
        return mitra_name;
    }

    public String getEmail() {
        return email;
    }

    public boolean isReady() {
        return isReady;
    }

    public String getCountry() {
        return country;
    }

    public void loadProfile() {
        try {
            BillerResponse resp = new ProfileView(true).sendRequest(new GetProfile(), "GET", true);
            if (resp != null) {
                JSONObject respObj = resp.getPayload();
                JSONObject detail = new JSONObject(respObj.get("detail").toString());

                this.mitra_name = detail.getString("mitra_name");
                this.address = detail.getString("address");
                this.city = detail.getString("city");
                this.provincy = detail.getString("provincy");
                this.country = detail.getString("country");
                this.email = detail.getString("email");
                this.wa_number = detail.getString("wa_number");
                this.isReady = true;
            }
        } catch (Exception e) {
            Common.showErrorMessage("Failed load profile!", null);
            Common.errorLog("[Exception] Failed load profile", e);
            System.exit(0);
        }
    }

    public HashMap<String, Object> getProfileMaps() {
        HashMap<String, Object> maps = new HashMap<>();
        maps.put("nama", mitra_name);
        maps.put("alamat", address);
        maps.put("kota", city + ", " + provincy);
        maps.put("kontak", "Telp. " + wa_number + ", Email: " + email);
        String tLogo = ProfileSetting.instance().getString("logo");
        File fileLogo = null;
        if (tLogo != null) {
            fileLogo = new File(tLogo);
        }
        maps.put("logo", fileLogo);

        return maps;
    }

    private static class Holder {

        public static ProfileManager session = new ProfileManager();
    }

    public static ProfileManager instance() {
        return Holder.session;
    }
}
