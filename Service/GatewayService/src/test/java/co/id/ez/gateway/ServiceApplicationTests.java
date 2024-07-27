package co.id.ez.gateway;

import co.id.ez.system.core.etc.EncryptionService;


public class ServiceApplicationTests {
    public static void main(String[] args) {
        String x = EncryptionService.encryptor().Base64Encrypt(
                EncryptionService.encryptor()
                        .encrypt("98ab76fg", "d0f6fc3a-f972-41a9-9"));
        System.out.println("co.id.ez.gateway.ServiceApplicationTests.main(): " + x);
    }
}
