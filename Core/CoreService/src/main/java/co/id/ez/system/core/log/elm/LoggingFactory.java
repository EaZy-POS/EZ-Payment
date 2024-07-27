package co.id.ez.system.core.log.elm;

import co.id.ez.system.core.config.ConfigService;

public class LoggingFactory {

    static boolean isEnabled(String module, Type type) {
        return isEnabled(module, type, false);
    }

    static boolean isEnabled(String module, Type type, boolean isTemp) {
        boolean enabled;
        if (!isTemp) {
            enabled = ConfigService.getInstance().getBoolean("ezsystem.log.general." + type.getName(), false);
        } else {
            enabled = ConfigService.getInstance().getBoolean("ezsystem.log.general.temp", false);
            if (ConfigService.getInstance().hasPath("ezsystem.log.temp." + type.getName())) {
                enabled = ConfigService.getInstance().getBoolean("ezsystem.log.temp." + type.getName(), false);
            }
        }

        if (ConfigService.getInstance().hasPath("ezsystem.log.modules." + module)) {
            if (!isTemp) {
                if (ConfigService.getInstance().hasPath("ezsystem.log.modules." + module + "." + type.getName())) {
                    enabled = ConfigService.getInstance().getBoolean("ezsystem.log.modules." + module + "." + type.getName(), false);
                }
            } else {
                enabled = ConfigService.getInstance().getBoolean("ezsystem.log.general.temp", false);
                if (ConfigService.getInstance().hasPath("ezsystem.log.modules." + module + ".temp." + type.getName())) {
                    enabled = ConfigService.getInstance().getBoolean("ezsystem.log.modules." + module + ".temp." + type.getName(), false);
                }
            }
        }

        return enabled;
    }
}
