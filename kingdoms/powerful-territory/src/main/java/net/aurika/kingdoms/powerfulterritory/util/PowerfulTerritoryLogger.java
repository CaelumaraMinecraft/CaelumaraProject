package net.aurika.kingdoms.powerfulterritory.util;

import net.aurika.kingdoms.powerfulterritory.PowerfulTerritoryAddon;

import java.util.logging.Logger;

public class PowerfulTerritoryLogger {

  private static final Logger logger = PowerfulTerritoryAddon.get().getLogger();

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
