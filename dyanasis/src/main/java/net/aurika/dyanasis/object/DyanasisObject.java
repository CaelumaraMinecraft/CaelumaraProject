package net.aurika.dyanasis.object;

import net.aurika.dyanasis.declaration.invokable.function.DyanasisFunction;
import net.aurika.dyanasis.declaration.invokable.function.DyanasisFunctionKey;
import net.aurika.dyanasis.declaration.invokable.function.DyanasisFunctionOwner;
import net.aurika.dyanasis.declaration.invokable.function.DyanasisFunctionsProvider;
import net.aurika.dyanasis.declaration.invokable.property.DyanasisPropertiesProvider;
import net.aurika.dyanasis.declaration.invokable.property.DyanasisProperty;
import net.aurika.dyanasis.declaration.invokable.property.DyanasisPropertyOwner;
import net.aurika.dyanasis.object.standard.StandardDyanasisObjectBool;
import net.aurika.dyanasis.object.standard.StandardDyanasisObjectNull;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public interface DyanasisObject extends DyanasisPropertiesProvider, DyanasisFunctionsProvider, DyanasisPropertyOwner, DyanasisFunctionOwner {
    static @NotNull StandardDyanasisObjectNull dyNull() {
        return StandardDyanasisObjectNull.INSTANCE;
    }

    static @NotNull StandardDyanasisObjectBool dyTrue() {
        return StandardDyanasisObjectBool.TRUE;
    }

    static @NotNull StandardDyanasisObjectBool dyFalse() {
        return StandardDyanasisObjectBool.FALSE;
    }

    @Override
    @NotNull Map<String, ? extends DyanasisProperty> dyanasisProperties();

    @Override
    @NotNull Map<DyanasisFunctionKey, ? extends DyanasisFunction> dyanasisFunctions();

    boolean equals(@NotNull String str);

    /**
     * Gets the value as a java object.
     *
     * @return the value
     */
    @NotNull Object value();
}
