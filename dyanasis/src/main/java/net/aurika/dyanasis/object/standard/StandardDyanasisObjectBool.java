package net.aurika.dyanasis.object.standard;

import net.aurika.dyanasis.declaration.invokable.function.container.DyanasisFunctions;
import net.aurika.dyanasis.declaration.invokable.property.container.DyanasisProperties;
import net.aurika.dyanasis.object.DyanasisObjectBool;
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
    public @NotNull DyanasisProperties dyanasisProperties() {
        return Collections.emptyMap();
    }

    @Override
    public @NotNull DyanasisFunctions dyanasisFunctions() {
        return Collections.emptyMap();
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
