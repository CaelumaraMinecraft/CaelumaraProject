package net.aurika.common.data.struct;

public class NoSuchSubPartException extends IllegalArgumentException {

  public NoSuchSubPartException() {
  }

  public NoSuchSubPartException(String s) {
    super(s);
  }

  public NoSuchSubPartException(String message, Throwable cause) {
    super(message, cause);
  }

  public NoSuchSubPartException(Throwable cause) {
    super(cause);
  }

}
