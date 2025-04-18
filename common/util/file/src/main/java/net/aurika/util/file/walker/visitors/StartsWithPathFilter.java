package net.aurika.util.file.walker.visitors;

import net.aurika.common.validate.Validate;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.function.Predicate;

public final class StartsWithPathFilter implements Predicate<Path> {

  private final @NotNull Path startsWith;

  public StartsWithPathFilter(@NotNull Path startsWith) {
    Validate.Arg.notNull(startsWith, "startsWith");
    this.startsWith = startsWith;
  }

  public @NotNull Path getStartsWith() {
    return startsWith;
  }

  public boolean test(@NotNull Path path) {
    Validate.Arg.notNull(path, "path");
    return path.startsWith(startsWith);
  }

  public @NotNull String toString() {
    return "StartsWithPathFilter(" + this.startsWith + ')';
  }

}
