package net.aurika.tasks;

import net.aurika.common.ident.Ident;
import net.aurika.common.ident.Identified;
import net.aurika.tasks.context.TaskContext;
import net.aurika.tasks.priority.Priority;
import net.aurika.common.validate.Validate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface Task<C extends TaskContext> extends Identified {

  @NotNull Ident ident();

  @NotNull Priority priority();

  @Nullable Task<C> parent();

  void run(@NotNull C context);

  int hashCode();

  boolean equals(@Nullable Object obj);

  default int compareTo(@NotNull Task<?> other, @NotNull TaskRegistry<?, ?> registry) {
    Validate.Arg.notNull(other, "other");
    Validate.Arg.notNull(registry, "registry");
    int compared = this.priority().compareTo(other, registry);
    if (compared == Integer.MAX_VALUE) {
      compared = other.priority().compareTo(this, registry);
      if (compared == Integer.MAX_VALUE) {
        compared = 0;
      } else {
        compared = -compared;
      }
    }

    return compared;
  }

}
