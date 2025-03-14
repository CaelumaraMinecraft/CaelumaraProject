package net.aurika.dyanasis.api.declaration.invokable;

import net.aurika.dyanasis.api.NamingContract;
import net.aurika.validate.Validate;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractDyanasisInvokable<A extends DyanasisInvokableAnchor> implements DyanasisInvokable {
    @NamingContract.Invokable
    private final @NotNull String name;
    private final @NotNull A anchored;

    public AbstractDyanasisInvokable(@NamingContract.Invokable final @NotNull String name, @NotNull A anchored) {
        Validate.Arg.notNull(name, "name");
        Validate.Arg.notNull(anchored, "anchored");
        this.name = name;
        this.anchored = anchored;
    }

    @Override
    @NamingContract.Invokable
    public @NotNull String name() {
        return name;
    }

    @Override
    public @NotNull A owner() {
        return anchored;
    }
}
