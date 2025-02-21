package net.aurika.common.key.registry;

import net.aurika.common.key.Ident;
import net.aurika.common.key.Identified;
import net.aurika.common.key.Keyed;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

public interface StaticKeyedRegistry<T extends Keyed> extends KeyedRegistry<T>, Identified {
    @ApiStatus.NonExtendable
    /*final*/ @NotNull Ident ident();

    @ApiStatus.NonExtendable
    /*final*/ @NotNull Ident registryID();  // TODO
}
