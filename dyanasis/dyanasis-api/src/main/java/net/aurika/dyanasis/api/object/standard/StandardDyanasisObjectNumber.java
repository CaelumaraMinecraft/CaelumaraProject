package net.aurika.dyanasis.api.object.standard;

import net.aurika.dyanasis.api.lexer.DyanasisLexer;
import net.aurika.dyanasis.api.object.DyanasisObjectNumber;
import org.jetbrains.annotations.NotNull;

public class StandardDyanasisObjectNumber<Lexer extends DyanasisLexer> extends StandardDyanasisObject<Number, Lexer> implements DyanasisObjectNumber {

    public StandardDyanasisObjectNumber(@NotNull Number value, @NotNull Lexer lexer) {
        super(value, lexer);
    }

    @Override
    public @NotNull ObjectPropertyContainer<? extends ObjectProperty> dyanasisProperties() {
        //TODO
    }

    @Override
    public @NotNull ObjectFunctionContainer<? extends ObjectFunction> dyanasisFunctions() {
        //TODO
        return null;
    }

    @Override
    public boolean equals(@NotNull String cfgStr) {
        //TODO
    }
}
