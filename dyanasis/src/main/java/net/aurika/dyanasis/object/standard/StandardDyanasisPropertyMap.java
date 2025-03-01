package net.aurika.dyanasis.object.standard;

import net.aurika.dyanasis.declaration.invokable.function.container.DyanasisFunctions;
import net.aurika.dyanasis.declaration.invokable.property.container.DyanasisProperties;
import net.aurika.dyanasis.lexer.DyanasisLexerSettings;
import net.aurika.dyanasis.object.AbstractDyanasisObject;
import net.aurika.dyanasis.object.DyanasisObjectMap;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class StandardDyanasisPropertyMap extends AbstractDyanasisObject implements DyanasisObjectMap {

    public StandardDyanasisPropertyMap(@NotNull Map<?, ?> value,
                                       @NotNull DyanasisLexerSettings lexerSettings) {
    }

    @Override
    public @NotNull DyanasisProperties dyanasisProperties() {
        return null;
    }

    @Override
    public @NotNull DyanasisFunctions dyanasisFunctions() {
        return null;
    }

    @Override
    public boolean equals(@NotNull String cfgStr) {
        return false;
    }

    @Override
    public @NotNull Map<?, ?> valueAsJava() {
        return Map.of();
    }
}
