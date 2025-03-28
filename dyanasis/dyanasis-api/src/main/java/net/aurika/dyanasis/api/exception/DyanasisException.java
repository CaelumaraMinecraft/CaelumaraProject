package net.aurika.dyanasis.api.exception;

import org.jetbrains.annotations.NotNull;

public class DyanasisException extends RuntimeException {

  public DyanasisException() {
  }

  public DyanasisException(@NotNull String message) {
    super(message);
  }

  public DyanasisException(@NotNull String message, Throwable cause) {
    super(message, cause);
  }

  public DyanasisException(Throwable cause) {
    super(cause);
  }

  protected DyanasisException(@NotNull String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

}
