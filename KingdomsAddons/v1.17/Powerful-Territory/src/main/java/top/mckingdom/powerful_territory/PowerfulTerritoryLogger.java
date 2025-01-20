package top.mckingdom.powerful_territory;

import java.util.logging.Logger;

public class PowerfulTerritoryLogger {
    private static Logger logger = PowerfulTerritory.get().getLogger();

    public static void error(Object o) {
        logger.severe(o.toString());
    }

    public static void warn(Object o) {
        logger.severe(o.toString());
    }

    public static void info(Object o) {
        logger.info(o.toString());
    }
}
