package net.aurika.dyanasis.api.object.standard;

import net.aurika.dyanasis.api.declaration.invokable.function.container.DyanasisFunctionContainer;
import net.aurika.dyanasis.api.declaration.invokable.property.container.DyanasisPropertyContainer;
import net.aurika.dyanasis.api.lexer.DyanasisLexerSettings;
import net.aurika.dyanasis.api.object.AbstractDyanasisObject;
import net.aurika.dyanasis.api.object.DyanasisObjectNull;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class StandardDyanasisObjectNull extends AbstractDyanasisObject<Void> implements DyanasisObjectNull {
    public StandardDyanasisObjectNull(@NotNull DyanasisLexerSettings settings) {
        super(null, settings);
    }

    @Override
    public @NotNull DyanasisPropertyContainer dyanasisProperties() {
        return DyanasisPropertyContainer.empty();
    }

    @Override
    public @NotNull DyanasisFunctionContainer dyanasisFunctions() {
        return DyanasisFunctionContainer.empty();
    }

    @Override
    public boolean equals(@NotNull String cfgStr) {
        return Objects.equals(cfgStr, lexer().idents().nil());
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
