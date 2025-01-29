package net.aurika.tasks.annotations;

import org.jetbrains.annotations.NotNull;
import net.aurika.tasks.container.LocalTaskSession;
import net.aurika.tasks.context.TaskContext;

@FunctionalInterface
public interface TaskSessionConstructor<C extends TaskContext> {
    @NotNull LocalTaskSession createSession(@NotNull C context);
}
