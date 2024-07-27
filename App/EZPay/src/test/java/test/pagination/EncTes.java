/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.pagination;

import co.id.ez.ezpay.app.App;
import co.id.ez.system.core.etc.EncryptionService;

/**
 *
 * @author RCS
 */
public class EncTes {
    public static void main(String[] args) {
        String result = EncryptionService
                .encryptor()
                .Base64Encrypt(
                        EncryptionService
                                .encryptor().encrypt(
                                        "http://127.0.0.1:6565", "enmcH556poKyPwxx"
                                )
                );
        
        String abn = EncryptionService
                .encryptor().decrypt(
                        EncryptionService
                                .encryptor()
                                .Base64Decrypt("N1lWTjVYNWkyTWI4cWVvd25yc3R5YnIya1lIVWJpQmhTZ2NKdVpDYVZZVT0="),
                         "0xdef09x70");
        
        System.out.println("result: "+ result);
        System.out.println("decrypt: "+ abn);
        
        String tDecPass = EncryptionService
                .encryptor().decrypt("c3B1VbtU/UCsl3RdNjoUr6k7Hn8ge5ZgsPPRUhWmG2pD47OFl8yl+tHrnJapUQD+/EsCdEkQQuionnPs9OgiIYOVQACPDUzk15dIB47pMzY=",
                         "fc17-4862-21ea33eb-8b61");
        String tpass = tDecPass.split(":")[2];
        String pass = EncryptionService
                .encryptor().decrypt(
                        EncryptionService
                                .encryptor()
                                .Base64Decrypt(tpass),
                         "fc17-4862-21ea33eb-8b61");
        System.out.println("pass: "+ tDecPass);
        System.out.println("pass: "+ pass);
//        return EncryptionService
//                .encryptor().decrypt(
//                        EncryptionService
//                                .encryptor()
//                                .Base64Decrypt(user_password),
//                         App.environment().getSvc_key());
    }
}
