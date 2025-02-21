package net.aurika.tasks.context;

import net.aurika.tasks.TaskState;
import net.aurika.tasks.container.TaskSession;
import net.aurika.validate.Validate;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractTaskContext implements TaskContext {

    private final @NotNull TaskSession session;
    private @NotNull TaskState state;

    public AbstractTaskContext(@NotNull TaskSession session) {
        Validate.Arg.notNull(session, "session");
        this.session = session;
        this.state = TaskState.CONTINUE;
    }

    public @NotNull TaskSession getSession() {
        return this.session;
    }

    public @NotNull TaskState getState() {
        return this.state;
    }

    public void setState(@NotNull TaskState state) {
        Validate.Arg.notNull(state, "state");
        this.state = state;
    }
}