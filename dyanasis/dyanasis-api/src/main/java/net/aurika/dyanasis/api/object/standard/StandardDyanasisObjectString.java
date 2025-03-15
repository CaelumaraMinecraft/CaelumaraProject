package net.aurika.dyanasis.api.object.standard;

import net.aurika.dyanasis.api.NamingContract;
import net.aurika.dyanasis.api.declaration.invokable.function.DyanasisFunctionKey;
import net.aurika.dyanasis.api.declaration.invokable.property.DyanasisGetableProperty;
import net.aurika.dyanasis.api.invoking.input.DyanasisFunctionInput;
import net.aurika.dyanasis.api.invoking.result.DyanasisFunctionResult;
import net.aurika.dyanasis.api.lexer.DyanasisLexer;
import net.aurika.dyanasis.api.object.DyanasisObject;
import net.aurika.dyanasis.api.object.DyanasisObjectNumber;
import net.aurika.dyanasis.api.object.DyanasisObjectString;
import net.aurika.dyanasis.api.type.AbstractDyanasisType;
import org.jetbrains.annotations.NotNull;

public class StandardDyanasisObjectString<Lexer extends DyanasisLexer> extends StandardDyanasisObject<String, Lexer> implements DyanasisObjectString {

    public static final String TYPE_NAME = "String";

    public static final String PROPERTY_LENGTH = "length";

    protected StandardDyanasisObjectString(@NotNull String value, @NotNull Lexer lexer, @NotNull AbstractDyanasisType typeData) {
        super(value, lexer, typeData);
    }

    @Override
    public @NotNull ObjectPropertyContainer<? extends ObjectProperty> dyanasisProperties() {
        //TODO
    }

    @Override
    public @NotNull ObjectFunctionContainer<? extends ObjectFunction> dyanasisFunctions() {
        //TODO
    }

    @Override
    public boolean equals(@NotNull String cfgStr) {
        return value.equals(cfgStr);
    }

    public class Length extends AbstractStringProperty implements DyanasisGetableProperty {
        public Length() {
            super("length");
        }

        @Override
        public @NotNull DyanasisObject getPropertyValue() {
            return new StandardDyanasisObjectNumber<>(value.length(), lexer);
        }

        @Override
        @NamingContract.Invokable
        public @NotNull String name() {
            return "length";
        }

        @Override
        public @NotNull DyanasisObjectNumber value() {
            return new StandardDyanasisObjectNumber<>(value.length(), lexer);
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
}
