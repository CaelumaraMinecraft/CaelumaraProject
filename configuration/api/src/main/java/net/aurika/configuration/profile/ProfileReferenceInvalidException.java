package net.aurika.configuration.profile;

public class ProfileReferenceInvalidException extends Exception {

  public ProfileReferenceInvalidException() {
  }

  public ProfileReferenceInvalidException(String message) {
    super(message);
  }

  public ProfileReferenceInvalidException(String message, Throwable cause) {
    super(message, cause);
  }

  public ProfileReferenceInvalidException(Throwable cause) {
    super(cause);
  }

  protected ProfileReferenceInvalidException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

}
