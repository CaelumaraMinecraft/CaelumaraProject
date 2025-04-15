package net.aurika.dyanasis.api.compiler.provider;

import net.aurika.dyanasis.api.compiler.DyanasisCompiler;
import net.aurika.dyanasis.api.compiler.setting.DefaultDyanasisCompilerSettings;

public interface DyanasisLexerCreatorBySettings<Lexer extends DyanasisCompiler, Settings extends DefaultDyanasisCompilerSettings> {

  Lexer createDyanasisLexer(Settings settings);

}
