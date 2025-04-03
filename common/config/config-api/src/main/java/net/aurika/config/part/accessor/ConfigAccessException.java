package net.aurika.config.part.accessor;

public class ConfigAccessException extends Exception {

  public ConfigAccessException() { super(); }

  public ConfigAccessException(String message) { super(message); }

  public ConfigAccessException(String message, Throwable cause) { super(message, cause); }

  public ConfigAccessException(Throwable cause) { super(cause); }

  protected ConfigAccessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

}
