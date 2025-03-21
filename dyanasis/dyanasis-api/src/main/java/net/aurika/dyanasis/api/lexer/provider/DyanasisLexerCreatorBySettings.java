package net.aurika.dyanasis.api.lexer.provider;

import net.aurika.dyanasis.api.lexer.DyanasisLexer;
import net.aurika.dyanasis.api.lexer.DyanasisLexerSettings;

public interface DyanasisLexerCreatorBySettings<Lexer extends DyanasisLexer, Settings extends DyanasisLexerSettings> {
    Lexer createDyanasisLexer(Settings settings);
}
