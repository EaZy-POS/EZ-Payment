package co.id.ez.system;

import co.id.ez.system.server.Server;
import co.id.ez.system.core.config.ConfigService;
import co.id.ez.system.core.etc.Utility;
import java.io.IOException;
import java.util.Arrays;
import org.fusesource.jansi.AnsiConsole;

public class Main {
    
    public static void main(String[] args) {
        
        if(args.length < 1) {
            Utility.printMessage("You need to provice configuration directory");
            System.exit(1);
        }
        
        long startTime = System.currentTimeMillis();
        AnsiConsole.systemInstall();
        
        ConfigService.createInstance(args[0]);
        
        try {
            Utility.drawAppsName(Main.class);
            Server.createHttpServer(startTime);
        } catch (IOException e) {
            Server.stopServer();
            Utility.printMessage("*** START FAILED ***");
            Utility.printError(e, "[IOException] Failed when trying to Start service");
            System.exit(1);
        } catch (Exception ex) {
            Utility.printMessage("*** START FAILED ***");
            Server.stopServer();
            Utility.printError(ex, "[Exception] Failed when trying to Start service");
            System.exit(1);
        }
        AnsiConsole.systemUninstall();
    }
}
