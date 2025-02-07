package net.aurika.tasks.container;

import org.jetbrains.annotations.NotNull;
import net.aurika.tasks.context.TaskContext;

public interface ConditionalLocalTaskSession<C extends TaskContext> extends LocalTaskSession {
    boolean shouldExecute(@NotNull C context);
}