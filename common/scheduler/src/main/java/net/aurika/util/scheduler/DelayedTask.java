package net.aurika.util.scheduler;

import org.jetbrains.annotations.NotNull;

import java.time.Duration;

public interface DelayedTask extends ScheduledTask {

  @NotNull Duration getDelay();

}
