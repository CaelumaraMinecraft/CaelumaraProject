package top.auspice.scheduler;

import org.jetbrains.annotations.NotNull;

import java.time.Duration;

public interface DelayedRepeatingTask extends DelayedTask {
    public abstract @NotNull Duration getIntervalDelay();
}