package top.mckingdom.powerfulterritory.util;

import top.mckingdom.powerfulterritory.PowerfulTerritoryAddon;

import java.util.logging.Logger;

public class PowerfulTerritoryLogger {
  private static Logger logger = PowerfulTerritoryAddon.get().getLogger();

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
