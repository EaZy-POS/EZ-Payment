/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.app;

import co.id.ez.ezpay.controller.MasterDataContoller;
import co.id.ez.ezpay.enums.MessageType;
import com.json.JSONException;
import com.json.JSONObject;
import java.sql.SQLException;

/**
 *
 * @author RCS
 */
public class SessionManager {
    
    private final MasterDataContoller controller = new MasterDataContoller();
    private final ModulManager modulManager = new ModulManager();
    private boolean isSuperadmin;
    
    private String userName, password, id, name, address, phone, email, position, access, nip;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
        this.modulManager.parseAccess(access);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public boolean isActive(){
        return userName != null;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public boolean isSuperadmin() {
        return isSuperadmin;
    }
    
    public void destroy(){
        setUserName(null);
        setPassword(null);
    }
    
    public boolean createSesion(String pUserID, String pPassword){
        try {
            isSuperadmin = App.environment().isSuperAdmin(pUserID, pPassword);
            if (isSuperadmin ) {
                setUserName("Super Admin");
                setName("Super Admin");
                setAccess("*/*");
                return true;
            } else {
                JSONObject dataUser = controller.getDataUsers(pUserID, pPassword);
                if (dataUser == null) {
                    Common.showErrorMessage("User tidak ditemukan!", null);
                    return false;
                } else {
                    int tEmpStatus = dataUser.getInt("emp_status");
                    int tUserStatus = dataUser.getInt("user_status");

                    if (tEmpStatus != 1) {
                        Common.showErrorMessage("Karyawan tidak aktif!", null);
                        return false;
                    }

                    if (tUserStatus != 1) {
                        Common.showErrorMessage("User tidak aktif!", null);
                        return false;
                    }

                    setUserName(pUserID);
                    setPassword(pPassword);
                    setName(dataUser.getString("name"));
                    setAddress(dataUser.getString("address"));
                    setPhone(dataUser.getString("phone"));
                    setEmail(dataUser.getString("email"));
                    setPosition(dataUser.getString("position"));
                    setAccess(dataUser.getString("access"));
                    setId(dataUser.get("id").toString());
                    setNip(dataUser.getString("employe_id"));
                    return true;
                }
            }
        } catch (JSONException | SQLException e) {
            Common.errorLog("[JSONException | SQLException] Error create session", e);
            Common.showErrorMessage(MessageType.SYSTEM_ERROR, null);
        } catch (Exception e) {
            Common.errorLog("[Exception] Error create session", e);
            Common.showErrorMessage(MessageType.FATAL_ERROR, null);
        }
        
        return false;
    }
    
    public boolean isAllowedAccess(String... access){
        if(getAccess().equals("*/*")){
            return true;
        }
        return modulManager.isHasAccessModule(access);
    }

    private static class Holder{
        public static SessionManager session = new SessionManager();
    }
    
    public static SessionManager instance(){
        return Holder.session;
    }
}
