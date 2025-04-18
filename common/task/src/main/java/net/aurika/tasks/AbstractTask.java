package net.aurika.tasks;

import net.aurika.common.ident.Ident;
import net.aurika.tasks.context.TaskContext;
import net.aurika.tasks.priority.Priority;
import net.aurika.common.validate.Validate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public abstract class AbstractTask<C extends TaskContext> implements Task<C> {

  private final @NotNull Ident ident;
  private final @NotNull Priority priority;
  private final @Nullable Task<C> parent;

  public AbstractTask(@NotNull Ident ident, @NotNull Priority priority, @Nullable Task<C> parent) {
    Validate.Arg.notNull(ident, "key");
    Validate.Arg.notNull(priority, "priority");
    this.ident = ident;
    this.priority = priority;
    this.parent = parent;
    String var10000 = this.priority.validateFor(this);
    if (var10000 != null) {
      throw new IllegalArgumentException(var10000 + " for task " + this);
    }
  }

  public final @NotNull Ident ident() {
    return this.ident;
  }

  public final @NotNull Priority priority() {
    return this.priority;
  }

  public final @Nullable Task<C> parent() {
    return this.parent;
  }

  public final boolean equals(@Nullable Object other) {
    if (this == other) {
      return true;
    } else {
      return other instanceof Task && Objects.equals(this.ident, ((Task<?>) other).ident());
    }
  }

  public final int hashCode() {
    return this.ident.hashCode();
  }

  public @NotNull String toString() {
    return this.getClass().getSimpleName() + "(name=" + this.ident.asDataString() + ", priority=" + this.priority + ", parent=" + this.parent + ')';
  }

}
