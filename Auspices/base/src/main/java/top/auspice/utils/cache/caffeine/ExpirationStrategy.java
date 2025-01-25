package top.auspice.utils.cache.caffeine;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import net.aurika.utils.Checker;

import java.time.Duration;

public final class ExpirationStrategy {
    @NotNull
    private final Duration expiryAfterCreate;
    @Nullable
    private final Duration expiryAfterUpdate;
    @Nullable
    private final Duration expiryAfterRead;

    public ExpirationStrategy(@NotNull Duration expiryAfterCreate, @Nullable Duration expiryAfterUpdate, @Nullable Duration expiryAfterRead) {
        Checker.Argument.checkNotNull(expiryAfterCreate, "expiryAfterCreate");
        this.expiryAfterCreate = expiryAfterCreate;
        this.expiryAfterUpdate = expiryAfterUpdate;
        this.expiryAfterRead = expiryAfterRead;
    }

    @NotNull
    public Duration getExpiryAfterCreate() {
        return this.expiryAfterCreate;
    }

    @Nullable
    public Duration getExpiryAfterUpdate() {
        return this.expiryAfterUpdate;
    }

    @Nullable
    public Duration getExpiryAfterRead() {
        return this.expiryAfterRead;
    }

    @NotNull
    public static ExpirationStrategy all(@NotNull Duration duration) {
        return new ExpirationStrategy(duration, duration, duration);
    }

    @NotNull
    public static ExpirationStrategy expireAfterRead(@NotNull Duration duration) {
        return new ExpirationStrategy(duration, duration, duration);
    }

    @NotNull
    public static ExpirationStrategy expireAfterCreate(@NotNull Duration duration) {
        return new ExpirationStrategy(duration, null, null);
    }
}

