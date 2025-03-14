package net.aurika.dyanasis.api.object.standard;

import net.aurika.dyanasis.api.NamingContract;
import net.aurika.dyanasis.api.declaration.invokable.function.AbstractDyanasisFunction;
import net.aurika.dyanasis.api.declaration.invokable.function.DyanasisFunctionKey;
import net.aurika.dyanasis.api.declaration.invokable.property.AbstractDyanasisProperty;
import net.aurika.dyanasis.api.declaration.invokable.property.DyanasisGetableProperty;
import net.aurika.dyanasis.api.invoking.input.DyanasisFunctionInput;
import net.aurika.dyanasis.api.invoking.result.DyanasisFunctionResult;
import net.aurika.dyanasis.api.lexer.DyanasisLexer;
import net.aurika.dyanasis.api.object.DyanasisObjectMap;
import net.aurika.dyanasis.api.typedata.AbstractDyanasisTypeData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class StandardDyanasisObjectMap<Lexer extends DyanasisLexer> extends StandardDyanasisObject<Map<?, ?>, Lexer> implements DyanasisObjectMap {

    protected static final AbstractDyanasisTypeData TYPE_DATA

            protected final

    public StandardDyanasisObjectMap(@NotNull Map<?, ?> value, @NotNull Lexer lexer) {
        this(value, lexer,null, TYPE_DATA);
    }

    protected StandardDyanasisObjectMap(Map<?, ?> value, @NotNull Lexer lexer, @Nullable StandardObjectDoc doc, @NotNull AbstractDyanasisTypeData typeData) {
        super(value, lexer, doc, typeData);
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

    public abstract class AbstractMapProperty extends AbstractDyanasisProperty {
        public AbstractMapProperty(@NamingContract.Invokable final @NotNull String name) {
            super(name);
        }

        @Override
        public @NotNull StandardDyanasisObjectMap<Lexer> owner() {
            return StandardDyanasisObjectMap.this;
        }
    }

    public abstract class AbstractMapFunction extends AbstractDyanasisFunction {
        public AbstractMapFunction(@NotNull DyanasisFunctionKey key) {
            super(key);
        }

        @Override
        public abstract @NotNull DyanasisFunctionResult apply(@NotNull DyanasisFunctionInput input);

        @Override
        public @NotNull StandardDyanasisObjectMap<Lexer> owner() {
            return StandardDyanasisObjectMap.this;
        }
    }
}
