package net.aurika.dyanasis.api.object.standard;

import net.aurika.dyanasis.api.declaration.invokable.function.container.DyanasisFunctionContainer;
import net.aurika.dyanasis.api.declaration.invokable.property.container.DyanasisPropertyContainer;
import net.aurika.dyanasis.api.object.DyanasisObjectBool;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;

public class StandardDyanasisObjectBool implements DyanasisObjectBool {

    public static final StandardDyanasisObjectBool TRUE = new StandardDyanasisObjectBool(true);
    public static final StandardDyanasisObjectBool FALSE = new StandardDyanasisObjectBool(false);

    private final boolean value;

    private StandardDyanasisObjectBool(boolean value) {
        this.value = value;
    }

    @Override
    public @NotNull DyanasisPropertyContainer dyanasisProperties() {
        return DyanasisPropertyContainer.empty();
    }

    @Override
    public @NotNull DyanasisFunctionContainer dyanasisFunctions() {
        return DyanasisFunctionContainer.empty();
    }

    @SuppressWarnings("PointlessBooleanExpression")
    @Override
    public boolean equals(@NotNull String cfgStr) {
        return switch (cfgStr) {
            case "true" -> value == true;
            case "false" -> value == false;
            default -> false;
        };
    }

    @Override
    public @NotNull Boolean valueAsJava() {
        return value ? Boolean.TRUE : Boolean.FALSE;
    }
}
