package net.aurika.dyanasis.object.standard;

import net.aurika.dyanasis.NamingContract;
import net.aurika.dyanasis.declaration.invokable.function.DyanasisFunctionKey;
import net.aurika.dyanasis.declaration.invokable.function.container.DyanasisFunctions;
import net.aurika.dyanasis.declaration.invokable.property.DyanasisGetableProperty;
import net.aurika.dyanasis.declaration.invokable.property.container.DyanasisProperties;
import net.aurika.dyanasis.invoking.input.DyanasisFunctionInput;
import net.aurika.dyanasis.invoking.result.DyanasisFunctionResult;
import net.aurika.dyanasis.lexer.DyanasisLexerSettings;
import net.aurika.dyanasis.object.AbstractDyanasisObject;
import net.aurika.dyanasis.object.DyanasisObject;
import net.aurika.dyanasis.object.DyanasisObjectString;
import org.jetbrains.annotations.NotNull;

public class StandardDyanasisObjectString extends AbstractDyanasisObject<String> implements DyanasisObjectString {

    public StandardDyanasisObjectString(@NotNull String value, @NotNull DyanasisLexerSettings settings) {
        super(value, settings);
    }

    @Override
    public @NotNull DyanasisProperties dyanasisProperties() {
        TODO
    }

    @Override
    public @NotNull DyanasisFunctions dyanasisFunctions() {
        TODO
    }

    @Override
    public boolean equals(@NotNull String cfgStr) {
        return value.equals(cfgStr);
    }

    public class Length extends AbstractProperty implements DyanasisGetableProperty {
        @Override
        public @NotNull DyanasisObject get() {
            return new StandardDyanasisObjectNumber(value.length(), settings);
        }

        @Override
        @NamingContract.Invokable
        public @NotNull String name() {
            return "length";
        }
    }

    public class Substring_1 extends AbstractFunction {
        public Substring_1() {
            super(DyanasisFunctionKey.dyanasisFunctionKey("substring", 1));
        }

        @Override
        public @NotNull DyanasisFunctionResult apply(@NotNull DyanasisFunctionInput input) {
            return value.substring();
        }
    }
}
