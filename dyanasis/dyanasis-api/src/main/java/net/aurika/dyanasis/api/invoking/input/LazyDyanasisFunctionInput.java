package net.aurika.dyanasis.api.invoking.input;

import net.aurika.dyanasis.api.lexer.DyanasisLexerSettings;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class LazyDyanasisFunctionInput extends DyanasisFunctionInput {
    private @Nullable DyanasisLexerSettings settings;
    private String[] args;

    public LazyDyanasisFunctionInput(@NotNull String original) {
        this(original, null);
    }

    /**
     * Create a {@linkplain LazyDyanasisFunctionInput} with {@code settings}.
     *
     * @param original the original string
     * @param settings the lexer settings
     */
    public LazyDyanasisFunctionInput(@NotNull String original, @Nullable DyanasisLexerSettings settings) {
        super(original);
        this.settings = settings;
    }

    @Override
    public @NotNull String @NotNull [] args() {
        if (args == null) {
            if (settings == null) {
                throw new IllegalArgumentException("Get arguments of " + LazyDyanasisFunctionInput.class.getSimpleName() + " requires non-null lexer settings.");
            } else {

            }
        } else {
            return args;
        }
    }

    /**
     * Gets the lexer settings.
     *
     * @return the lexer settings
     */
    public @Nullable DyanasisLexerSettings settings() {
        return settings;
    }

    /**
     * Sets the lexer settings
     *
     * @param settings the lexer settings
     */
    public void settings(@Nullable DyanasisLexerSettings settings) {
        this.settings = settings;
    }
}
