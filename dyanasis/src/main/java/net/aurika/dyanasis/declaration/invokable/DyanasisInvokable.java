package net.aurika.dyanasis.declaration.invokable;

import net.aurika.dyanasis.declaration.DyanasisDeclaration;
import net.aurika.dyanasis.declaration.doc.DyanasisDocOwner;
import net.aurika.dyanasis.declaration.invokable.function.DyanasisFunction;
import net.aurika.dyanasis.declaration.invokable.property.DyanasisProperty;
import org.jetbrains.annotations.NotNull;

/**
 * The declaration of dyanasis invokable. It's function and property.
 *
 * @see DyanasisFunction
 * @see DyanasisProperty
 */
public interface DyanasisInvokable extends DyanasisDeclaration, DyanasisDocOwner {
    @NotNull DyanasisInvokableOwner owner();

    enum Type {
        PROPERTY, FUNCTION
    }
}
