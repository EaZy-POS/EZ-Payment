package tester;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class Test {
    public static void main(String[] args) {
        Config config = ConfigFactory.parseString(
                "ezsystem.database.connections:[{password: toor, name: app, username: root, url: \"jdbc:mysql://127.0.0.1:3306/dbpayment?useUnicode=true&characterEncoding=UTF-8\"}, {password: toor, name: app2, username: root}]"
        );
        
        System.out.println("tester.Test.main(): "+ config.getConfigList("ezsystem.database.connections"));
        
        Config config2 = ConfigFactory.parseString("tes{result:true,value:minum, exp:setelah makan}");
        
        config = config2.withFallback(config);
        System.out.println("tester.Test.main(): "+ config.getString("tes.value"));
        System.out.println("tester.Test.main(): "+ config.getString("tes.exp"));
    }
}
