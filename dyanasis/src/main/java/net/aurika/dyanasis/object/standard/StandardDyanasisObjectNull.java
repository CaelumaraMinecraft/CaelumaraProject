package net.aurika.dyanasis.object.standard;

import net.aurika.dyanasis.declaration.invokable.function.container.DyanasisFunctions;
import net.aurika.dyanasis.declaration.invokable.property.container.DyanasisProperties;
import net.aurika.dyanasis.lexer.DyanasisLexerSettings;
import net.aurika.dyanasis.object.AbstractDyanasisObject;
import net.aurika.dyanasis.object.DyanasisObject;
import net.aurika.dyanasis.object.DyanasisObjectNull;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Objects;

public class StandardDyanasisObjectNull extends AbstractDyanasisObject<Void> implements DyanasisObjectNull {
    public StandardDyanasisObjectNull(@NotNull DyanasisLexerSettings settings) {
        super(null, settings);
    }

    @Override
    public @NotNull DyanasisProperties dyanasisProperties() {
        return DyanasisProperties.empty();
    }

    @Override
    public @NotNull DyanasisFunctions dyanasisFunctions() {
        return DyanasisFunctions.empty();
    }

    @Override
    public boolean equals(@NotNull String cfgStr) {
        return Objects.equals(cfgStr, settings().idents().nil());
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public @NotNull String toString() {
        return "DyanasisObjectNull";
    }
}
