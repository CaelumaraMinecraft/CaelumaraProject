package net.aurika.common.key.registry;

import net.aurika.common.key.Ident;
import net.aurika.common.key.Keyed;
import net.aurika.validate.Validate;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractStaticKeyedRegistry<T extends Keyed> extends AbstractKeyedRegistry<T> implements StaticKeyedRegistry<T> {
    private final @NotNull Ident registryID;

    protected AbstractStaticKeyedRegistry(@NotNull Ident registryID) {
        Validate.Arg.notNull(registryID, "registryID");
        this.registryID = registryID;
    }

    public final @NotNull Ident ident() {
        return registryID;
    }

    public final @NotNull Ident registryID() { // TODO
        return registryID;
    }
}
