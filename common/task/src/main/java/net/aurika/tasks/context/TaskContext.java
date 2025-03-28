package net.aurika.tasks.context;

import net.aurika.tasks.TaskState;
import net.aurika.tasks.container.TaskSession;
import org.jetbrains.annotations.NotNull;

public interface TaskContext {

  @NotNull TaskState getState();

  void setState(@NotNull TaskState state);

  @NotNull TaskSession getSession();

  @NotNull TaskContext createNew();

}
