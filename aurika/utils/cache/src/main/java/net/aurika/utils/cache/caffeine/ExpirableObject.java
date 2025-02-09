package net.aurika.utils.cache.caffeine;

import org.jetbrains.annotations.NotNull;

public interface ExpirableObject {
    @NotNull
    ExpirationStrategy getExpirationStrategy();
}
