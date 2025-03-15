package net.aurika.dyanasis.api.lexer.reference;

import net.aurika.dyanasis.api.lexer.DyanasisLexer;
import net.aurika.dyanasis.api.lexer.DyanasisLexerSettings;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

@ApiStatus.Experimental
public interface DyanasisLexerReference<Lexer extends DyanasisLexer> {
    @NotNull Lexer createLexer(@NotNull DyanasisLexerSettings settings, @NotNull String original);
}
