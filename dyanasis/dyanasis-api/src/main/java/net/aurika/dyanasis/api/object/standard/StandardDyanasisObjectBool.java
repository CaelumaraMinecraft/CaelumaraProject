package net.aurika.dyanasis.api.object.standard;

import net.aurika.dyanasis.api.NamingContract;
import net.aurika.dyanasis.api.declaration.invokable.function.DyanasisFunctionKey;
import net.aurika.dyanasis.api.declaration.namespace.DyanasisNamespace;
import net.aurika.dyanasis.api.invoking.input.DyanasisFunctionInput;
import net.aurika.dyanasis.api.invoking.result.DyanasisFunctionResult;
import net.aurika.dyanasis.api.lexer.DyanasisLexer;
import net.aurika.dyanasis.api.object.DefaultDyanasisObject;
import net.aurika.dyanasis.api.object.DyanasisObject;
import net.aurika.dyanasis.api.object.DyanasisObjectBool;
import net.aurika.dyanasis.api.runtime.DyanasisRuntime;
import net.aurika.dyanasis.api.type.DyanasisType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class StandardDyanasisObjectBool<Lexer extends DyanasisLexer> extends StandardDyanasisObject<Boolean, Lexer> implements DyanasisObjectBool {

    public static final String TYPE_NAME = "Bool";

    protected final StandardObjectBoolPropertyContainer properties = new StandardObjectBoolPropertyContainer();
    protected final StandardObjectBoolFunctionContainer functions = new StandardObjectBoolFunctionContainer();

    public StandardDyanasisObjectBool(@NotNull DyanasisRuntime runtime, boolean value, Lexer lexer) {
        this(runtime, value, lexer, null, standardType(runtime, TYPE_NAME, () -> new StandardObjectBoolType(runtime, standardNS(runtime))));
    }

    protected StandardDyanasisObjectBool(@NotNull DyanasisRuntime runtime, boolean value, Lexer lexer, @Nullable DefaultDyanasisObject.DefaultObjectDoc doc, @NotNull DyanasisType<? extends StandardDyanasisObjectBool<Lexer>> type) {
        super(runtime, value, lexer, doc, type);
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
        return switch (cfgStr) {
            case "true" -> value == Boolean.TRUE;
            case "false" -> value == Boolean.FALSE;
            default -> false;
        };
    }

    public class StandardObjectBoolPropertyContainer extends DefaultObjectPropertyContainer {
        public StandardObjectBoolPropertyContainer() {
            super();
        }

        @Override
        public @NotNull StandardDyanasisObjectBool<Lexer> owner() {
            return StandardDyanasisObjectBool.this;
        }
    }

    public class StandardObjectBoolFunctionContainer extends DefaultObjectFunctionContainer {
        public StandardObjectBoolFunctionContainer() {
            super();
        }

        @Override
        public @NotNull StandardDyanasisObjectBool<Lexer> owner() {
            return StandardDyanasisObjectBool.this;
        }
    }

    public abstract class StandardObjectBoolProperty extends DefaultObjectProperty {
        public StandardObjectBoolProperty(@NamingContract.Invokable final @NotNull String name) {
            super(name);
        }

        @Override
        public abstract @NotNull DyanasisObject value();

        @Override
        public @NotNull StandardDyanasisObjectBool<Lexer> owner() {
            return StandardDyanasisObjectBool.this;
        }
    }

    public abstract class StandardObjectBoolFunction extends DefaultObjectFunction {
        public StandardObjectBoolFunction(@NotNull DyanasisFunctionKey key) {
            super(key);
        }

        @Override
        public abstract @NotNull DyanasisFunctionResult apply(@NotNull DyanasisFunctionInput input);

        @Override
        public @NotNull StandardDyanasisObjectBool<Lexer> owner() {
            return StandardDyanasisObjectBool.this;
        }
    }

    public static class StandardObjectBoolType extends DefaultObjectType<StandardDyanasisObjectBool<?>> {
        public StandardObjectBoolType(@NotNull DyanasisRuntime runtime, @NotNull DyanasisNamespace namespace) {
            super(runtime, namespace, StandardDyanasisObjectBool.TYPE_NAME);
        }
    }
}
