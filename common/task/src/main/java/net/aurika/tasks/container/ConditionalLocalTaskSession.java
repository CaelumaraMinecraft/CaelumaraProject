package net.aurika.tasks.container;

import net.aurika.tasks.context.TaskContext;
import org.jetbrains.annotations.NotNull;

public interface ConditionalLocalTaskSession<C extends TaskContext> extends LocalTaskSession {

  boolean shouldExecute(@NotNull C context);

}