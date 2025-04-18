package net.aurika.auspice.platform;

public class PlatformNotSupportedException extends Exception {

  public PlatformNotSupportedException() {
  }

  public PlatformNotSupportedException(String message) {
    super(message);
  }

  public PlatformNotSupportedException(String message, Throwable cause) {
    super(message, cause);
  }

  public PlatformNotSupportedException(Throwable cause) {
    super(cause);
  }

  protected PlatformNotSupportedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

}
