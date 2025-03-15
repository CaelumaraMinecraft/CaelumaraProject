package net.aurika.dyanasis.api.declaration.invokable;

import net.aurika.dyanasis.api.Named;
import net.aurika.dyanasis.api.NamingContract;
import net.aurika.dyanasis.api.declaration.DyanasisDeclaration;
import net.aurika.dyanasis.api.declaration.NeedOwner;
import net.aurika.dyanasis.api.declaration.doc.DyanasisDocAnchor;
import net.aurika.dyanasis.api.declaration.invokable.function.DyanasisFunction;
import net.aurika.dyanasis.api.declaration.invokable.property.DyanasisProperty;
import net.aurika.dyanasis.api.runtime.DyanasisRuntime;
import net.aurika.dyanasis.api.runtime.DyanasisRuntimeObject;
import org.jetbrains.annotations.NotNull;

/**
 * The declaration of dyanasis invokable. It's function and property.
 *
 * @see DyanasisFunction
 * @see DyanasisProperty
 */
public interface DyanasisInvokable extends DyanasisDeclaration, DyanasisDocAnchor, NeedOwner, Named, DyanasisRuntimeObject {
    /**
     * Gets the name of the dyanasis invokable.
     *
     * @return the name
     */
    @Override
    @NamingContract.Invokable
    @NotNull String name();

    @Override
    @NotNull DyanasisInvokableAnchor owner();

    @Override
    @NotNull DyanasisRuntime dyanasisRuntime();
}
