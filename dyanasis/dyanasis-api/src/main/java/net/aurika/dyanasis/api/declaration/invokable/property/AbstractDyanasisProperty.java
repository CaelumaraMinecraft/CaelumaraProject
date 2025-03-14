package net.aurika.dyanasis.api.declaration.invokable.property;

import net.aurika.dyanasis.api.NamingContract;
import net.aurika.dyanasis.api.declaration.invokable.AbstractDyanasisInvokable;
import net.aurika.dyanasis.api.object.DyanasisObject;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractDyanasisProperty<A extends DyanasisPropertyAnchor> extends AbstractDyanasisInvokable<A> implements DyanasisProperty {

    public AbstractDyanasisProperty(@NamingContract.Invokable final @NotNull String name, @NotNull A anchored) {
        super(name, anchored);
    }

    @Override
    public abstract @NotNull DyanasisObject value();
}
