package net.aurika.dyanasis.api.compiler;

import net.aurika.dyanasis.api.compiler.expression.DeclareLiteralObject;
import net.aurika.dyanasis.api.compiler.expression.Expression;
import net.aurika.dyanasis.api.compiler.expression.Operation;
import org.jetbrains.annotations.NotNull;

public interface DefaultDyanasisCompiler extends DyanasisCompiler {

  @NotNull Lexer lex(@NotNull String string);

  @Override
  default @NotNull Expression compile(@NotNull String string) {
    return lex(string).evaluate();
  }

  interface MathLexer extends Lexer {

    @Override
    @NotNull Expression evaluate();

  }

  interface BlockLexer extends Lexer {

    @Override
    @NotNull Expression evaluate();

  }

  interface StatementLexer extends Lexer {

    @Override
    @NotNull Operation evaluate();

  }

  interface ConstantLexer extends Lexer {

    @Override
    @NotNull DeclareLiteralObject evaluate();

  }

  interface Lexer {

    @NotNull Expression evaluate();

    @NotNull DyanasisCompiler compiler();

  }

}
