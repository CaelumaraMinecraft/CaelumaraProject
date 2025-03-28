package net.aurika.dyanasis.api.lexer.provider;

import net.aurika.dyanasis.api.lexer.DyanasisCompiler;

/**
 * An interface to generate a dyanasis lexer.
 *
 * @param <Lexer> the lexer type
 */
@FunctionalInterface
public interface DyanasisLexerCreator<Lexer extends DyanasisCompiler> {

  /**
   * Creates a dyanasis lexer {@linkplain Lexer}.
   *
   * @return the created lexer
   */
  Lexer createDyanasisLexer();

}
