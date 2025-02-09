package net.aurika.data.api;

import org.jetbrains.annotations.NotNull;

public interface Keyed<K> {
    @Deprecated
    @NotNull K getKey();

    default @NotNull K getDataKey() {
        return getKey();
    }
}
