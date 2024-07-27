/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.msg.data.mitra;

import co.id.ez.ezpay.msg.data.CentralDataRequest;
import com.json.JSONObject;

/**
 *
 * @author Lutfi
 */
public class UpdateMitra extends CentralDataRequest{
    
    private String id, 
            client_id, 
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
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
    public String getPath() {
        return "/api/mitra/update";
    }

    @Override
    public Type getType() {
        return Type.AKTIFASI_MITRA;
    }

    @Override
    public JSONObject getBodyRequest() {
        JSONObject req = super.getBodyRequest();
        req.put("client_id", client_id)
                .put("mitra_name", mitra_name)
                .put("address", address)
                .put("city", city)
                .put("province", province)
                .put("country", country)
                .put("contact_person", contact_person)
                .put("phone", phone)
                .put("wa_number", wa_number)
                .put("email", email)
                .put("tagline", tagline)
                .put("coutry", country)
                .put("id", id);
        return req;
    }
    
}
