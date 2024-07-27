/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.auth;

import co.id.ez.auth.util.MitraAccess;
import co.id.ez.system.core.etc.EncryptionService;

/**
 *
 * @author RCS
 */
public class NewMitraTester {
    public static void main(String[] args) {
        MitraAccess mitra =new MitraAccess();
        System.out.println(mitra.generateNewSiswa("berlimart", "13e1211@13m412t"));
        System.out.println("dash: " + EncryptionService.encryptor().Base64Encrypt(
                EncryptionService.encryptor()
                        .encrypt("68d8a08241ad", "e830-4bc4-ebb858cd-bac8")));
    }
}
