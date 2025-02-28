package net.aurika.dyanasis.object.standard;

import net.aurika.dyanasis.declaration.invokable.function.DyanasisFunction;
import net.aurika.dyanasis.declaration.invokable.function.DyanasisFunctionKey;
import net.aurika.dyanasis.declaration.invokable.property.DyanasisProperty;
import net.aurika.dyanasis.object.DyanasisObject;
import net.aurika.validate.Validate;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class StandardDyanasisObjectString implements DyanasisObject {

    private final @NotNull String value;

    public StandardDyanasisObjectString(@NotNull String value) {
        Validate.Arg.notNull(value, "value");
        this.value = value;
    }

    @Override
    public @NotNull Map<String, ? extends DyanasisProperty> dyanasisProperties() {
        TODO
    }

    @Override
    public @NotNull Map<DyanasisFunctionKey, ? extends DyanasisFunction> dyanasisFunctions() {
        TODO
    }

    @Override
    public boolean equals(@NotNull String str) {
        return value.equals(str);
    }

    @Override
    public @NotNull String value() {
        return value;
    }
}
