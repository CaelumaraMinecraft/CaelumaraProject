package net.aurika.util.cache.caffeine;

import net.aurika.checker.Checker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.Duration;

public final class ExpirationStrategy {

    private final @NotNull Duration expiryAfterCreate;
    private final @Nullable Duration expiryAfterUpdate;
    private final @Nullable Duration expiryAfterRead;

    public ExpirationStrategy(@NotNull Duration expiryAfterCreate, @Nullable Duration expiryAfterUpdate, @Nullable Duration expiryAfterRead) {
        Checker.Arg.notNull(expiryAfterCreate, "expiryAfterCreate");
        this.expiryAfterCreate = expiryAfterCreate;
        this.expiryAfterUpdate = expiryAfterUpdate;
        this.expiryAfterRead = expiryAfterRead;
    }

    public @NotNull Duration getExpiryAfterCreate() {
        return this.expiryAfterCreate;
    }

    public @Nullable Duration getExpiryAfterUpdate() {
        return this.expiryAfterUpdate;
    }

    public @Nullable Duration getExpiryAfterRead() {
        return this.expiryAfterRead;
    }

    public static @NotNull ExpirationStrategy all(@NotNull Duration duration) {
        return new ExpirationStrategy(duration, duration, duration);
    }

    public static @NotNull ExpirationStrategy expireAfterRead(@NotNull Duration duration) {
        return new ExpirationStrategy(duration, duration, duration);
    }

    public static @NotNull ExpirationStrategy expireAfterCreate(@NotNull Duration duration) {
        return new ExpirationStrategy(duration, null, null);
    }
}

