package net.aurika.config.validator;

import net.aurika.config.part.ConfigPart;
import net.aurika.validate.Validate;
import org.jetbrains.annotations.NotNull;

public class ValidationFailure {

  private final @NotNull Severity severity;
  private final @NotNull ConfigPart config;
  private String message;

  public ValidationFailure(@NotNull Severity severity, @NotNull ConfigPart config) {
    this(severity, config, null);
  }

  public ValidationFailure(@NotNull Severity severity, @NotNull ConfigPart config, String message) {
    Validate.Arg.notNull(severity, "severity");
    Validate.Arg.notNull(config, "config");
    this.severity = severity;
    this.config = config;
    this.message = message;
  }

  public @NotNull Severity severity() {
    return this.severity;
  }

  public @NotNull ConfigPart config() {
    return config;
  }

  public String message() {
    return this.message;
  }

  public void message(String message) {
    this.message = message;
  }

  public enum Severity {
    ERROR,
    WARNING
  }

}
