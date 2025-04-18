package net.aurika.util.file.walker.visitors;

import net.aurika.common.validate.Validate;
import org.jetbrains.annotations.NotNull;

import java.nio.file.FileVisitResult;

public final class SkipAll implements PathVisitor {

  public static final @NotNull SkipAll INSTANCE = new SkipAll();

  private SkipAll() {
  }

  public @NotNull FileVisitResult onVisit(@NotNull PathVisit visit) {
    Validate.Arg.notNull(visit, "visit");
    return FileVisitResult.SKIP_SUBTREE;
  }

  public @NotNull String toString() {
    return "PathVisitor:SkipAll";
  }

}
