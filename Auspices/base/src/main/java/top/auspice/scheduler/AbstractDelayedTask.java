package top.auspice.scheduler;

import org.jetbrains.annotations.NotNull;

import java.time.Duration;

public abstract class AbstractDelayedTask extends AbstractTask implements DelayedTask {
    private boolean a;
    private final Duration delay;

    public boolean isCancelled() {
        return this.a;
    }

    public boolean cancel() {
        return this.a = true;
    }

    public @NotNull Duration getDelay() {
        return this.delay;
    }

    public AbstractDelayedTask(@NotNull Runnable runnable, @NotNull Duration delay, @NotNull Task.ExecutionContextType executionContextType) {
        super(executionContextType, runnable);
        this.delay = delay;
    }
}
