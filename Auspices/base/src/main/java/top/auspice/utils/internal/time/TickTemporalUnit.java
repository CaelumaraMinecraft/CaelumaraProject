package top.auspice.utils.internal.time;

import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalUnit;
import java.util.Objects;

public final class TickTemporalUnit implements TemporalUnit {
    @NotNull
    public static final TickTemporalUnit INSTANCE = new TickTemporalUnit();
    @NotNull
    private static final TickTemporalUnit INSTANCE$1;

    private TickTemporalUnit() {
    }

    @NotNull
    public static TickTemporalUnit getINSTANCE() {
        return INSTANCE$1;
    }

    public static long toTicks(@NotNull Duration $this$toTicks) {
        Objects.requireNonNull($this$toTicks);
        return $this$toTicks.toMillis() / 50L;
    }

    @NotNull
    public Duration getDuration() {
        return TickDuration.ONE;
    }

    public boolean isDurationEstimated() {
        return true;
    }

    public boolean isDateBased() {
        return false;
    }

    public boolean isTimeBased() {
        return true;
    }

    @NotNull
    public <R extends Temporal> R addTo(@NotNull R temporal, long amount) {
        Objects.requireNonNull(temporal);
        R var10000 = (R) temporal.plus(50L, ChronoUnit.MILLIS);
        Intrinsics.checkNotNull(var10000, "null cannot be cast to non-null type R of org.kingdoms.utils.time.internal.TickTemporalUnit.addTo");
        return var10000;
    }

    public long between(@NotNull Temporal temporal1Inclusive, @NotNull Temporal temporal2Exclusive) {
        Objects.requireNonNull(temporal1Inclusive);
        Objects.requireNonNull(temporal2Exclusive);
        return temporal1Inclusive.until(temporal2Exclusive, this);
    }

    static {
        INSTANCE$1 = INSTANCE;
    }
}
