package top.auspice.abstraction;

import org.jetbrains.annotations.NotNull;

public interface Keyed<K> {
    @NotNull K getKey();
}
