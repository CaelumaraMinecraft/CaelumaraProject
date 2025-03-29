package net.aurika.auspice.utils.compiler.condition;

public class LogicalException extends RuntimeException {

  public LogicalException(String message) {
    super(message);
  }

  public LogicalException(String message, Throwable cause) {
    super(message, cause);
  }

}