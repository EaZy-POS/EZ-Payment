/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.msg.registration;

import co.id.ez.ezpay.enums.RequestType;
import co.id.ez.ezpay.msg.BillerMessage;
import com.json.JSONObject;

/**
 *
 * @author Lutfi
 */
public class RegistrationRequest extends BillerMessage{

    private String user_id,
            password,
            mitra_name,
            address,
            city,
            province,
            country,
            contact_person,
            phone,
            wa_number,
            email,
            tagline;
    
    public RegistrationRequest() {
        super(RequestType.REGISTRASI, "");
    }

    @Override
    public JSONObject getBodyRequest(){
        JSONObject req = new JSONObject();
        req.put("user_id", user_id)
                .put("password", password)
                .put("mitra_name", mitra_name)
                .put("address", address)
                .put("city", city)
                .put("province", province)
                .put("country", country)
                .put("contact_person", contact_person)
                .put("phone", phone)
                .put("wa_number", wa_number)
                .put("email", email)
                .put("tagline", tagline);
        return req;
    }

    @Override
    public String getPath() {
        return "/api/mitra/registration";
    }

    @Override
    public String getCommand() {
        return null;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setMitra_name(String mitra_name) {
        this.mitra_name = mitra_name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setContact_person(String contact_person) {
        this.contact_person = contact_person;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setWa_number(String wa_number) {
        this.wa_number = wa_number;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getPassword() {
        return password;
    }

    public String getMitra_name() {
        return mitra_name;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getProvince() {
        return province;
    }

    public String getCountry() {
        return country;
    }

    public String getContact_person() {
        return contact_person;
    }

    public String getPhone() {
        return phone;
    }

    public String getWa_number() {
        return wa_number;
    }

    public String getEmail() {
        return email;
    }

    public String getTagline() {
        return tagline;
    }

    @Override
    public boolean isNeedHeader() {
        return false;
    }

}
