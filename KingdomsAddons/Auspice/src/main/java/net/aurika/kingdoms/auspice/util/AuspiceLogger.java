package net.aurika.kingdoms.auspice.util;

import top.mckingdom.auspice.AuspiceAddon;

import java.util.logging.Logger;

public class AuspiceLogger {
  private static final Logger logger = AuspiceAddon.get().getLogger();

  public static void info(Object o) {
    logger.info(o.toString());
  }

  public static void warn(Object o) {
    logger.warning(o.toString());
  }

  public static void error(Object o) {
    logger.severe(o.toString());
  }

}
