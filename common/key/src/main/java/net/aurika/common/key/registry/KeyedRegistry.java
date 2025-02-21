package net.aurika.common.key.registry;

import net.aurika.common.key.Key;
import net.aurika.common.key.Keyed;
import org.jetbrains.annotations.Nullable;

public interface KeyedRegistry<T extends Keyed> {
    void register(T obj);

    boolean isRegistered(Key key);

    @Nullable T getRegistered(Key key);
}
