package net.aurika.util.file.walker.visitors;

import net.aurika.common.validate.Validate;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.function.Predicate;

public class ConditionalPathVisitor {

  private final @NotNull Predicate<Path> predicate;
  private final @NotNull PathVisitor visitor;

  public ConditionalPathVisitor(@NotNull Predicate<Path> predicate, @NotNull PathVisitor visitor) {
    Validate.Arg.notNull(predicate, "predicate");
    Validate.Arg.notNull(visitor, "visitor");
    this.predicate = predicate;
    this.visitor = visitor;
  }

  public @NotNull Predicate<Path> getPredicate() {
    return predicate;
  }

  public @NotNull PathVisitor getVisitor() {
    return visitor;
  }

  public @NotNull String toString() {
    return "ConditionalPathVisitor(" + predicate + " -> " + visitor + ')';
  }

}
