package net.aurika.dyanasis.api.object.standard;

import net.aurika.dyanasis.api.NamingContract;
import net.aurika.dyanasis.api.declaration.invokable.function.AbstractDyanasisFunction;
import net.aurika.dyanasis.api.declaration.invokable.function.DyanasisFunctionKey;
import net.aurika.dyanasis.api.declaration.invokable.property.AbstractDyanasisProperty;
import net.aurika.dyanasis.api.declaration.invokable.property.DyanasisGetableProperty;
import net.aurika.dyanasis.api.invoking.input.DyanasisFunctionInput;
import net.aurika.dyanasis.api.invoking.result.DyanasisFunctionResult;
import net.aurika.dyanasis.api.lexer.DyanasisLexer;
import net.aurika.dyanasis.api.object.AbstractDyanasisObject;
import net.aurika.dyanasis.api.object.DyanasisObject;
import net.aurika.dyanasis.api.object.DyanasisObjectString;
import net.aurika.dyanasis.api.typedata.DyanasisTypeData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class StandardDyanasisObjectString<Lexer extends DyanasisLexer> extends AbstractDyanasisObject<String, Lexer> implements DyanasisObjectString {

    protected StandardDyanasisObjectString(@NotNull String value, @NotNull Lexer lexer, @NotNull DyanasisTypeData typeData) {
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
    public @Nullable ObjectDoc dyanasisDoc() {
        // TODO
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
        public @NotNull DyanasisObject value() {
            // TODO
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

    public abstract class AbstractStringProperty extends AbstractDyanasisProperty<StandardDyanasisObjectString<Lexer>> {
        public AbstractStringProperty(@NamingContract.Invokable final @NotNull String name) {
            super(name, StandardDyanasisObjectString.this);
        }
    }

    public abstract class AbstractStringFunction extends AbstractDyanasisFunction<StandardDyanasisObjectString<Lexer>> {
        public AbstractStringFunction(@NotNull DyanasisFunctionKey key) {
            super(key, StandardDyanasisObjectString.this);
        }

        @Override
        public abstract @NotNull DyanasisFunctionResult apply(@NotNull DyanasisFunctionInput input);
    }
}
