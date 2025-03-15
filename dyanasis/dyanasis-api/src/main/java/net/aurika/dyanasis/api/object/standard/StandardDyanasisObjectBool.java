package net.aurika.dyanasis.api.object.standard;

import net.aurika.dyanasis.api.NamingContract;
import net.aurika.dyanasis.api.declaration.invokable.function.DyanasisFunctionKey;
import net.aurika.dyanasis.api.declaration.namespace.DyanasisNamespaceContainer;
import net.aurika.dyanasis.api.invoking.input.DyanasisFunctionInput;
import net.aurika.dyanasis.api.invoking.result.DyanasisFunctionResult;
import net.aurika.dyanasis.api.lexer.DyanasisLexer;
import net.aurika.dyanasis.api.object.DyanasisObject;
import net.aurika.dyanasis.api.object.DyanasisObjectBool;
import net.aurika.dyanasis.api.runtime.DyanasisRuntime;
import net.aurika.validate.Validate;
import org.jetbrains.annotations.NotNull;

public class StandardDyanasisObjectBool<Lexer extends DyanasisLexer> extends StandardDyanasisObject<Boolean, Lexer> implements DyanasisObjectBool {

    public static <Lexer extends DyanasisLexer> StandardTypeData<StandardDyanasisObjectBool<Lexer>> typeData(@NotNull DyanasisRuntime runtime) {
        Validate.Arg.notNull(runtime, "runtime");
        DyanasisNamespaceContainer namespaces = runtime.environment().namespaces();
    }

    protected final StandardObjectBoolPropertyContainer properties = new StandardObjectBoolPropertyContainer();
    protected final StandardObjectBoolFunctionContainer functions = new StandardObjectBoolFunctionContainer();

    public StandardDyanasisObjectBool(@NotNull DyanasisRuntime runtime, boolean value, Lexer lexer) {
        super(runtime, value, lexer, null, typeData(runtime));
    }

    @Override
    public @NotNull ObjectPropertyContainer<? extends ObjectProperty> dyanasisProperties() {
        return properties;
    }

    @Override
    public @NotNull ObjectFunctionContainer<? extends ObjectFunction> dyanasisFunctions() {
        return functions;
    }

    @SuppressWarnings("PointlessBooleanExpression")
    @Override
    public boolean equals(@NotNull String cfgStr) {
        return switch (cfgStr) {
            case "true" -> value == true;
            case "false" -> value == false;
            default -> false;
        };
    }

    public class StandardObjectBoolPropertyContainer extends StandardObjectPropertyContainer {
        public StandardObjectBoolPropertyContainer() {
            super();
        }

        @Override
        public @NotNull StandardDyanasisObjectBool<Lexer> owner() {
            return StandardDyanasisObjectBool.this;
        }
    }

    public class StandardObjectBoolFunctionContainer extends StandardObjectFunctionContainer {
        public StandardObjectBoolFunctionContainer() {
            super();
        }

        @Override
        public @NotNull StandardDyanasisObjectBool<Lexer> owner() {
            return StandardDyanasisObjectBool.this;
        }
    }

    public abstract class StandardObjectBoolProperty extends StandardObjectProperty {
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

    public abstract class StandardObjectBoolFunction extends StandardObjectFunction {
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
}
