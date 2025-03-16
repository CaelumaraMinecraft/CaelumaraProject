package net.aurika.dyanasis.api.object.standard;

import net.aurika.dyanasis.api.NamingContract;
import net.aurika.dyanasis.api.declaration.invokable.function.DyanasisFunctionKey;
import net.aurika.dyanasis.api.declaration.invokable.property.DyanasisGetableProperty;
import net.aurika.dyanasis.api.declaration.namespace.DyanasisNamespace;
import net.aurika.dyanasis.api.invoking.input.DyanasisFunctionInput;
import net.aurika.dyanasis.api.invoking.result.DyanasisFunctionResult;
import net.aurika.dyanasis.api.lexer.DyanasisLexer;
import net.aurika.dyanasis.api.object.DyanasisObjectNumber;
import net.aurika.dyanasis.api.object.DyanasisObjectString;
import net.aurika.dyanasis.api.runtime.DyanasisRuntime;
import net.aurika.dyanasis.api.type.DyanasisType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class StandardDyanasisObjectString<Lexer extends DyanasisLexer> extends StandardDyanasisObject<String, Lexer> implements DyanasisObjectString {

    public static final String TYPE_NAME = "String";

    public static final String PROPERTY_LENGTH = "length";

    protected @NotNull StandardObjectStringPropertyContainer properties = new StandardObjectStringPropertyContainer();
    protected @NotNull StandardObjectStringFunctionContainer functions = new StandardObjectStringFunctionContainer();

    @SuppressWarnings("unchecked")
    private final @NotNull DyanasisType<? extends StandardDyanasisObjectString<Lexer>> type = standardType(runtime, TYPE_NAME, getClass(), () -> new StandardStringType(runtime, standardNS(runtime)));

    public StandardDyanasisObjectString(@NotNull DyanasisRuntime runtime, String value, @NotNull Lexer lexer) {
        this(runtime, value, lexer, null);
    }

    protected StandardDyanasisObjectString(@NotNull DyanasisRuntime runtime, String value, @NotNull Lexer lexer, @Nullable DefaultObjectDoc doc) {
        super(runtime, value, lexer, doc);
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
        return value.equals(cfgStr);
    }

    @Override
    public @NotNull DyanasisType<? extends StandardDyanasisObjectString<Lexer>> dyanasisType() {
        return type;
    }

    public class StandardObjectStringPropertyContainer extends DefaultObjectPropertyContainer<AbstractStringProperty> {
        @Override
        public @NotNull StandardDyanasisObject<String, Lexer> owner() {
            return StandardDyanasisObjectString.this;
        }
    }

    public class StandardObjectStringFunctionContainer extends DefaultObjectFunctionContainer<AbstractStringFunction> {
        @Override
        public @NotNull StandardDyanasisObject<String, Lexer> owner() {
            return StandardDyanasisObjectString.this;
        }
    }

    public class Length extends AbstractStringProperty implements DyanasisGetableProperty {
        public Length() {
            super("length");
        }

        @Override
        public @NotNull DyanasisObjectNumber getPropertyValue() {
            return value();
        }

        @Override
        @NamingContract.Invokable
        public @NotNull String name() {
            return "length";
        }

        @Override
        public @NotNull DyanasisObjectNumber value() {
            return new StandardDyanasisObjectNumber<>(runtime, value.length(), lexer);
        }
    }

    public class Substring_1 extends AbstractStringFunction {

        public Substring_1() {
            super(DyanasisFunctionKey.dyanasisFunctionKey("substring", 1));
        }

        @Override
        public @NotNull DyanasisFunctionResult apply(@NotNull DyanasisFunctionInput input) {
            return value.substring();
        }
    }

    public class Substring_2 extends AbstractStringFunction {

        public Substring_2() {
            super(DyanasisFunctionKey.dyanasisFunctionKey("substring", 2));
        }

        @Override
        public @NotNull DyanasisFunctionResult apply(@NotNull DyanasisFunctionInput input) {
            return value.substring();
        }
    }

    public abstract class AbstractStringProperty extends DefaultObjectProperty {
        public AbstractStringProperty(@NamingContract.Invokable final @NotNull String name) {
            super(name);
        }

        @Override
        public @NotNull StandardDyanasisObject<String, Lexer> owner() {
            return StandardDyanasisObjectString.this;
        }
    }

    public abstract class AbstractStringFunction extends DefaultObjectFunction {
        public AbstractStringFunction(@NotNull DyanasisFunctionKey key) {
            super(key);
        }

        @Override
        public abstract @NotNull DyanasisFunctionResult apply(@NotNull DyanasisFunctionInput input);

        @Override
        public @NotNull StandardDyanasisObject<String, Lexer> owner() {
            return StandardDyanasisObjectString.this;
        }
    }

    public class StandardStringType extends DefaultObjectType<StandardDyanasisObjectString<Lexer>> {
        public StandardStringType(@NotNull DyanasisRuntime runtime, @NotNull DyanasisNamespace namespace) {
            // noinspection unchecked
            super(runtime, namespace, StandardDyanasisObjectString.TYPE_NAME, (Class<? extends StandardDyanasisObjectString<Lexer>>) StandardDyanasisObjectString.this.getClass());
        }
    }
}
