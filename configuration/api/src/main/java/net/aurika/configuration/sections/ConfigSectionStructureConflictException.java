package net.aurika.configuration.sections;

public class ConfigSectionStructureConflictException extends RuntimeException {

  public ConfigSectionStructureConflictException() {
  }

  public ConfigSectionStructureConflictException(String message) {
    super(message);
  }

  public ConfigSectionStructureConflictException(String message, Throwable cause) {
    super(message, cause);
  }

  public ConfigSectionStructureConflictException(Throwable cause) {
    super(cause);
  }

  protected ConfigSectionStructureConflictException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

}
