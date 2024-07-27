package co.id.ez.gateway;

import co.id.ez.system.core.etc.EncryptionService;


public class ServiceApplicationTests1 {
    public static void main(String[] args) {
        String tClientID = "97cd88bf";
        String tUser = "reza";
        String tPassword = "r3z@4k53s";
        String tKey = "c890hgn-6f56-12bn-0";
        
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
        
        System.out.println("DAS: "+
                EncryptionService.encryptor().Base64Encrypt(
                EncryptionService.encryptor()
                        .encrypt(tClientID, tKey))
        );
        
        System.out.println("valid: " + EncryptionService.encryptor().validateKey(tEncryptedPassword, tKey, tClientID, tUser, tValidateTarget));
        
        System.out.println("USERS: "+ EncryptionService.encryptor().Base64Encrypt(
                EncryptionService.encryptor()
                        .encrypt(tUser, tKey)
        ));
    }
}
