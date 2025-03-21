package net.aurika.dyanasis.api.lexer;

import net.aurika.dyanasis.api.invoking.input.DyanasisFunctionInput;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DefaultFunctionInputLexer extends AbstractDyanasisLexer implements FunctionInputLexer {

    public DefaultFunctionInputLexer(@Nullable DyanasisLexer parent, @NotNull DyanasisLexerSettings settings, @NotNull String original) {
        super(parent, settings, original);
    }

    @Override
    public @NotNull ExpressionImpl lex() {

    }

    public abstract class ExpressionImpl implements FunctionInputLexer.Expression {

        @Override
        public abstract @NotNull DyanasisFunctionInput evaluate();

        @Override
        public @NotNull FunctionInputLexer lexer() {
            return DefaultFunctionInputLexer.this;
        }
    }
}
