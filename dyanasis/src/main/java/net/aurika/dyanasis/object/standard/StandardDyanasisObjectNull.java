package net.aurika.dyanasis.object.standard;

import net.aurika.dyanasis.declaration.invokable.function.DyanasisFunction;
import net.aurika.dyanasis.declaration.invokable.function.DyanasisFunctionKey;
import net.aurika.dyanasis.declaration.invokable.property.DyanasisProperty;
import net.aurika.dyanasis.object.DyanasisObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Collections;
import java.util.Map;

public class StandardDyanasisObjectNull implements DyanasisObject {
    public static final StandardDyanasisObjectNull INSTANCE = new StandardDyanasisObjectNull();

    private StandardDyanasisObjectNull() {
    }

    @Override
    public @NotNull @Unmodifiable Map<String, ? extends DyanasisProperty> dyanasisProperties() {
        return Collections.emptyMap();
    }

    @Override
    public @NotNull Map<DyanasisFunctionKey, ? extends DyanasisFunction> dyanasisFunctions() {
        return Collections.emptyMap();
    }

    @Override
    public boolean equals(@NotNull String str) {
        return str.equals("null");
    }

    @Override
    public @NotNull Void value() {
        return null;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public @NotNull String toString() {
        return "DyObjectNull";
    }
}
