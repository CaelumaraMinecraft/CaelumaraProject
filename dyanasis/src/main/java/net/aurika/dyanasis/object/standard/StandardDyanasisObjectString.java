package net.aurika.dyanasis.object.standard;

import net.aurika.dyanasis.declaration.invokable.function.container.DyanasisFunctions;
import net.aurika.dyanasis.declaration.invokable.property.container.DyanasisProperties;
import net.aurika.dyanasis.object.DyanasisObject;
import net.aurika.validate.Validate;
import org.jetbrains.annotations.NotNull;

public class StandardDyanasisObjectString implements DyanasisObject {

    private final @NotNull String value;

    public StandardDyanasisObjectString(@NotNull String value) {
        Validate.Arg.notNull(value, "value");
        this.value = value;
    }

    @Override
    public @NotNull DyanasisProperties dyanasisProperties() {
        TODO
    }

    @Override
    public @NotNull DyanasisFunctions dyanasisFunctions() {
        TODO
    }

    @Override
    public boolean equals(@NotNull String cfgStr) {
        return value.equals(cfgStr);
    }

    @Override
    public @NotNull String valueAsJava() {
        return value;
    }
}
