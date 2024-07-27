/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.system;

import co.id.ez.system.core.etc.Utility;
import co.id.ez.system.server.Server;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author RCS
 */
public class MainApps {

    public static void main(String[] args) {
        try {
//            if (args.length == 0) {
//                args = new String[1];
//            }
//            args[0] = "conf/";
//            String classpath = "service/*;";
            Runtime.getRuntime().exec("java -cp \"EZSystem-1.0.0-SNAPSHOT.jar;libs/*;service/*\" co.id.ez.system.Main conf/ > logs/service.out");
//            System.setOut(new PrintStream(new FileOutputStream("logs/service.out")));
//            Main.main(args);
//        } catch (FileNotFoundException ex) {
//            Utility.printMessage("*** START FAILED ***");
//            Server.stopServer();
//            Utility.printError(ex, "[FileNotFoundException] Failed when trying to Start service");
//            System.exit(1);
        } catch (Exception ex) {
            Utility.printMessage("*** START FAILED ***");
            Server.stopServer();
            Utility.printError(ex, "[Exception] Failed when trying to Start service");
            System.exit(1);
        }
    }
}
