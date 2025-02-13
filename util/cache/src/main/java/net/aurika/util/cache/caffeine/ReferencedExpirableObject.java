package net.aurika.util.cache.caffeine;

import net.aurika.validate.Validate;
import org.jetbrains.annotations.NotNull;

public final class ReferencedExpirableObject<T> implements ExpirableObject {
    private final T reference;

    private final @NotNull ExpirationStrategy expirationStrategy;

    public ReferencedExpirableObject(T reference, @NotNull ExpirationStrategy expirationStrategy) {
        Validate.Arg.notNull(expirationStrategy, "expirationStrategy");
        this.reference = reference;
        this.expirationStrategy = expirationStrategy;
    }

    public T getReference() {
        return this.reference;
    }

    public @NotNull ExpirationStrategy getExpirationStrategy() {
        return this.expirationStrategy;
    }
}
