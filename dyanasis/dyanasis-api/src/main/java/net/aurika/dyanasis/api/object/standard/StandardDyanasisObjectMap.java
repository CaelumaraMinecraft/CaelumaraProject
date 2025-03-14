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
import net.aurika.dyanasis.api.object.DyanasisObjectMap;
import net.aurika.dyanasis.api.typedata.DyanasisTypeData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class StandardDyanasisObjectMap<Lexer extends DyanasisLexer> extends AbstractDyanasisObject<Map<?, ?>, Lexer> implements DyanasisObjectMap {

    public StandardDyanasisObjectMap(@NotNull Map<?, ?> value, @NotNull Lexer lexer, @NotNull DyanasisTypeData typeData) {
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
        //TODO
    }

    @Override
    public boolean equals(@NotNull String cfgStr) {
        //TODO lex and equals
    }

    public class Size extends AbstractMapProperty implements DyanasisGetableProperty {

        public Size() {
            super("size");
        }

        @Override
        public @NotNull StandardDyanasisObjectNumber<? extends Lexer> value() {
            return new StandardDyanasisObjectNumber<>(value.size(), lexer);
        }

        @Override
        public @NotNull StandardDyanasisObjectNumber<? extends Lexer> getPropertyValue() {
            return value();
        }
    }

    public abstract class AbstractMapProperty extends AbstractDyanasisProperty<StandardDyanasisObjectMap<Lexer>> {
        public AbstractMapProperty(@NamingContract.Invokable final @NotNull String name) {
            super(name, StandardDyanasisObjectMap.this);
        }
    }

    public abstract class AbstractMapFunction extends AbstractDyanasisFunction<StandardDyanasisObjectMap<Lexer>> {
        public AbstractMapFunction(@NotNull DyanasisFunctionKey key) {
            super(key, StandardDyanasisObjectMap.this);
        }

        @Override
        public abstract @NotNull DyanasisFunctionResult apply(@NotNull DyanasisFunctionInput input);
    }
}
