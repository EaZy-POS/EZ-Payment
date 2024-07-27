/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.model.data.central;

import co.id.ez.ezpay.app.App;
import co.id.ez.ezpay.interfaces.DataTable;
import co.id.ez.system.core.etc.EncryptionService;

/**
 *
 * @author Lutfi
 */
public class CentralMitraModel implements DataTable {

    private int number;
    private final String id, client_id, client_key, user_id, user_password, nama, alamat, kota, provisi, kontak, telp, hp, email, tagline, status, aksi;

    public CentralMitraModel(int number, String id, String client_id, String client_key, 
            String user_id, String user_password, String nama, String alamat, String kota, String provisi, 
            String kontak, String telp, String hp, String email, String tagline, String status, String aksi) {
        this.number = number;
        this.id = id;
        this.client_id = client_id;
        this.client_key = client_key;
        this.user_id = user_id;
        this.user_password = user_password;
        this.nama = nama;
        this.alamat = alamat;
        this.kota = kota;
        this.provisi = provisi;
        this.telp = telp;
        this.hp = hp;
        this.email = email;
        this.tagline = tagline;
        this.aksi = aksi;
        this.kontak = kontak;
        this.status = status;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public String getId() {
        return id;
    }

    public String getClient_id() {
        return client_id;
    }

    public String getClient_key() {
        return client_key;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getUser_password() {
        return user_password;
    }

    public String getNama() {
        return nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public String getKota() {
        return kota;
    }

    public String getProvisi() {
        return provisi;
    }

    public String getTelp() {
        return telp;
    }

    public String getHp() {
        return hp;
    }

    public String getEmail() {
        return email;
    }

    public String getTagline() {
        return tagline;
    }

    public String getAksi() {
        return aksi;
    }

    public String getKontak() {
        return kontak;
    }

    public String getStatus() {
        return status;
    }
    
    public String getClientIDValue(){
        System.out.println("ClienID: "+ client_id);
        String result = EncryptionService
                .encryptor().decrypt(
                        EncryptionService
                                .encryptor()
                                .Base64Decrypt(client_id),
                         App.environment().getSvc_key());
        return result;
    }
    
    public String getClientKeyValue() {
        String result = EncryptionService
                .encryptor().decrypt(
                        EncryptionService
                                .encryptor()
                                .Base64Decrypt(client_key),
                         App.environment().getSvc_key());
        return result;
    }
    
    public String getUserIDValue() {
        return EncryptionService
                .encryptor().decrypt(
                        EncryptionService
                                .encryptor()
                                .Base64Decrypt(user_id),
                         getClientIDValue());
    }

    public String getUserPasswordValue() {
        String tDecPass = EncryptionService
                .encryptor().decrypt(user_password,
                         getClientKeyValue());
        String tpass = tDecPass.split(":")[2];
        String pass = EncryptionService
                .encryptor().decrypt(
                        EncryptionService
                                .encryptor()
                                .Base64Decrypt(tpass),
                         getClientKeyValue());
        return pass;
    }

    @Override
    public Object[] getArrayData() {
        return new Object[]{
            number,
            getClientIDValue(),
            nama,
            kota.concat(", ").concat(provisi),
            kontak,
            telp.concat(" / ").concat(hp),
            email,
            status,
            aksi
        };
    }
}
