package top.auspice.scheduler;

import net.aurika.abstraction.Cancellable;

public interface ScheduledTask extends Task, Cancellable {
    public abstract ExecutionContextType getExecutionContextType();

    public abstract void run();

    public abstract boolean cancel();

    public abstract boolean isCancelled();
}
