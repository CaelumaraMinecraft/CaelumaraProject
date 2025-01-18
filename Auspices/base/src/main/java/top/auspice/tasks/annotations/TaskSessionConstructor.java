package top.auspice.tasks.annotations;

import org.jetbrains.annotations.NotNull;
import top.auspice.tasks.container.LocalTaskSession;
import top.auspice.tasks.context.TaskContext;

@FunctionalInterface
public interface TaskSessionConstructor<C extends TaskContext> {
    @NotNull
    LocalTaskSession createSession(@NotNull C var1);
}
