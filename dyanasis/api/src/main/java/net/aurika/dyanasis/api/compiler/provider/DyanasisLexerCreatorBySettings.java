package net.aurika.dyanasis.api.compiler.provider;

import net.aurika.dyanasis.api.compiler.DyanasisCompiler;
import net.aurika.dyanasis.api.compiler.setting.DyanasisCompilerSettings;

public interface DyanasisLexerCreatorBySettings<Lexer extends DyanasisCompiler, Settings extends DyanasisCompilerSettings> {

  Lexer createDyanasisLexer(Settings settings);

}
