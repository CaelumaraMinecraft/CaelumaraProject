package net.aurika.utils.cache.caffeine;

import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

public final class ReferencedExpirableObject<T> implements ExpirableObject {
    private final T reference;
    @NotNull
    private final ExpirationStrategy expirationStrategy;

    public ReferencedExpirableObject(T reference, @NotNull ExpirationStrategy expirationStrategy) {
        Intrinsics.checkNotNullParameter(expirationStrategy, "expirationStrategy");
        this.reference = reference;
        this.expirationStrategy = expirationStrategy;
    }

    public final T getReference() {
        return this.reference;
    }

    @NotNull
    public ExpirationStrategy getExpirationStrategy() {
        return this.expirationStrategy;
    }
}
