package net.aurika.dyanasis.lexer;

import net.aurika.dyanasis.invoking.input.DyanasisFunctionInput;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FunctionInputLexerImpl extends DyanasisLexerImpl implements FunctionInputLexer {

    public FunctionInputLexerImpl(@Nullable DyanasisLexer parent, @NotNull DyanasisLexerSettings settings, @NotNull String original) {
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
            return FunctionInputLexerImpl.this;
        }
    }
}
