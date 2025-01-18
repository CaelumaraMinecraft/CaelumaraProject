package top.auspice.constants.base;

import org.jetbrains.annotations.NotNull;
import top.auspice.abstraction.Keyed;

public abstract class KeyedAuspiceObject<K> extends AuspiceObject implements Keyed<K> {
    public abstract @NotNull K getKey();
}
