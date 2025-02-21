package net.aurika.util.scheduler;

import org.jetbrains.annotations.NotNull;

import java.time.Duration;

public interface DelayedRepeatingTask extends DelayedTask {
    @NotNull Duration getIntervalDelay();
}