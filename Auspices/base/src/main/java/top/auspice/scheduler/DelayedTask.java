package top.auspice.scheduler;

import org.jetbrains.annotations.NotNull;

import java.time.Duration;

public interface DelayedTask extends ScheduledTask {
    public abstract @NotNull Duration getDelay();
}
