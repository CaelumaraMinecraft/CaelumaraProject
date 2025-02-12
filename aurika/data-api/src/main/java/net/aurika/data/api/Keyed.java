package net.aurika.data.api;

import org.jetbrains.annotations.NotNull;

public interface Keyed<K> {
    @NotNull K dataKey();
}
