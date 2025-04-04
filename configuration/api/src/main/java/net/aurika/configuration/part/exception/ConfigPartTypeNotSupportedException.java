package net.aurika.configuration.part.exception;

public class ConfigPartTypeNotSupportedException extends RuntimeException {

  public ConfigPartTypeNotSupportedException() {
  }

  public ConfigPartTypeNotSupportedException(String message) {
    super(message);
  }

  public ConfigPartTypeNotSupportedException(String message, Throwable cause) {
    super(message, cause);
  }

  public ConfigPartTypeNotSupportedException(Throwable cause) {
    super(cause);
  }

  protected ConfigPartTypeNotSupportedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

}
