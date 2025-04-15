package net.aurika.xariaz.api.database.dataprovider;

public class DataNotSectionableException extends Exception {

  public DataNotSectionableException() {
  }

  public DataNotSectionableException(String message) {
    super(message);
  }

  public DataNotSectionableException(String message, Throwable cause) {
    super(message, cause);
  }

  public DataNotSectionableException(Throwable cause) {
    super(cause);
  }

  protected DataNotSectionableException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

}
