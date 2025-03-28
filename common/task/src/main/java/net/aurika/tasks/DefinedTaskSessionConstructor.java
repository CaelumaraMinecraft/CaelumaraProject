package net.aurika.tasks;

import net.aurika.tasks.annotations.TaskSessionConstructor;
import net.aurika.tasks.container.LocalTaskSession;
import net.aurika.tasks.context.TaskContext;
import net.aurika.validate.Validate;
import org.jetbrains.annotations.NotNull;

public final class DefinedTaskSessionConstructor<C extends TaskContext> implements TaskSessionConstructor<C> {

  private final @NotNull TaskSessionConstructor<C> delegate;

  public DefinedTaskSessionConstructor(@NotNull TaskSessionConstructor<C> delegate) {
    Validate.Arg.notNull(delegate, "delegate");
    this.delegate = delegate;
  }

  public @NotNull LocalTaskSession createSession(@NotNull C context) {
    Validate.Arg.notNull(context, "context");
    LocalTaskSession instance = this.delegate.createSession(context);
    context.getSession().getInstances().put(instance.getClass(), instance);
    return instance;
  }

}
