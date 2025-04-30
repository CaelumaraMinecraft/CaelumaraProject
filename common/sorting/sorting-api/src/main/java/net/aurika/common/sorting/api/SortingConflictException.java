package net.aurika.common.sorting.api;

public class SortingConflictException extends RuntimeException {

  public SortingConflictException() {
  }

  public SortingConflictException(String message) {
    super(message);
  }

  public SortingConflictException(String message, Throwable cause) {
    super(message, cause);
  }

  public SortingConflictException(Throwable cause) {
    super(cause);
  }

  protected SortingConflictException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

}
