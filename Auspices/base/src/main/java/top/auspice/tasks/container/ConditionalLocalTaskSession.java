package top.auspice.tasks.container;

import org.jetbrains.annotations.NotNull;
import top.auspice.tasks.context.TaskContext;

public interface ConditionalLocalTaskSession<C extends TaskContext> extends LocalTaskSession {
    boolean shouldExecute(@NotNull C context);
}