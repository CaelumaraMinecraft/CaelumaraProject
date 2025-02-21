package net.aurika.auspice.utils.unsafe.time;

import java.time.Duration;

public final class TickDuration {
    public static final long MILLISECONDS_IN_ONE_TICK = 50L;
    public static final Duration ONE = of(1L);

    private TickDuration() {
    }

    public static Duration of(long ticks) {
        return Duration.ofMillis(ticks * 50L);
    }
}
