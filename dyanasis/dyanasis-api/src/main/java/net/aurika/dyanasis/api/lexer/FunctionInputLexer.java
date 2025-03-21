package net.aurika.dyanasis.api.lexer;

import net.aurika.dyanasis.api.invoking.input.DyanasisFunctionInput;
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
    static DefaultFunctionInputLexer standardFnInLexer(@Nullable DyanasisLexer parent, @NotNull DyanasisLexerSettings settings, @NotNull String original) {
        return new DefaultFunctionInputLexer(parent, settings, original);
    }

    @Override
    @NotNull Expression lex();

    interface Expression extends DyanasisLexer.Expression {
        @Override
        @NotNull DyanasisFunctionInput evaluate();

        @Override
        @NotNull FunctionInputLexer lexer();
    }
}
