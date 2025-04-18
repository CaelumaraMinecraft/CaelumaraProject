package net.aurika.tasks;

import net.aurika.common.ident.Ident;
import net.aurika.tasks.context.TaskContext;
import net.aurika.tasks.priority.Priority;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractParentTask<C extends TaskContext> extends AbstractTask<C> implements ParentTask<C> {

  private final @NotNull TaskRegistry<C, Task<C>> subTasks;

  public AbstractParentTask(@NotNull Ident ident, @NotNull Priority priority, @Nullable Task<C> parent) {
    super(ident, priority, parent);
    this.subTasks = new TaskRegistry<>(this);
  }

  public @NotNull TaskRegistry<C, Task<C>> getSubTasks() {
    return subTasks;
  }

}
