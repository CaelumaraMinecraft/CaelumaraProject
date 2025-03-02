package net.aurika.dyanasis.object.standard;

import net.aurika.dyanasis.NamingContract;
import net.aurika.dyanasis.declaration.invokable.function.container.DyanasisFunctions;
import net.aurika.dyanasis.declaration.invokable.property.DyanasisGetableProperty;
import net.aurika.dyanasis.declaration.invokable.property.container.DyanasisProperties;
import net.aurika.dyanasis.lexer.DyanasisLexerSettings;
import net.aurika.dyanasis.object.AbstractDyanasisObject;
import net.aurika.dyanasis.object.DyanasisObjectMap;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class StandardDyanasisPropertyMap extends AbstractDyanasisObject<Map<?, ?>> implements DyanasisObjectMap {

    public StandardDyanasisPropertyMap(@NotNull Map<?, ?> value, @NotNull DyanasisLexerSettings settings) {
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
        TODO // lex and equals
    }

    public class Size extends AbstractDyanasisObject<Map<?, ?>>.AbstractProperty implements DyanasisGetableProperty {
        @Override
        @NamingContract.Invokable
        public @NotNull String name() {
            return "size";
        }

        @Override
        public @NotNull StandardDyanasisObjectNumber get() {
            return new StandardDyanasisObjectNumber(value.size(), settings);
        }
    }
}
