package net.aurika.configuration.part.exception;

public class ConfigPartNotAbsolutePathException extends RuntimeException {

  public ConfigPartNotAbsolutePathException() {
  }

  public ConfigPartNotAbsolutePathException(String message) {
    super(message);
  }

  public ConfigPartNotAbsolutePathException(String message, Throwable cause) {
    super(message, cause);
  }

  public ConfigPartNotAbsolutePathException(Throwable cause) {
    super(cause);
  }

  protected ConfigPartNotAbsolutePathException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

}
