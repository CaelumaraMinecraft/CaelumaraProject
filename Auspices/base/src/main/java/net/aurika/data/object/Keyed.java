package net.aurika.data.object;

import org.jetbrains.annotations.NotNull;

public interface Keyed<K> {
    @NotNull K getKey();
}
