package net.aurika.configuration.part.exception;

public class ConfigPartAlreadyExistException extends RuntimeException {

  public ConfigPartAlreadyExistException() {
  }

  public ConfigPartAlreadyExistException(String message) {
    super(message);
  }

  public ConfigPartAlreadyExistException(String message, Throwable cause) {
    super(message, cause);
  }

  public ConfigPartAlreadyExistException(Throwable cause) {
    super(cause);
  }

  public ConfigPartAlreadyExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

}
