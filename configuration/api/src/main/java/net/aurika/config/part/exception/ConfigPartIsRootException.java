package net.aurika.config.part.exception;

public class ConfigPartIsRootException extends RuntimeException {

  public ConfigPartIsRootException() {
  }

  public ConfigPartIsRootException(String message) {
    super(message);
  }

  public ConfigPartIsRootException(String message, Throwable cause) {
    super(message, cause);
  }

  public ConfigPartIsRootException(Throwable cause) {
    super(cause);
  }

  protected ConfigPartIsRootException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

}
