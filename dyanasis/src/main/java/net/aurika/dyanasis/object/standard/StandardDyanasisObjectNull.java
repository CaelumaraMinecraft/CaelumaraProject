package net.aurika.dyanasis.object.standard;

import net.aurika.dyanasis.declaration.invokable.function.container.DyanasisFunctions;
import net.aurika.dyanasis.declaration.invokable.property.container.DyanasisProperties;
import net.aurika.dyanasis.object.DyanasisObject;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;

public class StandardDyanasisObjectNull implements DyanasisObject {
    public static final StandardDyanasisObjectNull INSTANCE = new StandardDyanasisObjectNull();

    private StandardDyanasisObjectNull() {
    }

    @Override
    public @NotNull DyanasisProperties dyanasisProperties() {
        return Collections.emptyMap();
    }

    @Override
    public @NotNull DyanasisFunctions dyanasisFunctions() {
        return Collections.emptyMap();
    }

    @Override
    public boolean equals(@NotNull String cfgStr) {
        return cfgStr.equals("null");
    }

    @Override
    public @NotNull Void valueAsJava() {
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
