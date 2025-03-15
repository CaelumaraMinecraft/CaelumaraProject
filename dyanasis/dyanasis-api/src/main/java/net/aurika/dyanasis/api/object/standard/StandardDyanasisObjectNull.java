package net.aurika.dyanasis.api.object.standard;

import net.aurika.dyanasis.api.lexer.DyanasisLexer;
import net.aurika.dyanasis.api.object.DyanasisObjectNull;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class StandardDyanasisObjectNull<Lexer extends DyanasisLexer> extends StandardDyanasisObject<Void, Lexer> implements DyanasisObjectNull {

    protected final DefaultObjectPropertyContainer properties = new DefaultObjectPropertyContainer() {
        @Override
        public @NotNull StandardDyanasisObjectNull<Lexer> owner() {
            return StandardDyanasisObjectNull.this;
        }
    };

    protected final DefaultObjectFunctionContainer functions = new DefaultObjectFunctionContainer() {
        @Override
        public @NotNull StandardDyanasisObjectNull<Lexer> owner() {
            return StandardDyanasisObjectNull.this;
        }
    };

    public StandardDyanasisObjectNull(@NotNull Lexer lexer) {
        super(null, lexer, TYPE);
    }

    @Override
    public @NotNull ObjectPropertyContainer<? extends ObjectProperty> dyanasisProperties() {
        return properties;
    }

    @Override
    public @NotNull ObjectFunctionContainer<? extends ObjectFunction> dyanasisFunctions() {
        return functions;
    }

    @Override
    public boolean equals(@NotNull String cfgStr) {
        return Objects.equals(cfgStr, lexer().settings().idents().nil());
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
