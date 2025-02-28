package net.aurika.dyanasis.object.standard;

import net.aurika.dyanasis.declaration.invokable.function.container.DyanasisFunctions;
import net.aurika.dyanasis.declaration.invokable.property.container.DyanasisProperties;
import net.aurika.dyanasis.object.DyanasisObject;
import net.aurika.validate.Validate;
import org.jetbrains.annotations.NotNull;

public class StandardDyanasisObjectNumber implements DyanasisObject {
    private final @NotNull Number value;

    public StandardDyanasisObjectNumber(@NotNull Number value) {
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
        TODO
    }

    @Override
    public @NotNull Number valueAsJava() {
        return value;
    }
}
