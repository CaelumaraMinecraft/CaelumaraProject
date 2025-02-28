package net.aurika.dyanasis.object.standard;

import net.aurika.dyanasis.declaration.invokable.function.DyanasisFunction;
import net.aurika.dyanasis.declaration.invokable.function.DyanasisFunctionKey;
import net.aurika.dyanasis.declaration.invokable.property.DyanasisProperty;
import net.aurika.dyanasis.object.DyanasisObjectBool;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Map;

public class StandardDyanasisObjectBool implements DyanasisObjectBool {

    public static final StandardDyanasisObjectBool TRUE = new StandardDyanasisObjectBool(true);
    public static final StandardDyanasisObjectBool FALSE = new StandardDyanasisObjectBool(false);

    private final boolean value;

    private StandardDyanasisObjectBool(boolean value) {
        this.value = value;
    }

    @Override
    public @NotNull Map<String, ? extends DyanasisProperty> dyanasisProperties() {
        return Collections.emptyMap();
    }

    @Override
    public @NotNull Map<DyanasisFunctionKey, ? extends DyanasisFunction> dyanasisFunctions() {
        return Collections.emptyMap();
    }

    @SuppressWarnings("PointlessBooleanExpression")
    @Override
    public boolean equals(@NotNull String str) {
        return switch (str) {
            case "true" -> value == true;
            case "false" -> value == false;
            default -> false;
        };
    }

    @Override
    public @NotNull Boolean value() {
        return value ? Boolean.TRUE : Boolean.FALSE;
    }
}
