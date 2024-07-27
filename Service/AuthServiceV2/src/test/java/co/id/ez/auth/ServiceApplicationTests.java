package co.id.ez.auth;

import co.id.ez.system.core.etc.EncryptionService;

public class ServiceApplicationTests {
    public static void main(String[] args) {
        String tClientID = "98ab76fg";
        String tUser = "admin";
        String tPassword = "admin";
        String tKey = "d0f6fc3a-f972-41a9-9";
        
        System.out.println(EncryptionService.encryptor().Base64Encrypt(EncryptionService.encryptor().encrypt(tClientID, tKey)));
        
        String tEncryptedUser = EncryptionService.encryptor().encrypt(tUser, tKey);
        System.out.println("EncryptedUser: "+ tEncryptedUser);
        tEncryptedUser = EncryptionService.encryptor().Base64Encrypt(tEncryptedUser);
        System.out.println("User: "+ tEncryptedUser);
        String tEncryptedPassword = EncryptionService.encryptor().encrypt(tPassword, tKey);
        System.out.println("Encrypted Password 1: "+ tEncryptedPassword);
        tEncryptedPassword = EncryptionService.encryptor().encrypt(tKey, tClientID, tEncryptedUser, tEncryptedPassword);
        System.out.println("Encrypted Password 2: "+ tEncryptedPassword);
        System.out.println("Password: "+ EncryptionService.encryptor().Base64Encrypt(tEncryptedPassword));
        
        String tValidateTarget = EncryptionService.encryptor().encrypt(tPassword, tKey);
        
        System.out.println("valid: " + EncryptionService.encryptor().validateKey(tEncryptedPassword, tKey, tClientID, tUser, tValidateTarget));
    }
    
}
