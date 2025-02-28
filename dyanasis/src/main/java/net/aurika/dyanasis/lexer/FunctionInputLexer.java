package net.aurika.dyanasis.lexer;

import net.aurika.dyanasis.invoking.input.DyanasisFunctionInput;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * The lexer to lex the dyanasis function invoking.
 * <blockquote><pre>
 *     return "string".subString(0, 5)
 *                               ^^^^
 * </pre></blockquote>
 */
public interface FunctionInputLexer extends DyanasisLexer {
    static FunctionInputLexerImpl standardFnInLexer(@Nullable DyanasisLexer parent, @NotNull DyanasisLexerSettings settings, @NotNull String original) {
        return new FunctionInputLexerImpl(parent, settings, original);
    }

    @Override
    @NotNull Expression lex();

    interface Expression extends DyanasisLexer.Expression {
        @NotNull DyanasisFunctionInput evaluate();

        @NotNull FunctionInputLexer lexer();
    }
}
