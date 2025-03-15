package net.aurika.dyanasis.api.object.standard;

import net.aurika.dyanasis.api.declaration.namespace.DyanasisNamespace;
import net.aurika.dyanasis.api.lexer.DyanasisLexer;
import net.aurika.dyanasis.api.object.DyanasisObject;
import net.aurika.dyanasis.api.object.DyanasisObjectNull;
import net.aurika.dyanasis.api.runtime.DyanasisRuntime;
import net.aurika.dyanasis.api.type.DyanasisType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class StandardDyanasisObjectNull<Lexer extends DyanasisLexer> extends StandardDyanasisObject<Void, Lexer> implements DyanasisObjectNull {

    public static final String TYPE_NAME = "Null";

    protected final StandardObjectNullPropertyContainer properties = new StandardObjectNullPropertyContainer();
    protected final StandardObjectNullFunctionContainer functions = new StandardObjectNullFunctionContainer();

    public StandardDyanasisObjectNull(@NotNull DyanasisRuntime runtime, @NotNull Lexer lexer) {
        this(runtime, lexer, null, standardType(runtime, TYPE_NAME, () -> new StandardNullType(runtime, standardNS(runtime))));
    }

    protected StandardDyanasisObjectNull(@NotNull DyanasisRuntime runtime, @NotNull Lexer lexer, @Nullable DefaultObjectDoc doc, @NotNull DyanasisType<? extends DyanasisObject> type) {
        super(runtime, null, lexer, doc, type);
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

    public class StandardObjectNullPropertyContainer extends DefaultObjectPropertyContainer {
        @Override
        public @NotNull DyanasisObject owner() {
            return StandardDyanasisObjectNull.this;
        }
    }

    public class StandardObjectNullFunctionContainer extends DefaultObjectFunctionContainer {
        @Override
        public @NotNull DyanasisObject owner() {
            return StandardDyanasisObjectNull.this;
        }
    }

    public static class StandardNullType extends DefaultObjectType<StandardDyanasisObjectNull<?>> {
        public StandardNullType(@NotNull DyanasisRuntime runtime, @NotNull DyanasisNamespace namespace) {
            super(runtime, namespace, StandardDyanasisObjectNull.TYPE_NAME);
        }
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
