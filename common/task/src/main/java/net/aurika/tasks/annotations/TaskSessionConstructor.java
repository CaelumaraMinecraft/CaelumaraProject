package net.aurika.tasks.annotations;

import net.aurika.tasks.container.LocalTaskSession;
import net.aurika.tasks.context.TaskContext;
import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface TaskSessionConstructor<C extends TaskContext> {

  @NotNull LocalTaskSession createSession(@NotNull C context);

}
