package net.aurika.dyanasis.object.standard;

import net.aurika.dyanasis.declaration.invokable.function.DyanasisFunction;
import net.aurika.dyanasis.declaration.invokable.function.DyanasisFunctionKey;
import net.aurika.dyanasis.declaration.invokable.property.DyanasisProperty;
import net.aurika.dyanasis.object.DyanasisObject;
import net.aurika.dyanasis.object.DyanasisObjectThis;
import net.aurika.validate.Validate;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class StandardDyanasisObjectThis implements DyanasisObjectThis {
    private final @NotNull DyanasisObject delegate;

    public StandardDyanasisObjectThis(@NotNull DyanasisObject delegate) {
        Validate.Arg.notNull(delegate, "delegate");
        this.delegate = delegate;
    }

    @Override
    public @NotNull Map<String, ? extends DyanasisProperty> dyanasisProperties() {
        return delegate.dyanasisProperties();
    }

    @Override
    public @NotNull Map<DyanasisFunctionKey, ? extends DyanasisFunction> dyanasisFunctions() {
        return delegate.dyanasisFunctions();
    }

    @Override
    public boolean equals(@NotNull String str) {
        return delegate.equals(str);
    }

    @Override
    public @NotNull Object value() {
        return delegate.value();
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
