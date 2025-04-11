package net.aurika.dyanasis.api.compiler;

import net.aurika.dyanasis.api.compiler.expression.Expression;
import net.aurika.dyanasis.api.compiler.expression.DeclareLiteralObject;
import net.aurika.dyanasis.api.compiler.expression.Operation;
import net.aurika.dyanasis.api.compiler.setting.DyanasisCompilerSettings;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface DyanasisCompiler {

  @NotNull Expression compile();

  /**
   * Gets the parent compiler of this compiler instance.
   *
   * @return the parent compiler
   */
  @Nullable DyanasisCompiler parent();

  /**
   * Gets the compile settings.
   *
   * @return the compile settings
   */
  @NotNull DyanasisCompilerSettings settings();

  /**
   * Gets the original string
   *
   * @return the original string
   */
  @NotNull String originalString();

  interface MathLexer extends Lexer {

    @Override
    @NotNull Expression lex();

  }

  interface BlockLexer extends Lexer {

    @Override
    @NotNull Expression lex();

  }

  interface StatementLexer extends Lexer {

    @Override
    @NotNull Operation lex();

  }

  interface ConstantLexer extends Lexer {

    @Override
    @NotNull DeclareLiteralObject lex();

  }

  interface Lexer {

    @NotNull Expression lex();

    @NotNull DyanasisCompiler compiler();

  }

}
