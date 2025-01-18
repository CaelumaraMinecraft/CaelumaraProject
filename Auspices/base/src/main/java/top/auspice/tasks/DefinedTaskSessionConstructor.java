package top.auspice.tasks;

import org.jetbrains.annotations.NotNull;
import top.auspice.tasks.annotations.TaskSessionConstructor;
import top.auspice.tasks.container.LocalTaskSession;
import top.auspice.tasks.context.TaskContext;

import java.util.Objects;

public final class DefinedTaskSessionConstructor<C extends TaskContext> implements TaskSessionConstructor<C> {
    @NotNull
    private final TaskSessionConstructor<C> delegate;

    public DefinedTaskSessionConstructor(@NotNull TaskSessionConstructor<C> delegate) {
        Objects.requireNonNull(delegate);
        this.delegate = delegate;
    }

    @NotNull
    public LocalTaskSession createSession(@NotNull C context) {
        Objects.requireNonNull(context);
        LocalTaskSession instance = this.delegate.createSession(context);
        context.getSession().getInstances().put(instance.getClass(), instance);
        return instance;
    }
}
