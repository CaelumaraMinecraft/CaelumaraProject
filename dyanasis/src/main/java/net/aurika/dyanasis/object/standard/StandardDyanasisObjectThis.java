package net.aurika.dyanasis.object.standard;

import net.aurika.dyanasis.declaration.invokable.function.container.DyanasisFunctions;
import net.aurika.dyanasis.declaration.invokable.property.container.DyanasisProperties;
import net.aurika.dyanasis.object.DyanasisObject;
import net.aurika.dyanasis.object.DyanasisObjectThis;
import net.aurika.validate.Validate;
import org.jetbrains.annotations.NotNull;

public class StandardDyanasisObjectThis implements DyanasisObjectThis {
    private final @NotNull DyanasisObject delegate;

    public StandardDyanasisObjectThis(@NotNull DyanasisObject delegate) {
        Validate.Arg.notNull(delegate, "delegate");
        this.delegate = delegate;
    }

    @Override
    public @NotNull DyanasisProperties dyanasisProperties() {
        return delegate.dyanasisProperties();
    }

    @Override
    public @NotNull DyanasisFunctions dyanasisFunctions() {
        return delegate.dyanasisFunctions();
    }

    @Override
    public boolean equals(@NotNull String cfgStr) {
        return delegate.equals(cfgStr);
    }

    @Override
    public @NotNull Object valueAsJava() {
        return delegate.valueAsJava();
    }

    /**
     * Gets the delegate object.
     *
     * @return the delegate object
     */
    public @NotNull DyanasisObject gelegate() {
        return delegate;
    }
}
