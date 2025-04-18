package net.aurika.util.file.walker.visitors;

import net.aurika.common.validate.Validate;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.Objects;
import java.util.function.Predicate;

public class ExactPathFilter implements Predicate<Path> {

  private final @NotNull Path exactPath;

  public ExactPathFilter(@NotNull Path exactPath) {
    Validate.Arg.notNull(exactPath, "exactPath");
    this.exactPath = exactPath;
  }

  public @NotNull Path getExactPath() {
    return exactPath;
  }

  public boolean test(@NotNull Path path) {
    Validate.Arg.notNull(path, "path");
    return Objects.equals(exactPath, path);
  }

  public @NotNull String toString() {
    return "ExactPathFilter(" + this.exactPath + ')';
  }

}
