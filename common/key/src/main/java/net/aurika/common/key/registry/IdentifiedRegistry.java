package net.aurika.common.key.registry;

import net.aurika.common.key.Ident;
import net.aurika.common.key.Identified;
import org.jetbrains.annotations.Nullable;

public interface IdentifiedRegistry<T extends Identified> extends BaseRegistry {
    void register(T obj);

    boolean isRegistered(Ident id);

    @Nullable T getRegistered(Ident ident);
}
