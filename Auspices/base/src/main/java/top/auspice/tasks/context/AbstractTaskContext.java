package top.auspice.tasks.context;

import org.jetbrains.annotations.NotNull;
import top.auspice.tasks.TaskState;
import top.auspice.tasks.container.TaskSession;

import java.util.Objects;

public abstract class AbstractTaskContext implements TaskContext {
    @NotNull
    private final TaskSession session;
    @NotNull
    private TaskState state;

    public AbstractTaskContext(@NotNull TaskSession session) {
        Objects.requireNonNull(session);
        this.session = session;
        this.state = TaskState.CONTINUE;
    }

    @NotNull
    public TaskSession getSession() {
        return this.session;
    }

    @NotNull
    public TaskState getState() {
        return this.state;
    }

    public void setState(@NotNull TaskState var1) {
        Objects.requireNonNull(var1);
        this.state = var1;
    }
}