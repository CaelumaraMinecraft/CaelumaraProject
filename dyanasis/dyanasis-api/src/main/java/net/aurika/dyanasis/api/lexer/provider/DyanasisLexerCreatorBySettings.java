package net.aurika.dyanasis.api.lexer.provider;

import net.aurika.dyanasis.api.lexer.DyanasisCompiler;
import net.aurika.dyanasis.api.lexer.setting.DyanasisCompilerSettings;

public interface DyanasisLexerCreatorBySettings<Lexer extends DyanasisCompiler, Settings extends DyanasisCompilerSettings> {

  Lexer createDyanasisLexer(Settings settings);

}
