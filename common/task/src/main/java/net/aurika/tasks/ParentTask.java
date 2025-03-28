package net.aurika.tasks;

import net.aurika.tasks.context.TaskContext;
import org.jetbrains.annotations.NotNull;

public interface ParentTask<C extends TaskContext> extends Task<C> {

  @NotNull TaskRegistry<C, Task<C>> getSubTasks();

}
