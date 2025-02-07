package net.aurika.data;

import org.jetbrains.annotations.NotNull;

public interface Keyed<K> {
    @NotNull K getKey();
}
