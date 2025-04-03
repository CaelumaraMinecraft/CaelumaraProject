package net.aurika.auspice.logging;

import net.aurika.validate.Validate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.logging.Logger;

public class AbstractAuspiceLogger implements AuspiceLogger {

  public static final String DEBUG_PREFIX = "&7[&5DEBUG&7] &6";

  public static String getFullDebugPrefix(@Nullable Debug debug) {
    return DEBUG_PREFIX + "&8[&5" + (debug == null ? "" : debug.getNamespacedKey().asString()) + "&8] &6";
  }

  protected final Logger logger;

  protected AbstractAuspiceLogger(@NotNull Logger logger) {
    Validate.Arg.notNull(logger, "logger");
    this.logger = logger;
  }

  @Override
  public void info(String message) {
    logger.info(message);
  }

  @Override
  public void debug(Debug debug, String message) {
    logger.info(getFullDebugPrefix(debug) + message);
  }

  @Override
  public void error(String message) {
    logger.severe(message);
  }

  @Override
  public void error(String message, Throwable e) {

  }

  @Override
  public void warn(String message) {
    logger.warning(message);
  }

  @Override
  public void warn(String message, Throwable e) {

  }

}
