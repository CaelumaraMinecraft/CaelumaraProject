package net.aurika.dyanasis.api.object.standard;

import net.aurika.dyanasis.api.NamingContract;
import net.aurika.dyanasis.api.declaration.invokable.function.DyanasisFunctionKey;
import net.aurika.dyanasis.api.declaration.namespace.DyanasisNamespace;
import net.aurika.dyanasis.api.invoking.input.DyanasisFunctionInput;
import net.aurika.dyanasis.api.invoking.result.DyanasisFunctionResult;
import net.aurika.dyanasis.api.lexer.DyanasisLexer;
import net.aurika.dyanasis.api.object.DyanasisObject;
import net.aurika.dyanasis.api.object.DyanasisObjectBool;
import net.aurika.dyanasis.api.typedata.AbstractDyanasisTypeData;
import org.jetbrains.annotations.NotNull;

public class StandardDyanasisObjectBool<Lexer extends DyanasisLexer> extends StandardDyanasisObject<Boolean, Lexer> implements DyanasisObjectBool {

    protected static final AbstractDyanasisTypeData TYPE_DATA = new AbstractDyanasisTypeData()

    protected final StandardObjectBoolPropertyContainer properties = new StandardObjectBoolPropertyContainer();
    protected final StandardObjectBoolFunctionContainer functions = new StandardObjectBoolFunctionContainer();

    public StandardDyanasisObjectBool(boolean value, Lexer lexer) {
        super(value, lexer, null, TYPE_DATA);
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
