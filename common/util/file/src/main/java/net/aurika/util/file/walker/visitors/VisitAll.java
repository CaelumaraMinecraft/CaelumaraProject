package net.aurika.util.file.walker.visitors;

import net.aurika.common.validate.Validate;
import org.jetbrains.annotations.NotNull;

import java.nio.file.FileVisitResult;

public final class VisitAll implements PathVisitor {

  public static final @NotNull VisitAll INSTANCE = new VisitAll();

  private VisitAll() {
  }

  public @NotNull FileVisitResult onVisit(@NotNull PathVisit visit) {
    Validate.Arg.notNull(visit, "visit");
    return FileVisitResult.CONTINUE;
  }

  public @NotNull String toString() {
    return "PathVisitor:VisitAll";
  }

}
