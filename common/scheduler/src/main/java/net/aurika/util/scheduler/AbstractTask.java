package net.aurika.util.scheduler;

import org.jetbrains.annotations.NotNull;

public class AbstractTask implements Task {
    protected final ExecutionContextType type;
    protected final Runnable runnable;

    public ExecutionContextType getExecutionContextType() {
        return this.type;
    }

    public void run() {
        this.runnable.run();
    }

    public AbstractTask(@NotNull Task.ExecutionContextType type, @NotNull Runnable runnable) {
        this.type = type;
        this.runnable = runnable;
    }
}
