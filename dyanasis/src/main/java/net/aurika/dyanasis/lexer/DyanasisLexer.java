package net.aurika.dyanasis.lexer;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface DyanasisLexer {
    @NotNull Expression lex();

    /**
     * Gets the parent lexer of this lexer instance.
     *
     * @return the parent lexer
     */
    @Nullable DyanasisLexer parent();

    @NotNull DyanasisLexerSettings settings();

    @NotNull String originalString();

    interface Expression {

        /**
         * Evaluates and returns the result.
         *
         * @return the result
         */
        @NotNull Object evaluate();

        /**
         * Gets the corresponding lexer for this expression.
         *
         * @return the lexer
         */
        @NotNull DyanasisLexer lexer();
    }

    static int hashCode(DyanasisLexer lexer) {
        return System.identityHashCode(lexer);
    }
}
