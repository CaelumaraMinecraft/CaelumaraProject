package net.aurika.configuration.part.exception;

public class ConfigPartNotNamedException extends RuntimeException {

  public ConfigPartNotNamedException() {
  }

  public ConfigPartNotNamedException(String message) {
    super(message);
  }

  public ConfigPartNotNamedException(String message, Throwable cause) {
    super(message, cause);
  }

  public ConfigPartNotNamedException(Throwable cause) {
    super(cause);
  }

  protected ConfigPartNotNamedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

}
