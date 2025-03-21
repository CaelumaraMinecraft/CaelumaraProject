package net.aurika.dyanasis.api.lexer;

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

    /**
     * Gets the lex settings.
     *
     * @return the lex settings
     */
    @NotNull DyanasisLexerSettings settings();

    /**
     * Gets the original string
     *
     * @return the original string
     */
    @NotNull String originalString();

    /**
     * The lexed expression.
     */
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
