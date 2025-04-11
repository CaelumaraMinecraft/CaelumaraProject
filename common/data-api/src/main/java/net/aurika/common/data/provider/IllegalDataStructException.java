package net.aurika.common.data.provider;

public class IllegalDataStructException extends IllegalStateException {

  public IllegalDataStructException() {
  }

  public IllegalDataStructException(String s) {
    super(s);
  }

  public IllegalDataStructException(String message, Throwable cause) {
    super(message, cause);
  }

  public IllegalDataStructException(Throwable cause) {
    super(cause);
  }

}
